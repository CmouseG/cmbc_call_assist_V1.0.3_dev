package com.guiji.process.agent;


import com.guiji.process.agent.model.CfgNodeOperVO;
import com.guiji.process.agent.model.CfgNodeVO;
import com.guiji.process.agent.service.ProcessCfgService;
import com.guiji.process.agent.util.CommandUtils;
import com.guiji.process.agent.util.ProcessUtil;
import com.guiji.process.core.IProcessCmdHandler;
import com.guiji.process.core.message.CmdMessageVO;
import com.guiji.process.core.vo.CmdTypeEnum;
import com.guiji.process.core.vo.ProcessInstanceVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.UnknownHostException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProcessAgentCmdHandler implements IProcessCmdHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public void excute(CmdMessageVO cmdMessageVO) throws  Exception {
        if(cmdMessageVO == null)
        {
            return;
        }


        CfgNodeOperVO cfgNodeOperVO = getNodeOper(cmdMessageVO.getCmdType(), cmdMessageVO.getProcessInstanceVO().getPort());
        switch (cmdMessageVO.getCmdType()) {
            case REGISTER:
                break;

            case RESTART:
                doCmd(cmdMessageVO,cfgNodeOperVO);
                break;

            case UNKNOWN:
                break;

            case START:
                doCmd(cmdMessageVO,cfgNodeOperVO);
                break;

            case STOP:
                doCmd(cmdMessageVO,cfgNodeOperVO);
                Thread.sleep(1000);//等待1s查看是否关闭成功
                //TODO 检查是否停掉，如果进程还在则kill -9
                if (ProcessUtil.checkRun(cmdMessageVO.getProcessInstanceVO().getPort())) {
                    ProcessUtil.killProcess(cmdMessageVO.getProcessInstanceVO().getPort());
                }
                break;

            case HEALTH:
                ProcessInstanceVO processInstanceVO = cmdMessageVO.getProcessInstanceVO();
                ProcessUtil.sendHealth(processInstanceVO.getPort(),processInstanceVO.getType(),cfgNodeOperVO,processInstanceVO.getName());
                break;

            case PULBLISH_SELLBOT_BOTSTENCE:
                ProcessInstanceVO processInstanceVO = cmdMessageVO.getProcessInstanceVO();
                ProcessUtil.sendHealth(processInstanceVO.getPort(),processInstanceVO.getType(),cfgNodeOperVO);
                break;

            case PULBLISH_FREESWITCH_BOTSTENCE:
                ProcessInstanceVO processInstanceVO = cmdMessageVO.getProcessInstanceVO();
                ProcessUtil.sendHealth(processInstanceVO.getPort(),processInstanceVO.getType(),cfgNodeOperVO);
                break;
            default:
                break;
        }
    }


    private CfgNodeOperVO getNodeOper(CmdTypeEnum cmdTypeEnum, int port)
    {
        CfgNodeVO cfgNodeVO = ProcessCfgService.cfgMap.get(port);
        if(cfgNodeVO == null)
        {
            return null;
        }

        for (CfgNodeOperVO cfgNodeOperVO: cfgNodeVO.getCfgNodeOpers()  ) {

            if(cfgNodeOperVO.getCmdTypeEnum() == cmdTypeEnum)
            {
                return cfgNodeOperVO;
            }

        }
        return null;
    }



    private void doCmd(CmdMessageVO cmdMessageVO,CfgNodeOperVO cfgNodeOperVO) {
        if (ProcessUtil.neetExecute(cmdMessageVO.getProcessInstanceVO().getPort(),cfgNodeOperVO.getCmdTypeEnum())) {

            String cmd = cfgNodeOperVO.getCmd();
            if(cmdMessageVO.getParameters() != null && !cmdMessageVO.getParameters().isEmpty())
            {
                cmd = MessageFormat.format(cfgNodeOperVO.getCmd(), cmdMessageVO.getParameters().toArray());
            }

            // 发起重启命令
            CommandUtils.exec(cmd);
            // 执行完命令保存结果到内存记录
            ProcessUtil.afterCMD(cmdMessageVO.getProcessInstanceVO().getPort(),cfgNodeOperVO.getCmdTypeEnum());
        }
    }

}
