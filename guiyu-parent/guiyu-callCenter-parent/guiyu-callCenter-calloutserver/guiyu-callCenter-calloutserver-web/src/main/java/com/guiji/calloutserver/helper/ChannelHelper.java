package com.guiji.calloutserver.helper;

import com.guiji.calloutserver.constant.Constant;
import com.guiji.calloutserver.entity.AIResponse;
import com.guiji.calloutserver.entity.Channel;
import com.guiji.calloutserver.eventbus.handler.AfterMediaChecker;
import com.guiji.calloutserver.fs.LocalFsServer;
import com.guiji.calloutserver.manager.ToAgentManager;
import com.guiji.calloutserver.service.ChannelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Date;
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
    RobotNextHelper robotNextHelper;
    @Autowired
    ToAgentManager toAgentManager;

    @Autowired
    LocalFsServer localFsServer;

    @Autowired
    AfterMediaChecker afterMediaChecker;

    ScheduledExecutorService scheduledExecutorService;
    ConcurrentHashMap<String, ScheduledFuture> futureConcurrentHashMap;

    ScheduledExecutorService killChannelExecutorService;
    ConcurrentHashMap<String, ScheduledFuture> killChannelScheduleHashMap;

    @PostConstruct
    private void init(){
        scheduledExecutorService = Executors.newScheduledThreadPool(10);
        futureConcurrentHashMap = new ConcurrentHashMap<>();
        killChannelExecutorService = Executors.newScheduledThreadPool(10);
        killChannelScheduleHashMap = new ConcurrentHashMap<>();
    }

    /**
     * 向FreeSWITCH通道播放机器人说话
     * @param uuid  通道uuid , uuid带orgId
     * @param mediaFile 媒体文件
     * @param mediaFileDuration 媒体文件时长
     * @param isPrologue    是否是开场白
     * @param isEnd     通话是否结束
     */
    private void playFile(String uuid, String mediaFile, Double mediaFileDuration, boolean isLock, boolean isEnd, boolean isPrologue) {
        log.info("需要设置通道媒体文件[{}]的属性，时间为[{}], isPrologue[{}], isEnd[{}]", mediaFile, mediaFileDuration, isPrologue, isEnd);

        Channel channel = channelService.findByUuid(uuid);

        if(isEnd){
            //预约挂断，在指定时长后挂断当前通道
            //在原来文件时长的基础上，增加3秒，防止话没说完，就被挂断
//            localFsServer.scheduleHangup(uuid, mediaFileDuration + 3);
            ScheduledFuture<?> schedule = killChannelExecutorService.schedule(() -> {
                        localFsServer.hangup(uuid);
                    },
                    (long)(mediaFileDuration*1000)+3000, TimeUnit.MILLISECONDS);
            killChannelScheduleHashMap.put(uuid, schedule);
        }

        localFsServer.playToChannel(uuid, mediaFile);
        setChannel(channel, uuid, mediaFile, mediaFileDuration, isLock, isEnd, isPrologue);
    }

    public void stopKillChannel(String uuid){
        if(killChannelScheduleHashMap.get(uuid)!=null){
            log.info("停止挂断计时器,uuid[{}]",uuid);
            ScheduledFuture scheduledFuture = killChannelScheduleHashMap.get(uuid);
            scheduledFuture.cancel(true);
        };
    }


    public void playAiReponse(AIResponse aiResponse, boolean isLock, boolean isPrologue, Integer orgId){
        playFile(aiResponse.getCallId()+ Constant.UUID_SEPARATE+orgId, aiResponse.getWavFile(),
                aiResponse.getWavDuration(),  isLock, false, isPrologue);
    }

    public void playFile(String uuid, String mediaFile, Double mediaFileDuration, boolean isLock, boolean isPrologue) {
        playFile(uuid, mediaFile, mediaFileDuration, isLock, false, isPrologue);
    }

    /**
     *
     * 在播放完媒体后，将当前呼叫转到座席组
     * @param mediaFile
     * @param mediaFileDuration
     * @param agentGroupId
     */
    public void playAndTransferToAgentGroup(String uuid, String mediaFile, Double mediaFileDuration, String agentGroupId){
        log.info("需要锁定通道媒体文件[{}]的播放，锁定时间为[{}]", mediaFile, mediaFileDuration);

        String toAgentFs = toAgentManager.findToAgentFsAdder();
        //转人工播放时间加0.5秒，防止声音未播放完，就转人工
        setChannel(uuid, mediaFileDuration + 0.5, false, () -> localFsServer.transferToAgentGroup(uuid, toAgentFs, agentGroupId));

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
        playFile(uuid, mediaFile, mediaFileDuration, true, true,false);
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
     * 判断当前通道是否在播放媒体
     * @param uuid
     * @return
     */
    public boolean isInPlay(String uuid){
        Channel callMedia = channelService.findByUuid(uuid);
        if(callMedia == null){
            return false;
        }

        return callMedia.isInPlay();
    }

    /**
     * 判断通道是否允许打断
     * @param uuid
     * @return
     */
    public boolean isAllowDisturb(String uuid){
        boolean isAllowed = false;
        Channel callMedia = channelService.findByUuid(uuid);
        //媒体通道还不存在，则允许打断
        if(callMedia == null){
            isAllowed = true;
        }else{
            //当前没有媒体播放，允许打断
            if(!callMedia.isInPlay()){
                isAllowed = true;
            }else{
                LocalTime disturbTime = callMedia.getDisturbTime();
                if (disturbTime == null) {
                    isAllowed = true;
                }else{
                    LocalTime currentTime = LocalTime.now();
                    Duration duration = Duration.between(disturbTime, currentTime);
                    isAllowed = (duration.getSeconds()>=3L);
                }
            }
        }
        log.info("通过callMedia[{}]判断允许打断的结果为[{}]", callMedia, isAllowed);

        return isAllowed;
    }

    /**
     * 锁定通道指定时长, 单位为秒
     * @param callMedia
     * @param uuid
     * @param mediaFile
     * @param mediaFileDuration
     * @param isEnd
     */
    private void setChannel(Channel callMedia, String uuid, String mediaFile, Double mediaFileDuration, boolean isLock, boolean isEnd, boolean isPrologue){
        if(callMedia == null){
            callMedia = new Channel();
            callMedia.setChannelId(uuid);
        }else if(callMedia.isInPlay()){
            log.info("符合打断条件，对当前进行打断");
            callMedia.setDisturbTime(LocalTime.now());
        }

        callMedia.setMediaFileName(mediaFile);
        callMedia.setIsPrologue(isPrologue);
        callMedia.setIsMediaLock(isLock);
        callMedia.setStartPlayTime(new Date());
        callMedia.setExpectEndPlayTime(new Date().getTime()+(long)Math.ceil(mediaFileDuration*1000)+200);
        channelService.save(callMedia);

        //把之前的定时器停掉
        stopTimer(uuid);
        //设置一个新的定时器
        log.info("使用定时服务setChannel,timeLen[{}],isEnd[{}]", mediaFileDuration, isEnd);
        ScheduledFuture<?> schedule = scheduledExecutorService.schedule(() -> {
                    if (!isEnd) {
                        log.info("在[{}]秒之后，将通道[{}]媒体文件清理掉，isPrologue设置为[{}]", mediaFileDuration, uuid, false);
                        channelService.updateMediaLock(uuid, false,false, null, null);
//                        afterMediaChecker.addAfterMediaCheck(uuid);
                    } else {
                        log.info("播放结束，开始删除callMedia[{}]", uuid);
                        channelService.delete(uuid);
                    }
                },
                (long)(mediaFileDuration*1000)+500, TimeUnit.MILLISECONDS);
                //加个500ms，调用异步命令到freeswitch放音会有部分时间消耗
        futureConcurrentHashMap.put(uuid, schedule);
    }

    /**
     * 锁定通道指定时长, 单位为秒
     * @param uuid
     * @param lockTimeLen  单位为秒
     */
    private void setChannel(String uuid, Double lockTimeLen, boolean isEnd, AfterLockHandle handler){
        channelService.updateMediaLock(uuid, true);

        log.debug("使用定时服务约定执行时间，timeLen[{}],isEnd[{}]", lockTimeLen, isEnd);
        ScheduledFuture<?> schedule = scheduledExecutorService.schedule(() -> {
                    if (!isEnd) {
                        log.debug("通道[{}]锁定时间[{}]已完成，解除锁定", uuid, lockTimeLen);
                        channelService.updateMediaLock(uuid, false);
                    } else {
                        log.debug("播放结束，开始删除callMedia");
                        channelService.delete(uuid);
                    }

                    if (handler != null) {
                        handler.handle();
                    }
                },
                lockTimeLen.longValue(), TimeUnit.SECONDS);
    }

    /**
     * 停止计时器
     * @param uuid
     */
    private void stopTimer(String uuid) {
        if(futureConcurrentHashMap.containsKey(uuid)){
            try{
                log.info("取消之前的定时任务，uuid[{}]", uuid);
                futureConcurrentHashMap.get(uuid).cancel(true);

                afterMediaChecker.removeMediaCheck(uuid);
            }catch (Exception ex){
                log.warn("取消定时任务时出现异常", ex);
            }
        }
    }


    /**
     * 释放资源，停止后续处理
     */
    public void hangup(String uuid){
        log.info("通道挂断，开始清理Channel");
        channelService.delete(uuid);

        //停止该通道相关的各种计时器
        log.info("停止通道[{}]的各种计时器", uuid);
        stopTimer(uuid);
        robotNextHelper.stopAiCallNextTimer(uuid);
    }

    interface AfterLockHandle{
        void handle();
    }

}
