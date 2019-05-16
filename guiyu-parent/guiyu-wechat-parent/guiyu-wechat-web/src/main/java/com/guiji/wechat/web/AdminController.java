package com.guiji.wechat.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.guiji.auth.api.IApiLogin;
import com.guiji.auth.api.IAuth;
import com.guiji.component.result.Result;
import com.guiji.guiyu.message.component.FanoutSender;
import com.guiji.user.dao.entity.SysUser;
import com.guiji.wechat.dtos.AuthAccessTokenDto;
import com.guiji.wechat.dtos.CustomMenuCreateDto;
import com.guiji.wechat.dtos.KeFuBindDto;
import com.guiji.wechat.dtos.WeChatUserDto;
import com.guiji.wechat.messages.UserBindWeChatMessage;
import com.guiji.wechat.scheduler.AccessTokenScheduler;
import com.guiji.wechat.service.api.WeChatCommonApi;
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
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

import static com.guiji.wechat.util.constants.RabbitMqConstant.USER_BIND_WECHAT_EXCHANGE;

@Controller
@RequestMapping("wechat")
public class AdminController {

    @Resource
    private AccessTokenScheduler accessTokenScheduler;

    private Logger logger = LoggerFactory.getLogger(AdminController.class);

    private static final String BASE_SCOPE = "snsapi_base";

    private static final String GUEST = "微信游客";

    private static final String STATIC_GUIJI_DOMAIN = "tel.guiji.ai";

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private IAuth iAuth;

    @Resource
    private IApiLogin iApiLogin;

    @Resource
    private WeChatUrlProperty weChatUrlProperty;

    @Resource
    private WeChatEnvProperties weChatEnvProperties;

    @Resource
    private AccessToken accessToken;

    @Resource
    private FanoutSender fanoutSender;

    @Resource
    private WeChatCommonApi weChatCommonApi;

    @RequestMapping("manual/update/token")
    @ResponseBody
    public ResponseEntity<String> manualUpdateToken(){
        return accessTokenScheduler.updateAccessToken();
    }

    @RequestMapping("custom/menu/create")
    @ResponseBody
    public ResponseEntity<String> createMenu(@RequestBody CustomMenuCreateDto customMenuCreateDto){
        logger.info("custom menu create request:{}", JSON.toJSONString(customMenuCreateDto));

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(weChatUrlProperty.getCustomMenuCreateUrl())
                .queryParam(WeChatConstant.PARAM_ACCESS_TOKEN, accessToken.getValue());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity = new HttpEntity<>(JSON.toJSONString(customMenuCreateDto), headers);

        return restTemplate.postForEntity(builder.build().toUri(), entity, String.class);
    }

    @RequestMapping("custom/menu/get")
    @ResponseBody
    public ResponseEntity<String> getMenu(){

        StringHttpMessageConverter messageConverter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        RestTemplate localRest = new RestTemplateBuilder().additionalMessageConverters(messageConverter).build();

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(weChatUrlProperty.getCustomMenuGetUrl())
                .queryParam(WeChatConstant.PARAM_ACCESS_TOKEN, accessToken.getValue());

        return localRest.getForEntity(builder.build().toUri(), String.class);
    }

    @RequestMapping("custom/menu/delete")
    @ResponseBody
    public ResponseEntity<String> deleteMenu(){

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(weChatUrlProperty.getCustomMenuDeleteUrl())
                .queryParam(WeChatConstant.PARAM_ACCESS_TOKEN, accessToken.getValue());

        return restTemplate.getForEntity(builder.build().toUri(), String.class);
    }

    @RequestMapping("custom/menu/create/kefu")
    @ResponseBody
    public ResponseEntity<String> createAuthMenu(){

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

        return createMenu(createDto);
    }

