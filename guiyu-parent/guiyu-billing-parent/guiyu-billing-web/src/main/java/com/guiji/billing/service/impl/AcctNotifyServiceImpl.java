package com.guiji.billing.service.impl;

import com.guiji.billing.constants.MqConstant;
import com.guiji.billing.vo.ArrearageNotifyVo;
import com.guiji.billing.service.AcctNotifyService;
import com.guiji.billing.utils.IdWorker;
import com.guiji.guiyu.message.component.FanoutSender;
import com.guiji.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 账户通知类
 */
@Service
public class AcctNotifyServiceImpl implements AcctNotifyService {

    private Logger logger = LoggerFactory.getLogger(AcctNotifyServiceImpl.class);

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private FanoutSender fanoutSender;

    /**
     * 通知欠费
     * @param arrearageNotifyDto
     * @return
     */
    @Override
    public boolean notifyArrearage(com.guiji.vo.ArrearageNotifyVo arrearageNotifyDto) {
        boolean bool = false;
        if(null != arrearageNotifyDto && null != arrearageNotifyDto.getUserIdList()){
            String message = JsonUtils.bean2Json(arrearageNotifyDto);
            String logId = idWorker.nextId();
            try {
                logger.info("欠费通知开始 logId:{},入参：{}", logId, message);
                //通知路由，广播
                fanoutSender.send(MqConstant.fanoutPushBilling.FANOUTPUSHBILLING, message);
                logger.info("欠费通知结束 logId:{}", logId);
                bool = true;
            }catch(Exception e){
                logger.error("通知欠费异常，logId:" + logId, e);
            }
        }
        return bool;
    }
}
