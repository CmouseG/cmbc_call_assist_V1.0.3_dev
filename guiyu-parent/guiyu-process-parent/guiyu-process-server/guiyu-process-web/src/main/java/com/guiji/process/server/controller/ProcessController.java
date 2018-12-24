package com.guiji.process.server.controller;

import com.guiji.common.model.Page;
import com.guiji.component.aspect.SysOperaLog;
import com.guiji.process.core.vo.CmdTypeEnum;
import com.guiji.process.server.dao.entity.SysProcess;
import com.guiji.process.server.model.ProcessCmdVO;
import com.guiji.process.server.service.ISysProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by ty on 2018/11/23.
 */
@RestController
public class ProcessController {
    @Autowired
    ISysProcessService sysProcessService;

    @SysOperaLog(operaTarget = "进程管理", operaType = "查询")
    @GetMapping("/list")
    public Page<SysProcess> list(int pageNo, int pageSize, SysProcess sysProcess) {
        return sysProcessService.queryProcessPage(pageNo,pageSize,sysProcess);
    }

    @SysOperaLog(operaTarget = "进程管理", operaType = "agent查询")
    @GetMapping("/listAgent")
    public Page<SysProcess> listAgent(int pageNo, int pageSize, SysProcess sysProcess) {
        return sysProcessService.queryAgentPage(pageNo,pageSize,sysProcess);
    }

    @SysOperaLog(operaTarget = "进程管理", operaType = "启动进程")
    @PostMapping("/start")
    public Object start(@RequestBody ProcessCmdVO processCmdVO,@RequestHeader Long userId) {
        sysProcessService.executeCmd(processCmdVO.getSysProcessList(),CmdTypeEnum.START,userId);
        return "success";
    }

    @SysOperaLog(operaTarget = "进程管理", operaType = "停止进程")
    @PostMapping("/stop")
    public Object stop(@RequestBody ProcessCmdVO processCmdVO,@RequestHeader Long userId) {
        sysProcessService.executeCmd(processCmdVO.getSysProcessList(),CmdTypeEnum.STOP,userId);
        return "success";
    }

    @SysOperaLog(operaTarget = "进程管理", operaType = "重启进程")
    @PostMapping("/restart")
    public Object restart(@RequestBody ProcessCmdVO processCmdVO,@RequestHeader Long userId) {
        sysProcessService.executeCmd(processCmdVO.getSysProcessList(),CmdTypeEnum.RESTART,userId);
        return "success";
    }
}
