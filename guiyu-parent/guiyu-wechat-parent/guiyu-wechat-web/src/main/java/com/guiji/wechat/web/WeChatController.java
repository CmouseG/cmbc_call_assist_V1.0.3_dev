package com.guiji.wechat.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.guiji.component.result.Result;
import com.guiji.wechat.api.WeChatApi;
import com.guiji.wechat.dtos.QRCodeReqDto;
import com.guiji.wechat.util.constants.WeChatConstant;
import com.guiji.wechat.util.properties.WeChatUrlProperty;
import com.guiji.wechat.vo.QRCodeReqVO;
import com.guiji.wechat.vo.QRCodeRpsVO;
import com.guiji.wechat.vo.SendMsgReqVO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

import static com.guiji.wechat.util.constants.WeChatConstant.*;
import static com.guiji.wechat.util.enums.WeChatErrorEnum.ERROR_TRANSFER;

@Controller
public class WeChatController implements WeChatApi {

    private Logger logger = LoggerFactory.getLogger(WeChatController.class);

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private WeChatUrlProperty weChatUrlProperty;

    @PostMapping("wechat/show/qrcode")
    public void show(@RequestBody QRCodeReqVO qrCodeReqVO, HttpServletResponse response) throws Exception {

        Result.ReturnData<QRCodeRpsVO> returnData = getQRCode(qrCodeReqVO);

        response.setContentType("image/png");
        OutputStream os = response.getOutputStream();
        os.write(returnData.getBody().getQrCodeBytes());
        os.flush();
        os.close();
    }


    @Override
    @PostMapping(value = "get/qrCode")
    @ResponseBody
    public Result.ReturnData<QRCodeRpsVO> getQRCode(@RequestBody QRCodeReqVO qrCodeReqVO) {

        logger.info("create qr code request:{}", JSON.toJSONString(qrCodeReqVO));

        QRCodeRpsVO qrCodeRpsVO = generateQRCodeTicket(qrCodeReqVO);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.IMAGE_JPEG);
        HttpEntity<Resource> httpEntity = new HttpEntity<>(httpHeaders);

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(weChatUrlProperty.getQrCodeShowUrl())
                .queryParam(WeChatConstant.PARAM_TICKET, qrCodeRpsVO.getTicket());

        try{
            ResponseEntity<byte[]> response = restTemplate.exchange(builder.build().encode().toUri().toString(), HttpMethod.GET, httpEntity, byte[].class);
            logger.info("weChat get qrCode image response{}", response.toString());
            qrCodeRpsVO.setQrCodeBytes(response.getBody());

        }catch (Exception e){
            logger.error("weChat get qrCode image error{}", e);
            return Result.error("微信接口请求失败，请稍侯重试!");
        }

        return Result.ok(qrCodeRpsVO);
    }

    @Override
    @PostMapping(value = "send/message")
    @ResponseBody
    public Result.ReturnData<String> send(@RequestBody SendMsgReqVO sendMsgReqVO) {

        logger.info("sendMsgReqVO:{}", JSON.toJSONString(sendMsgReqVO));

        String accessToken = stringRedisTemplate.opsForValue().get(WeChatConstant.ACCESS_TOKEN_CACHE_KEY);

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(weChatUrlProperty.getTemplateMessageUrl())
                .queryParam(WeChatConstant.PARAM_ACCESS_TOKEN, accessToken);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity = new HttpEntity<>(buildTemplateJsonStr(sendMsgReqVO), headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(builder.build().toUri(), entity, String.class);

        logger.info("send message result:{}", responseEntity.toString());

        if (HttpStatus.OK != responseEntity.getStatusCode()) {
            logger.error(ERROR_TRANSFER.getDesc());
            return Result.error(ERROR_TRANSFER.getKey());
        }

        return Result.ok();
    }

    @PostMapping(value = "wechat/send/message")
    @ResponseBody
    public Result.ReturnData<String> sendMessage(@RequestBody SendMsgReqVO sendMsgReqVO) {
        return send(sendMsgReqVO);
    }


    private QRCodeRpsVO generateQRCodeTicket(QRCodeReqVO qrCodeReqVO) {
        String accessToken = stringRedisTemplate.opsForValue().get(WeChatConstant.ACCESS_TOKEN_CACHE_KEY);

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(weChatUrlProperty.getQrCodeCreateUrl())
                .queryParam(WeChatConstant.PARAM_ACCESS_TOKEN, accessToken);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        QRCodeReqDto qrCodeReqDto = new QRCodeReqDto();
        qrCodeReqDto.setExpire_seconds(qrCodeReqVO.getExpireSeconds());
        qrCodeReqDto.setAction_name(qrCodeReqVO.getActionName());
        qrCodeReqDto.setPenetrateParameter(qrCodeReqVO.getCallbackParameter());

        HttpEntity<String> entity = new HttpEntity<>(JSON.toJSONString(qrCodeReqDto), headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(builder.build().toUri(), entity, String.class);

        logger.info("qr code ticket:{}", responseEntity.getBody());
        return JSON.parseObject(responseEntity.getBody(), QRCodeRpsVO.class);
    }

    private String buildTemplateJsonStr(SendMsgReqVO sendMsgReqVO) {
        JSONObject json = new JSONObject();

        json.put(TEMPLATE_PARAM_TO_USER, sendMsgReqVO.getOpenID());
        json.put(TEMPLATE_PARAM_TEMPLATE_ID, sendMsgReqVO.getTemplateId());

        if (StringUtils.isNotBlank(sendMsgReqVO.getUrl())) {
            json.put(TEMPLATE_PARAM_URL, sendMsgReqVO.getUrl());
        }

        if (StringUtils.isNotBlank(sendMsgReqVO.getAppId()) && StringUtils.isNotBlank(sendMsgReqVO.getPagePath())) {
            JSONObject miniProgramJson = new JSONObject();
            miniProgramJson.put(TEMPLATE_PARAM_APP_ID, sendMsgReqVO.getAppId());
            miniProgramJson.put(TEMPLATE_PARAM_PAGE_PATH, sendMsgReqVO.getPagePath());
            json.put(TEMPLATE_PARAM_MINI_PROGRAM, miniProgramJson);
        }

        json.put(TEMPLATE_PARAM_DATA, sendMsgReqVO.getData());

        logger.info("template message:{}", json.toJSONString());

        return json.toJSONString();
    }
}
