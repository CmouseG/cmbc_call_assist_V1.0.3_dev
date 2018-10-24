package com.guiji.utils;

import javax.servlet.ServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Map;

/** 
* @ClassName: HttpHelper 
* @Description: HTTP工具
* @author: weiyunbo
* @date 2018年7月17日 下午6:29:23 
* @version V1.0  
*/
public class HttpHelper {

	/**
	 * 获取URL上GET请求参数
	 * @param request
	 * @return
	 */
	public static String getGetBodyString(ServletRequest request) {
		Map<String, String[]> map =request.getParameterMap();
		if (map.size()>0){
			StringBuffer param=new StringBuffer("");
			for (Map.Entry entry :map.entrySet()){
				param.append("&"+(String)entry.getKey()+"=");
				String[] val=(String[])entry.getValue();
				if (val.length==1){
					param.append(val[0]);
				}else{
					param.append(Arrays.toString(val));
				}
			}
			return param.toString();
		}
		return null;
	}
	
	
	/**
	 * 获取HTTP请求中，post请求输入流信息
	 * @param request
	 * @return
	 */
	public static String getPostBodyString(ServletRequest request) {
	    StringBuilder sb = new StringBuilder();
	    InputStream inputStream = null;
	    BufferedReader reader = null;
	    try {
	        inputStream = request.getInputStream();
	        reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
	        String line = "";
	        while ((line = reader.readLine()) != null) {
	            sb.append(line);
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        if (inputStream != null) {
	            try {
	                inputStream.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	        if (reader != null) {
	            try {
	                reader.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	    return sb.toString();
	}
}
