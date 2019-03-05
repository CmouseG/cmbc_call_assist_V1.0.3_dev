package com.guiji.wechat.service.strategies;

import com.alibaba.fastjson.JSON;
import com.guiji.guiyu.message.component.FanoutSender;
import com.guiji.wechat.dtos.EventMsgReqDto;
import com.guiji.wechat.dtos.WeChatUserDto;
import com.guiji.wechat.messages.UserBindWeChatMessage;
import com.guiji.wechat.service.api.WeChatCommonApi;
import com.guiji.wechat.util.XmlUtil;
import com.guiji.wechat.util.enums.EventTypeEnum;
import com.guiji.wechat.util.enums.MsgTypeEnum;
import com.guiji.wechat.util.reply.BaseReply;
import com.guiji.wechat.util.reply.TextReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

import static com.guiji.wechat.util.constants.CallbackParameterNameConstant.*;
import static com.guiji.wechat.util.constants.RabbitMqConstant.USER_BIND_WECHAT_EXCHANGE;
import static com.guiji.wechat.util.constants.WeChatConstant.DEFAULT_SUBSCRIBE_REPLY;
import static com.guiji.wechat.util.constants.WeChatConstant.DEFAULT_SUCCESS;

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

        EventTypeEnum eventTypeEnum = eventMsgReqDto.getEventTypeEnum();

        if (null == eventTypeEnum) {
            logger.error("no match event type!");
            return DEFAULT_SUCCESS;
        }

        switch (eventTypeEnum) {
            case SUBSCRIBE:
                return handleSubscribeEvent(eventMsgReqDto);
            case SCAN:
                return handleScanEvent(eventMsgReqDto);
            case UNSUBSCRIBE:
                return DEFAULT_SUCCESS;// TODO: 19-2-25
            default:
                return DEFAULT_SUCCESS;
        }
    }

    private String handleSubscribeEvent(EventMsgReqDto eventMsgReqDto) {

        logger.info("user subscribe event:{}", JSON.toJSONString(eventMsgReqDto));

        sendUserBindWeChatMessage(eventMsgReqDto);

        BaseReply textReply = TextReply.build()
                .setContent(DEFAULT_SUBSCRIBE_REPLY)
                .setToUserOpenId(eventMsgReqDto.getFromUserName())
                .setWeChatOpenId(eventMsgReqDto.getToUserName());

        return XmlUtil.objectToXml(textReply);
    }

    private String handleScanEvent(EventMsgReqDto eventMsgReqDto) {

        logger.info("user scan event:{}", JSON.toJSONString(eventMsgReqDto));

        sendUserBindWeChatMessage(eventMsgReqDto);

        return DEFAULT_SUCCESS;
    }

    private void sendUserBindWeChatMessage(EventMsgReqDto eventMsgReqDto) {
        UserBindWeChatMessage message = new UserBindWeChatMessage();

        if(StringUtils.isEmpty(eventMsgReqDto.getCallbackParameter())){
            return;
        }

        String openId = eventMsgReqDto.getFromUserName();

        WeChatUserDto weChatUserDto = weChatCommonApi.getWeChatUserInfo(openId);

        message.setWeChatNumber(eventMsgReqDto.getToUserName());
        message.setWeChatNickName(weChatUserDto.getNickname());
        message.setOpenId(openId);
        message.setBindTime(eventMsgReqDto.getCreateTime());
        message.setCallbackParameter(eventMsgReqDto.getCallbackParameter());

        fanoutSender.send(USER_BIND_WECHAT_EXCHANGE, JSON.toJSONString(message));
        logger.info("user bind weChat message:{}", JSON.toJSONString(message));
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
