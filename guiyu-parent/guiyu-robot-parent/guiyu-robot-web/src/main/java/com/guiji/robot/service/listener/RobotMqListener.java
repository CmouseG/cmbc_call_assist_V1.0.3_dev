package com.guiji.robot.service.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.guiji.common.model.process.ProcessTypeEnum;
import com.guiji.guiyu.message.model.PublishBotstenceResultMsgVO;
import com.guiji.robot.service.impl.AiCacheService;
import com.guiji.utils.JsonUtils;

/** 
* @ClassName: RobotMqListener 
* @Description: 机器人能力中心MQ监听服务
* @date 2018年12月3日 下午2:33:14 
* @version V1.0  
*/
@Component
@RabbitListener(queues = "fanoutPublishBotstence.ROBOT")
public class RobotMqListener {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	AiCacheService aiCacheService;
	
	
	/**
	 * 监听ROBOT队列消息，目前主要处理：
	 * 1、
	 * @param message
	 */
    @RabbitHandler
    public void process(String message) {
        PublishBotstenceResultMsgVO publishBotstenceResultMsgVO = JsonUtils.json2Bean(message,PublishBotstenceResultMsgVO.class);
        if (publishBotstenceResultMsgVO.getProcessTypeEnum() == ProcessTypeEnum.ROBOT) {
        	logger.info("接收MQ监听消息{}",publishBotstenceResultMsgVO);
        	//话术模板发布，如果在内存中已经有的模板，需要将缓存中模板清空，重新获取
        	aiCacheService.updateHsReplace(publishBotstenceResultMsgVO.getTmplId());
        	logger.info("话术模板缓存更新完成...");
        }

    }
}
