package com.guiji.billing.service;

import com.guiji.billing.dto.*;
import com.guiji.billing.entity.BillingAcctChargingRecord;
import com.guiji.billing.entity.BillingAcctChargingTerm;
import com.guiji.billing.entity.BillingUserAcctBean;
import com.guiji.billing.entity.BillingUserAcctSetBean;
import com.guiji.billing.sys.ResultPage;
import com.guiji.billing.vo.ArrearageNotifyVo;
import com.guiji.billing.vo.UserAcctThresholdVo;
import com.guiji.billing.vo.UserRechargeTotalVo;

import java.util.Date;
import java.util.List;

public interface BillingUserAcctService {
    //查询用户账户列表
    List<BillingUserAcctBean> queryUserAcctList(QueryUserAcctDto queryUserAcctDto, ResultPage<BillingUserAcctBean> page);

    //查询用户账户数量
    int queryUserAcctCount(QueryUserAcctDto queryUserAcctDto);

    //用户余额查询
    BillingUserAcctBean queryUserAcctById(String accountId);

    //根据企业编码查询账户
    BillingUserAcctBean queryUserAcctByOrgCode(String orgCode);

    //根据企业下员工用户ID查询企业账户
    BillingUserAcctBean queryUserAcctByUserId(String userId);

    //新增企业账户
    BillingUserAcctBean addUserAcct(UserAcctAddDto acctAddDto);

    /**
     * 修改企业名称
     * @param acctAddDto
     * @return
     */
    boolean updAcctNameByOrg(UserAcctAddDto acctAddDto);

    //查询欠费企业
    List<BillingUserAcctBean> queryArrearageAcctList();

    //查询欠费企业的用户列表
    com.guiji.vo.ArrearageNotifyVo queryArrearageUserList();

    //查询可用余额低于阈值用户
    List<UserAcctThresholdVo> queryLowerThresholdAcctList();

    //查询到期expireDays天内的企业账户
    List<BillingUserAcctBean> queryExpireDaysAcctList(Date time, Integer expireDays);

    /*********充值    begin****************************/

    //用户充值
    boolean recharge(RechargeDto rechargeDto);

    //用户充值记录
    List<UserRechargeTotalVo> queryUserRechargeTotal(QueryRechargeDto queryRechargeDto, ResultPage<UserRechargeTotalVo> page);

    //用户充值记录数量
    int queryUserRechargeCount(QueryRechargeDto queryRechargeDto);

    //查询单条计费数据
    BillingAcctChargingRecord queryRechargeById(String chargingId);

    //编辑变更充值附件快照
    boolean editRechargeSnapshot(EditRechargeSnapshotDto editRechargeSnapshotDto);

    /*********充值    end****************************/

    /**************我的计费项    begin*********/
    //接收企业用户计费项
    BillingAcctChargingTerm receiveAcctUserChargingTerm(ChargingTermNotifyDto chargingTermNotifyDto);

    //查询账户计费项
    List<BillingAcctChargingTerm> queryAcctChargingTermList(QueryAcctChargingTermDto queryAcctChargingTermDto,
                                                            ResultPage<BillingAcctChargingTerm> page);

    //查询账户计费项条数
    int queryAcctChargingTermCount(QueryAcctChargingTermDto queryAcctChargingTermDto);

    //新增账户计费项
    BillingAcctChargingTerm addAcctChargingTerm(AcctChargingTermDto acctChargingTermDto);

    //修改账户计费项
    boolean updAcctChargingTerm(AcctChargingTermDto acctChargingTermDto);

    //删除账户计费项
    boolean delAcctChargingTerm(String userChargingId);
    /**************我的计费项    end*********/

    //查询计费记录
    List<BillingAcctChargingRecord> queryAcctChargingRecordList(QueryChargingRecordDto queryChargingRecordDto,
                                                                ResultPage<BillingAcctChargingRecord> page);
    //查询计费记录数量
    int queryAcctChargingRecordCount(QueryChargingRecordDto queryChargingRecordDto);

    /**************用户账户推送设置    begin*********/
    //查询账户推送设置列表
    List<BillingUserAcctSetBean> queryUserAcctSetList(QueryUserAcctDto queryUserAcctDto,
                                                      ResultPage<BillingUserAcctSetBean> page);
    //查询账户推送设置数量
    int queryUserAcctSetCount(QueryUserAcctDto queryUserAcctDto);

    //查询账户推送设置
    BillingUserAcctSetBean queryUserAcctSet(String accountId, String setKey);

    //新增账户推送设置
    BillingUserAcctSetBean addUserAcctSet(UserAcctSetDto userAcctSetDto);

    //修改账户推送设置
    boolean updUserAcctSet(UserAcctSetDto userAcctSetDto);

    //删除账户推送设置
    boolean delUserAcctSet(String acctSetId);
    /**************用户账户推送设置    end*********/
}
