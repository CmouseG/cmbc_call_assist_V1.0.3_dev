package com.guiji.ai.tts.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.guiji.ai.dao.TtsResultMapper;
import com.guiji.ai.dao.entity.TtsResult;
import com.guiji.ai.tts.constants.AiConstants;
import com.guiji.common.model.SysFileReqVO;
import com.guiji.common.model.SysFileRspVO;
import com.guiji.utils.NasUtil;
import com.guiji.utils.RedisUtil;

/**
 * Created by ty on 2018/11/14.
 */
@Component
public class GuiyuTtsGpu extends ITtsServiceProvide {
	private static Logger logger = LoggerFactory.getLogger(GuiyuTtsGpu.class);
	private static final int ConnectTimeout = 5000;
	private static final int SocketTimeout = 5000;
	private static final int MaxTotal = 200;
	private static final int MaxPerRoute = 40;
	private static final int MaxRoute = 100;
	
	private String ip;
	private String port;
	
	@Value("${filePath}")
	private String filePath;
	
	@Autowired
    private RedisUtil redisUtil;
	@Autowired
	TtsResultMapper ttsResultMapper;

	@Override
	File transferByChild(String model, String text) {
		PoolingHttpClientConnectionManager cm = null;
		CloseableHttpResponse response = null;
		CloseableHttpClient httpClient = null;
		FileOutputStream out = null;
		File file = null;
		try {
			File directory = new File(filePath);
			if (!directory.exists()) directory.mkdirs();
			file = new File(filePath + System.currentTimeMillis() + ".wav"); //文件保存路径
			if (!file.exists()) file.createNewFile();
			out = new FileOutputStream(file);
			
			ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
			LayeredConnectionSocketFactory sslsf = SSLConnectionSocketFactory.getSocketFactory();
			Registry<ConnectionSocketFactory> registry = RegistryBuilder
					.<ConnectionSocketFactory> create()
					.register("http", plainsf)
					.register("https", sslsf)
					.build();
			// 连接池管理对象（负责管理HttpClient连接池）
			cm = new PoolingHttpClientConnectionManager(registry);
			// 最大连接数
			cm.setMaxTotal(MaxTotal);
			// 每个路由基础的连接
			cm.setDefaultMaxPerRoute(MaxPerRoute);
			HttpHost httpHost = new HttpHost(ip, Integer.parseInt(port));
			// 目标主机的最大连接数
			cm.setMaxPerRoute(new HttpRoute(httpHost), MaxRoute);
			
			httpClient = HttpClients.custom().setConnectionManager(cm).build();
			HttpPost httpPost = new HttpPost("http://" + ip + ":" + port + "/synthesize");
			// 配置超时时间
			RequestConfig config = RequestConfig.custom().setConnectTimeout(ConnectTimeout).setSocketTimeout(SocketTimeout).build();
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
			releaseGpu(model, ip, port);
		} catch (Exception e) {
			logger.error("请求GPU失败！" + e);
			e.printStackTrace();
			return null;
		} finally {
			IOUtils.closeQuietly(out);
			IOUtils.closeQuietly(response);
			IOUtils.closeQuietly(httpClient);
			IOUtils.closeQuietly(cm);
		}
		return file;
	}

	// 释放GPU
	private void releaseGpu(String model, String ip, String port) {
		GuiyuTtsGpu gpu = new GuiyuTtsGpu(ip, port);
		//从不可用list中移除
		redisUtil.lRemove(AiConstants.GUIYUTTS + model + AiConstants.UNAVALIABLE, 1, gpu);
		//添加到可用list中
		redisUtil.lSet(AiConstants.GUIYUTTS + model + AiConstants.AVALIABLE, gpu);
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
			logger.info(file.getName() + "上传文件到NAS服务器...");
			SysFileRspVO sysFileRsp = new NasUtil().uploadNas(sysFileReqVO, file);
			if(sysFileRsp != null) {
				audioUrl = sysFileRsp.getSkUrl();
			}
			file.delete(); //删除本地文件
		} catch (Exception e) {
			logger.error(file.getName() + "上传失败！");
			e.printStackTrace();
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
		TtsTransferAfterImpl.getInstance().add(ttsResult, ttsResultMapper);
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
}
