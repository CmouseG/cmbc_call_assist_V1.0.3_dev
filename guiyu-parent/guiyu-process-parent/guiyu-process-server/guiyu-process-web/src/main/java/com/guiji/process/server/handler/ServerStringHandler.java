package com.guiji.process.server.handler;

import com.guiji.process.core.message.CmdProtoMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 消息处理
 * @author yinjihuan
 *
 */
public class ServerStringHandler extends ChannelInboundHandlerAdapter {
	
	@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        CmdProtoMessage.ProtoMessage message = (CmdProtoMessage.ProtoMessage) msg;
		System.err.println("server:" + message.getContent());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
    
}
