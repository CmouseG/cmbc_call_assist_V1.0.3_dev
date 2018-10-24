package com.guiji.common.exception;

public class CommonException extends RuntimeException{
	private static final long serialVersionUID = -8072930252478967506L;
	
	private String errorCode ;  //异常对应的返回码
    private String errorMessage;  //异常对应的描述信息
	
	public CommonException(){
		super();
	}
	
	public CommonException(String msg){
		super(msg);
		this.errorMessage = msg;
	}
	
	public CommonException(String errorCode, String msg){
		super(msg);
        this.errorCode = errorCode;
        this.errorMessage = msg;
	}
	
	public CommonException(Throwable throwable){
		super(throwable);
	}
	
	public CommonException(String msg, Throwable throwable){
		super(msg, throwable);
	}
	
	public String getErrorCode() {
        return errorCode;
    }
 
    public String getErrorMessage() {
        return errorMessage;
    }
}
