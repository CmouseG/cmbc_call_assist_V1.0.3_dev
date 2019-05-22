package com.guiji.sign;

import lombok.Data;

import java.util.Map;

@Data
public class SignRo {

    private String privateKey;

    private Map<String, Object> params;

    private String signature;
}
