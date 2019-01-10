package com.guiji.dispatch.pushcallcenter;

import com.guiji.dispatch.bean.MQSuccPhoneDto;

public interface SuccessPhoneMQService {
	boolean insertSuccesPhone4BusinessMQ(MQSuccPhoneDto mqSuccPhoneDto);
	
	boolean insertCallBack4MQ(MQSuccPhoneDto mqSuccPhoneDto);
}
