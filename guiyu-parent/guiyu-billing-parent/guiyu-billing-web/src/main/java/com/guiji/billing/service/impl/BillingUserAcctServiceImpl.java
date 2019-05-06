package com.guiji.billing.service.impl;

import com.guiji.auth.api.IAuth;
import com.guiji.auth.api.IOrg;
import com.guiji.billing.constants.AuthConstant;
import com.guiji.billing.constants.BusiTypeEnum;
import com.guiji.billing.constants.ThresholdKeyEnum;
import com.guiji.billing.dao.mapper.ext.BillingUserAcctMapper;
import com.guiji.billing.dto.*;
import com.guiji.billing.entity.BillingAcctChargingRecord;
import com.guiji.billing.entity.BillingAcctChargingTerm;
import com.guiji.billing.entity.BillingUserAcctBean;
import com.guiji.billing.entity.BillingUserAcctSetBean;
import com.guiji.billing.enums.*;
import com.guiji.billing.exception.BaseException;
import com.guiji.billing.service.AcctNotifyService;
import com.guiji.billing.service.BillingUserAcctService;
import com.guiji.billing.service.GetApiService;
import com.guiji.billing.service.GetAuthUtil;
import com.guiji.billing.service.msg.MsgNotifyComponent;
import com.guiji.billing.sys.ResultPage;
import com.guiji.billing.utils.DaoHandler;
import com.guiji.billing.utils.DateTimeUtils;
import com.guiji.billing.utils.IdWorker;
import com.guiji.billing.utils.ResHandler;
import com.guiji.billing.vo.ArrearageNotifyVo;
import com.guiji.billing.vo.UserAcctThresholdVo;
import com.guiji.billing.vo.UserRechargeTotalVo;
import com.guiji.component.result.Result;
import com.guiji.notice.api.INoticeSend;
import com.guiji.user.dao.entity.SysOrganization;
import com.guiji.user.dao.entity.SysUser;
import com.guiji.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
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
 * 企业账户
 */
@Service
public class BillingUserAcctServiceImpl implements BillingUserAcctService {

    private Logger logger = LoggerFactory.getLogger(BillingUserAcctServiceImpl.class);

    @Autowired
    private BillingUserAcctMapper billingUserAcctMapper;

    @Autowired
    private AcctNotifyService acctNotifyService;

    @Autowired
    private GetApiService getApiService;

    @Autowired
    private IAuth iAuth;

    @Autowired
    private IOrg iOrg;

    @Autowired
    private MsgNotifyComponent msgNotifyComponent;

    @Autowired
    private GetAuthUtil getAuthUtil;

    @Autowired
    private IdWorker idWorker;

    /**
     * 查询用户账户列表
     * @param queryUserAcctDto
     * @param page
     * @return
     */
    @Override
    public List<BillingUserAcctBean> queryUserAcctList(QueryUserAcctDto queryUserAcctDto, ResultPage<BillingUserAcctBean> page) {
        BillingUserAcctBean userAcct = null;
        if(null != queryUserAcctDto){
            userAcct = new BillingUserAcctBean();
            BeanUtils.copyProperties(queryUserAcctDto, userAcct, BillingUserAcctBean.class);
        }
        return billingUserAcctMapper.queryUserAcctList(userAcct, page);
    }

    /**
     * 查询用户账户数量
     * @param queryUserAcctDto
     * @return
     */
    @Override
    public int queryUserAcctCount(QueryUserAcctDto queryUserAcctDto) {
        BillingUserAcctBean userAcct = null;
        if(null != queryUserAcctDto){
            userAcct = new BillingUserAcctBean();
            BeanUtils.copyProperties(queryUserAcctDto, userAcct, BillingUserAcctBean.class);
        }
        return billingUserAcctMapper.queryUserAcctCount(userAcct);
    }

    /**
     * 用户余额查询
     * @param accountId
     * @return
     */
    @Override
    public BillingUserAcctBean queryUserAcctById(String accountId) {
        return !StringUtils.isEmpty(accountId)?billingUserAcctMapper.queryUserAcct(accountId):null;
    }

    /**
     * 根据企业编码查询账户
     * @param orgCode
     * @return
     */
    @Override
    public BillingUserAcctBean queryUserAcctByOrgCode(String orgCode) {
        return !StringUtils.isEmpty(orgCode)?billingUserAcctMapper.queryUserAcctByOrgCode(orgCode):null;
    }

    /**
     * 根据企业下员工用户ID查询企业账户
     * @param userId
     * @return
     */
    @Override
    public BillingUserAcctBean queryUserAcctByUserId(String userId) {
        if(!StringUtils.isEmpty(userId)) {
            String logId = idWorker.nextId();
            logger.info("企业员工用户ID:{}查询企业账户,日志ID:{}", userId, logId);
            //查询用户所在企业组织
            SysOrganization org = getApiService.getOrgByUserId(userId);
            logger.info("企业员工用户ID:{}所在企业:{}查询企业账户,日志ID:{}",userId, JsonUtils.bean2Json(org), logId);
            BillingUserAcctBean acct = (null != org && !StringUtils.isEmpty(org.getCode()))?//user.getOrgCode()
                    billingUserAcctMapper.queryUserAcctByOrgCode(org.getCode()):null;
            logger.info("企业员工用户ID:{}所在企业:{}查询企业账户:{},日志ID:{}",userId, JsonUtils.bean2Json(org), JsonUtils.bean2Json(acct), logId);
            return acct;
        }else{
            throw new BaseException(SysDefaultExceptionEnum.NULL_PARAM_EXCEPTION.getErrorCode(),
                    SysDefaultExceptionEnum.NULL_PARAM_EXCEPTION.getErrorMsg());
        }

    }

