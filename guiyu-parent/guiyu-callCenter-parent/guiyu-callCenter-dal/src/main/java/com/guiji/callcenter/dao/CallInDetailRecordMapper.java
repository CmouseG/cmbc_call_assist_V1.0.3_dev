package com.guiji.callcenter.dao;

import com.guiji.callcenter.dao.entity.CallInDetailRecord;
import com.guiji.callcenter.dao.entity.CallInDetailRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CallInDetailRecordMapper {
    int countByExample(CallInDetailRecordExample example);

    int deleteByExample(CallInDetailRecordExample example);

    int deleteByPrimaryKey(Long callDetailId);

    int insert(CallInDetailRecord record);

    int insertSelective(CallInDetailRecord record);

    List<CallInDetailRecord> selectByExample(CallInDetailRecordExample example);

    CallInDetailRecord selectByPrimaryKey(Long callDetailId);

    int updateByExampleSelective(@Param("record") CallInDetailRecord record, @Param("example") CallInDetailRecordExample example);

    int updateByExample(@Param("record") CallInDetailRecord record, @Param("example") CallInDetailRecordExample example);

    int updateByPrimaryKeySelective(CallInDetailRecord record);

    int updateByPrimaryKey(CallInDetailRecord record);
}