package com.guiji.billing.service;

import com.guiji.billing.dto.QueryAcctChargingTotalDto;
import com.guiji.billing.dto.QueryAcctRecDto;
import com.guiji.billing.entity.BillingAcctChargingTotal;
import com.guiji.billing.entity.BillingAcctReconciliation;
import com.guiji.billing.sys.ResultPage;
import com.guiji.billing.vo.BillingTotalChargingConsumerVo;

import java.util.List;

public interface BillingTotalAnalysisService {
    //按日查询费用统计
    List<BillingAcctChargingTotal> totalAcctChargingByDay(QueryAcctChargingTotalDto queryAcctChargingTotalDto,
                                                          ResultPage<BillingAcctChargingTotal> page);

    //按日查询费用统计条数
    int totalAcctChargingByDayCount(QueryAcctChargingTotalDto queryAcctChargingTotalDto);

    //按月查询费用统计
    List<BillingAcctChargingTotal> totalAcctChargingByMonth(QueryAcctChargingTotalDto queryAcctChargingTotalDto,
                                                            ResultPage<BillingAcctChargingTotal> page);

    //按月查询费用统计
    int totalAcctChargingByMonthCount(QueryAcctChargingTotalDto queryAcctChargingTotalDto);

    /************************************************/

    //按日查询费用统计
    List<BillingTotalChargingConsumerVo> totalChargingByDate(QueryAcctChargingTotalDto queryAcctChargingTotalDto,
                                                             ResultPage<BillingTotalChargingConsumerVo> page);

    int totalChargingCountByDate(QueryAcctChargingTotalDto queryAcctChargingTotalDto);

    //按月查询费用统计
    List<BillingTotalChargingConsumerVo> totalChargingByMonth(QueryAcctChargingTotalDto queryAcctChargingTotalDto,
                                                              ResultPage<BillingTotalChargingConsumerVo> page);

    int totalChargingCountByMonth(QueryAcctChargingTotalDto queryAcctChargingTotalDto);
    /************************************************/
    //查询对账记录
    List<BillingAcctReconciliation> queryAcctReconciliation(QueryAcctRecDto queryAcctRecDto,
                                                           ResultPage<BillingAcctReconciliation> page);

    //查询对账记录数量
    int queryAcctReconcCount(QueryAcctRecDto queryAcctRecDto);

    /************************************************/

    //存储过程，统计每日计费数据
    void procTotalChargingByDate();

    //存储过程，统计每月计费数据
    void procTotalChargingByMonth();
}