    /**
     * 创建用户账户
     * @param acctAddDto
     * @return
     */
    @Override
    public BillingUserAcctBean addUserAcct(UserAcctAddDto acctAddDto) {
        if(null != acctAddDto
                && !StringUtils.isEmpty(acctAddDto.getOrgCode())) {
            BillingUserAcctBean acct = new BillingUserAcctBean();
            String orgCode = acctAddDto.getOrgCode();
            //查询企业组织
            SysOrganization org = ResHandler.getResObj(iOrg.getOrgByCode(orgCode));
            if(null == org){
                throw new BaseException(SysDefaultExceptionEnum.DEFINE_EXCEPTION.getErrorCode(),
                        "企业组织不存在");
            }

            boolean bool = false;
            //查询该企业是否已注册
            BillingUserAcctBean acctExist = billingUserAcctMapper.queryUserAcctByOrgCode(orgCode);
            //企业已注册账户
            if(null != acctExist){
                acct.setOrgCode(org.getCode()); //企业组织编码
                acct.setCompanyId(org.getId()+"");  //企业ID
                acct.setCompanyName(org.getName()); //企业名称
                acct.setOrgType(org.getType());     //企业组织类型 1-代理商 2-企业公司

                acct.setUpdateTime(new Date());
                bool = DaoHandler.getMapperBoolRes(billingUserAcctMapper.updUserAcct(acct));

            //企业未注册
            }else {
                BeanUtils.copyProperties(acctAddDto, acct, BillingUserAcctBean.class);
                acct.setAccountId(idWorker.getBusiId(BusiTypeEnum.BILLING_ACCT.getType()));
                acct.setOrgCode(org.getCode()); //企业组织编码
                acct.setCompanyId(org.getId()+"");  //企业ID
                acct.setCompanyName(org.getName()); //企业名称
                acct.setOrgType(org.getType());     //企业组织类型 1-代理商 2-企业公司
                acct.setAmount(BigDecimal.ZERO);
                acct.setAvailableBalance(BigDecimal.ZERO);
                acct.setFreezingAmount(BigDecimal.ZERO);
                acct.setCreateTime(new Date());
                acct.setDelFlag(SysDelEnum.NORMAL.getState());
                bool = DaoHandler.getMapperBoolRes(billingUserAcctMapper.addUserAcct(acct));
            }
            acct = bool?acct:null;
            return acct;
        }else{
            throw new BaseException(SysDefaultExceptionEnum.NULL_PARAM_EXCEPTION.getErrorCode(),
                    SysDefaultExceptionEnum.NULL_PARAM_EXCEPTION.getErrorMsg());
        }
    }

    /**
     * 修改账户企业名称
     * @param acctAddDto
     * @return
     */
    @Override
    public boolean updAcctNameByOrg(UserAcctAddDto acctAddDto) {
        if(!StringUtils.isEmpty(acctAddDto.getOrgCode())){
            String orgCode = acctAddDto.getOrgCode();
            String companyName = acctAddDto.getCompanyName();
            boolean bool = DaoHandler.getMapperBoolRes(billingUserAcctMapper.updAcctNameByOrg(orgCode, companyName));
            return bool;
        }else{
            throw new BaseException(SysDefaultExceptionEnum.NULL_PARAM_EXCEPTION.getErrorCode(),
                    SysDefaultExceptionEnum.NULL_PARAM_EXCEPTION.getErrorMsg());
        }
    }

    /**
     * 查询欠费企业账户
     * @return
     */
    @Override
    public List<BillingUserAcctBean> queryArrearageAcctList() {
        return billingUserAcctMapper.queryArrearageAcctList();
    }

    /**
     * 查询欠费企业的用户列表
     * @return
     */
    @Override
    public com.guiji.vo.ArrearageNotifyVo queryArrearageUserList() {
        com.guiji.vo.ArrearageNotifyVo arrearageNotifyVo = new com.guiji.vo.ArrearageNotifyVo();
        String logId = idWorker.nextId();
        //查询欠费企业
        List<BillingUserAcctBean> list = this.queryArrearageAcctList();
        logger.info("欠费企业列表:{},日志ID:{}", null != list? JsonUtils.bean2Json(list):null, logId);
        if (null != list && list.size()>0) {
            List<String> userIdList = new ArrayList<String>();
            for (BillingUserAcctBean acct : list) {
                //获取企业组织下的所有用户
                String orgCode = acct.getOrgCode();
                List<SysUser> userList = ResHandler.getResObj(iAuth.getAllUserByOrgCode(orgCode));
             //   logger.info("欠费用户列表:{},日志ID:{}", null != userList? JsonUtils.bean2Json(userList):null, logId);
                if (null != userList && userList.size()>0) {
                    for (SysUser user : userList) {
                        userIdList.add(user.getId() + "");
                    }
                }
            }
            if (null != userIdList && userIdList.size() > 0) {
                //欠费用户ID
                arrearageNotifyVo.setUserIdList(userIdList);
                //欠费状态
                arrearageNotifyVo.setIsArrearage(AcctArrearageStatusEnum.ARREARAGE.getStatus());
            }
        }
        logger.info("最终结果，欠费用户列表:{},日志ID:{}", JsonUtils.bean2Json(arrearageNotifyVo));
        return arrearageNotifyVo;
    }

