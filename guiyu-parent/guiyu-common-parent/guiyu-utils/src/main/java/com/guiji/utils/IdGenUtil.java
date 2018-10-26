package com.guiji.utils;

import java.util.UUID;

/**
 * Created by ty on 2018/10/25.
 */
public class IdGenUtil {
    /**
     * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
