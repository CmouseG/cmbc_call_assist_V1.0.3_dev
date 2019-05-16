package com.guiji.ccmanager.service;

import com.guiji.ccmanager.vo.CallRecordListReq;

import java.util.Date;

/**
 * author:liyang
 * Date:2019/3/15 10:49
 * Description:
 */
public interface BatchExportService {

//    List<BigInteger> callrecord(Date startDate, Date endDate,Integer authLevel, String customerId, String orgCode,
//                                CallRecordListReq callRecordListReq, Integer isDesensitization);


    void generateExcelFile(Date finalStart, Date finalEnd, Integer authLevel, String s, Integer orgId,
                           CallRecordListReq callRecordListReq, Integer isDesensitization, String recordId);

    void generateAudioFile(Date finalStart, Date finalEnd, Integer authLevel, String s, Integer orgId,
                           CallRecordListReq callRecordListReq, Integer isDesensitization, String recordId);

    void batchDeleteCallRecord(Date startDate, Date endDate, Integer authLevel, String customerId,
                               CallRecordListReq callRecordListReq, Integer orgId);

    int countTotalNum(Date start, Date end, Integer authLevel, String s, String orgCode,
                      CallRecordListReq callRecordListReq, Integer orgId);
}
