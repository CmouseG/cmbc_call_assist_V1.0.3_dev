package com.guiji.process.agent.service.impl;


import com.guiji.process.agent.model.CfgNodeOperVO;
import com.guiji.process.agent.model.CfgNodeVO;
import com.guiji.process.agent.service.ProcessCfgService;
import com.guiji.process.agent.util.CommandUtils;
import com.guiji.process.agent.util.ProcessUtil;
import com.guiji.process.core.message.CmdMessageVO;
import com.guiji.process.core.vo.CmdTypeEnum;
import com.guiji.process.core.vo.ProcessInstanceVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.UnknownHostException;

public class ProcessCmdHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public void excute(CmdMessageVO cmdMessageVO) throws InterruptedException, UnknownHostException {
        if(cmdMessageVO == null)
        {
            return;
        }


        CfgNodeOperVO CfgNodeOperVO = getNodeOper(cmdMessageVO.getCmdType(), cmdMessageVO.getProcessInstanceVO().getPort());
        switch (cmdMessageVO.getCmdType()) {
            case REGISTER:
                break;
            case RESTART:
                doCmd(CfgNodeOperVO.getCmd());
                break;
            case UNKNOWN:
                break;
            case START:
                doCmd(CfgNodeOperVO.getCmd());
                break;
            case STOP:
                doCmd(CfgNodeOperVO.getCmd());
                Thread.sleep(1000);//等待1s查看是否关闭成功
                //TODO 检查是否停掉，如果进程还在则kill -9
                if (ProcessUtil.checkRun(cmdMessageVO.getProcessInstanceVO().getPort())) {
                    ProcessUtil.killProcess(cmdMessageVO.getProcessInstanceVO().getPort());
                }
                break;
            case HEALTH:
                ProcessInstanceVO processInstanceVO = cmdMessageVO.getProcessInstanceVO();
                ProcessUtil.sendHealth(processInstanceVO.getPort(),processInstanceVO.getType(),CfgNodeOperVO);
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



    private void doCmd(String cmd) {
        // 获取 命令
        CommandUtils.exec(cmd);
    }
}
