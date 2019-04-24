package com.guiji.sms.dao;

import com.guiji.sms.dao.entity.SmsSendDetail;
import com.guiji.sms.dao.entity.SmsSendDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SmsSendDetailMapper {
    long countByExample(SmsSendDetailExample example);

    int deleteByExample(SmsSendDetailExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SmsSendDetail record);

    int insertSelective(SmsSendDetail record);

    List<SmsSendDetail> selectByExampleWithBLOBs(SmsSendDetailExample example);

    List<SmsSendDetail> selectByExample(SmsSendDetailExample example);

    SmsSendDetail selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SmsSendDetail record, @Param("example") SmsSendDetailExample example);

    int updateByExampleWithBLOBs(@Param("record") SmsSendDetail record, @Param("example") SmsSendDetailExample example);

    int updateByExample(@Param("record") SmsSendDetail record, @Param("example") SmsSendDetailExample example);

    int updateByPrimaryKeySelective(SmsSendDetail record);

    int updateByPrimaryKeyWithBLOBs(SmsSendDetail record);

    int updateByPrimaryKey(SmsSendDetail record);
}