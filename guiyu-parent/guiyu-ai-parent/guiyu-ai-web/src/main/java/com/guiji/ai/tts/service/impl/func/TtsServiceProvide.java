package com.guiji.ai.tts.service.impl.func;

import java.io.File;

/**
 * Created by ty on 2018/11/14.
 */
public abstract class TtsServiceProvide {

	public String transfer(String busiId, String model, String text) {

		// 请求ip:port 返回音频文件
		File file = transferByChild(model, text);
		if(file == null)
		{
			return null;
		}
		
		// 上传文件服务器
		String audioUrl = uploadToServer(busiId, file);
		// 存储数据库和日志
		savaTtsResult(busiId, model, text, audioUrl);

		return audioUrl;

	}

	public abstract File transferByChild(String model, String text);
	
	public abstract String uploadToServer(String busiId, File file);

	public abstract void savaTtsResult(String busiId, String model, String text, String audioUrl);
}
