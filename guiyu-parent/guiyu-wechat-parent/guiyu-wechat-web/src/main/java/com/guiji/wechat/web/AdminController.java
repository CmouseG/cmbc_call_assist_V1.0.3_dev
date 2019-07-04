package com.guiji.wechat.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.guiji.auth.api.IApiLogin;
import com.guiji.auth.api.IAuth;
import com.guiji.auth.api.IOrg;
import com.guiji.component.result.Result;
import com.guiji.guiyu.message.component.FanoutSender;
import com.guiji.user.dao.entity.SysOrganization;
import com.guiji.user.dao.entity.SysUser;
import com.guiji.wechat.dtos.*;
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
import org.springframework.beans.BeanUtils;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    private IOrg iOrg;

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
        SysUserDto sysUserDto = getUserByCheckUserBind(openId);
        if(null != sysUserDto){
            return new ModelAndView("redirect:" + buildKeFuUrl(STATIC_GUIJI_DOMAIN, sysUserDto));
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
        SysUserDto sysUserDto = new SysUserDto();
        sysUserDto.setUsername(userName);
        sysUserDto.setOrgName(GUEST);

        return buildKeFuUrl(domain, sysUserDto);
    }

    private String buildKeFuUrl(String domain, SysUserDto sysUserDto){

        logger.info("domain:{}, sysUserDto:{}", domain, JSON.toJSONString(sysUserDto));

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(weChatEnvProperties.getKeFuUrl())
                .queryParam("userName", sysUserDto.getUsername())
                .queryParam("orgName", sysUserDto.getOrgName())
                .queryParam("businessEmail", sysUserDto.getBusinessEmail());

        return builder.build().toUri().toString();
    }

    private String buildLoginUrl(String openId){
        return weChatEnvProperties.getUserLoginUrl() + "?openId=" + openId;
    }

    private SysUserDto getUserByCheckUserBind(String openId){
        try{
            List<SysUser> userList = iAuth.getUserByOpenId(openId).getBody();
            logger.info("openId:{}, userList:{}", openId, JSON.toJSONString(userList));

            if(CollectionUtils.isEmpty(userList)){
                return null;
            }

            SysUserDto sysUserDto = new SysUserDto();
            BeanUtils.copyProperties(userList.get(0), sysUserDto);

            SysOrganization sysOrganization = iAuth.getOrgByUserId(sysUserDto.getId()).getBody();
            logger.info("sysOrganization:{}", JSON.toJSONString(sysOrganization));
            sysUserDto.setOrgCode(sysOrganization.getCode());
            sysUserDto.setOrgName(sysOrganization.getName());

            logger.info("userName:{},userName2:{}", sysUserDto.getUsername(), userList.get(0).getUsername());
            Map userBusinessInfoMap = iOrg.getOrgByUsername(userList.get(0).getUsername()).getBody();

            logger.info("userBusinessInfoMap:{}", JSON.toJSONString(userBusinessInfoMap));
            if(!CollectionUtils.isEmpty(userBusinessInfoMap)){
                sysUserDto.setBusinessEmail(String.valueOf(userBusinessInfoMap.get("business_email")));
            }

            return sysUserDto;
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
