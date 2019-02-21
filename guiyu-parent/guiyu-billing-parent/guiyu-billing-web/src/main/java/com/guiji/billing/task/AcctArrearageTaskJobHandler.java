package com.guiji.billing.task;

import com.guiji.auth.api.IAuth;
import com.guiji.billing.service.AcctNotifyService;
import com.guiji.billing.service.BillingUserAcctService;
import com.guiji.utils.JsonUtils;
import com.guiji.vo.ArrearageNotifyVo;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 定时任务，查询欠费企业用户，消息通知
 */
@Component
@JobHandler(value="acctArrearageTaskJobHandler")
public class AcctArrearageTaskJobHandler extends IJobHandler {

    private Logger logger = LoggerFactory.getLogger(AcctArrearageTaskJobHandler.class);

    @Autowired
    private BillingUserAcctService billingUserAcctService;

    @Autowired
    private AcctNotifyService acctNotifyService;

    @Autowired
    private IAuth iAuth;

    @Override
    public ReturnT<String> execute(String param) throws Exception {
        //查询欠费企业的用户列表
        ArrearageNotifyVo arrearage = billingUserAcctService.queryArrearageUserList();
        logger.info("定时任务通知欠费消息:{}", null != arrearage?JsonUtils.bean2Json(arrearage):null);
        if(null != arrearage && null != arrearage.getUserIdList()){
            //通知欠费消息
            acctNotifyService.notifyArrearage(arrearage);
        }
        return ReturnT.SUCCESS;
    }
}
