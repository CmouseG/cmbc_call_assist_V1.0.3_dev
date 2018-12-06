package com.guiji.process.agent.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 当编解码器为字符串时用来接收数据
 * @author yinjihuan
 *
 */
public class ClientStringHandler extends ChannelInboundHandlerAdapter {
	private final Logger logger = LoggerFactory.getLogger(ClientStringHandler.class);
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		logger.info("client:" + msg.toString());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
	
}
