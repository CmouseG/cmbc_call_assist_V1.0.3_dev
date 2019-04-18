package com.guiji.listener;

import com.guiji.service.AgentService;
import com.guiji.toagentserver.entity.AgentMembrVO;
import com.guiji.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class SyncAgentMembersListener {
    @Autowired
    AgentService agentService;
    @RabbitListener(queues = "SyncAgentMembers.direct.Auth")
    @RabbitHandler
    public void process(String message) {
        try {
            List<AgentMembrVO> agentMembers =JsonUtils.json2List(message,AgentMembrVO.class);
            //log.info("收到同步坐席的MQ");
            if (agentMembers!=null&&agentMembers.size()>0) {
                log.info("收到同步坐席的MQ:[{}]",agentMembers.toString());
                agentService.syncAgentMembers(agentMembers);
            }
        }catch (Exception e){
            log.error("处理同步坐席队列数据出现异常",e);
        }
    }
}
