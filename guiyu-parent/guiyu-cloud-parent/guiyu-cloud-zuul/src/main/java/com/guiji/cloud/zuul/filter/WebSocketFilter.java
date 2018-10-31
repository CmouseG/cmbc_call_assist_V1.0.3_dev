/** 
 *@Copyright:Copyright (c) 2008 - 2100 
 *@Company:guojaing
 */  
package com.guiji.cloud.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import javax.servlet.http.HttpServletRequest;

/** 
 *@Description: websocket过滤器
 *@Author:weiyunbo
 *@date:2018年7月5日 下午1:37:14
 *@history:
 *@Version:v1.0 
 */
public class WebSocketFilter extends ZuulFilter{
	@Override
    public String filterType() {
        return "pre";
    }
    @Override
    public int filterOrder() {
        return 1;
    }
    @Override
    public boolean shouldFilter() {
        return true;
    }
    @Override
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String upgradeHeader = request.getHeader("Upgrade");
        if (null == upgradeHeader) {
            upgradeHeader = request.getHeader("upgrade");
        }
        if (null != upgradeHeader && "websocket".equalsIgnoreCase(upgradeHeader)) {
            context.addZuulRequestHeader("connection", "Upgrade");
        }
        return null;
    }
}
  
