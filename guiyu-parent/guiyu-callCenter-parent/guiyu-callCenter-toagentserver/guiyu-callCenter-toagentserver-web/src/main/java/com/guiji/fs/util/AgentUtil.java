package com.guiji.fs.util;

public class AgentUtil {
    /**
     * 构建FreeSWITCH可以识别的agentId
     * @param agentId
     * @return
     */
    public static String buildAgentId(String agentId){
        return agentId;
    }

    /**
     * 将从FreeSWITCH获取的id进行清理,去掉@后面的信息
     * @param agentId
     * @return
     */
    public static String unbuildAgentId(String agentId){
        int key = agentId.indexOf("@");
        if(key>0){
            agentId = agentId.substring(0, key);
        }

        return agentId;
    }
}
