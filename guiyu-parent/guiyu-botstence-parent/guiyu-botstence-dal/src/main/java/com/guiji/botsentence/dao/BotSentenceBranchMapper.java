package com.guiji.botsentence.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.guiji.botsentence.dao.entity.BotSentenceBranch;
import com.guiji.botsentence.dao.entity.BotSentenceBranchExample;

public interface BotSentenceBranchMapper {
    int countByExample(BotSentenceBranchExample example);

    int deleteByExample(BotSentenceBranchExample example);

    int deleteByPrimaryKey(String branchId);

    int insert(BotSentenceBranch record);

    int insertSelective(BotSentenceBranch record);

    List<BotSentenceBranch> selectByExampleWithBLOBs(BotSentenceBranchExample example);

    List<BotSentenceBranch> selectByExample(BotSentenceBranchExample example);

    BotSentenceBranch selectByPrimaryKey(String branchId);

    int updateByExampleSelective(@Param("record") BotSentenceBranch record, @Param("example") BotSentenceBranchExample example);

    int updateByExampleWithBLOBs(@Param("record") BotSentenceBranch record, @Param("example") BotSentenceBranchExample example);

    int updateByExample(@Param("record") BotSentenceBranch record, @Param("example") BotSentenceBranchExample example);

    int updateByPrimaryKeySelective(BotSentenceBranch record);

    int updateByPrimaryKeyWithBLOBs(BotSentenceBranch record);

    int updateByPrimaryKey(BotSentenceBranch record);
}