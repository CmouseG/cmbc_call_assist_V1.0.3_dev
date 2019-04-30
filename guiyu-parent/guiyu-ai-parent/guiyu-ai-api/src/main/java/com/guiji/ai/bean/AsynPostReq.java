package com.guiji.ai.bean;

import java.util.List;

import lombok.Data;

/**
 * 异步请求对象
 */
@Data
public class AsynPostReq
{
    private String busId;
    private String version; //tts版本（cpu/gpu）
    private String model; // 模型
    private List<String> contents; // 需要合成的句子集合
    private String notifyUrl; // 回调地址
}
