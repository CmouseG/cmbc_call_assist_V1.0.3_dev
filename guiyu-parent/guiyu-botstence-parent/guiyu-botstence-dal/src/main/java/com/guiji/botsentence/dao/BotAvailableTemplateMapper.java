package com.guiji.botsentence.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.guiji.botsentence.dao.entity.BotAvailableTemplate;
import com.guiji.botsentence.dao.entity.BotAvailableTemplateExample;

public interface BotAvailableTemplateMapper {
    int countByExample(BotAvailableTemplateExample example);

    int deleteByExample(BotAvailableTemplateExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BotAvailableTemplate record);

    int insertSelective(BotAvailableTemplate record);

    List<BotAvailableTemplate> selectByExample(BotAvailableTemplateExample example);

    BotAvailableTemplate selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BotAvailableTemplate record, @Param("example") BotAvailableTemplateExample example);

    int updateByExample(@Param("record") BotAvailableTemplate record, @Param("example") BotAvailableTemplateExample example);

    int updateByPrimaryKeySelective(BotAvailableTemplate record);

    int updateByPrimaryKey(BotAvailableTemplate record);

    List<BotAvailableTemplate> getUserAvailableTemplate(Long userId);

    void addUserAvailableTemplate(@Param("userId")Long userId,@Param("array")String[] availableId);
    void addUserAvailableTemplateAuto(@Param("userId")Long userId,@Param("availableId")String availableId);

}