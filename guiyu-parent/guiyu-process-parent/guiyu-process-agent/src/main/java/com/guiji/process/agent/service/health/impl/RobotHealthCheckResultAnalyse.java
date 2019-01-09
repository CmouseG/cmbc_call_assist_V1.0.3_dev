package com.guiji.process.agent.service.health.impl;

import com.guiji.common.model.process.ProcessInstanceVO;
import com.guiji.common.model.process.ProcessStatusEnum;
import com.guiji.component.result.Result;
import com.guiji.process.agent.handler.ImClientProtocolBO;
import com.guiji.process.agent.model.CommandResult;
import com.guiji.process.agent.service.health.IHealthCheckResultAnalyse;
import com.guiji.process.core.message.CmdMessageVO;
import com.guiji.process.core.vo.CmdTypeEnum;
import com.guiji.utils.BeanUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

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
    public void afertPublish(CommandResult cmdResult, ProcessInstanceVO processInstanceVO,List<String> parameters,String reqKey) {
        String result = CMD_RESULT_CODE99;
        if (cmdResult != null && StringUtils.isNotEmpty(cmdResult.getOutput())) {
            String output = cmdResult.getOutput();
            result = output.substring(output.length()-1, output.length());
        }

        if (!"0".equals(result) && !"1".equals(result) && !"2".equals(result) && !"3".equals(result)) {
            result = CMD_RESULT_CODE99;
        }

        // 发送给服务端
        CmdMessageVO newCmdMsg = new CmdMessageVO();
        newCmdMsg.setCmdType(CmdTypeEnum.PUBLISH_ROBOT_BOTSTENCE);
        ProcessInstanceVO tmp = new ProcessInstanceVO();
        BeanUtil.copyProperties(processInstanceVO, tmp);
        newCmdMsg.setProcessInstanceVO(tmp);
        newCmdMsg.setParameters(parameters);
        newCmdMsg.setCommandResult(result);
        newCmdMsg.setCommandResultDesc(Result.error(result).getMsg());
        newCmdMsg.setReqKey(reqKey);
        ImClientProtocolBO.getIntance().send(newCmdMsg,3);

    }

    @Override
    public void afertRestart(CommandResult cmdResult, ProcessInstanceVO processInstanceVO, List<String> parameters,String reqKey) {

    }

    @Override
    public void afterRestoreModel(CommandResult cmdResult, ProcessInstanceVO processInstanceVO, List<String> parameters,String reqKey) {

    }
}
