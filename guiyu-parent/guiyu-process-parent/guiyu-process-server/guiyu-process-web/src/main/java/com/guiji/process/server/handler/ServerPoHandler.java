package com.guiji.process.server.handler;

import com.guiji.process.core.message.CmdProtoMessage;
import com.guiji.process.core.message.Message;
import com.guiji.process.server.core.ConnectionPool;
import com.guiji.process.server.util.DeviceProcessUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerPoHandler extends ChannelInboundHandlerAdapter {
	
	@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        String remoteIp = DeviceProcessUtil.getRemoreIp(ctx);
        CmdProtoMessage.ProtoMessage message = (CmdProtoMessage.ProtoMessage) msg;
		if (ConnectionPool.getChannel(remoteIp) == null) {
			ConnectionPool.putChannel(remoteIp, ctx);
		}
		System.err.println("server:" + message.getId());
		ctx.writeAndFlush(message);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
