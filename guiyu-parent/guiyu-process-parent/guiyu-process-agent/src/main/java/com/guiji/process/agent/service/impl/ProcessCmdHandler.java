package com.guiji.process.agent.service.impl;


import com.guiji.process.agent.model.CfgNodeOperVO;
import com.guiji.process.agent.model.CfgNodeVO;
import com.guiji.process.agent.service.ProcessCfgService;
import com.guiji.process.core.message.CmdMessageVO;
import com.guiji.process.core.vo.CmdTypeEnum;
import com.guiji.process.core.vo.ProcessInstanceVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

public class ProcessCmdHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public void excute(CmdMessageVO cmdMessageVO)
    {
        if(cmdMessageVO == null)
        {
            return;
        }


        CfgNodeOperVO CfgNodeOperVO = getNodeOper(cmdMessageVO.getCmdType(), cmdMessageVO.getProcessInstanceVO().getPort());
        switch (cmdMessageVO.getCmdType()) {


            case REGISTER:

                break;

            case RESTART:

                break;

            case UNKNOWN:
                break;

            case START:
                break;

            case STOP:
                break;

            case HEALTH:

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



    private void doCmd(int port,  String cmd)
    {


        // 获取 命令
    }
}
