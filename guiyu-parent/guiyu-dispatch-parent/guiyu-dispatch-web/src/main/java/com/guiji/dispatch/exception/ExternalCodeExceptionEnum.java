package com.guiji.dispatch.exception;

import com.guiji.common.exception.ExceptionEnum;

public enum ExternalCodeExceptionEnum implements ExceptionEnum {

    CALL_CENTER_0305012("0305012","呼叫中心模板没有准备好"),

    CALL_CENTER_0305013("0305013","超过呼叫中心承受数量，拒绝呼叫"),

    ;

    private String errorCode;

    private String errorMsg;

    ExternalCodeExceptionEnum(String errorCode, String errorMsg){
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMsg() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
