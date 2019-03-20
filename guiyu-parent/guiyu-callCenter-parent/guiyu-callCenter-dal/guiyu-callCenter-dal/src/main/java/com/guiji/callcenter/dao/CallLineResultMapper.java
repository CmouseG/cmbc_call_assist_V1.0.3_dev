package com.guiji.callcenter.dao;

import com.guiji.callcenter.dao.entity.CallLineResult;
import com.guiji.callcenter.dao.entity.CallLineResultExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CallLineResultMapper {
    int countByExample(CallLineResultExample example);

    int deleteByExample(CallLineResultExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CallLineResult record);

    int insertSelective(CallLineResult record);

    List<CallLineResult> selectByExample(CallLineResultExample example);

    CallLineResult selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CallLineResult record, @Param("example") CallLineResultExample example);

    int updateByExample(@Param("record") CallLineResult record, @Param("example") CallLineResultExample example);

    int updateByPrimaryKeySelective(CallLineResult record);

    int updateByPrimaryKey(CallLineResult record);
}