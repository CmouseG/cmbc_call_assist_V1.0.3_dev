package com.guiji.calloutserver.controller;

import com.google.common.eventbus.AsyncEventBus;
import com.guiji.callcenter.dao.entity.LineCount;
import com.guiji.calloutserver.api.ICallPlan;
import com.guiji.calloutserver.entity.CCException;
import com.guiji.calloutserver.eventbus.event.StartCallPlanEvent;
import com.guiji.calloutserver.eventbus.handler.CallResourceChecker;
import com.guiji.calloutserver.manager.EurekaManager;
import com.guiji.calloutserver.manager.FsAgentManager;
import com.guiji.calloutserver.service.CallStateService;
import com.guiji.calloutserver.service.LineCountService;
import com.guiji.component.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class CallPlanController implements ICallPlan {

    @Autowired
    AsyncEventBus asyncEventBus;

    @Autowired
    LineCountService lineCountService;

    @Autowired
    EurekaManager eurekaManager;

    @Autowired
    FsAgentManager fsAgentManager;

    @Override
    public Result.ReturnData startCallPlan(String customerId, String tempId, Integer lineId) {
        log.info("收到启动呼叫计划请求，customerId[{}], tempId[{}],lineId[{}]", customerId, tempId, lineId);

        List<LineCount> lineCountList = lineCountService.findByInstanceIdAndLineId(eurekaManager.getInstanceId(), lineId);
        if(lineCountList==null || lineCountList.size()==0){
            //线路不存在
            //TODO: 报警，线路不存在
            log.warn("启动呼叫计划失败，线路不存在[{}]", lineId);
            return Result.error(CCException.LINE_NOT_EXIST);
        }

        if(lineCountList.size()>1){
            //TODO: 报警
            log.warn("启动计划失败，外呼服务[{}]的线路[{}]存在重复记录", eurekaManager.getInstanceId(), lineId);
            return Result.error(CCException.LINE_DUPLICATE);
        }

        log.info("开始检查线路并发数");
        LineCount currentLine = lineCountList.get(0);
        if(currentLine.getMaxConcurrentCalls()<=0){
            log.warn("启动计划失败，线路没有并发数[{}]", lineId);
            return Result.error(CCException.LINE_NO_CONCURRENTS);
        }else{
            log.info("线路并发数检查通过，数量为[{}]", currentLine.getMaxConcurrentCalls());
        }

        log.info("开始检查线路空闲并发数是否够用");
        //判断线路空闲并发数是否够用
        if(currentLine.getUsedConcurrentCalls()!=null && currentLine.getUsedConcurrentCalls()>0){
            //TODO: 报警
            log.warn("启动计划失败，线路正在使用中[{}]", lineId);
            return Result.error(CCException.LINE_BUSY);
        }else{
            log.info("线路状态检查通过，处于空闲状态");
        }

        log.info("开始检查模板[{}]是否存在", tempId);
       try{
            Result.ReturnData<Boolean> result =  fsAgentManager.istempexist(tempId);
            if(!result.getBody()){
                log.warn("启动呼叫计划失败，模板不存在[{}]", tempId);
                return Result.error(CCException.TEMP_NOTEXISIT);
            }else{
                log.info("模板检查通过，返回结果为[{}]", result.getBody());
            }
        }catch (Exception e){
            log.warn("启动呼叫计划失败，出现异常,模板不存在[{}]", tempId);
            return Result.error(CCException.TEMP_NOTEXISIT);
        }

        log.info("开始获取模板录音时长[{}]", tempId);
        try {
            Map<String, Double> map = fsAgentManager.getwavlength(tempId);
            if(map==null || map.size()==0){
                log.warn("启动呼叫计划失败，录音不存在，下载录音文件时长失败[{}]", tempId);
                return Result.error(CCException.GET_WAV_LEN_ERROR);
            }else{
                log.info("模板录音获取成功，获取的数量为[{}]", map.size());
            }
        }catch (Exception e){
            log.warn("启动呼叫计划失败，下载录音文件时长失败[{}]", tempId);
            return Result.error(CCException.GET_WAV_LEN_ERROR);
        }

        log.info("构建StartCallPlanEvent事件，准备发起呼叫");
        asyncEventBus.post(new StartCallPlanEvent(Integer.valueOf(customerId), tempId, currentLine));

        return Result.ok();
    }
}