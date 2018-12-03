package com.guiji.cloud.zuul.exception;

/**
 * Created by ty on 2018/12/3.
 */
public enum ZuulErrorEnum {
    Zuul00010001("00010001","尚未登录,请先登录");

    //返回码
    private String errorCode;
    //返回信息
    private String errorMsg;
    private ZuulErrorEnum(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }
    //根据枚举的code获取msg的方法
    public static String getMsgByCode(String errorCode){
        for(ZuulErrorEnum responseEnum : ZuulErrorEnum.values()) {
            if(responseEnum.getErrorCode().equals(errorCode)){
                return responseEnum.errorMsg;
            }
        }
        return null;
    }
    /**
     * @return the errorCode
     */
    public String getErrorCode() {

        return errorCode;
    }
    /**
     @param errorCode the errorCode to set
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
    /**
     * @return the errorMsg
     */
    public String getErrorMsg() {

        return errorMsg;
    }
    /**
     @param errorMsg the errorMsg to set
     */
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }


}
