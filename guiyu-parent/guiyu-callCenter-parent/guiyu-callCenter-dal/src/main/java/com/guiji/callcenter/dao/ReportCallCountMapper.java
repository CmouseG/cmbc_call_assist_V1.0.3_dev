package com.guiji.callcenter.dao;

import com.guiji.callcenter.dao.entity.ReportCallCount;
import com.guiji.callcenter.dao.entity.ReportCallCountExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ReportCallCountMapper {
    int countByExample(ReportCallCountExample example);

    int deleteByExample(ReportCallCountExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ReportCallCount record);

    int insertSelective(ReportCallCount record);

    List<ReportCallCount> selectByExample(ReportCallCountExample example);

    ReportCallCount selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ReportCallCount record, @Param("example") ReportCallCountExample example);

    int updateByExample(@Param("record") ReportCallCount record, @Param("example") ReportCallCountExample example);

    int updateByPrimaryKeySelective(ReportCallCount record);

    int updateByPrimaryKey(ReportCallCount record);
}