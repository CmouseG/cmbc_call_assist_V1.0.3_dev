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
import com.guiji.utils.RedisUtil;
import com.rabbitmq.client.Channel;

/**
 * 呼叫中心回调记录
 * 
 * @author Administrator
 *
 */
@Component
@RabbitListener(queues = "dispatch.CallBackEvent")
public class CallBack4MQListener {
	@Autowired
	private PushRecordsMapper recordMapper;
	@Autowired
	private RedisUtil redisUtil;

	private static final String REDIS_CALL_QUEUE_USER_LINE_ROBOT_COUNT = "REDIS_CALL_QUEUE_USER_LINE_ROBOT_COUNT";

	@RabbitHandler
	public void process(String message, Channel channel, Message message2) {
		// 呼叫中心回调之后去获取最新的并发数量和呼叫中心的负载情况推送对应数量的号码
		MQSuccPhoneDto mqSuccPhoneDto = JsonUtils.json2Bean(message, MQSuccPhoneDto.class);
		PushRecordsExample ex = new PushRecordsExample();
		ex.createCriteria().andPlanuuidEqualTo(mqSuccPhoneDto.getPlanuuid())
				.andCallbackStatusEqualTo(Constant.NOCALLBACK);
		PushRecords re = new PushRecords();
		// 设置已经回调的状态
		re.setCallbackStatus(Constant.CALLBACKED);
		int result = recordMapper.updateByExampleSelective(re, ex);
		if (result > 0) {
			String queueCount = REDIS_CALL_QUEUE_USER_LINE_ROBOT_COUNT+mqSuccPhoneDto.getUserId()+"_"+mqSuccPhoneDto.getLineId()+"_"+mqSuccPhoneDto.getTempId();
			Integer currentCount = (Integer) redisUtil.get(queueCount);
			if (currentCount > 0) {
//				currentCount = currentCount - 1;
//				redisUtil.set(queueCount, currentCount);
				redisUtil.decr(queueCount, 1);
			}


		}
	}
}
