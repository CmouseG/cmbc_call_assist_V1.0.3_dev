package ai.guiji.botsentence.dao;

import ai.guiji.botsentence.dao.entity.NormalDialogueEffect;
import ai.guiji.botsentence.dao.entity.NormalDialogueEffectExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NormalDialogueEffectMapper {
    int countByExample(NormalDialogueEffectExample example);

    int deleteByExample(NormalDialogueEffectExample example);

    int deleteByPrimaryKey(String dialogueId);

    int insert(NormalDialogueEffect record);

    int insertSelective(NormalDialogueEffect record);

    List<NormalDialogueEffect> selectByExample(NormalDialogueEffectExample example);

    NormalDialogueEffect selectByPrimaryKey(String dialogueId);

    int updateByExampleSelective(@Param("record") NormalDialogueEffect record, @Param("example") NormalDialogueEffectExample example);

    int updateByExample(@Param("record") NormalDialogueEffect record, @Param("example") NormalDialogueEffectExample example);

    int updateByPrimaryKeySelective(NormalDialogueEffect record);

    int updateByPrimaryKey(NormalDialogueEffect record);
}