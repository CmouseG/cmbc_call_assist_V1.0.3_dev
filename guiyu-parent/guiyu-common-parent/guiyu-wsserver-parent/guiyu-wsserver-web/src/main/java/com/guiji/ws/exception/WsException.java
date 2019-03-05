package com.guiji.ws.exception;

import com.guiji.common.exception.GuiyuException;

/** 
* @ClassName: WsException 
* @Description: ws模块异常信息
* @version V1.0  
*/
public class WsException extends GuiyuException{
	private static final long serialVersionUID = 1L;
	
	public WsException(){
		
		super();
	}
	
	public WsException(String msg){
		super(msg);
	}
	
	public WsException(String errorCode,String msg){
		super(errorCode,msg);
	}
	
	public WsException(Throwable throwable){
		super(throwable);
	}
	
	public WsException(String msg,Throwable throwable){
		super(msg, throwable);
	}
	
}
