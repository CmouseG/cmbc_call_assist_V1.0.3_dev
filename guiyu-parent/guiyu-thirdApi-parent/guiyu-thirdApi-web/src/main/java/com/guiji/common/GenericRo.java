package com.guiji.common;

import lombok.Data;

/**
 * 通用请求信息
 */
@Data
public class GenericRo implements Encryptable {

    private Long userId;

    private String userName;

    private String orgCode;

    private Integer orgId;
}
