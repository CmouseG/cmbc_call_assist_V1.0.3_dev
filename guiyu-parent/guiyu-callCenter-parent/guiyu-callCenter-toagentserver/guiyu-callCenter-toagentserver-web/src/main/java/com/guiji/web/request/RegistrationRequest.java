package com.guiji.web.request;

import lombok.Data;

@Data
public class RegistrationRequest {

    private String customerName;
    private String customerMobile;
    private String customerAddr;
    private String remark;

    private String recordId;

    //private Long userId;

}
