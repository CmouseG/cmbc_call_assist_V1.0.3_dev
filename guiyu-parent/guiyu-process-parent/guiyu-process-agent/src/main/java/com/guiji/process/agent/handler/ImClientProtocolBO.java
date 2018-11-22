package com.guiji.process.agent.handler;

import com.guiji.process.agent.core.ImConnection;
import com.guiji.process.agent.model.OperateVO;
import com.guiji.process.core.message.MessageProto;
import io.netty.channel.Channel;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ty on 2018/11/19.
 */
public class ImClientProtocolBO {
    public static final String HOST = "127.0.0.1";
    public static int PORT = 2222;
    public static Channel channelGlobal = null;
    public static List<OperateVO> operateVOList = new ArrayList<OperateVO>();
    public static long operateIntervalTime = 30000;//命令操作间隔30s

    private static ImClientProtocolBO instance = new ImClientProtocolBO();

    public ImClientProtocolBO(){

    }

    public static ImClientProtocolBO getIntance()
    {
        return instance;
    }


    public void start() throws UnknownHostException {
        Channel channel = new ImConnection().connect(HOST, PORT);
        channelGlobal = channel;
        String id = Inet4Address.getLocalHost().getHostAddress();
        // protobuf
        MessageProto.Message message = MessageProto.Message.newBuilder().setId(id).setContent("register").build();
        channel.writeAndFlush(message);
        // 实体类传输数据，protobuf序列化
        channel.pipeline().addLast(new ClientPoHandlerProto());
		/*Message message = new Message();
		message.setId(id);
		message.setContent("hello yinjihuan");
		channel.writeAndFlush(message);*/
        //字符串传输数据
        //channel.writeAndFlush("yinjihuan");
    }


    public void send(String msg,int type) {

        //发送消息
        MessageProto.Message.Builder builder = MessageProto.Message.newBuilder().setType(type);
        builder.setContent(msg);

        channelGlobal.writeAndFlush(builder);
    }


}
