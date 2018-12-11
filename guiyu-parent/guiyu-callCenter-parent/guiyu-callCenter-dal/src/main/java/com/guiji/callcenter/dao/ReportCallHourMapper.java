package com.guiji.callcenter.dao;

import com.guiji.callcenter.dao.entity.ReportCallHour;
import com.guiji.callcenter.dao.entity.ReportCallHourExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ReportCallHourMapper {
    int countByExample(ReportCallHourExample example);

    int deleteByExample(ReportCallHourExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ReportCallHour record);

    int insertSelective(ReportCallHour record);

    List<ReportCallHour> selectByExample(ReportCallHourExample example);

    ReportCallHour selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ReportCallHour record, @Param("example") ReportCallHourExample example);

    int updateByExample(@Param("record") ReportCallHour record, @Param("example") ReportCallHourExample example);

    int updateByPrimaryKeySelective(ReportCallHour record);

    int updateByPrimaryKey(ReportCallHour record);
}