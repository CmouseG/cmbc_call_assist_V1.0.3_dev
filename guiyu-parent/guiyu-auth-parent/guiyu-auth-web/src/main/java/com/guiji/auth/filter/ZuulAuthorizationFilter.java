package com.guiji.auth.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.guiji.auth.util.PermissionResolve;
import com.guiji.common.result.Result;
import com.guiji.common.result.Result.ReturnData;

public class ZuulAuthorizationFilter extends AccessControlFilter {
	
	private static String errorMsg;
	
	private PermissionResolve permissionResolve;
	
	public ZuulAuthorizationFilter(PermissionResolve resolve){
		permissionResolve=resolve;
	}
	
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		boolean flag=true;
		Subject subject = getSubject(request, response);
		HttpServletRequest httpRequest=(HttpServletRequest) request;
		String url=httpRequest.getRequestURI();
		String permission=permissionResolve.parse(url);
		if(StringUtils.isEmpty(permission)||!subject.isPermitted(permission)){
			flag=false;
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().println(getErrorMsg());
		}
		return flag;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		return false;
	}

	private String getErrorMsg(){
		if(errorMsg==null){
			 ReturnData<Object> obj=Result.error("00010004");
        	 Gson gson=new Gson();
        	 errorMsg=gson.toJson(obj);
		}
		return errorMsg;
	}

}
