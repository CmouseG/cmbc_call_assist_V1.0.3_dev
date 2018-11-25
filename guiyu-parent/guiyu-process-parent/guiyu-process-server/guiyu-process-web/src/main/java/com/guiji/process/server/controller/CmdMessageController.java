package com.guiji.process.server.controller;

import com.guiji.process.core.ProcessMsgHandler;
import com.guiji.process.core.message.CmdMessageVO;
import com.guiji.process.core.vo.CmdTypeEnum;
import com.guiji.common.model.process.ProcessInstanceVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息API,提供消息的基本操作
 * @author yinjihuan
 *
 */
@RestController
@RequestMapping("/cmd")
public class CmdMessageController {

	/**
	 * cmd
	 * @param processInstances	进程
	 * @param cmdTypeEnum	命令
	 * @return
	 */
	@GetMapping("/cmd")
	public Object cmd(List<ProcessInstanceVO> processInstances, CmdTypeEnum cmdTypeEnum) {

		List<CmdMessageVO> cmdMessageVOs = new ArrayList<CmdMessageVO>();
		for (ProcessInstanceVO processInstance:processInstances) {
			CmdMessageVO cmdMessageVO = new CmdMessageVO();

			cmdMessageVO.setProcessInstanceVO(processInstance);
			cmdMessageVO.setCmdType(cmdTypeEnum);

			cmdMessageVOs.add(cmdMessageVO);
		}

		if(!cmdMessageVOs.isEmpty())
		{
			ProcessMsgHandler.getInstance().add(cmdMessageVOs);
		}

		return "success";
	}
}
