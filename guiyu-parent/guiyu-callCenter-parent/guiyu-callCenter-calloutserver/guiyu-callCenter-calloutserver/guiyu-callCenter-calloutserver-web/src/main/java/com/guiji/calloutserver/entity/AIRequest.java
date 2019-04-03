package com.guiji.calloutserver.entity;

import com.guiji.calloutserver.eventbus.event.AsrCustomerEvent;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class AIRequest {
    /**
     * 对应的FreeSWITCH通道ID
     */
    private String uuid;

    /**
     * 客户说话文本
     */
    private String sentence;

    private String state;


    /**
     * 是否在调用识别请求前，调用isMatch判断是否关键词匹配
     * 默认调用
     */
//    private boolean isCallMatch = true;

    public AIRequest(AsrCustomerEvent event){
        //AIRequest.builder().uuid(event.getUuid()).sentence(event.getAsrText()).state("state").isCallMatch(isMatch).build();
        this.uuid = event.getUuid();
        this.sentence = event.getAsrText();
        this.state = "state";
        //默认调用sellbot的is_match检测
//        this.isCallMatch=true;

        //如果是系统自己产生的事件，则绕过is_match检测，否则仍旧无法激活对话
//        if(event.isGenerated()){
//            log.info("收到系统自己产生的AliAsr事件，准备进一步激活会话");
//            this.isCallMatch = false;
//        }
    }
}
