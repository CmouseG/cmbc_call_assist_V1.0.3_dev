package com.guiji.dispatch.controller;

import com.guiji.component.jurisdiction.Jurisdiction;
import com.guiji.dispatch.dto.OptPlanDto;
import com.guiji.dispatch.service.IPlanBatchService;
import com.guiji.dispatch.util.Log;
import com.guiji.utils.JsonUtils;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/dispatch/batch/controller")
public class DispatchBatchController {

    private Logger logger = LoggerFactory.getLogger(DispatchBatchController.class);

    @Autowired
    private IPlanBatchService planBatchService;

 //   @Jurisdiction("taskCenter_phonelist_batchDelete,taskCenter_phonelist_delete")
    @ApiOperation(value="删除计划任务", notes="删除计划任务")
    @Log(info ="删除计划任务")
    @RequestMapping(value = "/delPlanBatch", method = {RequestMethod.POST, RequestMethod.GET})
    public boolean delPlanBatch(@RequestBody OptPlanDto optPlanDto){
        logger.info("/dispatch/batch/controller/delPlanBatch入参:{}", JsonUtils.bean2Json(optPlanDto));
        boolean bool = planBatchService.delPlanBatch(optPlanDto);
        return bool;
    }


    @ApiOperation(value="暂停计划任务", notes="暂停计划任务")
    @Log(info ="暂停计划任务")
    @RequestMapping(value = "/suspendPlanBatch", method = {RequestMethod.POST, RequestMethod.GET})
    public boolean suspendPlanBatch(@RequestBody OptPlanDto optPlanDto){
        logger.info("/dispatch/batch/controller/suspendPlanBatch入参:{}", JsonUtils.bean2Json(optPlanDto));
        boolean bool = planBatchService.delPlanBatch(optPlanDto);
        return bool;
    }

    @ApiOperation(value="停止计划任务", notes="停止计划任务")
    @Log(info ="停止计划任务")
    @RequestMapping(value = "/stopPlanBatch", method = {RequestMethod.POST, RequestMethod.GET})
    public boolean stopPlanBatch(@RequestBody OptPlanDto optPlanDto){
        logger.info("/dispatch/batch/controller/stopPlanBatch:{}", JsonUtils.bean2Json(optPlanDto));
        boolean bool = planBatchService.delPlanBatch(optPlanDto);
        return bool;
    }
}
