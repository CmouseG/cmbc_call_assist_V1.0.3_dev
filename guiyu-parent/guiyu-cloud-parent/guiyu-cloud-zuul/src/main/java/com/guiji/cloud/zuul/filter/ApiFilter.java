package com.guiji.cloud.zuul.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.guiji.cloud.zuul.service.ApiCheckService;
import com.guiji.utils.JsonUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.http.ServletInputStreamWrapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * 第三方api filter
 */
@Component
public class ApiFilter extends ZuulFilter {

    private final static Logger log = LoggerFactory.getLogger(ApiFilter.class);

    @Autowired
    private ApiCheckService apiCheckService;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {

        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        if(request.getRequestURI().matches("/v1/thirdApi/.*")) {
            return true;
        }

        return false;
    }

    @Override
    public Object run() {

        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        log.info(String.format("%s >>> %s", request.getMethod(), request.getRequestURL().toString()));
        // 获取请求的输入流
        InputStream in = null;
        String body = "";

        try {
            in = request.getInputStream();
            body = StreamUtils.copyToString(in, Charset.forName("UTF-8"));
        } catch (IOException e) {
            log.error("读取流信息错误: {}", e.getMessage());
        }
        if (StringUtils.isEmpty(body)) {
            log.info("api接收到的信息为空");
        }
        log.info("body:{}", body);

        // 转化成json
        JSONObject json = JSONObject.parseObject(body);
        Map<String, Object> map = json;
        String check = apiCheckService.check(map);

        log.info("check result: {}", check);
        if (!"0".equals(check)) {
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(200);
            ctx.getResponse().setCharacterEncoding("UTF-8");
            ctx.getResponse().setContentType("application/json;charset=UTF-8");
            try {
                ctx.getResponse().getWriter().println(getErrMsg(check));
            } catch (IOException e) {
                log.error("写信息错误：{}", e.getMessage());
            }
            return false;
        } else {
            map.remove("timestamp");
            map.remove("signType");
            map.remove("sign");
            map.remove("nonce");

            byte[] reqBodyBytes = JsonUtils.bean2Json(map).getBytes();

            //删除加密类参数，重写请求内容
            ctx.setRequest(new HttpServletRequestWrapper(request) {
                @Override
                public ServletInputStream getInputStream(){
                    return new ServletInputStreamWrapper(reqBodyBytes);
                }

                @Override
                public int getContentLength() {
                    return reqBodyBytes.length;
                }

                @Override
                public long getContentLengthLong() {
                    return reqBodyBytes.length;
                }
            });
        }
        return null;
    }

    private String getErrMsg(String msg) {
        JSONObject json = new JSONObject();

        json.put("code", "1");
        json.put("msg", msg);
        json.put("success", "1");

        return JSON.toJSONString(json);
    }
}
