package com.guiji.process.agent.service.health;

import com.guiji.common.model.process.ProcessInstanceVO;
import com.guiji.common.model.process.ProcessStatusEnum;
import com.guiji.process.agent.model.CommandResult;

import java.util.List;

public interface IHealthCheckResultAnalyse {

    ProcessStatusEnum check(CommandResult cmdResult);

    void afertPublish(CommandResult cmdResult, ProcessInstanceVO processInstanceVO,List<String> parameters,String reqKey);

    void afertRestart(CommandResult cmdResult, ProcessInstanceVO processInstanceVO,List<String> parameters,String reqKey);

    void afterRestoreModel(CommandResult cmdResult, ProcessInstanceVO processInstanceVO,List<String> parameters,String reqKey);
}
