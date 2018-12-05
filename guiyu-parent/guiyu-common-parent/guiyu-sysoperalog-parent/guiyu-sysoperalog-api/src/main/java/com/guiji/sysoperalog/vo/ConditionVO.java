package com.guiji.sysoperalog.vo;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * sys_user_action查询请求条件
 * Created by ty on 2018/11/13.
 */
@ApiModel(value="ConditionVO对象",description="sys_user_action查询请求条件对象")
public class ConditionVO implements Serializable
{
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value="开始时间")
	private Date startTime;
	
	@ApiModelProperty(value="结束时间")
	private Date endTime;
	
	@ApiModelProperty(value="偏移量")
	private Integer limitStart;

	@ApiModelProperty(value="条数")
    private Integer limitEnd;
    
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

	public Integer getLimitStart()
	{
		return limitStart;
	}

	public void setLimitStart(Integer limitStart)
	{
		this.limitStart = limitStart;
	}

	public Integer getLimitEnd()
	{
		return limitEnd;
	}

	public void setLimitEnd(Integer limitEnd)
	{
		this.limitEnd = limitEnd;
	}

}
