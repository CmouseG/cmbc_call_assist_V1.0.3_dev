package com.guiji.component.aspect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.guiji.common.exception.GuiyuException;

@ControllerAdvice
public class ExceptionHandle
{
	private final static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);
	
	@ExceptionHandler(value = Exception.class)
	public void exceptionGet(Exception e)
	{
		if(e instanceof GuiyuException){
			GuiyuException ex = (GuiyuException) e;
			logger.error("ErrorCode： " + ex.getErrorCode());
			logger.error("ErrorMessage： " + ex.getErrorMessage());
        }
		
		logger.info("【系统异常】", e);
	}
}