    /**
     * 查询可用余额低于阈值用户
     * @return
     */
    @Override
    public List<UserAcctThresholdVo> queryLowerThresholdAcctList() {
        return billingUserAcctMapper.queryLowerThresholdAcctList();
    }

    /**
     * 查询到期expireDays天内的企业账户
     * @param time
     * @param expireDays
     * @return
     */
    @Override
    public List<BillingUserAcctBean> queryExpireDaysAcctList(Date time, Integer expireDays) {
        return billingUserAcctMapper.queryExpireDaysAcctList(time, expireDays);
    }

    /**********充值   begin********************************/

    /**
     * 充值
     * @param rechargeDto
     * @return
     */
    @Override
    public boolean recharge(RechargeDto rechargeDto) {
        if(null != rechargeDto && null != rechargeDto.getAmount()) {
            boolean bool = false;
            String accountId = null;
            BigDecimal amount = rechargeDto.getAmount(); //充值金额
            BigDecimal rechargeAmount = amount.multiply(new BigDecimal(100));//充值金额转化成分
            BigDecimal srcAmount = BigDecimal.ZERO, toAmount = BigDecimal.ZERO;//充值前，充值后金额
            rechargeDto.setAmount(rechargeAmount);
            BillingUserAcctBean acct = null;
            if(!StringUtils.isEmpty(rechargeDto.getAccountId())) {
                accountId = rechargeDto.getAccountId();
                //查询账户
                acct = billingUserAcctMapper.queryUserAcct(accountId);
                srcAmount = acct.getAvailableBalance();
                toAmount = srcAmount.add(rechargeAmount);
                acct.setAmount(acct.getAmount().add(rechargeAmount));//总金额
                acct.setAvailableBalance(acct.getAvailableBalance().add(rechargeAmount));//账户可以剩余余额
                //账户充值
                bool = DaoHandler.getMapperBoolRes(billingUserAcctMapper.recharge(accountId, rechargeAmount, new Date()));

                //通知取消欠费消息
                this.notifyUnfreeze(acct, srcAmount, toAmount);
            }else {
                //根据企业编码
                if(!StringUtils.isEmpty(rechargeDto.getOrgCode())){
                    //查询该企业是否已注册
                    String orgCode = rechargeDto.getOrgCode();
                    /*//如果前端传过来的带后缀点.
                    if(!StringUtils.isEmpty(orgCode) && orgCode.endsWith(AuthConstant.orgSuffix)){
                        orgCode = orgCode.substring(0, orgCode.length()-1);
                    }*/
                    BillingUserAcctBean acctExist = billingUserAcctMapper.queryUserAcctByOrgCode(orgCode);
                    //企业已注册账户
                    if(null != acctExist){
                        acct = acctExist;
                        srcAmount = acct.getAvailableBalance();
                        toAmount = srcAmount.add(rechargeAmount);
                        accountId = acct.getAccountId();
                        acct.setAmount(acct.getAvailableBalance().add(rechargeAmount));//总金额
                        acct.setAvailableBalance(acct.getAvailableBalance().add(rechargeAmount));//账户可以剩余余额
                        //账户充值
                        bool = DaoHandler.getMapperBoolRes(billingUserAcctMapper.recharge(accountId, rechargeAmount, new Date()));

                        //通知取消欠费消息
                        this.notifyUnfreeze(acct, srcAmount, toAmount);

                    //企业未注册,先注册账户
                    }else {
                        toAmount = srcAmount.add(rechargeAmount);
                        accountId = idWorker.getBusiId(BusiTypeEnum.BILLING_ACCT.getType());
                        //查询企业组织
                        SysOrganization org = ResHandler.getResObj(iOrg.getOrgByCode(orgCode));
                        acct = new  BillingUserAcctBean();
                        acct.setAccountId(accountId);
                        acct.setCompanyId(null != org?(org.getId() + ""):null);
                        acct.setCompanyName(null != org?org.getName():null);
                        acct.setOrgCode(orgCode);
                        acct.setAmount(rechargeAmount);
                        acct.setAvailableBalance(rechargeAmount);
                        acct.setFreezingAmount(BigDecimal.ZERO);
                        acct.setCreateTime(new Date());
                        acct.setDelFlag(SysDelEnum.NORMAL.getState());
                        bool = DaoHandler.getMapperBoolRes(billingUserAcctMapper.addUserAcct(acct));
                    }
                }else{
                    throw new BaseException(SysDefaultExceptionEnum.NULL_PARAM_EXCEPTION.getErrorCode(),
                            SysDefaultExceptionEnum.NULL_PARAM_EXCEPTION.getErrorMsg());
                }
            }

            if(bool){
                //充值记录
                bool = this.rechargeRecord(acct, rechargeDto, srcAmount, toAmount);

                //充值消息通知
                msgNotifyComponent.notifyByRecharge(acct, rechargeDto);
            }
            return bool;
        }else{
            throw new BaseException(SysDefaultExceptionEnum.NULL_PARAM_EXCEPTION.getErrorCode(),
                    SysDefaultExceptionEnum.NULL_PARAM_EXCEPTION.getErrorMsg());
        }
    }

