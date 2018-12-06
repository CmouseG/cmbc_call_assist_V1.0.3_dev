package com.guiji.process.server.http;

import com.guiji.process.core.util.ByteUtils;
import com.guiji.process.server.handler.ServerPoHandlerProto;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.EmptyByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.DefaultHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders.Names;
import io.netty.handler.codec.http.HttpHeaders.Values;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.LastHttpContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyHttpServerHandler extends ChannelInboundHandlerAdapter {
	Logger logger = LoggerFactory.getLogger(NettyHttpServerHandler.class);

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof HttpRequest) {
			DefaultHttpRequest request = (DefaultHttpRequest) msg;
			logger.debug("URI:" + request.getUri());
			System.err.println(msg);
		}
		
		if (msg instanceof HttpContent) {
			LastHttpContent httpContent = (LastHttpContent) msg;
			ByteBuf byteData = httpContent.content();
			if (byteData instanceof EmptyByteBuf) {
				logger.debug("Content：无数据");
			} else {
				String content = new String(ByteUtils.objectToByte(byteData));
				logger.debug("Content:" + content);
			}
		}
		
		FullHttpResponse response = new DefaultFullHttpResponse(
				HttpVersion.HTTP_1_1,
				HttpResponseStatus.OK,
				Unpooled.wrappedBuffer("欢迎来到硅语进程管理".getBytes("utf-8")));
		response.headers().set(Names.CONTENT_TYPE, "text/plain;charset=UTF-8");
		response.headers().set(Names.CONTENT_LENGTH, response.content().readableBytes());
		response.headers().set(Names.CONNECTION, Values.KEEP_ALIVE);
		ctx.write(response);
		ctx.flush();
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
		cause.printStackTrace();
	}
}
