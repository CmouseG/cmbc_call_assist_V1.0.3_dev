package com.guiji.callcenter.dao;

import com.guiji.callcenter.dao.entity.CallLineDayReport;
import com.guiji.callcenter.dao.entity.CallLineDayReportExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CallLineDayReportMapper {
    int countByExample(CallLineDayReportExample example);

    int deleteByExample(CallLineDayReportExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CallLineDayReport record);

    int insertSelective(CallLineDayReport record);

    List<CallLineDayReport> selectByExample(CallLineDayReportExample example);

    CallLineDayReport selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CallLineDayReport record, @Param("example") CallLineDayReportExample example);

    int updateByExample(@Param("record") CallLineDayReport record, @Param("example") CallLineDayReportExample example);

    int updateByPrimaryKeySelective(CallLineDayReport record);

    int updateByPrimaryKey(CallLineDayReport record);
}