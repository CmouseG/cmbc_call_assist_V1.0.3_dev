package com.guiji.callcenter.dao;

import com.guiji.callcenter.dao.entity.CallOutDetail;
import com.guiji.callcenter.dao.entity.CallOutDetailExample;

import java.math.BigInteger;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CallOutDetailMapper {
    int countByExample(CallOutDetailExample example);

    int deleteByExample(CallOutDetailExample example);

    int deleteByPrimaryKey(BigInteger callDetailId);

    int insert(CallOutDetail record);

    int insertSelective(CallOutDetail record);

    List<CallOutDetail> selectByExample(CallOutDetailExample example);

    CallOutDetail selectByPrimaryKey(BigInteger callDetailId);

    int updateByExampleSelective(@Param("record") CallOutDetail record, @Param("example") CallOutDetailExample example);

    int updateByExample(@Param("record") CallOutDetail record, @Param("example") CallOutDetailExample example);

    int updateByPrimaryKeySelective(CallOutDetail record);

    int updateByPrimaryKey(CallOutDetail record);
}