package com.guiji.ai.tts.service.impl;

import java.io.OutputStream;

/**
 * Created by ty on 2018/11/14.
 */
public abstract class ITtsServiceProvide {

    public  String transfer(String text){
        OutputStream out = transferByChild(text);

        // 本地存储

        // 上传文件服务器

        // 存储数据库和日志

        return  null;


    }

   abstract OutputStream transferByChild(String text);
}
