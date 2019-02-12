package com.guiji.billing.dao.mapper;

import com.guiji.billing.entity.BillingUserAcctBean;

public interface BillingUserAcctBeanMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BillingUserAcctBean record);

    int insertSelective(BillingUserAcctBean record);

    BillingUserAcctBean selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BillingUserAcctBean record);

    int updateByPrimaryKey(BillingUserAcctBean record);
}