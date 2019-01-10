package com.guiji.dispatch.sms;

import com.guiji.dispatch.bean.sendMsgDto;

public interface IMessageService {
	
	public boolean insertMessMq(sendMsgDto msgDto);
}
