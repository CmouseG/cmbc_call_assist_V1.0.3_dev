package com.guiji.dispatch.service;

import com.guiji.dispatch.bean.sendMsgDto;

public interface IMessageService {
	
	public boolean insertMessMq(sendMsgDto msgDto);
}
