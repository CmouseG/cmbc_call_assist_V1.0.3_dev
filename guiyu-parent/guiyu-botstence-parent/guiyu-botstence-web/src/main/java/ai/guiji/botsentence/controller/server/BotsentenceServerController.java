package ai.guiji.botsentence.controller.server;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import ai.guiji.botsentence.constant.Constant;
import ai.guiji.botsentence.controller.server.vo.AccountInfoVO;
import ai.guiji.botsentence.controller.server.vo.BotSentenceTemplateIndustryVO;
import ai.guiji.botsentence.dao.BotSentenceIndustryMapper;
import ai.guiji.botsentence.dao.BotSentenceProcessMapper;
import ai.guiji.botsentence.dao.BotSentenceTemplateMapper;
import ai.guiji.botsentence.dao.BotSentenceTtsBackupMapper;
import ai.guiji.botsentence.dao.BotSentenceTtsContentMapper;
import ai.guiji.botsentence.dao.BotSentenceTtsTaskMapper;
import ai.guiji.botsentence.dao.UserAccountIndustryRelationMapper;
import ai.guiji.botsentence.dao.UserAccountMapper;
import ai.guiji.botsentence.dao.VoliceInfoMapper;
import ai.guiji.botsentence.dao.entity.BotSentenceIndustry;
import ai.guiji.botsentence.dao.entity.BotSentenceIndustryExample;
import ai.guiji.botsentence.dao.entity.BotSentenceProcess;
import ai.guiji.botsentence.dao.entity.BotSentenceProcessExample;
import ai.guiji.botsentence.dao.entity.BotSentenceTemplate;
import ai.guiji.botsentence.dao.entity.BotSentenceTemplateExample;
import ai.guiji.botsentence.dao.entity.BotSentenceTtsBackup;
import ai.guiji.botsentence.dao.entity.BotSentenceTtsContent;
import ai.guiji.botsentence.dao.entity.BotSentenceTtsTask;
import ai.guiji.botsentence.dao.entity.UserAccount;
import ai.guiji.botsentence.dao.entity.UserAccountExample;
import ai.guiji.botsentence.dao.entity.UserAccountIndustryRelation;
import ai.guiji.botsentence.dao.entity.UserAccountIndustryRelationExample;
import ai.guiji.botsentence.dao.entity.VoliceInfo;
import ai.guiji.botsentence.dao.ext.VoliceInfoExtMapper;
import ai.guiji.botsentence.service.impl.BotSentenceProcessServiceImpl;
import ai.guiji.botsentence.service.impl.BotSentenceTemplateServiceImpl;
import ai.guiji.botsentence.service.impl.BotSentenceTtsServiceImpl;
import ai.guiji.botsentence.util.HttpRequestUtils;
import ai.guiji.botsentence.vo.RequestCrmVO;
import ai.guiji.botsentence.vo.ResponseCrmVO;
import ai.guiji.component.client.config.JsonParam;
import ai.guiji.component.client.util.BeanUtil;
import ai.guiji.component.exception.CommonException;
import ai.guiji.component.model.ServerResult;
import ai.guiji.user.dao.SysUserMapper;
import ai.guiji.user.dao.SysUserRoleMapper;
import ai.guiji.user.dao.entity.SysUser;
import ai.guiji.user.dao.entity.SysUserRole;
import ai.guiji.user.dao.entity.SysUserRoleExample;

/**
 * 
 * @Description:话术制作对外提供服务类
 * @author 张朋  
 * @date 2018年8月20日  
 *
 */
@RestController
@RequestMapping(value="botsentenceServer")
public class BotsentenceServerController {

	Logger logger = LoggerFactory.getLogger(BotsentenceServerController.class);
	
	@Autowired
	private UserAccountMapper userAccountMapper;
	
	@Autowired
	private UserAccountIndustryRelationMapper relationMapper;
	
	@Autowired
	private SysUserMapper userMapper;
	
	@Autowired
	private SysUserRoleMapper userRoleMapper;
	
	@Autowired
	private BotSentenceIndustryMapper botSentenceIndustryMapper;
	
	@Autowired
	private BotSentenceTemplateMapper botSentenceTemplateMapper;
	
	@Autowired
	private BotSentenceProcessMapper botSentenceProcessMapper;
	
	@Autowired
	private VoliceInfoExtMapper voliceInfoExtMapper;
	
