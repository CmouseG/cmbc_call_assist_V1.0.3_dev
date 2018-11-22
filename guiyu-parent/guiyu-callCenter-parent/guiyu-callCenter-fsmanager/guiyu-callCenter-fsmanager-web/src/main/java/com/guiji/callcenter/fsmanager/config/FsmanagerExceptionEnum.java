package com.guiji.callcenter.fsmanager.config;

import com.guiji.common.exception.ExceptionEnum;

/**
 * fsagent服务异常的枚举
 */
public enum FsmanagerExceptionEnum implements ExceptionEnum {

    EXCP_FSMANAGER_LINE_REPEAT("0301003","创建线路失败，线路名称重复"),
    EXCP_FSAGENT_UPLOAD_ERROR("0300005","录音上传失败"),
    EXCP_FSAGENT_FSMANAGER_LINEXMLINFOS("0300007","从fsmanager服务获取线路信息失败"),
    EXCP_FSAGENT_TTS_DOWNLOAD("0300008","从机器人中心获取TTS录音URL失败");
    //返回码
    private String errorCode;
    //返回信息
    private String msg;
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
    @Override
    public String getName() {
        return this.name();
    }
    //根据枚举的code获取msg的方法
    public static FsmanagerExceptionEnum getMsgByErrorCode(String errorCode){
        for(FsmanagerExceptionEnum fsagentExceptionEnum : FsmanagerExceptionEnum.values()) {
            if(fsagentExceptionEnum.getErrorCode().equals(errorCode)){
                return fsagentExceptionEnum;
            }
        }
        return null;
    }

    FsmanagerExceptionEnum(String errorCode, String msg) {
        this.errorCode = errorCode;
        this.msg = msg;
    }

}
