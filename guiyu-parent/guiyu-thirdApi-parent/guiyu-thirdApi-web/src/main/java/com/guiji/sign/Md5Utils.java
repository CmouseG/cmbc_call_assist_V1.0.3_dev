package com.guiji.sign;

import org.springframework.util.DigestUtils;

import java.util.*;

/**
 * md5工具类
 */
public class Md5Utils {

    /**
     * 验签
     *
     * @param signRo
     * @return
     */
    public static boolean verify(SignRo signRo) {

        String signature = signRo.getSignature();

        String newSignature = sign(signRo);

        return newSignature.equals(signature.toUpperCase());
    }

    /**
     * 签名
     *
     * @param signRo
     * @return
     */
    public static String sign(SignRo signRo) {
        return buildRequestMysign(signRo.getParams(), signRo.getPrivateKey());
    }

    /**
     * 除去数组中的空值和签名参数
     *
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map<String, String> paraFilter(Map<String, String> sArray) {

        Map<String, String> result = new HashMap<String, String>();

        if (sArray == null || sArray.size() <= 0) {
            return result;
        }

        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("")
                    || key.equalsIgnoreCase("sign")
                    || key.equalsIgnoreCase("signType")) {
                continue;
            }
            result.put(key, value);
        }

        return result;
    }


    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     *
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params) {

        params = paraFilter(params);

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);

            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }

        return prestr;
    }

    /**
     * 生成签名结果
     *
     * @param sPara 要签名的数组
     * @return 签名结果字符串
     */
    public static String buildRequestMysign(Map<String, Object> sPara, String privateKey) {

        Map<String, String> map = new HashMap<>();

        sPara.forEach((k, v) -> {


            map.put(k, v.toString());

        });

        String prestr = "";

        if (sPara != null) {
            prestr = createLinkString(map); //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        }

        String mysign = "";


        //body={"lineList":"2^外呼^1"}&code=0&msg=处理成功&success=0abcdefg
        mysign = DigestUtils.md5DigestAsHex((prestr + privateKey).getBytes()).toUpperCase();

        return mysign;
    }

}
