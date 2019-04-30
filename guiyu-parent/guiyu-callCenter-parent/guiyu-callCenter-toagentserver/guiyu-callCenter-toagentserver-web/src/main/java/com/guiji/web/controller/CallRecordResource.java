package com.guiji.web.controller;

import com.guiji.callcenter.dao.entity.Agent;
import com.guiji.callcenter.dao.entity.CallOutRecord;
import com.guiji.component.result.Result;
import com.guiji.config.ErrorConstant;
import com.guiji.entity.CustomSessionVar;
import com.guiji.service.CallOutPlanService;
import com.guiji.service.CallOutRecordService;
import com.guiji.web.request.UpdateLabelRequest;
import com.guiji.web.response.QueryQueueCalls;
import com.guiji.web.response.QueryRecordInDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigInteger;

@Slf4j
@RestController
@RequestMapping(value = "/rs")
public class CallRecordResource {
    @Autowired
    CallOutPlanService callOutPlanService;
    @Autowired
    CallOutRecordService callOutRecordService;

    /**
     * 获取指定通话记录信息
     * @param recordId
     * @return
     */
/*
    @RequestMapping(path = "/callrecords/{recordId}", method = RequestMethod.GET)
    public Result.ReturnData<CallOutRecord> getCallRecord(@PathVariable String recordId) {
        log.info("收到获取指定通话记录请求[{}]", recordId);
        CallOutRecord callOutRecord =callOutRecordService.findByRecordId(recordId);
        if(callOutRecord!=null){
            return Result.ok(callOutRecord);
        }
       return Result.error(ErrorConstant.ERROR_NO_RECORD_BY_RECORDID);
    }
*/

    /**
     * 获取座席组通话信息
     *
     * @return
     */
    @RequestMapping(path = "/queuecalls/{queueId}", method = RequestMethod.GET)
    public Result.ReturnData<QueryQueueCalls> queueCalls(@PathVariable String queueId, HttpSession session,
                                                         @RequestHeader Integer orgId, @RequestHeader Integer authLevel) {
        log.info("收到获取座席组通话信息的请求queueId[{}]",queueId);
        Agent agent = (Agent) session.getAttribute(CustomSessionVar.LOGIN_USER);
        QueryQueueCalls queryQueueCalls = callOutPlanService.queueCalls(queueId,agent,orgId,authLevel);
        return Result.ok(queryQueueCalls);
    }



    /**
     * 修改通话记录的意向标签
     *
     * @param request
     * @return
     */
    @RequestMapping(path = "/updatelabel", method = RequestMethod.PUT)
    public Result.ReturnData updateLabel(@RequestBody UpdateLabelRequest request,
                                         @RequestHeader Integer orgId, @RequestHeader Integer authLevel) {
        log.info("收到修改通话记录的意向标签请求UpdateLabelRequest:[{}]", request.toString());
        callOutPlanService.updateLabel(request,orgId,authLevel);
        return Result.ok();
    }


    /**
     * 根据token获取正在通话的通话信息
     *
     * @return
     */
    @RequestMapping(path = "/phoneinfo/{mobile}", method = RequestMethod.GET)
    public  Result.ReturnData<QueryRecordInDetail> getCallrecord(@PathVariable String mobile,
                                     @RequestHeader Integer orgId, @RequestHeader Integer authLevel) {
        log.info("开始号码的实时通话信息[{}]",mobile);
        QueryRecordInDetail queryQueueCalls = callOutPlanService.getRealCallInfo(mobile,authLevel,orgId);
        return Result.ok(queryQueueCalls);
    }


}
