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
    private String model;
    private List<String> contents;
    private String notifyUrl;
}
