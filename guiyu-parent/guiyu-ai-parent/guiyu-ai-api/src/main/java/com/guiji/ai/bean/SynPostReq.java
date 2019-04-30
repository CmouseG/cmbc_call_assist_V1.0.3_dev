package com.guiji.ai.bean;

import lombok.Data;

/**
 * 同步请求对象
 */
@Data
public class SynPostReq
{
	private String busId;
	private String version; //tts版本（cpu/gpu）
	private String model; // 模型
	private String content; // 需要合成的句子
}
