package com.guiji.component.aspect;

import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.guiji.common.exception.GuiyuException;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.guiyu.sysoperalog.dao.entity.SysUserAction;

@Aspect
@Component
public class ControllerLogAspect
{
	private static final Logger logger = LoggerFactory.getLogger(ControllerLogAspect.class);
	
	SysUserAction SysUserAction = new SysUserAction();
	
	@Autowired
	private RestTemplate restTemplate;

	/**
	 * 切点定义
	 */
	@Pointcut("execution(public * com.guiji.*.controller.*.*(..)) || @annotation(com.guiji.component.aspect.SysOperaLog)")
	public void log()
	{}

	/**
	 * 打印请求信息
	 * @param joinPoint
	 */
	@Before("log()")
	public void doBefore(JoinPoint joinPoint)
	{
		try
		{
			ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
			HttpServletRequest request = sra.getRequest();

			StringBuilder sb = new StringBuilder();
			sb.append(joinPoint.getSignature().getDeclaringTypeName()).append(".").append(joinPoint.getSignature().getName()).append("|") // 类名.方法名
					.append(request.getRequestURI()).append("|") // url
					.append(JSON.toJSONString(joinPoint.getArgs())).append("|") // args
					.append(request.getRemoteHost()); // ip

			logger.debug(sb.toString());
			
			 MethodSignature sign =  (MethodSignature)joinPoint.getSignature();
		     Method method = sign.getMethod();
		     SysOperaLog annotation = method.getAnnotation(SysOperaLog.class);
		     if(annotation == null){
		    	 return;
		     }
		     SysUserAction.setActionName(annotation.actionName());
		     SysUserAction.setUserId(annotation.userId());
		     SysUserAction.setOperateTime(new Date());
		     SysUserAction.setUrl(annotation.url());
		     SysUserAction.setData(annotation.data());
		     restTemplate.postForObject("http://guiyu-cloud-zuul:18061/save", SysUserAction, ReturnData.class);
		     

		} catch (Exception e) {
			
		}

	}

	/**
	 * 返回结果
	 * @param result
	 */
	@AfterReturning(returning = "result", pointcut = "log()")
	public void doAfterReturning(Object result)
	{
		try
		{
			logger.debug("Response...：" + JSON.toJSONString(result));
			
		} catch (Exception e) {
			
		}
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
		
		logger.error("【系统异常】", e);
	}

}
