package com.guiji.user.dao;

import com.guiji.user.dao.entity.SysBusiness;
import com.guiji.user.dao.entity.SysBusinessExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysBusinessMapper {
    long countByExample(SysBusinessExample example);

    int deleteByExample(SysBusinessExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SysBusiness record);

    int insertSelective(SysBusiness record);

    List<SysBusiness> selectByExample(SysBusinessExample example);

    SysBusiness selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SysBusiness record, @Param("example") SysBusinessExample example);

    int updateByExample(@Param("record") SysBusiness record, @Param("example") SysBusinessExample example);

    int updateByPrimaryKeySelective(SysBusiness record);

    int updateByPrimaryKey(SysBusiness record);
}