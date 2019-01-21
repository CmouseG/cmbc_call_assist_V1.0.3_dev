package com.guiji.sms.dao;

import com.guiji.sms.dao.entity.SmsTask;
import com.guiji.sms.dao.entity.SmsTaskExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SmsTaskMapper {
    long countByExample(SmsTaskExample example);

    int deleteByExample(SmsTaskExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SmsTask record);

    int insertSelective(SmsTask record);

    List<SmsTask> selectByExampleWithBLOBs(SmsTaskExample example);

    List<SmsTask> selectByExample(SmsTaskExample example);

    SmsTask selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SmsTask record, @Param("example") SmsTaskExample example);

    int updateByExampleWithBLOBs(@Param("record") SmsTask record, @Param("example") SmsTaskExample example);

    int updateByExample(@Param("record") SmsTask record, @Param("example") SmsTaskExample example);

    int updateByPrimaryKeySelective(SmsTask record);

    int updateByPrimaryKeyWithBLOBs(SmsTask record);

    int updateByPrimaryKey(SmsTask record);
}