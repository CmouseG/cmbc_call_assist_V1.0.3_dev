package com.guiji.fs.pojo;

import lombok.Data;

@Data
public class Agent {
    private String agentId;
    private AgentType agentType = AgentType.Verto;
    private AgentStatus status;
    private String state;
    private String contact;
    private String mobile;
    private String max_no_answer;
    private String wrap_up_time;
    private String reject_delay_time;
    private String busy_delay_time;
    private String no_answer_delay_time;
    private String last_bridge_start;
    private String last_bridge_end;
    private String last_offered_call;
    private String last_status_change;
    private String no_answer_count;
    private String calls_answered;
    private String talk_time;
    private String ready_time;
}
