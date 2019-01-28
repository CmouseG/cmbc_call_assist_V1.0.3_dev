package com.guiji.web.request;


import com.guiji.fs.pojo.AgentState;
import com.guiji.fs.pojo.AgentStatus;
import com.guiji.fs.pojo.AgentType;
import lombok.Data;

@Data
public class AgentInfo {
    private String agentId;
    private String password;
    private AgentType agentType = AgentType.Verto;
    private AgentStatus status;
    private AgentState state;
    private String contact;
}
