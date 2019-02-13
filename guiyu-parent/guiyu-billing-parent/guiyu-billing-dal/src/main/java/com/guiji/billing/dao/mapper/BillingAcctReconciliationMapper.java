package com.guiji.billing.dao.mapper;

import com.guiji.billing.entity.BillingAcctReconciliation;

public interface BillingAcctReconciliationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BillingAcctReconciliation record);

    int insertSelective(BillingAcctReconciliation record);

    BillingAcctReconciliation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BillingAcctReconciliation record);

    int updateByPrimaryKey(BillingAcctReconciliation record);
}