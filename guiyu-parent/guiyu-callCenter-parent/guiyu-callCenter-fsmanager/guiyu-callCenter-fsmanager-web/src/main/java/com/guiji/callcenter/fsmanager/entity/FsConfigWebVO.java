package com.guiji.callcenter.fsmanager.entity;

import lombok.Data;

@Data
public class FsConfigWebVO {
    private int id;
    private String roleId;
    private String module;
    private String busKey;
    private String value;

}
