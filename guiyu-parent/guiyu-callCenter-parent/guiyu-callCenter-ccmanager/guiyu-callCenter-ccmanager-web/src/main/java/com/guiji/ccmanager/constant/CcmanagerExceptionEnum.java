package com.guiji.ccmanager.constant;

import com.guiji.common.exception.ExceptionEnum;

/**
 * @Auther: 黎阳
 * @Date: 2018/11/22 0022 13:23
 * @Description:
 */
public enum CcmanagerExceptionEnum implements ExceptionEnum {

    EXCP_CCMANAGER_PARAM("0303001","Missing Parameters"),
    EXCP_CCMANAGER_LINE_NOTEXIST("0303002","line not exist"),
    EXCP_CCMANAGER_LINE_RUNNING("0303003","line is running"),
    EXCP_CCMANAGER_TEMP_NOTEXIST ("0303004","temp not exist"),
    EXCP_CCMANAGER_TEMP_DOWNLOADFAIL("0303005","temp download failed"),
    EXCP_CCMANAGER_CODEC("0303006","codec not right"),
    EXCP_CCMANAGER_FSMANAGER_ADDLINE("0303007","新增线路失败，参数错误或线路id重复"),
    EXCP_CCMANAGER_FSMANAGER_EDITLINE("0303008","fsmanageer edit line error"),
    EXCP_CCMANAGER_FSMANAGER_DELETELINE("0303009","fsmanageer delete line error"),
    EXCP_CCMANAGER_CALLOUTSERVER_ERROR("0303010","callouserver null or error");

    //返回码
    private String errorCode;
    //返回信息
    private String msg;

    public String getName(){
        return this.name();
    }

    //根据枚举的code获取msg的方法
    public static CcmanagerExceptionEnum getMsgByErrorCode(String errorCode){
        for(CcmanagerExceptionEnum guiyuNasExceptionEnum : CcmanagerExceptionEnum.values()) {
            if(guiyuNasExceptionEnum.getErrorCode().equals(errorCode)){
                return guiyuNasExceptionEnum;
            }
        }
        return null;
    }

    CcmanagerExceptionEnum(String errorCode, String msg) {
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
