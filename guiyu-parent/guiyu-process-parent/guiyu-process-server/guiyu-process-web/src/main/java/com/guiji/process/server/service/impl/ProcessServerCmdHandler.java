package com.guiji.process.server.service.impl;


import com.guiji.process.core.IProcessCmdHandler;
import com.guiji.process.core.message.CmdMessageVO;
import com.guiji.process.core.vo.CmdTypeEnum;
import com.guiji.process.core.vo.ProcessInstanceVO;
import com.guiji.process.server.dao.entity.SysProcess;
import com.guiji.process.server.service.ISysProcessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProcessServerCmdHandler implements IProcessCmdHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ProcessManageService processManageService;

    @Autowired
    private ISysProcessService sysProcessService;

    public void excute(CmdMessageVO cmdMessageVO)
    {
        if(cmdMessageVO == null)
        {
            return;
        }

        switch (cmdMessageVO.getCmdType()) {
            case AGENTREGISTER:
                doAgentRegister(cmdMessageVO);
                break;
            case REGISTER:
                doRegister(cmdMessageVO);
                break;
            case UNREGISTER:
                doUnRegister(cmdMessageVO);
                break;
            case RESTART:
                processManageService.cmd(cmdMessageVO.getProcessInstanceVO(), CmdTypeEnum.RESTART, cmdMessageVO.getParameters());
                break;
            case UNKNOWN:
                break;
            case START:
                processManageService.cmd(cmdMessageVO.getProcessInstanceVO(), CmdTypeEnum.START, cmdMessageVO.getParameters());
                break;
            case STOP:
                processManageService.cmd(cmdMessageVO.getProcessInstanceVO(), CmdTypeEnum.STOP, cmdMessageVO.getParameters());
                break;
            case HEALTH:
                doHealthStatus(cmdMessageVO);
                break;
            default:
                break;
        }

    }


    private void doHealthStatus(CmdMessageVO cmdMessageVO)
    {
        ProcessInstanceVO processInstanceVO = cmdMessageVO.getProcessInstanceVO();
        ProcessInstanceVO oldProcessInstanceVO = processManageService.getDevice(processInstanceVO.getType(), processInstanceVO.getIp(), processInstanceVO.getPort());
        if(oldProcessInstanceVO == null)
        {
            // 未注册过，不做
            return;
        }

        processManageService.updateStatus(processInstanceVO.getType(), processInstanceVO.getIp(), processInstanceVO.getPort(), processInstanceVO.getStatus());
    }

    private void doAgentRegister(CmdMessageVO cmdMessageVO) {
        ProcessInstanceVO processInstanceVO = cmdMessageVO.getProcessInstanceVO();
        //TODO 新的agent注册入库
        // 存入数据库
        SysProcess sysProcess = new SysProcess();
        sysProcess.setIp(processInstanceVO.getIp());
        sysProcess.setPort(String.valueOf(processInstanceVO.getPort()));
        sysProcess.setName(processInstanceVO.getName());
        sysProcess.setProcessKey(processInstanceVO.getProcessKey());
        sysProcess.setStatus(processInstanceVO.getStatus().getValue());
        sysProcess.setType(processInstanceVO.getType().getValue());
        sysProcess.setCreateTime(new Date());
        sysProcess.setUpdateTime(new Date());
        sysProcessService.insert(sysProcess);

    }


    private void doRegister(CmdMessageVO cmdMessageVO)
    {
        ProcessInstanceVO processInstanceVO = cmdMessageVO.getProcessInstanceVO();

        List<ProcessInstanceVO> lst = new ArrayList<ProcessInstanceVO>();

        if(processInstanceVO == null)
        {
            return;
        }

        ProcessInstanceVO oldProcessInstanceVO = processManageService.getDevice(processInstanceVO.getType(), processInstanceVO.getIp(), processInstanceVO.getPort());
        if(oldProcessInstanceVO != null)
        {
            return;
        }

        lst.add(processInstanceVO);
        processManageService.register(lst);
    }

    private void doUnRegister(CmdMessageVO cmdMessageVO)
    {
        ProcessInstanceVO processInstanceVO = cmdMessageVO.getProcessInstanceVO();

        List<ProcessInstanceVO> lst = new ArrayList<ProcessInstanceVO>();

        if(processInstanceVO == null)
        {
            return;
        }

        ProcessInstanceVO oldProcessInstanceVO = processManageService.getDevice(processInstanceVO.getType(), processInstanceVO.getIp(), processInstanceVO.getPort());
        if(oldProcessInstanceVO == null)
        {
            return;
        }

        lst.add(processInstanceVO);
        processManageService.unRegister(lst);
    }

}
