package com.guiji.botsentence.dao.ext;

import org.apache.ibatis.annotations.Param;

public interface BotSentenceDeployExtMapper {

    int countVersionByTemplateId(@Param("templateId") String templateId);
}