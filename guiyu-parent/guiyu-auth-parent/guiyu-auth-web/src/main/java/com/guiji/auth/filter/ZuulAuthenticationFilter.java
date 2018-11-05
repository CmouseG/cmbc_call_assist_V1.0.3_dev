package com.guiji.auth.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;

import com.google.gson.Gson;
import com.guiji.component.result.Result;
import com.guiji.component.result.Result.ReturnData;

/**
 * 登陆校验
 * @author HP
 *
 */
public class ZuulAuthenticationFilter extends AccessControlFilter{
	
	private static String errorMsg;

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
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
