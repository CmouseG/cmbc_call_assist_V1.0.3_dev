package com.guiji.process.agent;


import com.guiji.common.model.process.ProcessStatusEnum;
import com.guiji.common.model.process.ProcessTypeEnum;
import com.guiji.component.result.Result;
import com.guiji.process.agent.handler.ImClientProtocolBO;
import com.guiji.process.agent.model.CfgProcessOperVO;
import com.guiji.process.agent.model.CfgProcessVO;
import com.guiji.process.agent.model.CommandResult;
import com.guiji.process.agent.service.ProcessCfgService;
import com.guiji.process.agent.service.ProcessStatusLocal;
import com.guiji.process.agent.service.health.HealthCheckResultAnylyse;
import com.guiji.process.agent.util.CommandUtils;
import com.guiji.process.agent.util.ProcessUtil;
import com.guiji.process.core.IProcessCmdHandler;
import com.guiji.process.core.ProcessMsgHandler;
import com.guiji.process.core.message.CmdMessageVO;
import com.guiji.process.core.vo.CmdTypeEnum;
import com.guiji.common.model.process.ProcessInstanceVO;
import com.guiji.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
public class ProcessAgentCmdHandler implements IProcessCmdHandler {

    private final Logger logger = LoggerFactory.getLogger(ProcessAgentCmdHandler.class);

    public void excute(CmdMessageVO cmdMessageVO) throws  Exception {

        if(cmdMessageVO == null)
        {
            return;
        }

        ProcessInstanceVO processInstanceVO = cmdMessageVO.getProcessInstanceVO();
        CfgProcessOperVO cfgProcessOperVO = getNodeOper(cmdMessageVO.getCmdType(), cmdMessageVO.getProcessInstanceVO().getPort());
        if(cfgProcessOperVO == null ) {
            if (cmdMessageVO.getCmdType() != CmdTypeEnum.HEALTH) {
                HealthCheckResultAnylyse.doNothing(processInstanceVO,processInstanceVO.getType(),cmdMessageVO.getParameters(),cmdMessageVO.getReqKey());
            }
            return;
        }

        switch (cmdMessageVO.getCmdType()) {
            case REGISTER:
                break;

            case RESTART:
                cfgProcessOperVO = getNodeOper(CmdTypeEnum.RESTART, cmdMessageVO.getProcessInstanceVO().getPort());
                if (cfgProcessOperVO != null) {
                    CommandResult commandResult = doCmd(cmdMessageVO, cfgProcessOperVO);
                    HealthCheckResultAnylyse.afterRestart(commandResult,processInstanceVO,processInstanceVO.getType(),cmdMessageVO.getParameters(),cmdMessageVO.getReqKey());
                }
                break;

            case UNKNOWN:
                break;

            case START:
                doCmd(cmdMessageVO, cfgProcessOperVO);
                break;

            case STOP:
                doCmd(cmdMessageVO, cfgProcessOperVO);
                Thread.sleep(1000);//等待1s查看是否关闭成功
                //  检查是否停掉，如果进程还在则kill -9
                if (ProcessUtil.checkRun(processInstanceVO.getPort())) {
                    ProcessUtil.killProcess(cmdMessageVO.getProcessInstanceVO().getPort());
                }
                break;

            case RESTORE_MODEL:
                CommandResult restoreResult = doCmd(cmdMessageVO, cfgProcessOperVO);
                HealthCheckResultAnylyse.afterRestoreModel(restoreResult,processInstanceVO,processInstanceVO.getType(),cmdMessageVO.getParameters(),cmdMessageVO.getReqKey());
                // 更新配置文件
                ProcessCfgService.getIntance().refreshProcessKey(cmdMessageVO.getProcessInstanceVO().getPort(), cmdMessageVO.getParameters().get(1));
                Thread.sleep(30000);//等待1s查看是否关闭成功
                break;

            case HEALTH:
                if(cmdMessageVO.getProcessInstanceVO().getType() != ProcessTypeEnum.AGENT)
                {
                    doHealth(cmdMessageVO, cfgProcessOperVO);
                }

                break;

            case PULBLISH_SELLBOT_BOTSTENCE:
                CommandResult sellbotResult = doCmd(cmdMessageVO, cfgProcessOperVO);
                HealthCheckResultAnylyse.afertPublish(sellbotResult,processInstanceVO,ProcessTypeEnum.SELLBOT,cmdMessageVO.getParameters(),cmdMessageVO.getReqKey());
                break;

            case PULBLISH_FREESWITCH_BOTSTENCE:
                CommandResult freeswitchResult = doCmd(cmdMessageVO, cfgProcessOperVO);
                HealthCheckResultAnylyse.afertPublish(freeswitchResult,processInstanceVO,ProcessTypeEnum.FREESWITCH,cmdMessageVO.getParameters(),cmdMessageVO.getReqKey());
                break;
            case PUBLISH_ROBOT_BOTSTENCE:
                CommandResult robotResult = doCmd(cmdMessageVO, cfgProcessOperVO);
                HealthCheckResultAnylyse.afertPublish(robotResult,processInstanceVO,ProcessTypeEnum.ROBOT,cmdMessageVO.getParameters(),cmdMessageVO.getReqKey());
                break;
            default:
                break;
        }
    }


