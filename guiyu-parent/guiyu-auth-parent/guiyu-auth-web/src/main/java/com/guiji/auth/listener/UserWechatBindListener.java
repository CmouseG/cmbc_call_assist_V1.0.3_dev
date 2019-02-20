package com.guiji.auth.listener;

import com.guiji.auth.model.UserIdVo;
import com.guiji.auth.service.UserService;
import com.guiji.common.model.process.ProcessTypeEnum;
import com.guiji.guiyu.message.model.PublishBotstenceResultMsgVO;
import com.guiji.utils.JsonUtils;
import com.guiji.wechat.messages.UserBindWeChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/** 
* @ClassName: UserWechatBindListener
* @Description: 用户中心监听微信绑定消息队列
* @date 2019年1月30日
* @version V1.0  
*/
@Component
@RabbitListener(bindings=@QueueBinding(value=@Queue(value="fanoutWechatUserBindQueue",durable = "true"),exchange=@Exchange(value="fanoutWechatUserBindExchange",type="fanout",durable = "true")))
//@RabbitListener(queues = "fanoutWechatUserBindQueue")
public class UserWechatBindListener {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private UserService userService;
	
	/**
	 * 监听ROBOT队列消息，目前主要处理：
	 * 1、
	 * @param message
	 */
    @RabbitHandler
    public void process(String message) {
        UserBindWeChatMessage userBindWeChatMessage = JsonUtils.json2Bean(message,UserBindWeChatMessage.class);
        if (userBindWeChatMessage != null) {
        	logger.info("接收MQ监听消息{}",message);
			UserIdVo userIdVo = JsonUtils.json2Bean(userBindWeChatMessage.getCallbackParameter(), UserIdVo.class);
        	//用户绑定微信
        	userService.userBindWechat(userIdVo.getUserId(),userBindWeChatMessage.getWeChatNickName(),userBindWeChatMessage.getOpenId());
        	logger.info("用户微信绑定完成");
        }

    }
}
