package com.guiji.dispatch.dao;

import com.guiji.dispatch.dao.entity.PhoneRegionError;
import com.guiji.dispatch.dao.entity.PhoneRegionErrorExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PhoneRegionErrorMapper {
    int countByExample(PhoneRegionErrorExample example);

    int deleteByExample(PhoneRegionErrorExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PhoneRegionError record);

    int insertSelective(PhoneRegionError record);

    List<PhoneRegionError> selectByExample(PhoneRegionErrorExample example);

    PhoneRegionError selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PhoneRegionError record, @Param("example") PhoneRegionErrorExample example);

    int updateByExample(@Param("record") PhoneRegionError record, @Param("example") PhoneRegionErrorExample example);

    int updateByPrimaryKeySelective(PhoneRegionError record);

    int updateByPrimaryKey(PhoneRegionError record);
}