package com.guiji.cloud.zuul.filter;

import com.google.gson.Gson;
import com.guiji.auth.util.PermissionResolve;
import com.guiji.component.result.Result;
import com.guiji.component.result.Result.ReturnData;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class ZuulAuthorizationFilter extends AccessControlFilter {
	
	private static String errorMsg;

	private static String whiteIPs = "127.0.0.1";
	
	private PermissionResolve permissionResolve2;
	
	public ZuulAuthorizationFilter(PermissionResolve resolve){
		permissionResolve2 =resolve;
	}
	
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		boolean flag=true;
		// IP白名单
		String remoteIP = request.getRemoteAddr();
		if(StringUtils.isNotEmpty(whiteIPs)  && whiteIPs.contains(remoteIP)) {
			return flag;
		}
		Subject subject = getSubject(request, response);
		HttpServletRequest httpRequest=(HttpServletRequest) request;
		String url=httpRequest.getRequestURI();
		String permission= permissionResolve2.parse(url);
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

	@Autowired(required = true)
	public  void setWhiteIPs(@Qualifier("getWhiteIPs") String whiteIPs) {
		this.whiteIPs = whiteIPs;
	}

}
