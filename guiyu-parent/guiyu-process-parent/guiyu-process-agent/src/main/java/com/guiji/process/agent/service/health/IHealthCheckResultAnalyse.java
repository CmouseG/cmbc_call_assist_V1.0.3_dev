package com.guiji.process.agent.service.health;

import com.guiji.common.model.process.ProcessStatusEnum;
import com.guiji.process.agent.model.CommandResult;

public interface IHealthCheckResultAnalyse {

    ProcessStatusEnum check(CommandResult cmdResult);

    void afertPublish(CommandResult cmdResult);

}
