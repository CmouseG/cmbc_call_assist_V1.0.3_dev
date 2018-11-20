package com.guiji.robot.exception;


/** 
* @ClassName: AiException 
* @Description: AI模块异常信息
* @version V1.0  
*/
public class AiException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public AiException(){
		
		super();
	}
	
	public AiException(String msg){
//		super(msg);
	}
	
	public AiException(String errorCode,String msg){
//		super(msg);
	}
	
	public AiException(Throwable throwable){
//		super(throwable);
	}
	
	public AiException(String msg,Throwable throwable){
//		super(msg, throwable);
	}
	
}
