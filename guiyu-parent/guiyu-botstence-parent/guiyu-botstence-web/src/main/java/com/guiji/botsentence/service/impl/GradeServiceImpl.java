package com.guiji.botsentence.service.impl;

import com.guiji.botsentence.dao.BotSentenceGradeMapper;
import com.guiji.botsentence.dao.BotSentenceGradeRuleMapper;
import com.guiji.botsentence.dao.entity.BotSentenceGrade;
import com.guiji.botsentence.dao.entity.BotSentenceGradeExample;
import com.guiji.botsentence.dao.entity.BotSentenceGradeRule;
import com.guiji.botsentence.dao.entity.BotSentenceGradeRuleExample;
import com.guiji.botsentence.service.IGradeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GradeServiceImpl implements IGradeService {

    @Resource
    private BotSentenceGradeRuleMapper botSentenceGradeRuleMapper;

    @Resource
    private BotSentenceGradeMapper botSentenceGradeMapper;

    @Override
    @Transactional
    public void deleteGradeForBindBranch(String processId, String branchName) {

        BotSentenceGradeRuleExample gradeRuleExample = new BotSentenceGradeRuleExample();
        gradeRuleExample.createCriteria()
                .andProcessIdEqualTo(processId)
                .andValue2EqualTo(branchName);

        List<BotSentenceGradeRule> gradeRules = botSentenceGradeRuleMapper.selectByExample(gradeRuleExample);
        if(CollectionUtils.isEmpty(gradeRules)){
            return;
        }

        BotSentenceGradeExample gradeExample = new BotSentenceGradeExample();
        gradeExample.createCriteria().andProcessIdEqualTo(processId);

        List<BotSentenceGrade> grades = botSentenceGradeMapper.selectByExample(gradeExample);
        if(CollectionUtils.isEmpty(grades)){
            return;
        }
        BotSentenceGrade grade = grades.get(0);
        String statOrder = grade.getStatOrder();
        for (BotSentenceGradeRule gradeRule: gradeRules) {
            String intentName = gradeRule.getIntentName();

            BotSentenceGradeRuleExample ruleExample = new BotSentenceGradeRuleExample();
            ruleExample.createCriteria()
                    .andProcessIdEqualTo(processId)
                    .andIntentNameEqualTo(intentName)
                    .andRuleNoEqualTo(gradeRule.getRuleNo());

            if(1 == botSentenceGradeRuleMapper.countByExample(ruleExample)){
                statOrder = statOrder.replaceAll(intentName + "," , "");
            }
        }

        botSentenceGradeRuleMapper.deleteByExample(gradeRuleExample);

        if(grade.getStatOrder().equals(statOrder)){
            return;
        }
        grade.setStatOrder(statOrder);
        botSentenceGradeMapper.updateByPrimaryKey(grade);
    }
}
