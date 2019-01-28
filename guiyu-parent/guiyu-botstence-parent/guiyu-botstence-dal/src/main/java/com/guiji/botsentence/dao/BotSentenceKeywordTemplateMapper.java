package com.guiji.botsentence.dao;


import org.apache.ibatis.annotations.Param;

import com.guiji.botsentence.dao.entity.BotSentenceKeywordTemplate;
import com.guiji.botsentence.dao.entity.BotSentenceKeywordTemplateExample;

import java.util.List;

public interface BotSentenceKeywordTemplateMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bot_sentence_keyword_template
     *
     * @mbggenerated
     */
    int countByExample(BotSentenceKeywordTemplateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bot_sentence_keyword_template
     *
     * @mbggenerated
     */
    int deleteByExample(BotSentenceKeywordTemplateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bot_sentence_keyword_template
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bot_sentence_keyword_template
     *
     * @mbggenerated
     */
    int insert(BotSentenceKeywordTemplate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bot_sentence_keyword_template
     *
     * @mbggenerated
     */
    int insertSelective(BotSentenceKeywordTemplate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bot_sentence_keyword_template
     *
     * @mbggenerated
     */
    List<BotSentenceKeywordTemplate> selectByExampleWithBLOBs(BotSentenceKeywordTemplateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bot_sentence_keyword_template
     *
     * @mbggenerated
     */
    List<BotSentenceKeywordTemplate> selectByExample(BotSentenceKeywordTemplateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bot_sentence_keyword_template
     *
     * @mbggenerated
     */
    BotSentenceKeywordTemplate selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bot_sentence_keyword_template
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") BotSentenceKeywordTemplate record, @Param("example") BotSentenceKeywordTemplateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bot_sentence_keyword_template
     *
     * @mbggenerated
     */
    int updateByExampleWithBLOBs(@Param("record") BotSentenceKeywordTemplate record, @Param("example") BotSentenceKeywordTemplateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bot_sentence_keyword_template
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") BotSentenceKeywordTemplate record, @Param("example") BotSentenceKeywordTemplateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bot_sentence_keyword_template
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(BotSentenceKeywordTemplate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bot_sentence_keyword_template
     *
     * @mbggenerated
     */
    int updateByPrimaryKeyWithBLOBs(BotSentenceKeywordTemplate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bot_sentence_keyword_template
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(BotSentenceKeywordTemplate record);
}