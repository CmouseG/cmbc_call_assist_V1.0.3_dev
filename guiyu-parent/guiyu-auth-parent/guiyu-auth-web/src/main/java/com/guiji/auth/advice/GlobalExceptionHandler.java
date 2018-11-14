package com.guiji.auth.advice;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.guiji.auth.exception.CheckConditionException;
import com.guiji.component.result.Result;
import com.guiji.component.result.Result.ReturnData;


@ControllerAdvice
public class GlobalExceptionHandler {
	
	private Logger logger=LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler
    @ResponseBody
    public ReturnData<?> handleException(Exception e){
		logger.error("",e);
		ReturnData<?> result=null;
		if(e instanceof CheckConditionException){
        	result=Result.error(((CheckConditionException)e).getCode());
        }
        return result;
    }

}
