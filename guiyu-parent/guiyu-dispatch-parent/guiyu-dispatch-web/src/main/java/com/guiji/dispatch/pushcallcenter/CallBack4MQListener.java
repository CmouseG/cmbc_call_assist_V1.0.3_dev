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
			Integer currentCount = (Integer) redisUtil.get("REDIS_CURRENTLY_COUNT");
			if (currentCount > 0) {
				currentCount = currentCount - 1;
				redisUtil.set("REDIS_CURRENTLY_COUNT", currentCount);
			}

			Integer userIdCurrentCount = (Integer) redisUtil
					.get("REDIS_USERID_CURRENTLY_COUNT_" + mqSuccPhoneDto.getUserId());
			if (userIdCurrentCount > 0) {
				userIdCurrentCount = userIdCurrentCount - 1;
				redisUtil.set("REDIS_USERID_CURRENTLY_COUNT_" + mqSuccPhoneDto.getUserId(), userIdCurrentCount);
			}
		}
	}
}
