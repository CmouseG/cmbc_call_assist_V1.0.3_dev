package ai.guiji.botsentence.dao;

import ai.guiji.botsentence.dao.entity.BotAvailableTemplate;
import ai.guiji.botsentence.dao.entity.BotAvailableTemplateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

public interface BotAvailableTemplateMapper {
    int countByExample(BotAvailableTemplateExample example);

    int deleteByExample(BotAvailableTemplateExample example);

    int deleteByPrimaryKey(Long id);

    int insert(BotAvailableTemplate record);

    int insertSelective(BotAvailableTemplate record);

    List<BotAvailableTemplate> selectByExample(BotAvailableTemplateExample example);

    BotAvailableTemplate selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") BotAvailableTemplate record, @Param("example") BotAvailableTemplateExample example);

    int updateByExample(@Param("record") BotAvailableTemplate record, @Param("example") BotAvailableTemplateExample example);

    int updateByPrimaryKeySelective(BotAvailableTemplate record);

    int updateByPrimaryKey(BotAvailableTemplate record);
    
    List<BotAvailableTemplate> getUserAvailableTemplate(Long userId);
    
    public void addUserAvailableTemplate(@Param("userId")Long userId,@Param("array")String[] availableId);
}