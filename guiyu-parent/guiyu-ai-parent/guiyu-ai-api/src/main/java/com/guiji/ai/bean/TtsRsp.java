package com.guiji.ai.bean;

import java.util.Map;

import lombok.Data;

@Data
public class TtsRsp
{
    private String busId;
	private Integer status;
    private String statusMsg;
    private Map<String,String> audios;
}
