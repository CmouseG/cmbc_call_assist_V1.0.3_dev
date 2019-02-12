package com.guiji.billing.service;

import com.guiji.billing.dto.ChargingTermDto;
import com.guiji.billing.dto.QueryChargingTermDto;
import com.guiji.billing.entity.BillingChargingTermBean;
import com.guiji.billing.sys.ResultPage;

import java.util.List;

public interface BillingChargingTermService {

    /**
     * 查询计费项列表
     * @param queryChargingTermDto
     * @param page
     * @return
     */
    List<BillingChargingTermBean> queryChargingTermList(QueryChargingTermDto queryChargingTermDto, ResultPage<BillingChargingTermBean> page);

    /**
     * 查询计费项数量
     * @param queryChargingTermDto
     * @return
     */
    int queryChargingTermCount(QueryChargingTermDto queryChargingTermDto);

    /**
     * 新增计费项
     * @param chargingTermDto
     * @return
     */
    BillingChargingTermBean addChargingTerm(ChargingTermDto chargingTermDto);

    /**
     * 修改计费项
     * @param chargingTermDto
     * @return
     */
    boolean updChargingTerm(ChargingTermDto chargingTermDto);

    /**
     * 删除计费项
     * @param chargingItemId
     * @return
     */
    boolean delChargingTerm(String chargingItemId);

    /**
     * 启用/停用计费项
     * @param chargingItemId
     * @param status
     * @return
     */
    boolean updChargingTermStatus(String chargingItemId, Integer status);
}
