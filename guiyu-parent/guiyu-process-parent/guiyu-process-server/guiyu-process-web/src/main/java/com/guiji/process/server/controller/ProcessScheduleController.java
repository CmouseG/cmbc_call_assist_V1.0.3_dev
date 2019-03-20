package com.guiji.process.server.controller;

import com.guiji.common.model.process.ProcessTypeEnum;
import com.guiji.component.aspect.SysOperaLog;
import com.guiji.component.result.Result;
import com.guiji.process.api.IProcessSchedule;
import com.guiji.process.model.ChangeModelReq;
import com.guiji.process.model.ProcessReleaseVO;
import com.guiji.common.model.process.ProcessInstanceVO;
import com.guiji.process.model.PublishBotstenceTaskVO;
import com.guiji.process.model.UpgrateResouceReq;
import com.guiji.process.server.service.IProceseScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by ty on 2018/11/23.
 */
@RestController
public class ProcessScheduleController implements IProcessSchedule {
    Logger logger = LoggerFactory.getLogger(ProcessScheduleController.class);
    @Autowired
    IProceseScheduleService processScheduleService;
    @Override
    public Result.ReturnData<List<ProcessInstanceVO>> getTTS(@RequestParam("model") String model, @RequestParam("requestCount") int requestCount) {
        logger.info("申请TTS资源开始");
        List<ProcessInstanceVO> processInstanceVOList = processScheduleService.getTTS(model,requestCount);
        logger.info("申请TTS资源结束");
        return Result.ok(processInstanceVOList);
    }

    @Override
    public Result.ReturnData<List<ProcessInstanceVO>> getAllTTS() {
        logger.info("查询所有TTS资源开始");
        List<ProcessInstanceVO> processInstanceVOList = processScheduleService.getTTS();
        logger.info("查询所有TTS资源结束");
        return Result.ok(processInstanceVOList);
    }

    @Override
    public Result.ReturnData<Integer> sellbotCount() {
        int count = processScheduleService.sellbotCount();
        return Result.ok(Integer.valueOf(count));
    }

    @Override
    public Result.ReturnData<Boolean> changeTTS(@RequestBody ChangeModelReq req) {
        ProcessInstanceVO processInstance = new ProcessInstanceVO();
        processInstance.setType(ProcessTypeEnum.TTS);
        processInstance.setIp(req.getIp());
        processInstance.setPort(req.getPort());
        processScheduleService.restoreTtsModel(req.getFromModel(),req.getToModel(),processInstance,req.getUserId());
        return Result.ok();
    }

    @Override
    public Result.ReturnData<List<ProcessInstanceVO>> getSellbot(@RequestParam("requestCount") int requestCount) {
        List<ProcessInstanceVO> processInstanceVOList = processScheduleService.getSellbot(requestCount);
        return Result.ok(processInstanceVOList);
    }

    @Override
    public Result.ReturnData<Boolean> release(@RequestBody ProcessReleaseVO processReleaseVO) {
        boolean result = processScheduleService.release(processReleaseVO);
        return Result.ok(result);
    }

    @Override
    @SysOperaLog(operaTarget = "进程管理", operaType = "发布话术")
    public Result.ReturnData<PublishBotstenceTaskVO> publishResource(@RequestBody UpgrateResouceReq req) {
        PublishBotstenceTaskVO publisthBotstenceTaskVo = processScheduleService.publishResource(req.getProcessTypeEnum(),req.getTmplId(),req.getFile(),req.getUserId());
        return Result.ok(publisthBotstenceTaskVo);
    }

}
