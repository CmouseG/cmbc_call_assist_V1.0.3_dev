package com.guiji.cloud.zuul.service;

import com.alibaba.fastjson.JSON;
import com.guiji.component.result.Result;
import com.guiji.third.api.VerifyApi;
import com.guiji.utils.RedisUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ApiCheckService {

    /**
     * 随机码长度
     */
    public static final int NONCE_LEN = 6;

    /**
     * 时间戳长度
     */
    public static final int TIMESTAMP_LEN = 13;

    /**
     * clientId长度
     */
    public static final int CLIENT_ID_LEN = 10;


    /**
     * clientId长度
     */
    public static final int SIGN_LEN = 32;


    @Autowired
    VerifyApi verifyApi;

    public String check(Map<String, Object> map) {

        String code = "0";

        boolean flag = true;

        //校验时间戳
        if (!(map.containsKey("timestamp") && StringUtils.isNotEmpty(String.valueOf(map.get("timestamp"))) && String.valueOf(map.get("timestamp")).length() == TIMESTAMP_LEN
                && map.containsKey("clientId") && StringUtils.isNotEmpty(String.valueOf(map.get("clientId"))) && String.valueOf(map.get("clientId")).length() == CLIENT_ID_LEN
                && map.containsKey("signType") && StringUtils.isNotEmpty((String) map.get("signType")) && ("md5".equalsIgnoreCase(String.valueOf(map.get("signType"))))
                && map.containsKey("sign") && StringUtils.isNotEmpty((String) map.get("sign")) && String.valueOf(map.get("sign")).length() == SIGN_LEN
                && map.containsKey("nonce") && StringUtils.isNotEmpty((String) map.get("nonce")) && ((String) map.get("nonce")).length() == NONCE_LEN)) {
            //参数不合法
            code = "1000";
            flag = false;
        }

        if (flag &&  !checkRequestTime(Long.valueOf((String) map.get("timestamp")))) {
            //请求已过期
            code = "1001";
            flag = false;
        }

        if (flag && checkNonce((String) map.get("nonce"), (String) map.get("clientId"))) {
            //重复请求
            code = "1002";
            flag = false;
        }

        if (flag) {

            Result.ReturnData<Boolean> check = verifyApi.check(JSON.toJSONString(map));

            if (flag && (check == null || !check.success || !check.getBody())) {
                //验签失败
                code = "1003";
            }
        }

        return code;
    }

    @Autowired
    RedisUtil redisUtil;

    /**
     * 若查询到clientId+nonce，则认为是重复请求
     *
     * @param nonce
     * @param clientId
     * @return
     */
    private boolean checkNonce(String nonce, String clientId) {

        Object o = redisUtil.get("third_api_nonce_" + nonce + "_" + clientId);

        if (o == null) {
            redisUtil.set("third_api_nonce_" + nonce + "_" + clientId, 1, 600);
            return false;
        } else {
            return true;
        }

    }

    /**
     * @param timestamp
     */
    private boolean checkRequestTime(Long timestamp) {
        /*校验请求时间是否在10分钟内*/
        long timestampDate = timestamp + 1000 * 60 * 10;
        long currDate = System.currentTimeMillis();
        // 请求过期
        if (timestampDate < currDate) {
            return false;
        }
        return true;
    }

}
