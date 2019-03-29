package com.guiji.voipgateway.dingxin.dao;

import com.guiji.voipgateway.dingxin.dao.entity.TblPort;
import com.guiji.voipgateway.dingxin.dao.entity.TblPortExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TblPortMapper {
    int countByExample(TblPortExample example);

    int deleteByExample(TblPortExample example);

    int deleteByPrimaryKey(Integer uuid);

    int insert(TblPort record);

    int insertSelective(TblPort record);

    List<TblPort> selectByExample(TblPortExample example);

    TblPort selectByPrimaryKey(Integer uuid);

    int updateByExampleSelective(@Param("record") TblPort record, @Param("example") TblPortExample example);

    int updateByExample(@Param("record") TblPort record, @Param("example") TblPortExample example);

    int updateByPrimaryKeySelective(TblPort record);

    int updateByPrimaryKey(TblPort record);
}