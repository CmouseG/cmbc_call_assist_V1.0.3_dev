package com.guiji.dispatch.controller;

import com.guiji.common.exception.GuiyuException;
import com.guiji.common.exception.GuiyuExceptionEnum;
import com.guiji.component.jurisdiction.Jurisdiction;
import com.guiji.dispatch.dto.JoinPlanDto;
import com.guiji.dispatch.dto.OptPlanDto;
import com.guiji.dispatch.service.IPlanBatchService;
import com.guiji.dispatch.util.Log;
import com.guiji.utils.JsonUtils;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/dispatch/batch/controller")
public class DispatchBatchController {

    private Logger logger = LoggerFactory.getLogger(DispatchBatchController.class);

    @Autowired
    private IPlanBatchService planBatchService;


    @ApiOperation(value="删除计划任务", notes="删除计划任务")
    @Log(info ="删除计划任务")
    @Jurisdiction("taskCenter_phonelist_batchDelete,taskCenter_phonelist_delete")
    @RequestMapping(value = "/delPlanBatch", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public boolean delPlanBatch(@RequestHeader String userId, @RequestHeader String orgCode,
                                @RequestHeader Integer orgId, @RequestHeader Integer authLevel,
                                @RequestBody OptPlanDto optPlanDto){
        logger.info("/dispatch/batch/controller/delPlanBatch入参:{}", JsonUtils.bean2Json(optPlanDto));
        if(null == optPlanDto){
            optPlanDto = new OptPlanDto();
        }
        optPlanDto.setOperUserId(userId);
        optPlanDto.setOperOrgCode(orgCode);
        optPlanDto.setOperOrgId(orgId);
        optPlanDto.setAuthLevel(authLevel);
        boolean bool = planBatchService.delPlanBatch(optPlanDto);
        if(!bool){
            throw new GuiyuException("删除计划失败");
        }
        return bool;
    }


    @ApiOperation(value="批量暂停计划任务", notes="批量暂停计划任务")
    @Log(info ="批量暂停计划任务")
    @Jurisdiction("taskCenter_phonelist_batchPause")
    @RequestMapping(value = "/suspendPlanBatch", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public boolean suspendPlanBatch(@RequestHeader String userId, @RequestHeader String orgCode,
                                @RequestHeader Integer orgId, @RequestHeader Integer authLevel,
                                @RequestBody OptPlanDto optPlanDto){
        if(null == optPlanDto){
            optPlanDto = new OptPlanDto();
        }
        optPlanDto.setOperUserId(userId);
        optPlanDto.setOperOrgCode(orgCode);
        optPlanDto.setOperOrgId(orgId);
        optPlanDto.setAuthLevel(authLevel);
        logger.info("/dispatch/batch/controller/suspendPlanBatch入参:{}", JsonUtils.bean2Json(optPlanDto));
        boolean bool = planBatchService.suspendPlanBatch(optPlanDto);
        if(!bool){
            throw new GuiyuException("暂停计划失败");
        }
        return bool;
    }

    @ApiOperation(value="批量停止计划任务", notes="批量停止计划任务")
    @Log(info ="批量停止计划任务")
    @Jurisdiction("taskCenter_phonelist_batchStop")
    @RequestMapping(value = "/stopPlanBatch", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public boolean stopPlanBatch(@RequestHeader String userId, @RequestHeader String orgCode,
                             @RequestHeader Integer orgId, @RequestHeader Integer authLevel,
                             @RequestBody OptPlanDto optPlanDto){
        if(null == optPlanDto){
            optPlanDto = new OptPlanDto();
        }
        optPlanDto.setOperUserId(userId);
        optPlanDto.setOperOrgCode(orgCode);
        optPlanDto.setOperOrgId(orgId);
        optPlanDto.setAuthLevel(authLevel);
        logger.info("/dispatch/batch/controller/stopPlanBatch:{}", JsonUtils.bean2Json(optPlanDto));
        boolean bool = planBatchService.stopPlanBatch(optPlanDto);
        if(!bool){
            throw new GuiyuException("批量停止计划任务失败");
        }
        return bool;
    }


    //
    @ApiOperation(value="批量恢复计划任务", notes="恢复计划任务")
    @Log(info ="批量恢复计划任务")
    @Jurisdiction("taskCenter_phonelist_batchRecover")
    @RequestMapping(value = "/recoveryPlanBatch", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public boolean recoveryPlanBatch(@RequestHeader String userId, @RequestHeader String orgCode,
                                 @RequestHeader Integer orgId, @RequestHeader Integer authLevel,
                                 @RequestBody OptPlanDto optPlanDto){
        if(null == optPlanDto){
            optPlanDto = new OptPlanDto();
        }
        optPlanDto.setOperUserId(userId);
        optPlanDto.setOperOrgCode(orgCode);
        optPlanDto.setOperOrgId(orgId);
        optPlanDto.setAuthLevel(authLevel);
        logger.info("/dispatch/batch/controller/recoveryPlanBatch:{}", JsonUtils.bean2Json(optPlanDto));
        boolean bool = planBatchService.recoveryPlanBatch(optPlanDto);
        if(!bool){
            throw new GuiyuException("批量恢复计划任务失败");
        }
        return bool;
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
    @Jurisdiction("taskCenter_phonelist_batchExport")
    @RequestMapping(value = "/exportPlanBatch", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public boolean exportPlanBatch(@RequestHeader String userId, @RequestHeader String orgCode,
                                 @RequestHeader Integer orgId, @RequestHeader Integer authLevel,
                                   @RequestBody OptPlanDto optPlanDto){
        if(null == optPlanDto){
            optPlanDto = new OptPlanDto();
        }
        optPlanDto.setOperUserId(userId);
        optPlanDto.setOperOrgCode(orgCode);
        optPlanDto.setOperOrgId(orgId);
        optPlanDto.setAuthLevel(authLevel);
        logger.info("/dispatch/batch/controller/exportPlanBatch:{}", JsonUtils.bean2Json(optPlanDto));
        boolean bool = planBatchService.exportPlanBatch(optPlanDto);
        if(!bool){
            throw new GuiyuException("批量导出计划任务失败");
        }
        return bool;
    }
}
