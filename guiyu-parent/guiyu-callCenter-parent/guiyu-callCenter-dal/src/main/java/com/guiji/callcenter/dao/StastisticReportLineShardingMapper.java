package com.guiji.callcenter.dao;

import com.guiji.callcenter.dao.entity.ReportLineCode;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface StastisticReportLineShardingMapper {

    List<ReportLineCode> selectLineHangupCodeReport(@Param("startTime") Date startTime, @Param("enTime") Date enTime, @Param("orgIdList") List<Integer> orgIdList);

}
