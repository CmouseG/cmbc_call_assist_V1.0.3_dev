package com.guiji.cloud.zuul.filter;

import com.google.gson.Gson;
import com.guiji.cloud.zuul.config.JwtConfig;
import com.guiji.cloud.zuul.entity.JwtToken;
import com.guiji.cloud.zuul.white.WhiteIPUtil;
import com.guiji.component.result.Result;
import com.guiji.utils.NetworkUtil;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT核心过滤器配置
 * 所有的请求都会先经过Filter，所以我们继承官方的BasicHttpAuthenticationFilter，并且重写鉴权的方法。
 * 执行流程 preHandle->isAccessAllowed->isLoginAttempt->executeLogin
 */
public class JwtFilter extends BasicHttpAuthenticationFilter {

    private final Logger logger = LoggerFactory.getLogger(JwtFilter.class);
    private static String errorMsg;

    private JwtConfig jwtConfig;

    public JwtFilter(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    /**
     * 判断用户是否想要进行 需要验证的操作
     * 检测header里面是否包含Authorization字段即可
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        String auth = getAuthzHeader(request);
        return auth != null && !auth.equals("");
    }
    /**
     * 此方法调用登陆，验证逻辑
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue){
        // IP白名单
        String whiteIPs = WhiteIPUtil.getIps();
        String remoteIP = null;
        try {
            remoteIP = NetworkUtil.getIpAddress((HttpServletRequest)request);
        } catch (IOException e) {
            logger.error("NetworkUtil.getIpAddress hasIOException "+e);
        }
        if(StringUtils.isNotEmpty(whiteIPs) && whiteIPs.contains(remoteIP)) {
            return true;
        }
        boolean flag =true;
        if (isLoginAttempt(request, response)) {
           try {
               String tok = getAuthzHeader(request);
               JwtToken token = new JwtToken(getAuthzHeader(request));
               Subject subject = getSubject(request, response);
               subject.login(token);

               Long userId = jwtConfig.getUserIdByToken(tok);
               String orgCode = jwtConfig.getOrgCodeByToken(tok);
               Boolean isSuperAdmin = jwtConfig.getSuperAdminByToken(tok);
               Integer isDesensitization = jwtConfig.getIsDesensitizationByToken(tok);

               RequestContext ctx = RequestContext.getCurrentContext();
               ctx.addZuulRequestHeader("userId", String.valueOf(userId));
               ctx.addZuulRequestHeader("orgCode", orgCode);
               ctx.addZuulRequestHeader("isSuperAdmin", isSuperAdmin.toString());
               ctx.addZuulRequestHeader("isDesensitization", isDesensitization.toString());
           }catch (Exception e){
               flag =false;
           }
        }else{
            flag =false;
        }
        if(!flag){
            response.setContentType("application/json;charset=UTF-8");
            try {
                response.getWriter().println(getErrorMsg());
            } catch (IOException e) {
                logger.error("JwtFilter.response.getWriter() hasIOException "+e);
            } catch (ClassNotFoundException e) {
                logger.error("JwtFilter.getErrorMsg ClassNotFoundException "+e);
            }
        }
        return flag;
    }
    /**
     * 提供跨域支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }

    private String getErrorMsg() throws ClassNotFoundException{
        if(errorMsg==null){
            Result.ReturnData<Object> obj= Result.error("00010001");
            Gson gson=new Gson();
            errorMsg=gson.toJson(obj);
        }
        return errorMsg;
    }

    @Override
    protected boolean sendChallenge(ServletRequest request, ServletResponse response) {
        logger.debug("Authentication required: sending 401 Authentication challenge response.");

        HttpServletResponse httpResponse = WebUtils.toHttp(response);
        httpResponse.setStatus(HttpServletResponse.SC_OK);
        String authcHeader = getAuthcScheme() + " realm=\"" + getApplicationName() + "\"";
        httpResponse.setHeader(AUTHENTICATE_HEADER, authcHeader);
        return false;
    }

}

