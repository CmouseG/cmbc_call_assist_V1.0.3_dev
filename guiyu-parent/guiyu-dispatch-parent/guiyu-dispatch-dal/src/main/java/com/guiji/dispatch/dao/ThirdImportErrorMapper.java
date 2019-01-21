package com.guiji.dispatch.dao;

import com.guiji.dispatch.dao.entity.ThirdImportError;
import com.guiji.dispatch.dao.entity.ThirdImportErrorExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ThirdImportErrorMapper {
    int countByExample(ThirdImportErrorExample example);

    int deleteByExample(ThirdImportErrorExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ThirdImportError record);

    int insertSelective(ThirdImportError record);

    List<ThirdImportError> selectByExample(ThirdImportErrorExample example);

    ThirdImportError selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ThirdImportError record, @Param("example") ThirdImportErrorExample example);

    int updateByExample(@Param("record") ThirdImportError record, @Param("example") ThirdImportErrorExample example);

    int updateByPrimaryKeySelective(ThirdImportError record);

    int updateByPrimaryKey(ThirdImportError record);
}