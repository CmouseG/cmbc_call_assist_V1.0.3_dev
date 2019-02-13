package com.guiji.wechat.service;

import com.guiji.wechat.service.api.CallbackHandlerApi;
import com.guiji.wechat.service.strategies.EventMsgHandleStrategy;
import com.guiji.wechat.util.enums.MsgTypeEnum;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

import static com.guiji.wechat.util.constants.CallbackParameterNameConstant.MSG_TYPE;

@Service
public class CallbackHandlerImpl implements CallbackHandlerApi {

    @Resource
    private EventMsgHandleStrategy eventMsgHandleStrategy;

    @Override
    public String handle(Map<String, String> callbackMessage) {

        String msgType = callbackMessage.get(MSG_TYPE);

        MsgTypeEnum msgTypeEnum = MsgTypeEnum.getEnumByKey(msgType);

        if (null == msgTypeEnum) {
            return "no match message type:" + msgType;
        }

        switch (msgTypeEnum) {
            case EVENT:
                return eventMsgHandleStrategy.handle(callbackMessage);
            case TEXT:
                // 处理文本消息
        }
        return "no handler";

    }
}
