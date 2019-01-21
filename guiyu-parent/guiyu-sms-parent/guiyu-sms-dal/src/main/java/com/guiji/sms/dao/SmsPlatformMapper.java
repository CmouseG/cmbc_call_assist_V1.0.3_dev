package com.guiji.sms.dao;

import com.guiji.sms.dao.entity.SmsPlatform;
import com.guiji.sms.dao.entity.SmsPlatformExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SmsPlatformMapper {
    long countByExample(SmsPlatformExample example);

    int deleteByExample(SmsPlatformExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SmsPlatform record);

    int insertSelective(SmsPlatform record);

    List<SmsPlatform> selectByExample(SmsPlatformExample example);

    SmsPlatform selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SmsPlatform record, @Param("example") SmsPlatformExample example);

    int updateByExample(@Param("record") SmsPlatform record, @Param("example") SmsPlatformExample example);

    int updateByPrimaryKeySelective(SmsPlatform record);

    int updateByPrimaryKey(SmsPlatform record);
}