package com.guiji.callcenter.dao;

import com.guiji.callcenter.dao.entity.CallInRecord;
import com.guiji.callcenter.dao.entity.CallInRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CallInRecordMapper {
    int countByExample(CallInRecordExample example);

    int deleteByExample(CallInRecordExample example);

    int deleteByPrimaryKey(Long callId);

    int insert(CallInRecord record);

    int insertSelective(CallInRecord record);

    List<CallInRecord> selectByExample(CallInRecordExample example);

    CallInRecord selectByPrimaryKey(Long callId);

    int updateByExampleSelective(@Param("record") CallInRecord record, @Param("example") CallInRecordExample example);

    int updateByExample(@Param("record") CallInRecord record, @Param("example") CallInRecordExample example);

    int updateByPrimaryKeySelective(CallInRecord record);

    int updateByPrimaryKey(CallInRecord record);
}