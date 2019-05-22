package com.guiji.exception;

import com.alibaba.fastjson.JSONObject;
import com.guiji.cache.ThirdApiUser;
import com.guiji.cache.UserCacheUtil;
import com.guiji.support.MyEncryptHttpMessageConverter;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * @Classname MsgConvertExceptionHandler
 * @Description TODO
 * @Date 2019/5/6 17:25
 * @Created by qinghua
 */
@ControllerAdvice
public class MsgConvertExceptionHandler {

    /** logger */
    private final static Logger LOGGER = LoggerFactory.getLogger(MsgConvertExceptionHandler.class);

    @Autowired
    UserCacheUtil userCacheUtil;

    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    //    @ExceptionHandler(value={RuntimeException.class,MyRuntimeException.class})
    //    @ExceptionHandler//处理所有异常
    @ResponseBody //在返回自定义相应类的情况下必须有，这是@ControllerAdvice注解的规定
    public JSONObject exceptionHandler(RuntimeException e, HttpServletResponse response) {

        LOGGER.error("message convert error : {}", e.getMessage());

        JSONObject json = new JSONObject();

        json.put("code", 1);
        json.put("msg", ThirdApiExceptionEnum.ILLEGAL_ARG.getErrorCode());
        json.put("success", 1);

        Long userId = MyEncryptHttpMessageConverter.userIdMap.get();

        ThirdApiUser thirdApiUser = userCacheUtil.getUserInfoByUserId(userId);

        //加签
        json.put("sign", DigestUtils.md5Hex(String.format("code=%s&msg=%s&success=%s%s", json.get("code"), json.get("msg"), json.get("success"), thirdApiUser.getClientSecret())).toUpperCase());

        return json;
    }
}