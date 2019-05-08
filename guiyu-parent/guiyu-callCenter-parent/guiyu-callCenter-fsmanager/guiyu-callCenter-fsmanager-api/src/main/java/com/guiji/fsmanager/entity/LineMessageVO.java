package com.guiji.fsmanager.entity;

import lombok.Data;

@Data
public class LineMessageVO {
    private String lineId;
    private String serviceId;
    private String type;

    @Override
    public String toString() {
        return
                "{lineId:'" + lineId + '\'' +
                ", serviceId:'" + serviceId + '\'' +
                ", type:'" + type + '\''+"}"
                ;
    }
}