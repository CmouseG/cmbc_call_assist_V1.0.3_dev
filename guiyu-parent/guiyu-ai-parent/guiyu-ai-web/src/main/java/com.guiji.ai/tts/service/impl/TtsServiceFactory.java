package com.guiji.ai.tts.service.impl;

/**
 * Created by ty on 2018/11/14.
 */
public class TtsServiceFactory {

    public static ITtsServiceProvide getTtsProvide(String module)
    {
        // 获取一个可用的
        return new GuiyuTtsGpu();
    }


}
