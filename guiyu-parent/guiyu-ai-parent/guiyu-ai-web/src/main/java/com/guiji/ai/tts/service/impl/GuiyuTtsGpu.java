package com.guiji.ai.tts.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.guiji.ai.dao.entity.TtsResult;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.nas.api.INas;
import com.guiji.nas.vo.SysFileReqVO;
import com.guiji.nas.vo.SysFileRspVO;

/**
 * Created by ty on 2018/11/14.
 */
public class GuiyuTtsGpu extends ITtsServiceProvide {

private static Logger logger = LoggerFactory.getLogger(GuiyuTtsGpu.class);
	
	@Autowired
	INas Inas;
	
	private String ip;
	private String port;

    public GuiyuTtsGpu(String ip, String port) {
    	this.ip = ip;
    	this.port = port;
	}

	@Override
    File transferByChild(String text) {
		CloseableHttpResponse response = null;
		CloseableHttpClient httpClient = null;
		FileOutputStream out = null;
		File file = null;
		try {
			file = new File("*********"); //文件保存路径
			if(!file.exists()){
				file.createNewFile();
			}
			out = new FileOutputStream(file);
			httpClient = HttpClients.createDefault(); 
			HttpPost httpPost = new HttpPost("http://"+ip+":"+port);
			//配置超时时间
			RequestConfig config = RequestConfig.custom()
					.setConnectTimeout(5000)
					.setSocketTimeout(3000)
					.build();
			httpPost.setConfig(config);
			
			//添加请求参数
			JSONObject param= new JSONObject();
		    param.put("text", text); 
		    StringEntity entity = new StringEntity(param.toString(), "UTF-8");
		    entity.setContentType("application/json");
		    httpPost.setEntity(entity);
		    
		    logger.info("请求GPU...");
		    response = httpClient.execute(httpPost); //发送http请求
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity responseEntity = response.getEntity();
				responseEntity.writeTo(out); //写到输出流中
			}
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

	/**
	 * 上传文件服务器
	 */
	@Override
	String uploadToServer(String busiId, File file) {
		
		String audioUrl = null;
		ReturnData<SysFileRspVO> returnData = null;
		SysFileRspVO sysFileRspVO = null;
		FileInputStream fileInputStream = null;
		try {
			SysFileReqVO sysFileReqVO = null;
			sysFileReqVO = new SysFileReqVO();
			sysFileReqVO.setBusiId(busiId);
			sysFileReqVO.setBusiType("上传的影像文件业务类型"); //TODO 上传的影像文件业务类型
			sysFileReqVO.setSysCode("文件上传系统码"); //TODO 文件上传系统码
			sysFileReqVO.setThumbImageFlag("0"); //是否需要生成缩略图,0-无需生成，1-生成，默认不生成缩略图
			
			fileInputStream = new FileInputStream(file);
			MockMultipartFile mockMultipartFile = new MockMultipartFile(file.getName(), file.getName(), 
					ContentType.APPLICATION_SVG_XML.toString(), fileInputStream);
			
			Long userId = 1L;
			logger.info("上传文件服务器");
			returnData = Inas.uploadFile(sysFileReqVO, mockMultipartFile, userId);
			if(returnData != null){
				sysFileRspVO = returnData.getBody();
				if(sysFileRspVO != null)
				audioUrl = sysFileRspVO.getSkUrl();
			}
		} catch (Exception e) {
			logger.error("上传失败！" + e);
			return null;
		} finally {
			IOUtils.closeQuietly(fileInputStream);
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
		ttsResult.setContent(text);
		ttsResult.setCreateTime(new Date());
		ttsResult.setDelFlag("0");
		ttsResult.setModel(model);
		logger.info("添加数据到队列");
		TtsTransferAfterImpl.getInstance().add(ttsResult);
	}
}
