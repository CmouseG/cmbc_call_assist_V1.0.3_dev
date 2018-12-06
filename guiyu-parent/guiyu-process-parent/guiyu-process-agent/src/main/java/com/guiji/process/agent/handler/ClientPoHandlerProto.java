package com.guiji.process.agent.handler;

import com.guiji.ImClientApp;
import com.guiji.common.model.process.ProcessInstanceVO;
import com.guiji.common.model.process.ProcessStatusEnum;
import com.guiji.process.agent.core.ImConnection;
import com.guiji.process.agent.core.filemonitor.impl.FileMonitor;
import com.guiji.process.core.ProcessMsgHandler;
import com.guiji.process.core.message.CmdMessageVO;
import com.guiji.process.core.message.CmdMsgTypeEnum;
import com.guiji.process.core.message.CmdProtoMessage;
import com.guiji.process.core.message.MessageProto;
import com.guiji.process.core.util.CmdMessageUtils;
import com.guiji.process.core.vo.CmdTypeEnum;
import com.guiji.utils.IdGenUtil;
import com.guiji.utils.JsonUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.EventLoop;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Inet4Address;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ClientPoHandlerProto extends ChannelInboundHandlerAdapter {
	private final Logger logger = LoggerFactory.getLogger(ClientPoHandlerProto.class);
	private ImConnection imConnection = new ImConnection();

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		CmdProtoMessage.ProtoMessage message = (CmdProtoMessage.ProtoMessage) msg;
		if (message.getType() == 1) {
			// 收到服务端回复信息
			logger.debug(message.getContent());
		}
		else if (message.getType() == 2) {
			// 收到服务端发送指令并执行
			CmdMessageVO cmdMessageVO = CmdMessageUtils.convert(message);
			logger.debug("收到服务给客户端的命令：" + cmdMessageVO);
			ProcessMsgHandler.getInstance().add(cmdMessageVO);

			if(cmdMessageVO.getMsgTypeEnum() == CmdMsgTypeEnum.REQ)
			{
				CmdMessageVO responseVO = new CmdMessageVO();
				responseVO.setReqKey(cmdMessageVO.getReqKey());
				responseVO.setMsgTypeEnum(CmdMsgTypeEnum.REQ_ACK);

				ImClientProtocolBO.getIntance().send(responseVO,3);
			}
		}
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.fireChannelActive();  // 若把这一句注释掉将无法将event传递给下一个ClientHandler
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		logger.debug("掉线了...");
		//使用过程中断线重连
		final EventLoop eventLoop = ctx.channel().eventLoop();
		eventLoop.schedule(new Runnable() {
			@Override
			public void run() {
				imConnection.connect(ImClientApp.imClientApp.configInit.getServerIp(), ImClientApp.imClientApp.configInit.getServerPort());
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
				logger.debug("1分钟未收到服务器返回心跳");
            } else if (event.state().equals(IdleState.WRITER_IDLE)) {
				logger.debug("20秒向服务器发送一个心跳");
                //发送心跳包
				ImClientProtocolBO.getIntance().send(new CmdMessageVO(),1);

            } else if (event.state().equals(IdleState.ALL_IDLE)) {
				logger.debug("ALL");
            }
        }
    }
}