	@Autowired
	private BotSentenceTtsTaskMapper botSentenceTtsTaskMapper;
	
	@Autowired
	private BotSentenceTtsContentMapper botSentenceTtsContentMapper;
	
	/**
	 * CRM开户需要同时把开户信息推送至话术平台
	 * @param
	 */
	@RequestMapping(value="openAccount")
	@Transactional
	public ServerResult<String> openAccount(@JsonParam AccountInfoVO requestParam) {
		logger.info("账户开立...");
		logger.info(requestParam.toString());
		
		if(StringUtils.isBlank(requestParam.getAccountNo())) {
			return ServerResult.createByErrorMessage("增加账号失败，账号为空!");
		}
		/*if(StringUtils.isBlank(requestParam.getMachineCode())) {
			return ServerResult.createByErrorMessage("增加账号失败，机器码为空!");
		}*/
		if(StringUtils.isBlank(requestParam.getHost())) {
			return ServerResult.createByErrorMessage("增加账号失败，域名为空!");
		}
		if(StringUtils.isBlank(requestParam.getGroupId())) {
			return ServerResult.createByErrorMessage("增加账号失败，权限为空!");
		}
		
		String userId = requestParam.getHost()+ "-" +requestParam.getAccountNo();
		
		UserAccount userAccount = new UserAccount();
		
		UserAccountExample example = new UserAccountExample();
		example.createCriteria().andAccountNoEqualTo(userId);
		List<UserAccount> list = userAccountMapper.selectByExample(example);
		if(null != list && list.size() > 0) {
			logger.info("当前账号已存在，更新机器码信息...");
			userAccount = list.get(0);
			userAccount.setMachineCode(requestParam.getMachineCode());
			userAccount.setLstUpdateTime(new Date(System.currentTimeMillis()));
			userAccount.setLstUpdateUser("crm");
			userAccount.setFullHost(requestParam.getFullHost());
			userAccountMapper.updateByPrimaryKey(userAccount);
			
			SysUserRoleExample userRoleExample = new SysUserRoleExample();
			userRoleExample.createCriteria().andUserIdEqualTo(userId);
			List<SysUserRole> userRoleList = userRoleMapper.selectByExample(userRoleExample);
			if(null != userRoleList && userRoleList.size() > 0) {
				SysUserRole userRole = userRoleList.get(0);
				if("1".equals(requestParam.getGroupId())) {//审核权限
					userRole.setRoleId("002");
				}else if("3".equals(requestParam.getGroupId())) {//制作话术权限
					userRole.setRoleId("001");
				}
				userRoleMapper.updateByPrimaryKey(userRole);
			}
			
			
		}else {
			//创建用户信息
			SysUser user = new SysUser();
			user.setUserid(userId);
			user.setPassword(Constant.DEFAULT_PASSWORD);
			user.setTokenPassword(Constant.DEFAULT_PASSWORD);
			user.setName(requestParam.getAccountName());
			user.setCreateTime(new Date(System.currentTimeMillis()));
			userMapper.insert(user);
			
			//创建用户角色
			SysUserRole role = new SysUserRole();
			if("1".equals(requestParam.getGroupId())) {//审核权限
				role.setRoleId("002");
			}else if("3".equals(requestParam.getGroupId())) {//制作话术权限
				role.setRoleId("001");
			}
			
			role.setUserId(userId);
			role.setCreateTime(new Date(System.currentTimeMillis()));
			role.setCreateBy("crm");
			userRoleMapper.insert(role);
			
			logger.info("当前账号不存在，新增账号信息...");
			userAccount.setMachineCode(requestParam.getMachineCode());
			userAccount.setAccountNo(userId);
			userAccount.setCrtTime(new Date(System.currentTimeMillis()));
			userAccount.setHost(requestParam.getHost());
			userAccount.setFullHost(requestParam.getFullHost());
			userAccount.setCrtUser("crm");
			userAccountMapper.insert(userAccount);
		}
		
		//删除当前账号与行业关系
		UserAccountIndustryRelationExample relationExample = new UserAccountIndustryRelationExample();
		relationExample.createCriteria().andAccountNoEqualTo(userId);
		relationMapper.deleteByExample(relationExample);
		
		//新增账号与行业关系信息
		if(null != requestParam.getList() && requestParam.getList().size() > 0) {
			for(BotSentenceTemplateIndustryVO industry : requestParam.getList()) {
				UserAccountIndustryRelation relation = new UserAccountIndustryRelation();
				relation.setAccountNo(userId);
				relation.setAccountName(requestParam.getAccountName());
				relation.setIndustryId(industry.getIndustryId());//实际为话术模板流程编号
				relation.setIndustryName(industry.getIndustryName());//实际为话术模板名称
				relation.setCrtTime(new Date(System.currentTimeMillis()));
				relation.setCrtUser("crm");
				relationMapper.insert(relation);
			}
		}
		
		
		
		logger.info("更新/添加账号【"+userId+"】成功...");
		
		return ServerResult.createBySuccess("更新/增加账户成功", userAccount.getUserId());
	}
	
