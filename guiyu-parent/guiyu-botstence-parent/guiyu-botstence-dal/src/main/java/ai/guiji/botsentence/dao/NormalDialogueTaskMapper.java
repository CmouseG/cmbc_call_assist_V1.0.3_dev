package ai.guiji.botsentence.dao;

import ai.guiji.botsentence.dao.entity.NormalDialogueTask;
import ai.guiji.botsentence.dao.entity.NormalDialogueTaskExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NormalDialogueTaskMapper {
    int countByExample(NormalDialogueTaskExample example);

    int deleteByExample(NormalDialogueTaskExample example);

    int deleteByPrimaryKey(String dialogueId);

    int insert(NormalDialogueTask record);

    int insertSelective(NormalDialogueTask record);

    List<NormalDialogueTask> selectByExample(NormalDialogueTaskExample example);

    NormalDialogueTask selectByPrimaryKey(String dialogueId);

    int updateByExampleSelective(@Param("record") NormalDialogueTask record, @Param("example") NormalDialogueTaskExample example);

    int updateByExample(@Param("record") NormalDialogueTask record, @Param("example") NormalDialogueTaskExample example);

    int updateByPrimaryKeySelective(NormalDialogueTask record);

    int updateByPrimaryKey(NormalDialogueTask record);
}