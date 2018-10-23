package com.guiji.dispatch.dao;

import com.guiji.dispatch.entity.Plan;

public interface PlanMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Plan record);

    int insertSelective(Plan record);

    Plan selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Plan record);

    int updateByPrimaryKey(Plan record);
}