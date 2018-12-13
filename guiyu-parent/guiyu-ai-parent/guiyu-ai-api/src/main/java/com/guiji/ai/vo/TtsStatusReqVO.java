package com.guiji.ai.vo;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 语音合成状态请求对象
 * Created by ty on 2018/11/13.
 *
 */
@ApiModel(value="TtsStatusReqVO对象",description="TTS状态请求对象")
public class TtsStatusReqVO implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value="起始时间")
	private Date startTime;
	@ApiModelProperty(value="结束时间")
	private Date endTime;
	@ApiModelProperty(value="模型名称")
	private String model;
	@ApiModelProperty(value="处理状态")
	private String status;

	public Date getStartTime()
	{
		return startTime;
	}

	public void setStartTime(Date startTime)
	{
		this.startTime = startTime;
	}

	public Date getEndTime()
	{
		return endTime;
	}

	public void setEndTime(Date endTime)
	{
		this.endTime = endTime;
	}

	public String getModel()
	{
		return model;
	}

	public void setModel(String model)
	{
		this.model = model;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}
	
}
