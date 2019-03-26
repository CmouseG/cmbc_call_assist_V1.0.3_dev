package com.guiji.callcenter.dao;

import com.guiji.callcenter.dao.entity.ReportCallDay;
import com.guiji.callcenter.dao.entity.ReportCallDayExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ReportCallDayMapper {
    int countByExample(ReportCallDayExample example);

    int deleteByExample(ReportCallDayExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ReportCallDay record);

    int insertSelective(ReportCallDay record);

    List<ReportCallDay> selectByExample(ReportCallDayExample example);

    ReportCallDay selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ReportCallDay record, @Param("example") ReportCallDayExample example);

    int updateByExample(@Param("record") ReportCallDay record, @Param("example") ReportCallDayExample example);

    int updateByPrimaryKeySelective(ReportCallDay record);

    int updateByPrimaryKey(ReportCallDay record);

    List<String> getAllLabelFromDate(@Param("orgCode") String orgCode,@Param("callDate") String callDate,
                                     @Param("userId") Long userId,@Param("authLevel") Integer authLevel);
    List<String> getAllLabelFromToday(@Param("orgCode") String orgCode, @Param("userId") Long userId,@Param("authLevel") Integer authLevel);
}