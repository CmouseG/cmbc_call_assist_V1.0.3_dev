package com.guiji.dispatch.state;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.guiji.dispatch.dao.DispatchPlanMapper;
import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.dispatch.dao.entity.DispatchPlanExample;
import com.guiji.dispatch.util.Constant;
import com.guiji.robot.model.TtsComposeCheckRsp;
import com.guiji.utils.JsonUtils;
import com.rabbitmq.client.Channel;



@Component
@RabbitListener(queues = "robot.ttsok")
public class TtsSuccMQListener {
	private static Logger logger = LoggerFactory.getLogger(TtsSuccMQListener.class);

	@Autowired
	private DispatchPlanMapper dispatchMapper;
	@RabbitHandler
	public void process(String message,Channel channel,Message message2) {
		TtsComposeCheckRsp checkRes = JsonUtils.json2Bean(message, TtsComposeCheckRsp.class);
		logger.info("接受到当前合成成功的tts:"+checkRes.getSeqId()+"----"+checkRes.getStatus());
		DispatchPlanExample ex = new DispatchPlanExample();
		ex.createCriteria().andPlanUuidEqualTo(checkRes.getSeqId());
		List<DispatchPlan> selectByExample = dispatchMapper.selectByExample(ex);
		logger.info("当前ttsOK查询数据结果:"+selectByExample.size());
		if(selectByExample.size()>0){
			DispatchPlan dispatchPlan = selectByExample.get(0);
			dispatchPlan.setFlag(Constant.IS_FLAG_2);
			dispatchMapper.updateByPrimaryKeySelective(dispatchPlan);
		}
	}
}