    @RequestMapping("custom/menu/auth")
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
            return new ModelAndView("redirect:" + buildKeFuUrl(STATIC_GUIJI_DOMAIN, GUEST));
        }

        String openId = authAccessTokenDto.getOpenid();
        SysUser user = getUserByCheckUserBind(openId);
        if(null != user){
            return new ModelAndView("redirect:" + buildKeFuUrl(STATIC_GUIJI_DOMAIN, user.getUsername(), user.getOrgName()));
        }

        return new ModelAndView("redirect:" + buildLoginUrl(openId));
    }

    @RequestMapping("custom/menu/goto/kefu")
    public ModelAndView gotoKeFu(@RequestParam(value = "domain", required = false, defaultValue = STATIC_GUIJI_DOMAIN) String domain,
                                 @RequestParam("userName") String userName){
        logger.info("goto kefu request domain:{}, userName:{}", domain, userName);

        return new ModelAndView("redirect:" + buildKeFuUrl(domain, userName));
    }


    @RequestMapping("custom/menu/login")
    @ResponseBody
    public Result.ReturnData<Boolean> checkLogin(@RequestParam(value = "domain", required = false, defaultValue = STATIC_GUIJI_DOMAIN) String domain,
                                                 @RequestParam("openId") String openId,
                                                 @RequestParam("userName") String userName,
                                                 @RequestParam("password") String password){

        logger.info("wechat check login, openId:{}, userName:{}, password:{}", openId, userName, password);

        Long userId;

        try{
            userId = iApiLogin.getUserIdByCheckLogin(userName, password).getBody();
        }catch (Exception e){
            logger.error("failed get userId by check login", e);
            return Result.error("1000612");
        }

        if(null == userId){
            return Result.error("1000613");
        }

        sendUserBindWeChatMessage(openId, userId);

        return Result.ok(true);
    }
    private String buildKeFuUrl(String domain, String userName){
        return buildKeFuUrl(domain, userName, GUEST);
    }

    private String buildKeFuUrl(String domain, String userName, String orgName){

        logger.info("domain:{}, userName:{}", domain, userName);

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(weChatEnvProperties.getKeFuUrl())
                .queryParam("userName", userName)
                .queryParam("orgName", orgName);

        return builder.build().toUri().toString();
    }

    private String buildLoginUrl(String openId){
        return weChatEnvProperties.getUserLoginUrl() + "?openId=" + openId;
    }

    private SysUser getUserByCheckUserBind(String openId){
        try{
            List<SysUser> userList = iAuth.getUserByOpenId(openId).getBody();
            logger.info("openId:{}, userList:{}", openId, JSON.toJSON(userList));

            if(CollectionUtils.isEmpty(userList)){
                return null;
            }
            return userList.get(0);
        }catch (Exception e){
            logger.error("failed get user by openId", e);
            return null;
        }
    }

    private void keFuBind(String openId, String userName, String domain){

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(weChatEnvProperties.getKeFuBindUrl());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.add("access-token", weChatEnvProperties.getKeFuBindAccessToken());

        KeFuBindDto keFuBindDto = KeFuBindDto.build()
                .setDomain(domain)
                .setAccount(userName)
                .setOpen_id(openId);

        HttpEntity<String> entity = new HttpEntity<>(JSON.toJSONString(keFuBindDto), headers);

        restTemplate.postForEntity(builder.build().toUri(), entity, String.class);
        logger.info("kefu bind url:{}, entity:{}", builder.build().toUri(), JSON.toJSONString(entity));
    }

    private void sendUserBindWeChatMessage(String openId, Long userId){

        WeChatUserDto weChatUserDto = weChatCommonApi.getWeChatUserInfo(openId);
        UserBindWeChatMessage message = new UserBindWeChatMessage();

        message.setWeChatNickName(weChatUserDto.getNickname());
        message.setOpenId(openId);
        message.setBindTime(new Date());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", String.valueOf(userId));
        message.setCallbackParameter(jsonObject.toJSONString());

        fanoutSender.send(USER_BIND_WECHAT_EXCHANGE, JSON.toJSONString(message));
        logger.info("after login, send user bind weChat message:{}", JSON.toJSONString(message));
    }
}
