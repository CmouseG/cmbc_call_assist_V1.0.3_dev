package com.guiji.toagentserver.entity;

import lombok.Data;

@Data
public class AgentMembrVO {
    private Long customerId;
    private String loginAccount;
    private String customerName;
    private String orgCode;
}
