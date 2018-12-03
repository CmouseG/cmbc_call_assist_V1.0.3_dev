package com.guiji.process.agent.service.health;

import com.guiji.common.model.process.ProcessInstanceVO;
import com.guiji.common.model.process.ProcessStatusEnum;
import com.guiji.common.model.process.ProcessTypeEnum;
import com.guiji.process.agent.model.CommandResult;
import com.guiji.process.agent.service.health.impl.FreeswitchHealthCheckResultAnalyse;
import com.guiji.process.agent.service.health.impl.GpuHealthCheckResultAnalyse;
import com.guiji.process.agent.service.health.impl.RobotHealthCheckResultAnalyse;
import com.guiji.process.agent.service.health.impl.SellbotHealthCheckResultAnalyse;

import java.util.List;

public class HealthCheckResultAnylyse {


    public static ProcessStatusEnum check(CommandResult cmdResult, ProcessTypeEnum processType)
    {
        IHealthCheckResultAnalyse analyse = null;
        switch (processType)
        {
            case SELLBOT:
                analyse = new SellbotHealthCheckResultAnalyse();
                break;

            case TTS:
                analyse = new GpuHealthCheckResultAnalyse();
                break;
        }

        ProcessStatusEnum result = null;
        if(analyse != null)
        {
            result = analyse.check(cmdResult);
        }

        return result;
    }

    public static void afertPublish(CommandResult cmdResult,ProcessInstanceVO processInstanceVO,ProcessTypeEnum processType,List<String> parameters) {
        IHealthCheckResultAnalyse analyse = null;
        switch (processType) {
            case SELLBOT:
                analyse = new SellbotHealthCheckResultAnalyse();
                break;

            case TTS:
                analyse = new GpuHealthCheckResultAnalyse();
                break;
            case FREESWITCH:
                analyse = new FreeswitchHealthCheckResultAnalyse();
                break;
            case ROBOT:
                analyse = new RobotHealthCheckResultAnalyse();
                break;
        }

        if (analyse != null) {
            analyse.afertPublish(cmdResult,processInstanceVO,parameters);
        }
    }
}
