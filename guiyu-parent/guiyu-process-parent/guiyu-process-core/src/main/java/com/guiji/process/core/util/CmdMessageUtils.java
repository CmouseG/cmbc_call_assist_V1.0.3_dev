package com.guiji.process.core.util;

import com.guiji.common.model.process.ProcessInstanceVO;
import com.guiji.common.model.process.ProcessStatusEnum;
import com.guiji.common.model.process.ProcessTypeEnum;
import com.guiji.process.core.message.CmdMessageVO;
import com.guiji.process.core.message.CmdMsgTypeEnum;
import com.guiji.process.core.message.CmdProtoMessage;
import com.guiji.process.core.vo.CmdTypeEnum;
import io.netty.buffer.ByteBuf;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.util.Arrays;

public class CmdMessageUtils {

	public static CmdProtoMessage.ProtoMessage.Builder convert(CmdMessageVO cmdMessageVO) {

		CmdProtoMessage.ProtoMessage.Builder builder = CmdProtoMessage.ProtoMessage.newBuilder();
		if (cmdMessageVO != null) {
			builder.setReqKey(cmdMessageVO.getReqKey()== null?"":cmdMessageVO.getReqKey());
			if(cmdMessageVO.getMsgTypeEnum() != null)
			{
				builder.setMsgType(cmdMessageVO.getMsgTypeEnum().getValue());
			}
			if(cmdMessageVO.getCmdType() != null)
			{
				builder.setCmdType(cmdMessageVO.getCmdType().getValue());
			}
			String parameters = StringUtils.join(cmdMessageVO.getParameters(),",");
			builder.setParameters(parameters==null?"":parameters);

			if (cmdMessageVO.getProcessInstanceVO() != null) {

				builder.setPort(cmdMessageVO.getProcessInstanceVO().getPort());
				builder.setProcessKey(cmdMessageVO.getProcessInstanceVO().getProcessKey()==null?"":cmdMessageVO.getProcessInstanceVO().getProcessKey());
				builder.setName(cmdMessageVO.getProcessInstanceVO().getName()==null?"":cmdMessageVO.getProcessInstanceVO().getName());
				if (cmdMessageVO.getProcessInstanceVO().getType() != null) {
					builder.setProcessType(cmdMessageVO.getProcessInstanceVO().getType().getValue());
				}
				if (cmdMessageVO.getProcessInstanceVO().getStatus() != null) {
					builder.setStatus(cmdMessageVO.getProcessInstanceVO().getStatus().getValue());
				}
			}
			builder.setCmdResult(cmdMessageVO.getCommandResult()==null?"":cmdMessageVO.getCommandResult());
			builder.setCmdResultDesc(cmdMessageVO.getCommandResultDesc()==null?"":cmdMessageVO.getCommandResultDesc());

		}


		return builder;
	}

	public static CmdMessageVO convert(CmdProtoMessage.ProtoMessage message) {
		CmdMessageVO cmdMessageVO = new CmdMessageVO();
		cmdMessageVO.setReqKey(message.getReqKey());
		cmdMessageVO.setParameters(Arrays.asList(message.getParameters().split(",")));
		cmdMessageVO.setCmdType(CmdTypeEnum.valueOf(message.getCmdType()));
		cmdMessageVO.setMsgTypeEnum(CmdMsgTypeEnum.valueOf(message.getMsgType()));
		cmdMessageVO.setCommandResult(message.getCmdResult());
		cmdMessageVO.setCommandResultDesc(message.getCmdResultDesc());

		ProcessInstanceVO processInstanceVO = new ProcessInstanceVO();
		processInstanceVO.setPort(message.getPort());
		processInstanceVO.setProcessKey(message.getProcessKey());
		processInstanceVO.setStatus(ProcessStatusEnum.valueOf(message.getStatus()));
		processInstanceVO.setType(ProcessTypeEnum.valueOf(message.getProcessType()));

		// TODO
		processInstanceVO.setName(message.getName());
		cmdMessageVO.setProcessInstanceVO(processInstanceVO);

		return cmdMessageVO;
	}
}
