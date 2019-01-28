package com.guiji.botsentence.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.guiji.botsentence.dao.entity.BotSentenceWechatUserInfo;
import com.guiji.botsentence.dao.entity.BotSentenceWechatUserInfoExample;

public interface BotSentenceWechatUserInfoMapper {
    int countByExample(BotSentenceWechatUserInfoExample example);

    int deleteByExample(BotSentenceWechatUserInfoExample example);

    int deleteByPrimaryKey(String userId);

    int insert(BotSentenceWechatUserInfo record);

    int insertSelective(BotSentenceWechatUserInfo record);

    List<BotSentenceWechatUserInfo> selectByExample(BotSentenceWechatUserInfoExample example);

    BotSentenceWechatUserInfo selectByPrimaryKey(String userId);

    int updateByExampleSelective(@Param("record") BotSentenceWechatUserInfo record, @Param("example") BotSentenceWechatUserInfoExample example);

    int updateByExample(@Param("record") BotSentenceWechatUserInfo record, @Param("example") BotSentenceWechatUserInfoExample example);

    int updateByPrimaryKeySelective(BotSentenceWechatUserInfo record);

    int updateByPrimaryKey(BotSentenceWechatUserInfo record);
}