package com.guiji.process.agent.core;

import com.guiji.process.agent.handler.ClientPoHandlerProto;
import com.guiji.process.agent.handler.ImClientProtocolBO;
import com.guiji.process.core.message.CmdProtoMessage;
import com.guiji.process.core.message.MessageProto;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class ImConnection {

	private Channel channel;
	
	public Channel connect(String host, int port) {
		doConnect(host, port);
		return this.channel;
	}

	private void doConnect(String host, int port) {
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(workerGroup);
			b.channel(NioSocketChannel.class);
			b.option(ChannelOption.SO_KEEPALIVE, true);
			b.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(SocketChannel ch) throws Exception {
					//ch.pipeline().addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
					//ch.pipeline().addLast("frameEncoder", new LengthFieldPrepender(4));
					//实体类传输数据，jdk序列化
					/*ch.pipeline().addLast("decoder", new MessageDecoder());
					ch.pipeline().addLast("encoder", new MessageEncoder());*/
					// 使用框架自带的对象编解码器
                	/*ch.pipeline().addLast(
                			new ObjectDecoder(
                					ClassResolvers.cacheDisabled(
                							this.getClass().getClassLoader()
                					)
                			)
                	);
                	ch.pipeline().addLast(new ObjectEncoder());*/
					// 实体类传输数据，protobuf序列化
					ch.pipeline().addLast("ping", new IdleStateHandler(60, 20, 60 * 10, TimeUnit.SECONDS));
					// ----Protobuf处理器，这里的配置是关键----
					ch.pipeline().addLast("frameDecoder", new ProtobufVarint32FrameDecoder());// 用于decode前解决半包和粘包问题（利用包头中的包含数组长度来识别半包粘包）
                	ch.pipeline().addLast("decoder",  
                            new ProtobufDecoder(CmdProtoMessage.ProtoMessage.getDefaultInstance()));
					// 用于在序列化的字节数组前加上一个简单的包头，只包含序列化的字节长度。
					ch.pipeline().addLast("frameEncoder",
							new ProtobufVarint32LengthFieldPrepender());
                	ch.pipeline().addLast("encoder",  
                            new ProtobufEncoder());
                	ch.pipeline().addLast(new ClientPoHandlerProto());
					/*ch.pipeline().addLast("decoder", new KryoDecoder());
					ch.pipeline().addLast("encoder", new KryoEncoder());*/
					//ch.pipeline().addLast(new ClientPoHandler());
					
					//字符串传输数据
					/*ch.pipeline().addLast("decoder", new StringDecoder());
					ch.pipeline().addLast("encoder", new StringEncoder());
					ch.pipeline().addLast(new ClientStringHandler());*/
				}
			});

			ChannelFuture f = b.connect(host, port);
			f.addListener(new ConnectionListener());
			channel = f.channel();
			ImClientProtocolBO.channelGlobal = channel;
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
