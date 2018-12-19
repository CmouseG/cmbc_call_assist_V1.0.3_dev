package com.guiji.process.agent.core;

import com.guiji.ImClientApp;
import com.guiji.process.agent.handler.ClientPoHandlerProto;
import com.guiji.process.agent.handler.ImClientProtocolBO;
import com.guiji.process.agent.service.ProcessCfgService;
import com.guiji.process.core.message.CmdMessageVO;
import io.netty.channel.Channel;
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
					Channel channel = imConnection.connect(ImClientApp.imClientApp.configInit.getServerIp(), ImClientApp.imClientApp.configInit.getServerPort());

					// 实体类传输数据，protobuf序列化
					channel.pipeline().addLast(new ClientPoHandlerProto());
					ImClientProtocolBO.getIntance().channelGlobal = channel;

					ImClientProtocolBO.getIntance().send(new CmdMessageVO(),2);
					ProcessCfgService.getIntance().reConnect();
				}
			}, 1L, TimeUnit.SECONDS);
		} else {
			System.err.println("服务端链接成功...");
		}
	}
}
