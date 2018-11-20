package com.guiji;

import java.net.UnknownHostException;

import com.guiji.process.agent.handler.ImClientProtocolBO;
import com.guiji.process.agent.service.ProcessCfgService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * IM 客户端启动入口
 * @author yinjihuan
 */
@SpringBootApplication
@EnableScheduling
public class ImClientApp {
	public static void main(String[] args) throws UnknownHostException {
		ImClientProtocolBO.getIntance().start();
		ProcessCfgService.getIntance().init("D:\\cfg.json");
		SpringApplication.run(ImClientApp.class, args);
	}

}
