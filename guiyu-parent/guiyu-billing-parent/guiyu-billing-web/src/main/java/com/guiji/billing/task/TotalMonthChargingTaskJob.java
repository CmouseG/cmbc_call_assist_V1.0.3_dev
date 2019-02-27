package com.guiji.billing.task;

import com.guiji.billing.service.BillingTotalAnalysisService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 统计每月计费数据
 */
@Component
@JobHandler(value="totalMonthChargingTaskJob")
public class TotalMonthChargingTaskJob extends IJobHandler {

    private Logger logger = LoggerFactory.getLogger(TotalMonthChargingTaskJob.class);

    @Autowired
    private BillingTotalAnalysisService billingTotalAnalysisService;

    @Override
    public ReturnT<String> execute(String param) throws Exception {
        billingTotalAnalysisService.procTotalChargingByMonth();
        return ReturnT.SUCCESS;
    }
}
