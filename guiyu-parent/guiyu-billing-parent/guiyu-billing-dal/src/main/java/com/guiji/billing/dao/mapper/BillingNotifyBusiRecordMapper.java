package com.guiji.billing.dao.mapper;

import com.guiji.billing.entity.BillingNotifyBusiRecord;

public interface BillingNotifyBusiRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BillingNotifyBusiRecord record);

    int insertSelective(BillingNotifyBusiRecord record);

    BillingNotifyBusiRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BillingNotifyBusiRecord record);

    int updateByPrimaryKey(BillingNotifyBusiRecord record);
}