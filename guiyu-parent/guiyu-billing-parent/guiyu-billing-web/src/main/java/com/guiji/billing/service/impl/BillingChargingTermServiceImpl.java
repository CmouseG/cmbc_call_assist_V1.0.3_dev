package com.guiji.billing.service.impl;

import com.guiji.billing.constants.BusiTypeEnum;
import com.guiji.billing.dao.mapper.ext.BillingChargingTermMapper;
import com.guiji.billing.dto.ChargingTermDto;
import com.guiji.billing.dto.QueryChargingTermDto;
import com.guiji.billing.entity.BillingChargingTermBean;
import com.guiji.billing.enums.AcctChargingStatusEnum;
import com.guiji.billing.enums.SysDefaultExceptionEnum;
import com.guiji.billing.exception.BaseException;
import com.guiji.billing.service.BillingChargingTermService;
import com.guiji.billing.sys.ResultPage;
import com.guiji.billing.utils.DaoHandler;
import com.guiji.billing.utils.IdWorker;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 计费项service
 */
@Service
public class BillingChargingTermServiceImpl implements BillingChargingTermService {

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private BillingChargingTermMapper billingChargingTermMapper;

    @Override
    public List<BillingChargingTermBean> queryChargingTermList(QueryChargingTermDto queryChargingTermDto, ResultPage<BillingChargingTermBean> page) {
        BillingChargingTermBean chargingTerm = null;
        if(null != queryChargingTermDto){
            chargingTerm = new BillingChargingTermBean();
            BeanUtils.copyProperties(queryChargingTermDto, chargingTerm, BillingChargingTermBean.class);
        }
        return billingChargingTermMapper.queryChargingTermList(chargingTerm, page);
    }

    @Override
    public int queryChargingTermCount(QueryChargingTermDto queryChargingTermDto) {
        BillingChargingTermBean chargingTerm = null;
        if(null != queryChargingTermDto){
            chargingTerm = new BillingChargingTermBean();
            BeanUtils.copyProperties(queryChargingTermDto, chargingTerm, BillingChargingTermBean.class);
        }
        return billingChargingTermMapper.queryChargingTermCount(chargingTerm);
    }

    @Override
    public BillingChargingTermBean addChargingTerm(ChargingTermDto chargingTermDto) {
        if(null != chargingTermDto) {
            BillingChargingTermBean chargingTerm = new BillingChargingTermBean();
            BeanUtils.copyProperties(chargingTermDto, chargingTerm, BillingChargingTermBean.class);
            String chargingItemId = idWorker.getBusiId(BusiTypeEnum.BILLING_ACCT.getType());
            chargingTerm.setChargingItemId(chargingItemId);
            chargingTerm.setCreateTime(new Date());
            boolean bool = DaoHandler.getMapperBoolRes(billingChargingTermMapper.addChargingTerm(chargingTerm));
            return bool?chargingTerm:null;
        }else{
            throw new BaseException(SysDefaultExceptionEnum.NULL_PARAM_EXCEPTION.getErrorCode(),
                    SysDefaultExceptionEnum.NULL_PARAM_EXCEPTION.getErrorMsg());
        }
    }

    @Override
    public boolean updChargingTerm(ChargingTermDto chargingTermDto) {
        if(null != chargingTermDto && !StringUtils.isEmpty(chargingTermDto.getChargingItemId())) {
            BillingChargingTermBean chargingTerm = new BillingChargingTermBean();
            BeanUtils.copyProperties(chargingTermDto, chargingTerm, BillingChargingTermBean.class);
            chargingTerm.setUpdateTime(new Date());
            boolean bool = DaoHandler.getMapperBoolRes(billingChargingTermMapper.updChargingTerm(chargingTerm));
            return bool;
        }else{
            throw new BaseException(SysDefaultExceptionEnum.NULL_PARAM_EXCEPTION.getErrorCode(),
                    SysDefaultExceptionEnum.NULL_PARAM_EXCEPTION.getErrorMsg());
        }
    }

    @Override
    public boolean delChargingTerm(String chargingItemId) {
        if(!StringUtils.isEmpty(chargingItemId)) {
            boolean bool = DaoHandler.getMapperBoolRes(
                    billingChargingTermMapper.delChargingTerm(chargingItemId, new Date()));
            return bool;
        }else{
            throw new BaseException(SysDefaultExceptionEnum.NULL_PARAM_EXCEPTION.getErrorCode(),
                    SysDefaultExceptionEnum.NULL_PARAM_EXCEPTION.getErrorMsg());
        }
    }

    @Override
    public boolean updChargingTermStatus(String chargingItemId, Integer status) {
        if(!StringUtils.isEmpty(chargingItemId) && null != status) {
            boolean bool = DaoHandler.getMapperBoolRes(
                    billingChargingTermMapper.updChargingTermStatus(chargingItemId, status, new Date()));
            if(bool){
                if(status == AcctChargingStatusEnum.START_UP.getStatus()){//启用

                }else if(status == AcctChargingStatusEnum.OFF.getStatus()){//停用
                    //停用是否将用户的此项设置计费项全部停用

                }
            }
            return bool;
        }else{
            throw new BaseException(SysDefaultExceptionEnum.NULL_PARAM_EXCEPTION.getErrorCode(),
                    SysDefaultExceptionEnum.NULL_PARAM_EXCEPTION.getErrorMsg());
        }
    }
}
