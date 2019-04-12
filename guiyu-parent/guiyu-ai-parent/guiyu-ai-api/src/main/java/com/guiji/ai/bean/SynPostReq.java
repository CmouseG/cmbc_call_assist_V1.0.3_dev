package com.guiji.ai.bean;

import lombok.Data;

/**
 * 同步请求对象
 */
@Data
public class SynPostReq
{
	private String busId;
	private String model;
	private String content;
}
