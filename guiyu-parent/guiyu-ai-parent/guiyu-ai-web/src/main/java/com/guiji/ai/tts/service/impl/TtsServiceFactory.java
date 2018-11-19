package com.guiji.ai.tts.service.impl;

/**
 * Created by ty on 2018/11/14.
 */
public class TtsServiceFactory {

    public static ITtsServiceProvide getTtsProvide(String module)
    {
    	String ip = null;
    	String port = null;
    	
    	// TODO 添加从redis中获取一个活的TTS Server
    	
        // 获取一个可用的
        return new GuiyuTtsGpu(ip, port);
    }


}
