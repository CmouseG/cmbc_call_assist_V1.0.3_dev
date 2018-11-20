package com.guiji;

import java.io.File;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import com.guiji.process.agent.core.ImConnection;
import com.guiji.process.agent.core.filemonitor.impl.FileListener;
import com.guiji.process.agent.core.filemonitor.impl.FileMonitor;
import com.guiji.process.agent.handler.ImClientProtocolBO;
import com.guiji.process.core.message.MessageProto;

import io.netty.channel.Channel;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
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
		SpringApplication.run(ImClientApp.class, args);
	}

}
