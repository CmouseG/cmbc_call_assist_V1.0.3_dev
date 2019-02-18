package com.guiji.billing.task;

import com.guiji.billing.entity.BillingUserAcctBean;
import com.guiji.billing.service.BillingUserAcctService;
import com.guiji.billing.service.msg.MsgNotifyComponent;
import com.guiji.billing.vo.UserAcctThresholdVo;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 企业账户到期通知
 */
@Component
@JobHandler(value="expireDaysAcctTaskJob")
public class ExpireDaysAcctTaskJob extends IJobHandler {

    @Autowired
    private BillingUserAcctService billingUserAcctService;

    @Autowired
    private MsgNotifyComponent msgNotifyComponent;

    public final static int expireDays = 7;


    @Override
    public ReturnT<String> execute(String param) throws Exception {
        //查询到期expireDays天内的企业账户
        List<BillingUserAcctBean> acctList =  billingUserAcctService.queryExpireDaysAcctList(new Date(), expireDays);
        if(null != acctList && acctList.size()>0) {
            //消息通知
            msgNotifyComponent.notifyByExpireDay(acctList, expireDays);
        }
        return ReturnT.SUCCESS;
    }
}
