package com.guiji.clm.dao;

import com.guiji.clm.dao.entity.SipLineShare;
import com.guiji.clm.dao.entity.SipLineShareExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SipLineShareMapper {
    int countByExample(SipLineShareExample example);

    int deleteByExample(SipLineShareExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SipLineShare record);

    int insertSelective(SipLineShare record);

    List<SipLineShare> selectByExample(SipLineShareExample example);

    SipLineShare selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SipLineShare record, @Param("example") SipLineShareExample example);

    int updateByExample(@Param("record") SipLineShare record, @Param("example") SipLineShareExample example);

    int updateByPrimaryKeySelective(SipLineShare record);

    int updateByPrimaryKey(SipLineShare record);
}