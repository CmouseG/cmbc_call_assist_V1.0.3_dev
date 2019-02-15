package com.guiji.ai.tts.platform;

import java.io.File;

import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.util.IOUtils;
import com.guiji.ai.tts.TtsService;
import com.guiji.ai.util.FileUtil;
import com.guiji.ai.util.HttpClientUtil;
import com.guiji.ai.vo.AsynPostReqVO;
import com.guiji.ai.vo.HttpVO;
import com.guiji.ai.vo.SynPostReqVO;

public class Guiji implements TtsService
{
	private String ttsUrl;
	private String filePath;
	
	public Guiji(String ttsUrl, String filePath)
	{
		this.ttsUrl = ttsUrl;
		this.filePath = filePath;
	}

	/**
	 * 同步请求
	 */
	@Override
	public File synPost(SynPostReqVO postVO) throws Exception
	{
		File file = null;
		HttpVO httpVo = HttpClientUtil.post(ttsUrl+"/synPost", postVO);
		file = FileUtil.writeToFile(httpVo.getHttpEntity(),filePath);
		closeHttp(httpVo);
		return file;
	}

	/**
	 * 异步请求
	 */
	@Override
	public String asynPost(AsynPostReqVO ttsReq) throws Exception
	{
		HttpVO httpVo = HttpClientUtil.post(ttsUrl+"/asynPost", ttsReq);
		String result = EntityUtils.toString(httpVo.getHttpEntity(), "utf-8");
		closeHttp(httpVo);
		return result;
	}
	
	/**
	 * 关闭连接
	 */
	private void closeHttp(HttpVO httpVo)
	{
		IOUtils.close(httpVo.getHttpResponse());
		IOUtils.close(httpVo.getHttpClient());
	}
}
