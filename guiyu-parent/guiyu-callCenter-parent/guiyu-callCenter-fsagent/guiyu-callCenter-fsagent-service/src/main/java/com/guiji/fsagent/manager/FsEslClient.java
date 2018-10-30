package com.guiji.fsagent.manager;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.freeswitch.esl.client.inbound.Client;
import org.freeswitch.esl.client.transport.message.EslMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 用于管理与FreeSWITCH的ESL连接
 */
public class FsEslClient {
    private final Logger logger = LoggerFactory.getLogger(FsEslClient.class);

    private String fsName;
    private Client eslClient;
    private String eslIp;
    private String eslPort;
    private String eslPwd;

    public FsEslClient(String fsName, String eslIp, String eslPort, String eslPwd){
        Preconditions.checkArgument(!Strings.isNullOrEmpty(eslIp), "null eslIp");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(eslPort), "null eslPort");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(eslPwd), "null eslPwd");

        this.fsName = fsName;
        this.eslIp = eslIp;
        this.eslPort = eslPort;
        this.eslPwd = eslPwd;
    }

    /**
     * 判断ESL是否可用
     * @param client
     * @return
     */
    public boolean isConnect(Client client){
        return client!=null && client.canSend();
    }

    private synchronized Client getFsClient(){
        if(isConnect(eslClient)){
            return eslClient;
        }else{
            try {
                eslClient = new Client();
                logger.debug("开始连接FreeSWITCH,[{}][{}]", eslIp, eslPort);
                eslClient.connect(eslIp, Integer.parseInt(eslPort), eslPwd, 10);
            }catch (Exception ex){
                logger.warn("获取FreeSWITCH ESL连接出现异常", ex);
            }
        }

        Preconditions.checkState(isConnect(eslClient), "fail to Connect to FreeSWITCH ESL");
        return eslClient;
    }

    public List<String> api(String command){
        EslMessage eslMessage = getFsClient().sendSyncApiCommand(command, "");
        return eslMessage.getBodyLines();
    }

    public String execute(String command){
        EslMessage eslMessage = getFsClient().sendSyncApiCommand(command, "");
        String response = Joiner.on(" ").skipNulls().join(eslMessage.getBodyLines());
        logger.debug("FreeSWITCH命令[{}]返回结果为[{}]", command, response);
        return response;
    }
    public String executeAsync(String command){
        String response = getFsClient().sendAsyncApiCommand(command, "");
       // String response = Joiner.on(" ").skipNulls().join(eslMessage.getBodyLines());
        logger.debug("FreeSWITCH命令[{}]返回结果为[{}]", command, response);
        return response;
    }


//    /**
//     * 回卷cdr文件
//     */
//    public void rotateCdr(){
//        String cmd = "cdr_csv rotate";
//        String result = execute(cmd);
//        logger.debug("[{}]回卷日志结果为[{}]", fsName, result);
//    }
}
