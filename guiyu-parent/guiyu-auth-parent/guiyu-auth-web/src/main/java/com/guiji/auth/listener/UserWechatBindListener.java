package com.guiji.auth.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.guiji.auth.model.UserIdVo;
import com.guiji.auth.service.UserService;
import com.guiji.utils.JsonUtils;
import com.guiji.wechat.api.WeChatApi;
import com.guiji.wechat.messages.UserBindWeChatMessage;
import com.guiji.wechat.vo.SendMsgReqVO;

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
	@Autowired
    private WeChatApi weChatApi;

	/**
	 * 监听ROBOT队列消息，目前主要处理：
	 * 1、
	 * @param message
	 */
	@RabbitHandler
	public void process(String message) {
		try {
			UserBindWeChatMessage userBindWeChatMessage = JsonUtils.json2Bean(message,UserBindWeChatMessage.class);
			if (userBindWeChatMessage != null) {
				logger.info("接收MQ监听消息{}",message);
				UserIdVo userIdVo = JsonUtils.json2Bean(userBindWeChatMessage.getCallbackParameter(), UserIdVo.class);
				//用户绑定微信
				userService.userBindWechat(Long.parseLong(userIdVo.getUserId()),userBindWeChatMessage.getWeChatNickName(),userBindWeChatMessage.getOpenId());
				logger.info("用户微信绑定完成");
				SendMsgReqVO sendMsgReqVO = new SendMsgReqVO();
				sendMsgReqVO.setOpenID(userBindWeChatMessage.getOpenId());
			    sendMsgReqVO.setTemplateId("7cD1dvma95TfQSFrwp5QozqznAFbvN4CA9vQKIZFzCQ"); //绑定模版
			    sendMsgReqVO.addData("first", "绑定硅基帐号成功");
			    sendMsgReqVO.addData("keyword1", userService.getUserById(Long.parseLong(userIdVo.getUserId())).getUsername());
			    sendMsgReqVO.addData("keyword2", "该微信成功绑定硅基帐号");
			    sendMsgReqVO.addData("remark", "解绑方式：点击后台个人中心解绑");
				weChatApi.send(sendMsgReqVO);
			}
		} catch (Exception e) {
			logger.error("消息队列监听失败，错误信息{}",e.getMessage());
		}
	}
}
