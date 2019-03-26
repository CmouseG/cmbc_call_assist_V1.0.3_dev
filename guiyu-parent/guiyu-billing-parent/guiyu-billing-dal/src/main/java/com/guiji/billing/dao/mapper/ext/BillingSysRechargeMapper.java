package com.guiji.billing.dao.mapper.ext;

import com.guiji.billing.entity.BillingAcctChargingRecord;
import com.guiji.billing.entity.BillingAcctChargingTerm;
import com.guiji.billing.entity.BillingUserAcctBean;
import com.guiji.billing.entity.BillingUserAcctSetBean;
import com.guiji.billing.sys.ResultPage;
import com.guiji.billing.vo.SysRechargeTotalVo;
import com.guiji.billing.vo.UserRechargeTotalVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
public interface BillingSysRechargeMapper {

    //查询充值记录
    List<SysRechargeTotalVo> queryCompanyRechargeTotal(@Param("companyName") String companyName, @Param("orgCode") String orgCode,
                                                   @Param("type") Integer type, @Param("feeMode") Integer feeMode,
                                                   @Param("beginDate") Date beginDate, @Param("endDate") Date endDate,
                                                   @Param("authLevel") Integer authLevel,
                                                   @Param("page") ResultPage<SysRechargeTotalVo> page);

    //查询充值记录数量
    int queryCompanyRechargeCount(@Param("companyName") String companyName, @Param("orgCode") String orgCode,
                               @Param("type") Integer type, @Param("feeMode") Integer feeMode,
                               @Param("beginDate") Date beginDate, @Param("endDate") Date endDate,
                                  @Param("authLevel") Integer authLevel);

}
