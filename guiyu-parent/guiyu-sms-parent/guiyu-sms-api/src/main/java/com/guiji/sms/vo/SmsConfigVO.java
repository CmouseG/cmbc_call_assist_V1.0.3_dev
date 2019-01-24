package com.guiji.sms.vo;

import java.util.Date;

import lombok.Data;

@Data
public class SmsConfigVO
{
	private Integer id;

    private String tunnelName;
    
    private String templateId;

    private String templateName;

    private String intentionTag;

    private Integer smsTemplateId;

    private Integer auditingStatus;

    private Integer runStatus;

    private String companyName;

    private String createUser;

    private Date createTime;

    private String smsContent;
}
