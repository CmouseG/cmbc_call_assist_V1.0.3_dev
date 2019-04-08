package com.guiji.robot.model;

import lombok.Data;

@Data
public class TemplateInfo {

    //话术模板：1、默认1版本；2-飞龙版本
    private Integer version;

    private Integer num;

    private String templateId;
}
