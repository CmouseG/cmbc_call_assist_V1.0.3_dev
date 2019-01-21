package com.guiji.sms.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="ConfigReqVO对象",description="新增短信配置请求对象")
public class ConfigReqVO
{
	@ApiModelProperty(value="id")
	private Integer id;
	@ApiModelProperty(value="通道名称")
	private String tunnelName;  
	@ApiModelProperty(value="话术模版id")
	private String templateId;  
	@ApiModelProperty(value="话术模版名称")
	private String templateName;  
	@ApiModelProperty(value="意向标签")
	private String intentionTag;  
	@ApiModelProperty(value="自定义短信内容")
	private String smsContent; 
	@ApiModelProperty(value="短信模板id")
	private Integer smsTemplateId; 
	
}
