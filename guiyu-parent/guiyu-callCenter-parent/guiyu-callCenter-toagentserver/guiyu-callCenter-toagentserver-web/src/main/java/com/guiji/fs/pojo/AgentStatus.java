package com.guiji.fs.pojo;

/**
 * 座席状态
 */
public enum AgentStatus {
    /**
     * 登出，无法接听电话
     */
    Logged_Out,

    /**
     * 待命，随时可以接听电话
     */
    Available,

    /**
     * 小休，停止接听电话，直到重置为Available
     */
    On_Break;

    /**
     * 获取状态的字符串标识，用于FreeSWITCH设置座席状态
     * @return
     */
    public String getStrStatus(){
        String name = this.name();
        if(name.contains("_")){
            name = name.replace("_", " ");
        }

        return name;
    }

    /**
     * 将字符串转为状态
     * @param status
     * @return
     */
    public static AgentStatus getAgentStatus(String status){
        if(status.contains(" ")){
            status = status.replace(" ", "_");
        }

        AgentStatus agentStatus = AgentStatus.valueOf(status);
        return agentStatus;
    }
}
