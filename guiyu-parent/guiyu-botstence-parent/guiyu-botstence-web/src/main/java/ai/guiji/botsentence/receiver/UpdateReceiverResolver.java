package ai.guiji.botsentence.receiver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.guiji.auth.api.IAuth;
import com.guiji.common.model.process.ProcessTypeEnum;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.dispatch.api.IDispatchPlanOut;
import com.guiji.guiyu.message.model.PublishBotstenceResultMsgVO;
import com.guiji.user.dao.entity.SysUser;

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
	
	protected static Logger logger=LoggerFactory.getLogger(UpdateReceiverResolver.class);
	
	private Map<String,UpdateReceiverVo> cache=new HashMap<>();
	
	@Autowired
	private IDispatchPlanOut iDispatchPlanOut;
	
	@Autowired
	private VoliceInfoExtMapper voliceInfoExtMapper;
	
	@Autowired
	private BotSentenceProcessMapper botSentenceProcessMapper;
	
	@Autowired
	private BotPublishSentenceLogMapper botPublishSentenceLogMapper;
	
	public void resolver(PublishBotstenceResultMsgVO param){
		logger.info("resolver---start");
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
		logger.info(JSONObject.toJSONString(vo));
		logger.info("--------------------------cache------------------------");
		logger.info(JSONObject.toJSONString(cache));
		logger.info("--------------------------cache------------------------");
		if(vo.getSellbot()!=-1 && vo.getRobot()!=-1 && vo.getFreeswitch()!=-1){
			cache.remove(tempId);
			iDispatchPlanOut.successSchedule4TempId(tempId);
		}
		if(vo.getSellbot()==1 || vo.getRobot()==1 || vo.getFreeswitch()==1){
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
		    botPublishSentenceLogMapper.updateByPrimaryKeySelective(record);
		    
		}
		
		if(vo.getSellbot()==0 && vo.getRobot()==0 && vo.getFreeswitch()==0){
				logger.info("BotSentenceProcessExample----start");
				
				BotSentenceProcessExample example=new BotSentenceProcessExample();
				example.createCriteria().andTemplateIdEqualTo(tempId);
				List<BotSentenceProcess> list = botSentenceProcessMapper.selectByExample(example);
				BotSentenceProcess botSentenceProcess =list.get(0);
				botSentenceProcess.setState(Constant.APPROVE_ONLINE);//部署中
				int version=Integer.valueOf(botSentenceProcess.getVersion())+1;
				botSentenceProcess.setVersion(String.valueOf(version));
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
			    botAvailableTemplate.setUserId(Long.valueOf(botSentenceProcess.getCrtUser()));
			    botAvailableTemplate.setOrgCode(botSentenceProcess.getOrgCode());;
			    botPublishSentenceLogMapper.deleteAvailableTemplate(botAvailableTemplate);
			    botPublishSentenceLogMapper.insertAvailableTemplate(botAvailableTemplate);
//			    
//			    //清空volice的【新增】和【修改】
				voliceInfoExtMapper.updateVoliceFlag(botSentenceProcess.getProcessId());
				logger.info("UpdateReceiverResolver---end");
			}
		logger.info("resolver----end");
	}

}
