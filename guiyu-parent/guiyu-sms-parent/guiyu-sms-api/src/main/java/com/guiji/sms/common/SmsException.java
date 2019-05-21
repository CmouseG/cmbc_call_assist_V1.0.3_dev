package com.guiji.sms.common;

/**
 * 自定义异常类
 * @author Sun
 *
 */
public class SmsException extends RuntimeException 
{
	private static final long serialVersionUID = 1L;
	
	private String code;
	
    public SmsException(ExceptionEnum exceptionEnum) {
    	super(exceptionEnum.getMsg());
    	this.code = exceptionEnum.getCode();
    }
    
    public SmsException(String code, String message) {
    	super(message);
        this.code = code;
    }
    
    public SmsException(String message) {
    	super(message);
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
    
}
