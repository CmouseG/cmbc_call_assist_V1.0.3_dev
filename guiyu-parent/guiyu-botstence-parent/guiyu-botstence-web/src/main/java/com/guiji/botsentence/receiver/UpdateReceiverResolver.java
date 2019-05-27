package com.guiji.botsentence.receiver;

import com.guiji.auth.api.IAuth;
import com.guiji.botsentence.constant.Constant;
import com.guiji.botsentence.dao.BotAvailableTemplateMapper;
import com.guiji.botsentence.dao.BotPublishSentenceLogMapper;
import com.guiji.botsentence.dao.BotSentenceDeployMapper;
import com.guiji.botsentence.dao.BotSentenceProcessMapper;
import com.guiji.botsentence.dao.entity.*;
import com.guiji.botsentence.dao.ext.BotSentenceDeployExtMapper;
import com.guiji.botsentence.dao.ext.VoliceInfoExtMapper;
import com.guiji.botsentence.util.enums.SpeechAuditStatusEnum;
import com.guiji.component.result.Result;
import com.guiji.guiyu.message.model.PublishBotstenceResultMsgVO;
import com.guiji.user.dao.entity.SysRole;
import com.guiji.utils.RedisUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Component
public class UpdateReceiverResolver {
	
	protected static Logger logger=LoggerFactory.getLogger(UpdateReceiverResolver.class);
	
	@Resource
	private BotSentenceDeployExtMapper botSentenceDeployExtMapper;
	
	@Resource
	private VoliceInfoExtMapper voliceInfoExtMapper;
	
	@Resource
	private BotSentenceProcessMapper botSentenceProcessMapper;
	
	@Resource
	private BotPublishSentenceLogMapper botPublishSentenceLogMapper;

	@Resource
	private IAuth iAuth;

	@Resource
	private BotAvailableTemplateMapper botAvailableTemplateMapper;

	@Resource
	private BotSentenceDeployMapper botSentenceDeployMapper;

	@Resource
	private RedisUtil redisUtil;

	private static final String BOTSTENCE_DEPLOY_JOB_ID = "BOTSTENCE_DEPLOY_JOB_ID_";

	@Transactional
	public void resolver(PublishBotstenceResultMsgVO param){
		logger.info("resolver---start");
		logger.info("接收部署参数: " + param.toString());
		String tempId=param.getTmplId();
		String subJobId = param.getSubJobId();
		//根据模板号和子任务号查询发布任务记录

		boolean flg = true;
		int threadTimeCount = 1;
		while (flg)
		{
			String redisValue = redisUtil.get(BOTSTENCE_DEPLOY_JOB_ID+subJobId)== null? "":(String)redisUtil.get(BOTSTENCE_DEPLOY_JOB_ID+subJobId);
			if(StringUtils.isEmpty(redisValue))
			{
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					logger.error("系统等待5秒异常...", e);
				}

				threadTimeCount++;

				if(threadTimeCount >=100)
				{
					flg = false;
					return;
				}

				continue;
			}
			else
			{
				String jobValue = redisUtil.get(BOTSTENCE_DEPLOY_JOB_ID+redisValue)== null? "":(String)redisUtil.get(BOTSTENCE_DEPLOY_JOB_ID+redisValue);
				if(StringUtils.equals(jobValue, "false"))
				{
					flg = false;
					return;
				}
				else
				{
					flg = false;
					break;
				}
			}
		}
			
			BotSentenceDeployExample deployExample = new BotSentenceDeployExample();
			deployExample.createCriteria().andTemplateIdEqualTo(tempId).andSubJobIdEqualTo(subJobId);
			List<BotSentenceDeploy> deployList = botSentenceDeployMapper.selectByExample(deployExample);
			if(null != deployList && deployList.size() > 0) {
				BotSentenceDeploy deploy = deployList.get(0);
				deploy.setStatus(param.getResult().toString());
				botSentenceDeployMapper.updateByPrimaryKey(deploy);
				
				//如果当前是失败，则设置话术流程状态为部署失败
				if(1 == param.getResult()) {
					logger.info("当前发布任务: " + subJobId + "部署失败,设置话术流程状态为部署失败");
					BotSentenceProcess botSentenceProcess = botSentenceProcessMapper.selectByPrimaryKey(deploy.getProcessId());
					botSentenceProcess.setState(Constant.ERROR);//部署失败
					botSentenceProcess.setLstUpdateUser("deploy");
					botSentenceProcess.setLstUpdateTime(new Date(System.currentTimeMillis()));
				    botSentenceProcessMapper.updateByPrimaryKeySelective(botSentenceProcess);
				    
				    BotPublishSentenceLog record=new BotPublishSentenceLog();
				    Long id=botPublishSentenceLogMapper.getLastPublishSentence(tempId);
				    record.setId(id);
				    record.setStatus("3");
				    botPublishSentenceLogMapper.updateByPrimaryKeySelective(record);

					redisUtil.set(BOTSTENCE_DEPLOY_JOB_ID+deploy.getJobId(),"false",24*60*60);

				}else if(0 == param.getResult()) {//如果当前任务是成功
					logger.info("当前发布任务: " + subJobId + "部署成功...");
				    
				  //如果全部部署成功，则设置状态为部署成功
					BotSentenceDeployExample deployExample1 = new BotSentenceDeployExample();
					deployExample1.createCriteria().andTemplateIdEqualTo(tempId).andStatusEqualTo("1").andJobIdEqualTo(deploy.getJobId());
					int num = botSentenceDeployMapper.countByExample(deployExample1);
					if(num == 0) {
						logger.info("当前话术: " + tempId + "部署成功...");
						
						BotSentenceProcess botSentenceProcess = botSentenceProcessMapper.selectByPrimaryKey(deploy.getProcessId());
						botSentenceProcess.setState(SpeechAuditStatusEnum.ONLINE.getKey());//已上线
						int version = botSentenceDeployExtMapper.countVersionByTemplateId(tempId);
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
					    List<BotAvailableTemplate> list = botAvailableTemplateMapper.selectByExample(botAvailableTemplateExample);
					    if(null != list && list.size() > 0) {
					    	botAvailableTemplate = list.get(0);
					    }else {
					    	botPublishSentenceLogMapper.insertAvailableTemplate(botAvailableTemplate);	
					    }
					    //清空volice的【新增】和【修改】
						voliceInfoExtMapper.updateVoliceFlag(botSentenceProcess.getProcessId());

						//企业管理员创建的话术，部署成功后，将话术这个模板配置给这个企业管理员
					    addSentenceTouser(Long.valueOf(botSentenceProcess.getCrtUser()),String.valueOf(botAvailableTemplate.getId()));

						logger.info("UpdateReceiverResolver---end");
					}
				}
			}else {
				logger.info("当前发布任务: " + subJobId + "不存在，忽略...");
			}
		logger.info("resolver----end");
	}

	private void addSentenceTouser (Long userid, String availableId){
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

	private int getVersion(String templateId){

		BotSentenceDeployExample deployExample = new BotSentenceDeployExample();
		deployExample.setDistinct(true);




		return 0;
	}

}
