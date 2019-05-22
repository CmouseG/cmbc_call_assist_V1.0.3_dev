package com.guiji.robot.model;

import lombok.Data;

@Data
public class CustTemplateVo {

    //话术模板ID
    private String templateId;
    //话术模板名称
    private String templateName;
    //true需坐席组false不需要
    private Boolean agentFlag;

}
