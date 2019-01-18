//package com.guiji.api.controller;
//
//import com.guiji.api.entity.CallOutPlanVO;
//import com.guiji.api.entity.CallRecord;
//import com.guiji.auth.api.IAuth;
//import com.guiji.ccmanager.api.ICallPlanDetail;
//import com.guiji.ccmanager.vo.CallOutDetailVO;
//import com.guiji.ccmanager.vo.CallOutPlan4ListSelect;
//import com.guiji.ccmanager.vo.CallPlanDetailRecordVO;
//import com.guiji.ccmanager.vo.CallRecordReq;
//import com.guiji.common.model.Page;
//import com.guiji.component.result.Result;
//import com.guiji.user.dao.entity.SysUser;
//import io.swagger.annotations.ApiImplicitParam;
//import io.swagger.annotations.ApiImplicitParams;
//import io.swagger.annotations.ApiOperation;
//import org.apache.commons.lang.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestHeader;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//@RestController
//public class CallRecordController {
//
//    @Autowired
//    IAuth iAuth;
//    @Autowired
//    ICallPlanDetail iCallPlanDetail;
//
//
//    @ApiOperation(value = "获取通话记录标签")
//    @GetMapping("5c2f31a9754c7")
//    public Result.ReturnData<String[]> getCallIntent(@RequestHeader Long userId) {
//        Result.ReturnData<SysUser> result = iAuth.getUserById(userId);
//        String intent = result.getBody().getIntenLabel();
//        if (StringUtils.isNotBlank(intent)) {
//            String[] arr = intent.split(",");
//            return Result.ok(arr);
//        } else {
//            return Result.ok();
//        }
//    }
//
//    @ApiOperation(value = "获取单条通话记录详情")
//    @GetMapping("5c2f31180997a")
//    public Result.ReturnData<CallOutPlanVO> getCallDetail(String id) {
//        Result.ReturnData<CallPlanDetailRecordVO> result = iCallPlanDetail.getCallDetail(id);
//        CallPlanDetailRecordVO callOutPlanVO = result.getBody();
//        if (callOutPlanVO != null) {
//            com.guiji.api.entity.CallOutPlanVO resCallOutPlan = new com.guiji.api.entity.CallOutPlanVO();
//            if (callOutPlanVO.getCallId() != null)
//                resCallOutPlan.setId(callOutPlanVO.getCallId().toString());
//            if (callOutPlanVO.getCallStartTime() != null)
//                resCallOutPlan.setStarttime(callOutPlanVO.getCallStartTime());
//            if (callOutPlanVO.getAccurateIntent​() != null)
//                resCallOutPlan.setLabel(callOutPlanVO.getAccurateIntent​());
//            if (callOutPlanVO.getDuration() != null)
//                resCallOutPlan.setCalltime(callOutPlanVO.getDuration());
//            if (callOutPlanVO.getRemarks() != null)
//                resCallOutPlan.setRemarks(callOutPlanVO.getRemarks());
//            if (callOutPlanVO.getPhoneNum() != null)
//                resCallOutPlan.setPhone(callOutPlanVO.getPhoneNum());
//            if (callOutPlanVO.getRecordUrl() != null)
//                resCallOutPlan.setVoicefile(callOutPlanVO.getRecordUrl());
//
//            List<CallOutDetailVO> list = callOutPlanVO.getDetailList();
//            if (list != null && list.size() > 0) {
//                List<com.guiji.api.entity.CallOutDetailVO> resList = new ArrayList<com.guiji.api.entity.CallOutDetailVO>();
//                for (CallOutDetailVO callOutDetailVO : list) {
//                    com.guiji.api.entity.CallOutDetailVO resCallOutDetailVO = new com.guiji.api.entity.CallOutDetailVO();
//                    resCallOutDetailVO.setCallid(callOutDetailVO.getCallId().toString());
//                    resCallOutDetailVO.setId(callOutDetailVO.getCallDetailId());
//                    if (callOutDetailVO.getBotAnswerText() != null)
//                        resCallOutDetailVO.setAnswer(callOutDetailVO.getBotAnswerText());
//                    if (callOutDetailVO.getCustomerSayText() != null)
//                        resCallOutDetailVO.setContent(callOutDetailVO.getCustomerSayText());
//                    if (callOutDetailVO.getCustomerRecordUrl() != null)
//                        resCallOutDetailVO.setFile(callOutDetailVO.getCustomerRecordUrl());
//                    resList.add(resCallOutDetailVO);
//                }
//                resCallOutPlan.setVoiceInfo(resList);
//            }
//            return Result.ok(resCallOutPlan);
//        }
//        return Result.ok();
//    }
//
//
//    @ApiOperation(value = "获取通话记录列表")
//    @ApiImplicitParams({
//        @ApiImplicitParam(name = "durationMin", value = "通话时长，最小值，单位秒", dataType = "String", paramType = "query"),
//        @ApiImplicitParam(name = "durationMax", value = "通话时长，最大值，单位秒", dataType = "String", paramType = "query"
//        )})
//    @GetMapping("5c2f31ee71a53")
//    public Result.ReturnData<Page<Map>> getCallRecordList(String size, String page, String durationMin,String durationMax, String label,
//                                                          @RequestHeader Long userId, @RequestHeader Boolean isSuperAdmin, @RequestHeader String orgCode) {
//
//        if(StringUtils.isBlank(size)){
//            size = "50";
//        }
//        if(StringUtils.isBlank(page)){
//            page = "1";
//        }
//        CallRecordReq callRecordReq = new CallRecordReq();
//        callRecordReq.setAccurateIntent(label);
//        callRecordReq.setDurationMax(durationMax);
//        callRecordReq.setDurationMin(durationMin);
//        callRecordReq.setOrgCode(orgCode);
//        callRecordReq.setPageNo(Integer.valueOf(page));
//        callRecordReq.setPageSize(Integer.valueOf(size));
//        callRecordReq.setSuperAdmin(isSuperAdmin);
//        callRecordReq.setUserId(userId);
//        Result.ReturnData<Page<Map>> result = iCallPlanDetail.getCallRecordList(callRecordReq);
//
//        return result;
//    }
//
//}
