package com.guiji.billing.service.impl;

import com.guiji.auth.api.IAuth;
import com.guiji.auth.model.SysUserRoleVo;
import com.guiji.billing.constants.BusiTypeEnum;
import com.guiji.billing.dao.mapper.ext.BillingChargingMapper;
import com.guiji.billing.dao.mapper.ext.BillingUserAcctMapper;
import com.guiji.billing.dto.CallChargingNotifyDto;
import com.guiji.billing.entity.BillNotifyLogBean;
import com.guiji.billing.entity.BillingAcctChargingRecord;
import com.guiji.billing.entity.BillingAcctChargingTerm;
import com.guiji.billing.entity.BillingUserAcctBean;
import com.guiji.billing.enums.*;
import com.guiji.billing.exception.BaseException;
import com.guiji.billing.service.AcctNotifyService;
import com.guiji.billing.service.ChargingService;
import com.guiji.billing.utils.DaoHandler;
import com.guiji.billing.utils.IdWorker;
import com.guiji.billing.utils.ResHandler;
import com.guiji.component.result.Result;
import com.guiji.user.dao.entity.SysOrganization;
import com.guiji.user.dao.entity.SysUser;
import com.guiji.utils.JsonUtils;
import com.guiji.vo.ArrearageNotifyVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 计费处理
 */
@Service
public class ChargingServiceImpl implements ChargingService {

    private Logger logger = LoggerFactory.getLogger(ChargingServiceImpl.class);

    @Autowired
    private IAuth iAuth;

    @Autowired
    private BillingUserAcctMapper billingUserAcctMapper;

    @Autowired
    private BillingChargingMapper billingChargingMapper;

    @Autowired
    private AcctNotifyService acctNotifyService;

    @Autowired
    private IdWorker idWorker;

    /**
     * 通话计费处理
     * @param callChargingNotifyDto
     * @return
     */
    @Override
    public boolean charging(CallChargingNotifyDto callChargingNotifyDto) {
        boolean bool = false;
        if (null != callChargingNotifyDto){
            //新增话单日志
            this.addBillNotifyLog(callChargingNotifyDto);

            Integer userId = callChargingNotifyDto.getUserId();//用户ID
            Integer billSec = callChargingNotifyDto.getBillSec();//通话时长 秒
            String lineId = callChargingNotifyDto.getLineId()+"";   //通话线路
            String phone = callChargingNotifyDto.getPhone();    //号码
            Date beginTime = callChargingNotifyDto.getBeginTime();  //开始时间
            Date endTime = callChargingNotifyDto.getEndTime();  //结束时间
            //查询通话用户信息,企业组织编码后面有.
            SysUser user = ResHandler.getResObj(iAuth.getUserById(Long.valueOf(userId)));
            //查询企业组织，企业组织编码后面没有.
            SysOrganization org = ResHandler.getResObj(iAuth.getOrgByUserId(Long.valueOf(userId)));
            String orgCode = (null != org)?org.getCode():null;
            //查询企业用户账户
            BillingUserAcctBean acct = billingUserAcctMapper.queryUserAcctByOrgCode(orgCode);
            String accountId = null != acct?acct.getAccountId():null;
            //查询计费项
            BillingAcctChargingTerm acctChargingParam = new BillingAcctChargingTerm();
            acctChargingParam.setAccountId(accountId);
            acctChargingParam.setUserId(userId+"");
            acctChargingParam.setChargingItemId(lineId);
            BillingAcctChargingTerm term = billingUserAcctMapper.queryAcctChargingTerm(accountId, userId+"", lineId);
            logger.info("账户用户线路计费项:{},账户ID:{},用户ID:{},线路ID:{}", null != term?JsonUtils.bean2Json(term):"无", accountId, userId, lineId);
            if(null != term) {
                //通话时长
                Long duration = Long.valueOf(billSec)   ;
                //此次通话计费金额（根据线路计费项计算原始消费金额）
                BigDecimal chargingAmount = this.getAmount(term, duration);
                //此次通话实际消费金额(判断计费项是否"扣费"，是否"启用"，否则实际消费金额为0)
                BigDecimal consumeAmount = (term.getIsDeducted() == AcctChargingDeductedEnum.CHARGING.getStatus()
                        && term.getStatus() == AcctChargingStatusEnum.START_UP.getStatus()) ?
                        chargingAmount : BigDecimal.ZERO;
                //账户可用金额
                BigDecimal availableBalance = null != acct?acct.getAvailableBalance():BigDecimal.ZERO;
                //消费前账户金额
                BigDecimal srcAmount = availableBalance;
                //消费后账户金额
                BigDecimal toAmount = availableBalance.subtract(consumeAmount);

                BillingAcctChargingRecord chargingRecord = new BillingAcctChargingRecord();
                chargingRecord.setChargingId(idWorker.getBusiId(BusiTypeEnum.BILLING_ACCT.getType()));
                chargingRecord.setAccountId(accountId);
                chargingRecord.setOperUserId(userId+"");
                chargingRecord.setOperUserName(null != user?user.getUsername():"");
                chargingRecord.setOperOrgCode(null != user?user.getOrgCode():"");
                chargingRecord.setOperUserOrgCode(null != user?user.getOrgCode():"");//消费企业编码
                chargingRecord.setOperBeginTime(beginTime);
                chargingRecord.setOperEndTime(endTime);
                chargingRecord.setOperDuration(duration);
                chargingRecord.setOperDurationM(this.getMins(duration));
                chargingRecord.setOperDurationStr(this.getDurationStr(duration));

                chargingRecord.setType(ChargingTypeEnum.CONSUME.getType());//消费
                chargingRecord.setFeeMode(AcctChargingFeeModeEnum.CALL_RESUME.getFeeCode());//通话消费
                chargingRecord.setUserChargingId(term.getUserChargingId());
                chargingRecord.setAmount(consumeAmount);//实际消费金额
                chargingRecord.setChargingAmount(chargingAmount);//原始计费金额
                chargingRecord.setSrcAmount(srcAmount);
                chargingRecord.setToAmount(toAmount);
                chargingRecord.setPhone(phone);
                chargingRecord.setCreateTime(new Date());
                chargingRecord.setDelFlag(SysDelEnum.NORMAL.getState());
                //插入计费记录
                bool = DaoHandler.getMapperBoolRes(billingUserAcctMapper.addAcctChargingRecord(chargingRecord));
                if(bool) {
                    //计费扣减企业账户余额
                    bool = DaoHandler.getMapperBoolRes(billingUserAcctMapper.subAcctBalanceAmount(accountId, consumeAmount, new Date()));
                }

                //如果扣费后欠费，则欠费消息通知
                if(toAmount.compareTo(BigDecimal.ZERO)<0){
                    this.notifyArrearage(String.valueOf(userId), orgCode);
                }
            }
            return bool;
        }else{
            throw new BaseException(SysDefaultExceptionEnum.NULL_PARAM_EXCEPTION.getErrorCode(),
                    SysDefaultExceptionEnum.NULL_PARAM_EXCEPTION.getErrorMsg());
        }
    }

