package com.guiji.process.server.core;

import com.guiji.process.core.message.CmdProtoMessage;
import com.guiji.process.core.message.MessageProto;
import com.guiji.process.server.handler.ServerPoHandlerProto;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * IM服务启动
 * @author yinjihuan
 *
 */
@Component
public class ImServer {

	public void run(int port) {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();

		ServerBootstrap bootstrap = new ServerBootstrap();
		bootstrap.group(bossGroup, workerGroup)
				.channel(NioServerSocketChannel.class)
				.childHandler(new ChannelInitializer<SocketChannel>() {
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
                    	/*ch.pipeline().addLast("decoder", new KryoDecoder());
    					ch.pipeline().addLast("encoder", new KryoEncoder());*/
						// ----Protobuf处理器，这里的配置是关键----
						ch.pipeline().addLast("frameDecoder", new ProtobufVarint32FrameDecoder());// 用于decode前解决半包和粘包问题（利用包头中的包含数组长度来识别半包粘包）
						// 实体类传输数据，protobuf序列化
						ch.pipeline().addLast("decoder",
								new ProtobufDecoder(CmdProtoMessage.ProtoMessage.getDefaultInstance()));
						// 用于在序列化的字节数组前加上一个简单的包头，只包含序列化的字节长度。
						ch.pipeline().addLast("frameEncoder",
								new ProtobufVarint32LengthFieldPrepender());
						ch.pipeline().addLast("encoder",
								new ProtobufEncoder());
						ch.pipeline().addLast(new ServerPoHandlerProto());
						//ch.pipeline().addLast(new ServerPoHandler());
						//字符串传输数据
    					/*ch.pipeline().addLast("decoder", new StringDecoder());
    					ch.pipeline().addLast("encoder", new StringEncoder());
    					ch.pipeline().addLast(new ServerStringHandler());*/
					}
				})
				.option(ChannelOption.SO_BACKLOG, 1024)
				.childOption(ChannelOption.SO_KEEPALIVE, true);

		try {
			ChannelFuture f = bootstrap.bind(port).sync();
			f.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}

}
