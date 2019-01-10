package com.guiji.dispatch.pushcallcenter;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.guiji.dispatch.bean.MQSuccPhoneDto;
import com.guiji.dispatch.dao.PushRecordsMapper;
import com.guiji.dispatch.dao.entity.PushRecords;
import com.guiji.dispatch.dao.entity.PushRecordsExample;
import com.guiji.dispatch.util.Constant;
import com.guiji.utils.JsonUtils;
import com.rabbitmq.client.Channel;

/**
 * 呼叫中心回调记录
 * @author Administrator
 *
 */
@Component
@RabbitListener(queues = "dispatch.CallBackEvent")
public class CallBack4MQListener {
	@Autowired
	private IPushPhonesHandler pushHandler;
	@Autowired
	private PushRecordsMapper recordMapper;

	@RabbitHandler
	public void process(String message, Channel channel, Message message2) {
		// 呼叫中心回调之后去获取最新的并发数量和呼叫中心的负载情况推送对应数量的号码
		MQSuccPhoneDto mqSuccPhoneDto = JsonUtils.json2Bean(message, MQSuccPhoneDto.class);
		PushRecordsExample ex = new PushRecordsExample();
		ex.createCriteria().andPlanuuidEqualTo(mqSuccPhoneDto.getPlanuuid())
				.andCallbackStatusEqualTo(Constant.NOCALLBACK);
		PushRecords re = new PushRecords();
		//设置已经回调的状态
		re.setCallbackStatus(Constant.CALLBACKED);
		recordMapper.updateByExampleSelective(re, ex);
		// 每次回调都去判断当前并发数量是否负载充分
		pushHandler.pushHandler();
	}
}
