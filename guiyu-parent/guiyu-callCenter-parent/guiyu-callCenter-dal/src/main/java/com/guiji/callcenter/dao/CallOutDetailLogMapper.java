package com.guiji.callcenter.dao;

import com.guiji.callcenter.dao.entity.CallOutDetailLog;
import com.guiji.callcenter.dao.entity.CallOutDetailLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CallOutDetailLogMapper {
    int countByExample(CallOutDetailLogExample example);

    int deleteByExample(CallOutDetailLogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CallOutDetailLog record);

    int insertSelective(CallOutDetailLog record);

    List<CallOutDetailLog> selectByExample(CallOutDetailLogExample example);

    CallOutDetailLog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CallOutDetailLog record, @Param("example") CallOutDetailLogExample example);

    int updateByExample(@Param("record") CallOutDetailLog record, @Param("example") CallOutDetailLogExample example);

    int updateByPrimaryKeySelective(CallOutDetailLog record);

    int updateByPrimaryKey(CallOutDetailLog record);
}