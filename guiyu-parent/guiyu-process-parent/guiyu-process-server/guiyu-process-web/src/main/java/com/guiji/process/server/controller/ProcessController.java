package com.guiji.process.server.controller;

import com.guiji.process.core.ProcessMsgHandler;
import com.guiji.process.core.message.CmdMessageVO;
import com.guiji.process.core.vo.CmdTypeEnum;
import com.guiji.process.core.vo.ProcessInstanceVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ty on 2018/11/23.
 */
@RestController
@RequestMapping("/process")
public class ProcessController {
    @PostMapping("/start")
    public Object start(ProcessInstanceVO processInstances) {
        List<CmdMessageVO> cmdMessageVOs = new ArrayList<CmdMessageVO>();
        CmdMessageVO cmdMessageVO = new CmdMessageVO();

        cmdMessageVO.setProcessInstanceVO(processInstances);
        cmdMessageVO.setCmdType(CmdTypeEnum.START);
        cmdMessageVOs.add(cmdMessageVO);
//        for (ProcessInstanceVO processInstance:processInstances) {
//            CmdMessageVO cmdMessageVO = new CmdMessageVO();
//
//            cmdMessageVO.setProcessInstanceVO(processInstance);
//            cmdMessageVO.setCmdType(CmdTypeEnum.START);
//
//            cmdMessageVOs.add(cmdMessageVO);
//        }

        if(!cmdMessageVOs.isEmpty())
        {
            ProcessMsgHandler.getInstance().add(cmdMessageVOs);
        }
        return "success";
    }
}
