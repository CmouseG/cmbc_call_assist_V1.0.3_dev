package com.guiji.robot.dao;

import com.guiji.robot.dao.entity.UserAiCfgHisInfo;
import com.guiji.robot.dao.entity.UserAiCfgHisInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserAiCfgHisInfoMapper {
    int countByExample(UserAiCfgHisInfoExample example);

    int deleteByExample(UserAiCfgHisInfoExample example);

    int deleteByPrimaryKey(String id);

    int insert(UserAiCfgHisInfo record);

    int insertSelective(UserAiCfgHisInfo record);

    List<UserAiCfgHisInfo> selectByExample(UserAiCfgHisInfoExample example);

    UserAiCfgHisInfo selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") UserAiCfgHisInfo record, @Param("example") UserAiCfgHisInfoExample example);

    int updateByExample(@Param("record") UserAiCfgHisInfo record, @Param("example") UserAiCfgHisInfoExample example);

    int updateByPrimaryKeySelective(UserAiCfgHisInfo record);

    int updateByPrimaryKey(UserAiCfgHisInfo record);
}