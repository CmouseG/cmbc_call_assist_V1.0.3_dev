package com.guiji.callcenter.dao;

import com.guiji.callcenter.dao.entity.CallOutRecord;
import com.guiji.callcenter.dao.entity.CallOutRecordExample;

import java.math.BigInteger;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CallOutRecordMapper {
    int countByExample(CallOutRecordExample example);

    int deleteByExample(CallOutRecordExample example);

    int deleteByPrimaryKey(BigInteger callId);

    int insert(CallOutRecord record);

    int insertSelective(CallOutRecord record);

    List<CallOutRecord> selectByExample(CallOutRecordExample example);

    CallOutRecord selectByPrimaryKey(BigInteger callId);

    int updateByExampleSelective(@Param("record") CallOutRecord record, @Param("example") CallOutRecordExample example);

    int updateByExample(@Param("record") CallOutRecord record, @Param("example") CallOutRecordExample example);

    int updateByPrimaryKeySelective(CallOutRecord record);

    int updateByPrimaryKey(CallOutRecord record);
}