package com.guiji.service;

import com.alibaba.fastjson.JSON;
import com.guiji.cache.ThirdApiUser;
import com.guiji.cache.UserCacheUtil;
import com.guiji.exception.ThirdApiException;
import com.guiji.exception.ThirdApiExceptionEnum;
import com.guiji.sign.Md5Utils;
import com.guiji.sign.SignRo;
import groovy.util.logging.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * 验签服务
 */
@Service
@Slf4j
public class VerifyService {

    private final static Logger log = LoggerFactory.getLogger(VerifyService.class);

    @Autowired
    UserCacheUtil userCacheUtil;

    private final static String CLIENT_ID = "clientId";

    public void verify(Map map) {
        String clientId = (String) map.get(CLIENT_ID);

        ThirdApiUser thirdApiUser = userCacheUtil.getUserInfoByClientId(clientId);

        checkSign(map, thirdApiUser);
    }


    /**
     * 签名校验
     *
     * @param map
     */
    private void checkSign(Map<String, Object> map, ThirdApiUser thirdApiUser) {

        /*签名校验*/
        String signature = (String) map.get("sign");
        if (StringUtils.isEmpty(signature)) {
            throw new ThirdApiException(ThirdApiExceptionEnum.VERIFY_SIGN_ERROR);
        }

        SignRo signRo = new SignRo();
        signRo.setParams(map);
        signRo.setPrivateKey(thirdApiUser.getClientSecret());
        signRo.setSignature(signature);

        log.info("待验签信息：{}", JSON.toJSONString(signRo));

        if (!map.containsKey("signType") || !"MD5".equals(map.get("signType"))) {
            throw new ThirdApiException(ThirdApiExceptionEnum.SIGN_TYPE_ERROR);
        }

        boolean verify = Md5Utils.verify(signRo);

        if (!verify) {
            throw new ThirdApiException(ThirdApiExceptionEnum.VERIFY_SIGN_ERROR);
        }
    }

}
