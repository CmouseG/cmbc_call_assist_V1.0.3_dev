package com.guiji.billing.dao.mapper;

import com.guiji.billing.entity.BillingAcctChargingTotal;

public interface BillingAcctChargingTotalMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BillingAcctChargingTotal record);

    int insertSelective(BillingAcctChargingTotal record);

    BillingAcctChargingTotal selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BillingAcctChargingTotal record);

    int updateByPrimaryKey(BillingAcctChargingTotal record);
}