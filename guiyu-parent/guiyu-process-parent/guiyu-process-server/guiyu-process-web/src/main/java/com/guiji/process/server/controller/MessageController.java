package com.guiji.process.server.controller;

import com.guiji.process.core.message.Message;
import com.guiji.process.server.core.ConnectionPool;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 消息API,提供消息的基本操作
 * @author yinjihuan
 *
 */
@RestController
@RequestMapping("/message")
public class MessageController {
	
	/**
	 * 推送消息给所有客户端
	 * @param content	消息内容
	 * @return
	 */
	@GetMapping("/push")
	public Object pushAllMessage(String content) {
		ConnectionPool.getChannels().forEach(c -> {
			Message message = new Message();
			message.setContent(content);
			c.writeAndFlush(content);
		});
		return "success";
	}
	
	/**
	 * 推送消息给指定客户端
	 * @param clientId  客户端ID
	 * @param content	消息内容
	 * @return
	 */
	@GetMapping("/push/{clientId}")
	public Object pushAllMessage(@PathVariable("clientId")String clientId, String content) {
		ChannelHandlerContext channel = ConnectionPool.getChannel(clientId);
		Message message = new Message();
		message.setContent(content);
		channel.writeAndFlush(content);
		return "success";
	}
	
}
