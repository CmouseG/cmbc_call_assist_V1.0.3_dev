package com.guiji.wechat.service;

import com.alibaba.fastjson.JSON;
import com.guiji.wechat.service.api.CallbackHandlerApi;
import com.guiji.wechat.service.strategies.EventMsgHandleStrategy;
import com.guiji.wechat.util.enums.MsgTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

import static com.guiji.wechat.util.constants.CallbackParameterNameConstant.MSG_TYPE;
import static com.guiji.wechat.util.constants.WeChatConstant.DEFAULT_SUCCESS;

@Service
public class CallbackHandlerImpl implements CallbackHandlerApi {

    private Logger logger = LoggerFactory.getLogger(CallbackHandlerImpl.class);

    @Resource
    private EventMsgHandleStrategy eventMsgHandleStrategy;

    @Override
    public String handle(Map<String, String> callbackMessage) {

        logger.info("weChat callback map:{}", JSON.toJSONString(callbackMessage));

        String msgType = callbackMessage.get(MSG_TYPE);

        MsgTypeEnum msgTypeEnum = MsgTypeEnum.getEnumByKey(msgType);

        if (null == msgTypeEnum) {
            logger.error("no match callback message type:{}", msgType);
            return DEFAULT_SUCCESS;
        }

        switch (msgTypeEnum) {
            case EVENT:
                return eventMsgHandleStrategy.handle(callbackMessage);
            case TEXT:
                return DEFAULT_SUCCESS;// TODO: 19-2-1   // 处理文本消息
            default:
                logger.info("no suitable message type handler!");
                return DEFAULT_SUCCESS;
        }
    }
}
