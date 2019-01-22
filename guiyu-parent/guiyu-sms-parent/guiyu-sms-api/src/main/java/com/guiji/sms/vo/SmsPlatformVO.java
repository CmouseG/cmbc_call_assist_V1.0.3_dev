package com.guiji.sms.vo;

import java.util.Date;

import lombok.Data;

@Data
public class SmsPlatformVO
{
	private Integer id;

    private String platformName;

    private String platformParams;

    private String identification;

    private String createUser;

    private Date createTime;

    private String updateUser;

    private Date updateTime;
}
