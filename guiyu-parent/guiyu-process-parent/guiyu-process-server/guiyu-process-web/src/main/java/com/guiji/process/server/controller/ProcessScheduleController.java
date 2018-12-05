package com.guiji.process.server.controller;

import com.guiji.common.model.process.ProcessTypeEnum;
import com.guiji.component.result.Result;
import com.guiji.process.api.IProcessSchedule;
import com.guiji.process.model.ProcessReleaseVO;
import com.guiji.common.model.process.ProcessInstanceVO;
import com.guiji.process.model.UpgrateResouceReq;
import com.guiji.process.server.service.IProceseScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
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
    public Result.ReturnData<Boolean> changeTTS(@RequestParam("fromModel") String fromModel, @RequestParam("toModel") String toModel, @RequestParam("ip") String ip, @RequestParam("port") int port) {
        ProcessInstanceVO processInstance = new ProcessInstanceVO();
        processInstance.setType(ProcessTypeEnum.TTS);
        processInstance.setIp(ip);
        processInstance.setPort(port);
        processScheduleService.restoreTtsModel(fromModel,toModel,processInstance);
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
    public Result.ReturnData<Boolean> publishResource(@RequestBody UpgrateResouceReq req) {
        System.out.println("调用到了");
        processScheduleService.publishResource(req.getProcessTypeEnum(),req.getTmplId(),req.getFile());
        return Result.ok();
    }

}
