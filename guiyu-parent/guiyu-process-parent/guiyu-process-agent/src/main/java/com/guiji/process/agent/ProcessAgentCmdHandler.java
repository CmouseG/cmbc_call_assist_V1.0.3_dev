package com.guiji.process.agent;


import com.guiji.common.model.process.ProcessStatusEnum;
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
import com.guiji.process.core.vo.ProcessInstanceVO;
import com.guiji.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.Inet4Address;
import java.text.MessageFormat;

@Service
public class ProcessAgentCmdHandler implements IProcessCmdHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public void excute(CmdMessageVO cmdMessageVO) throws  Exception {

        if(cmdMessageVO == null)
        {
            return;
        }

        ProcessInstanceVO processInstanceVO = cmdMessageVO.getProcessInstanceVO();
        CfgProcessOperVO cfgProcessOperVO = getNodeOper(cmdMessageVO.getCmdType(), cmdMessageVO.getProcessInstanceVO().getPort());

        switch (cmdMessageVO.getCmdType()) {
            case REGISTER:
                break;

            case RESTART:
                doCmd(cmdMessageVO, cfgProcessOperVO);
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

            case HEALTH:
                doHealth(cmdMessageVO, cfgProcessOperVO);
                break;

            case PULBLISH_SELLBOT_BOTSTENCE:
                doCmd(cmdMessageVO, cfgProcessOperVO);
                break;

            case PULBLISH_FREESWITCH_BOTSTENCE:
                doCmd(cmdMessageVO, cfgProcessOperVO);
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
        if (ProcessUtil.neetExecute(cmdMessageVO.getProcessInstanceVO().getPort(), cfgProcessOperVO.getCmdTypeEnum())) {

            String cmd = cfgProcessOperVO.getCmd();
            if(cmdMessageVO.getParameters() != null && !cmdMessageVO.getParameters().isEmpty())
            {
                cmd = MessageFormat.format(cfgProcessOperVO.getCmd(), cmdMessageVO.getParameters().toArray());
            }

            // 发起命令
            cmdResult = CommandUtils.exec(cmd);
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
            String msg = JsonUtils.bean2Json(newCmdMsg);

            ImClientProtocolBO.getIntance().send(msg,3);

            //停止状态的进程自动重启
            if (ProcessStatusEnum.DOWN == processInstanceVO.getStatus()) {
                cmdMessageVO.setCmdType(CmdTypeEnum.START);
                ProcessMsgHandler.getInstance().add(cmdMessageVO);
            }

            // 更新本地状态
            ProcessStatusLocal.getInstance().put(cmdMessageVO.getProcessInstanceVO().getPort(), nowStatus);
        }
    }

}
