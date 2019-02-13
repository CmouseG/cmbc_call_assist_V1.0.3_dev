package com.guiji.billing.dao.mapper;

import com.guiji.billing.entity.BillingUserAcctSetBean;

public interface BillingUserAcctSetBeanMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BillingUserAcctSetBean record);

    int insertSelective(BillingUserAcctSetBean record);

    BillingUserAcctSetBean selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BillingUserAcctSetBean record);

    int updateByPrimaryKey(BillingUserAcctSetBean record);
}