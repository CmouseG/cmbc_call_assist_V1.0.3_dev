package com.guiji.billing.service.impl;

import com.guiji.auth.api.IAuth;
import com.guiji.billing.dao.mapper.ext.BillingCompanyTotalMapper;
import com.guiji.billing.dto.QueryTotalChargingItemDto;
import com.guiji.billing.service.BillingCompanyTotalService;
import com.guiji.billing.service.GetApiService;
import com.guiji.billing.sys.ResultPage;
import com.guiji.billing.utils.DateTimeUtils;
import com.guiji.billing.utils.ResHandler;
import com.guiji.billing.vo.TotalChargingItemDetailVo;
import com.guiji.billing.vo.TotalChargingItemVo;
import com.guiji.user.dao.entity.SysOrganization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 企业查询统计
 */
@Service
public class BillingCompanyTotalServiceImpl implements BillingCompanyTotalService {

    @Autowired
    private BillingCompanyTotalMapper billingCompanyTotalMapper;

    @Autowired
    private GetApiService getApiService;

    /**
     * 统计公司企业日期、线路数据
     * @param queryTotalChargingItemDto
     * @param page
     * @return
     */
    @Override
    public List<TotalChargingItemVo> totalCompanyChargingItem(QueryTotalChargingItemDto queryTotalChargingItemDto, ResultPage<TotalChargingItemVo> page) {
        //获取用户ID
        String userId = null != queryTotalChargingItemDto.getUserId()?queryTotalChargingItemDto.getUserId():"1";
        //获取企业组织
        SysOrganization org = getApiService.getOrgByUserId(userId);
        //获取企业组织编码
        String orgCode = null != org?org.getCode():"1";
        if(null != queryTotalChargingItemDto && null != queryTotalChargingItemDto.getType()){
            if(1 == queryTotalChargingItemDto.getType()){//按日查询
                return billingCompanyTotalMapper.totalCompanyChargingByDate(queryTotalChargingItemDto.getOperUserId(),
                        orgCode,
                        queryTotalChargingItemDto.getBeginDate(), queryTotalChargingItemDto.getEndDate(), page);
            }else if(2 == queryTotalChargingItemDto.getType()){//按月查询
                return billingCompanyTotalMapper.totalCompanyChargingByMonth(queryTotalChargingItemDto.getOperUserId(),
                        orgCode,
                        queryTotalChargingItemDto.getBeginDate(), queryTotalChargingItemDto.getEndDate(), page);
            }
        }
        //默认
        return billingCompanyTotalMapper.totalCompanyChargingByDate(queryTotalChargingItemDto.getOperUserId(),
                orgCode,
                DateTimeUtils.DEFAULT_BEGIN_DATE, DateTimeUtils.DEFAULT_END_DATE, page);
    }

    /**
     * 统计公司企业日期、线路数量
     * @param queryTotalChargingItemDto
     * @return
     */
    @Override
    public int totalCompanyChargingCount(QueryTotalChargingItemDto queryTotalChargingItemDto) {
        //获取用户ID
        String userId = null != queryTotalChargingItemDto.getUserId()?queryTotalChargingItemDto.getUserId():"1";
        //获取企业组织
        SysOrganization org = getApiService.getOrgByUserId(userId);
        //获取企业组织编码
        String orgCode = null != org?org.getCode():"1";
        if(null != queryTotalChargingItemDto && null != queryTotalChargingItemDto.getType()){
            if(1 == queryTotalChargingItemDto.getType()){//按日查询
                return billingCompanyTotalMapper.totalChargingCountByDate(queryTotalChargingItemDto.getOperUserId(),
                        orgCode,
                        queryTotalChargingItemDto.getBeginDate(), queryTotalChargingItemDto.getEndDate());
            }else if(2 == queryTotalChargingItemDto.getType()){//按月查询
                return billingCompanyTotalMapper.totalChargingCountByMonth(queryTotalChargingItemDto.getOperUserId(),
                        orgCode,
                        queryTotalChargingItemDto.getBeginDate(), queryTotalChargingItemDto.getEndDate());
            }
        }
        //默认
        return billingCompanyTotalMapper.totalChargingCountByDate(queryTotalChargingItemDto.getOperUserId(),
                orgCode,
                DateTimeUtils.DEFAULT_BEGIN_DATE, DateTimeUtils.DEFAULT_END_DATE);
    }

    @Override
    public List<TotalChargingItemDetailVo> totalChargingItemList(QueryTotalChargingItemDto queryTotalChargingItemDto, ResultPage<TotalChargingItemDetailVo> page) {
        return billingCompanyTotalMapper.totalChargingItemList(queryTotalChargingItemDto.getChargingItemId(),
                queryTotalChargingItemDto.getBeginDate(), queryTotalChargingItemDto.getEndDate(), page);
    }

    @Override
    public int totalChargingItemCount(QueryTotalChargingItemDto queryTotalChargingItemDto) {
        return billingCompanyTotalMapper.totalChargingItemCount(queryTotalChargingItemDto.getChargingItemId(),
                queryTotalChargingItemDto.getBeginDate(), queryTotalChargingItemDto.getEndDate());
    }
}
