package com.guiji.wechat.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.HashMap;

@ApiModel(value = "SendMsgReqVO", description = "发消息请求对象")
public class SendMsgReqVO implements Serializable {

    private static final long serialVersionUID = -3849430970880024867L;

    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "微信用户openID")
    private String openID;

    @ApiModelProperty(value = "模板ID")
    private String templateId;

    @ApiModelProperty(value = "模板跳转链接（海外帐号没有跳转能力）")
    private String url;

    @ApiModelProperty(value = "所需跳转到的小程序appid")
    private String appId;

    @ApiModelProperty(value = "所需跳转到小程序的具体页面路径")
    private String pagePath;

    @ApiModelProperty(value = "模板数据")
    public HashMap<String, SendMsgReqVO.Item> data;

    public SendMsgReqVO() {
        this.data = new HashMap<>();
    }

    public String getUserId() {
        return userId;
    }

    public String getOpenID() {
        return openID;
    }

    public String getTemplateId() {
        return templateId;
    }

    public String getUrl() {
        return url;
    }

    public String getAppId() {
        return appId;
    }

    public String getPagePath() {
        return pagePath;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setOpenID(String openID) {
        this.openID = openID;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setPagePath(String pagePath) {
        this.pagePath = pagePath;
    }

    public HashMap<String, Item> getData() {
        return data;
    }

    public void setData(HashMap<String, Item> data) {
        this.data = data;
    }

    public void addData(String key, Object value) {
        addData(key, value, null);
    }

    public void addData(String key, Object value, String color) {
        data.put(key, new Item(value, color));
    }

    public static class Item {
        private Object value;
        private String color;

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public Item() {
        }

        public Item(Object value, String color) {
            this.value = value;
            this.color = color;
        }

        @Override
        public String toString() {
            return "{" +
                    "value=" + value +
                    ", color='" + color + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "SendMsgReqVO{" +
                "userId='" + userId + '\'' +
                ", openID='" + openID + '\'' +
                ", templateId='" + templateId + '\'' +
                ", url='" + url + '\'' +
                ", appId='" + appId + '\'' +
                ", pagePath='" + pagePath + '\'' +
                ", data=" + data.toString() +
                '}';
    }
}
