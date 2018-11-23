package com.guiji.common.model;

/**
 *
 */
public class ResourceNotifyReq {


    /** notifyTypeEnum */
    private ResourceNotifyTypeEnum notifyTypeEnum;

    /** busyKey */
    private String busyKey;

    public ResourceNotifyTypeEnum getNotifyTypeEnum() {
        return notifyTypeEnum;
    }

    public void setNotifyTypeEnum(ResourceNotifyTypeEnum notifyTypeEnum) {
        this.notifyTypeEnum = notifyTypeEnum;
    }

    public String getBusyKey() {
        return busyKey;
    }

    public void setBusyKey(String busyKey) {
        this.busyKey = busyKey;
    }

    @Override
    public String toString() {
        return "ResourceNotifyReq{" +
                "notifyTypeEnum=" + notifyTypeEnum +
                ", busyKey='" + busyKey + '\'' +
                '}';
    }
}
