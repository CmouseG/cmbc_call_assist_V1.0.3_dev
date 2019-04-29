package com.guiji.botsentence.service.impl;

import com.guiji.botsentence.dao.BotSentenceProcessMapper;
import com.guiji.botsentence.dao.BotSentenceTtsTaskMapper;
import com.guiji.botsentence.dao.entity.BotSentenceProcess;
import com.guiji.botsentence.dao.entity.BotSentenceProcessExample;
import com.guiji.botsentence.dao.entity.BotSentenceTtsTaskExample;
import com.guiji.botsentence.service.IVoiceInfoService;
import com.guiji.common.exception.CommonException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class VoiceInfoServiceImpl implements IVoiceInfoService {

    @Resource
    private BotSentenceProcessMapper botSentenceProcessMapper;

    @Resource
    private BotSentenceTtsTaskMapper botSentenceTtsTaskMapper;

    @Override
    public int getTtsParamsCountByTemplateId(String templateId) {

        BotSentenceProcessExample processExample = new BotSentenceProcessExample();
        processExample.createCriteria().andTemplateIdEqualTo(templateId);
        List<BotSentenceProcess> processes = botSentenceProcessMapper.selectByExample(processExample);

        if(CollectionUtils.isEmpty(processes)){
            throw new CommonException("未找到对应的话术！");
        }

        String processId = processes.get(0).getProcessId();

        BotSentenceTtsTaskExample ttsTaskExample = new BotSentenceTtsTaskExample();
        ttsTaskExample.createCriteria()
                .andProcessIdEqualTo(processId)
                .andIsParamEqualTo("01");

        return botSentenceTtsTaskMapper.countByExample(ttsTaskExample);
    }
}
