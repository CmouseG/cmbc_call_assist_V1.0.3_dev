package com.guiji.dispatch.enums;

public enum SysDefaultExceptionEnum {

    OTHER_EXCEPTION("1000000001","未知异常"),

    NULL_PARAM_EXCEPTION("1000000002","参数为空"),

    EXEC_FAIL("1000000003","处理失败"),

    SQL_EXCEPTION("1000000004","SQL异常"),

    EXTERNAL_FAIL("1000001000", "调用外部系统失败"),

    NULL_DATA_EXCEPTION("1000000005","数据为空"),

    DEFINE_EXCEPTION("-1","自定义提示信息异常"),
    ;

    private String errorCode;

    private String errorMsg;

    SysDefaultExceptionEnum(String errorCode, String errorMsg){
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public String getErrorCode() {
        return errorCode;
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
