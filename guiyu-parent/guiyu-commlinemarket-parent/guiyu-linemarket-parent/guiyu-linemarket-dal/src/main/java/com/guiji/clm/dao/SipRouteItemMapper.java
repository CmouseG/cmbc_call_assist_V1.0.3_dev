package com.guiji.clm.dao;

import com.guiji.clm.dao.entity.SipRouteItem;
import com.guiji.clm.dao.entity.SipRouteItemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SipRouteItemMapper {
    int countByExample(SipRouteItemExample example);

    int deleteByExample(SipRouteItemExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SipRouteItem record);

    int insertSelective(SipRouteItem record);

    List<SipRouteItem> selectByExample(SipRouteItemExample example);

    SipRouteItem selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SipRouteItem record, @Param("example") SipRouteItemExample example);

    int updateByExample(@Param("record") SipRouteItem record, @Param("example") SipRouteItemExample example);

    int updateByPrimaryKeySelective(SipRouteItem record);

    int updateByPrimaryKey(SipRouteItem record);
}