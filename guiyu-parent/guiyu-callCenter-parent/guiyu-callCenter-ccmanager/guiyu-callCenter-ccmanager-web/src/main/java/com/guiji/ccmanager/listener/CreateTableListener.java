package com.guiji.ccmanager.listener;

import com.guiji.callcenter.dao.MyCallOutPlanMapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * author:liyang
 * Date:2019/4/23 9:48
 * Description:
 */
@Component
public class CreateTableListener {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    MyCallOutPlanMapper myCallOutPlanMapper;

    /**
     *
     * @param message
     */
    @RabbitListener(queues = "AddOrgNotice.fanout.CallCenter")
    @RabbitHandler
    public void process(String message) {
        logger.info("创建新的组织机构Mq，收到消息{}", message);
        if(StringUtils.isEmpty(message)){
            logger.info("当前CreateTableOfShardingMQListener消费数据有问题");
            return;
        }
        Integer orgId = Integer.valueOf(message);

        if(orgId <= 0)
        {
            return;
        }

        try
        {
            myCallOutPlanMapper.createCallOutPlan(orgId);
            myCallOutPlanMapper.createCallOutDetail(orgId);
        } catch (Exception e)
        {
            logger.error("创建表出现异常",e);
        }
    }
}
