package com.guiji.common.exception;

public class GuiyuException extends RuntimeException{
	private static final long serialVersionUID = -8072930252478967506L;
	
	private String errorCode ;  //异常对应的返回码
    private String errorMessage;  //异常对应的描述信息
	private String name;

	public GuiyuException(ExceptionEnum exceptionEnum) {
		this.errorCode = exceptionEnum.getErrorCode();
		this.errorMessage = exceptionEnum.getMsg();
		this.name = exceptionEnum.getName();
	}
	public GuiyuException(){
		super();
	}
	
	public GuiyuException(String msg){
		super(msg);
		this.errorMessage = msg;
	}
	public GuiyuException(Throwable throwable){
		super(throwable);
	}
	
	public GuiyuException(String msg, Throwable throwable){
		super(msg, throwable);
	}
	
	public String getErrorCode() {
        return errorCode;
    }
 
    public String getErrorMessage() {
        return errorMessage;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
