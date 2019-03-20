package com.guiji.callcenter.dao;

import com.guiji.callcenter.dao.entity.LineInfo;
import com.guiji.callcenter.dao.entity.LineInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LineInfoMapper {
    int countByExample(LineInfoExample example);

    int deleteByExample(LineInfoExample example);

    int deleteByPrimaryKey(Integer lineId);

    int insert(LineInfo record);

    int insertSelective(LineInfo record);

    List<LineInfo> selectByExample(LineInfoExample example);

    LineInfo selectByPrimaryKey(Integer lineId);

    int updateByExampleSelective(@Param("record") LineInfo record, @Param("example") LineInfoExample example);

    int updateByExample(@Param("record") LineInfo record, @Param("example") LineInfoExample example);

    int updateByPrimaryKeySelective(LineInfo record);

    int updateByPrimaryKey(LineInfo record);
}