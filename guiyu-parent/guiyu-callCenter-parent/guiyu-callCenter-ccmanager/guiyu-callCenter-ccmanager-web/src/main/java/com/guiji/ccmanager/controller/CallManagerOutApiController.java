package com.guiji.ccmanager.controller;

import com.guiji.ccmanager.entity.CallOutPlan;
import com.guiji.callcenter.dao.entity.LineInfo;
import com.guiji.ccmanager.api.ICallManagerOut;
import com.guiji.ccmanager.constant.Constant;
import com.guiji.ccmanager.entity.LineConcurrent;
import com.guiji.ccmanager.service.CallManagerOutService;
import com.guiji.ccmanager.service.LineInfoService;
import com.guiji.common.model.Page;
import com.guiji.component.result.Result;
import com.guiji.utils.BeanUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: 黎阳
 * @Date: 2018/10/30 0030 09:37
 * @Description:
 */
@RestController
public class CallManagerOutApiController implements ICallManagerOut {

    private final Logger log = LoggerFactory.getLogger(CallManagerOutApiController.class);

    @Autowired
    private LineInfoService lineInfoService;

    @Autowired
    private CallManagerOutService callManagerOutService;

    @Override
    public Result.ReturnData<List<LineConcurrent>> getLineInfos(String customerId){

        log.info("get request getLineInfos，customerId[{}]", customerId);

        if(StringUtils.isBlank(customerId)){
            return Result.error(Constant.ERROR_PARAM);
        }
        List<LineInfo> lineInfos = lineInfoService.outLineinfos(customerId);
        List<LineConcurrent> resList = getLineConcurrent(lineInfos);
        log.info("response success getLineInfos，customerId[{}]", customerId);
        return Result.ok(resList);
    }

    private List<LineConcurrent> getLineConcurrent(List<LineInfo> lineInfos){
        List<LineConcurrent> resList = new ArrayList<LineConcurrent>();
        if(lineInfos!=null && lineInfos.size()>0){
            for(LineInfo lineInfo:lineInfos){
                LineConcurrent target = new LineConcurrent();
                target.setLineId(String.valueOf(lineInfo.getLineId()));
                target.setConcurrent(lineInfo.getMaxConcurrentCalls());
                target.setLineName(lineInfo.getLineName());
                resList.add(target);
            }
        }
        return resList;
    }


    @Override
    public Result.ReturnData<List<LineConcurrent>> getLineNameAndCount(@RequestParam(value="customerId", required = true) String customerId,
                                                                       @RequestParam(value="isSuperAdmin", required = true) Boolean isSuperAdmin){

        log.info("get request getLineInfos，customerId[{}]，isSuperAdmin[{}]", customerId, isSuperAdmin);

        List<LineInfo> lineInfos = lineInfoService.outLineinfos(isSuperAdmin ? null:customerId);
        List<LineConcurrent> resList = getLineConcurrent(lineInfos);

        log.info("response success getLineInfos，customerId[{}]，isSuperAdmin[{}]", customerId, isSuperAdmin);
        return Result.ok(resList);
    }

    @Override
    public Result.ReturnData<Boolean> startCallPlan(String customerId, String tempId, String lineId) {

        log.info("get request startCallPlan，customerId[{}]，tempId[{}]，lineId[{}]", customerId, tempId, lineId);

        if(StringUtils.isBlank(customerId) || StringUtils.isBlank(tempId) || StringUtils.isBlank(lineId) ){
            return Result.error(Constant.ERROR_PARAM);
        }

        Result.ReturnData<Object>  result = callManagerOutService.startcallplan(customerId,tempId,lineId);
        if(result.getCode().equals(Constant.SUCCESS_COMMON)){
            log.info("response success startCallPlan，customerId[{}]，tempId[{}]，lineId[{}]", customerId, tempId, lineId);
            return Result.ok(true);
        }else{
            log.warn("callManagerOutService.startcallplan return code:"+result.getCode());
            return Result.error(result.getCode());
        }

    }

    @Override
    public Result.ReturnData<CallOutPlan> getCallRecordById(String callId ){

        com.guiji.callcenter.dao.entity.CallOutPlan callOutPlan = callManagerOutService.getCallRecordById(callId);
        if(callOutPlan!=null){
            CallOutPlan CallOutPlanApi = new CallOutPlan();
            BeanUtil.copyProperties(callOutPlan,CallOutPlanApi);
            return Result.ok(CallOutPlanApi);
        }
        return Result.ok();
    }

}
