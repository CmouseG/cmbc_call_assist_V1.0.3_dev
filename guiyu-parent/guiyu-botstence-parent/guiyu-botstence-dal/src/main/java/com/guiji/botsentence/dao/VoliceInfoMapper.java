package com.guiji.botsentence.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.guiji.botsentence.dao.entity.VoliceInfo;
import com.guiji.botsentence.dao.entity.VoliceInfoExample;

public interface VoliceInfoMapper {
    int countByExample(VoliceInfoExample example);

    int deleteByExample(VoliceInfoExample example);

    int deleteByPrimaryKey(Long voliceId);

    int insert(VoliceInfo record);

    int insertSelective(VoliceInfo record);

    List<VoliceInfo> selectByExample(VoliceInfoExample example);

    VoliceInfo selectByPrimaryKey(Long voliceId);

    int updateByExampleSelective(@Param("record") VoliceInfo record, @Param("example") VoliceInfoExample example);

    int updateByExample(@Param("record") VoliceInfo record, @Param("example") VoliceInfoExample example);

    int updateByPrimaryKeySelective(VoliceInfo record);

    int updateByPrimaryKey(VoliceInfo record);
}