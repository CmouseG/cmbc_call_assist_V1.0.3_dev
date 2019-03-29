package com.guiji.dispatch.exception;

import com.guiji.common.exception.ExceptionEnum;

public enum DispatchCodeExceptionEnum implements ExceptionEnum {


    IN_DATA_EXCEPTION("2000000001","入参错误"),

    IMPORT_FILE_NULL("2000000002","批量导入计划文件为空");
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
