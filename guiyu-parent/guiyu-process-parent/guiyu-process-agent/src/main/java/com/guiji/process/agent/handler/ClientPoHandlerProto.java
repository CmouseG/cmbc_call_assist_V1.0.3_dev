package com.guiji.process.agent.handler;

import com.guiji.ImClientApp;
import com.guiji.process.agent.core.ImConnection;
import com.guiji.process.core.ProcessMsgHandler;
import com.guiji.process.core.message.CmdMessageVO;
import com.guiji.process.core.message.MessageProto;
import com.guiji.utils.JsonUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.EventLoop;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import java.net.Inet4Address;
import java.util.concurrent.TimeUnit;

public class ClientPoHandlerProto extends ChannelInboundHandlerAdapter {
	private ImConnection imConnection = new ImConnection();

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		MessageProto.Message message = (MessageProto.Message) msg;
		if (message.getType() == 1) {
			// 收到服务端回复信息
			System.out.println(message.getContent());
		} else if (message.getType() == 2) {
			// 收到服务端发送指令并执行
			CmdMessageVO cmdMessageVO = JsonUtils.json2Bean(message.getContent(),CmdMessageVO.class);
			ProcessMsgHandler.getInstance().add(cmdMessageVO);
		} else if (message.getType() == 3) {
			// 收到服务端回复信息
			System.out.println("服务端反馈:" + message.getContent());
		}

	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("Client01Handler Active");
		ctx.fireChannelActive();  // 若把这一句注释掉将无法将event传递给下一个ClientHandler
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.err.println("掉线了...");
		//使用过程中断线重连
		final EventLoop eventLoop = ctx.channel().eventLoop();
		eventLoop.schedule(new Runnable() {
			@Override
			public void run() {
				imConnection.connect(ImClientProtocolBO.HOST, ImClientProtocolBO.PORT);
			}
		}, 1L, TimeUnit.SECONDS);
		super.channelInactive(ctx);
	}
	
	@Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state().equals(IdleState.READER_IDLE)) {
                System.out.println("1分钟未收到服务器返回心跳");
            } else if (event.state().equals(IdleState.WRITER_IDLE)) {
                System.out.println("20秒向服务器发送一个心跳");
                //发送心跳包
				MessageProto.Message.Builder builder = MessageProto.Message.newBuilder().setType(1);
				String ip= Inet4Address.getLocalHost().getHostAddress();
				int port = ImClientApp.agentPort;
				builder.setContent(ip+":" + port);
                ctx.writeAndFlush(builder);
            } else if (event.state().equals(IdleState.ALL_IDLE)) {
            	System.out.println("ALL");
            }
        }
    }
}
