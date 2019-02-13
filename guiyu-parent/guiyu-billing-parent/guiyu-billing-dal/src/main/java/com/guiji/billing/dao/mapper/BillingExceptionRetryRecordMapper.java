package com.guiji.billing.dao.mapper;

import com.guiji.billing.entity.BillingExceptionRetryRecord;

public interface BillingExceptionRetryRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BillingExceptionRetryRecord record);

    int insertSelective(BillingExceptionRetryRecord record);

    BillingExceptionRetryRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BillingExceptionRetryRecord record);

    int updateByPrimaryKey(BillingExceptionRetryRecord record);
}