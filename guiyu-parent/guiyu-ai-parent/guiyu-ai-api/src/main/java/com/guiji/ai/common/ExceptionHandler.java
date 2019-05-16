package com.guiji.ai.common;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.support.spring.FastJsonJsonView;

/**
 * 异常处理类
 * @author Sun
 */
@Component
public class ExceptionHandler implements HandlerExceptionResolver
{
	@Override
	public ModelAndView resolveException(HttpServletRequest request, 
										 HttpServletResponse response, 
										 Object handler, 
										 Exception exception)
	{
		ModelAndView modelAndView = new ModelAndView();
		FastJsonJsonView fastJsonJsonView = new FastJsonJsonView();
		if(exception != null)
		{
			Map<String, Object> attributesMap = new HashMap<String, Object>();
			if(exception instanceof AiException)
			{
				AiException ex = (AiException) exception;
				attributesMap.put("code", ex.getCode());
				attributesMap.put("msg", ex.getMessage());
			} else {
				attributesMap.put("code", "999999"); //未知异常码
				attributesMap.put("msg", exception.getMessage());
			}
			fastJsonJsonView.setAttributesMap(attributesMap);
			modelAndView.setView(fastJsonJsonView);
		}
		return modelAndView;
	}

}
