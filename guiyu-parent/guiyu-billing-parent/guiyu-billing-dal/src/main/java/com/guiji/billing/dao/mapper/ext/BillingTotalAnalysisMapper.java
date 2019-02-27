package com.guiji.billing.dao.mapper.ext;

import com.guiji.billing.entity.BillingAcctChargingTotal;
import com.guiji.billing.entity.BillingAcctReconciliation;
import com.guiji.billing.sys.ResultPage;
import com.guiji.billing.vo.BillingTotalChargingConsumerVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 计费统计
 */
@Repository
public interface BillingTotalAnalysisMapper {

    //插入统计结果数据
    int addAcctCharging(BillingAcctChargingTotal acctCharging);

    //按账户日查询费用统计
    List<BillingAcctChargingTotal> totalAcctChargingByDay(@Param("total") BillingAcctChargingTotal total,
                                                          @Param("beginDate") String beginDate,
                                                          @Param("endDate") String endDate,
                                                          @Param("page") ResultPage<BillingAcctChargingTotal> page);

    //按日查询费用统计条数
    int totalAcctChargingByDayCount(@Param("total") BillingAcctChargingTotal total,
                                    @Param("beginDate") String beginDate,
                                    @Param("endDate") String endDate);

    //按月查询费用统计
    List<BillingAcctChargingTotal> totalAcctChargingByMonth(@Param("total") BillingAcctChargingTotal total,
                                                            @Param("beginMonth") String beginMonth,
                                                            @Param("endMonth") String endMonth,
                                                            @Param("page") ResultPage<BillingAcctChargingTotal> page);

    //按月查询费用统计
    int totalAcctChargingByMonthCount(@Param("total") BillingAcctChargingTotal total,
                                      @Param("beginMonth") String beginMonth,
                                      @Param("endMonth") String endMonth);


    /******************************************/
    //按日查询费用统计
    List<BillingTotalChargingConsumerVo> totalChargingByDate(@Param("beginDate") String beginDate,
                                                          @Param("endDate") String endDate,
                                                          @Param("orgCode") String orgCode,
                                                          @Param("page") ResultPage<BillingTotalChargingConsumerVo> page);

    //按日查询费用统计条数
    int totalChargingCountByDate(@Param("beginDate") String beginDate,
                                 @Param("endDate") String endDate,
                                 @Param("orgCode") String orgCode);

    //按月查询费用统计
    List<BillingTotalChargingConsumerVo> totalChargingByMonth(@Param("beginMonth") String beginMonth,
                                                              @Param("endMonth") String endMonth,
                                                              @Param("orgCode") String orgCode,
                                                              @Param("page") ResultPage<BillingTotalChargingConsumerVo> page);

    //按月查询费用统计
    int totalChargingCountByMonth(@Param("beginMonth") String beginMonth,
                                  @Param("endMonth") String endMonth,
                                  @Param("orgCode") String orgCode);
    /****************************************/

    //查询对账记录
    List<BillingAcctReconciliation> queryAcctReconciliation(@Param("acctRec") BillingAcctReconciliation acctRec,
                                                            @Param("page") ResultPage<BillingAcctReconciliation> page);

    //查询对账记录数量
    int queryAcctReconcCount(@Param("acctRec") BillingAcctReconciliation acctRec);

    /******************************/

    //调用存储过程，统计每日计费数据
  //  int procTotalChargingByDate(@Param("beginTime") String beginTime, @Param("endTime") String endTime);
    int procTotalChargingByDate(@Param("totalDate") String totalDate);

    //调用存储过程，统计每月计费数据
    int procTotalChargingByMonth(@Param("totalMonth") String totalMonth,
                                 @Param("beginDate") String beginDate,
                                 @Param("endDate") String endDate);
}
