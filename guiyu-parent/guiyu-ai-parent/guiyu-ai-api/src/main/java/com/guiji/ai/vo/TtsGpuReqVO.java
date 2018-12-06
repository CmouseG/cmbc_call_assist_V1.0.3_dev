package com.guiji.ai.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;

@ApiModel(value="TtsGpuReqVO对象",description="查询GPU模型列表分页条件")
public class TtsGpuReqVO implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private int pageSize;
	
	private int pageNum;
	
	
	public TtsGpuReqVO(int pageSize, int pageNum)
	{
		this.pageSize = pageSize;
		this.pageNum = pageNum;
	}

	public Integer getPageSize()
	{
		return pageSize;
	}

	public void setPageSize(Integer pageSize)
	{
		this.pageSize = pageSize;
	}

	public Integer getPageNum()
	{
		return pageNum;
	}

	public void setPageNum(Integer pageNum)
	{
		this.pageNum = pageNum;
	}

}
