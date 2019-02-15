package com.guiji.sms.dao;

import com.guiji.sms.dao.entity.SmsTaskDetail;
import com.guiji.sms.dao.entity.SmsTaskDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SmsTaskDetailMapper {
    long countByExample(SmsTaskDetailExample example);

    int deleteByExample(SmsTaskDetailExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SmsTaskDetail record);

    int insertSelective(SmsTaskDetail record);

    List<SmsTaskDetail> selectByExampleWithBLOBs(SmsTaskDetailExample example);

    List<SmsTaskDetail> selectByExample(SmsTaskDetailExample example);

    SmsTaskDetail selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SmsTaskDetail record, @Param("example") SmsTaskDetailExample example);

    int updateByExampleWithBLOBs(@Param("record") SmsTaskDetail record, @Param("example") SmsTaskDetailExample example);

    int updateByExample(@Param("record") SmsTaskDetail record, @Param("example") SmsTaskDetailExample example);

    int updateByPrimaryKeySelective(SmsTaskDetail record);

    int updateByPrimaryKeyWithBLOBs(SmsTaskDetail record);

    int updateByPrimaryKey(SmsTaskDetail record);
}