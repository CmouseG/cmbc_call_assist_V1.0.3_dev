package com.guiji.ai.tts.service.impl;

import java.io.File;

/**
 * Created by ty on 2018/11/14.
 */
public abstract class ITtsServiceProvide {

	public String transfer(String busiId, String model, String text) {

		// 请求ip:port 返回音频文件
		File file = transferByChild(model, text);
		// 上传文件服务器
		String audioUrl = uploadToServer(busiId, file);
		// 存储数据库和日志
//		savaToDB(busiId, model, text, audioUrl);

		return audioUrl;

	}

	abstract File transferByChild(String model, String text);
	
	abstract String uploadToServer(String busiId, File file);

	abstract void savaToDB(String busiId, String model, String text, String audioUrl);
}
