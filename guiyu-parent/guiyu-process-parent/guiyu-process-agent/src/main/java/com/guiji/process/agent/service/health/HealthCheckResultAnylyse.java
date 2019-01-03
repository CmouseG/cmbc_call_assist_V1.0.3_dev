package com.guiji.process.agent.service.health;

import com.guiji.common.model.process.ProcessInstanceVO;
import com.guiji.common.model.process.ProcessStatusEnum;
import com.guiji.common.model.process.ProcessTypeEnum;
import com.guiji.component.result.Result;
import com.guiji.process.agent.handler.ImClientProtocolBO;
import com.guiji.process.agent.model.CommandResult;
import com.guiji.process.agent.service.health.impl.FreeswitchHealthCheckResultAnalyse;
import com.guiji.process.agent.service.health.impl.GpuHealthCheckResultAnalyse;
import com.guiji.process.agent.service.health.impl.RobotHealthCheckResultAnalyse;
import com.guiji.process.agent.service.health.impl.SellbotHealthCheckResultAnalyse;
import com.guiji.process.core.message.CmdMessageVO;
import com.guiji.process.core.vo.CmdTypeEnum;
import com.guiji.utils.BeanUtil;

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

    public static void afertPublish(CommandResult cmdResult,ProcessInstanceVO processInstanceVO,ProcessTypeEnum processType,List<String> parameters,String reqKey) {
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
            analyse.afertPublish(cmdResult,processInstanceVO,parameters,reqKey);
        }
    }
    public static void afterRestart(CommandResult cmdResult,ProcessInstanceVO processInstanceVO,ProcessTypeEnum processType,List<String> parameters,String reqKey) {
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
            analyse.afertRestart(cmdResult,processInstanceVO,parameters,reqKey);
        }
    }

    public static void afterRestoreModel(CommandResult cmdResult,ProcessInstanceVO processInstanceVO,ProcessTypeEnum processType,List<String> parameters,String reqKey) {
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
            analyse.afterRestoreModel(cmdResult,processInstanceVO,parameters,reqKey);
        }
    }

    public static void doNothing(ProcessInstanceVO processInstanceVO,ProcessTypeEnum processType,List<String> parameters,String reqKey) {
        String result = "10";

        ProcessInstanceVO processInstanceVOTmp = new ProcessInstanceVO();
        BeanUtil.copyProperties(processInstanceVO, processInstanceVOTmp);
        // 发送给服务端
        CmdMessageVO newCmdMsg = new CmdMessageVO();
        newCmdMsg.setCmdType(CmdTypeEnum.DO_NOTHING);
        newCmdMsg.setProcessInstanceVO(processInstanceVOTmp);
        newCmdMsg.setParameters(parameters);
        newCmdMsg.setCommandResult(result);
        newCmdMsg.setCommandResultDesc(Result.error("10").getMsg());
        newCmdMsg.setReqKey(reqKey);
        ImClientProtocolBO.getIntance().send(newCmdMsg,3);
    }
}
