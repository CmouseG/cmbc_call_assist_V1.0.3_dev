package com.guiji.listener;

import com.guiji.service.AgentService;
import com.guiji.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class DelAgentMembersListener {
    @Autowired
    AgentService agentService;
    @RabbitListener(queues = "DelAgentMembers.direct.Auth")
    @RabbitHandler
    public void process(String message) {
        try {
            List<Long> list= JsonUtils.json2List(message,Long.class);
            if (list!=null&&list.size()>0) {
                log.info("接收删除坐席用MQ监听消息:[{}]", list);
                 agentService.delAgentMembers(list);
            }
        }catch (Exception e){
            log.error("处理删除坐席队列数据出现异常",e);
        }
    }
}
