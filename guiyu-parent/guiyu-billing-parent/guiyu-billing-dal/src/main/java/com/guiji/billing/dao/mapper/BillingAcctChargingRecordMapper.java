package com.guiji.billing.dao.mapper;

import com.guiji.billing.entity.BillingAcctChargingRecord;

public interface BillingAcctChargingRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BillingAcctChargingRecord record);

    int insertSelective(BillingAcctChargingRecord record);

    BillingAcctChargingRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BillingAcctChargingRecord record);

    int updateByPrimaryKey(BillingAcctChargingRecord record);
}