package com.guiji.process.agent.handler;

import com.guiji.ImClientApp;
import com.guiji.common.model.process.ProcessTypeEnum;
import com.guiji.process.agent.core.ImConnection;
import com.guiji.process.agent.model.OperateVO;
import com.guiji.process.agent.service.ProcessCfgService;
import com.guiji.process.core.message.CmdMessageVO;
import com.guiji.process.core.message.CmdProtoMessage;
import com.guiji.process.core.message.MessageProto;
import com.guiji.process.core.util.CmdMessageUtils;
import com.guiji.process.core.vo.CmdTypeEnum;
import com.guiji.common.model.process.ProcessInstanceVO;
import com.guiji.utils.JsonUtils;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ty on 2018/11/19.
 */
public class ImClientProtocolBO {
    private final Logger logger = LoggerFactory.getLogger(ImClientProtocolBO.class);
    public static Channel channelGlobal = null;
    public static List<OperateVO> operateVOList = new ArrayList<OperateVO>();
    public static long operateIntervalTime = 1000;//命令操作间隔30s

    private static ImClientProtocolBO instance = new ImClientProtocolBO();

    public ImClientProtocolBO(){

    }

    public static ImClientProtocolBO getIntance()
    {
        return instance;
    }


    public void start(ProcessTypeEnum processTypeEnum, Integer agentPort) throws UnknownHostException {
        Channel channel = new ImConnection().connect(ImClientApp.imClientApp.configInit.getServerIp(), ImClientApp.imClientApp.configInit.getServerPort());
        channelGlobal = channel;
        String id = Inet4Address.getLocalHost().getHostAddress();


        // 实体类传输数据，protobuf序列化
        channel.pipeline().addLast(new ClientPoHandlerProto());

        // protobuf

        /*CmdMessageVO cmdMessageVO = new CmdMessageVO();
        cmdMessageVO.setCmdType(CmdTypeEnum.AGENTREGISTER);
        ProcessInstanceVO processInstanceVO = new ProcessInstanceVO();
        processInstanceVO.setIp(Inet4Address.getLocalHost().getHostAddress());
        processInstanceVO.setType(processTypeEnum);
        processInstanceVO.setPort(agentPort);
        cmdMessageVO.setProcessInstanceVO(processInstanceVO);

        send(cmdMessageVO,3);*/
        send(new CmdMessageVO(),2);
    }


    public void send(CmdMessageVO cmdMessageVO,int type) {

        //发送消息
        CmdProtoMessage.ProtoMessage.Builder builder = CmdMessageUtils.convert(cmdMessageVO);
        builder.setType(type);

        channelGlobal.writeAndFlush(builder);


        logger.debug("客户端发送消息：：" + cmdMessageVO);
    }


}
