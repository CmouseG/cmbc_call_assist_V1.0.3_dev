package com.guiji.dispatch.dao;

import com.guiji.dispatch.dao.entity.ThirdInterfaceRecords;
import com.guiji.dispatch.dao.entity.ThirdInterfaceRecordsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ThirdInterfaceRecordsMapper {
    int countByExample(ThirdInterfaceRecordsExample example);

    int deleteByExample(ThirdInterfaceRecordsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ThirdInterfaceRecords record);

    int insertSelective(ThirdInterfaceRecords record);

    List<ThirdInterfaceRecords> selectByExample(ThirdInterfaceRecordsExample example);

    ThirdInterfaceRecords selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ThirdInterfaceRecords record, @Param("example") ThirdInterfaceRecordsExample example);

    int updateByExample(@Param("record") ThirdInterfaceRecords record, @Param("example") ThirdInterfaceRecordsExample example);

    int updateByPrimaryKeySelective(ThirdInterfaceRecords record);

    int updateByPrimaryKey(ThirdInterfaceRecords record);
}