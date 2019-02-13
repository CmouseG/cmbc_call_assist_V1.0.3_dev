package com.guiji.clm.dao;

import com.guiji.clm.dao.entity.SipRouteRule;
import com.guiji.clm.dao.entity.SipRouteRuleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SipRouteRuleMapper {
    int countByExample(SipRouteRuleExample example);

    int deleteByExample(SipRouteRuleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SipRouteRule record);

    int insertSelective(SipRouteRule record);

    List<SipRouteRule> selectByExample(SipRouteRuleExample example);

    SipRouteRule selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SipRouteRule record, @Param("example") SipRouteRuleExample example);

    int updateByExample(@Param("record") SipRouteRule record, @Param("example") SipRouteRuleExample example);

    int updateByPrimaryKeySelective(SipRouteRule record);

    int updateByPrimaryKey(SipRouteRule record);
}