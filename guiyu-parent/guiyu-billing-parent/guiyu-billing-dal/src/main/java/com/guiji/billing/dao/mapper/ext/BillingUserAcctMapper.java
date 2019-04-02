package com.guiji.billing.dao.mapper.ext;

import com.guiji.billing.dto.*;
import com.guiji.billing.entity.BillingAcctChargingRecord;
import com.guiji.billing.entity.BillingAcctChargingTerm;
import com.guiji.billing.entity.BillingUserAcctBean;
import com.guiji.billing.entity.BillingUserAcctSetBean;
import com.guiji.billing.sys.ResultPage;
import com.guiji.billing.vo.UserAcctThresholdVo;
import com.guiji.billing.vo.UserRechargeTotalVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
public interface BillingUserAcctMapper {
    //查询用户账户列表
    List<BillingUserAcctBean> queryUserAcctList(@Param("userAcct") BillingUserAcctBean userAcct,
                                                @Param("page") ResultPage<BillingUserAcctBean> page);

    //查询用户账户数量
    int queryUserAcctCount(@Param("userAcct") BillingUserAcctBean userAcct);

    //用户账户查询
    BillingUserAcctBean queryUserAcct(String accountId);

    //根据企业编码查询账户
    BillingUserAcctBean queryUserAcctByOrgCode(String orgCode);

    //新增企业账户
    int addUserAcct(BillingUserAcctBean userAcct);

    //修改企业账户
    int updUserAcct(BillingUserAcctBean userAcct);

    //根据企业CODE变更企业名称
    int updAcctNameByOrg(@Param("orgCode") String orgCode, @Param("companyName") String companyName);

    //查询欠费用户(包括可用余额为O的企业用户)
    List<BillingUserAcctBean> queryArrearageAcctList();

    //查询可用余额低于阈值用户
    List<UserAcctThresholdVo> queryLowerThresholdAcctList();

    //查询到期7天内的企业账户
    List<BillingUserAcctBean> queryExpireDaysAcctList(@Param("time") Date time,
                                                      @Param("expireDays") Integer expireDays);

    /**
     * 下减账户余额
     * @param accountId
     * @param amount
     * @return
     */
    int subAcctBalanceAmount(@Param("accountId") String accountId, @Param("amount") BigDecimal amount,
                             @Param("updateTime") Date updateTime);

    /**
     * 增加账户余额
     * @param accountId
     * @param amount
     * @return
     */
    int addAcctBalanceAmount(@Param("accountId") String accountId, @Param("amount") BigDecimal amount,
                             @Param("updateTime") Date updateTime);

    /****************充值 begin*********************************/

    //充值
    int recharge(@Param("accountId") String accountId, @Param("rechargeAmount") BigDecimal rechargeAmount,
                     @Param("updateTime") Date updateTime);

    //
    List<UserRechargeTotalVo> queryUserRechargeTotal(@Param("accountId") String accountId, @Param("orgCode") String orgCode, @Param("userId") String userId,
                                                     @Param("type") Integer type, @Param("feeMode") Integer feeMode,
                                                     @Param("beginDate") Date beginDate, @Param("endDate") Date endDate,
                                                     @Param("page") ResultPage<UserRechargeTotalVo> page);

    int queryUserRechargeCount(@Param("accountId") String accountId, @Param("orgCode") String orgCode, @Param("userId") String userId,
                               @Param("type") Integer type, @Param("feeMode") Integer feeMode,
                               @Param("beginDate") Date beginDate, @Param("endDate") Date endDate);
    /****************充值 end*********************************/

    /**************我的计费项    begin*********/
    //查询账户计费项
    List<BillingAcctChargingTerm> queryAcctChargingTermList(@Param("acctCharging") BillingAcctChargingTerm acctCharging,
                                                            @Param("page") ResultPage<BillingAcctChargingTerm> page);

    //查询账户计费项条数
    int queryAcctChargingTermCount(@Param("acctCharging") BillingAcctChargingTerm acctCharging);

    BillingAcctChargingTerm queryAcctChargingTerm(@Param("accountId") String accountId,
                                                  @Param("userId") String userId,
                                                  @Param("chargingItemId") String chargingItemId);

    //新增账户计费项
    int addAcctChargingTerm(BillingAcctChargingTerm acctCharging);

    //修改账户计费项
    int updAcctChargingTerm(BillingAcctChargingTerm acctCharging);

    //删除账户计费项
    int delAcctChargingTerm(String userChargingId);

    /**************我的计费项    end*********/

    /**************计费    begin*********/
    //查询计费记录
    List<BillingAcctChargingRecord> queryAcctChargingRecordList(@Param("acctChargingRecord") BillingAcctChargingRecord acctChargingRecord,
                                                                @Param("page") ResultPage<BillingAcctChargingRecord> page);
    //查询计费记录数量
    int queryAcctChargingRecordCount(@Param("acctChargingRecord") BillingAcctChargingRecord acctChargingRecord);

    //新增计费
    int addAcctChargingRecord(BillingAcctChargingRecord acctCharging);

    //查询充值数据
    BillingAcctChargingRecord queryChargingRecordById(String chargingId);

    //变更充值附件快照
    int updRechargeSnapshot(@Param("chargingId") String chargingId,
                            @Param("attachmentSnapshotUrl") String attachmentSnapshotUrl,
                            @Param("updateTime") Date updateTime);

    /**************计费    end*********/

    /**************用户账户推送设置    begin*********/
    //查询账户推送设置列表
    List<BillingUserAcctSetBean> queryUserAcctSetList(@Param("userAcctSet") BillingUserAcctSetBean userAcctSet,
                                                      @Param("page") ResultPage<BillingUserAcctSetBean> page);
    //查询账户推送设置数量
    int queryUserAcctSetCount(BillingUserAcctSetBean userAcctSet);

    //查询账户推送设置
    BillingUserAcctSetBean queryUserAcctSet(@Param("accountId") String accountId, @Param("setKey") String setKey);

    //新增账户推送设置
    int addUserAcctSet(BillingUserAcctSetBean userAcctSet);

    //修改账户推送设置
    int updUserAcctSet(BillingUserAcctSetBean userAcctSet);

    //删除账户推送设置
    int delUserAcctSet(String acctSetId);
    /**************用户账户推送设置    end*********/
}
