package com.guiji.component.client.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class JsonObjectArgResolverHandler implements HandlerMethodArgumentResolver {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Override
	public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer arg1,
			NativeWebRequest nativeWebRequest, WebDataBinderFactory arg3) throws Exception {
		try {
			JSONObject para = getRequestInfo(nativeWebRequest, methodParameter);
			Class<?> type = methodParameter.getParameterType();
			String name = methodParameter.getParameterName();
			if("java.lang.String".equals(type.getName())) {
				return para.getString(name);
			}
			if (null != para && para.containsKey(name)) {
				return JSON.parseObject(para.getString(name), type);
			}
		} catch (Exception e) {
			logger.error("参数赋值异常",e);
		}
		return null;
	}

	@Override
	public boolean supportsParameter(MethodParameter arg0) {
		// TODO Auto-generated method stub
		//return true;
		return arg0.hasParameterAnnotation(JsonObject.class);
	}
	
	 private JSONObject getRequestInfo(NativeWebRequest webRequest, MethodParameter methodParameter) throws IOException {
	        JSONObject para = new JSONObject();
	        HttpServletRequest httpServletRequest =
	            (HttpServletRequest) webRequest.getNativeRequest(HttpServletRequest.class);
	        String method = httpServletRequest.getMethod();
	        if (!method.equals("GET") && !method.equals("DELETE")) {
	 
	            if (null != httpServletRequest.getAttribute("para")) {
	                try {
	                    para = JSON.parseObject(httpServletRequest.getAttribute("para").toString());
	                } catch (Exception e) {
	                }
	            } else {
	                StringBuilder buffer = new StringBuilder();
	                BufferedReader reader = httpServletRequest.getReader();
	                String line;
	                while ((line = reader.readLine()) != null) {
	                    buffer.append(line);
	                }
	                String name = methodParameter.getParameterName();
	                
	                
	                httpServletRequest.setAttribute("para", "{\""+name+"\"" + ":" + buffer.toString() + "}");
	                try {
	                    para = JSON.parseObject("{\""+name+"\"" + ":" + buffer.toString() + "}");
	                } catch (Exception e) {
	                }
	            }
	        } else {
	            Map<String, String[]> parameterMap = webRequest.getParameterMap();
	            for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
	                String key = entry.getKey();
	                String values = StringUtils.join(entry.getValue());
	                para.put(key, values);
	            }
	        }
	        return para;
	    }
}
