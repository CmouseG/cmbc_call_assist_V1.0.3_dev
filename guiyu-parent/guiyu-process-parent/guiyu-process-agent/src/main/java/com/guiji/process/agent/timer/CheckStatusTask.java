package com.guiji.process.agent.timer;

import com.guiji.process.agent.model.CfgProcessVO;
import com.guiji.process.agent.service.ProcessCfgService;
import com.guiji.process.agent.util.ProcessUtil;
import com.guiji.process.core.ProcessMsgHandler;
import com.guiji.process.core.message.CmdMessageVO;
import com.guiji.process.core.vo.CmdTypeEnum;
import com.guiji.common.model.process.ProcessInstanceVO;
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
	@Scheduled(fixedRate = 1000*30)
    public void checkStatusTask() throws InterruptedException, UnsupportedEncodingException, UnknownHostException {
		Map<Integer, CfgProcessVO> cfgMap = ProcessCfgService.getIntance().cfgMap;
		String ip = ProcessUtil.getLocalIp();
		if (cfgMap == null) {

			return;
		}

		CmdMessageVO newCmdMsg = null;
		for (Map.Entry<Integer, CfgProcessVO> ent : cfgMap.entrySet()) {

			newCmdMsg = new CmdMessageVO();
			newCmdMsg.setCmdType(CmdTypeEnum.HEALTH);

			ProcessInstanceVO processInstanceVO = new ProcessInstanceVO();
			processInstanceVO.setIp(ip);
			processInstanceVO.setType(ent.getValue().getProcessTypeEnum());
			processInstanceVO.setPort(ent.getKey());
			processInstanceVO.setName(ent.getValue().getName());
			newCmdMsg.setProcessInstanceVO(processInstanceVO);

			ProcessMsgHandler.getInstance().add(newCmdMsg);
		}
    }
}
