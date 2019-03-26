package com.guiji.component.jurisdiction;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.guiji.common.exception.GuiyuException;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.utils.LocalCacheUtil;

/**
 * 校验权限切面
 */
@Aspect
@Component
public class CheckJurisdictionAspect
{	
	@Autowired
	private RestTemplate restTemplate;

	/**
	 * 切点定义
	 */
	@Pointcut("@annotation(com.guiji.component.jurisdiction.Jurisdiction)")
	public void check()
	{}
	
	
	@Before("check()")
	public void doBefore(JoinPoint joinPoint)
	{
		MethodSignature sign = (MethodSignature) joinPoint.getSignature();
		Jurisdiction annotation = sign.getMethod().getAnnotation(Jurisdiction.class);
		if (annotation == null) {
			return; //没有添加此注解，直接返回，不做校验
		}
		String value = annotation.value(); //注解配置值
		List<String> urls = Arrays.asList(value.split(",")); //url集合
		
		ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = sra.getRequest();
		String userId = request.getHeader("userId");
		
		List<String> urlList = LocalCacheUtil.getT("Key_Jurisdiction_"+userId);
		if(urlList == null) {
			//根据用户id查询其拥有的权限
			ReturnData<List<String>> result = restTemplate.getForObject("http://guiyu-cloud-zuul:18000/auth/user/queryButtonByUser?userId="+userId, ReturnData.class);
			urlList = result.getBody();
			if(CollectionUtils.isEmpty(urlList)){
				throw new GuiyuException("00010404","您没有此权限!!!");
			}
			LocalCacheUtil.set("Key_Jurisdiction_"+userId, urlList, LocalCacheUtil.TEN_MIN);
		}
		
		boolean flag = false;
		for(String url : urls){
			if(urlList.contains(url)) {
				flag = true;
				break;
			}
		}
		if(!flag)
		{
			throw new GuiyuException("00010404","您没有此权限!!!");
		}
		
	}
}
