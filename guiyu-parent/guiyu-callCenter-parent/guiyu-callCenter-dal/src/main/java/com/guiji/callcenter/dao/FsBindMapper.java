package com.guiji.callcenter.dao;

import com.guiji.callcenter.dao.entity.FsBind;
import com.guiji.callcenter.dao.entity.FsBindExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FsBindMapper {
    int countByExample(FsBindExample example);

    int deleteByExample(FsBindExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(FsBind record);

    int insertSelective(FsBind record);

    List<FsBind> selectByExample(FsBindExample example);

    FsBind selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") FsBind record, @Param("example") FsBindExample example);

    int updateByExample(@Param("record") FsBind record, @Param("example") FsBindExample example);

    int updateByPrimaryKeySelective(FsBind record);

    int updateByPrimaryKey(FsBind record);
}