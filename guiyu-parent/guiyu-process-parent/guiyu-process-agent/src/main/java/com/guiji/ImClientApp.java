package com.guiji;

import java.net.Inet4Address;
import java.net.UnknownHostException;

import com.guiji.process.agent.core.ImConnection;
import com.guiji.process.core.message.MessageProto;

import io.netty.channel.Channel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * IM 客户端启动入口
 * @author yinjihuan
 */
@SpringBootApplication
public class ImClientApp {
	public static final String HOST = "127.0.0.1";
	public static int PORT = 2222;
	public static void main(String[] args) throws UnknownHostException {
		Channel channel = new ImConnection().connect(HOST, PORT);
		String id = Inet4Address.getLocalHost().getHostAddress();
		// protobuf
		MessageProto.Message message = MessageProto.Message.newBuilder().setId(id).setContent("register").build();
		channel.writeAndFlush(message);
		//对象传输数据
		/*Message message = new Message();
		message.setId(id);
		message.setContent("hello yinjihuan");
		channel.writeAndFlush(message);*/
		//字符串传输数据
		//channel.writeAndFlush("yinjihuan");
		SpringApplication.run(ImClientApp.class, args);
	}
}
