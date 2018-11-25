package com.guiji.process.server.controller;

import com.guiji.common.model.Page;
import com.guiji.process.core.vo.CmdTypeEnum;
import com.guiji.process.server.dao.entity.SysProcess;
import com.guiji.process.server.service.ISysProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by ty on 2018/11/23.
 */
@RestController
@RequestMapping("/process")
public class ProcessController {
    @Autowired
    ISysProcessService sysProcessService;

    @PostMapping("/list")
    public Page<SysProcess> list(int pageNo, int pageSize, SysProcess sysProcess) {
        return sysProcessService.queryProcessPage(pageNo,pageSize,sysProcess);
    }


    @PostMapping("/start")
    public Object start(List<SysProcess> sysProcessList) {
        sysProcessService.executeCmd(sysProcessList,CmdTypeEnum.START);
        return "success";
    }

    @PostMapping("/stop")
    public Object stop(List<SysProcess> sysProcessList) {
        sysProcessService.executeCmd(sysProcessList,CmdTypeEnum.STOP);
        return "success";
    }
}
