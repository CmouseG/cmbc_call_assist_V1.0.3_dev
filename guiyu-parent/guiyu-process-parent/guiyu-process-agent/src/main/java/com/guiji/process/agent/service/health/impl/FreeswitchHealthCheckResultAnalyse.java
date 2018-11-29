package com.guiji.process.agent.service.health.impl;

import com.guiji.common.model.process.ProcessInstanceVO;
import com.guiji.common.model.process.ProcessStatusEnum;
import com.guiji.process.agent.model.CommandResult;
import com.guiji.process.agent.service.health.IHealthCheckResultAnalyse;

/**
 * Created by ty on 2018/11/29.
 */
public class FreeswitchHealthCheckResultAnalyse implements IHealthCheckResultAnalyse{
    @Override
    public ProcessStatusEnum check(CommandResult cmdResult) {
        return null;
    }

    @Override
    public void afertPublish(CommandResult cmdResult, ProcessInstanceVO processInstanceVO) {

    }
}
