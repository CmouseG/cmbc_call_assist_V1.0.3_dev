package com.guiji.sms.vo;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="TaskReqVO对象",description="任务请求对象")
public class TaskReqVO
{
	@ApiModelProperty(value="id")
	private Integer id;
	@ApiModelProperty(value="发送方式: 0-手动发送；1-定时发送")
	private Integer sendType;
	@ApiModelProperty(value="任务名称")
	private String taskName;
	@ApiModelProperty(value="平台通道")
	private String tunnelName;
	@ApiModelProperty(value="短信模版id")
	private Integer smsTemplateId;
	@ApiModelProperty(value="自定义短信内容")
	private String smsContent;
	@ApiModelProperty(value="发送时间")
	private String sendDate;
	@ApiModelProperty(value="发送名单文件")
	private MultipartFile file;
}