    /**
     * 通知取消欠费消息
     * @param acct 充值后的账户数据
     * @param srcAmount  充值前数据
     * @param toAmount  充值后数据
     */
    private void notifyUnfreeze(BillingUserAcctBean acct, BigDecimal srcAmount, BigDecimal toAmount){
        try {
            //如果充值使之前欠费变更为不欠费，通知取消欠费消息
            if (srcAmount.compareTo(BigDecimal.ZERO) < 0
                    && (toAmount).compareTo(BigDecimal.ZERO) > 0) {
                com.guiji.vo.ArrearageNotifyVo arrearageNotifyVo = null;
                List<BillingUserAcctBean> list = this.queryArrearageAcctList();
                List<String> userIdList = new ArrayList<String>();
                //获取企业组织下的所有用户
                String orgCode = acct.getOrgCode();
                Result.ReturnData<List<SysUser>> res = iAuth.getAllUserByOrgCode(orgCode);
                if (null != res && res.success) {
                    List<SysUser> userList = res.getBody();
                    for (SysUser user : userList) {
                        userIdList.add(user.getId() + "");
                    }
                }

                logger.info("充值通知取消欠费用户:{},充值前:{},充值后:{}", JsonUtils.bean2Json(userIdList), srcAmount, toAmount);
                if (null != userIdList && userIdList.size() > 0) {
                    arrearageNotifyVo = new com.guiji.vo.ArrearageNotifyVo();
                    //欠费用户ID
                    arrearageNotifyVo.setUserIdList(userIdList);
                    //欠费状态
                    arrearageNotifyVo.setIsArrearage(AcctArrearageStatusEnum.NORMAL.getStatus());

                    logger.info("充值通知取消欠费消息:{},充值前:{},充值后:{}", JsonUtils.bean2Json(arrearageNotifyVo), srcAmount, toAmount);
                    //通知取消欠费消息
                    acctNotifyService.notifyArrearage(arrearageNotifyVo);
                }
            }
        }catch(Exception e){
            logger.error("通知取消欠费消息异常", e);
        }
    }

    /**
     * 充值记录
     * @param acct
     * @param rechargeDto
     * @return
     */
    private boolean rechargeRecord(BillingUserAcctBean acct, RechargeDto rechargeDto, BigDecimal srcAmount, BigDecimal toAmount){
        BillingAcctChargingRecord chargingRecord = new BillingAcctChargingRecord();
        String userId = rechargeDto.getUserId();
        SysUser user = ResHandler.getResObj(iAuth.getUserById(Long.valueOf(userId)));
        Date time = new Date();
        chargingRecord.setChargingId(idWorker.getBusiId(BusiTypeEnum.BILLING_ACCT.getType()));
        chargingRecord.setAccountId(acct.getAccountId());
        chargingRecord.setOperUserId(userId);
        chargingRecord.setOperUserName(user.getUsername());
        chargingRecord.setOperOrgCode(user.getOrgCode());   //操作员用户企业编码
        chargingRecord.setOperUserOrgCode(acct.getOrgCode());//被充值所属企业orgCode, 从企业账户里面去是不带.,自己拼接
        chargingRecord.setOperBeginTime(time);
        chargingRecord.setOperEndTime(time);
        chargingRecord.setOperDuration(0L);
        chargingRecord.setOperDurationM(0L);
        chargingRecord.setOperDurationStr("00:00");

        chargingRecord.setType(ChargingTypeEnum.RECHARGE.getType());//充值
        chargingRecord.setFeeMode(AcctChargingFeeModeEnum.BANK_RECHARGE.getFeeCode());//通话消费
        chargingRecord.setUserChargingId(null);
        chargingRecord.setAmount(rechargeDto.getAmount());
        chargingRecord.setSrcAmount(srcAmount);
        chargingRecord.setToAmount(toAmount);
        chargingRecord.setPhone(null);
        chargingRecord.setAttachmentSnapshotUrl(rechargeDto.getAttachmentSnapshotUrl());
        chargingRecord.setCreateTime(time);
        chargingRecord.setDelFlag(SysDelEnum.NORMAL.getState());
        //插入计费记录
        return DaoHandler.getMapperBoolRes(billingUserAcctMapper.addAcctChargingRecord(chargingRecord));
    }

    /**
     * 查询用户充值记录
     * @param queryRechargeDto
     * @return
     */
    @Override
    public List<UserRechargeTotalVo> queryUserRechargeTotal(QueryRechargeDto queryRechargeDto, ResultPage<UserRechargeTotalVo> page) {
        String accountId = queryRechargeDto.getAccountId();
        Integer authLevel = queryRechargeDto.getAuthLevel();//操作用户权限等级
        String userId = getAuthUtil.getUserIdByAuthLevel(authLevel, queryRechargeDto.getOperUserId());//获取用户ID
        String orgCode = getAuthUtil.getOrgCodeByAuthLevel(authLevel, userId, queryRechargeDto.getOrgCode());//获取企业组织编码
        Date beginDate = queryRechargeDto.getBeginDate();
        Date endDate = queryRechargeDto.getEndDate();
        if(null != beginDate && null == endDate){
            endDate = DateTimeUtils.getDateByString(DateTimeUtils.DEFAULT_BEGIN_TIME, DateTimeUtils.DEFAULT_DATE_FORMAT_PATTERN_FULL);
        }else if(null == beginDate && null != endDate){
            beginDate = DateTimeUtils.getDateByString(DateTimeUtils.DEFAULT_END_TIME, DateTimeUtils.DEFAULT_DATE_FORMAT_PATTERN_FULL);
        }
        return billingUserAcctMapper.queryUserRechargeTotal(accountId, orgCode, userId, ChargingTypeEnum.RECHARGE.getType(), queryRechargeDto.getFeeMode(),
                beginDate, endDate, page);
    }

