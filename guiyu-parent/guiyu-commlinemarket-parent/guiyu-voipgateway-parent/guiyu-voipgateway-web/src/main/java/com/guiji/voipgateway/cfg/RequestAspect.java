package com.guiji.voipgateway.cfg;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;

/**
 * @ClassName: RequestAspect
 * @Description: 所有controller请求、返回日志统一打印
 * @date 2018年11月26日 上午10:03:47
 * @version V1.0
 */
@Aspect
@Component
public class RequestAspect {
	private Logger logger = Logger.getLogger(RequestAspect.class);

	@Pointcut("execution(public * com.guiji.*.controller.*.*(..))")
	public void log() {
	}

	/**
	 * 打印请求信息
	 * @param joinPoint
	 */
	@Before("log()")
	public void exBefore(JoinPoint joinPoint) {
		ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = sra.getRequest();
		logger.info("RQEUEST...：service:"+ joinPoint.getSignature().getDeclaringTypeName() + "."+ joinPoint.getSignature().getName()
				    +";  params:" + JSON.toJSONString(joinPoint.getArgs()));
	}

	/**
	 * 执行完毕打印(不需要)
	 * @param result
	 */
//	@After("log()")
//	public void exAfter(JoinPoint joinPoint) {
//		logger.info("service:" + joinPoint.getSignature().getDeclaringTypeName() + "."
//				+ joinPoint.getSignature().getName() + "方法执行完毕！");
//	}

	/**
	 * 返回结果
	 * @param result
	 */
	@AfterReturning(returning = "result", pointcut = "log()")
	public void exAfterReturning(Object result) {
		logger.info("RESPONSE...：" + JSON.toJSONString(result));
	}
}
