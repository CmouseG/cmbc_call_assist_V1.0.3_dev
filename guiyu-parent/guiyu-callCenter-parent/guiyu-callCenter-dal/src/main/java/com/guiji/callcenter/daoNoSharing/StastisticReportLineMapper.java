package com.guiji.callcenter.daoNoSharing;

import com.guiji.callcenter.dao.entity.ReportLineCode;
import com.guiji.callcenter.dao.entity.ReportLineStatus;
import com.guiji.callcenter.dao.entityext.LineMonitorRreport;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface StastisticReportLineMapper {

//    List<ReportLineCode> selectLineHangupCodeReport(@Param("startTime") Date startTime, @Param("enTime") Date enTime);

    void insertReportLineCodeBatch(List<ReportLineCode> list);

    List<LineMonitorRreport> getLineMonitorReportByLineId(@Param("lineId") Integer lineId, @Param("startTime") Date startTime,
                                                          @Param("orgCode") String orgCode, @Param("authLevel") Integer authLevel,@Param("userId") Long userId);

    List<LineMonitorRreport> getLineMonitorReportByUserId(@Param("orgCode") String orgCode, @Param("authLevel") Integer authLevel,@Param("userId") Long userId,
                                                          @Param("startTime") Date startTime);

    List<Map> getLineHangupCodeOverView(@Param("lineId") Integer lineId, @Param("startTime") Date startTime, @Param("enTime") Date enTime,
                                        @Param("orgCode") String orgCode, @Param("userId") Integer userId, @Param("authLevel") Integer authLevel);

    List<Map> getLineHangupCodeErrorSum(@Param("lineId") Integer lineId, @Param("startTime") Date startTime, @Param("enTime") Date enTime,
                                        @Param("orgCode") String orgCode, @Param("userId") Integer userId, @Param("authLevel") Integer authLevel);

    List<Map> getLineHangupCodeErrorNums(@Param("lineId") Integer lineId, @Param("startTime") Date startTime, @Param("enTime") Date enTime,
                                         @Param("orgCode") String orgCode, @Param("userId") Integer userId, @Param("authLevel") Integer authLevel);
    List<Map> getLineHangupCodeErrorNumsCancel(@Param("lineId") Integer lineId, @Param("startTime") Date startTime, @Param("enTime") Date enTime,
                                               @Param("orgCode") String orgCode, @Param("userId") Integer userId, @Param("authLevel") Integer authLevel);

    List<ReportLineStatus> selectReportLineStatusFromCode(@Param("createTime") Date createTime);

    void insertReportLineStatusBatch(List<ReportLineStatus> list);
    void deleteReportLineCode(@Param("createTime") Date createTime);
    void deleteReportLineStatus(@Param("createTime") Date createTime);

    void deleteReportLineCodeDaysAgo(@Param("days") int days);

    void deleteReportLineStatusDaysAgo(@Param("days") int days);

    void deleteCallLineResultDaysAgo(@Param("days") int days);
}
