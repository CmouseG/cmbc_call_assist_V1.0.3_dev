package com.guiji.wxapi.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CallRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String phone;
    private String remarks;
    private String label;
    private Integer calltime;
    private Date starttime;
    private String voicefile;
}
