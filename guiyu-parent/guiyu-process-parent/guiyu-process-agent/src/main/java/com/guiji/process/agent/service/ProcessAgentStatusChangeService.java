package com.guiji.process.agent.service;

import com.guiji.common.model.process.ProcessStatusEnum;

public class ProcessAgentStatusChangeService {


    public void checkStatus(int prot, ProcessStatusEnum statusEnum)
    {
        if(statusEnum == ProcessStatusEnum.DOWN)
        {
            // 启动它
        }

        // 看当前状态和缓存中的状态是否一致，不一致，则通知服务端
    }
}
