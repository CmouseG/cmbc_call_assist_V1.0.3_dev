package com.guiji.cloud.zuul.advice;


import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

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
        if(e instanceof  AccountException){
        	result=Result.error("00010002");
        }else if(e instanceof AuthenticationException){
        	result=Result.error("00010003");
        }else if(e instanceof UnauthorizedException){
        	result=Result.error("00010004");
        }
        return result;
    }

}
