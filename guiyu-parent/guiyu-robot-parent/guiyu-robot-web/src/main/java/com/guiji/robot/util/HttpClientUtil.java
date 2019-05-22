package com.guiji.robot.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/** 
* @ClassName: HttpClientUtil 
* @Description: Http工具类
* @author: weiyunbo
* @date 2018年8月29日 下午4:04:15 
* @version V1.0  
*/
public class HttpClientUtil {
	private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
	
	private static CloseableHttpClient httpClient;

    static {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(100);
        cm.setDefaultMaxPerRoute(50);
        httpClient = HttpClients.custom().setConnectionManager(cm).build();
    }
	
	/**
	 * GET - 拼url方式
	 * @param url
	 * @param param
	 * @return
	 */
	public static String doGet(String url, Map<String, String> param) {
		// 创建Httpclient对象
		CloseableHttpClient httpclient = null;
		String resultString = "";
		CloseableHttpResponse response = null;
		try {
			httpclient = HttpClients.createDefault();
			// 创建uri
			URIBuilder builder = new URIBuilder(url);
			if (param != null) {
				for (String key : param.keySet()) {
					builder.addParameter(key, param.get(key));
				}
			}
			URI uri = builder.build();
			// 创建http GET请求
			HttpGet httpGet = new HttpGet(uri);
			// 执行请求
			response = httpclient.execute(httpGet);
			// 判断返回状态是否为200
			if (response.getStatusLine().getStatusCode() == 200) {
				resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
			}
		} catch (Exception e) {
			logger.error("调用接口异常！",e);
		} finally {
			IOUtils.closeQuietly(response);
			IOUtils.closeQuietly(httpclient);
		}
		return resultString;
	}
 
	public static String doGet(String url) {
		return doGet(url, null);
	}
 
	/**
	 * POST-拼URL方式
	 * @param url
	 * @param param
	 * @return
	 */
	public static String doPost(String url, Map<String, String> param) {
		// 创建Httpclient对象
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		String resultString = "";
		try {
			httpClient = HttpClients.createDefault();
			// 创建Http Post请求
			HttpPost httpPost = new HttpPost(url);
			// 创建参数列表
			if (param != null) {
				List<NameValuePair> paramList = new ArrayList<>();
				for (String key : param.keySet()) {
					paramList.add(new BasicNameValuePair(key, param.get(key)));
				}
				// 模拟表单
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList,"utf-8");
				httpPost.setEntity(entity);
			}
			// 执行http请求
			response = httpClient.execute(httpPost);
			resultString = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (Exception e) {
			logger.error("调用接口异常！",e);
		} finally {
			IOUtils.closeQuietly(response);
			IOUtils.closeQuietly(httpClient);
		}
 
		return resultString;
	}
 
	public static String doPost(String url) {
		return doPost(url, null);
	}
	
