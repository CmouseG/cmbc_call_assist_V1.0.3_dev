package com.guiji.process.agent.timer;

import com.guiji.process.agent.handler.ImClientProtocolBO;
import com.guiji.process.agent.model.ProcessStatus;
import com.guiji.process.agent.service.ProcessServiceImpl;
import com.guiji.utils.JsonUtils;
import com.guiji.utils.StrUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class CheckStatusTask {

	private static Logger logger = LoggerFactory.getLogger(CheckStatusTask.class);
	@Autowired
	ProcessServiceImpl processService;
	
	//定时任务，启动时运行（每1执行一次）
	@Scheduled(fixedRate = 1000*5)
    public void checkStatusTask() throws InterruptedException, UnsupportedEncodingException {
		List<ProcessStatus> processStatusList = new ArrayList<ProcessStatus>();
		for (int i=0;i<32;i++){
			ProcessStatus processStatus = new ProcessStatus();
			processStatus.setIp("127.0.0.1");
			processStatus.setPort("18001");
			String pid = processService.getPid("18001");
			boolean isUp = processService.checkRun("18001");

			processStatus.setPid(pid);
			processStatus.setUp(isUp);
			processStatusList.add(processStatus);
		}
		String msg = JsonUtils.bean2Json(processStatusList.get(0));
		ImClientProtocolBO.getIntance().send(msg,2);
    }
}
