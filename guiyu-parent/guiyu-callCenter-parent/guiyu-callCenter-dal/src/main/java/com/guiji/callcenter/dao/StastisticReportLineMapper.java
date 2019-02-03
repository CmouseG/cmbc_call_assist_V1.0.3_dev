package com.guiji.callcenter.dao;

import com.guiji.callcenter.dao.entity.ReportCallDay;
import com.guiji.callcenter.dao.entity.ReportLineCode;
import com.guiji.callcenter.dao.entity.ReportLineStatus;
import com.guiji.callcenter.dao.entityext.LineMonitorRreport;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface StastisticReportLineMapper {

    List<ReportLineCode> selectLineHangupCodeReport(@Param("startTime") Date startTime,@Param("enTime") Date enTime);

    void insertReportLineCodeBatch(List<ReportLineCode> list);

    List<LineMonitorRreport> getLineMonitorReportByLineId(@Param("lineId") Integer lineId, @Param("startTime")  Date startTime);

    List<LineMonitorRreport> getLineMonitorReportByUserId(@Param("lineIdList") List lineIdList,@Param("startTime")  Date startTime);

    List<Map> getLineHangupCodeOverView(@Param("lineId") Integer lineId,@Param("startTime") Date startTime,@Param("enTime") Date enTime);

    List<Map> getLineHangupCodeErrorSum(@Param("lineId") Integer lineId,@Param("startTime") Date startTime,@Param("enTime") Date enTime);

    List<Map> getLineHangupCodeErrorNums(@Param("lineId") Integer lineId,@Param("startTime") Date startTime,@Param("enTime") Date enTime);
    List<Map> getLineHangupCodeErrorNumsCancel(@Param("lineId") Integer lineId,@Param("startTime") Date startTime,@Param("enTime") Date enTime);

    List<ReportLineStatus> selectReportLineStatusFromCode(@Param("createTime") Date createTime);

    void insertReportLineStatusBatch(List<ReportLineStatus> list);
    void deleteReportLineCode(@Param("createTime") Date createTime);
    void deleteReportLineStatus(@Param("createTime") Date createTime);

    void deleteReportLineCodeDaysAgo(@Param("days")  int days);

    void deleteReportLineStatusDaysAgo(@Param("days")  int days);

    void deleteCallLineResultDaysAgo(@Param("days")  int days);
}
