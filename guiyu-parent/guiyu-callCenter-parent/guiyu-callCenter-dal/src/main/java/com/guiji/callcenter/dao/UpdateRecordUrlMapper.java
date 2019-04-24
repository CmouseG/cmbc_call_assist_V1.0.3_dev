package com.guiji.callcenter.dao;

import com.guiji.callcenter.dao.entity.UpdateRecordUrl;
import com.guiji.callcenter.dao.entity.UpdateRecordUrlExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UpdateRecordUrlMapper {
    int countByExample(UpdateRecordUrlExample example);

    int deleteByExample(UpdateRecordUrlExample example);

    int insert(UpdateRecordUrl record);

    int insertSelective(UpdateRecordUrl record);

    List<UpdateRecordUrl> selectByExample(UpdateRecordUrlExample example);

    int updateByExampleSelective(@Param("record") UpdateRecordUrl record, @Param("example") UpdateRecordUrlExample example);

    int updateByExample(@Param("record") UpdateRecordUrl record, @Param("example") UpdateRecordUrlExample example);
}