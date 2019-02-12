package com.guiji.clm.dao;

import com.guiji.clm.dao.entity.SipLineApply;
import com.guiji.clm.dao.entity.SipLineApplyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SipLineApplyMapper {
    int countByExample(SipLineApplyExample example);

    int deleteByExample(SipLineApplyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SipLineApply record);

    int insertSelective(SipLineApply record);

    List<SipLineApply> selectByExample(SipLineApplyExample example);

    SipLineApply selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SipLineApply record, @Param("example") SipLineApplyExample example);

    int updateByExample(@Param("record") SipLineApply record, @Param("example") SipLineApplyExample example);

    int updateByPrimaryKeySelective(SipLineApply record);

    int updateByPrimaryKey(SipLineApply record);
}