    /**
     * 查询用户充值条数
     * @param queryRechargeDto
     * @return
     */
    @Override
    public int queryUserRechargeCount(QueryRechargeDto queryRechargeDto) {
        String accountId = queryRechargeDto.getAccountId();
        Integer authLevel = queryRechargeDto.getAuthLevel();//操作用户权限等级
        String userId = getAuthUtil.getUserIdByAuthLevel(authLevel, queryRechargeDto.getOperUserId());//获取用户ID
        String orgCode = getAuthUtil.getOrgCodeByAuthLevel(authLevel, userId, queryRechargeDto.getOrgCode());//获取企业组织编码
        Date beginDate = queryRechargeDto.getBeginDate();
        Date endDate = queryRechargeDto.getEndDate();
        if(null != beginDate && null == endDate){
            endDate = DateTimeUtils.getDateByString(DateTimeUtils.DEFAULT_BEGIN_TIME, DateTimeUtils.DEFAULT_DATE_FORMAT_PATTERN_FULL);
        }else if(null == beginDate && null != endDate){
            beginDate = DateTimeUtils.getDateByString(DateTimeUtils.DEFAULT_END_TIME, DateTimeUtils.DEFAULT_DATE_FORMAT_PATTERN_FULL);
        }
        return billingUserAcctMapper.queryUserRechargeCount(accountId, orgCode, userId, ChargingTypeEnum.RECHARGE.getType(), queryRechargeDto.getFeeMode(),
                beginDate, endDate);
    }

    /**
     * 查询充值单条数据
     * @param chargingId
     * @return
     */
    @Override
    public BillingAcctChargingRecord queryRechargeById(String chargingId) {
        return billingUserAcctMapper.queryChargingRecordById(chargingId);
    }

    /**
     * 变更充值附件快照
     * @param editRechargeSnapshotDto
     * @return
     */
    @Override
    public boolean editRechargeSnapshot(EditRechargeSnapshotDto editRechargeSnapshotDto) {
        if(null != editRechargeSnapshotDto
            && !StringUtils.isEmpty(editRechargeSnapshotDto.getChargingId())
            && !StringUtils.isEmpty(editRechargeSnapshotDto.getAttachmentSnapshotUrl())){
            //变更充值附件快照
            String chargingId = editRechargeSnapshotDto.getChargingId();
            String attachmentSnapshotUrl = editRechargeSnapshotDto.getAttachmentSnapshotUrl();
            return DaoHandler.getMapperBoolRes(billingUserAcctMapper.updRechargeSnapshot(chargingId, attachmentSnapshotUrl, new Date()));
        }else{
            throw new BaseException(SysDefaultExceptionEnum.NULL_PARAM_EXCEPTION.getErrorCode(),
                    SysDefaultExceptionEnum.NULL_PARAM_EXCEPTION.getErrorMsg());
        }
    }

    /**
     * 接收企业账户用户计费项
     * @param chargingTermNotifyDto
     * @return
     */
    @Override
    public BillingAcctChargingTerm receiveAcctUserChargingTerm(ChargingTermNotifyDto chargingTermNotifyDto) {
        if(null != chargingTermNotifyDto){
            BillingAcctChargingTerm term = null;
            String userId = chargingTermNotifyDto.getUserId();
            String chargingItemId = chargingTermNotifyDto.getChargingItemId();
            //查询用户所在企业组织编码
            SysOrganization org = ResHandler.getResObj(iAuth.getOrgByUserId(Long.valueOf(userId)));
            //查询企业用户账户
            String orgCode = (null != org)?org.getCode():null;
            BillingUserAcctBean acct = billingUserAcctMapper.queryUserAcctByOrgCode(orgCode);
            String accountId = null != acct?acct.getAccountId():null;
            term = new BillingAcctChargingTerm();
            term.setAccountId(accountId);
            term.setUserId(userId);
            term.setChargingItemId(chargingItemId);
            term.setChargingItemName(chargingTermNotifyDto.getChargingItemName());
            term.setChargingType(null != chargingTermNotifyDto.getChargingType()?
                    chargingTermNotifyDto.getChargingType():ChargingTremTypeEnum.TIME_DURATION.getType());
            term.setPrice(new BigDecimal(chargingTermNotifyDto.getPrice()));
            term.setUnitPrice(chargingTermNotifyDto.getUnitPrice());
            term.setIsDeducted(chargingTermNotifyDto.getIsDeducted());
            term.setStatus(chargingTermNotifyDto.getStatus());

            BillingAcctChargingTerm acctTermExist = billingUserAcctMapper.queryAcctChargingTerm(accountId, userId, chargingItemId);
            //已存在，修改企业用户计费项
            if(null != acctTermExist){//修改
                term.setUserChargingId(acctTermExist.getUserChargingId());
                term.setUpdateTime(new Date());
                term.setDelFlag(SysDelEnum.NORMAL.getState());
                billingUserAcctMapper.updAcctChargingTerm(term);
            //不存在，新增企业用户计费项
            }else {
                term.setUserChargingId(idWorker.getBusiId(BusiTypeEnum.BILLING_ACCT.getType()));
                term.setCreateTime(new Date());
                term.setDelFlag(SysDelEnum.NORMAL.getState());
                //新增
                billingUserAcctMapper.addAcctChargingTerm(term);
            }
            return term;
        }else{
            throw new BaseException(SysDefaultExceptionEnum.NULL_PARAM_EXCEPTION.getErrorCode(),
                    SysDefaultExceptionEnum.NULL_PARAM_EXCEPTION.getErrorMsg());
        }
    }