    private CfgProcessOperVO getNodeOper(CmdTypeEnum cmdTypeEnum, int port)
    {
        CfgProcessVO cfgProcessVO = ProcessCfgService.cfgMap.get(port);
        if(cfgProcessVO == null)
        {
            return null;
        }

        /*if(cmdTypeEnum == CmdTypeEnum.PULBLISH_FREESWITCH_BOTSTENCE || cmdTypeEnum == CmdTypeEnum.PULBLISH_SELLBOT_BOTSTENCE || )
        {

        }*/

        if(cfgProcessVO.getCfgNodeOpers() == null)
        {
            return null;
        }

        for (CfgProcessOperVO cfgProcessOperVO : cfgProcessVO.getCfgNodeOpers()  ) {

            if(cfgProcessOperVO.getCmdTypeEnum() == cmdTypeEnum)
            {
                return cfgProcessOperVO;
            }

        }
        return null;
    }



    private CommandResult doCmd(CmdMessageVO cmdMessageVO, CfgProcessOperVO cfgProcessOperVO) {

        CommandResult cmdResult = null;
        logger.debug("执行命令开始：：" + cmdMessageVO +", " + cfgProcessOperVO);
        if (ProcessUtil.neetExecute(cmdMessageVO.getProcessInstanceVO().getPort(), cfgProcessOperVO.getCmdTypeEnum())) {
            String cmd = "";
            if(cfgProcessOperVO != null) {
                cmd = cfgProcessOperVO.getCmd();
            }

            if(cmdMessageVO.getParameters() != null && !cmdMessageVO.getParameters().isEmpty())
            {
                cmd = MessageFormat.format(cfgProcessOperVO.getCmd(), cmdMessageVO.getParameters().toArray());
            }

            // 发起命令
            cmdResult = CommandUtils.exec(cmd);
            logger.info("命令执行结果:" + cmdResult.getOutput());
            // 执行完命令保存结果到内存记录
            ProcessUtil.afterCMD(cmdMessageVO.getProcessInstanceVO().getPort(), cfgProcessOperVO.getCmdTypeEnum());
        }

        return cmdResult;
    }



    private void doHealth(CmdMessageVO cmdMessageVO, CfgProcessOperVO cfgProcessOperVO)
    {
        CommandResult cmdResult = doCmd(cmdMessageVO,cfgProcessOperVO);

        // 对结果分析
        ProcessStatusEnum nowStatus = HealthCheckResultAnylyse.check(cmdResult, cmdMessageVO.getProcessInstanceVO().getType());

        boolean hanChanged = ProcessStatusLocal.getInstance().hasChanged(cmdMessageVO.getProcessInstanceVO().getPort(), nowStatus);
        if(hanChanged)
        {
            // 发送给服务端
            CmdMessageVO newCmdMsg = new CmdMessageVO();
            newCmdMsg.setCmdType(CmdTypeEnum.HEALTH);
            ProcessInstanceVO processInstanceVO = new ProcessInstanceVO();
            processInstanceVO.setIp(cmdMessageVO.getProcessInstanceVO().getIp());
            processInstanceVO.setType(cmdMessageVO.getProcessInstanceVO().getType());
            processInstanceVO.setPort(cmdMessageVO.getProcessInstanceVO().getPort());
            processInstanceVO.setName(cmdMessageVO.getProcessInstanceVO().getName());
            processInstanceVO.setStatus(nowStatus);
            newCmdMsg.setProcessInstanceVO(processInstanceVO);
            ImClientProtocolBO.getIntance().send(newCmdMsg,3);
        }

        //停止状态的进程自动重启
        if (ProcessStatusEnum.DOWN == nowStatus) {
            cmdMessageVO.setCmdType(CmdTypeEnum.START);
            ProcessMsgHandler.getInstance().add(cmdMessageVO);
        }

        // 更新本地状态
        ProcessStatusLocal.getInstance().put(cmdMessageVO.getProcessInstanceVO().getPort(), nowStatus);
    }
}
