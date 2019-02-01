package com.guiji.fs;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.freeswitch.esl.client.IEslEventListener;
import org.freeswitch.esl.client.inbound.Client;
import org.freeswitch.esl.client.transport.event.EslEvent;
import org.freeswitch.esl.client.transport.message.EslMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 用于管理与FreeSWITCH的ESL连接
 */
public class FsEslClient implements IEslEventListener{
    private final Logger logger = LoggerFactory.getLogger(FsEslClient.class);

    private Client eslClient;
    private String eslIp;
    private String eslPort;
    private String eslPwd;
    private FsEventHandler fsEventHandler;

    public FsEslClient(String eslIp, String eslPort, String eslPwd, FsEventHandler fsEventHandler){
        Preconditions.checkArgument(!Strings.isNullOrEmpty(eslIp), "null eslIp");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(eslPort), "null eslPort");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(eslPwd), "null eslPwd");

        this.eslIp = eslIp;
        this.eslPort = eslPort;
        this.eslPwd = eslPwd;
        this.fsEventHandler = fsEventHandler;
    }

    /**
     * 判断ESL是否可用
     * @param client
     * @return
     */
    public boolean isConnect(Client client){
        return client!=null && client.canSend();
    }

    public synchronized Client getFsClient(){
        if(isConnect(eslClient)){
            return eslClient;
        }else{
            try {
                eslClient = new Client();
                logger.info("开始连接FreeSWITCH,[{}][{}]", eslIp, eslPort);
                eslClient.connect(eslIp, Integer.parseInt(eslPort), eslPwd, 10);
                initClient(eslClient);
            }catch (Exception ex){
                logger.warn("获取FreeSWITCH ESL连接出现异常", ex);
            }
        }

        Preconditions.checkState(isConnect(eslClient), "fail to Connect to FreeSWITCH ESL");
        return eslClient;
    }
    /**
     * 初始化，主要用于订阅事件
     * @param eslClient
     */
    private void initClient(Client eslClient) {
        logger.info("开始初始化eslClient，订阅事件");
        eslClient.setEventSubscriptions( "plain", "all" );
        eslClient.addEventFilter("Event-Name","CHANNEL_ANSWER");
        eslClient.addEventFilter("Event-Name","CHANNEL_HANGUP_COMPLETE");
        eslClient.addEventFilter("Event-Subclass","EV_ALIASR");
        eslClient.addEventFilter("Event-Subclass","callcenter::info");
        eslClient.addEventFilter("Event-Subclass","verto::client_disconnect");
        eslClient.addEventFilter("Event-Subclass","verto::login");

        eslClient.addEventListener(this);
        logger.info("初始化eslClient完毕");
    }
    public List<String> api(String command){
        EslMessage eslMessage = getFsClient().sendSyncApiCommand(command, "");
        return eslMessage.getBodyLines();
    }

    public String execute(String command){
        EslMessage eslMessage = getFsClient().sendSyncApiCommand(command, "");
        String response = Joiner.on(" ").skipNulls().join(eslMessage.getBodyLines());
        logger.info("FreeSWITCH命令[{}]返回结果为[{}]", command, response);
        return response;
    }

    public String executeAsync(String command){
        String response = getFsClient().sendAsyncApiCommand(command, "");
        logger.info("FreeSWITCH异步命令[{}]返回结果为[{}]", command, response);
        return response;
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