    /**
     * 查询账户计费项
     * @param queryAcctChargingTermDto
     * @param page
     * @return
     */
    @Override
    public List<BillingAcctChargingTerm> queryAcctChargingTermList(QueryAcctChargingTermDto queryAcctChargingTermDto,
                                                                   ResultPage<BillingAcctChargingTerm> page) {
        BillingAcctChargingTerm acctChargingTerm = null;
        if(null != queryAcctChargingTermDto){
            acctChargingTerm = new BillingAcctChargingTerm();
            BeanUtils.copyProperties(queryAcctChargingTermDto, acctChargingTerm, BillingAcctChargingTerm.class);
        }
        return billingUserAcctMapper.queryAcctChargingTermList(acctChargingTerm, page);
    }

    /**
     * 查询账户计费项条数
     * @param queryAcctChargingTermDto
     * @return
     */
    @Override
    public int queryAcctChargingTermCount(QueryAcctChargingTermDto queryAcctChargingTermDto) {
        BillingAcctChargingTerm acctChargingTerm = null;
        if(null != queryAcctChargingTermDto){
            acctChargingTerm = new BillingAcctChargingTerm();
            BeanUtils.copyProperties(queryAcctChargingTermDto, acctChargingTerm, BillingAcctChargingTerm.class);
        }
        return billingUserAcctMapper.queryAcctChargingTermCount(acctChargingTerm);
    }

    /**
     * 新增账户计费项
     * @param acctChargingTermDto
     * @return
     */
    @Override
    public BillingAcctChargingTerm addAcctChargingTerm(AcctChargingTermDto acctChargingTermDto) {
        if(null != acctChargingTermDto
            && !StringUtils.isEmpty(acctChargingTermDto.getChargingItemId())
            && !StringUtils.isEmpty(acctChargingTermDto.getAccountId())) {
            String chargingItemId = acctChargingTermDto.getChargingItemId();
            String userId = acctChargingTermDto.getUserId();
            String accountId = acctChargingTermDto.getAccountId();
            BillingAcctChargingTerm acctChargingTermExist = billingUserAcctMapper.queryAcctChargingTerm(accountId, userId, chargingItemId);
            if(null != acctChargingTermExist){
                throw new BaseException(SysDefaultExceptionEnum.DEFINE_EXCEPTION.getErrorCode(),
                        "账户计费项信息已经存在!");
            }
            BillingAcctChargingTerm acctChargingTerm = new BillingAcctChargingTerm();
            BeanUtils.copyProperties(acctChargingTermDto, acctChargingTerm, BillingAcctChargingTerm.class);
            acctChargingTerm.setUserChargingId(idWorker.getBusiId(BusiTypeEnum.BILLING_ACCT.getType()));
            acctChargingTerm.setCreateTime(new Date());
            acctChargingTerm.setDelFlag(SysDelEnum.NORMAL.getState());
            billingUserAcctMapper.addAcctChargingTerm(acctChargingTerm);
            return acctChargingTerm;
        }else{
            throw new BaseException(SysDefaultExceptionEnum.NULL_PARAM_EXCEPTION.getErrorCode(),
                    SysDefaultExceptionEnum.NULL_PARAM_EXCEPTION.getErrorMsg());
        }
    }

    /**
     * 修改账户计费项
     * @param acctChargingTermDto
     * @return
     */
    @Override
    public boolean updAcctChargingTerm(AcctChargingTermDto acctChargingTermDto) {
        if(null != acctChargingTermDto && !StringUtils.isEmpty(acctChargingTermDto.getAccountId())
             && !StringUtils.isEmpty(acctChargingTermDto.getChargingItemId())) {
            String userChargingId = acctChargingTermDto.getUserChargingId();
            String userId = acctChargingTermDto.getUserId();
            String chargingItemId = acctChargingTermDto.getChargingItemId();
            String accountId = acctChargingTermDto.getAccountId();
            BillingAcctChargingTerm acctChargingTermExist = billingUserAcctMapper.queryAcctChargingTerm(accountId, userId, chargingItemId);
            if(null == acctChargingTermExist){
                throw new BaseException(SysDefaultExceptionEnum.DEFINE_EXCEPTION.getErrorCode(),
                        "该用户的账户计费项信息不存在!");
            }

            BillingAcctChargingTerm acctChargingTerm = new BillingAcctChargingTerm();
            BeanUtils.copyProperties(acctChargingTermDto, acctChargingTerm, BillingAcctChargingTerm.class);
            acctChargingTerm.setUpdateTime(new Date());
            boolean bool = DaoHandler.getMapperBoolRes(billingUserAcctMapper.updAcctChargingTerm(acctChargingTerm));
            return bool;
        }else{
            throw new BaseException(SysDefaultExceptionEnum.NULL_PARAM_EXCEPTION.getErrorCode(),
                    SysDefaultExceptionEnum.NULL_PARAM_EXCEPTION.getErrorMsg());
        }
    }

