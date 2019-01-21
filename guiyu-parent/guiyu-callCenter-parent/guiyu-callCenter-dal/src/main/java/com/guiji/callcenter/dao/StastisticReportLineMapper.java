package com.guiji.callcenter.dao;

import com.guiji.callcenter.dao.entity.ReportLineCode;
import com.guiji.callcenter.dao.entity.ReportLineStatus;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface StastisticReportLineMapper {

    List<ReportLineCode> selectLineHangupCodeReport();

    List<Map> getLineMonitorReportByLineId(@Param("lineId") Integer lineId, @Param("startTime")  Date startTime);

    List<Map> getLineMonitorReportByUserId(@Param("userId") Long userId,@Param("startTime")  Date startTime);

    List<Map> getLineHangupCodeOverView(@Param("lineId") Integer lineId,@Param("startTime") Date startTime,@Param("enTime") Date enTime);

    List<Map> getLineHangupCodeErrorSum(@Param("lineId") Integer lineId,@Param("startTime") Date startTime,@Param("enTime") Date enTime);

    List<Map> getLineHangupCodeErrorNums(@Param("lineId") Integer lineId,@Param("startTime") Date startTime,@Param("enTime") Date enTime);
    List<Map> getLineHangupCodeErrorNumsCancel(@Param("lineId") Integer lineId,@Param("startTime") Date startTime,@Param("enTime") Date enTime);
}
