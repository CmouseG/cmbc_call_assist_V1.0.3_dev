package com.guiji.robot.dao;

import com.guiji.robot.dao.entity.UserAiCfgInfo;
import com.guiji.robot.dao.entity.UserAiCfgInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserAiCfgInfoMapper {
    int countByExample(UserAiCfgInfoExample example);

    int deleteByExample(UserAiCfgInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserAiCfgInfo record);

    int insertSelective(UserAiCfgInfo record);

    List<UserAiCfgInfo> selectByExample(UserAiCfgInfoExample example);

    UserAiCfgInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserAiCfgInfo record, @Param("example") UserAiCfgInfoExample example);

    int updateByExample(@Param("record") UserAiCfgInfo record, @Param("example") UserAiCfgInfoExample example);

    int updateByPrimaryKeySelective(UserAiCfgInfo record);

    int updateByPrimaryKey(UserAiCfgInfo record);
}