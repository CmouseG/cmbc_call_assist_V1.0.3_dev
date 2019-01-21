package com.guiji.sms.dao;

import com.guiji.sms.dao.entity.SmsConfig;
import com.guiji.sms.dao.entity.SmsConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SmsConfigMapper {
    long countByExample(SmsConfigExample example);

    int deleteByExample(SmsConfigExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SmsConfig record);

    int insertSelective(SmsConfig record);

    List<SmsConfig> selectByExampleWithBLOBs(SmsConfigExample example);

    List<SmsConfig> selectByExample(SmsConfigExample example);

    SmsConfig selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SmsConfig record, @Param("example") SmsConfigExample example);

    int updateByExampleWithBLOBs(@Param("record") SmsConfig record, @Param("example") SmsConfigExample example);

    int updateByExample(@Param("record") SmsConfig record, @Param("example") SmsConfigExample example);

    int updateByPrimaryKeySelective(SmsConfig record);

    int updateByPrimaryKeyWithBLOBs(SmsConfig record);

    int updateByPrimaryKey(SmsConfig record);
}