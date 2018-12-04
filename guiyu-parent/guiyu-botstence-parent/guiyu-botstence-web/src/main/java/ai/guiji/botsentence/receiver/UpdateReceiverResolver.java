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
import ai.guiji.botsentence.dao.BotSentenceProcessMapper;
import ai.guiji.botsentence.dao.entity.BotSentenceProcess;
import ai.guiji.botsentence.dao.entity.BotSentenceProcessExample;

@Component
public class UpdateReceiverResolver {
	
	private Map<String,UpdateReceiverVo> cache=new HashMap<>();
	
	@Autowired
	private IDispatchPlanOut iDispatchPlanOut;
	
	@Autowired
	private BotSentenceProcessMapper botSentenceProcessMapper;
	
	public void resolver(PublishBotstenceResultMsgVO param){
		String tempId=param.getTmplId();
		UpdateReceiverVo vo=cache.get(tempId);
		if(vo==null){
			vo=new UpdateReceiverVo();
			vo.setTmplId(tempId);
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
		}
	}

}
