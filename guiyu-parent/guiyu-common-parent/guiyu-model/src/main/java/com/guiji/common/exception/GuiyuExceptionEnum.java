package com.guiji.common.exception;

/**
 * Created by ty on 2018/11/1.
 */
public enum GuiyuExceptionEnum implements ExceptionEnum {
    /**
     * 公共异常
     */
    EXCP_COMMON_USERNAME_OR_PASSWORD_ERR("00000001","用户名密码错误"),



    EXCP_COMMON_DOWNLOAD_URL_ERR("00000101","下载URL流失败");

    public String getName() {
        return this.name();
    }

    //返回码
    private String errorCode;
    //返回信息
    private String msg;

    //根据枚举的code获取msg的方法
    public static GuiyuExceptionEnum getMsgByErrorCode(String errorCode){
        for(GuiyuExceptionEnum guiyuExceptionEnum : GuiyuExceptionEnum.values()) {
            if(guiyuExceptionEnum.getErrorCode().equals(errorCode)){
                return guiyuExceptionEnum;
            }
        }
        return null;
    }

    GuiyuExceptionEnum(String errorCode, String msg) {
        this.errorCode = errorCode;
        this.msg = msg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
