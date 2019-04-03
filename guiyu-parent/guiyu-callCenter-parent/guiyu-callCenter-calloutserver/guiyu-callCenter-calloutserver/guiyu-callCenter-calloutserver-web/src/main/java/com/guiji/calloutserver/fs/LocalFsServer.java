package com.guiji.calloutserver.fs;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.guiji.calloutserver.manager.FsManager;
import com.guiji.fsmanager.entity.FsBindVO;
import org.apache.commons.lang3.StringUtils;
import org.freeswitch.esl.client.IEslEventListener;
import org.freeswitch.esl.client.inbound.Client;
import org.freeswitch.esl.client.transport.event.EslEvent;
import org.freeswitch.esl.client.transport.message.EslMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

/**
 * 用于管理与FreeSWITCH的ESL连接
 */
@Component
public class LocalFsServer implements IEslEventListener {
    private final Logger logger = LoggerFactory.getLogger(LocalFsServer.class);

    @Autowired
    FsEventHandler fsEventHandler;

    @Autowired
    FsWatchDog fsWatchDog;

    @Autowired
    FsManager fsManager;

    private FsBindVO fsBindInfo;

    private Client eslClient;

    public void init(FsBindVO fsInfo){
        this.fsBindInfo = fsInfo;

        eslClient = getFsClient();
        fsWatchDog.monitor(this);
    }

    /**
     * 判断ESL是否可用
     * @return
     */
    public boolean isConnect(){
        return eslClient!=null && eslClient.canSend();
    }

    /**
     * 重新连接FreeSWITCH
     */
    public void reConnect(){
        if(isConnect()){
            logger.info("已经处于连接状态，无需重连");
        }else{
            logger.info("开始重连FreeSWITCH...");
            getFsClient();
            logger.info("重连后状态为[{}]", isConnect());
        }
    }

    private synchronized Client getFsClient(){
        if(isConnect()){
            return eslClient;
        }else{
            try {
                //将老的资源释放干净
                if(eslClient != null){
                    logger.info("为方便FreeSWITCH重连，清理旧资源");
                    eslClient.destroy();
                }

                eslClient = new Client();
                logger.info("开始连接FreeSWITCH，[{}]", fsBindInfo);

                String fsAgentAddr = fsBindInfo.getFsAgentAddr();
                if(fsAgentAddr.contains(":")){
                    fsAgentAddr = fsAgentAddr.split(":")[0];
                }
                eslClient.connect(fsAgentAddr, Integer.parseInt(fsBindInfo.getFsEslPort()), fsBindInfo.getFsEslPwd(), 2);

                if(isConnect()){
                    logger.info("---------------成功连接FreeSWITCH, 开始执行订阅时间等操作-------------");
                    initClient(eslClient);
                }else{
                    //TODO: 报警，连接fs失败
                    logger.info("连接FreeSWITCH失败");
                }
            }catch (Exception ex){
                //TODO: 报警，freeswitch连接异常
                logger.warn("获取FreeSWITCH ESL连接出现异常" + ex.getMessage());
            }
        }

        Preconditions.checkNotNull(eslClient, "连接FreeSWITCH出现异常, eslClient为空");
        return eslClient;
    }

    /**
     * 初始化，主要用于订阅事件
     * @param eslClient
     */
    private void initClient(Client eslClient) {
        logger.info("开始初始化eslClient");
        eslClient.setEventSubscriptions( "plain", "all" );
        eslClient.addEventFilter("Event-Name","CHANNEL_ANSWER");
        eslClient.addEventFilter("Event-Name","CHANNEL_HANGUP_COMPLETE");
        eslClient.addEventFilter("Event-Name","CHANNEL_PROGRESS");
        eslClient.addEventFilter("Event-Name","CHANNEL_PROGRESS_MEDIA");
        eslClient.addEventFilter("Event-Subclass","EV_ALIASR");
        eslClient.addEventFilter("Event-Subclass","callcenter::info");

        eslClient.addEventListener(this);
        logger.info("初始化eslClient完毕");
    }

    /**
     * 将通道中播放的旧音频替换为新的
     * @param uuid
     * @param oldWav    正在播放的音频文件, 可以为空
     * @param newWav    准备播放的新音频文件
     */
    public void displace(String uuid, String oldWav, String newWav){
        Preconditions.checkArgument(!Strings.isNullOrEmpty(uuid), "null uuid");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(newWav), "null newWav");

        if(!Strings.isNullOrEmpty(oldWav)){
            String cmd = String.format("uuid_displace %s stop %s 0 mux", uuid, oldWav);
            executeAsync(cmd);
        }

        String cmd = String.format("uuid_displace %s start %s 0 mux", uuid, newWav);
        execute(cmd);
    }

    /**
     * 向verto客户端发送消息
     * @param msg
     */
    public void vchat(String recvNum, Object msg){
        String cmd = String.format("lua vchat.lua %s %s", recvNum, msg.toString());
        executeAsync(cmd);
    }

    /**
     * 向通道播放录音文件，如果通道中已有录音在播放，则停止，播放最新的文件
     * @param uuid
     * @param wavFile
     */
    public void playToChannel(String uuid, String wavFile){
        String hangupCmd = String.format("lua playfile.lua %s %s", uuid, wavFile);
        executeAsync(hangupCmd);
    }

    /**
     * 预约挂断, 让通道在多长时间时间后挂断
     * @param uuid
     * @param timeOut
     */
//    public void scheduleHangup(String uuid, Double timeOut){
//        String hangupCmd = String.format("sched_api +%s  uuid_kill %s", timeOut, uuid);
//        executeAsync(hangupCmd);
//    }

    /**
     * 杀掉通道
     * @param uuid
     */
    public void hangup(String uuid){
        executeAsync("uuid_kill " + uuid);
    }

    public String execute(String command){
        EslMessage eslMessage = getFsClient().sendSyncApiCommand(command, "");
        String response = StringUtils.join(eslMessage.getBodyLines(), "\n");
        logger.info("FreeSWITCH命令[{}]返回结果为[{}]", command, response);
        return response;
    }

    /**
     * 将呼叫转到指定CallCenter中的座席组
     * @param uuid
     * @param agentGroupId
     */
    public void transferToAgentGroup(String uuid, String agentFs, String agentGroupId){
        //uuid_transfer cb5f0f74-8fd0-4929-abf8-a1478f30e4cc 'bridge:sofia/external/9-out-294926373783339008-30006@192.168.1.78:50600' inline
        String command = String.format("uuid_transfer %s 'bridge:sofia/internal/9-out-%s-%s@%s' inline",
                uuid,uuid,
                agentGroupId,
                agentFs);
        String response = getFsClient().sendAsyncApiCommand(command, "");
        logger.info("FreeSWITCH异步命令[{}]返回结果为[{}]", command, response);
    }

    public void transfer(String uuid, String destNum){
        String command = String.format("uuid_transfer %s %s", uuid,destNum);
        String response = getFsClient().sendAsyncApiCommand(command, "");
        logger.info("FreeSWITCH异步命令[{}]返回结果为[{}]", command, response);
    }

    public void executeAsync(String command){
        String response = getFsClient().sendAsyncApiCommand(command, "");
        logger.info("FreeSWITCH异步命令[{}]返回结果为[{}]", command, response);
    }

    @Override
    public void eventReceived(EslEvent eslEvent) {
        try{
            fsEventHandler.handleEvent(eslEvent);
        }catch (Exception ex){
            logger.warn("处理eslEvent异常", ex);
        }
    }

    @Override
    public void backgroundJobResultReceived(EslEvent event) {
    }
}
