package com.guiji.calloutserver.eventbus.handler;

import com.google.common.eventbus.AsyncEventBus;
import com.guiji.callcenter.dao.entity.CallOutPlan;
import com.guiji.calloutserver.eventbus.event.CallResourceReadyEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Auther: 魏驰
 * @Date: 2018/11/2 14:56
 * @Project：guiyu-parent
 * @Description: 用于检查呼叫相关的资源是否齐备，如模板、tts、sellbot等
 */
@Component
public class CallResourceChecker {
    @Autowired
    AsyncEventBus asyncEventBus;

    /**
     * 检查呼叫依赖的各项资源是否齐备，只有完全齐备，才允许进行后面的呼叫
     * @param callOutPlan
     */
    public void checkCallResources(CallOutPlan callOutPlan){
        //启动多个线程，用于检测各个资源的状态
        checkTemp(callOutPlan);
        checkTts(callOutPlan);
        checkSellbot(callOutPlan);

                /*
        根据服务id和线路id到linecount中获取并发数
        将获取的呼叫计划存入数据库，并将状态设置为init
        调度线程处理如下：
        如果发现含有tts，则调用绑定的fsagent下载tts话术录音接口
        如果下载失败，则报警，并跳过该条记录的调度
        下载tts话术录音失败的呼叫计划，只能不断的等待一次次的调度。
        请求sellbot资源
        请求不到则报警，并不断的请求，直到资源可用为止。
        调度完成后，生成呼叫命令送到freeswitch
        在每通呼叫挂断后请求新的计划（请求数=1）
        如果请求不到数据，则处理如下：
        请求失败，则报警，再不断的重试，直到重新获取到数据为止
        请求为空，标志位结束，则退出线程处理
        请求为空，标志位未结束，则报警，再不断重试，直到获取到数据
*/

        asyncEventBus.post(new CallResourceReadyEvent(callOutPlan));
    }

    /**
     * 检查sellbot资源是否就位
     * @param callOutPlan
     */
    private void checkSellbot(CallOutPlan callOutPlan) {
        //TODO:sellbot资源检查
    }

    /**
     * 检查tts是否就位
     * @param callOutPlan
     */
    private void checkTts(CallOutPlan callOutPlan) {
        //TODO:tts资源检查
    }

    /**
     * 检查模板是否就位
     * @param callOutPlan
     */
    private void checkTemp(CallOutPlan callOutPlan) {
        //TODO:模板资源检查
    }
}
