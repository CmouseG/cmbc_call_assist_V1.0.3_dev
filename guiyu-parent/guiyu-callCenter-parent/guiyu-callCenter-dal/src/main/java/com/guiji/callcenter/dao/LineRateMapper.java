package com.guiji.callcenter.dao;

import com.guiji.ccmanager.entity.LineRateResponse;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface LineRateMapper {

    List<LineRateResponse> getLineRate(@Param("lineId") Integer lineId, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    List<LineRateResponse> getLineRateAll(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

}
