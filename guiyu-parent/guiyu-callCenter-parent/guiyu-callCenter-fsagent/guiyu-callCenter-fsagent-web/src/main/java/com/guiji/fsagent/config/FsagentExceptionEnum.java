package com.guiji.fsagent.config;

import com.guiji.common.exception.ExceptionEnum;

/**
 * fsagent服务异常的枚举
 */
public enum FsagentExceptionEnum implements ExceptionEnum {
    EXCP_FSAGENT_CODE_BASE64_ERROR("0300003","下载配置文件失败，下载新得xml后转base64过程中异常"),
    EXCP_FSAGENT_TEMP_NOTEXIST("0300004","模板录音文件夹不存在"),
    EXCP_FSAGENT_UPLOAD_ERROR("0300005","录音上传失败"),
    EXCP_FSAGENT_RECORDING_NOTEXIST("0300006","录音上传失败，传的录音文件不存在"),
    EXCP_FSAGENT_RECORDING_NO_LENGTH("0300009","录音上传失败，上传的录音文件长度为0"),
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
    public static FsagentExceptionEnum getMsgByErrorCode(String errorCode){
        for(FsagentExceptionEnum fsagentExceptionEnum : FsagentExceptionEnum.values()) {
            if(fsagentExceptionEnum.getErrorCode().equals(errorCode)){
                return fsagentExceptionEnum;
            }
        }
        return null;
    }

    FsagentExceptionEnum(String errorCode, String msg) {
        this.errorCode = errorCode;
        this.msg = msg;
    }

}
