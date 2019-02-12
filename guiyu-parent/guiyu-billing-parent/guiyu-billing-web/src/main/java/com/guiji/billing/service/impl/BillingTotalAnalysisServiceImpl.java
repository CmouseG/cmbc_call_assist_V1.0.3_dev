package com.guiji.billing.service.impl;

import com.guiji.billing.dao.mapper.ext.BillingChargingTermMapper;
import com.guiji.billing.dao.mapper.ext.BillingTotalAnalysisMapper;
import com.guiji.billing.dto.QueryAcctChargingTotalDto;
import com.guiji.billing.dto.QueryAcctRecDto;
import com.guiji.billing.entity.BillingAcctChargingTotal;
import com.guiji.billing.entity.BillingAcctReconciliation;
import com.guiji.billing.service.BillingTotalAnalysisService;
import com.guiji.billing.sys.ResultPage;
import com.guiji.billing.utils.DateTimeUtils;
import com.guiji.billing.vo.BillingTotalChargingConsumerVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 计费统计
 */
@Service
public class BillingTotalAnalysisServiceImpl implements BillingTotalAnalysisService {

    @Autowired
    private BillingTotalAnalysisMapper billingTotalAnalysisMapper;

    /**
     * 按日查询费用统计
     * @param queryAcctChargingTotalDto
     * @param page
     * @return
     */
    @Override
    public List<BillingAcctChargingTotal> totalAcctChargingByDay(QueryAcctChargingTotalDto queryAcctChargingTotalDto,
                                                                 ResultPage<BillingAcctChargingTotal> page) {
        BillingAcctChargingTotal totalParam = null;
        if(null != queryAcctChargingTotalDto){
            totalParam = new BillingAcctChargingTotal();
            BeanUtils.copyProperties(queryAcctChargingTotalDto, totalParam, BillingAcctChargingTotal.class);
        }

        String beginDate = queryAcctChargingTotalDto.getBeginDate();
        String endDate = queryAcctChargingTotalDto.getEndDate();
        if(!StringUtils.isEmpty(beginDate) && StringUtils.isEmpty(endDate)){
            endDate = DateTimeUtils.DEFAULT_END_DATE;
        }else if(StringUtils.isEmpty(beginDate) && !StringUtils.isEmpty(endDate)){
            beginDate = DateTimeUtils.DEFAULT_BEGIN_DATE;
        }
        return billingTotalAnalysisMapper.totalAcctChargingByDay(totalParam, beginDate, endDate, page);
    }

    @Override
    public int totalAcctChargingByDayCount(QueryAcctChargingTotalDto queryAcctChargingTotalDto) {
        BillingAcctChargingTotal totalParam = null;
        if(null != queryAcctChargingTotalDto){
            totalParam = new BillingAcctChargingTotal();
            BeanUtils.copyProperties(queryAcctChargingTotalDto, totalParam, BillingAcctChargingTotal.class);
        }

        String beginDate = queryAcctChargingTotalDto.getBeginDate();
        String endDate = queryAcctChargingTotalDto.getEndDate();
        if(!StringUtils.isEmpty(beginDate) && StringUtils.isEmpty(endDate)){
            endDate = DateTimeUtils.DEFAULT_END_DATE;
        }else if(StringUtils.isEmpty(beginDate) && !StringUtils.isEmpty(endDate)){
            beginDate = DateTimeUtils.DEFAULT_BEGIN_DATE;
        }
        return billingTotalAnalysisMapper.totalAcctChargingByDayCount(totalParam, beginDate, endDate);
    }

    /**
     * 每月话费查询
     * @param queryAcctChargingTotalDto
     * @param page
     * @return
     */
    @Override
    public List<BillingAcctChargingTotal> totalAcctChargingByMonth(QueryAcctChargingTotalDto queryAcctChargingTotalDto, ResultPage<BillingAcctChargingTotal> page) {
        BillingAcctChargingTotal totalParam = null;
        if(null != queryAcctChargingTotalDto){
            totalParam = new BillingAcctChargingTotal();
            BeanUtils.copyProperties(queryAcctChargingTotalDto, totalParam, BillingAcctChargingTotal.class);
        }

        String beginMonth = queryAcctChargingTotalDto.getBeginMonth();
        String endMonth = queryAcctChargingTotalDto.getEndMonth();
        if(!StringUtils.isEmpty(beginMonth) && StringUtils.isEmpty(endMonth)){
            endMonth = DateTimeUtils.DEFAULT_END_MONTH;
        }else if(StringUtils.isEmpty(beginMonth) && !StringUtils.isEmpty(endMonth)){
            beginMonth = DateTimeUtils.DEFAULT_BEGIN_MONTH;
        }
        return billingTotalAnalysisMapper.totalAcctChargingByMonth(totalParam, beginMonth, endMonth, page);
    }

