package com.guiji.cache;

import lombok.Data;

/**
 * 缓存于redis中的用户数据
 */
@Data
public class ThirdApiUser {

    private Long userId;

    private String clientId;

    private String clientSecret;

    private String orgCode;

}