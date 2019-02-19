package com.guiji.billing.dao.mapper.ext;

import com.guiji.billing.entity.BillNotifyLogBean;
import org.springframework.stereotype.Repository;

@Repository
public interface BillingChargingMapper {

    //新增计费项
    int addBillNotifyLog(BillNotifyLogBean billNotifyLog);
}
