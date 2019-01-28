package com.guiji.botsentence.vo;

import lombok.Data;

import java.util.Date;

@Data
public class BotSentenceIntentVO {
    private Long id;

    private String name;
    
    private String industry;

    private String templateId;

    private String processId;

    private Date crtTime;

    private String crtUser;

    private Date lstUpdateTime;

    private String lstUpdateUser;

    private Integer forSelect;

    private String domainName;

    private Long oldId;

    private String keywords;
    
    private Long referenceId;
    
    private String intentDomain;
}
