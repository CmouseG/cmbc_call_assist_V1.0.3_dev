package com.guiji.dispatch.util;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.guiji.dispatch.dao.entity.DispatchLog;
import com.guiji.dispatch.service.OperatorLogService;

public class LogInterceptor implements HandlerInterceptor {

    /**
     * 日志
     */
    private final Logger logger = LoggerFactory.getLogger(LogInterceptor.class);


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    	DispatchLog log = null;
        HandlerMethod hardlerMethod = (HandlerMethod) handler;
        Method method = hardlerMethod.getMethod();
        try {
            //不包含Log注解的不进行日志记录
            if (method.isAnnotationPresent(Log.class)) {
                log = OperatorLogUtil.getLog(request, method);
                OperatorLogService operatorLogService = getService(OperatorLogService.class, request);
                operatorLogService.saveOperatorLog(log);
            }
        } catch (Exception e) {
            logger.error("[记录操作日志时失败]", e.getMessage());
        }
    }


    /**
     * 拦截器中，通过@Autowired注入失效，需要通过以下方式获取service对象
     * @param clazz  对象class类
     * @param request http request
     * @param <T>  对象类别
     * @return 返回一个对象
     */
    private <T> T getService(Class<T> clazz, HttpServletRequest request) {
        BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
        return factory.getBean(clazz);
    }
}
