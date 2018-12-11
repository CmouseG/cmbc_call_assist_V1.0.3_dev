package com.guiji.callcenter.dao;

import com.guiji.callcenter.dao.entity.ReportCallToday;
import com.guiji.callcenter.dao.entity.ReportCallTodayExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ReportCallTodayMapper {
    int countByExample(ReportCallTodayExample example);

    int deleteByExample(ReportCallTodayExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ReportCallToday record);

    int insertSelective(ReportCallToday record);

    List<ReportCallToday> selectByExample(ReportCallTodayExample example);

    ReportCallToday selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ReportCallToday record, @Param("example") ReportCallTodayExample example);

    int updateByExample(@Param("record") ReportCallToday record, @Param("example") ReportCallTodayExample example);

    int updateByPrimaryKeySelective(ReportCallToday record);

    int updateByPrimaryKey(ReportCallToday record);
}