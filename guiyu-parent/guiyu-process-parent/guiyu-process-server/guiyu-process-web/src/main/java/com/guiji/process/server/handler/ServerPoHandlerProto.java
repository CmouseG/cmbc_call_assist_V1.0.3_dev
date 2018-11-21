package com.guiji.process.server.handler;

import com.guiji.process.core.message.CmdMessageVO;
import com.guiji.process.core.message.MessageProto;
import com.guiji.process.server.core.ConnectionPool;
import com.guiji.process.server.service.impl.DeviceMsgHandler;
import com.guiji.utils.JsonUtils;
import com.guiji.utils.StrUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.swagger.annotations.Scope;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
@org.springframework.context.annotation.Scope("prototype")
public class ServerPoHandlerProto extends ChannelInboundHandlerAdapter {
	@Autowired
	private DeviceMsgHandler deviceMsgHandler;
	
	@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws UnsupportedEncodingException {
		MessageProto.Message message = (MessageProto.Message) msg;
		if (ConnectionPool.getChannel(message.getId()) == null) {
			ConnectionPool.putChannel(message.getId(), ctx);
		}
		if (message.getType() == 0) {
			if(StringUtils.isNotEmpty(message.getId())) {
				//客户端启动
				System.out.println("客户端:" + "clientId-" + message.getId());
			}
		}

		// ping
		if (message.getType() == 1) {
			System.out.println("服务端收到消息:" +  message.getContent());
			CmdMessageVO cmdMessageVO = JsonUtils.json2Bean(message.getContent(),CmdMessageVO.class);
			deviceMsgHandler.add(cmdMessageVO);
			System.out.println("转换后的bean"+cmdMessageVO.toString());
			//发送响应
			MessageProto.Message.Builder builder = MessageProto.Message.newBuilder().setType(1);
			builder.setContent("服务端响应:" + "hello");
			ctx.writeAndFlush(builder);
		}

		// ping
		if (message.getType() == 3) {
			System.out.println("服务端收到状态监控:" +  message.getContent());
			CmdMessageVO cmdMessageVO = JsonUtils.json2Bean(message.getContent(),CmdMessageVO.class);
			deviceMsgHandler.add(cmdMessageVO);
			System.out.println("转换后的bean"+cmdMessageVO.toString());
			//发送响应
			MessageProto.Message.Builder builder = MessageProto.Message.newBuilder().setType(1);
			builder.setContent("服务端响应:" + "hello");
			ctx.writeAndFlush(builder);
		}
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
