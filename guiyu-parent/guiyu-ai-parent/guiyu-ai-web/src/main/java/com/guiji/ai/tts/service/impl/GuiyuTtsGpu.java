package com.guiji.ai.tts.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.guiji.ai.dao.entity.TtsResult;
import com.guiji.ai.tts.constants.AiConstants;
import com.guiji.common.model.SysFileReqVO;
import com.guiji.utils.RedisUtil;

/**
 * Created by ty on 2018/11/14.
 */
@Component
public class GuiyuTtsGpu extends ITtsServiceProvide {
	private static Logger logger = LoggerFactory.getLogger(GuiyuTtsGpu.class);
	
	private String ip;
	private String port;
	
	@Autowired
    private RedisUtil redisUtil;

	@Override
	File transferByChild(String model, String text) {
		CloseableHttpResponse response = null;
		CloseableHttpClient httpClient = null;
		FileOutputStream out = null;
		File file = null;
		try {
			file = new File("E:\\file\\redio.wav"); // 文件保存路径
			if (!file.exists()) {
				file.createNewFile();
			}
			out = new FileOutputStream(file);
			httpClient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost("http://" + ip + ":" + port + "/synthesize");
			// 配置超时时间
			RequestConfig config = RequestConfig.custom().setConnectTimeout(5000).setSocketTimeout(3000).build();
			httpPost.setConfig(config);

			// 添加请求参数
			JSONObject param = new JSONObject();
			param.put("text", text);
			StringEntity entity = new StringEntity(param.toString(), "UTF-8");
			entity.setContentType("application/json");
			httpPost.setEntity(entity);

			logger.info("请求GPU...");
			response = httpClient.execute(httpPost); // 发送http请求
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity responseEntity = response.getEntity();
				responseEntity.writeTo(out); // 写到输出流中
			}
			// 释放GPU
//			releaseGpu(model, ip, port);
		} catch (Exception e) {
			logger.error("请求失败！" + e);
			return null;
		} finally {
			IOUtils.closeQuietly(out);
			IOUtils.closeQuietly(httpClient);
			IOUtils.closeQuietly(response);
		}
		return file;
	}

	// 释放GPU
	private void releaseGpu(String model, String ip, String port) {
		//从不可用list中移除
		List<GuiyuTtsGpu> unavaliableGpuList = (List<GuiyuTtsGpu>) redisUtil.get(AiConstants.GUIYUTTS + model + AiConstants.UNAVALIABLE);
		int i = 0;
		for(GuiyuTtsGpu gpu : unavaliableGpuList){
			if(ip.equals(gpu.getIp()) && port.equals(gpu.getPort())){
				i = unavaliableGpuList.indexOf(gpu);
			}
		}
		unavaliableGpuList.remove(i);
		redisUtil.lSet(AiConstants.GUIYUTTS + model + AiConstants.UNAVALIABLE, unavaliableGpuList);
		//添加到可用list中
		List<GuiyuTtsGpu> avaliableGpuList = (List<GuiyuTtsGpu>) redisUtil.get(AiConstants.GUIYUTTS + model + AiConstants.AVALIABLE);
		GuiyuTtsGpu gpu = new GuiyuTtsGpu(ip, port);
		avaliableGpuList.add(gpu);
		redisUtil.lSet(AiConstants.GUIYUTTS + model + AiConstants.AVALIABLE, avaliableGpuList);
	}

	/**
	 * 上传文件服务器
	 */
	@Override
	String uploadToServer(String busiId, File file) {
		String audioUrl = null;
		try {
			SysFileReqVO sysFileReqVO = new SysFileReqVO();
			sysFileReqVO.setBusiId(busiId);
			sysFileReqVO.setBusiType(AiConstants.BUSITYPE); //上传的影像文件业务类型
			sysFileReqVO.setSysCode(AiConstants.SYSCODE); //文件上传系统码
			sysFileReqVO.setThumbImageFlag("0"); // 是否需要生成缩略图,0-无需生成，1-生成，默认不生成缩略图
			//调用本地工具-上传文件到NAS服务器
//			SysFileRspVO sysFileRsp = new NasUtil().uploadNas(sysFileReqVO, file);
//			if(sysFileRsp != null) {
//				audioUrl = sysFileRsp.getSkUrl();
//			}
			audioUrl = "www.baidu.com";
			file.delete(); //删除本地文件
		} catch (Exception e) {
			logger.error(file.getName() + "上传失败！", e);
			return null;
		}
		return audioUrl;
	}

	/**
	 * 保存数据库
	 */
	@Override
	void savaToDB(String busiId, String model, String text, String audioUrl) {
		TtsResult ttsResult = new TtsResult();
		ttsResult.setAudioUrl(audioUrl);
		ttsResult.setBusId(busiId);
		ttsResult.setContent(text); //待转换文本内容
		ttsResult.setCreateTime(new Date());
		ttsResult.setDelFlag("0"); //删除标识：0-正常，1-删除
		ttsResult.setModel(model);
		TtsTransferAfterImpl.getInstance().add(ttsResult);
	}

	public GuiyuTtsGpu(String ip, String port) {
		this.ip = ip;
		this.port = port;
	}

	public GuiyuTtsGpu() {
		super();
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
	
	@Autowired
	public void setRedisUtil(RedisUtil redisUtil) {
		this.redisUtil = redisUtil;
	}
}
