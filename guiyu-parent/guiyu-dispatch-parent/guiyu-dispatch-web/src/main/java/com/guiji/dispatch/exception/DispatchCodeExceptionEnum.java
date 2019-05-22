package com.guiji.dispatch.exception;

import com.guiji.common.exception.ExceptionEnum;

public enum DispatchCodeExceptionEnum implements ExceptionEnum {


    IN_DATA_EXCEPTION("2000000001","入参错误"),

    IMPORT_FILE_NULL("2000000002","批量导入计划文件为空"),

    NO_THIS_LINE("2000000003", "无该线路信息"),

    NO_THIS_USER("2000000004", "无该用户信息"),

    BATCH_SAME_LINE_TYPE("2000000005", "同批次线路类型需一致"),

    USER_NO_THIS_TEMPLATE("2000000006", "用户无此话术"),

    THIS_TEMPLATE_HAS_CALL_AGENT("2000000006", "此话术需配置坐席组"),

    NO_THIS_DISPATCH("2000000007", "无此批次信息"),

    DUPLICATE_USER_BATCH_NAME("2000000008", "当前用户下的批次名重复"),
    ;

    private String errorCode;

    private String errorMsg;

    DispatchCodeExceptionEnum(String errorCode, String errorMsg){
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
