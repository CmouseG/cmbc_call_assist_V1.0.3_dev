package com.guiji.sms.vo;

import java.util.Date;

import lombok.Data;

@Data
public class SmsTunnelVO
{
	private Integer id;

    private String platformName;

    private String tunnelName;

    private String companyName;

    private String createUser;

    private Date createTime;

    private String updateUser;

    private Date updateTime;

    private String platformConfig;
}
