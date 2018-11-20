package com.guiji.process.agent.timer;

import com.guiji.process.agent.handler.ImClientProtocolBO;
import com.guiji.process.agent.util.ProcessUtil;
import com.guiji.process.core.message.CmdMessageVO;
import com.guiji.process.core.vo.CmdTypeEnum;
import com.guiji.process.core.vo.DeviceStatusEnum;
import com.guiji.process.core.vo.DeviceTypeEnum;
import com.guiji.process.core.vo.ProcessInstanceVO;
import com.guiji.utils.JsonUtils;
import com.guiji.utils.StrUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class CheckStatusTask {

	private static Logger logger = LoggerFactory.getLogger(CheckStatusTask.class);

	//定时任务，启动时运行（每1分钟执行一次）
	@Scheduled(fixedRate = 1000*60)
    public void checkStatusTask() throws InterruptedException, UnsupportedEncodingException, UnknownHostException {
		CmdMessageVO cmdMessageVO = new CmdMessageVO();
		cmdMessageVO.setCmdType(CmdTypeEnum.HEALTH);
		ProcessInstanceVO processInstanceVO = new ProcessInstanceVO();
		processInstanceVO.setIp(Inet4Address.getLocalHost().getHostAddress());
		processInstanceVO.setType(DeviceTypeEnum.SELLBOT);
		processInstanceVO.setPort(18001);
		boolean isUp = ProcessUtil.checkRun(18001);
		if (isUp) {
			processInstanceVO.setStatus(DeviceStatusEnum.UP);
		} else {
			processInstanceVO.setStatus(DeviceStatusEnum.DOWN);
		}
		cmdMessageVO.setProcessInstanceVO(processInstanceVO);
		String msg = JsonUtils.bean2Json(cmdMessageVO);
		ImClientProtocolBO.getIntance().send(msg,1);
    }
}
