package com.guiji.botsentence.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.guiji.botsentence.dao.entity.AccountGroup;
import com.guiji.botsentence.dao.entity.AccountGroupExample;

public interface AccountGroupMapper {
    int countByExample(AccountGroupExample example);

    int deleteByExample(AccountGroupExample example);

    int deleteByPrimaryKey(String accountGroupNo);

    int insert(AccountGroup record);

    int insertSelective(AccountGroup record);

    List<AccountGroup> selectByExample(AccountGroupExample example);

    AccountGroup selectByPrimaryKey(String accountGroupNo);

    int updateByExampleSelective(@Param("record") AccountGroup record, @Param("example") AccountGroupExample example);

    int updateByExample(@Param("record") AccountGroup record, @Param("example") AccountGroupExample example);

    int updateByPrimaryKeySelective(AccountGroup record);

    int updateByPrimaryKey(AccountGroup record);
}