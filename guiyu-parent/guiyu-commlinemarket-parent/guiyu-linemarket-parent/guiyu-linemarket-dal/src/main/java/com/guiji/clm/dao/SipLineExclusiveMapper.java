package com.guiji.clm.dao;

import com.guiji.clm.dao.entity.SipLineExclusive;
import com.guiji.clm.dao.entity.SipLineExclusiveExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SipLineExclusiveMapper {
    int countByExample(SipLineExclusiveExample example);

    int deleteByExample(SipLineExclusiveExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SipLineExclusive record);

    int insertSelective(SipLineExclusive record);

    List<SipLineExclusive> selectByExample(SipLineExclusiveExample example);

    SipLineExclusive selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SipLineExclusive record, @Param("example") SipLineExclusiveExample example);

    int updateByExample(@Param("record") SipLineExclusive record, @Param("example") SipLineExclusiveExample example);

    int updateByPrimaryKeySelective(SipLineExclusive record);

    int updateByPrimaryKey(SipLineExclusive record);
}