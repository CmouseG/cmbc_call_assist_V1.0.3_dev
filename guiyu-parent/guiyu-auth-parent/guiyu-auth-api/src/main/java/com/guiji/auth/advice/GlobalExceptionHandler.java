package com.guiji.auth.advice;


import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.guiji.common.result.Result;
import com.guiji.common.result.Result.ReturnData;


@ControllerAdvice
public class GlobalExceptionHandler {
	
	private Logger logger=LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler
    @ResponseBody
    public ReturnData handleException(Exception e){
		logger.error("",e);
		ReturnData result=null;
        if(e instanceof  AccountException){
        	result=Result.error("0001002");
        }else if(e instanceof AuthenticationException){
        	result=Result.error("0001003");
        }else if(e instanceof UnauthorizedException){
        	result=Result.error("0001004");
        }
        return result;
    }

}
