package com.guiji.billing.service.impl;

import com.guiji.auth.api.IAuth;
import com.guiji.billing.constants.AuthConstant;
import com.guiji.billing.dao.mapper.ext.BillingChargingTermMapper;
import com.guiji.billing.dao.mapper.ext.BillingTotalAnalysisMapper;
import com.guiji.billing.dto.QueryAcctChargingTotalDto;
import com.guiji.billing.dto.QueryAcctRecDto;
import com.guiji.billing.entity.BillingAcctChargingTotal;
import com.guiji.billing.entity.BillingAcctReconciliation;
import com.guiji.billing.service.BillingTotalAnalysisService;
import com.guiji.billing.service.GetApiService;
import com.guiji.billing.service.GetAuthUtil;
import com.guiji.billing.sys.ResultPage;
import com.guiji.billing.utils.DateTimeUtils;
import com.guiji.billing.utils.IdWorker;
import com.guiji.billing.utils.ResHandler;
import com.guiji.billing.vo.BillingTotalChargingConsumerVo;
import com.guiji.user.dao.entity.SysOrganization;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 计费统计
 */
@Service
public class BillingTotalAnalysisServiceImpl implements BillingTotalAnalysisService {

    private Logger logger = LoggerFactory.getLogger(BillingTotalAnalysisServiceImpl.class);

    @Autowired
    private BillingTotalAnalysisMapper billingTotalAnalysisMapper;

    @Autowired
    private GetAuthUtil getAuthUtil;

    @Autowired
    private GetApiService getApiService;

    @Autowired
    private IAuth iAuth;

