package com.guiji.dispatch.dao;

import com.guiji.dispatch.dao.entity.BlackListRecords;
import com.guiji.dispatch.dao.entity.BlackListRecordsExample;
import java.util.List;

import com.guiji.dispatch.dto.QueryBlackListDto;
import com.guiji.dispatch.sys.ResultPage;
import org.apache.ibatis.annotations.Param;

public interface BlackListRecordsMapper {
    int countByExample(BlackListRecordsExample example);

    int deleteByExample(BlackListRecordsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BlackListRecords record);

    int insertSelective(BlackListRecords record);

    List<BlackListRecords> selectByExample(BlackListRecordsExample example);

    BlackListRecords selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BlackListRecords record, @Param("example") BlackListRecordsExample example);

    int updateByExample(@Param("record") BlackListRecords record, @Param("example") BlackListRecordsExample example);

    int updateByPrimaryKeySelective(BlackListRecords record);

    int updateByPrimaryKey(BlackListRecords record);

    /*********add by qianxin  begin*****************************/
    //查询黑名单记录列表
    List<BlackListRecords> queryBlackListRecords(@Param("blackRecord") QueryBlackListDto blackRecord,
                                                 @Param("page") ResultPage<BlackListRecords> page);

    //查询黑名单记录数量
    int queryBlackRecordsCount(@Param("blackRecord") QueryBlackListDto blackRecord);

}