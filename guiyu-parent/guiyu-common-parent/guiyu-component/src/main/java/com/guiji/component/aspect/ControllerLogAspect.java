package com.guiji.component.aspect;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;

@Aspect
@Component
public class ControllerLogAspect
{
	private static final Logger logger = LoggerFactory.getLogger(ControllerLogAspect.class);

	@Autowired
	ExceptionHandle exceptionHandle;

	@Pointcut("execution(public * com.guiji.*.web.*.*(..))")
	public void log()
	{
	}

	/**
	 * 打印请求信息
	 * 
	 * @param joinPoint
	 */
	@Before("log()")
	public void doBefore(JoinPoint joinPoint)
	{
		ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = sra.getRequest();
		logger.info("Request_url：" + request.getRequestURI());
		logger.info("Request_ip：" + request.getRemoteHost());
		logger.info("Request_method：" + request.getMethod());
		logger.info("Request_classMethod：" 
				+ joinPoint.getSignature().getDeclaringTypeName() + "."
				+ joinPoint.getSignature().getName());
		logger.info("Request_args：" + JSON.toJSONString(joinPoint.getArgs()));
	}

	/**
	 * 返回结果
	 * @param result
	 */
	@AfterReturning(returning = "result", pointcut = "log()")
	public void doAfterReturning(Object result)
	{
		logger.info("Response...：" + JSON.toJSONString(result));
	}

}
