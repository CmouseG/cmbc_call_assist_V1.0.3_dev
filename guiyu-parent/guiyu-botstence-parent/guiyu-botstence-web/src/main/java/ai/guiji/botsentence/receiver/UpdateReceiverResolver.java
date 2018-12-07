package ai.guiji.botsentence.receiver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.guiji.common.model.process.ProcessTypeEnum;
import com.guiji.dispatch.api.IDispatchPlanOut;
import com.guiji.guiyu.message.model.PublishBotstenceResultMsgVO;

import ai.guiji.botsentence.constant.Constant;
import ai.guiji.botsentence.dao.BotPublishSentenceLogMapper;
import ai.guiji.botsentence.dao.BotSentenceProcessMapper;
import ai.guiji.botsentence.dao.entity.BotAvailableTemplate;
import ai.guiji.botsentence.dao.entity.BotPublishSentenceLog;
import ai.guiji.botsentence.dao.entity.BotSentenceProcess;
import ai.guiji.botsentence.dao.entity.BotSentenceProcessExample;
import ai.guiji.botsentence.dao.ext.VoliceInfoExtMapper;

@Component
public class UpdateReceiverResolver {
	
	private Map<String,UpdateReceiverVo> cache=new HashMap<>();
	
	@Autowired
	private IDispatchPlanOut iDispatchPlanOut;
	
	@Autowired
	private BotSentenceProcessMapper botSentenceProcessMapper;
	
	@Autowired
	private VoliceInfoExtMapper voliceInfoExtMapper;
	
	@Autowired
	private BotPublishSentenceLogMapper botPublishSentenceLogMapper;
	
	public void resolver(PublishBotstenceResultMsgVO param){
		String tempId=param.getTmplId();
		UpdateReceiverVo vo=cache.get(tempId);
		if(vo==null){
			vo=new UpdateReceiverVo();
			vo.setTmplId(tempId);
			cache.put(tempId, vo);
		}
		
		if(param.getProcessTypeEnum()==ProcessTypeEnum.SELLBOT){
			vo.setSellbot(param.getResult());
		}else if(param.getProcessTypeEnum()==ProcessTypeEnum.ROBOT){
			vo.setRobot(param.getResult());
		}else if(param.getProcessTypeEnum()==ProcessTypeEnum.FREESWITCH){
			vo.setFreeswitch(param.getResult());
		}
		
		if(vo.getSellbot()==0 && vo.getRobot()==0 && vo.getRobot()==0){
			BotSentenceProcessExample example=new BotSentenceProcessExample();
			example.createCriteria().andTemplateIdEqualTo(tempId);
			List<BotSentenceProcess> list = botSentenceProcessMapper.selectByExample(example);
			BotSentenceProcess botSentenceProcess =list.get(0);
			botSentenceProcess.setState(Constant.APPROVE_ONLINE);//部署中
		    botSentenceProcessMapper.updateByPrimaryKeySelective(botSentenceProcess);
		    
		    BotPublishSentenceLog record=new BotPublishSentenceLog();
		    Long id=botPublishSentenceLogMapper.getLastPublishSentence(tempId);
		    record.setId(id);
		    record.setStatus("2");
		    botPublishSentenceLogMapper.updateByPrimaryKeySelective(record);
		    
		    //添加可用话术
		    BotAvailableTemplate botAvailableTemplate=new BotAvailableTemplate();
		    botAvailableTemplate.setTemplateId(tempId);
		    botAvailableTemplate.setTemplateName(botSentenceProcess.getTemplateName());
		    botAvailableTemplate.setUserId(botSentenceProcess.getCrtUser());
		    botPublishSentenceLogMapper.insertAvailableTemplate(botAvailableTemplate);
		    
		    //清空volice的【新增】和【修改】
			voliceInfoExtMapper.updateVoliceFlag(botSentenceProcess.getProcessId());
		}
		
		if(vo.getSellbot()!=-1 && vo.getRobot()!=-1 && vo.getRobot()!=-1){
			cache.remove(tempId);
			iDispatchPlanOut.successSchedule4TempId(tempId);
		}
		
		if(vo.getSellbot()==1 || vo.getRobot()==1 || vo.getRobot()==1){
			BotSentenceProcessExample example=new BotSentenceProcessExample();
			example.createCriteria().andTemplateIdEqualTo(tempId);
			List<BotSentenceProcess> list = botSentenceProcessMapper.selectByExample(example);
			BotSentenceProcess botSentenceProcess =list.get(0);
			botSentenceProcess.setState(Constant.ERROR);//部署中
		    botSentenceProcessMapper.updateByPrimaryKeySelective(botSentenceProcess);
		    
		    BotPublishSentenceLog record=new BotPublishSentenceLog();
		    Long id=botPublishSentenceLogMapper.getLastPublishSentence(tempId);
		    record.setId(id);
		    record.setStatus("3");
		    botPublishSentenceLogMapper.updateByPrimaryKey(record);
		}
	}

}
