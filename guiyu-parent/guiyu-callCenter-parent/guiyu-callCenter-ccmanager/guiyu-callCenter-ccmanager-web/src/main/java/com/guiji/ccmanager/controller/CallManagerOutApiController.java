//package com.guiji.ccmanager.controller;
//
//import com.guiji.callcenter.dao.entity.LineInfo;
//import com.guiji.ccmanager.api.ICallManagerOut;
//import com.guiji.ccmanager.entity.LineConcurrent;
//import com.guiji.ccmanager.service.CallManagerOutService;
//import com.guiji.ccmanager.service.LineInfoService;
//import com.guiji.component.result.Result;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.client.discovery.DiscoveryClient;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @Auther: 黎阳
// * @Date: 2018/10/30 0030 09:37
// * @Description:
// */
//@RestController
//public class CallManagerOutApiController implements ICallManagerOut {
//
//    private final Logger log = LoggerFactory.getLogger(CallManagerOutApiController.class);
//
//
//    @Autowired
//    private CallManagerOutService callManagerOutService;
//    @Autowired
//    DiscoveryClient discoveryClient;
//
//
//
///*    @Override
//    @GetMapping(value="out/getLineInfos")
//    public Result.ReturnData<List<LineConcurrent>> getLineInfos(String customerId){
//
//        log.info("get request getLineInfos，customerId[{}]", customerId);
//
//        if(StringUtils.isBlank(customerId)){
//            return Result.error(Constant.ERROR_PARAM);
//        }
//        List<LineInfo> lineInfos = lineInfoService.outLineinfos(customerId);
//        List<LineConcurrent> resList = getLineConcurrent(lineInfos);
//        log.info("response success getLineInfos，customerId[{}]", customerId);
//        return Result.ok(resList);
//    }*/
//
//    private List<LineConcurrent> getLineConcurrent(List<LineInfo> lineInfos){
//        List<LineConcurrent> resList = new ArrayList<LineConcurrent>();
//        if(lineInfos!=null && lineInfos.size()>0){
//            for(LineInfo lineInfo:lineInfos){
//                LineConcurrent target = new LineConcurrent();
//                target.setLineId(String.valueOf(lineInfo.getLineId()));
//                target.setConcurrent(lineInfo.getMaxConcurrentCalls());
//                target.setLineName(lineInfo.getLineName());
//                resList.add(target);
//            }
//        }
//        return resList;
//    }
//
//
///*    @Override
//    @GetMapping(value="out/getLineNameAndCount")
//    public Result.ReturnData<List<LineConcurrent>> getLineNameAndCount(@RequestParam(value="customerId", required = true) String customerId,
//                                                                       @RequestParam(value="isSuperAdmin", required = true) Boolean isSuperAdmin){
//
//        log.info("get request getLineInfos，customerId[{}]，isSuperAdmin[{}]", customerId, isSuperAdmin);
//
//        List<LineInfo> lineInfos = lineInfoService.outLineinfos(isSuperAdmin ? null:customerId);
//        List<LineConcurrent> resList = getLineConcurrent(lineInfos);
//
//        log.info("response success getLineInfos，customerId[{}]，isSuperAdmin[{}]", customerId, isSuperAdmin);
//        return Result.ok(resList);
//    }*/
//
//
///*    @Override
//    @ApiOperation(value = "获取callId获取通话记录")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "callId", value = "call_out_plan的id", dataType = "String", paramType = "query", required = true)
//    })
//    @GetMapping(value="getCallRecordById")
//    public Result.ReturnData<CallOutPlan> getCallRecordById(String callId ){
//
//        com.guiji.callcenter.dao.entity.CallOutPlan callOutPlan = callManagerOutService.getCallRecordById(new BigInteger(callId));
//        if(callOutPlan!=null){
//            CallOutPlan CallOutPlanApi = new CallOutPlan();
//            BeanUtil.copyProperties(callOutPlan,CallOutPlanApi);
//            return Result.ok(CallOutPlanApi);
//        }
//        return Result.ok();
//    }*/
//
///*    @Override
//    public Result.ReturnData<TempIsOkResult> isTempOk(@RequestBody List<String> tempIdList) {
//        log.info("收到isTempOk请求,tempIdList[{}]",tempIdList);
//        TempIsOkResult tempIsOkResult = callManagerOutService.isTempOk(tempIdList);
//        log.info("isTempOk请求, 返回结果",tempIsOkResult);
//        return Result.ok(tempIsOkResult);
//    }*/
//}
