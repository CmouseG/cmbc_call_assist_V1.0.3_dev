package com.guiji.fsmanager.entity;

import lombok.Data;

@Data
public class ConfigMessageVO {
    private String serviceId;
    private String module;
    private String key;
    private String value;

    @Override
    public String toString() {
        return "{" +
                "serviceId:'" + serviceId + '\'' +
                ", module:'" + module + '\'' +
                ", key:'" + key + '\'' +
                ", value:'" + value + '\'' +
                '}';
    }
}
