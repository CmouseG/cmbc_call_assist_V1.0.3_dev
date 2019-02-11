package com.guiji.dispatch.dao;

import com.guiji.dispatch.dao.entity.PhoneRegion;
import com.guiji.dispatch.dao.entity.PhoneRegionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PhoneRegionMapper {
    int countByExample(PhoneRegionExample example);

    int deleteByExample(PhoneRegionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PhoneRegion record);

    int insertSelective(PhoneRegion record);

    List<PhoneRegion> selectByExample(PhoneRegionExample example);

    PhoneRegion selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PhoneRegion record, @Param("example") PhoneRegionExample example);

    int updateByExample(@Param("record") PhoneRegion record, @Param("example") PhoneRegionExample example);

    int updateByPrimaryKeySelective(PhoneRegion record);

    int updateByPrimaryKey(PhoneRegion record);
}