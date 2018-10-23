package com.guiji.dispatch.model;

public class CommonResponse {

    //返回码
    private String respCode;

    //返回消息
    private String respMsg;

    //响应内容
    private Object content;

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}
