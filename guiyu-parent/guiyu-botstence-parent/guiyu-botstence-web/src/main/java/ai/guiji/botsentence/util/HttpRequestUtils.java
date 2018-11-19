package ai.guiji.botsentence.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;

public class HttpRequestUtils {

	private static Logger logger = LoggerFactory.getLogger(HttpRequestUtils.class);    //日志记录
	 
    /**
     * post请求
     * @param url         url地址
     * @return
     * @throws UnsupportedEncodingException 
     */
    public static String httpPost(String url, Map<String,String> paramMap) throws UnsupportedEncodingException{
        //post请求返回结果
        DefaultHttpClient httpClient = new DefaultHttpClient();
        
        
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
        
        
        List<NameValuePair> params=new ArrayList<NameValuePair>();

        if(null != paramMap) {
        	for(String key : paramMap.keySet()) {
        		params.add(new BasicNameValuePair(key, paramMap.get(key)));
        	}
        }
        UrlEncodedFormEntity entity=new UrlEncodedFormEntity(params, "utf-8");

        String parameter = JSONObject.toJSONString(paramMap);

        Gson gson = new Gson();
        parameter = gson.toJson(paramMap);
        
        StringEntity stringEntity = new StringEntity(parameter,"UTF-8");
        stringEntity.setContentType("text/json");
        stringEntity.setContentEncoding(new BasicHeader("Content-Type", "application/json"));
        
        httpPost.setEntity(stringEntity);
        
        String str = "";
        try {
            HttpResponse response = httpClient.execute(httpPost);
            url = URLDecoder.decode(url, "UTF-8");
            
            /*CloseableHttpResponse response = httpClient.execute(method);
            HttpEntity httpEntity = response.getEntity();
            String result22 = EntityUtils.toString(httpEntity, "UTF-8");*/

            /*if (httpEntity != null) {    
                InputStream instreams = httpEntity.getContent();    
                String aaa = convertStreamToString(instreams);  
                method.abort();    
                return str;  
            }*/
            
            /**请求发送成功，并得到响应**/
            if (response.getStatusLine().getStatusCode() == 200) {
                try {
                    /**读取服务器返回过来的json字符串数据**/
                    str = EntityUtils.toString(response.getEntity());
                } catch (Exception e) {
                    logger.error("post请求提交失败:" + url, e);
                }
            }
        } catch (IOException e) {
            logger.error("post请求提交失败:" + url, e);
        }
        return str;
    }
 
 
    /**
     * 发送get请求
     * @param url    路径
     * @return
     */
    public static String httpGet(String url){
        //get请求返回结果
    	 String strResult = null;
        try {
            DefaultHttpClient client = new DefaultHttpClient();
            //发送get请求
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);
 
            /**请求发送成功，并得到响应**/
            if (response.getStatusLine().getStatusCode() == org.apache.http.HttpStatus.SC_OK) {
                /**读取服务器返回过来的json字符串数据**/
                  strResult = EntityUtils.toString(response.getEntity());
            } else {
                logger.error("get请求提交失败:" + url);
            }
        } catch (IOException e) {
            logger.error("get请求提交失败:" + url, e);
        }
        return strResult;
    }

    
    public static String convertStreamToString(InputStream is) {      
        StringBuilder sb1 = new StringBuilder();      
        byte[] bytes = new byte[4096];    
        int size = 0;    
          
        try {      
            while ((size = is.read(bytes)) > 0) {    
                String str = new String(bytes, 0, size, "UTF-8");    
                sb1.append(str);    
            }    
        } catch (IOException e) {      
            e.printStackTrace();      
        } finally {      
            try {      
                is.close();      
            } catch (IOException e) {      
               e.printStackTrace();      
            }      
        }      
        return sb1.toString();      
    }  
    
}
