package com.guiji.botsentence.dao.ext;

import java.util.List;

public interface BotSentenceGradeDetailExtMapper {
   
    List<String> queryGradeOrderByProcessId(String processId);
    List<String> queryDistinctGradeNameByProcessId(String processId);
    List<String> queryGradeNameByProcessId(String processId);
    
}