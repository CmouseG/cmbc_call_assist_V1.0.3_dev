package com.guiji.billing.task;

import com.guiji.billing.dao.mapper.ext.BillingUserAcctMapper;
import com.guiji.billing.service.msg.MsgNotifyComponent;
import com.guiji.billing.vo.UserAcctThresholdVo;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 账户余额低于阈值消息通知
 */
@Component
@JobHandler(value="thresholdNotifyTaskJob")
public class ThresholdNotifyTaskJob extends IJobHandler {

    private Logger logger = LoggerFactory.getLogger(ThresholdNotifyTaskJob.class);

    @Autowired
    private BillingUserAcctMapper billingUserAcctMapper;

    @Autowired
    private MsgNotifyComponent msgNotifyComponent;

    /**
     * 账户余额低于阈值消息通知
     * @param param
     * @return
     * @throws Exception
     */
    @Override
    public ReturnT<String> execute(String param) throws Exception {
        //查询低于阈值列表
        List<UserAcctThresholdVo> thresholdVoList =  billingUserAcctMapper.queryLowerThresholdAcctList();
        if(null != thresholdVoList && thresholdVoList.size()>0) {
            //消息通知
            msgNotifyComponent.notifyByThreshold(thresholdVoList);
        }
        return ReturnT.SUCCESS;
    }
}
