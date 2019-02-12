package com.guiji.billing.dao.mapper.ext;

import com.guiji.billing.entity.BillingChargingTermBean;
import com.guiji.billing.sys.ResultPage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BillingChargingTermMapper {

    //查询计费项列表
    List<BillingChargingTermBean> queryChargingTermList(@Param("chargingTerm") BillingChargingTermBean chargingTerm,
                                                        @Param("page") ResultPage<BillingChargingTermBean> page);
    //查询计费项数量
    int queryChargingTermCount(@Param("chargingTerm") BillingChargingTermBean chargingTerm);

    //新增计费项
    int addChargingTerm(BillingChargingTermBean chargingTerm);

    //修改计费项
    int updChargingTerm(BillingChargingTermBean chargingTerm);

    //删除计费项
    int delChargingTerm(@Param("chargingItemId") String chargingItemId, @Param("updateTime") Date updateTime);

    //变更计费项状态
    int updChargingTermStatus(@Param("chargingItemId") String chargingItemId, @Param("status") Integer status,
                              @Param("updateTime") Date updateTime);
}
