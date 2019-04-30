package com.guiji.wxapi.controller;

import com.guiji.auth.api.IAuth;
import com.guiji.ccmanager.api.ICallPlanDetail;
import com.guiji.ccmanager.api.ILabel;
import com.guiji.ccmanager.vo.CallOutDetailVO;
import com.guiji.ccmanager.vo.CallPlanDetailRecordVO;
import com.guiji.ccmanager.vo.CallRecordReq;
import com.guiji.component.result.Result;
import com.guiji.wxapi.constant.Constant;
import com.guiji.wxapi.entity.CallData;
import com.guiji.wxapi.entity.CallOutPlanVO;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class CallRecordController {

    @Autowired
    IAuth iAuth;
    @Autowired
    ICallPlanDetail iCallPlanDetail;
    @Autowired
    ILabel iLabel;

    private final Logger logger = LoggerFactory.getLogger(CallRecordController.class);

    @ApiOperation(value = "获取通话记录标签")
    @GetMapping("getCallLabel")
    public Result.ReturnData<List<String>> getCallIntent(@RequestHeader Long userId, @RequestHeader String orgCode,
                                                         @RequestHeader Integer authLevel) {
        return iLabel.getAllLabelOneMonth(orgCode,userId, authLevel);
    }

    @ApiOperation(value = "获取单条通话记录详情")
    @GetMapping("getCallDetailById")
    public Result.ReturnData<CallOutPlanVO> getCallDetail(String id,Integer orgId) {
        Result.ReturnData<CallPlanDetailRecordVO> result = iCallPlanDetail.getCallDetailApi(id,orgId);
        CallPlanDetailRecordVO callOutPlanVO = result.getBody();
        if (callOutPlanVO != null) {
            CallOutPlanVO resCallOutPlan = new CallOutPlanVO();
            CallData callData = new CallData();
            if (callOutPlanVO.getCallId() != null)
                callData.setId(callOutPlanVO.getCallId().toString());
            if (callOutPlanVO.getCallStartTime() != null)
                callData.setStarttime(callOutPlanVO.getCallStartTime());
            if (callOutPlanVO.getAccurateIntent​() != null)
                callData.setLabel(callOutPlanVO.getAccurateIntent​());
            if (callOutPlanVO.getDuration() != null)
                callData.setCalltime(callOutPlanVO.getDuration());
            if (callOutPlanVO.getReason() != null)
                callData.setRemarks(callOutPlanVO.getReason());
            if (callOutPlanVO.getPhoneNum() != null)
                callData.setPhone(callOutPlanVO.getPhoneNum());
            if (callOutPlanVO.getRecordUrl() != null)
                callData.setVoicefile(callOutPlanVO.getRecordUrl());
            if (callOutPlanVO.getAttach() != null)
                callData.setAttach(callOutPlanVO.getAttach());
            resCallOutPlan.setCallData(callData);

            List<CallOutDetailVO> list = callOutPlanVO.getDetailList();
            if (list != null && list.size() > 0) {
                List<com.guiji.wxapi.entity.CallOutDetailVO> resList = new ArrayList<com.guiji.wxapi.entity.CallOutDetailVO>();
                for (CallOutDetailVO callOutDetailVO : list) {
                    com.guiji.wxapi.entity.CallOutDetailVO resCallOutDetailVO = new com.guiji.wxapi.entity.CallOutDetailVO();
                    resCallOutDetailVO.setCallid(callOutDetailVO.getCallId().toString());
                    resCallOutDetailVO.setId(callOutDetailVO.getCallDetailId());
                    if (callOutDetailVO.getBotAnswerText() != null) {
                        resCallOutDetailVO.setAnswer(callOutDetailVO.getBotAnswerText());
                    } else if (callOutDetailVO.getCustomerSayText() != null) {
                        resCallOutDetailVO.setContent(callOutDetailVO.getCustomerSayText());
                    } else if (callOutDetailVO.getAgentAnswerText() != null) {
                        resCallOutDetailVO.setAnswer(StringUtils.isBlank(callOutDetailVO.getAgentAnswerText()) ? "无应答":callOutDetailVO.getAgentAnswerText());
                    }
                    if (callOutDetailVO.getCustomerRecordUrl() != null){
                        resCallOutDetailVO.setFile(callOutDetailVO.getCustomerRecordUrl());
                    }else if (callOutDetailVO.getAgentRecordUrl() != null){
                        resCallOutDetailVO.setFile(callOutDetailVO.getAgentRecordUrl());
                    }
                    if (callOutDetailVO.getKeywords() != null)
                        resCallOutDetailVO.setKeywords(callOutDetailVO.getKeywords());
                    if (callOutDetailVO.getWordSegmentResult() != null)
                        resCallOutDetailVO.setWordSegmentResult(callOutDetailVO.getWordSegmentResult());
                    resList.add(resCallOutDetailVO);
                }
                resCallOutPlan.setVoiceInfo(resList);
            }
            return Result.ok(resCallOutPlan);
        }
        return Result.ok();
    }


    @ApiOperation(value = "获取通话记录列表")
    @GetMapping("getCallRecordList")
    public Result.ReturnData<Map> getCallRecordList(String size, String page, String time, String label, @RequestHeader Integer isDesensitization,
                                                    @RequestHeader Long userId, @RequestHeader Boolean isSuperAdmin, @RequestHeader String orgCode,
                                                    @RequestHeader Integer authLevel, @RequestHeader Integer orgId) {

        if(StringUtils.isBlank(label)){
            return Result.error(Constant.ERROR_PARAM);
        }
        if(StringUtils.isBlank(size)){
            size = "50";
        }
        if(StringUtils.isBlank(page)){
            page = "1";
        }
        CallRecordReq callRecordReq = new CallRecordReq();
        callRecordReq.setAccurateIntent(label);
        if(StringUtils.isNotBlank(time)) {
            callRecordReq.setTime(Integer.valueOf(time));
        }
        callRecordReq.setOrgCode(orgCode);
        callRecordReq.setPageNo(Integer.valueOf(page));
        callRecordReq.setPageSize(Integer.valueOf(size));
        callRecordReq.setSuperAdmin(isSuperAdmin);
        callRecordReq.setUserId(userId);
        callRecordReq.setIsDesensitization(isDesensitization);
        callRecordReq.setAuthLevel(authLevel);
        callRecordReq.setOrgId(orgId);

        Result.ReturnData<Map> result = iCallPlanDetail.getCallRecordList(callRecordReq);

        return result;
    }

    @ApiOperation(value = "号码搜索")
    @GetMapping("getCallRecordListByPhone")
    public Result.ReturnData<List> getCallRecordListByPhone(String phone,@RequestHeader Integer authLevel,@RequestHeader Integer orgId){

        if(StringUtils.isBlank(phone)){
            return Result.error(Constant.ERROR_PARAM);
        }

        return iCallPlanDetail.getCallRecordListByPhone(phone,authLevel,orgId);
    }
}
