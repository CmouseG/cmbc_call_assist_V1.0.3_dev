package com.guiji;

import java.net.UnknownHostException;

import com.guiji.process.agent.core.filemonitor.impl.FileMonitor;
import com.guiji.process.agent.handler.ImClientProtocolBO;
import com.guiji.process.agent.service.ProcessCfgService;

import com.guiji.process.core.vo.ProcessTypeEnum;
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
		ImClientProtocolBO.getIntance().start(ProcessTypeEnum.AGENT);
		new FileMonitor().monitor("d:\\conf");
		ProcessCfgService.getIntance().init("D:\\conf\\cfg.json");
		SpringApplication.run(ImClientApp.class, args);
	}

}
