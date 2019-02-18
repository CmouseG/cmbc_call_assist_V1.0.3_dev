package com.guiji.notice.constant;

import com.guiji.common.exception.ExceptionEnum;

/**
 * @Auther: 黎阳
 * @Date: 2018/11/22 0022 13:23
 * @Description:
 */
public enum NoticeExceptionEnum implements ExceptionEnum {

    EXCP_NOTICE_PARAM("1303003","修改意向标签失败，请稍后重试");


    //返回码
    private String errorCode;
    //返回信息
    private String msg;

    public String getName(){
        return this.name();
    }

    //根据枚举的code获取msg的方法
    public static NoticeExceptionEnum getMsgByErrorCode(String errorCode){
        for(NoticeExceptionEnum guiyuNasExceptionEnum : NoticeExceptionEnum.values()) {
            if(guiyuNasExceptionEnum.getErrorCode().equals(errorCode)){
                return guiyuNasExceptionEnum;
            }
        }
        return null;
    }

    NoticeExceptionEnum(String errorCode, String msg) {
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
