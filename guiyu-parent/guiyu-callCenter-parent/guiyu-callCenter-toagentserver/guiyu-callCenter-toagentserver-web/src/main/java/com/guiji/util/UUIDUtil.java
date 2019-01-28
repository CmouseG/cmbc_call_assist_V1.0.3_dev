package com.guiji.util;

import java.util.UUID;

/**
 * Created by zyq on 2017/4/18.
 */
public class UUIDUtil {
    /**
     * 获取生成的uuid
     * @return
     */
    public static String uuid(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}
