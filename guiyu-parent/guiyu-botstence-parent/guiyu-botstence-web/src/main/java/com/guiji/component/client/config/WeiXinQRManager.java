package com.guiji.component.client.config;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.guiji.botsentence.util.HttpRequestUtils;
import com.guiji.common.exception.CommonException;

@Service
public class WeiXinQRManager {
	private static final Logger logger = LoggerFactory.getLogger(WeiXinQRManager.class);

	@Value("${wechat.appid}")
	private String appid;
	
	@Value("${wechat.secret}")
	private String secret;
	
	/**
	 * @param reqUrl
	 *            基础的url地址
	 * @param params
	 *            查询参数
	 * @return 构建好的url
	 */

	public String httpPostWithJSON(HttpServletRequest request, String url, String json, String id)
			throws Exception {
		String result = null;
		// 将JSON进行UTF-8编码,以便传输中文
		String encoderJson = URLEncoder.encode(json, HTTP.UTF_8);
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
		StringEntity se = new StringEntity(json);
		se.setContentType("application/json");
		se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "UTF-8"));
		httpPost.setEntity(se);
		// httpClient.execute(httpPost);
		HttpResponse response = httpClient.execute(httpPost);
		if (response != null) {
			HttpEntity resEntity = response.getEntity();
			if (resEntity != null) {
				InputStream instreams = resEntity.getContent();
				// ResourceBundle systemConfig =
				// ResourceBundle.getBundle("config/system",
				// Locale.getDefault());
				// String uploadSysUrl =
				// systemConfig.getString("agentImgUrl")+id+"/";
				// File saveFile = new File(uploadSysUrl+id+".jpg");
				// String uploadSysUrl = "D:\\upload" + "/";
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String dateStr = sdf.format(new Date());
				String xdPath = "/upload/" + dateStr;
				String filePath = request.getServletContext().getRealPath(xdPath);// 保存文件路径
				File saveFile = new File(filePath);
				// 判断这个文件（saveFile）是否存在
				if (!saveFile.exists()) {
					// 如果不存在就创建这个文件夹
					saveFile.mkdirs();
				}
				String newHeadImgName = "";// 重新设置要保存头像的文件名
				// 获取当前时间
				Date d = new Date();
				newHeadImgName += "" + d.getTime() + ((int) (Math.random() * 9000 + 1000)) + ".jpg";
				saveToImgByInputStream(instreams, filePath, newHeadImgName);
				result = xdPath + "/" + newHeadImgName;
			}
		}
		return result;
	}

	/*
	 * @param instreams 二进制流
	 * 
	 * @param imgPath 图片的保存路径
	 * 
	 * @param imgName 图片的名称
	 * 
	 * @return 1：保存正常 0：保存失败
	 */
	public static int saveToImgByInputStream(InputStream instreams, String imgPath, String imgName)
			throws FileNotFoundException {

		int stateInt = 1;
		File file = new File(imgPath, imgName);// 可以是任何图片格式.jpg,.png等
		FileOutputStream fos = new FileOutputStream(file);
		if (instreams != null) {
			try {

				byte[] b = new byte[1024];
				int nRead = 0;
				while ((nRead = instreams.read(b)) != -1) {
					fos.write(b, 0, nRead);
				}

			} catch (Exception e) {
				stateInt = 0;
				e.printStackTrace();
			} finally {

				try {
					fos.flush();
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return stateInt;
	}

	public static boolean exists(String imgPath) {
		File saveFile = new File(imgPath);
		if (!saveFile.getParentFile().exists()) {
			return false;
		} else {
			// 如果存在判断这个文件的大小
			if (saveFile.length() > 0) {
				System.out.println("--------------------------------" + saveFile.length());
				return true;
			} else {
				return false;
			}
		}

	}

	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param;
			System.out.println(urlNameString + "........");
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	public static Object httpPostWithJSON2(String url, String json, String id) throws Exception {
		// 将JSON进行UTF-8编码,以便传输中文
		InputStream instreams = null;
		String encoderJson = URLEncoder.encode(json, HTTP.UTF_8);
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
		StringEntity se = new StringEntity(json);
		se.setContentType("application/json");
		se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "UTF-8"));
		httpPost.setEntity(se);
		// httpClient.execute(httpPost);
		HttpResponse response = httpClient.execute(httpPost);
		if (response != null) {
			HttpEntity resEntity = response.getEntity();
			if (resEntity != null) {
				instreams = resEntity.getContent();

			}
		}
		return instreams;
	}

	/**
	 * 生成小程序二维码 -并保存到本地-返回二维码地址
	 * 
	 * @param request
	 * @param access_token
	 * @param path
	 * @param width
	 * @param scene
	 * @return
	 */
	public String createwxaqrcode(HttpServletRequest request, String access_token, String path, String width,
			String scene) {
		String URL = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + access_token;
		// 二维码图片位置
		String downloadUrl = null;
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			Map<String, String> line_color = new HashMap<String, String>();
			line_color.put("r", "0");
			line_color.put("g", "0");
			line_color.put("b", "0");
			map.put("path", path);
			map.put("width", width);
			map.put("scene", scene);
			map.put("auto_color", false);
			map.put("line_color", line_color);
			String json = JSONObject.toJSONString(map);
			downloadUrl = this.httpPostWithJSON(request, URL, json.toString(), scene);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return downloadUrl;
	}

	public String getToken() {
		Map<String, String> jsonParam = new LinkedHashMap<String, String>();
		jsonParam.put("appid", appid);
		jsonParam.put("secret", secret);
		jsonParam.put("grant_type", "client_credential");
		String rt;
		rt = HttpRequestUtils.httpGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+secret);
		com.alibaba.fastjson.JSONObject json = com.alibaba.fastjson.JSONObject.parseObject(rt);
		if (json.getString("access_token") != null || json.getString("access_token") != "") {
			return json.getString("access_token");
		} else {
			return null;
		}

	}

	public InputStream getminiqrQr(String accessToken, String templateId) {
		logger.info("getminiqrQr templateId: "+templateId);
		InputStream inputStream = null;
		
		try {
			URL url = new URL("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + accessToken);
			HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestMethod("POST");// 提交模式
			// conn.setConnectTimeout(10000);//连接超时 单位毫秒
			// conn.setReadTimeout(2000);//读取超时 单位毫秒
			// 发送POST请求必须设置如下两行
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
			// 发送请求参数
			JSONObject paramJson = new JSONObject();
			paramJson.put("scene", templateId);
			//paramJson.put("path", "pages/chat/chat");
			paramJson.put("width", 430);
			paramJson.put("is_hyaline", true);
			paramJson.put("auto_color", true);
			
			printWriter.write(paramJson.toString());
			// flush输出流的缓冲
			printWriter.flush();
			// 开始获取数据
			inputStream = httpURLConnection.getInputStream();

			// 获取当前时间
			//Date d = new Date();
			//newHeadImgName += "" + d.getTime() + ((int) (Math.random() * 9000 + 1000)) + ".jpg";
			
			//saveToImgByInputStream(bis, cfg.getUploadurl()+ "/upload/", newHeadImgName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return inputStream;
	}

	public static void main(String[] args) {
		WeiXinQRManager obj = new WeiXinQRManager();
		//String token = obj.getToken();
		//System.out.println(token);
		
		//obj.getminiqrQr("16_Yt4PD9X_IF8gtFDSBj_-HSGfTa3TagbgdQJld32_yd2QWQI4wGA5BtfkC25dHZYasm29t14lvOzjBdilh7Bvaw-2awigS4JClKimo4nDCGlp71qMHhYwwMiULVMuT9CXZXucMRkZnD51gFmnCCGiAFAVVZ", request);
	}
}
