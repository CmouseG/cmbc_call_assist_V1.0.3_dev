package com.guiji.component.aspect;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.guiji.common.exception.GuiyuException;

@Aspect
@Component
public class ControllerLogAspect
{
	private static final Logger logger = LoggerFactory.getLogger(ControllerLogAspect.class);
	

	@Pointcut("execution(public * com.guiji.*.controller.*.*(..))")
	public void log()
	{
	}

	/**
	 * 打印请求信息
	 * @param joinPoint
	 */
	@Before("log()")
	public void doBefore(JoinPoint joinPoint)
	{
		ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = sra.getRequest();
		
		StringBuilder sb = new StringBuilder();
		sb.append(joinPoint.getSignature().getDeclaringTypeName()).append(".").append(joinPoint.getSignature().getName()).append("|") // 类名.方法名
		  .append(request.getRequestURI()).append("|") // url
		  .append(JSON.toJSONString(joinPoint.getArgs())).append("|") // args
		  .append(request.getRemoteHost()); // ip
		
		logger.info(sb.toString());
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
	
	/**
	 * 处理异常
	 * @param e
	 */
	@AfterThrowing(value = "log()", throwing = "e")
	public void doAfterThrowing(Throwable e)
	{
		if (e instanceof GuiyuException)
		{
			GuiyuException ex = (GuiyuException) e;
			logger.error("ErrorCode： " + ex.getErrorCode(), "ErrorMessage： " + ex.getErrorMessage(), ex);
		}
		
		logger.info("【系统异常】", e);
	}

}
