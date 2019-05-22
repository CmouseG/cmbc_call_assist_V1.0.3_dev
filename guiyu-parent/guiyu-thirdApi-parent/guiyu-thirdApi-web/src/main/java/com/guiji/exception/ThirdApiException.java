package com.guiji.exception;

/**
 * thirdApi独有异常处理类
 */
public class ThirdApiException extends RuntimeException {
    private static final long serialVersionUID = -8072930252478967506L;

    private Integer success;
    private String errorCode;  //异常对应的返回码
    private String errorMessage;  //异常对应的描述信息
    private String name;

    public ThirdApiException(ExceptionEnum exceptionEnum) {
        this.errorCode = exceptionEnum.getErrorCode();
        this.errorMessage = exceptionEnum.getMsg();
        this.name = exceptionEnum.getName();
        this.success = exceptionEnum.getSuccess();
    }

    public ThirdApiException() {
        super();
    }

    public ThirdApiException(String msg) {
        super(msg);
        this.errorMessage = msg;
    }

    public ThirdApiException(String errorCode, String msg) {
        super(msg);
        this.errorCode = errorCode;
        this.errorMessage = msg;
    }

    public ThirdApiException(Throwable throwable) {
        super(throwable);
    }

    public ThirdApiException(String msg, Throwable throwable) {
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

    public Integer getSuccess() {
        return success;
    }
}
