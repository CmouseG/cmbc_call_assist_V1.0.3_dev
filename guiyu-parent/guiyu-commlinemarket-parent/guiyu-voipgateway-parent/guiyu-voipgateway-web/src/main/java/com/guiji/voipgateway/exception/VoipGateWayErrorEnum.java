/**
 * @Copyright:Copyright (c) 2008 - 2100
 * @Company:guojaing
 */
package com.guiji.voipgateway.exception;

import com.guiji.common.exception.ExceptionEnum;

/**
 *@Description: 异常码定义枚举
 *@Author:weiyunbo
 *@history:
 *@Version:v1.0
 */
public enum VoipGateWayErrorEnum implements ExceptionEnum {

    C00060001("Voip00060001", "该品牌不存在!"),
    ERROR("ERROR", "ERROR");

    //返回码
    private String errorCode;
    //返回信息
    private String msg;

    public String getName() {
        return this.name();
    }

    //根据枚举的code获取msg的方法
    public static VoipGateWayErrorEnum getMsgByErrorCode(String errorCode) {
        for (VoipGateWayErrorEnum voipGateWayErrorEnum : VoipGateWayErrorEnum.values()) {
            if (voipGateWayErrorEnum.getErrorCode().equals(errorCode)) {
                return voipGateWayErrorEnum;
            }
        }
        return null;
    }

    VoipGateWayErrorEnum(String errorCode, String msg) {
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
  
