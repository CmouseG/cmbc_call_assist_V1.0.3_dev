package com.guiji.voipgateway.model;

/**
 * @Classname PortStatusVo
 * @Description TODO
 * @Date 2019/5/13 15:43
 * @Created by qinghua
 */
public enum PortStatusEnum {

    IDLE(1, "空闲"),

    BUSY(2, "忙碌"),

    CLOSED(3, "关机"),

    ERROR_CONN(4, "通信失败"),

    INIT(5, "初始化"),

    NOT_REGIST(6, "未注册"),

    EMPTY(7, "空的"),

    NO_SIM(8, "无卡"),

    OTHER(9, "其他")
    ;


    /**
     * 0空闲1忙碌2空的3关机4通信失败5初始化
     */
    private Integer status;

    /**
     * 状态信息描述
     */
    private String statusMsg;


    private PortStatusEnum(Integer status, String statusMsg) {
        this.status = status;
        this.statusMsg = statusMsg;
    }

    public Integer getStatus() {
        return status;
    }

    public String getStatusMsg() {
        return statusMsg;
    }
}
