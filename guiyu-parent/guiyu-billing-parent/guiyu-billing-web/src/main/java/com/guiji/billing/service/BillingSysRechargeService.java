package com.guiji.billing.service;

import com.guiji.billing.dto.QueryRechargeDto;
import com.guiji.billing.sys.ResultPage;
import com.guiji.billing.vo.SysRechargeTotalVo;

import java.util.List;

public interface BillingSysRechargeService {

    //查询充值记录
    List<SysRechargeTotalVo> queryCompanyRechargeTotal(QueryRechargeDto queryRechargeDto, ResultPage<SysRechargeTotalVo> page);

    //查询充值记录数量
    int queryCompanyRechargeCount(QueryRechargeDto queryRechargeDto);
}
