package com.guiji.process.server.controller;

import com.guiji.common.model.Page;
import com.guiji.component.aspect.SysOperaLog;
import com.guiji.process.server.dao.entity.SysProcess;
import com.guiji.process.server.dao.entity.SysProcessTask;
import com.guiji.process.server.service.ISysProcessTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ty on 2018/12/6.
 */
@RestController
public class ProcessTaskController {
    @Autowired
    private ISysProcessTaskService sysProcessTaskService;

    @GetMapping("/processTaskList")
    @SysOperaLog(operaTarget = "进程任务", operaType = "查询")
    public Page<SysProcessTask> list(int pageNo, int pageSize, SysProcessTask sysProcessTask) {
        return sysProcessTaskService.queryProcessTaskPage(pageNo,pageSize,sysProcessTask);
    }
}
