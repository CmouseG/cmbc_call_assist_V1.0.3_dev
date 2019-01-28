package com.guiji.web.controller;


import com.guiji.fs.FsManager;
import com.guiji.web.request.TierInfo;
import com.guiji.web.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
public class TierResource {
    @Autowired
    FsManager fsManager;

    @RequestMapping(path = "/tiers", method = RequestMethod.POST)
    public ApiResponse addTier(@RequestBody TierInfo TierInfo){
        log.info("收到创建队列绑定请求[{}]", TierInfo);
        ApiResponse apiResponse = new ApiResponse();

        try{
            Boolean result = fsManager.addTier(TierInfo);
            apiResponse.setResult(result);
        }catch (Exception ex){
            apiResponse.setResult(false);
            apiResponse.setMsg(ex.getMessage());
        }

        log.info("创建队列返回结果[{}]", apiResponse);
        return apiResponse;
    }

    @RequestMapping(path = "/tiers/{tierId}", method = RequestMethod.PUT)
    public ApiResponse updateTier(@PathVariable String tierId, @RequestBody TierInfo request){
        log.info("收到更新队列绑定请求[{}]", request);
        ApiResponse apiResponse = new ApiResponse();

        log.info("更新队列返回结果[{}]", apiResponse);
        return apiResponse;
    }

    @RequestMapping(path = "/tiers/{tierId}", method = RequestMethod.GET)
    public TierInfo getTier(@PathVariable String tierId){
        log.info("收到获取队列绑定请求[{}]", tierId);
        TierInfo tierInfo = new TierInfo();

        log.info("获取队列返回结果[{}]", tierInfo);
        return tierInfo;
    }

    @RequestMapping(path = "/tiers", method = RequestMethod.GET)
    public ApiResponse getTier(){
        log.info("收到获取所有队列绑定请求");
        ApiResponse apiResponse = new ApiResponse();

        try{
            List<TierInfo> tierInfoList = fsManager.getAllTiers();
            apiResponse.setDatas((List<Object>)(Object)tierInfoList);
            apiResponse.setResult(true);
        }catch (Exception ex){
            apiResponse.setMsg(ex.getMessage());
            log.warn("获取所有队列绑定异常", ex);
        }

        log.info("获取所有队列绑定返回结果[{}]", apiResponse);
        return apiResponse;
    }

    @RequestMapping(path = "/tiers/{queueId}/{agentId}", method = RequestMethod.DELETE)
    public ApiResponse deleteTier(@PathVariable String queueId, @PathVariable String agentId){
        log.info("收到删除队列绑定请求[{}][{}]", queueId, agentId);
        ApiResponse apiResponse = new ApiResponse();

        try{
            TierInfo tierInfo = new TierInfo(queueId, agentId);
            boolean result = fsManager.deleteTier(tierInfo);
            apiResponse.setResult(result);
        }catch (Exception ex){
            apiResponse.setResult(false);
            apiResponse.setMsg(ex.getMessage());
            log.warn("删除队列绑定出现异常," + queueId + ":" + agentId, ex);
        }

        log.info("删除队列返回结果[{}]", apiResponse);
        return apiResponse;
    }
}
