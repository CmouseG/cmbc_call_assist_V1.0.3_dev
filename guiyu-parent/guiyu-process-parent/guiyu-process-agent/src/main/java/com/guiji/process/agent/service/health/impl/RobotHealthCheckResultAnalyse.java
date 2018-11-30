package com.guiji.process.agent.service.health.impl;

import com.guiji.common.model.process.ProcessInstanceVO;
import com.guiji.common.model.process.ProcessStatusEnum;
import com.guiji.component.result.Result;
import com.guiji.process.agent.handler.ImClientProtocolBO;
import com.guiji.process.agent.model.CommandResult;
import com.guiji.process.agent.service.health.IHealthCheckResultAnalyse;
import com.guiji.process.core.message.CmdMessageVO;
import com.guiji.process.core.vo.CmdTypeEnum;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by ty on 2018/11/29.
 */
public class RobotHealthCheckResultAnalyse implements IHealthCheckResultAnalyse {
    private static final String CMD_RESULT_CODE99 = "99";
    @Override
    public ProcessStatusEnum check(CommandResult cmdResult) {
        return null;
    }

    @Override
    public void afertPublish(CommandResult cmdResult, ProcessInstanceVO processInstanceVO) {
        String result = CMD_RESULT_CODE99;
        if (cmdResult != null && StringUtils.isNotEmpty(cmdResult.getOutput())) {
            String output = cmdResult.getOutput();
            result = output.substring(output.length()-1, output.length());
        }

        // 发送给服务端
        CmdMessageVO newCmdMsg = new CmdMessageVO();
        newCmdMsg.setCmdType(CmdTypeEnum.PUBLISH_ROBOT_BOTSTENCE);
        newCmdMsg.setProcessInstanceVO(processInstanceVO);
        newCmdMsg.setCommandResult(result);
        newCmdMsg.setCommandResultDesc(Result.error(result).getMsg());
        ImClientProtocolBO.getIntance().send(newCmdMsg,3);

    }
}
