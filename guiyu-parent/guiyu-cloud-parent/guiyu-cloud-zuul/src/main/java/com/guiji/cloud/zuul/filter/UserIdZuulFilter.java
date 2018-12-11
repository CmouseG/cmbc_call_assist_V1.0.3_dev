package com.guiji.cloud.zuul.filter;

import com.guiji.cloud.zuul.exception.ZuulErrorEnum;
import com.guiji.cloud.zuul.exception.ZuulException;
import com.guiji.common.exception.GuiyuException;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Component;

import com.guiji.cloud.zuul.util.IpUtil;
import com.guiji.cloud.zuul.white.WhiteIPUtil;
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
//		boolean isWiteIpFlag = false;
		RequestContext ctx = RequestContext.getCurrentContext();
		// IP白名单
//		String whiteIPs = WhiteIPUtil.getIps();
//		String remoteIP = IpUtil.getIpAddress(ctx.getRequest());
//		if(StringUtils.isNotEmpty(whiteIPs) && whiteIPs.contains(remoteIP)) {
//			isWiteIpFlag = true;
//		}
		Object userIdObj = SecurityUtils.getSubject().getSession().getAttribute("userId");
		Object isSuperAdminObj = SecurityUtils.getSubject().getSession().getAttribute("isSuperAdmin");
		try {
			String userId=userIdObj.toString();
			String isSuperAdmin = isSuperAdminObj.toString();
			ctx.addZuulRequestHeader("userId", userId);
			ctx.addZuulRequestHeader("isSuperAdmin", isSuperAdmin);
		} catch (NullPointerException e) {
			//处理下一些特殊不需要user的场景
//			if(!isWiteIpFlag) {
//				throw new ZuulException(ZuulErrorEnum.Zuul00010001.getErrorCode(),ZuulErrorEnum.Zuul00010001.getErrorMsg());
//			}
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
