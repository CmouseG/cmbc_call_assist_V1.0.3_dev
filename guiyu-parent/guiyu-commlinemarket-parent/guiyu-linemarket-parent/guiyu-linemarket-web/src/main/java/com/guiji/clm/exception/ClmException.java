package com.guiji.clm.exception;

import com.guiji.common.exception.GuiyuException;

/** 
* @ClassName: ClmException 
* @Description: 线路市场模块异常信息
* @version V1.0  
*/
public class ClmException extends GuiyuException{
	private static final long serialVersionUID = 1L;
	
	public ClmException(){
		
		super();
	}
	
	public ClmException(String msg){
		super(msg);
	}
	
	public ClmException(String errorCode,String msg){
		super(errorCode,msg);
	}
	
	public ClmException(Throwable throwable){
		super(throwable);
	}
	
	public ClmException(String msg,Throwable throwable){
		super(msg, throwable);
	}
	
}
