package com.guiji.config;

import com.guiji.common.exception.ExceptionEnum;

public enum ToagentserverException implements ExceptionEnum {

    EXCP_TOAGENT_NOT_OWNER("0307005","修改坐席失败，不是管理员且改的不是自己"),
    EXCP_TOAGENT_ANSWERTYPE_NONEMOBILE("0307006","手机号码未填写，不可以设置为手机接听"),
    EXCP_TOAGENT_QUEUE_ISIN("0307007","不能创建同名坐席组"),
    EXCP_TOAGENT__NONE_CRMUSER("0307008","CRM中该用户不存在"),
    EXCP_TOAGENT_NOT_AGENTANDADMIN("0307009","非管理员角色，且不是坐席"),
    EXCP_TOAGENT_QUEUE_NO_LINE("0307011","坐席所在的坐席组没有配置线路");
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
    public static ToagentserverException getMsgByErrorCode(String errorCode){
        for(ToagentserverException toagentserverExceptionEnum : ToagentserverException.values()) {
            if(toagentserverExceptionEnum.getErrorCode().equals(errorCode)){
                return toagentserverExceptionEnum;
            }
        }
        return null;
    }

    ToagentserverException(String errorCode, String msg) {
        this.errorCode = errorCode;
        this.msg = msg;
    }

}

