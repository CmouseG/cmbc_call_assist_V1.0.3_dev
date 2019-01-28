package com.guiji.dispatch.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.guiji.dispatch.dao.entity.BlackList;
import com.guiji.dispatch.dao.entity.BlackListExample;

public interface BlackListMapper {
    int countByExample(BlackListExample example);

    int deleteByExample(BlackListExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BlackList record);

    int insertSelective(BlackList record);

    List<BlackList> selectByExample(BlackListExample example);

    BlackList selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BlackList record, @Param("example") BlackListExample example);

    int updateByExample(@Param("record") BlackList record, @Param("example") BlackListExample example);

    int updateByPrimaryKeySelective(BlackList record);

    int updateByPrimaryKey(BlackList record);
    
	int BatchinsertBlackList(List<BlackList> list);
	
}