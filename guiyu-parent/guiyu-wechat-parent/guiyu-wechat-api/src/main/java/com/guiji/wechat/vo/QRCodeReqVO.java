package com.guiji.wechat.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "QRCodeReqVO对象", description = "二维码请求参数对象")
public class QRCodeReqVO implements Serializable {

    private static final long serialVersionUID = 6324136394910494629L;

    @ApiModelProperty(value = "expireSeconds,二维码有效时间，以秒为单位,默认为10分钟")
    private long expireSeconds = 600L;

    @ApiModelProperty(value = "actionName * 二维码类型，\n" +
            "     * QR_SCENE为临时的整型参数值\n" +
            "     * QR_STR_SCENE为临时的字符串参数值\n" +
            "     * QR_LIMIT_SCENE为永久的整型参数值\n" +
            "     * QR_LIMIT_STR_SCENE为永久的字符串参数值")
    private String actionName = "QR_STR_SCENE";

    /**
     * 回调参数
     */
    @ApiModelProperty(value = "callbackParameter,二维码有效时间，以秒为单位,默认为10分钟")
    private String callbackParameter;

    public long getExpireSeconds() {
        return expireSeconds;
    }

    public void setExpireSeconds(long expireSeconds) {
        this.expireSeconds = expireSeconds;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getCallbackParameter() {
        return callbackParameter;
    }

    public void setCallbackParameter(String callbackParameter) {
        this.callbackParameter = callbackParameter;
    }
}




