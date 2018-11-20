package com.guiji.process.agent.core;

import com.guiji.ImClientApp;
import com.guiji.process.agent.handler.ImClientProtocolBO;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoop;

import java.util.concurrent.TimeUnit;

/**
 * 负责监听启动时连接失败，重新连接功能
 * @author yinjihuan
 *
 */
public class ConnectionListener implements ChannelFutureListener {
	
	private ImConnection imConnection = new ImConnection();
	
	@Override
	public void operationComplete(ChannelFuture channelFuture) throws Exception {
		if (!channelFuture.isSuccess()) {
			final EventLoop loop = channelFuture.channel().eventLoop();
			loop.schedule(new Runnable() {
				@Override
				public void run() {
					System.err.println("服务端链接不上，开始重连操作...");
					imConnection.connect(ImClientProtocolBO.HOST, ImClientProtocolBO.PORT);
				}
			}, 1L, TimeUnit.SECONDS);
		} else {
			System.err.println("服务端链接成功...");
		}
	}
}
