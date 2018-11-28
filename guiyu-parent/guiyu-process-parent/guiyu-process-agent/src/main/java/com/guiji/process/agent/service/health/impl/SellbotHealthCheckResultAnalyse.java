package com.guiji.process.agent.service.health.impl;

import com.guiji.common.model.process.ProcessStatusEnum;
import com.guiji.process.agent.model.CommandResult;
import com.guiji.process.agent.service.health.IHealthCheckResultAnalyse;
import org.apache.commons.lang3.StringUtils;

public class SellbotHealthCheckResultAnalyse implements IHealthCheckResultAnalyse {

    @Override
    public ProcessStatusEnum check(CommandResult cmdResult) {
        if (cmdResult != null && StringUtils.isNotEmpty(cmdResult.getOutput())) {
            if(cmdResult.getOutput().contains("success") || cmdResult.getOutput().contains("RUNNING"))
            {
                return ProcessStatusEnum.UP;
            }
        }

        return ProcessStatusEnum.DOWN;
    }

    @Override
    public void afertPublish(CommandResult cmdResult) {
        if (cmdResult != null && StringUtils.isNotEmpty(cmdResult.getOutput())) {
            String output = cmdResult.getOutput();
            String result = output.substring(output.length()-1, output.length());

        }
    }
}
