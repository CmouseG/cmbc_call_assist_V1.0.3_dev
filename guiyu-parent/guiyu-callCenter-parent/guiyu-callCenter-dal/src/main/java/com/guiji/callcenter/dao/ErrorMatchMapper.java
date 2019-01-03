package com.guiji.callcenter.dao;

import com.guiji.callcenter.dao.entity.ErrorMatch;
import com.guiji.callcenter.dao.entity.ErrorMatchExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ErrorMatchMapper {
    int countByExample(ErrorMatchExample example);

    int deleteByExample(ErrorMatchExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ErrorMatch record);

    int insertSelective(ErrorMatch record);

    List<ErrorMatch> selectByExample(ErrorMatchExample example);

    ErrorMatch selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ErrorMatch record, @Param("example") ErrorMatchExample example);

    int updateByExample(@Param("record") ErrorMatch record, @Param("example") ErrorMatchExample example);

    int updateByPrimaryKeySelective(ErrorMatch record);

    int updateByPrimaryKey(ErrorMatch record);

    List<String> selectDistinctErrorName();
}