	/**
	 * POST-JSON方式
	 * @param url
	 * @param json
	 * @return
	 */
	public static String doPostJson(String url, String json) {
		// 创建Httpclient对象
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		String resultString = "";
		try {
			httpClient = HttpClients.createDefault();
			// 创建Http Post请求
			HttpPost httpPost = new HttpPost(url);
			// 创建请求内容
			StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
			httpPost.setEntity(entity);
			// 执行http请求
			response = httpClient.execute(httpPost);
			resultString = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (Exception e) {
			logger.error("调用接口异常！",e);
		} finally {
			IOUtils.closeQuietly(response);
			IOUtils.closeQuietly(httpClient);
		}
		return resultString;
	}
	
	
	/**
	 * POST JSON请求，可以设置header
	 * @param url
	 * @param jsonString
	 * @param headerMap
	 * @return
	 */
	public static String doPostJsonHeader(String url, String jsonString,Map<String,String> headerMap) {
        CloseableHttpResponse response = null;
        BufferedReader in = null;
        String result = "";
        try {
            HttpPost httpPost = new HttpPost(url);
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(30000).setConnectionRequestTimeout(30000).setSocketTimeout(30000).build();
            httpPost.setConfig(requestConfig);
            httpPost.setConfig(requestConfig);
            httpPost.addHeader("Content-type", "application/json; charset=utf-8");
            httpPost.setHeader("Accept", "application/json");
            if(headerMap != null && !headerMap.isEmpty()) {
    			Iterator headerIterator = headerMap.entrySet().iterator();          //循环增加header
    			while(headerIterator.hasNext()){  
    			    Entry<String,String> elem = (Entry<String, String>) headerIterator.next();  
    			    httpPost.addHeader(elem.getKey(), elem.getValue()); //设置header
    			}
    		}
            httpPost.setEntity(new StringEntity(jsonString, Charset.forName("UTF-8")));
            response = httpClient.execute(httpPost);
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
            in.close();
            result = sb.toString();
        } catch (IOException e) {
        	logger.error("IO error: {}", e.getMessage());
        } finally {
            try {
                if (null != response) {
                    response.close();
                }
            } catch (IOException e) {
				logger.error("close io error: {}", e.getMessage());
            }
        }
        return result;
    }
	
	
//	public static String doPostJsonHeader(String urlPath, String json, Map<String,String> headerMap) throws Exception {
//        String body = "";
//        URL url = new URL(urlPath);
//        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//        urlConnection.setDoOutput(true);
//        urlConnection.setRequestProperty("content-type", "application/json");
//		if(headerMap != null && !headerMap.isEmpty()) {
//			Iterator headerIterator = headerMap.entrySet().iterator();          //循环增加header
//			while(headerIterator.hasNext()){  
//			    Entry<String,String> elem = (Entry<String, String>) headerIterator.next();  
//			    urlConnection.setRequestProperty(elem.getKey(), elem.getValue()); //设置header
//			}
//		}
//        try( OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());) {
//            out.write(json);
//            out.flush();
//            InputStream inputStream = urlConnection.getInputStream();
//            String encoding = urlConnection.getContentEncoding();
//            body = IOUtils.toString(inputStream, encoding);
//            if (urlConnection.getResponseCode() != 200 && urlConnection.getResponseCode() != 201) {
//                throw new IOException() ;
//            }
//            return body;
//        } catch (IOException e) {
//           
//        }
//        return body;
//    }
	
	
	
	/**
	 * POST-以流的方式提交
	 * form表单方式提交
	 * 比如某些场景需要在url后挂大批量数据，使用url会报request超长，可以使用流的方式进行处理
	 * @param sendUrl
	 * @param backEncodType
	 * @return
	 */
	public String doPostStream(String sendUrl, String sendParam,String backEncodType) {
		StringBuffer receive = new StringBuffer();
		BufferedReader rd = null;
		try {
			if (backEncodType == null || backEncodType.equals("")) {
				backEncodType = "UTF-8";
			}
			URL url = new URL(sendUrl);
			HttpURLConnection URLConn = (HttpURLConnection) url.openConnection();
			URLConn.setDoOutput(true);
			URLConn.setDoInput(true);
			((HttpURLConnection) URLConn).setRequestMethod("POST");
			URLConn.setUseCaches(false);
			URLConn.setAllowUserInteraction(true);
			HttpURLConnection.setFollowRedirects(true);
			URLConn.setInstanceFollowRedirects(true);

			URLConn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			URLConn.setRequestProperty("Content-Length", String
					.valueOf(sendParam.getBytes().length));
			DataOutputStream dos = new DataOutputStream(URLConn.getOutputStream());
			dos.writeBytes(sendParam);
			rd = new BufferedReader(new InputStreamReader(
					URLConn.getInputStream(), backEncodType));
			String line;
			while ((line = rd.readLine()) != null) {
				receive.append(line).append("\r\n");
			}
			rd.close();
		} catch (java.io.IOException e) {
			receive.append("访问产生了异常-->").append(e.getMessage());
			logger.error("IO error: {}", e.getMessage());
		} finally {
			if (rd != null) {
				try {
					rd.close();
				} catch (IOException ex) {
					logger.error("close io error: {}", ex.getMessage());
				}
				rd = null;
			}
		}
		return receive.toString();
	}

	/**
	 * GET-以流的方式提交
	 * 比如某些场景需要在url后挂大批量数据，使用url会报request超长，可以使用流的方式进行处理
	 * @param sendUrl
	 * @param backEncodType
	 * @return
	 */
	public String doGetStrem(String sendUrl, String backEncodType) {
		StringBuffer receive = new StringBuffer();
		BufferedReader in = null;
		try {
			if (backEncodType == null || backEncodType.equals("")) {
				backEncodType = "UTF-8";
			}
			URL url = new URL(sendUrl);
			HttpURLConnection URLConn = (HttpURLConnection) url
					.openConnection();
			URLConn.setDoInput(true);
			URLConn.setDoOutput(true);
			URLConn.connect();
			URLConn.getOutputStream().flush();
			in = new BufferedReader(new InputStreamReader(URLConn.getInputStream(), backEncodType));
			String line;
			while ((line = in.readLine()) != null) {
				receive.append(line).append("\r\n");
			}
		} catch (IOException e) {
			receive.append("访问产生了异常-->").append(e.getMessage());
			logger.error("IO error: {}", e.getMessage());
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (java.io.IOException ex) {
					logger.error("close stream error: {}", ex.getMessage());
				}
				in = null;
			}
		}
		return receive.toString();
	}
	
	
	
	public static void main(String[] args) throws InterruptedException {
//		String url = "http://192.168.1.78:18041/remote/checkParams";
		String url = "http://192.168.1.78:18041/remote/checkParams";
		long beginTime = System.currentTimeMillis();
		for(int m=0;m<600;m++) {
			String jsonBegin = "{\r\n" + 
					"	\"needResourceInit\":true,\r\n" + 
					"	\"checkers\":[";
			String jsonEnd = "	]\r\n" + 
					"}";
			String json = "";
			for(int i=0;i<1;i++) {
				String jsonStep = "{\r\n" + 
						"			\"seqid\":\""+m+"\",\r\n" + 
						"			\"templateId\":\"ttscs_46188_en\",\r\n" + 
						"			\"params\":\"你好我知的"+m+"打发啊a的|1"+m+"1.41\"\r\n" + 
						"		},";
				json = json + jsonStep;
			}
			json = json.substring(0,json.length()-1);
			json = jsonBegin + json + jsonEnd;
			HttpClientUtil.doPostJson(url, json);
			System.out.println("第"+m+"次调用");
			Thread.sleep(1000);
		}
		long endTime = System.currentTimeMillis();
		System.out.println("============全部结束===========耗时"+(endTime-beginTime)+"秒");
	}
}
