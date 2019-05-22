package com.guiji.dispatch.api;

import com.guiji.component.result.Result;
import com.guiji.dispatch.model.*;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Classname DispatchThirdApi
 * @Description 提供三方api的能力支持
 * @Date 2019/5/21 14:42
 * @Created by qinghua
 */
@FeignClient("GUIYU-DISPATCH-WEB")
public interface DispatchThirdApi {

    /**
     * 添加批次任务
     * @param ro
     * @return
     */
    @PostMapping("/thirdapi/addBatchPlan")
    Result.ReturnData<Boolean> addBatchPlan(@RequestBody DispatchPlanForApiRo ro);

    /**
     * 暂停任务
     * @param ro
     * @return
     */
    @PostMapping("/thirdapi/suspendPlanThirdBatch")
    Result.ReturnData<Boolean> suspendPlanThirdBatch(@RequestBody OperDispatchRo ro);

    /**
     * 停止任务
     * @param ro
     * @return
     */
    @PostMapping("/thirdapi/stopPlanThirdBatch")
    Result.ReturnData<Boolean> stopPlanThirdBatch(@RequestBody OperDispatchRo ro);

    /**
     * 恢复任务
     * @param ro
     * @return
     */
    @PostMapping("/thirdapi/recoveryPlanThirdBatch")
    Result.ReturnData<Boolean> recoveryPlanThirdBatch(@RequestBody OperDispatchRo ro);

    /**
     * 获取批次任务详情
     * @param ro
     * @return
     */
    @PostMapping("/thirdapi/getPlanThirdBatchDetail")
    Result.ReturnData<DispatchPlanBatchAddVo> getPlanThirdBatchDetail(@RequestBody OperDispatchRo ro);

    /**
     * 获取批次拨打结果
     * @param operDispatchRo
     * @return
     */
    @PostMapping("/thirdApi/getPlanThirdBatchDial")
    Result.ReturnData<IPlanThirdBatchDialVo> getPlanThirdBatchDial(@RequestBody OperDispatchRo operDispatchRo);

    /**
     * 获取批次的号码列表
     * @param queryPlanThirdRo
     * @return
     */
    @PostMapping("/thirdApi/queryPlanThirdBatchPage")
    public Result.ReturnData<IPlanThirdBatchPhoneVo> queryPlanThirdBatchPage(@RequestBody QueryPlanThirdRo queryPlanThirdRo);

}
