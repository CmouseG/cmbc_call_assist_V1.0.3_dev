package com.guiji.web.response;

import lombok.Data;

@Data
public class QueryRegistration {
    private Long regId;
    private String customerName;
    private String customerMobile;
    private String customerAddr;

    private String updateTime;
    private String remark;

    private Long userId;
    private String userName;
}
