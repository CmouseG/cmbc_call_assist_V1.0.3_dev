package com.guiji.calloutserver.helper;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.Subscribe;
import com.guiji.callcenter.dao.entity.CallOutPlan;
import com.guiji.calloutserver.entity.AIResponse;
import com.guiji.calloutserver.entity.Channel;
import com.guiji.calloutserver.eventbus.event.ChannelHangupEvent;
import com.guiji.calloutserver.eventbus.handler.AfterMediaChecker;
import com.guiji.calloutserver.fs.LocalFsServer;
import com.guiji.calloutserver.config.AiConfig;
import com.guiji.calloutserver.service.ChannelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.*;

/**
 * 用于机器人媒体播放的助手类
 */
@Slf4j
@Service
public class ChannelHelper {
    @Autowired
    ChannelService channelService;

    @Autowired
    AiConfig aiConfig;

    @Autowired
    LocalFsServer localFsServer;

    @Autowired
    AsyncEventBus simpleEventSender;

    @Autowired
    AfterMediaChecker afterMediaChecker;

    ScheduledExecutorService scheduledExecutorService;
    ConcurrentHashMap<String, ScheduledFuture> futureConcurrentHashMap;

    @PostConstruct
    private void init(){
        scheduledExecutorService = Executors.newScheduledThreadPool(2);
        futureConcurrentHashMap = new ConcurrentHashMap<>();
    }



    /**
     * 向FreeSWITCH通道播放机器人说话
     * @param uuid  通道uuid
     * @param mediaFile 媒体文件
     * @param mediaFileDuration 媒体文件时长
     * @param isLock    是否锁定通道
     * @param isEnd     通话是否结束
     */
    private void playFile(String uuid, String mediaFile, Double mediaFileDuration, boolean isLock, boolean isEnd) {
        log.info("需要设置通道媒体文件[{}]的属性，时间为[{}], isLock[{}], isEnd[{}]", mediaFile, mediaFileDuration, isLock, isEnd);
        setChannel(uuid, mediaFileDuration, isLock, isEnd);

        if(isEnd){
            //预约挂断，在指定时长后挂断当前通道
            //在原来文件时长的基础上，增加3秒，防止话没说完，就被挂断
            localFsServer.scheduleHangup(uuid, mediaFileDuration + 1.5);
        }

        Channel channel = channelService.findByUuid(uuid);
        //如果还在播放另一个文件，则停止
        if(mediaFile.equals(channel.getMediaFileName())){
            log.info("文件[{}]已经在播放，暂时不用播放新的文件", mediaFile);
            return;
        }

        localFsServer.playToChannel(uuid, mediaFile);
        channel.setMediaFileName(mediaFile);
        channelService.save(channel);
    }

    public void playAiReponse(AIResponse aiResponse, boolean isLock){
        //TODO: 等待完善

        playFile(aiResponse.getCallId(), aiResponse.getWavFile(), aiResponse.getWavDuration(),  isLock);
    }

    public void playFile(String uuid, String mediaFile, Double mediaFileDuration, boolean isLock) {
        playFile(uuid, mediaFile, mediaFileDuration, isLock, false);
    }

    /**
     *
     * 在播放完媒体后，将当前呼叫转到座席组
     * @param callPlan
     * @param mediaFile
     * @param mediaFileDuration
     * @param agentGroup
     */
    public void playAndTransferToAgentGroup(CallOutPlan callPlan, String mediaFile, Double mediaFileDuration, String agentGroup){
        log.info("需要锁定通道媒体文件[{}]的播放，锁定时间为[{}]", mediaFile, mediaFileDuration);

        String uuid = callPlan.getCallId();

        //转人工播放时间加0.5秒，防止声音未播放完，就转人工
        setChannel(uuid, mediaFileDuration + 0.5, false, () -> localFsServer.transferToAgentGroup(uuid, callPlan.getPhoneNum(), agentGroup));

        Channel channel = channelService.findByUuid(uuid);

        localFsServer.playToChannel(uuid, mediaFile);
        channel.setMediaFileName(mediaFile);
        channelService.save(channel);
    }

    /**
     * 播放机器人说话，并在说完话之后挂断
     * @param uuid
     * @param mediaFile
     * @param mediaFileDuration
     */
    public void playFileToChannelAndHangup(String uuid, String mediaFile, Double mediaFileDuration){
        playFile(uuid, mediaFile, mediaFileDuration, true, true);
    }

    /**
     * 通道是否锁定
     * @param uuid
     * @return
     */
    public boolean isChannelLock(String uuid){
        return channelService.isMediaLock(uuid);
    }

    /**
     * 锁定通道指定时长, 单位为秒
     * @param uuid
     * @param lockTimeLen  单位为秒
     * @param isLock
     */
    private void setChannel(String uuid, Double lockTimeLen, boolean isLock, boolean isEnd){
        final boolean afterTimeout;
        if(isLock){
            afterTimeout = !isLock;
        }else{
            afterTimeout = isLock;
        }

        channelService.updateMediaLock(uuid, isLock);

        if(futureConcurrentHashMap.containsKey(uuid)){
            try{
                log.info("取消之前的定时任务，uuid[{}]", uuid);
                futureConcurrentHashMap.get(uuid).cancel(true);

                afterMediaChecker.removeMediaCheck(uuid);
            }catch (Exception ex){
                log.warn("取消定时任务时出现异常", ex);
            }
        }

        log.info("使用定时服务setChannel,timeLen[{}],isEnd[{}]", lockTimeLen, isEnd);
        ScheduledFuture<?> schedule = scheduledExecutorService.schedule(() -> {
                    if (!isEnd) {
                        log.info("在[{}]秒之后，将通道[{}]媒体文件清理掉，isLock设置为[{}]", lockTimeLen, uuid, afterTimeout);
                        channelService.updateMediaLock(uuid, afterTimeout, null);
                        //simpleEventSender.sendEvent(new AfterMediaEvent(uuid));
                        afterMediaChecker.addAfterMediaCheck(uuid);
                    } else {
                        log.info("播放结束，开始删除callMedia[{}]", uuid);
                        channelService.delete(uuid);
                    }
                },
                lockTimeLen.longValue(), TimeUnit.SECONDS);

        futureConcurrentHashMap.put(uuid, schedule);
    }


    /**
     * 锁定通道指定时长, 单位为秒
     * @param uuid
     * @param lockTimeLen  单位为秒
     */
    private void setChannel(String uuid, Double lockTimeLen, boolean isEnd, AfterLockHandle handler){
        channelService.updateMediaLock(uuid, true);

        log.info("使用定时服务约定执行时间，timeLen[{}],isEnd[{}]", lockTimeLen, isEnd);
        ScheduledFuture<?> schedule = scheduledExecutorService.schedule(() -> {
                    if (!isEnd) {
                        log.info("通道[{}]锁定时间[{}]已完成，解除锁定", uuid, lockTimeLen);
                        channelService.updateMediaLock(uuid, false);
                    } else {
                        log.info("播放结束，开始删除callMedia");
                        channelService.delete(uuid);
                    }

                    if (handler != null) {
                        handler.handle();
                    }
                },
                lockTimeLen.longValue(), TimeUnit.SECONDS);
    }

    @Subscribe
    public void handleHangup(ChannelHangupEvent event){
        log.info("通道挂断，开始清理Channel");
        channelService.delete(event.getUuid());
    }

    interface AfterLockHandle{
        void handle();
    }
}
