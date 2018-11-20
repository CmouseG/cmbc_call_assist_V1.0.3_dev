package com.guiji.process.agent.service;

import com.guiji.process.core.vo.DeviceStatusEnum;

public class ProcessAgentStatusChangeService {


    public void checkStatus(int prot, DeviceStatusEnum statusEnum)
    {
        if(statusEnum == DeviceStatusEnum.DOWN)
        {
            // 启动它
        }

        // 看当前状态和缓存中的状态是否一致，不一致，则通知服务端
    }
}
