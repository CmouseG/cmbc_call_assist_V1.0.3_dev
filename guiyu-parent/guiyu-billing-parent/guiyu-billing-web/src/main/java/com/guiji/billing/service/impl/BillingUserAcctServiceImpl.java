package com.guiji.billing.service.impl;

import com.guiji.auth.api.IAuth;
import com.guiji.auth.api.IOrg;
import com.guiji.billing.constants.BusiTypeEnum;
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
import com.guiji.billing.sys.ResultPage;
import com.guiji.billing.utils.DaoHandler;
import com.guiji.billing.utils.DateTimeUtils;
import com.guiji.billing.utils.IdWorker;
import com.guiji.billing.utils.ResHandler;
import com.guiji.billing.vo.ArrearageNotifyVo;
import com.guiji.billing.vo.UserRechargeTotalVo;
import com.guiji.component.result.Result;
import com.guiji.user.dao.entity.SysOrganization;
import com.guiji.user.dao.entity.SysUser;
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
    private IAuth iAuth;

    @Autowired
    private IOrg iOrg;

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
            //查询用户信息
        //    SysUser user = ResHandler.getResObj(iAuth.getUserById(Long.valueOf(userId)));
            //查询用户所在企业组织
            SysOrganization org = ResHandler.getResObj(iAuth.getOrgByUserId(Long.valueOf(userId)));
            return (null != org && !StringUtils.isEmpty(org.getCode()))?//user.getOrgCode()
                    billingUserAcctMapper.queryUserAcctByOrgCode(org.getCode()):null;
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
            //查询该企业是否已注册
            String orgCode = acctAddDto.getOrgCode();
            BillingUserAcctBean acctExist = billingUserAcctMapper.queryUserAcctByOrgCode(orgCode);
            //企业已注册账户
            if(null != acctExist){
                acct = acctExist;

            //企业未注册
            }else {
                //查询企业组织
                SysOrganization org = ResHandler.getResObj(iOrg.getOrgByCode(orgCode));
                if(null == org){
                    throw new BaseException(SysDefaultExceptionEnum.DEFINE_EXCEPTION.getErrorCode(),
                            "企业组织不存在");
                }
                BeanUtils.copyProperties(acctAddDto, acct, BillingUserAcctBean.class);
                acct.setAccountId(idWorker.getBusiId(BusiTypeEnum.BILLING_ACCT.getType()));
                acct.setOrgCode(org.getCode());
                acct.setCompanyId(org.getId()+"");
                acct.setCompanyName(org.getName());
                acct.setAmount(BigDecimal.ZERO);
                acct.setAvailableBalance(BigDecimal.ZERO);
                acct.setFreezingAmount(BigDecimal.ZERO);
                acct.setCreateTime(new Date());
                acct.setDelFlag(SysDelEnum.NORMAL.getState());
                boolean bool = DaoHandler.getMapperBoolRes(billingUserAcctMapper.addUserAcct(acct));
                acct = bool?acct:null;
            }
            return acct;
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
        com.guiji.vo.ArrearageNotifyVo arrearageNotifyVo = null;
        //查询欠费企业
        List<BillingUserAcctBean> list = this.queryArrearageAcctList();
        if (null != list) {
            List<String> userIdList = new ArrayList<String>();
            for (BillingUserAcctBean acct : list) {
                //获取企业组织下的所有用户
                String orgCode = acct.getOrgCode();
                Result.ReturnData<List<SysUser>> res = iAuth.getAllUserByOrgCode(orgCode);
                if (null != res && res.success) {
                    List<SysUser> userList = res.getBody();
                    for (SysUser user : userList) {
                        userIdList.add(user.getId() + "");
                    }
                }
            }
            if (null != userIdList && userIdList.size() > 0) {
                arrearageNotifyVo = new com.guiji.vo.ArrearageNotifyVo();
                //欠费用户ID
                arrearageNotifyVo.setUserIdList(userIdList);
                //欠费状态
                arrearageNotifyVo.setIsArrearage(AcctArrearageStatusEnum.ARREARAGE.getStatus());
            }
        }
        return arrearageNotifyVo;
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
            BigDecimal rechargeAmount = rechargeDto.getAmount();//充值金额
            BillingUserAcctBean acct = null;
            if(!StringUtils.isEmpty(rechargeDto.getAccountId())) {
                accountId = rechargeDto.getAccountId();
                //查询账户
                acct = billingUserAcctMapper.queryUserAcct(accountId);
                //账户充值
                bool = DaoHandler.getMapperBoolRes(billingUserAcctMapper.recharge(accountId, rechargeAmount, new Date()));

                //通知取消欠费消息
                this.notifyUnfreeze(acct, rechargeAmount);
            }else {
                //根据企业编码
                if(!StringUtils.isEmpty(rechargeDto.getOrgCode())){
                    //查询该企业是否已注册
                    String orgCode = rechargeDto.getOrgCode();
                    BillingUserAcctBean acctExist = billingUserAcctMapper.queryUserAcctByOrgCode(orgCode);
                    //企业已注册账户
                    if(null != acctExist){
                        acct = acctExist;
                        accountId = acct.getAccountId();
                        //账户充值
                        bool = DaoHandler.getMapperBoolRes(billingUserAcctMapper.recharge(accountId, rechargeAmount, new Date()));

                        //通知取消欠费消息
                        this.notifyUnfreeze(acct, rechargeAmount);

                    //企业未注册,先注册账户
                    }else {
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
            //充值记录
            if(bool){
                bool = this.rechargeRecord(acct, rechargeDto);
            }
            return bool;
        }else{
            throw new BaseException(SysDefaultExceptionEnum.NULL_PARAM_EXCEPTION.getErrorCode(),
                    SysDefaultExceptionEnum.NULL_PARAM_EXCEPTION.getErrorMsg());
        }
    }

    private void notifyUnfreeze(BillingUserAcctBean acct, BigDecimal rechargeAmount){
        try {
            BigDecimal balanceAmount = acct.getAvailableBalance();
            //如果充值使之前欠费变更为不欠费，通知取消欠费消息
            if (balanceAmount.compareTo(BigDecimal.ZERO) <= 0
                    && (rechargeAmount.add(balanceAmount)).compareTo(BigDecimal.ZERO) > 0) {
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

                if (null != userIdList && userIdList.size() > 0) {
                    arrearageNotifyVo = new com.guiji.vo.ArrearageNotifyVo();
                    //欠费用户ID
                    arrearageNotifyVo.setUserIdList(userIdList);
                    //欠费状态
                    arrearageNotifyVo.setIsArrearage(AcctArrearageStatusEnum.NORMAL.getStatus());

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
    private boolean rechargeRecord(BillingUserAcctBean acct, RechargeDto rechargeDto){
        BillingAcctChargingRecord chargingRecord = new BillingAcctChargingRecord();
        Date time = new Date();
        chargingRecord.setChargingId(idWorker.getBusiId(BusiTypeEnum.BILLING_ACCT.getType()));
        chargingRecord.setAccountId(acct.getAccountId());
        chargingRecord.setOperUserId(rechargeDto.getUserId());
        chargingRecord.setOperUserName("");
        chargingRecord.setOperUserOrgCode(acct.getOrgCode());
        chargingRecord.setOperBeginTime(null);
        chargingRecord.setOperEndTime(null);
        chargingRecord.setOperDuration(0L);
        chargingRecord.setOperDurationM(0L);
        chargingRecord.setOperDurationStr("00:00");

        chargingRecord.setType(ChargingTypeEnum.RECHARGE.getType());//消费
        chargingRecord.setFeeMode(AcctChargingFeeModeEnum.BANK_RECHARGE.getFeeCode());//通话消费
        chargingRecord.setUserChargingId(null);
        chargingRecord.setAmount(rechargeDto.getAmount());
        chargingRecord.setSrcAmount(acct.getAvailableBalance());
        chargingRecord.setToAmount(acct.getAvailableBalance().add(rechargeDto.getAmount()));
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
        if(null != queryRechargeDto && !StringUtils.isEmpty(queryRechargeDto.getAccountId())) {
            String accountId = queryRechargeDto.getAccountId();
            Date beginDate = queryRechargeDto.getBeginDate();
            Date endDate = queryRechargeDto.getEndDate();
            if(null != beginDate && null == endDate){
                endDate = DateTimeUtils.getDateByString(DateTimeUtils.DEFAULT_END_DATE, DateTimeUtils.DEFAULT_DATE_FORMAT_PATTERN_FULL);
            }else if(null == beginDate && null != endDate){
                beginDate = DateTimeUtils.getDateByString(DateTimeUtils.DEFAULT_BEGIN_DATE, DateTimeUtils.DEFAULT_DATE_FORMAT_PATTERN_FULL);
            }
            return billingUserAcctMapper.queryUserRechargeTotal(accountId, ChargingTypeEnum.RECHARGE.getType(), queryRechargeDto.getFeeMode(),
                    beginDate, endDate, page);
        }else{
            throw new BaseException(SysDefaultExceptionEnum.NULL_PARAM_EXCEPTION.getErrorCode(),
                    SysDefaultExceptionEnum.NULL_PARAM_EXCEPTION.getErrorMsg());
        }
    }

    /**
     * 查询用户充值条数
     * @param queryRechargeDto
     * @return
     */
    @Override
    public int queryUserRechargeCount(QueryRechargeDto queryRechargeDto) {
        if(null != queryRechargeDto && !StringUtils.isEmpty(queryRechargeDto.getAccountId())) {
            String accountId = queryRechargeDto.getAccountId();
            Date beginDate = queryRechargeDto.getBeginDate();
            Date endDate = queryRechargeDto.getEndDate();
            if(null != beginDate && null == endDate){
                endDate = DateTimeUtils.getDateByString(DateTimeUtils.DEFAULT_BEGIN_DATE, DateTimeUtils.DEFAULT_DATE_FORMAT_PATTERN_FULL);
            }else if(null == beginDate && null != endDate){
                beginDate = DateTimeUtils.getDateByString(DateTimeUtils.DEFAULT_END_DATE, DateTimeUtils.DEFAULT_DATE_FORMAT_PATTERN_FULL);
            }
            return billingUserAcctMapper.queryUserRechargeCount(accountId, ChargingTypeEnum.RECHARGE.getType(), queryRechargeDto.getFeeMode(),
                    beginDate, endDate);
        }else{
            throw new BaseException(SysDefaultExceptionEnum.NULL_PARAM_EXCEPTION.getErrorCode(),
                    SysDefaultExceptionEnum.NULL_PARAM_EXCEPTION.getErrorMsg());
        }
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
            if(null != org){
                //查询企业用户账户
                String orgCode = org.getCode();
                BillingUserAcctBean acct = billingUserAcctMapper.queryUserAcctByOrgCode(orgCode);
                if(null != acct){
                    String accountId = acct.getAccountId();
                    term = new BillingAcctChargingTerm();
                    term.setAccountId(accountId);
                    term.setUserId(userId);
                    term.setChargingItemId(chargingItemId);
                    term.setChargingItemName(chargingTermNotifyDto.getChargingItemName());
                    term.setChargingType(1);
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
                }
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
            if(null != acctChargingTermExist
                    && !userChargingId.equals(acctChargingTermExist.getUserChargingId())){
                throw new BaseException(SysDefaultExceptionEnum.DEFINE_EXCEPTION.getErrorCode(),
                        "该用户的账户计费项信息已经存在!");
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
            billingUserAcctMapper.addUserAcctSet(userAcctSet);
            return userAcctSet;
        }else{
            throw new BaseException(SysDefaultExceptionEnum.NULL_PARAM_EXCEPTION.getErrorCode(),
                    SysDefaultExceptionEnum.NULL_PARAM_EXCEPTION.getErrorMsg());
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
            String accountId = userAcctSetDto.getAccountId();
            String setKey = userAcctSetDto.getSetKey();
            BillingUserAcctSetBean acctSetExist = billingUserAcctMapper.queryUserAcctSet(accountId, setKey);
            BillingUserAcctSetBean userAcctSet = new BillingUserAcctSetBean();
            if(null != acctSetExist){//已存在，则修改
                BeanUtils.copyProperties(userAcctSetDto, userAcctSet, BillingUserAcctSetBean.class);
                userAcctSet.setUpdateTime(new Date());
                bool = DaoHandler.getMapperBoolRes(billingUserAcctMapper.updUserAcctSet(userAcctSet));
            }else{//不存在，则新增
                BeanUtils.copyProperties(userAcctSetDto, userAcctSet, BillingUserAcctSetBean.class);
                userAcctSet.setAcctSetId(idWorker.getBusiId(BusiTypeEnum.BILLING_ACCT.getType()));
                userAcctSet.setCreateTime(new Date());
                userAcctSet.setDelFlag(SysDelEnum.NORMAL.getState());
                bool = DaoHandler.getMapperBoolRes(billingUserAcctMapper.addUserAcctSet(userAcctSet));
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
