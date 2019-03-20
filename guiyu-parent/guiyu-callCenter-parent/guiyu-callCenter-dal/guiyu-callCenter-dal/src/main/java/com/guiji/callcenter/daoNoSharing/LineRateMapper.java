package com.guiji.callcenter.daoNoSharing;

import com.guiji.callcenter.dao.entity.CallLineDayReport;
import com.guiji.ccmanager.entity.LineRateResponse;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface LineRateMapper {

    List<LineRateResponse> getLineRateFromResult(@Param("lineId") Integer lineId, @Param("startTime") Date startTime,
                                                 @Param("endTime") Date endTime);

    List<LineRateResponse> getLineRateAllFromResult(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    List<LineRateResponse> getLineRateFromDayReport(@Param("lineId") Integer lineId, @Param("startTime") Date startTime,
                                                    @Param("endTime") Date endTime, @Param("middleTime") Date middleTime);

    List<LineRateResponse> getLineRateAllFromDayReport(@Param("startTime") Date startTime, @Param("endTime") Date endTime,
                                                       @Param("middleTime") Date middleTime);

    List<CallLineDayReport> countDayReport(@Param("endTime") Date endTime);

    void insertCallLineDayReportBatch(List<CallLineDayReport> list);
}
