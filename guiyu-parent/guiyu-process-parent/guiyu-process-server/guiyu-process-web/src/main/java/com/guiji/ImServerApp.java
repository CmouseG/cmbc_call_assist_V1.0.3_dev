package com.guiji;

import com.guiji.process.server.core.ImServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * IM服务启动入口
 * @author yinjihuan
 */
@SpringBootApplication
public class ImServerApp {
	public static void main(String[] args) {
		SpringApplication.run(ImServerApp.class, args);
	}
}
