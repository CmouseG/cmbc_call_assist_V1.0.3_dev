package com.guiji.cloud.zuul.filter;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

@Component
public class UserIdZuulFilter extends ZuulFilter{

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
		Object obj=SecurityUtils.getSubject().getSession().getAttribute("userId");
		if(obj!=null){
			String userId=obj.toString();
			String isSuperAdmin=SecurityUtils.getSubject().getSession().getAttribute("isSuperAdmin").toString();
			RequestContext ctx = RequestContext.getCurrentContext();
			ctx.addZuulRequestHeader("userId", userId);
			ctx.addZuulRequestHeader("isSuperAdmin", isSuperAdmin);
		}
		return null;
	}

	@Override
	public String filterType() {
		// TODO Auto-generated method stub
		return "pre";
	}

	@Override
	public int filterOrder() {
		// TODO Auto-generated method stub
		return 0;
	}

}
