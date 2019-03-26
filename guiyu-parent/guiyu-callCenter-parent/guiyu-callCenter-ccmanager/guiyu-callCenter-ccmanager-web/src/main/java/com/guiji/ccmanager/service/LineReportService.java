package com.guiji.ccmanager.service;


import com.guiji.callcenter.dao.entity.ReportLineCode;
import com.guiji.callcenter.dao.entityext.LineMonitorRreport;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface LineReportService {



    void statisticsReportLineCode() throws ParseException;

    List<LineMonitorRreport> getLineMonitorReport(Integer lineId, Long userId, Date startTime, String orgCode, Integer authLevel);

    Map getLineHangupDetail(Integer lineId, Date startTime, Date enTime, String orgCode,Integer userId, Integer authLevel);
}
