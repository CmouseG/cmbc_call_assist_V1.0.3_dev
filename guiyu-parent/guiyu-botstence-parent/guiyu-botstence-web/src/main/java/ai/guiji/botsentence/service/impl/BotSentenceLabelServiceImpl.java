package ai.guiji.botsentence.service.impl;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ai.guiji.botsentence.dao.MyLabelMapper;
import ai.guiji.botsentence.dao.entity.BotSentenceLabel;
import ai.guiji.botsentence.dao.entity.MyBotLabel;
import ai.guiji.botsentence.service.IBotSentenceLabelService;
import ai.guiji.component.exception.CommonException;

@Service
public class BotSentenceLabelServiceImpl implements IBotSentenceLabelService{
	
	@Autowired
	private MyLabelMapper MyLabelMapper;
	@Autowired
	private BotSentenceProcessServiceImpl botSentenceProcessService;
	
	@Override
	public List<MyBotLabel> getKeysList(String processId) {
		return MyLabelMapper.getKeysList(processId);
	}

 
	@Override
	@Transactional
	public int updateBotLabelVO(BotSentenceLabel botSentenceLabel) {
		if(StringUtils.isBlank(botSentenceLabel.getProcessId()) || StringUtils.isBlank(botSentenceLabel.getLabelId())) {
			throw new CommonException("请求参数不完整!");
		}
		
		return MyLabelMapper.updateByPrimaryKeySelective(botSentenceLabel);
	}


	
}
