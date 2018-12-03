package com.guiji.ai.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class TtsStatus implements Serializable
{
	private Integer id;

    private String busId;
    
    private String model;
    
    private String status;
    
    private Integer text_count;
    
    private Date createTime;
    
    private Date updateTime;
	
	private static final long serialVersionUID = 1L;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

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

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public Integer getText_count()
	{
		return text_count;
	}

	public void setText_count(Integer text_count)
	{
		this.text_count = text_count;
	}

	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	public Date getUpdateTime()
	{
		return updateTime;
	}

	public void setUpdateTime(Date updateTime)
	{
		this.updateTime = updateTime;
	}
	
}