    /**
     * 删除账户计费项
     * @param userChargingId
     * @return
     */
    @Override
    public boolean delAcctChargingTerm(String userChargingId) {
        if(!StringUtils.isEmpty(userChargingId)) {
            boolean bool = DaoHandler.getMapperBoolRes(billingUserAcctMapper.delAcctChargingTerm(userChargingId));
            return bool;
        }else{
            throw new BaseException(SysDefaultExceptionEnum.NULL_PARAM_EXCEPTION.getErrorCode(),
                    SysDefaultExceptionEnum.NULL_PARAM_EXCEPTION.getErrorMsg());
        }
    }

    /**
     * 查询计费记录
     * @param queryChargingRecordDto
     * @param page
     * @return
     */
    @Override
    public List<BillingAcctChargingRecord> queryAcctChargingRecordList(QueryChargingRecordDto queryChargingRecordDto, ResultPage<BillingAcctChargingRecord> page) {
        BillingAcctChargingRecord acctChargingRecord = null;
        if(null != queryChargingRecordDto){
            acctChargingRecord = new BillingAcctChargingRecord();
            BeanUtils.copyProperties(queryChargingRecordDto, acctChargingRecord, BillingAcctChargingRecord.class);
        }
        return billingUserAcctMapper.queryAcctChargingRecordList(acctChargingRecord, page);
    }

    /**
     * 查询计费记录数量
     * @param queryChargingRecordDto
     * @return
     */
    @Override
    public int queryAcctChargingRecordCount(QueryChargingRecordDto queryChargingRecordDto) {
        BillingAcctChargingRecord acctChargingRecord = null;
        if(null != queryChargingRecordDto){
            acctChargingRecord = new BillingAcctChargingRecord();
            BeanUtils.copyProperties(queryChargingRecordDto, acctChargingRecord, BillingAcctChargingRecord.class);
        }
        return billingUserAcctMapper.queryAcctChargingRecordCount(acctChargingRecord);
    }

    /**
     * 查询账户推送设置列表
     * @param queryUserAcctDto
     * @param page
     * @return
     */
    @Override
    public List<BillingUserAcctSetBean> queryUserAcctSetList(QueryUserAcctDto queryUserAcctDto, ResultPage<BillingUserAcctSetBean> page) {
        BillingUserAcctSetBean userAcctSet = null;
        if(null != queryUserAcctDto){
            userAcctSet = new BillingUserAcctSetBean();
            BeanUtils.copyProperties(queryUserAcctDto, userAcctSet, BillingUserAcctSetBean.class);
        }
        return billingUserAcctMapper.queryUserAcctSetList(userAcctSet, page);
    }

    /**
     * 查询账户推送设置数量
     * @param queryUserAcctDto
     * @return
     */
    @Override
    public int queryUserAcctSetCount(QueryUserAcctDto queryUserAcctDto) {
        BillingUserAcctSetBean userAcctSet = null;
        if(null != queryUserAcctDto){
            userAcctSet = new BillingUserAcctSetBean();
            BeanUtils.copyProperties(queryUserAcctDto, userAcctSet, BillingUserAcctSetBean.class);
        }
        return billingUserAcctMapper.queryUserAcctSetCount(userAcctSet);
    }

    /**
     * 查询账户推送设置
     * @param accountId
     * @param setKey
     * @return
     */
    @Override
    public BillingUserAcctSetBean queryUserAcctSet(String accountId, String setKey) {
        return billingUserAcctMapper.queryUserAcctSet(accountId, setKey);
    }

    /**
     * 查询账户推送设置
     * @param userAcctSetDto
     * @return
     */
    @Override
    public BillingUserAcctSetBean addUserAcctSet(UserAcctSetDto userAcctSetDto) {
        if(null != userAcctSetDto && !StringUtils.isEmpty(userAcctSetDto.getAccountId())
            && !StringUtils.isEmpty(userAcctSetDto.getSetKey())) {
            String accountId = userAcctSetDto.getAccountId();
            String setKey = userAcctSetDto.getSetKey();
            BillingUserAcctSetBean acctSet = billingUserAcctMapper.queryUserAcctSet(accountId, setKey);
            if(null != acctSet){
                throw new BaseException(SysDefaultExceptionEnum.DEFINE_EXCEPTION.getErrorCode(),
                        "账户配置信息已经存在!");
            }
            BillingUserAcctSetBean userAcctSet = new BillingUserAcctSetBean();
            BeanUtils.copyProperties(userAcctSetDto, userAcctSet, BillingUserAcctSetBean.class);
            userAcctSet.setAcctSetId(idWorker.getBusiId(BusiTypeEnum.BILLING_ACCT.getType()));
            userAcctSet.setCreateTime(new Date());
            userAcctSet.setDelFlag(SysDelEnum.NORMAL.getState());
            //企业余额阈值
            if(ThresholdKeyEnum.BalanceEarlyWarning.getKey().equals(setKey)){
                String setValue = null != userAcctSet.getSetValue()?(userAcctSet.getSetValue()+"00"):"0";//元转化成分
                userAcctSet.setSetValue(setValue);
            }

            //新增配置信息
            boolean bool = DaoHandler.getMapperBoolRes(billingUserAcctMapper.addUserAcctSet(userAcctSet));
            if(bool){
                if(ThresholdKeyEnum.BalanceEarlyWarning.getKey().equals(setKey)) {
                    //消息通知
                    this.thresholdNotify(userAcctSet);
                }
            }
            return bool?userAcctSet:null;
        }else{
            throw new BaseException(SysDefaultExceptionEnum.NULL_PARAM_EXCEPTION.getErrorCode(),
                    SysDefaultExceptionEnum.NULL_PARAM_EXCEPTION.getErrorMsg());
        }
    }

