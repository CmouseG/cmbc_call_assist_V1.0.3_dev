package com.guiji.billing.dao.mapper;

import com.guiji.billing.entity.BillingChargingTermBean;

public interface BillingChargingTermBeanMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BillingChargingTermBean record);

    int insertSelective(BillingChargingTermBean record);

    BillingChargingTermBean selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BillingChargingTermBean record);

    int updateByPrimaryKey(BillingChargingTermBean record);
}