package com.guiji.cloud.zuul.filter;

import org.apache.shiro.SecurityUtils;
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
		String userId=SecurityUtils.getSubject().getSession().getAttribute("userId").toString();
		RequestContext ctx = RequestContext.getCurrentContext();
		ctx.addZuulRequestHeader("userId", userId);
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
