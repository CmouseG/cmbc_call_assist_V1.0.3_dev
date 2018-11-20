package com.guiji.process.server.handler;

import com.guiji.process.core.message.MessageProto;
import com.guiji.process.server.core.ConnectionPool;
import com.guiji.utils.StrUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;

public class ServerPoHandlerProto extends ChannelInboundHandlerAdapter {
	
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
			//发送心跳包
			MessageProto.Message.Builder builder = MessageProto.Message.newBuilder().setType(1);
			builder.setType(1);
			builder.setContent("服务端响应:" + "hello");
			ctx.writeAndFlush(builder);
		}

		if (message.getType() == 2) {
			System.out.println("服务端收到消息:" +  message.getContent());
			//发送心跳包
			MessageProto.Message.Builder builder = MessageProto.Message.newBuilder().setType(1);
			builder.setType(1);
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
