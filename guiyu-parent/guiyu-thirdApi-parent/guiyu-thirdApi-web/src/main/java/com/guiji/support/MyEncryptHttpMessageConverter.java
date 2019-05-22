package com.guiji.support;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.guiji.cache.ThirdApiUser;
import com.guiji.cache.UserCacheUtil;
import com.guiji.common.Encryptable;
import com.guiji.common.GenericResponse;
import com.guiji.common.SuccessBody;
import com.guiji.exception.ThirdApiException;
import com.guiji.exception.ThirdApiExceptionEnum;
import com.guiji.sign.Md5Utils;
import com.guiji.sign.SignRo;
import com.guiji.utils.BeanUtil;
import com.guiji.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

@Component
public class MyEncryptHttpMessageConverter extends MappingJackson2HttpMessageConverter {

    public final static Logger LOGGER = LoggerFactory.getLogger(MyEncryptHttpMessageConverter.class);

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    UserCacheUtil userCacheUtil;

    public final static ThreadLocal<Long> userIdMap = new ThreadLocal<>();

    @Override
    public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {

        JavaType javaType = getJavaType(type, contextClass);
        if (!Encryptable.class.isAssignableFrom(javaType.getRawClass())) {
            return super.read(type, contextClass, inputMessage);
        }
        Map<String, Object> map = objectMapper.readValue(StreamUtils.copyToByteArray(inputMessage.getBody()), HashMap.class);

        ThirdApiUser user = userCacheUtil.getUserInfoByClientId((String) map.get("clientId"));

        map.put("userId", user.getUserId());
        map.put("orgCode", user.getOrgCode());
        map.remove("clientId");

        userIdMap.set(user.getUserId());

        LOGGER.info("before convert: {}", JsonUtils.bean2Json(map));

        return objectMapper.readValue(JsonUtils.bean2Json(map), javaType.getRawClass());

    }

    @Override
    protected void writeInternal(Object object, Type type, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {

        Class<?> clazz = object.getClass();

        if (Encryptable.class.isAssignableFrom(clazz)) {

            GenericResponse out = new GenericResponse();

            if (clazz.isAssignableFrom(SuccessBody.class)) {
                out.setBody("");
            } else {
                out.setBody(JsonUtils.bean2Json(object));
            }

            LOGGER.info("before sign : {}", JsonUtils.bean2Json(object));

            sign(out);

            super.writeInternal(out, type, outputMessage);

        } else {

            super.writeInternal(object, type, outputMessage);

        }

    }

    private void sign(GenericResponse out) {
        Long userId = userIdMap.get();

        ThirdApiUser thirdApiUser = userCacheUtil.getUserInfoByUserId(userId);

        try {
            out.setCode(0);
            out.setMsg(ThirdApiExceptionEnum.SUCCESS.getMsg());
            out.setSuccess(ThirdApiExceptionEnum.SUCCESS.getSuccess());

            SignRo signRo = new SignRo();
            signRo.setPrivateKey(thirdApiUser.getClientSecret());
            signRo.setParams(BeanUtil.bean2Map(out));
            out.setSign(Md5Utils.sign(signRo));

        } catch (Exception e) {
            throw new ThirdApiException(ThirdApiExceptionEnum.SIGN_ERROR);
        } finally {
            userIdMap.remove();
        }
    }
}
