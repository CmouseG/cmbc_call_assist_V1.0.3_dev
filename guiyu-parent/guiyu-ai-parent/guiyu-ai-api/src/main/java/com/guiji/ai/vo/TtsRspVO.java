package com.guiji.ai.vo;

import java.util.Map;

import lombok.Data;

@Data
public class TtsRspVO
{
    private String busId;
	private Integer status;
    private String statusMsg;
    private Map<String,String> audios;
}
