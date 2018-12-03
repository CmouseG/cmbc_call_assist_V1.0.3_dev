package com.guiji.ai.vo;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 语音合成状态返回对象
 * Created by ty on 2018/11/13.
 */
@ApiModel(value="TtsStatusRspVO对象",description="语音合成状态返回对象")
public class TtsStatusRspVO implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value="业务ID")
	private String busId;
	
	@ApiModelProperty(value="模型名称")
	private String model;
	
	@ApiModelProperty(value="数量")
	private Integer count;
	
	@ApiModelProperty(value="创建时间")
	private Date createTime;

	public String getBusId()
	{
		return busId;
	}

	public void setBusId(String busId)
	{
		this.busId = busId;
	}

	public String getModel()
	{
		return model;
	}

	public void setModel(String model)
	{
		this.model = model;
	}

	public Integer getCount()
	{
		return count;
	}

	public void setCount(Integer count)
	{
		this.count = count;
	}

	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}
	
}
