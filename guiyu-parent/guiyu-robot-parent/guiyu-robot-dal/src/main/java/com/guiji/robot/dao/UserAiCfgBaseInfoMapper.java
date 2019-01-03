package com.guiji.robot.dao;

import com.guiji.robot.dao.entity.UserAiCfgBaseInfo;
import com.guiji.robot.dao.entity.UserAiCfgBaseInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserAiCfgBaseInfoMapper {
    int countByExample(UserAiCfgBaseInfoExample example);

    int deleteByExample(UserAiCfgBaseInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserAiCfgBaseInfo record);

    int insertSelective(UserAiCfgBaseInfo record);

    List<UserAiCfgBaseInfo> selectByExample(UserAiCfgBaseInfoExample example);

    UserAiCfgBaseInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserAiCfgBaseInfo record, @Param("example") UserAiCfgBaseInfoExample example);

    int updateByExample(@Param("record") UserAiCfgBaseInfo record, @Param("example") UserAiCfgBaseInfoExample example);

    int updateByPrimaryKeySelective(UserAiCfgBaseInfo record);

    int updateByPrimaryKey(UserAiCfgBaseInfo record);
}