package com.guiji.web.config;

import com.google.common.base.Strings;
import com.guiji.callcenter.dao.AgentMapper;
import com.guiji.callcenter.dao.entity.Agent;
import com.guiji.callcenter.dao.entity.AgentExample;
import com.guiji.entity.CustomHttpHeader;
import com.guiji.entity.CustomSessionVar;
import com.guiji.manager.AuthManager;
import com.guiji.service.AgentService;
import com.guiji.web.request.CrmUserVO;
import com.guiji.web.response.ApiResponse;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 用于拦截所有指定的Http请求，并进行鉴权
 * Created by wchi on 2017/4/18.
 */
public class TokenAuthInterceptor extends HandlerInterceptorAdapter {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(TokenAuthInterceptor.class);

    @Autowired
    AgentMapper agentMapper;
    @Autowired
    AgentService agentService;
    @Autowired
    AuthManager authManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String requestUrl = request.getRequestURL().toString();
        String userId = request.getHeader(CustomHttpHeader.HTTP_HEADER_USERID);
        String token = request.getHeader(CustomHttpHeader.HTTP_HEADER_TOKEN);
        log.info("接收到新的Request请求, method[{}], url[{}],token[{}]", request.getMethod(), requestUrl, token);

        ApiResponse apiResp = new ApiResponse();
        //判断header中的token是否为空，否则返回错误
        if (StringUtils.isBlank(token)) {
            log.info("请求的header中token为空，直接报错返回");
            apiResp.setResult(false);
            apiResp.setMsg("null token in header");
            outputResult(response, apiResp);
            return false;
        }
        AgentExample example = new AgentExample();
        example.createCriteria().andCrmLoginIdEqualTo(token);
        List<Agent> agentList = agentMapper.selectByExample(example);
        if (agentList == null || agentList.size() == 0) {
            //用于排除不需要拦截的接口
            if (requestUrl.contains("prelogin")) {
                if(Strings.isNullOrEmpty(userId)){
                    log.warn("获取用户信息接口中没有userId，报错返回");
                    apiResp.setResult(false);
                    apiResp.setMsg("null userId in header");
                    outputResult(response, apiResp);
                    return false;
                }

                CrmUserVO crmUserVO = authManager.getUser(userId);
                Agent user = agentService.initUser(crmUserVO);
                request.getSession().setAttribute(CustomSessionVar.LOGIN_USER, user);
                request.getSession().setAttribute(CustomSessionVar.CRM_USERID, userId);
                return true;
            } else {
                log.info("请求失败，因用户[{}]不存在", token);
                apiResp.setResult(false);
                apiResp.setMsg("no agent find by token in header");
                outputResult(response, apiResp);
                return false;
            }
        }else{
            request.getSession().setAttribute(CustomSessionVar.LOGIN_USER, agentList.get(0));
            if(!StringUtils.isBlank(userId)){
                request.getSession().setAttribute(CustomSessionVar.CRM_USERID, userId);
            }
        }
        log.info("请求经历重重考验过关！");
        return true;
    }

    /**
     * 向客户端输出返回结果
     *
     * @param response
     * @param apiResponse
     */
    private void outputResult(HttpServletResponse response, ApiResponse apiResponse) {
        Gson gson = new Gson();
        String rtMsg = gson.toJson(apiResponse);
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        try {
            log.info("请求返回结果[{}]", apiResponse);
            response.getWriter().write(rtMsg);
        } catch (IOException e) {
            log.warn("在输出拦截器返回结果时出现异常", e);
            apiResponse.setResult(false);
            try {
                response.getWriter().write(gson.toJson(apiResponse));
            } catch (IOException e1) {
                log.warn("在返回服务器错误时出现异常", e1);
            }
        }
    }
}
