package com.guiji.callcenter.dao;

import com.guiji.callcenter.dao.entity.ReportLineStatus;
import com.guiji.callcenter.dao.entity.ReportLineStatusExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ReportLineStatusMapper {
    int countByExample(ReportLineStatusExample example);

    int deleteByExample(ReportLineStatusExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ReportLineStatus record);

    int insertSelective(ReportLineStatus record);

    List<ReportLineStatus> selectByExample(ReportLineStatusExample example);

    ReportLineStatus selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ReportLineStatus record, @Param("example") ReportLineStatusExample example);

    int updateByExample(@Param("record") ReportLineStatus record, @Param("example") ReportLineStatusExample example);

    int updateByPrimaryKeySelective(ReportLineStatus record);

    int updateByPrimaryKey(ReportLineStatus record);
}