package com.guiji.dispatch.sms;

import com.guiji.dispatch.bean.MQSuccPhoneDto;
import com.guiji.dispatch.dao.entity.DispatchPlan;

public interface SendSmsInterface {
	public void execute(DispatchPlan dis,MQSuccPhoneDto mqSuccPhoneDto);
}
