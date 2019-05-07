package com.guiji.billing.service.impl;

import com.guiji.auth.api.IAuth;
import com.guiji.billing.constants.AuthConstant;
import com.guiji.billing.dao.mapper.ext.BillingCompanyTotalMapper;
import com.guiji.billing.dto.QueryTotalChargingItemDto;
import com.guiji.billing.enums.AuthLevelEnum;
import com.guiji.billing.service.BillingCompanyTotalService;
import com.guiji.billing.service.GetApiService;
import com.guiji.billing.service.GetAuthUtil;
import com.guiji.billing.sys.ResultPage;
import com.guiji.billing.utils.DateTimeUtils;
import com.guiji.billing.utils.ResHandler;
import com.guiji.billing.vo.TotalChargingItemDetailVo;
import com.guiji.billing.vo.TotalChargingItemVo;
import com.guiji.user.dao.entity.SysOrganization;
import org.apache.commons.lang3.StringUtils;
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
    private GetAuthUtil getAuthUtil;

    /**
     * 统计公司企业日期、线路数据
     * @param queryTotalChargingItemDto
     * @param page
     * @return
     */
    @Override
    public List<TotalChargingItemVo> totalCompanyChargingItem(QueryTotalChargingItemDto queryTotalChargingItemDto, ResultPage<TotalChargingItemVo> page) {
        Integer authLevel = queryTotalChargingItemDto.getAuthLevel();//操作用户权限等级
        String operUserId = getAuthUtil.getUserIdByAuthLevel(authLevel, queryTotalChargingItemDto.getOperUserId());//获取用户ID
        String orgCode = getAuthUtil.getOrgCodeByAuthLevel(authLevel, operUserId, queryTotalChargingItemDto.getOrgCode());//获取企业组织编码
        //用户过滤条件
        String userId = queryTotalChargingItemDto.getUserId();
        if(null != queryTotalChargingItemDto && null != queryTotalChargingItemDto.getType()){
            if(1 == queryTotalChargingItemDto.getType()){//按日查询
                return billingCompanyTotalMapper.totalCompanyChargingByDate(operUserId,
                        orgCode,
                        queryTotalChargingItemDto.getBeginDate(), queryTotalChargingItemDto.getEndDate(),
                        queryTotalChargingItemDto.getAuthLevel(),
                        userId,
                        queryTotalChargingItemDto.getChargingItemId(),
                        page);
            }else if(2 == queryTotalChargingItemDto.getType()){//按月查询
                return billingCompanyTotalMapper.totalCompanyChargingByMonth(operUserId,
                        orgCode,
                        queryTotalChargingItemDto.getBeginDate(), queryTotalChargingItemDto.getEndDate(),
                        authLevel,
                        userId,
                        queryTotalChargingItemDto.getChargingItemId(),
                        page);
            }else if(3 == queryTotalChargingItemDto.getType()){//按时间段查询
                return billingCompanyTotalMapper.totalCompanyChargingByDay(operUserId,
                        orgCode,
                        queryTotalChargingItemDto.getBeginDate(), queryTotalChargingItemDto.getEndDate(),
                        authLevel,
                        userId,
                        queryTotalChargingItemDto.getChargingItemId(),
                        page);
            }
        }
        //默认
        return billingCompanyTotalMapper.totalCompanyChargingByDate(queryTotalChargingItemDto.getOperUserId(),
                orgCode,
                DateTimeUtils.DEFAULT_BEGIN_DATE, DateTimeUtils.DEFAULT_END_DATE,
                queryTotalChargingItemDto.getAuthLevel(),
                userId,
                queryTotalChargingItemDto.getChargingItemId(),
                page);
    }

    /**
     * 统计公司企业日期、线路数量
     * @param queryTotalChargingItemDto
     * @return
     */
    @Override
    public int totalCompanyChargingCount(QueryTotalChargingItemDto queryTotalChargingItemDto) {
        Integer authLevel = queryTotalChargingItemDto.getAuthLevel();//操作用户权限等级
        String operUserId = getAuthUtil.getUserIdByAuthLevel(authLevel, queryTotalChargingItemDto.getOperUserId());//获取用户ID
        String orgCode = getAuthUtil.getOrgCodeByAuthLevel(authLevel, operUserId, queryTotalChargingItemDto.getOrgCode());//获取企业组织编码
        //用户过滤条件
        String userId = queryTotalChargingItemDto.getUserId();
        if(null != queryTotalChargingItemDto && null != queryTotalChargingItemDto.getType()){
            if(1 == queryTotalChargingItemDto.getType()){//按日查询
                return billingCompanyTotalMapper.totalChargingCountByDate(operUserId,
                        orgCode,
                        queryTotalChargingItemDto.getBeginDate(), queryTotalChargingItemDto.getEndDate(),
                        authLevel,
                        userId,
                        queryTotalChargingItemDto.getChargingItemId());
            }else if(2 == queryTotalChargingItemDto.getType()){//按月查询
                return billingCompanyTotalMapper.totalChargingCountByMonth(operUserId,
                        orgCode,
                        queryTotalChargingItemDto.getBeginDate(), queryTotalChargingItemDto.getEndDate(),
                        authLevel,
                        userId,
                        queryTotalChargingItemDto.getChargingItemId());
            }else if(3 == queryTotalChargingItemDto.getType()){//按时间段查询
                return billingCompanyTotalMapper.totalChargingCountByDay(operUserId,
                        orgCode,
                        queryTotalChargingItemDto.getBeginDate(), queryTotalChargingItemDto.getEndDate(),
                        authLevel,
                        userId,
                        queryTotalChargingItemDto.getChargingItemId());
            }
        }
        //默认
        return billingCompanyTotalMapper.totalChargingCountByDate(operUserId,
                orgCode,
                DateTimeUtils.DEFAULT_BEGIN_DATE, DateTimeUtils.DEFAULT_END_DATE,
                authLevel,
                userId,
                queryTotalChargingItemDto.getChargingItemId());
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