    @Autowired
    private IdWorker idWorker;

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
        String beginDate = null;
        String endDate = null;
        if(null != queryAcctChargingTotalDto){
            totalParam = new BillingAcctChargingTotal();
            BeanUtils.copyProperties(queryAcctChargingTotalDto, totalParam, BillingAcctChargingTotal.class);
            beginDate = queryAcctChargingTotalDto.getBeginDate();
            endDate = queryAcctChargingTotalDto.getEndDate();
        }

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
        String beginDate = null;
        String endDate = null;
        if(null != queryAcctChargingTotalDto){
            totalParam = new BillingAcctChargingTotal();
            BeanUtils.copyProperties(queryAcctChargingTotalDto, totalParam, BillingAcctChargingTotal.class);
            beginDate = queryAcctChargingTotalDto.getBeginDate();
            endDate = queryAcctChargingTotalDto.getEndDate();
        }

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
        String beginMonth = null;
        String endMonth = null;
        if(null != queryAcctChargingTotalDto){
            totalParam = new BillingAcctChargingTotal();
            BeanUtils.copyProperties(queryAcctChargingTotalDto, totalParam, BillingAcctChargingTotal.class);
            beginMonth = queryAcctChargingTotalDto.getBeginMonth();
            endMonth = queryAcctChargingTotalDto.getEndMonth();
        }

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
        String beginMonth = null;
        String endMonth = null;
        if(null != queryAcctChargingTotalDto){
            totalParam = new BillingAcctChargingTotal();
            BeanUtils.copyProperties(queryAcctChargingTotalDto, totalParam, BillingAcctChargingTotal.class);
            beginMonth = queryAcctChargingTotalDto.getBeginMonth();
            endMonth = queryAcctChargingTotalDto.getEndMonth();
        }

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
        Integer authLevel = queryAcctChargingTotalDto.getAuthLevel();//操作用户权限等级
        String operUserId = getAuthUtil.getUserIdByAuthLevel(authLevel, queryAcctChargingTotalDto.getOperUserId());//获取用户ID
        String orgCode = getAuthUtil.getOrgCodeByAuthLevel(authLevel, operUserId, queryAcctChargingTotalDto.getOrgCode());//获取企业组织编码
        String beginDate = queryAcctChargingTotalDto.getBeginDate();
        String endDate = queryAcctChargingTotalDto.getEndDate();
        if(!StringUtils.isEmpty(beginDate) && StringUtils.isEmpty(endDate)){
            beginDate = DateTimeUtils.getFormatStrByDateStr(beginDate, DateTimeUtils.DEFAULT_DATE_FORMAT_PATTERN_SHORT);
            endDate = DateTimeUtils.DEFAULT_END_DATE;
        }else if(StringUtils.isEmpty(beginDate) && !StringUtils.isEmpty(endDate)){
            beginDate = DateTimeUtils.DEFAULT_BEGIN_DATE;
            endDate = DateTimeUtils.getFormatStrByDateStr(beginDate, DateTimeUtils.DEFAULT_DATE_FORMAT_PATTERN_SHORT);
        }else if(!StringUtils.isEmpty(beginDate) && !StringUtils.isEmpty(endDate)){
            beginDate = DateTimeUtils.getFormatStrByDateStr(beginDate, DateTimeUtils.DEFAULT_DATE_FORMAT_PATTERN_SHORT);
            endDate = DateTimeUtils.getFormatStrByDateStr(beginDate, DateTimeUtils.DEFAULT_DATE_FORMAT_PATTERN_SHORT);
        }
        return billingTotalAnalysisMapper.totalChargingByDate(beginDate, endDate, orgCode, page);
    }

    @Override
    public int totalChargingCountByDate(QueryAcctChargingTotalDto queryAcctChargingTotalDto) {
        //获取企业组织编码
        String orgCode = queryAcctChargingTotalDto.getOrgCode();
        if(StringUtils.isEmpty(orgCode)) {
            //获取企业组织
            SysOrganization org = getApiService.getOrgByUserId(queryAcctChargingTotalDto.getOperUserId());
            orgCode =  null != org?org.getCode(): AuthConstant.superOrgCode;
        }
        String beginDate = queryAcctChargingTotalDto.getBeginDate();
        String endDate = queryAcctChargingTotalDto.getEndDate();
        if(!StringUtils.isEmpty(beginDate) && StringUtils.isEmpty(endDate)){
            beginDate = DateTimeUtils.getFormatStrByDateStr(beginDate, DateTimeUtils.DEFAULT_DATE_FORMAT_PATTERN_SHORT);
            endDate = DateTimeUtils.DEFAULT_END_DATE;
        }else if(StringUtils.isEmpty(beginDate) && !StringUtils.isEmpty(endDate)){
            beginDate = DateTimeUtils.DEFAULT_BEGIN_DATE;
            endDate = DateTimeUtils.getFormatStrByDateStr(beginDate, DateTimeUtils.DEFAULT_DATE_FORMAT_PATTERN_SHORT);
        }else if(!StringUtils.isEmpty(beginDate) && !StringUtils.isEmpty(endDate)){
            beginDate = DateTimeUtils.getFormatStrByDateStr(beginDate, DateTimeUtils.DEFAULT_DATE_FORMAT_PATTERN_SHORT);
            endDate = DateTimeUtils.getFormatStrByDateStr(beginDate, DateTimeUtils.DEFAULT_DATE_FORMAT_PATTERN_SHORT);
        }
        return billingTotalAnalysisMapper.totalChargingCountByDate(beginDate, endDate, orgCode);
    }

    /**
     * 按月统计费用
     * @param queryAcctChargingTotalDto
     * @param page
     * @return
     */
    @Override
    public List<BillingTotalChargingConsumerVo> totalChargingByMonth(QueryAcctChargingTotalDto queryAcctChargingTotalDto, ResultPage<BillingTotalChargingConsumerVo> page) {
        Integer authLevel = queryAcctChargingTotalDto.getAuthLevel();//操作用户权限等级
        String operUserId = getAuthUtil.getUserIdByAuthLevel(authLevel, queryAcctChargingTotalDto.getOperUserId());//获取用户ID
        String orgCode = getAuthUtil.getOrgCodeByAuthLevel(authLevel, operUserId, queryAcctChargingTotalDto.getOrgCode());//获取企业组织编码
        String beginMonth = queryAcctChargingTotalDto.getBeginMonth();
        String endMonth = queryAcctChargingTotalDto.getEndMonth();
        if(!StringUtils.isEmpty(beginMonth) && StringUtils.isEmpty(endMonth)){
            endMonth = DateTimeUtils.DEFAULT_END_MONTH;
        }else if(StringUtils.isEmpty(beginMonth) && !StringUtils.isEmpty(endMonth)){
            beginMonth = DateTimeUtils.DEFAULT_BEGIN_MONTH;
        }
        return billingTotalAnalysisMapper.totalChargingByMonth(beginMonth, endMonth, orgCode, page);
    }

    @Override
    public int totalChargingCountByMonth(QueryAcctChargingTotalDto queryAcctChargingTotalDto) {
        Integer authLevel = queryAcctChargingTotalDto.getAuthLevel();//操作用户权限等级
        String operUserId = getAuthUtil.getUserIdByAuthLevel(authLevel, queryAcctChargingTotalDto.getOperUserId());//获取用户ID
        String orgCode = getAuthUtil.getOrgCodeByAuthLevel(authLevel, operUserId, queryAcctChargingTotalDto.getOrgCode());//获取企业组织编码
        String beginMonth = queryAcctChargingTotalDto.getBeginMonth();
        String endMonth = queryAcctChargingTotalDto.getEndMonth();
        if(!StringUtils.isEmpty(beginMonth) && StringUtils.isEmpty(endMonth)){
            endMonth = DateTimeUtils.DEFAULT_END_MONTH;
        }else if(StringUtils.isEmpty(beginMonth) && !StringUtils.isEmpty(endMonth)){
            beginMonth = DateTimeUtils.DEFAULT_BEGIN_MONTH;
        }
        return billingTotalAnalysisMapper.totalChargingCountByMonth(beginMonth, endMonth, orgCode);
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


    @Override
    public void procTotalChargingByDate() {
        String logId = idWorker.nextId();
        //统计前一天日期
        Date thisDate = DateTimeUtils.getDateByOffsetDays(new Date(), -1);
        String dateStr = new SimpleDateFormat(DateTimeUtils.DEFAULT_DATE_FORMAT_PATTERN_SHORT).format(thisDate);
        //设置开始时间
        String beginTime = dateStr + " " + DateTimeUtils.DEFAULT_DATE_START_TIME;
        //设置结束时间
        String endTime = dateStr + " " + DateTimeUtils.DEFAULT_DATE_END_TIME;
        SimpleDateFormat sdf = new SimpleDateFormat(DateTimeUtils.DEFAULT_DATE_FORMAT_PATTERN_SHORT);
        try {
    //        billingTotalAnalysisMapper.procTotalChargingByDate(beginTime, endTime);
            logger.info("begin-统计日期:{},logId:{}", dateStr, logId);
            billingTotalAnalysisMapper.procTotalChargingByDate(dateStr);
            logger.info("end-统计日期:{},logId:{}", dateStr, logId);
        }catch(Exception e){
            logger.error("统计每日计费数据，调用存储过程异常,logId:"+logId, e);
        }
    }

    /**
     * 统计每月计费数据
     */
    @Override
    public void procTotalChargingByMonth() {
        String logId = idWorker.nextId();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, -1);
        String yearMonth = format.format(c.getTime());
        String year = yearMonth.split("-")[0];
        String month = yearMonth.split("-")[1];

        String firstDate = DateTimeUtils.getMonthLastDate(year, month, "first");
        String lastDate = DateTimeUtils.getMonthLastDate(year, month, "last");
        try {
            logger.info("begin-统计月份:{},logId:{}", year + "-" + month, logId);
            billingTotalAnalysisMapper.procTotalChargingByMonth(year + "-" + month, firstDate, lastDate);
            logger.info("end-统计月份:{},logId:{}", year + "-" + month, logId);
        }catch(Exception e){
            logger.error("统计每月计费数据，调用存储过程异常,logId:"+logId, e);
        }

    }
}
