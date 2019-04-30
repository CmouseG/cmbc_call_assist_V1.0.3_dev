package com.guiji.web.controller;

import com.guiji.callcenter.dao.entity.CallOutDetail;
import com.guiji.service.CallOutDetailService;
import com.guiji.service.CallOutPlanService;
import com.guiji.web.response.ApiResponse;
import com.guiji.web.response.QueryRecordInDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/rs")
public class CallDetailResource {
    @Autowired
    CallOutDetailService callOutDetailService;
    @Autowired
    CallOutPlanService callOutPlanService;

    /**
     * 根据callrecordId查询通话详情
     *
     * @return
     */
   /* @RequestMapping(path = "/calldetail", method = RequestMethod.GET)
    public ApiResponse queryQueues(@RequestParam("callRecordId") String callRecordId) {
        log.info("收到根据callrecordId查询通话详情请求callRecordId:[{}]",callRecordId);
        ApiResponse apiResponse = new ApiResponse();
        try {
            QueryRecordInDetail record = callOutPlanService.queryCallrecord(callRecordId);
            apiResponse.setData(record);
            List<CallOutDetail> list = callOutDetailService.findByCallPlanId(callRecordId);
            apiResponse.setDatas((List<Object>) (Object) list);
            apiResponse.setResult(true);
            apiResponse.setCode(0);
        } catch (Exception ex) {
            log.warn("根据callrecordId查询通话详情请求失败", ex);
            apiResponse.setMsg(ex.getMessage());
            apiResponse.setCode(1);
        }
        log.info("获取根据callrecordId查询通话详情返回结果[{}]", apiResponse);
        return apiResponse;
    }*/
}
