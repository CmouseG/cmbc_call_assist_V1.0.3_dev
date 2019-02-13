package com.guiji.wechat.service.strategies;

import com.alibaba.fastjson.JSON;
import com.guiji.guiyu.message.component.FanoutSender;
import com.guiji.wechat.dtos.EventMsgReqDto;
import com.guiji.wechat.dtos.WeChatUserDto;
import com.guiji.wechat.messages.UserBindWeChatMessage;
import com.guiji.wechat.service.api.WeChatCommonApi;
import com.guiji.wechat.util.enums.EventTypeEnum;
import com.guiji.wechat.util.enums.MsgTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

import static com.guiji.wechat.util.constants.CallbackParameterNameConstant.*;
import static com.guiji.wechat.util.constants.RabbitMqConstant.USER_BIND_WECHAT_EXCHANGE;

@Component("eventMsgHandleStrategy")
public class EventMsgHandleStrategy extends MsgHandleStrategy {

    @Resource
    private FanoutSender fanoutSender;

    @Resource
    private WeChatCommonApi weChatCommonApi;

    private Logger logger = LoggerFactory.getLogger(EventMsgHandleStrategy.class);

    @Override
    public String handle(Map<String, String> callbackMessage) {

        EventMsgReqDto eventMsgReqDto = convertMsgToDto(callbackMessage);

        String replyMessage = "no match event type:";

        EventTypeEnum eventTypeEnum = eventMsgReqDto.getEventTypeEnum();
        if (null == eventTypeEnum) {
            return replyMessage;
        }

        switch (eventTypeEnum) {
            case SUBSCRIBE:
                replyMessage = handleSubscribeEvent(eventMsgReqDto);
                break;
            case SCAN:
                replyMessage = handleScanEvent(eventMsgReqDto);
                break;
            case UNSUBSCRIBE:
                // TODO: 19-1-26
                break;
        }

        return replyMessage;
    }

    private String handleSubscribeEvent(EventMsgReqDto eventMsgReqDto) {

        sendUserBindWeChatMessage(eventMsgReqDto);
        return buildUserBindWeChatReply(eventMsgReqDto);
    }

    private String handleScanEvent(EventMsgReqDto eventMsgReqDto) {

        sendUserBindWeChatMessage(eventMsgReqDto);
        return buildUserBindWeChatReply(eventMsgReqDto);
    }

    private void sendUserBindWeChatMessage(EventMsgReqDto eventMsgReqDto) {
        UserBindWeChatMessage message = new UserBindWeChatMessage();

        String openId = eventMsgReqDto.getFromUserName();

        WeChatUserDto weChatUserDto = weChatCommonApi.getWeChatUserInfo(openId);

        message.setWeChatNumber(eventMsgReqDto.getToUserName());
        message.setWeChatNickName(weChatUserDto.getNickname());
        message.setOpenId(openId);
        message.setBindTime(eventMsgReqDto.getCreateTime());
        message.setCallbackParameter(eventMsgReqDto.getEventKey());

        fanoutSender.send(USER_BIND_WECHAT_EXCHANGE, JSON.toJSONString(message));
        logger.info("user bind weChat message:{}", JSON.toJSONString(message));
    }

    private String buildUserBindWeChatReply(EventMsgReqDto eventMsgReqDto) {

        return "success";
//        TextReplyVO textReplyVO = new TextReplyVO();
//        textReplyVO.setFromUserName(eventMsgReqDto.getToUserName());
//        textReplyVO.setToUserName(eventMsgReqDto.getFromUserName());
//        textReplyVO.setCreateTime(System.currentTimeMillis());
//        textReplyVO.setMsgType(MsgTypeEnum.TEXT.getKey());
//        textReplyVO.setContent("回复文本消息");// TODO: 19-1-26
//        return XmlUtil.objectToXml(textReplyVO);
    }

    private EventMsgReqDto convertMsgToDto(Map<String, String> callbackMessage) {

        EventMsgReqDto eventMsgReqDto = new EventMsgReqDto();

        eventMsgReqDto.setToUserName(callbackMessage.get(TO_USER_NAME));
        eventMsgReqDto.setFromUserName(callbackMessage.get(FROM_USER_NAME));
        eventMsgReqDto.setCreateTime(new Date(Long.parseLong(callbackMessage.get(CREATE_TIME))));
        eventMsgReqDto.setMsgTypeEnum(MsgTypeEnum.getEnumByKey(callbackMessage.get(MSG_TYPE)));
        eventMsgReqDto.setEventTypeEnum(EventTypeEnum.getEnumByKey(callbackMessage.get(EVENT)));
        eventMsgReqDto.setEventKey(callbackMessage.get(EVENT_KEY));
        eventMsgReqDto.setTicket(callbackMessage.get(TICKET));

        return eventMsgReqDto;
    }
}
