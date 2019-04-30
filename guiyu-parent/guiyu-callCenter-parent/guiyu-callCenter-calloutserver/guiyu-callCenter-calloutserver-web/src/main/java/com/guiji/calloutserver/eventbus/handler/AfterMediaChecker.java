package com.guiji.calloutserver.eventbus.handler;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.Subscribe;
import com.guiji.calloutserver.eventbus.event.AsrCustomerEvent;
import com.guiji.calloutserver.eventbus.event.ToAgentEvent;
import com.guiji.calloutserver.config.FsBotConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.*;

/**
 * 该类作用主要是检测客户长时间不说话、或者没有匹配到关键字，导致机器人长时间不说话。
 * 解决方法是启动定时器检测空闲时间，如果空闲时间过长，则自动触发后续播放内容。
 */
@Slf4j
@Service
public class AfterMediaChecker {
    @Autowired
    FsBotConfig fsBotConfig;

    @Autowired
    AsyncEventBus simpleEventSender;

    @Autowired
    AsyncEventBus asyncEventBus;
    //注册这个监听器
    @PostConstruct
    public void register() {
        asyncEventBus.register(this);
    }

    ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
    ConcurrentHashMap<String, ScheduledFuture> futureConcurrentHashMap;

    @Subscribe
    @AllowConcurrentEvents
    public void handleToAgent(ToAgentEvent toAgentEvent){
        String uuid = toAgentEvent.getUuid();
        log.info("收到ToAgentEvent，开始从MediaChecker中移除计时器, uuid[{}]", uuid);
        removeMediaCheck(uuid);
    }

    public AfterMediaChecker(){
        futureConcurrentHashMap = new ConcurrentHashMap<>();
    }

    /**
     * 该方法会在新媒体播放前调用，来清理掉计时器
     * @param uuid
     */
    public void removeMediaCheck(String uuid){
        if(futureConcurrentHashMap.containsKey(uuid)){
            try{
                futureConcurrentHashMap.get(uuid).cancel(true);
                futureConcurrentHashMap.remove(uuid);
            }catch (Exception ex){
                log.warn("在取消定时任务时出现异常", ex);
            }
        }
    }

    /**
     * 该方法会在媒体播放完毕后调用，启动定时器，检测是否存在长时间不播放声音的问题
     * @param uuid
     */
    public void addAfterMediaCheck(String uuid){
        try{
            //定时n秒，如果超时，还是没有任何媒体播放出来，则调用ai接口获取媒体播放
            ScheduledFuture<?> schedule = scheduledExecutorService.schedule(() -> {
                AsrCustomerEvent aliAsrEvent = new AsrCustomerEvent();
                aliAsrEvent.setUuid(uuid);
                aliAsrEvent.setAsrText("");
                aliAsrEvent.setAsrDuration(2000L);
                aliAsrEvent.setFileName("");
                aliAsrEvent.setGenerated(true);
                log.info("媒体在播放完成[{}]秒后，都没有新的媒体播放，所以构建AliAsr事件，产生新的录音", fsBotConfig.getMaxMediaTimeout());
                simpleEventSender.post(aliAsrEvent);
            }, fsBotConfig.getMaxMediaTimeout(), TimeUnit.SECONDS);

            futureConcurrentHashMap.put(uuid, schedule);
        }catch (Exception ex){
            log.warn("在处理呼叫结果时出现异常", ex);
        }
    }
}
