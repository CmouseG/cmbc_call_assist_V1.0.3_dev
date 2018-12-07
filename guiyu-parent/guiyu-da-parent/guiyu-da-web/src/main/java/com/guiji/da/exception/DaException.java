package com.guiji.da.exception;

import com.guiji.common.exception.GuiyuException;

/** 
* @ClassName: DaException 
* @Description: 量化分析模块异常信息
* @version V1.0  
*/
public class DaException extends GuiyuException{
	private static final long serialVersionUID = 1L;
	
	public DaException(){
		
		super();
	}
	
	public DaException(String msg){
		super(msg);
	}
	
	public DaException(String errorCode,String msg){
		super(errorCode,msg);
	}
	
	public DaException(Throwable throwable){
		super(throwable);
	}
	
	public DaException(String msg,Throwable throwable){
		super(msg, throwable);
	}
	
}
