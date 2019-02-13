package com.guiji.cloud.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@Component
public class UserIdZuulFilter extends ZuulFilter {
	Logger logger = LoggerFactory.getLogger(UserIdZuulFilter.class);

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
		Subject subject = SecurityUtils.getSubject();
		Session session= subject.getSession();
		Object userIdObj = subject.getSession().getAttribute("userId");
		Object orgCode = session.getAttribute("orgCode");
		System.out.println("****************"+session.getId());
		System.out.println("****************"+userIdObj);
		Object isSuperAdminObj = SecurityUtils.getSubject().getSession().getAttribute("isSuperAdmin");
		Object isDesensitizationObj = SecurityUtils.getSubject().getSession().getAttribute("isDesensitization");
		try {
			String userId=userIdObj.toString();
			String isSuperAdmin = isSuperAdminObj.toString();
			String isDesensitization = isDesensitizationObj.toString();
			ctx.addZuulRequestHeader("userId", userId);
			ctx.addZuulRequestHeader("orgCode", orgCode.toString());
			ctx.addZuulRequestHeader("isSuperAdmin", isSuperAdmin);
			ctx.addZuulRequestHeader("isDesensitization", isDesensitization);
		} catch (NullPointerException e) {
			logger.error("userIdZuulFilter:" + e.getMessage());
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
