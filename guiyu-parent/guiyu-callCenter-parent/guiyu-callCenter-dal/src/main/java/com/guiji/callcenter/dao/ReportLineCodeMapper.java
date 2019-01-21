package com.guiji.callcenter.dao;

import com.guiji.callcenter.dao.entity.ReportLineCode;
import com.guiji.callcenter.dao.entity.ReportLineCodeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ReportLineCodeMapper {
    int countByExample(ReportLineCodeExample example);

    int deleteByExample(ReportLineCodeExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ReportLineCode record);

    int insertSelective(ReportLineCode record);

    List<ReportLineCode> selectByExample(ReportLineCodeExample example);

    ReportLineCode selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ReportLineCode record, @Param("example") ReportLineCodeExample example);

    int updateByExample(@Param("record") ReportLineCode record, @Param("example") ReportLineCodeExample example);

    int updateByPrimaryKeySelective(ReportLineCode record);

    int updateByPrimaryKey(ReportLineCode record);
}