package com.guiji.ai.vo;

import java.util.List;

import lombok.Data;

@Data
public class AsynPostReqVO
{
    private String busId;
    private String model;
    private List<String> contents;
    private String notifyUrl;
}
