package com.guiji.cloud.zuul.filter;

import com.google.gson.Gson;
import com.guiji.component.result.Result;
import com.guiji.component.result.Result.ReturnData;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.Properties;

/**
 * 登陆校验
 * @author HP
 *
 */
public class ZuulAuthenticationFilter extends AccessControlFilter{
	
	private static String errorMsg;

	private static String whiteIPs = "127.0.0.1";

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		// IP白名单
		String remoteIP = request.getRemoteAddr();
		if(StringUtils.isNotEmpty(whiteIPs) && whiteIPs.contains(remoteIP)) {
			return true;
		}
		Subject subject = getSubject(request, response);
        boolean flag=subject.isAuthenticated();
        if(!flag){
        	response.setContentType("application/json;charset=UTF-8");
        	response.getWriter().println(getErrorMsg());
        }
        return flag;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		return false;
	}
	
	private String getErrorMsg() throws ClassNotFoundException{
		if(errorMsg==null){
			 ReturnData<Object> obj=Result.error("00010003");
        	 Gson gson=new Gson();
        	 errorMsg=gson.toJson(obj);
		}
		return errorMsg;
	}
}
