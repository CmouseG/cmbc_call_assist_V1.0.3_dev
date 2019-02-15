package com.guiji.billing.service.impl;

import com.guiji.auth.api.IAuth;
import com.guiji.billing.dao.mapper.ext.BillingSysRechargeMapper;
import com.guiji.billing.dto.QueryRechargeDto;
import com.guiji.billing.enums.ChargingTypeEnum;
import com.guiji.billing.service.BillingSysRechargeService;
import com.guiji.billing.sys.ResultPage;
import com.guiji.billing.utils.DateTimeUtils;
import com.guiji.billing.utils.ResHandler;
import com.guiji.billing.vo.SysRechargeTotalVo;
import com.guiji.user.dao.entity.SysOrganization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 企业查询充值记录
 */
@Service
public class BillingSysRechargeServiceImpl implements BillingSysRechargeService {

    @Autowired
    private BillingSysRechargeMapper billingSysRechargeMapper;

    @Autowired
    private IAuth iAuth;

    /**
     * 查询公司充值记录
     * @param queryRechargeDto
     * @param page
     * @return
     */
    @Override
    public List<SysRechargeTotalVo> queryCompanyRechargeTotal(QueryRechargeDto queryRechargeDto, ResultPage<SysRechargeTotalVo> page) {
        //获取用户ID
        String userId = null != queryRechargeDto.getUserId()?queryRechargeDto.getUserId():"1";
        //获取企业组织
        SysOrganization org = ResHandler.getResObj(iAuth.getOrgByUserId(Long.valueOf(userId)));
        //获取企业组织编码
        String orgCode = null != org?org.getCode():"1";
        Date beginDate = queryRechargeDto.getBeginDate();
        Date endDate = queryRechargeDto.getEndDate();
        if(null != beginDate && null == endDate){
            endDate = DateTimeUtils.getDateByString(DateTimeUtils.DEFAULT_END_TIME, DateTimeUtils.DEFAULT_DATE_FORMAT_PATTERN_FULL);
        }else if(null == beginDate && null != endDate){
            beginDate = DateTimeUtils.getDateByString(DateTimeUtils.DEFAULT_BEGIN_TIME, DateTimeUtils.DEFAULT_DATE_FORMAT_PATTERN_FULL);
        }
        return billingSysRechargeMapper.queryCompanyRechargeTotal(queryRechargeDto.getCompanyName(), orgCode,
                ChargingTypeEnum.RECHARGE.getType(), queryRechargeDto.getFeeMode(), beginDate, endDate, page);
    }

    /**
     * 查询公司充值记录数量
     * @param queryRechargeDto
     * @return
     */
    @Override
    public int queryCompanyRechargeCount(QueryRechargeDto queryRechargeDto) {
        //获取用户ID
        String userId = null != queryRechargeDto.getUserId()?queryRechargeDto.getUserId():"1";
        //获取企业组织
        SysOrganization org = ResHandler.getResObj(iAuth.getOrgByUserId(Long.valueOf(userId)));
        //获取企业组织编码
        String orgCode = null != org?org.getCode():"1";
        Date beginDate = queryRechargeDto.getBeginDate();
        Date endDate = queryRechargeDto.getEndDate();
        if(null != beginDate && null == endDate){
            endDate = DateTimeUtils.getDateByString(DateTimeUtils.DEFAULT_END_TIME, DateTimeUtils.DEFAULT_DATE_FORMAT_PATTERN_FULL);
        }else if(null == beginDate && null != endDate){
            beginDate = DateTimeUtils.getDateByString(DateTimeUtils.DEFAULT_BEGIN_TIME, DateTimeUtils.DEFAULT_DATE_FORMAT_PATTERN_FULL);
        }
        return billingSysRechargeMapper.queryCompanyRechargeCount(queryRechargeDto.getCompanyName(), orgCode,
                ChargingTypeEnum.RECHARGE.getType(), queryRechargeDto.getFeeMode(), beginDate, endDate);
    }
}
