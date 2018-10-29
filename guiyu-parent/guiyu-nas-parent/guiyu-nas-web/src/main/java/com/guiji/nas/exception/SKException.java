package com.guiji.nas.exception;


import com.guiji.common.exception.CommonException;

/**
* 影像系统异常信息
*/
public class SKException extends CommonException {
	private static final long serialVersionUID = 1L;
	
	public SKException(){
		
		super();
	}
	
	public SKException(String msg){
		super(msg);
	}
	
	public SKException(String errorCode,String msg){
		super(msg);
	}
	
	public SKException(Throwable throwable){
		super(throwable);
	}
	
	public SKException(String msg,Throwable throwable){
		super(msg, throwable);
	}
	
}
