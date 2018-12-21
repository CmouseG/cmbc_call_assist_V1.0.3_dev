package com.guiji.dispatch.dao;

import com.guiji.dispatch.dao.entity.UserSmsConfig;
import com.guiji.dispatch.dao.entity.UserSmsConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserSmsConfigMapper {
    int countByExample(UserSmsConfigExample example);

    int deleteByExample(UserSmsConfigExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserSmsConfig record);

    int insertSelective(UserSmsConfig record);

    List<UserSmsConfig> selectByExampleWithBLOBs(UserSmsConfigExample example);

    List<UserSmsConfig> selectByExample(UserSmsConfigExample example);

    UserSmsConfig selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserSmsConfig record, @Param("example") UserSmsConfigExample example);

    int updateByExampleWithBLOBs(@Param("record") UserSmsConfig record, @Param("example") UserSmsConfigExample example);

    int updateByExample(@Param("record") UserSmsConfig record, @Param("example") UserSmsConfigExample example);

    int updateByPrimaryKeySelective(UserSmsConfig record);

    int updateByPrimaryKeyWithBLOBs(UserSmsConfig record);

    int updateByPrimaryKey(UserSmsConfig record);
}