package com.guiji.clm.dao;

import com.guiji.clm.dao.entity.SipLineBaseInfo;
import com.guiji.clm.dao.entity.SipLineBaseInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SipLineBaseInfoMapper {
    int countByExample(SipLineBaseInfoExample example);

    int deleteByExample(SipLineBaseInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SipLineBaseInfo record);

    int insertSelective(SipLineBaseInfo record);

    List<SipLineBaseInfo> selectByExample(SipLineBaseInfoExample example);

    SipLineBaseInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SipLineBaseInfo record, @Param("example") SipLineBaseInfoExample example);

    int updateByExample(@Param("record") SipLineBaseInfo record, @Param("example") SipLineBaseInfoExample example);

    int updateByPrimaryKeySelective(SipLineBaseInfo record);

    int updateByPrimaryKey(SipLineBaseInfo record);
}