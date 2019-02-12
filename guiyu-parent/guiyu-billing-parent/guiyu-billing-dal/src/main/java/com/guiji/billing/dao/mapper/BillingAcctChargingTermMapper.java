package com.guiji.billing.dao.mapper;

import com.guiji.billing.entity.BillingAcctChargingTerm;

public interface BillingAcctChargingTermMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BillingAcctChargingTerm record);

    int insertSelective(BillingAcctChargingTerm record);

    BillingAcctChargingTerm selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BillingAcctChargingTerm record);

    int updateByPrimaryKey(BillingAcctChargingTerm record);
}