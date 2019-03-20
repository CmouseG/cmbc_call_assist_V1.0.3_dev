package com.guiji.callcenter.dao;

import com.guiji.callcenter.dao.entity.CallInDetail;
import com.guiji.callcenter.dao.entity.CallInDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CallInDetailMapper {
    int countByExample(CallInDetailExample example);

    int deleteByExample(CallInDetailExample example);

    int deleteByPrimaryKey(Long callDetailId);

    int insert(CallInDetail record);

    int insertSelective(CallInDetail record);

    List<CallInDetail> selectByExample(CallInDetailExample example);

    CallInDetail selectByPrimaryKey(Long callDetailId);

    int updateByExampleSelective(@Param("record") CallInDetail record, @Param("example") CallInDetailExample example);

    int updateByExample(@Param("record") CallInDetail record, @Param("example") CallInDetailExample example);

    int updateByPrimaryKeySelective(CallInDetail record);

    int updateByPrimaryKey(CallInDetail record);
}