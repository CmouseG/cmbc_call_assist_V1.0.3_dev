package com.guiji.dispatch.controller;

import com.guiji.common.exception.GuiyuException;
import com.guiji.common.exception.GuiyuExceptionEnum;
import com.guiji.component.jurisdiction.Jurisdiction;
import com.guiji.dispatch.dto.JoinPlanDto;
import com.guiji.dispatch.dto.OptPlanDto;
import com.guiji.dispatch.enums.PlanOperBatchEnum;
import com.guiji.dispatch.service.IPlanBatchService;
import com.guiji.dispatch.util.Log;
import com.guiji.utils.JsonUtils;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/dispatch/batch/controller")
public class DispatchBatchController {

    private Logger logger = LoggerFactory.getLogger(DispatchBatchController.class);

    @Autowired
    private IPlanBatchService planBatchService;

    @Autowired
    private ThreadPoolTaskExecutor asyncServiceExecutor;


    @ApiOperation(value="删除计划任务", notes="删除计划任务")
    @Log(info ="删除计划任务")
  //  @Jurisdiction("taskCenter_phonelist_batchDelete,taskCenter_phonelist_delete")
    @RequestMapping(value = "/delPlanBatch", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public boolean delPlanBatch(@RequestHeader String userId, @RequestHeader String orgCode,
                                @RequestHeader Integer orgId, @RequestHeader Integer authLevel,
                                @RequestBody OptPlanDto optPlanDto)throws Exception{
        if(null == optPlanDto){
            optPlanDto = new OptPlanDto();
        }
        optPlanDto.setOperUserId(userId);
        optPlanDto.setOperOrgCode(orgCode);
        optPlanDto.setOperOrgId(orgId);
        optPlanDto.setAuthLevel(authLevel);
        logger.info("/dispatch/batch/controller/delPlanBatch入参:{}", JsonUtils.bean2Json(optPlanDto));
        /*boolean bool = planBatchService.delPlanBatch(optPlanDto);
        if(!bool){
            throw new GuiyuException("删除计划失败");
        }*/
        this.batchOptThread(optPlanDto, PlanOperBatchEnum.DEL.getType());
        Thread.sleep(1000L);//等待一秒
        return true;
    }


    @ApiOperation(value="批量暂停计划任务", notes="批量暂停计划任务")
    @Log(info ="批量暂停计划任务")
    @Jurisdiction("taskCenter_phonelist_batchPause")
    @RequestMapping(value = "/suspendPlanBatch", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public boolean suspendPlanBatch(@RequestHeader String userId, @RequestHeader String orgCode,
                                @RequestHeader Integer orgId, @RequestHeader Integer authLevel,
                                @RequestBody OptPlanDto optPlanDto)throws Exception{
        if(null == optPlanDto){
            optPlanDto = new OptPlanDto();
        }
        optPlanDto.setOperUserId(userId);
        optPlanDto.setOperOrgCode(orgCode);
        optPlanDto.setOperOrgId(orgId);
        optPlanDto.setAuthLevel(authLevel);
        logger.info("/dispatch/batch/controller/suspendPlanBatch入参:{}", JsonUtils.bean2Json(optPlanDto));
        /*boolean bool = planBatchService.suspendPlanBatch(optPlanDto);
        if(!bool){
            throw new GuiyuException("暂停计划失败");
        }*/
        this.batchOptThread(optPlanDto, PlanOperBatchEnum.SUSPEND.getType());
        Thread.sleep(1000L);//等待一秒
        return true;
    }

    @ApiOperation(value="批量停止计划任务", notes="批量停止计划任务")
    @Log(info ="批量停止计划任务")
    @Jurisdiction("taskCenter_phonelist_batchStop")
    @RequestMapping(value = "/stopPlanBatch", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public boolean stopPlanBatch(@RequestHeader String userId, @RequestHeader String orgCode,
                             @RequestHeader Integer orgId, @RequestHeader Integer authLevel,
                             @RequestBody OptPlanDto optPlanDto)throws Exception{
        if(null == optPlanDto){
            optPlanDto = new OptPlanDto();
        }
        optPlanDto.setOperUserId(userId);
        optPlanDto.setOperOrgCode(orgCode);
        optPlanDto.setOperOrgId(orgId);
        optPlanDto.setAuthLevel(authLevel);
        logger.info("/dispatch/batch/controller/stopPlanBatch:{}", JsonUtils.bean2Json(optPlanDto));
        /*boolean bool = planBatchService.stopPlanBatch(optPlanDto);
        if(!bool){
            throw new GuiyuException("批量停止计划任务失败");
        }*/
        this.batchOptThread(optPlanDto, PlanOperBatchEnum.STOP.getType());
        Thread.sleep(1000L);//等待一秒
        return true;
    }


    //
    @ApiOperation(value="批量恢复计划任务", notes="恢复计划任务")
    @Log(info ="批量恢复计划任务")
    @Jurisdiction("taskCenter_phonelist_batchRecover")
    @RequestMapping(value = "/recoveryPlanBatch", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public boolean recoveryPlanBatch(@RequestHeader String userId, @RequestHeader String orgCode,
                                 @RequestHeader Integer orgId, @RequestHeader Integer authLevel,
                                 @RequestBody OptPlanDto optPlanDto)throws Exception{
        if(null == optPlanDto){
            optPlanDto = new OptPlanDto();
        }
        optPlanDto.setOperUserId(userId);
        optPlanDto.setOperOrgCode(orgCode);
        optPlanDto.setOperOrgId(orgId);
        optPlanDto.setAuthLevel(authLevel);
        logger.info("/dispatch/batch/controller/recoveryPlanBatch:{}", JsonUtils.bean2Json(optPlanDto));
        /*boolean bool = planBatchService.recoveryPlanBatch(optPlanDto);
        if(!bool){
            throw new GuiyuException("批量恢复计划任务失败");
        }*/
        this.batchOptThread(optPlanDto, PlanOperBatchEnum.RECOVERY.getType());
        Thread.sleep(1000L);//等待一秒
        return true;
    }


    protected void batchOptThread(OptPlanDto optPlanDto, Integer opType) {
        logger.info("start batchOptThread");
        try{
            asyncServiceExecutor.execute(new Runnable(){
                @Override
                public void run(){
                    switch (opType){
                        case 1://删除
                            planBatchService.delPlanBatch(optPlanDto);
                            break;
                        case 2://暂停
                            planBatchService.suspendPlanBatch(optPlanDto);
                            break;
                        case 3://停止
                            planBatchService.stopPlanBatch(optPlanDto);
                            break;
                        case 4://恢复
                            planBatchService.recoveryPlanBatch(optPlanDto);
                            break;
                    }

                }
            });
        }catch(Exception e){
            e.printStackTrace();
        }
        logger.info("end batchOptThread");
    }


    //批量加入
    @ApiOperation(value="批量加入计划任务", notes="恢复计划任务")
    @Log(info ="批量计划任务")
    @Jurisdiction("taskCenter_phonelist_batchJoin")
    @RequestMapping(value = "/joinPlanBatch", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public boolean joinPlanBatch(@RequestHeader String userId, @RequestHeader String orgCode,
                                     @RequestHeader Integer orgId, @RequestHeader Integer authLevel,
                                     @RequestBody JoinPlanDto joinPlanDto){
        if(null == joinPlanDto){
            joinPlanDto = new JoinPlanDto();
        }
        joinPlanDto.setOperUserId(userId);
        joinPlanDto.setOperOrgCode(orgCode);
        joinPlanDto.setOperOrgId(orgId);
        joinPlanDto.setAuthLevel(authLevel);
        logger.info("/dispatch/batch/controller/joinPlanBatch:{}", JsonUtils.bean2Json(joinPlanDto));
        boolean bool = planBatchService.joinPlanBatch(joinPlanDto);
        if(!bool){
            throw new GuiyuException("批量加入计划任务失败");
        }
        return bool;
    }


    @ApiOperation(value="批量导出计划任务", notes="批量导出计划任务")
    @Log(info ="批量导出计划任务")
//    @Jurisdiction("taskCenter_phonelist_batchExport")
    @RequestMapping(value = "/exportPlanBatch", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public boolean exportPlanBatch(@RequestHeader String userId, @RequestHeader String orgCode,
                                 @RequestHeader Integer orgId, @RequestHeader Integer authLevel,
                                   @RequestBody OptPlanDto optPlanDto)throws Exception{
        if(null == optPlanDto){
            optPlanDto = new OptPlanDto();
        }
        optPlanDto.setOperUserId(userId);
        optPlanDto.setOperOrgCode(orgCode);
        optPlanDto.setOperOrgId(orgId);
        optPlanDto.setAuthLevel(authLevel);
        logger.info("/dispatch/batch/controller/exportPlanBatch:{}", JsonUtils.bean2Json(optPlanDto));
        boolean bool = planBatchService.exportPlanBatch(optPlanDto);
        Thread.sleep(2000L);//等待二秒
        if(!bool){
            throw new GuiyuException("批量导出计划任务失败");
        }
        return bool;
    }
}
