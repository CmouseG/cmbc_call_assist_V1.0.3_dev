package com.guiji.callcenter.dao;

import com.guiji.callcenter.dao.entity.LineCount;
import com.guiji.callcenter.dao.entity.LineCountExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LineCountMapper {
    int countByExample(LineCountExample example);

    int deleteByExample(LineCountExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LineCount record);

    int insertSelective(LineCount record);

    List<LineCount> selectByExample(LineCountExample example);

    LineCount selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LineCount record, @Param("example") LineCountExample example);

    int updateByExample(@Param("record") LineCount record, @Param("example") LineCountExample example);

    int updateByPrimaryKeySelective(LineCount record);

    int updateByPrimaryKey(LineCount record);
}