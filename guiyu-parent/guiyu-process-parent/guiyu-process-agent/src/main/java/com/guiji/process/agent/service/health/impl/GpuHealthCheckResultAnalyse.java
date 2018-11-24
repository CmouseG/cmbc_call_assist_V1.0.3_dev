package com.guiji.process.agent.service.health.impl;

import com.guiji.common.model.process.ProcessStatusEnum;
import com.guiji.process.agent.model.CommandResult;
import com.guiji.process.agent.service.health.IHealthCheckResultAnalyse;

public class GpuHealthCheckResultAnalyse implements IHealthCheckResultAnalyse {

    @Override
    public ProcessStatusEnum check(CommandResult cmdResult) {
        return null;
    }
}
