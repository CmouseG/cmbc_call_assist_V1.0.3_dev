package com.guiji.ccmanager.service;

import com.guiji.ccmanager.vo.CallRecordListReq;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * author:liyang
 * Date:2019/3/15 10:49
 * Description:
 */
public interface BatchExportService {

    List<BigInteger> callrecord(Date startDate, Date endDate, Boolean isSuperAdmin, String customerId, String orgCode,
                                CallRecordListReq callRecordListReq, Integer isDesensitization);


}