    /**
     *
     * @param queryAcctChargingTotalDto
     * @return
     */
    @Override
    public int totalAcctChargingByMonthCount(QueryAcctChargingTotalDto queryAcctChargingTotalDto) {
        BillingAcctChargingTotal totalParam = null;
        if(null != queryAcctChargingTotalDto){
            totalParam = new BillingAcctChargingTotal();
            BeanUtils.copyProperties(queryAcctChargingTotalDto, totalParam, BillingAcctChargingTotal.class);
        }
        String beginMonth = queryAcctChargingTotalDto.getBeginMonth();
        String endMonth = queryAcctChargingTotalDto.getEndMonth();
        if(!StringUtils.isEmpty(beginMonth) && StringUtils.isEmpty(endMonth)){
            endMonth = DateTimeUtils.DEFAULT_END_MONTH;
        }else if(StringUtils.isEmpty(beginMonth) && !StringUtils.isEmpty(endMonth)){
            beginMonth = DateTimeUtils.DEFAULT_BEGIN_MONTH;
        }
        return billingTotalAnalysisMapper.totalAcctChargingByMonthCount(totalParam, beginMonth, endMonth);
    }

    /**********************************/

    /**
     * 按日统计费用
     * @param queryAcctChargingTotalDto
     * @param page
     * @return
     */
    @Override
    public List<BillingTotalChargingConsumerVo> totalChargingByDate(QueryAcctChargingTotalDto queryAcctChargingTotalDto, ResultPage<BillingTotalChargingConsumerVo> page) {
        String beginDate = queryAcctChargingTotalDto.getBeginDate();
        String endDate = queryAcctChargingTotalDto.getEndDate();
        if(!StringUtils.isEmpty(beginDate) && StringUtils.isEmpty(endDate)){
            endDate = DateTimeUtils.DEFAULT_END_DATE;
        }else if(StringUtils.isEmpty(beginDate) && !StringUtils.isEmpty(endDate)){
            beginDate = DateTimeUtils.DEFAULT_BEGIN_DATE;
        }
        return billingTotalAnalysisMapper.totalChargingByDate(beginDate, endDate, page);
    }

    @Override
    public int totalChargingCountByDate(QueryAcctChargingTotalDto queryAcctChargingTotalDto) {
        String beginDate = queryAcctChargingTotalDto.getBeginDate();
        String endDate = queryAcctChargingTotalDto.getEndDate();
        if(!StringUtils.isEmpty(beginDate) && StringUtils.isEmpty(endDate)){
            endDate = DateTimeUtils.DEFAULT_END_DATE;
        }else if(StringUtils.isEmpty(beginDate) && !StringUtils.isEmpty(endDate)){
            beginDate = DateTimeUtils.DEFAULT_BEGIN_DATE;
        }
        return billingTotalAnalysisMapper.totalChargingCountByDate(beginDate, endDate);
    }

    /**
     * 按月统计费用
     * @param queryAcctChargingTotalDto
     * @param page
     * @return
     */
    @Override
    public List<BillingTotalChargingConsumerVo> totalChargingByMonth(QueryAcctChargingTotalDto queryAcctChargingTotalDto, ResultPage<BillingTotalChargingConsumerVo> page) {
        String beginMonth = queryAcctChargingTotalDto.getBeginMonth();
        String endMonth = queryAcctChargingTotalDto.getEndMonth();
        if(!StringUtils.isEmpty(beginMonth) && StringUtils.isEmpty(endMonth)){
            endMonth = DateTimeUtils.DEFAULT_END_MONTH;
        }else if(StringUtils.isEmpty(beginMonth) && !StringUtils.isEmpty(endMonth)){
            beginMonth = DateTimeUtils.DEFAULT_BEGIN_MONTH;
        }
        return billingTotalAnalysisMapper.totalChargingByMonth(beginMonth, endMonth, page);
    }

    @Override
    public int totalChargingCountByMonth(QueryAcctChargingTotalDto queryAcctChargingTotalDto) {
        String beginMonth = queryAcctChargingTotalDto.getBeginMonth();
        String endMonth = queryAcctChargingTotalDto.getEndMonth();
        if(!StringUtils.isEmpty(beginMonth) && StringUtils.isEmpty(endMonth)){
            endMonth = DateTimeUtils.DEFAULT_END_MONTH;
        }else if(StringUtils.isEmpty(beginMonth) && !StringUtils.isEmpty(endMonth)){
            beginMonth = DateTimeUtils.DEFAULT_BEGIN_MONTH;
        }
        return billingTotalAnalysisMapper.totalChargingCountByMonth(beginMonth, endMonth);
    }


    /******************************************************/
    /**
     * 查询对账记录
     * @param queryAcctRecDto
     * @param page
     * @return
     */
    @Override
    public List<BillingAcctReconciliation> queryAcctReconciliation(QueryAcctRecDto queryAcctRecDto,
                                                                  ResultPage<BillingAcctReconciliation> page) {
        BillingAcctReconciliation acctRec = null;
        if(null != queryAcctRecDto){
            acctRec = new BillingAcctReconciliation();
            BeanUtils.copyProperties(queryAcctRecDto, acctRec, BillingAcctReconciliation.class);
        }
        return billingTotalAnalysisMapper.queryAcctReconciliation(acctRec, page);
    }

    @Override
    public int queryAcctReconcCount(QueryAcctRecDto queryAcctRecDto) {
        BillingAcctReconciliation acctRec = null;
        if(null != queryAcctRecDto){
            acctRec = new BillingAcctReconciliation();
            BeanUtils.copyProperties(queryAcctRecDto, acctRec, BillingAcctReconciliation.class);
        }
        return billingTotalAnalysisMapper.queryAcctReconcCount(acctRec);
    }
}
