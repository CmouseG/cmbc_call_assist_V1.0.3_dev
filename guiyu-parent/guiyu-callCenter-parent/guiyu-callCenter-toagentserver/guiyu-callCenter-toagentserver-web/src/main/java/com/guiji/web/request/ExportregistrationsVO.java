package com.guiji.web.request;

import lombok.Data;

@Data
public class ExportregistrationsVO {
    private String regIds;
    private Long agentId;
    private int authLevel;
    private String orgCode;
}
