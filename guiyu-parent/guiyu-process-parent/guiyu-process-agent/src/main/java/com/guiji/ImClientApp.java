package com.guiji;

import java.net.UnknownHostException;

import com.guiji.common.model.process.ProcessTypeEnum;
import com.guiji.process.agent.config.ConfigInit;
import com.guiji.process.agent.core.filemonitor.impl.FileMonitor;
import com.guiji.process.agent.handler.ImClientProtocolBO;
import com.guiji.process.agent.service.ProcessCfgService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;

/**
 * IM 客户端启动入口
 * @author yinjihuan
 */
@SpringBootApplication
@EnableScheduling
public class ImClientApp {
	@Autowired
	public ConfigInit configInit;
	public static ImClientApp imClientApp;
	@PostConstruct
	public void init() {
		imClientApp = this;
		imClientApp.configInit = this.configInit;

	}
	public static void main(String[] args) throws UnknownHostException {
		SpringApplication.run(ImClientApp.class, args);
		ImClientProtocolBO.getIntance().start(ProcessTypeEnum.AGENT, imClientApp.configInit.getAgentPort());
		new FileMonitor().monitor(imClientApp.configInit.getMonitorDir());
		ProcessCfgService.getIntance().init(imClientApp.configInit.getMonitorFile(), imClientApp.configInit.getAgentPort());
	}

}
