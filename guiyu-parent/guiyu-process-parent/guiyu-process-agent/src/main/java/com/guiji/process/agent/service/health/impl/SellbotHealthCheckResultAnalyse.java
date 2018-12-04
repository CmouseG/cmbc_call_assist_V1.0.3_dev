package com.guiji.process.agent.service.health.impl;

import com.guiji.common.model.process.ProcessInstanceVO;
import com.guiji.common.model.process.ProcessStatusEnum;
import com.guiji.component.result.Result;
import com.guiji.process.agent.handler.ImClientProtocolBO;
import com.guiji.process.agent.model.CommandResult;
import com.guiji.process.agent.service.health.IHealthCheckResultAnalyse;
import com.guiji.process.core.message.CmdMessageVO;
import com.guiji.process.core.vo.CmdTypeEnum;
import com.guiji.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class SellbotHealthCheckResultAnalyse implements IHealthCheckResultAnalyse {
    private static final String CMD_RESULT_CODE99 = "99";

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
    public void afertPublish(CommandResult cmdResult,ProcessInstanceVO processInstanceVO,List<String> parameters) {
        String result = CMD_RESULT_CODE99;
        if (cmdResult != null && StringUtils.isNotEmpty(cmdResult.getOutput())) {
            String output = cmdResult.getOutput();
            result = output.substring(output.length()-1, output.length());
        }

        // 发送给服务端
        CmdMessageVO newCmdMsg = new CmdMessageVO();
        newCmdMsg.setCmdType(CmdTypeEnum.PULBLISH_SELLBOT_BOTSTENCE);
        newCmdMsg.setProcessInstanceVO(processInstanceVO);
        newCmdMsg.setParameters(parameters);
        newCmdMsg.setCommandResult(result);
        newCmdMsg.setCommandResultDesc(Result.error(result).getMsg());
        ImClientProtocolBO.getIntance().send(newCmdMsg,3);
    }

    @Override
    public void afertRestart(CommandResult cmdResult,ProcessInstanceVO processInstanceVO,List<String> parameters) {
        String result = CMD_RESULT_CODE99;
        if (cmdResult != null && StringUtils.isNotEmpty(cmdResult.getOutput())) {
            if(cmdResult.getOutput().contains("started")){
                result = "4";
            } else {
                result = "5";
            }
        }

        // 发送给服务端
        CmdMessageVO newCmdMsg = new CmdMessageVO();
        newCmdMsg.setCmdType(CmdTypeEnum.AFTER_RESTART);
        newCmdMsg.setProcessInstanceVO(processInstanceVO);
        newCmdMsg.setParameters(parameters);
        newCmdMsg.setCommandResult(result);
        newCmdMsg.setCommandResultDesc(Result.error(result).getMsg());
        ImClientProtocolBO.getIntance().send(newCmdMsg,3);
    }
}