	/**
	 * 获取行业列表
	 * @param accountNo
	 */
	@RequestMapping(value="getIndustryList")
	public ServerResult<List<BotSentenceTemplateIndustryVO>> getIndustryList(@JsonParam String accountNo) {
		
		List<BotSentenceTemplateIndustryVO> results = new ArrayList<>();
		
		if(StringUtils.isNotBlank(accountNo)) {
			
			UserAccountIndustryRelationExample example = new UserAccountIndustryRelationExample();
			example.createCriteria().andAccountNoEqualTo(accountNo);
			List<UserAccountIndustryRelation> relationList = relationMapper.selectByExample(example);
			if(null != relationList && relationList.size() > 0) {
				for(UserAccountIndustryRelation rela : relationList) {
					//根据行业查询模板列表
					BotSentenceTemplateExample example2 = new BotSentenceTemplateExample();
					example2.createCriteria().andIndustryNameEqualTo(rela.getIndustryName());
					List<BotSentenceTemplate> list = botSentenceTemplateMapper.selectByExample(example2);
					if(null != list && list.size() > 0) {
						for(BotSentenceTemplate template : list) {
							BotSentenceTemplateIndustryVO vo = new BotSentenceTemplateIndustryVO();
							vo.setIndustryId(template.getProcessId());
							vo.setIndustryName(template.getTemplateName());
							results.add(vo);
						}
					}
				}
			}
			
			return ServerResult.createBySuccess(results);
		}else {
			BotSentenceTemplateExample example2 = new BotSentenceTemplateExample();
			List<BotSentenceTemplate> list = botSentenceTemplateMapper.selectByExample(example2);
			
			if(null != list && list.size() > 0) {
				for(BotSentenceTemplate template : list) {
					BotSentenceTemplateIndustryVO vo = new BotSentenceTemplateIndustryVO();
					vo.setIndustryId(template.getProcessId());
					vo.setIndustryName(template.getTemplateName());
					results.add(vo);
				}
			}
			
			return ServerResult.createBySuccess(results);
		}
	}
	
	
	/**
	 * 回调自动化部署状态
	 * @param
	 */
	@RequestMapping(value="autoDeployCallback")
	public void autoDeployCallback(@JsonParam String templateId) {
		logger.info("接收自动化参数: " + templateId);
		BotSentenceProcessExample example1 = new BotSentenceProcessExample();
		example1.createCriteria().andTemplateIdEqualTo(templateId+"_en").andStateEqualTo(Constant.DEPLOYING);
		List<BotSentenceProcess> list1 = botSentenceProcessMapper.selectByExample(example1);
		if(null != list1 && list1.size() > 0) {
			BotSentenceProcess botSentenceProcess = list1.get(0);
			botSentenceProcess.setState(Constant.APPROVE_ONLINE);
			botSentenceProcess.setLstUpdateTime(new Date(System.currentTimeMillis()));
			botSentenceProcess.setLstUpdateUser("agent");
			int version = new Integer(botSentenceProcess.getVersion().trim()) + 1;
			botSentenceProcess.setVersion(version+"");
			botSentenceProcess.setState(Constant.APPROVE_ONLINE);//已上线
		    
		    
			//清空volice的【新增】和【修改】
			voliceInfoExtMapper.updateVoliceFlag(botSentenceProcess.getProcessId());
			logger.info("清空录音flag标志...");
			
		    //推送数据到相应的crm
		    //获取URL
		    String accountNo = botSentenceProcess.getAccountNo();
		    UserAccountExample example = new UserAccountExample();
			example.createCriteria().andAccountNoEqualTo(accountNo);
			List<UserAccount> list = userAccountMapper.selectByExample(example);
			if(null != list && list.size() > 0) {
				String fullHost = list.get(0).getFullHost();
				String host = list.get(0).getHost();
				
				 RequestCrmVO req = new RequestCrmVO();
			    req.setDes(botSentenceProcess.getTemplateName());
			    req.setFile("");
			    req.setIs_tts("0");
			    req.setIs_visit("0");
			    req.setKey_str(botSentenceProcess.getTemplateId());
			    req.setName(botSentenceProcess.getTemplateName());
			    req.setUsername(botSentenceProcess.getAccountNo().split(host+"-")[1]);
			    
			    logger.info("请求参数: " + req.toString());
				
				
				String key = DigestUtils.md5Hex(fullHost);
				String url = "http://" + fullHost + "/api/trans_temp.php?key=" + key;
				String jsonResult;
				try {
					jsonResult = HttpRequestUtils.httpPost(url, BeanUtil.bean2Map(req));
					logger.info("返回参数: " + jsonResult);
					Gson gson = new Gson();
					ResponseCrmVO rsp = gson.fromJson(jsonResult, ResponseCrmVO.class);
					if(null != rsp && "0".equals(rsp.getCode())) {
						logger.info("推送crm数据成功...");
					}else {
						logger.error("推送数据异常，请联系管理员!", rsp.getErrmsg());
						throw new CommonException("推送数据异常，请联系管理员!");
					}
					
				} catch (UnsupportedEncodingException e) {
					logger.error("推送crm数据异常...", e);
					throw new CommonException("推送数据异常，请联系管理员!");
				}
			}
			botSentenceProcessMapper.updateByPrimaryKeySelective(botSentenceProcess);
		}
		
	}
	
	
	/**
	 * 回调TTS合成
	 * @param id
	 */
	@RequestMapping(value="generateTTSCallback")
	@Transactional
	public ServerResult generateTTSCallback(@JsonParam String id, @JsonParam String url) {
		logger.info("接收TTS合成回调参数: " + id);
		logger.info("接收TTS合成回调参数: " + url);
		if(StringUtils.isNotBlank(id) && StringUtils.isNotBlank(url)) {
			BotSentenceTtsTask ttsTask = botSentenceTtsTaskMapper.selectByPrimaryKey(new Long(id));
			if(null != ttsTask) {
				logger.info("更新任务表状态及url");
				ttsTask.setVoliceUrl(url);
				ttsTask.setStatus(Constant.TTS_TASK_FINISH);
				botSentenceTtsTaskMapper.updateByPrimaryKey(ttsTask);
				
				logger.info("新增TTS合成内容记录");
				BotSentenceTtsContent record = new BotSentenceTtsContent();
				record.setContent(ttsTask.getContent());
				record.setCrtTime(new Date(System.currentTimeMillis()));
				record.setCrtUser("tts");
				record.setSoundType(ttsTask.getSoundType());
				record.setUrl(url);
				botSentenceTtsContentMapper.insert(record);
				
				/*
				if("01".equals(ttsTask.getBusiType())) {
					//更新volice url
					VoliceInfo volice = voliceInfoMapper.selectByPrimaryKey(new Long(ttsTask.getBusiId()));
					if(!url.equals(volice.getVoliceUrl())) {
						//判断是否合成录音，如果是合成录音，则不更新URL
						if(botSentenceTtsService.validateContainParam(volice.getContent())){
							logger.info("当前录音需要TTS合成，不需要更新录音 URL");
						}else {
							volice.setVoliceUrl(url);
							volice.setLstUpdateTime(new Date(System.currentTimeMillis()));
							volice.setLstUpdateUser("tts");
							voliceInfoMapper.updateByPrimaryKeySelective(volice);
							logger.info("更新TTS合成URL成功...");
						}
						
					}
				}else if("02".equals(ttsTask.getBusiType())) {
					//更新backup url
					BotSentenceTtsBackup backup = botSentenceTtsBackupMapper.selectByPrimaryKey(ttsTask.getBusiId());
					if(!url.equals(backup.getUrl())) {
						backup.setUrl(url);
						backup.setLstUpdateTime(new Date(System.currentTimeMillis()));
						backup.setLstUpdateUser("tts");
						botSentenceTtsBackupMapper.updateByPrimaryKeySelective(backup);
						logger.info("更新备用文案TTS合成URL成功...");
					}
				}*/
				
			}
		}
		return ServerResult.createBySuccess();
	}
}
