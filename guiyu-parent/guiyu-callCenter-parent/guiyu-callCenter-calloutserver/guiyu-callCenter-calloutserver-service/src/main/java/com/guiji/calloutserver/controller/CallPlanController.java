package com.guiji.calloutserver.controller;

import com.guiji.calloutserver.api.ICallPlanApi;
import com.guiji.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class CallPlanController implements ICallPlanApi {
    @GetMapping("/startcallplan")
    @Override
    public Result.ReturnData startCallPlan(String customerId, String tempId, Integer lineId) {
        log.debug("收到启动呼叫计划请求，customerId[{}], tempId[{}],lineId[{}]", customerId, tempId, lineId);
        return Result.ok();
    }
}