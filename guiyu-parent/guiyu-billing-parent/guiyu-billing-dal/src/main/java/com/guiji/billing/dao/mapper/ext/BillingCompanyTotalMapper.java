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

    //按月统计
    List<TotalChargingItemVo> totalCompanyChargingByMonth(@Param("operUserId") String operUserId,
                                                          @Param("orgCode") String orgCode,
                                                          @Param("beginMonth") String beginMonth, @Param("endMonth") String endMonth,
                                                          @Param("authLevel") Integer authLevel,
                                                          @Param("userId") String userId,
                                                          @Param("chargingItemId") String chargingItemId,
                                                          @Param("page")   ResultPage<TotalChargingItemVo> page);

    //按月统计数量
    int totalChargingCountByMonth(@Param("operUserId") String operUserId,
                                  @Param("orgCode") String orgCode,
                                  @Param("beginMonth") String beginMonth, @Param("endMonth") String endMonth,
                                  @Param("authLevel") Integer authLevel,
                                  @Param("userId") String userId,
                                  @Param("chargingItemId") String chargingItemId);

    //按日统计
    List<TotalChargingItemVo> totalCompanyChargingByDate(@Param("operUserId") String operUserId,
                                                          @Param("orgCode") String orgCode,
                                                          @Param("beginDate") String beginDate, @Param("endDate") String endDate,
                                                          @Param("authLevel") Integer authLevel,
                                                          @Param("userId") String userId,
                                                          @Param("chargingItemId") String chargingItemId,
                                                          @Param("page")   ResultPage<TotalChargingItemVo> page);

    //按日统计数量
    int totalChargingCountByDate(@Param("operUserId") String operUserId,
                                 @Param("orgCode") String orgCode,
                                 @Param("beginDate") String beginDate, @Param("endDate") String endDate,
                                 @Param("authLevel") Integer authLevel,
                                 @Param("userId") String userId,
                                 @Param("chargingItemId") String chargingItemId);

    //按时间段统计
    List<TotalChargingItemVo> totalCompanyChargingByDay(@Param("operUserId") String operUserId,
                              @Param("orgCode") String orgCode,
                              @Param("beginDate") String beginDate, @Param("endDate") String endDate,
                              @Param("authLevel") Integer authLevel,
                              @Param("userId") String userId,
                              @Param("chargingItemId") String chargingItemId,
                              @Param("page")   ResultPage<TotalChargingItemVo> page);

    //按时间段统计数量
    int totalChargingCountByDay(@Param("operUserId") String operUserId,
                                @Param("orgCode") String orgCode,
                                @Param("beginDate") String beginDate, @Param("endDate") String endDate,
                                @Param("authLevel") Integer authLevel,
                                @Param("userId") String userId,
                                @Param("chargingItemId") String chargingItemId);
    /******************************/

    List<TotalChargingItemDetailVo> totalChargingItemList(@Param("chargingItemId") String chargingItemId,
                                                          @Param("beginDate") String beginDate, @Param("endDate") String endDate,
                                                          @Param("page")   ResultPage<TotalChargingItemDetailVo> page);


    int totalChargingItemCount(@Param("chargingItemId") String chargingItemId,
                                 @Param("beginDate") String beginDate, @Param("endDate") String endDate);

}
