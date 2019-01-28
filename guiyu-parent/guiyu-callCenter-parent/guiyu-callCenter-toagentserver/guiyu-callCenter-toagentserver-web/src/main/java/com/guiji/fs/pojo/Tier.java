package com.guiji.fs.pojo;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Tier {
    @NonNull
    private String queueId;
    @NonNull
    private String agentId;
    private String level;
    private String position;

    /**
     * 获取在callcenter模块中使用的id
     * @return
     */
    public String getCCQueueId(){
        return queueId;
    }

    /**
     * 获取在callcenter模块中使用的id
     * @return
     */
    public String getCCAgentId(){
        return agentId;
    }
}