    /**
     * 欠费消息通知
     * @param userId
     */
    private void notifyArrearage(String userId, String orgCode){
        logger.info("用户ID:{},所属企业orgCode:{}欠费通知开始", userId, orgCode);
        try {
            //获取企业组织下的员工
        //    List<SysUser> userList = ResHandler.getResObj(iAuth.getAllUserByOrgCode(orgCode));
            //查询欠费企业的用户列表
            ArrearageNotifyVo arrearage = new ArrearageNotifyVo();
            List<String> userIdList = new ArrayList<String>();
            userIdList.add(userId);
            //欠费用户ID
            arrearage.setUserIdList(userIdList);
            //欠费状态
            arrearage.setIsArrearage(AcctArrearageStatusEnum.ARREARAGE.getStatus());

            logger.info("通话计费通知欠费消息:{}", JsonUtils.bean2Json(arrearage));
            //通知欠费消息
            acctNotifyService.notifyArrearage(arrearage);
        }catch(Exception e){
            logger.error("用户ID:{"+userId+"}欠费通知异常", e);
        }
        logger.info("用户ID:{}欠费通知结束", userId);
    }

    /**
     * 计算消费扣费金额
     * @param term
     * @param duration
     * @return
     */
    private BigDecimal getAmount(BillingAcctChargingTerm term, Long duration){
        BigDecimal amount = BigDecimal.ZERO;
        //计费项启用，并且属于扣费类型
        /*if(term.getStatus() == AcctChargingStatusEnum.START_UP.getStatus()
                && term.getIsDeducted() == AcctChargingDeductedEnum.CHARGING.getStatus()){*/
            if(term.getUnitPrice() == ChargingUnitPriceTypeEnum.MINUTE.getType()){//按分钟计费
                //按分钟计算，不满一分钟算一分钟
                long mins = this.getMins(duration);
                amount = term.getPrice().multiply(new BigDecimal(mins));
            }
        /*}*/
        return amount;
    }

    /**
     * 计算通话时长：分钟
     * @param duration
     * @return
     */
    private long getMins(Long duration){
        return (duration%60 == 0)?duration/60:(duration/60 + 1);
    }

    /**
     * 通话时长描述
     * @param duration
     * @return
     */
    private String getDurationStr(Long duration){
        String str = "";
        if(duration>=3600L){
            long hours = duration/3600;
            long subMins = duration - (hours * 3600);
            long mins = subMins/60;
            long secs = duration - (hours * 3600) - (mins * 60);
            str = (hours>9?hours:("0"+hours)) + ":" + (mins>9?mins:("0"+mins)) + ":" + (secs>9?secs:("0"+secs));
        }else if (duration>=60L){
            long mins = duration/60;
            long secs = duration - (mins * 60);
            str = (mins>9?mins:("0"+mins)) + ":" + (secs>9?secs:("0"+secs));
        }else{
            str = "00:" + (duration>9?duration:("0"+duration));
        }
        return str;
    }

    /**
     * 新增话单日志
     * @param callChargingNotifyDto
     */
    private void addBillNotifyLog(CallChargingNotifyDto callChargingNotifyDto){
        if(null != callChargingNotifyDto) {
            try {
                BillNotifyLogBean billLog = new BillNotifyLogBean();
                BeanUtils.copyProperties(callChargingNotifyDto, billLog, BillNotifyLogBean.class);
                billingChargingMapper.addBillNotifyLog(billLog);
            }catch(Exception e){
                logger.error("新增话单日志异常", e);
            }
        }
    }
}
