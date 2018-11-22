package com.guiji.process.agent.timer;

import com.guiji.process.agent.model.CfgNodeVO;
import com.guiji.process.agent.service.ProcessCfgService;
import com.guiji.process.agent.util.ProcessUtil;
import com.guiji.process.core.vo.CmdTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.Map;

@Component
public class CheckStatusTask {

	private static Logger logger = LoggerFactory.getLogger(CheckStatusTask.class);

	//定时任务，启动时运行（每1分钟执行一次）
	@Scheduled(fixedRate = 1000*2)
    public void checkStatusTask() throws InterruptedException, UnsupportedEncodingException, UnknownHostException {
		Map<Integer, CfgNodeVO> cfgMap = ProcessCfgService.getIntance().cfgMap;
		if (cfgMap != null) {
			for (Integer key : cfgMap.keySet()) {
				ProcessUtil.sendHealth(key,cfgMap.get(key).getDeviceTypeEnum(),cfgMap.get(key).getCfgNodeOper(CmdTypeEnum.START));
			}
		}
    }
}
