package com.guiji.billing.dao.mapper;

import com.guiji.billing.entity.BillingNotifyMsgRecord;

public interface BillingNotifyMsgRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BillingNotifyMsgRecord record);

    int insertSelective(BillingNotifyMsgRecord record);

    BillingNotifyMsgRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BillingNotifyMsgRecord record);

    int updateByPrimaryKey(BillingNotifyMsgRecord record);
}