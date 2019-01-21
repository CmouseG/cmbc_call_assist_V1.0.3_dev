package com.guiji.sms.vo;

import java.util.List;

import com.guiji.sms.dao.entity.SmsConfig;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="ConfigListRspVO对象",description="获取短信配置列表返回对象")
public class ConfigListRspVO
{
	@ApiModelProperty(value="总条数")
	private int totalCount;
	@ApiModelProperty(value="配置列表")
	private List<SmsConfig> smsConfigList;
}
