package com.guiji.billing.dao.mapper.ext;

import com.guiji.billing.dto.QueryTotalChargingItemDto;
import com.guiji.billing.sys.ResultPage;
import com.guiji.billing.vo.TotalChargingItemDetailVo;
import com.guiji.billing.vo.TotalChargingItemVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillingCompanyTotalMapper {

    List<TotalChargingItemVo> totalCompanyChargingByMonth(@Param("operUserId") String operUserId,
                                                          @Param("orgCode") String orgCode,
                                                          @Param("beginMonth") String beginMonth, @Param("endMonth") String endMonth,
                                                          @Param("authLevel") Integer authLevel,
                                                          @Param("page")   ResultPage<TotalChargingItemVo> page);

    int totalChargingCountByMonth(@Param("operUserId") String operUserId,
                                  @Param("orgCode") String orgCode,
                                  @Param("beginMonth") String beginMonth, @Param("endMonth") String endMonth,
                                  @Param("authLevel") Integer authLevel);

    List<TotalChargingItemVo> totalCompanyChargingByDate(@Param("operUserId") String operUserId,
                                                          @Param("orgCode") String orgCode,
                                                          @Param("beginDate") String beginDate, @Param("endDate") String endDate,
                                                          @Param("authLevel") Integer authLevel,
                                                          @Param("page")   ResultPage<TotalChargingItemVo> page);


    int totalChargingCountByDate(@Param("operUserId") String operUserId,
                                 @Param("orgCode") String orgCode,
                                 @Param("beginDate") String beginDate, @Param("endDate") String endDate,
                                 @Param("authLevel") Integer authLevel);

    /******************************/

    List<TotalChargingItemDetailVo> totalChargingItemList(@Param("chargingItemId") String chargingItemId,
                                                          @Param("beginDate") String beginDate, @Param("endDate") String endDate,
                                                          @Param("page")   ResultPage<TotalChargingItemDetailVo> page);


    int totalChargingItemCount(@Param("chargingItemId") String chargingItemId,
                                 @Param("beginDate") String beginDate, @Param("endDate") String endDate);

}
