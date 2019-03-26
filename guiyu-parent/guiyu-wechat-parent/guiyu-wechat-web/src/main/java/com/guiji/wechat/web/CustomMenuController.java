package com.guiji.wechat.web;

import com.alibaba.fastjson.JSON;
import com.guiji.auth.api.IAuth;
import com.guiji.wechat.dtos.AuthAccessTokenDto;
import com.guiji.wechat.dtos.CustomMenuCreateDto;
import com.guiji.wechat.util.AccessToken;
import com.guiji.wechat.util.constants.WeChatConstant;
import com.guiji.wechat.util.properties.WeChatEnvProperties;
import com.guiji.wechat.util.properties.WeChatUrlProperty;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("wechat/custom/menu")
public class CustomMenuController {

    private Logger logger = LoggerFactory.getLogger(CustomMenuController.class);

    private static final String BASE_SCOPE = "snsapi_base";

    private static final String GUEST = "微信游客";

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private IAuth iAuth;

    @Resource
    private WeChatUrlProperty weChatUrlProperty;

    @Resource
    private WeChatEnvProperties weChatEnvProperties;

    @Resource
    private AccessToken accessToken;

    @PostMapping("create")
    public ResponseEntity<String> createMenu(@RequestBody CustomMenuCreateDto customMenuCreateDto){
        logger.info("custom menu create request:{}", JSON.toJSONString(customMenuCreateDto));

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(weChatUrlProperty.getCustomMenuCreateUrl())
                .queryParam(WeChatConstant.PARAM_ACCESS_TOKEN, accessToken.getValue());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity = new HttpEntity<>(JSON.toJSONString(customMenuCreateDto), headers);

        return restTemplate.postForEntity(builder.build().toUri(), entity, String.class);
    }

    @GetMapping("get")
    public ResponseEntity<String> getMenu(){

        StringHttpMessageConverter messageConverter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        RestTemplate localRest = new RestTemplateBuilder().additionalMessageConverters(messageConverter).build();

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(weChatUrlProperty.getCustomMenuGetUrl())
                .queryParam(WeChatConstant.PARAM_ACCESS_TOKEN, accessToken.getValue());

        return localRest.getForEntity(builder.build().toUri(), String.class);
    }

    @GetMapping("delete")
    public ResponseEntity<String> deleteMenu(){

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(weChatUrlProperty.getCustomMenuDeleteUrl())
                .queryParam(WeChatConstant.PARAM_ACCESS_TOKEN, accessToken.getValue());

        return restTemplate.getForEntity(builder.build().toUri(), String.class);
    }

    @GetMapping("auth")
    public ModelAndView auth(HttpServletRequest request){
        logger.info("weChat auth request parameterMap:{}", JSON.toJSONString(request.getParameterMap()));

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(weChatUrlProperty.getAuthAccessTokenUrl())
                .queryParam(WeChatConstant.PARAM_APPID, weChatEnvProperties.getAppId())
                .queryParam(WeChatConstant.PARAM_SECRET, weChatEnvProperties.getAppSecret())
                .queryParam(WeChatConstant.PARAM_CODE, request.getParameter(WeChatConstant.PARAM_CODE))
                .queryParam(WeChatConstant.PARAM_GRANT_TYPE, "authorization_code");

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(builder.build().toUri(), String.class);

        AuthAccessTokenDto authAccessTokenDto = JSON.parseObject(responseEntity.getBody(), AuthAccessTokenDto.class);

        logger.info("weChat auth responseEntity:{}", JSON.toJSONString(authAccessTokenDto));

        if(authAccessTokenDto == null || StringUtils.isBlank(authAccessTokenDto.getOpenid())){
            logger.error("failed get weChat auth!");
            return new ModelAndView("redirect:" + weChatEnvProperties.getKeFuUrl());
        }



        // TODO: 19-3-25  
        return new ModelAndView("redirect:" + buildKeFuUrl("xiong"));
    }

    @GetMapping("create/kefu")
    public void createAuthMenu(){

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(weChatUrlProperty.getAuthUrl())
                .queryParam(WeChatConstant.PARAM_APPID, weChatEnvProperties.getAppId())
                .queryParam("redirect_uri", weChatEnvProperties.getAuthRedirectUrl())
                .queryParam("response_type", WeChatConstant.PARAM_CODE)
                .queryParam(WeChatConstant.PARAM_SCOPE, BASE_SCOPE)
                .queryParam(WeChatConstant.PARAM_STATE, "123#wechat_redirect");


        CustomMenuCreateDto.Button keFuBtn = CustomMenuCreateDto.Button.build()
                .setName("在线客服")
                .setType("view")
                .setUrl(builder.build().toUri().toString());

        CustomMenuCreateDto createDto = CustomMenuCreateDto.build().addButton(keFuBtn);

        createMenu(createDto);
    }

    private String buildKeFuUrl(String userName){

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(weChatEnvProperties.getKeFuUrl())
                .queryParam("guiji_hostname", "tel.guiji.ai")
                .queryParam("name", userName);

        return builder.build().toUri().toString();
    }

}
