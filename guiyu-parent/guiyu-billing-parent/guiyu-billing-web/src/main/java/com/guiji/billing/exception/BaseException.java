package com.guiji.billing.exception;

public class BaseException extends RuntimeException {

    /**
     * 消息码
     */
    private String messageCode;
    /**
     * 异常描述
     */
    private String message;

    public BaseException(String messageCode, String message) {
        this.messageCode = messageCode;
        this.message = message;
    }

    public String getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
