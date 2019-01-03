package com.guiji.calloutserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AIInitRequest {
    /**
     * FreeSWITCH的通道id
     */
    private String uuid;

    private String planUuid;
    /**
     * 通道使用的模板id
     */
    private String tempId;

    /**
     * 客户号码
     */
    private String phoneNum;


    private String userId;


    private String aiId;
}
