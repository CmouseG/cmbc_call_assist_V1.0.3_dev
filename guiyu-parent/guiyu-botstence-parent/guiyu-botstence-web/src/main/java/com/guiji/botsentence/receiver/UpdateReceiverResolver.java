package com.guiji.botsentence.receiver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.guiji.component.result.Result;
import com.guiji.user.dao.entity.SysRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.guiji.auth.api.IAuth;
import com.guiji.botsentence.constant.Constant;
import com.guiji.botsentence.dao.BotAvailableTemplateMapper;
import com.guiji.botsentence.dao.BotPublishSentenceLogMapper;
import com.guiji.botsentence.dao.BotSentenceProcessMapper;
import com.guiji.botsentence.dao.entity.BotAvailableTemplate;
import com.guiji.botsentence.dao.entity.BotAvailableTemplateExample;
import com.guiji.botsentence.dao.entity.BotPublishSentenceLog;
import com.guiji.botsentence.dao.entity.BotSentenceProcess;
import com.guiji.botsentence.dao.entity.BotSentenceProcessExample;
import com.guiji.botsentence.dao.ext.VoliceInfoExtMapper;
import com.guiji.common.model.process.ProcessTypeEnum;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.dispatch.api.IDispatchPlanOut;
import com.guiji.guiyu.message.model.PublishBotstenceResultMsgVO;
import com.guiji.user.dao.entity.SysUser;

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
	@Autowired
	IAuth iAuth;
	@Autowired
	BotAvailableTemplateMapper botAvailableTemplateMapper;

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
			logger.info("部署失败UpdateReceiverVo[{}]",vo);
			BotSentenceProcessExample example=new BotSentenceProcessExample();
			example.createCriteria().andTemplateIdEqualTo(tempId);
			List<BotSentenceProcess> list = botSentenceProcessMapper.selectByExample(example);
			BotSentenceProcess botSentenceProcess =list.get(0);
			botSentenceProcess.setState(Constant.ERROR);//部署失败
		    botSentenceProcessMapper.updateByPrimaryKeySelective(botSentenceProcess);
		    
		    BotPublishSentenceLog record=new BotPublishSentenceLog();
		    Long id=botPublishSentenceLogMapper.getLastPublishSentence(tempId);
		    record.setId(id);
		    record.setStatus("3");
		    botPublishSentenceLogMapper.updateByPrimaryKeySelective(record);
		    
		}
		
		if(vo.getSellbot()==0 && vo.getRobot()==0 && vo.getFreeswitch()==0){
				logger.info("BotSentenceProcessExample----start");
			    logger.info("部署成功UpdateReceiverVo[{}]",vo);
				
				BotSentenceProcessExample example=new BotSentenceProcessExample();
				example.createCriteria().andTemplateIdEqualTo(tempId);
				List<BotSentenceProcess> list = botSentenceProcessMapper.selectByExample(example);
				BotSentenceProcess botSentenceProcess =list.get(0);
				botSentenceProcess.setState(Constant.APPROVE_ONLINE);//已上线
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
			    //如果没有当前话术，则新增 add by zhangpeng 20190220
			    BotAvailableTemplateExample botAvailableTemplateExample = new BotAvailableTemplateExample();
			    botAvailableTemplateExample.createCriteria().andUserIdEqualTo(Long.valueOf(botSentenceProcess.getCrtUser())).andTemplateIdEqualTo(tempId);
			    int count = botAvailableTemplateMapper.countByExample(botAvailableTemplateExample);
			    if(count == 0) {
			    	botPublishSentenceLogMapper.insertAvailableTemplate(botAvailableTemplate);	
			    }
			    //botPublishSentenceLogMapper.deleteAvailableTemplate(botAvailableTemplate);
			    //botPublishSentenceLogMapper.insertAvailableTemplate(botAvailableTemplate);
//			    
//			    //清空volice的【新增】和【修改】
				voliceInfoExtMapper.updateVoliceFlag(botSentenceProcess.getProcessId());

				//企业管理员创建的话术，部署成功后，将话术这个模板配置给这个企业管理员
			    addSentenceTouser(Long.valueOf(botSentenceProcess.getCrtUser()),String.valueOf(botAvailableTemplate.getId()));

				logger.info("UpdateReceiverResolver---end");
			}
		logger.info("resolver----end");
	}

	public void addSentenceTouser (Long userid, String availableId){
		//企业管理员创建的话术，部署成功后，将话术这个模板配置给这个企业管理员
		logger.info("userid[{}]进入部署成功自动分配话术方法availableId[{}]",userid,availableId);
		Result.ReturnData<List<SysRole>> result =  iAuth.getRoleByUserId(userid);
		List<SysRole> listRole = result.getBody();
		if(listRole!=null && listRole.size()>0){
			for(SysRole sysRole:listRole){
				if(sysRole.getId()==3){
					botAvailableTemplateMapper.addUserAvailableTemplateAuto(userid,availableId);
					logger.info("[{}]是企业管理员，部署成功后将话术availableId[{}]分配给他",userid,availableId);
					break;
				}
			}
		}
	}

}
