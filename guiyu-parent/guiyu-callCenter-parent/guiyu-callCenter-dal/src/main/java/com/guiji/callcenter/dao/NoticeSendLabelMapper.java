package com.guiji.callcenter.dao;

import com.guiji.callcenter.dao.entity.NoticeSendLabel;
import com.guiji.callcenter.dao.entity.NoticeSendLabelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NoticeSendLabelMapper {
    int countByExample(NoticeSendLabelExample example);

    int deleteByExample(NoticeSendLabelExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(NoticeSendLabel record);

    int insertSelective(NoticeSendLabel record);

    List<NoticeSendLabel> selectByExample(NoticeSendLabelExample example);

    NoticeSendLabel selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") NoticeSendLabel record, @Param("example") NoticeSendLabelExample example);

    int updateByExample(@Param("record") NoticeSendLabel record, @Param("example") NoticeSendLabelExample example);

    int updateByPrimaryKeySelective(NoticeSendLabel record);

    int updateByPrimaryKey(NoticeSendLabel record);
}