    /**
     * 设置阈值消息通知
     * @param userAcctSet
     */
    private void thresholdNotify(BillingUserAcctSetBean userAcctSet){
        try {
            if (null != userAcctSet) {
                BigDecimal thresholdAmount = new BigDecimal(userAcctSet.getSetValue());
                String accountId = userAcctSet.getAccountId();
                BillingUserAcctBean acct = billingUserAcctMapper.queryUserAcct(accountId);
                if (null != acct) {
                    //当可用剩余金额小于阈值，则消息通知
                    if (null != acct.getAvailableBalance()
                            && acct.getAvailableBalance().compareTo(thresholdAmount) < 0) {
                        List<UserAcctThresholdVo> thresholdVoList = new ArrayList<UserAcctThresholdVo>();
                        UserAcctThresholdVo thresholdVo = new UserAcctThresholdVo();
                        BeanUtils.copyProperties(acct, thresholdVo, UserAcctThresholdVo.class);
                        thresholdVo.setThresholdKey(userAcctSet.getSetKey());
                        thresholdVo.setThresholdValue(userAcctSet.getSetValue());
                        thresholdVoList.add(thresholdVo);
                        msgNotifyComponent.notifyByThreshold(thresholdVoList);
                    }
                }
            }
        }catch(Exception e){
            logger.error("设置阈值消息通知异常", e);
        }
    }

    /**
     * 新增账户推送设置
     * @param userAcctSetDto
     * @return
     */
    @Override
    public boolean updUserAcctSet(UserAcctSetDto userAcctSetDto) {
        if(null != userAcctSetDto
                && !StringUtils.isEmpty(userAcctSetDto.getAccountId()) && !StringUtils.isEmpty(userAcctSetDto.getSetKey())) {
            boolean bool = false;
            //查询推送设置
            String accountId = userAcctSetDto.getAccountId();
            String setKey = userAcctSetDto.getSetKey();
            BillingUserAcctSetBean acctSetExist = billingUserAcctMapper.queryUserAcctSet(accountId, setKey);
            BillingUserAcctSetBean userAcctSet = new BillingUserAcctSetBean();
            if(null != acctSetExist){//已存在，修改
                BeanUtils.copyProperties(userAcctSetDto, userAcctSet, BillingUserAcctSetBean.class);
                userAcctSet.setUpdateTime(new Date());
                //企业余额阈值
                if(ThresholdKeyEnum.BalanceEarlyWarning.getKey().equals(setKey)){
                    String setValue = null != userAcctSet.getSetValue()?(userAcctSet.getSetValue()+"00"):"0";//元转化成分
                    userAcctSet.setSetValue(setValue);
                }
                bool = DaoHandler.getMapperBoolRes(billingUserAcctMapper.updUserAcctSet(userAcctSet));
            }else{//不存在，新增
                BeanUtils.copyProperties(userAcctSetDto, userAcctSet, BillingUserAcctSetBean.class);
                userAcctSet.setAcctSetId(idWorker.getBusiId(BusiTypeEnum.BILLING_ACCT.getType()));
                userAcctSet.setCreateTime(new Date());
                userAcctSet.setDelFlag(SysDelEnum.NORMAL.getState());
                //企业余额阈值
                if(ThresholdKeyEnum.BalanceEarlyWarning.getKey().equals(setKey)){
                    String setValue = null != userAcctSet.getSetValue()?(userAcctSet.getSetValue()+"00"):"0";//元转化成分
                    userAcctSet.setSetValue(setValue);
                }
                bool = DaoHandler.getMapperBoolRes(billingUserAcctMapper.addUserAcctSet(userAcctSet));
            }

            if(bool){
                if(ThresholdKeyEnum.BalanceEarlyWarning.getKey().equals(setKey)) {
                    //消息通知
                    this.thresholdNotify(userAcctSet);
                }
            }
            return bool;
        }else{
            throw new BaseException(SysDefaultExceptionEnum.NULL_PARAM_EXCEPTION.getErrorCode(),
                    SysDefaultExceptionEnum.NULL_PARAM_EXCEPTION.getErrorMsg());
        }
    }

    /**
     * 删除账户推送设置
     * @param acctSetId
     * @return
     */
    @Override
    public boolean delUserAcctSet(String acctSetId) {
        if(!StringUtils.isEmpty(acctSetId)) {
            boolean bool = DaoHandler.getMapperBoolRes(billingUserAcctMapper.delUserAcctSet(acctSetId));
            return bool;
        }else{
            throw new BaseException(SysDefaultExceptionEnum.NULL_PARAM_EXCEPTION.getErrorCode(),
                    SysDefaultExceptionEnum.NULL_PARAM_EXCEPTION.getErrorMsg());
        }
    }
}
