package com.guiji.callcenter.fsmanager.config;

import com.guiji.common.exception.ExceptionEnum;

/**
 * fsmanager服务异常的枚举
 */
public enum FsmanagerExceptionEnum implements ExceptionEnum {

    EXCP_FSMANAGER_LINE_REPEAT("0301003","创建线路失败，线路名称重复"),
    EXCP_FSMANAGER_NONE_FREESWITCH("0301002","申请freeswitch资源失败，空闲且正常的freeswitch为空"),
    EXCP_FSMANAGER_FSAGENT_NO_RECORD("0301004","模板不存在"),
    EXCP_FSMANAGER_SIMAGENT_DOWN("0301005","simagent服务宕了");
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
