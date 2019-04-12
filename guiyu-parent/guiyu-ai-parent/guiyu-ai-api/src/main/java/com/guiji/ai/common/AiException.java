package com.guiji.ai.common;

/**
 * 自定义异常类
 * @author Sun
 *
 */
public class AiException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	private String code;
	
	
    public AiException(ExceptionEnum exceptionEnum) {
    	super(exceptionEnum.getMsg());
    	this.code = exceptionEnum.getCode();
    }
    
    public AiException(String code, String message) {
    	super(message);
        this.code = code;
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
    
}
