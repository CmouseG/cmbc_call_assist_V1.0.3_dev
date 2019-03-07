package com.guiji.botsentence.controller.server;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.guiji.botsentence.api.entity.BotSentenceTradeVO;
import com.guiji.botsentence.constant.Constant;
import com.guiji.botsentence.controller.server.vo.BotSentenceTemplateIndustryVO;
import com.guiji.botsentence.controller.server.vo.BotSentenceTemplateTradeVO;
import com.guiji.botsentence.dao.BotSentenceIndustryMapper;
import com.guiji.botsentence.dao.BotSentenceProcessMapper;
import com.guiji.botsentence.dao.BotSentenceTemplateMapper;
import com.guiji.botsentence.dao.BotSentenceTradeMapper;
import com.guiji.botsentence.dao.BotSentenceTtsBackupMapper;
import com.guiji.botsentence.dao.BotSentenceTtsContentMapper;
import com.guiji.botsentence.dao.BotSentenceTtsTaskMapper;
import com.guiji.botsentence.dao.VoliceInfoMapper;
import com.guiji.botsentence.dao.entity.BotSentenceProcess;
import com.guiji.botsentence.dao.entity.BotSentenceProcessExample;
import com.guiji.botsentence.dao.entity.BotSentenceTemplateExample;
import com.guiji.botsentence.dao.entity.BotSentenceTrade;
import com.guiji.botsentence.dao.entity.BotSentenceTradeExample;
import com.guiji.botsentence.dao.entity.BotSentenceTtsBackup;
import com.guiji.botsentence.dao.entity.BotSentenceTtsTask;
import com.guiji.botsentence.dao.entity.BotSentenceTtsTaskExample;
import com.guiji.botsentence.dao.entity.VoliceInfo;
import com.guiji.botsentence.dao.ext.VoliceInfoExtMapper;
import com.guiji.botsentence.service.IWeChatAppletService;
import com.guiji.botsentence.service.impl.BotSentenceProcessServiceImpl;
import com.guiji.botsentence.service.impl.BotSentenceTtsServiceImpl;
import com.guiji.botsentence.service.impl.VoliceServiceImpl;
import com.guiji.botsentence.util.BotSentenceUtil;
import com.guiji.botsentence.util.HttpRequestUtils;
import com.guiji.botsentence.vo.RequestCrmVO;
import com.guiji.botsentence.vo.ResponseCrmVO;
import com.guiji.common.exception.CommonException;
import com.guiji.common.exception.GuiyuException;
import com.guiji.component.client.config.JsonParam;
import com.guiji.component.client.util.BeanUtil;
import com.guiji.component.result.Result;
import com.guiji.component.result.ServerResult;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.user.dao.SysUserMapper;
import com.guiji.user.dao.entity.SysUser;

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
	private SysUserMapper userMapper;
	
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
	
	@Autowired
	private IWeChatAppletService weChatAppletService;
	
	@Autowired
	private VoliceServiceImpl voliceService;
	
	@Autowired
	private VoliceInfoMapper voliceInfoMapper;
	
	@Autowired
	private BotSentenceTtsBackupMapper botSentenceTtsBackupMapper;
	
	@Autowired
	private BotSentenceTtsServiceImpl botSentenceTtsService;
	
	@Autowired
	private BotSentenceTradeMapper botSentenceTradeMapper;
	
	@Autowired
	private BotSentenceProcessServiceImpl botSentenceProcessService;
	
	/**
	 * CRM开户需要同时把开户信息推送至话术平台
	 * @param requestNo
	 */
	/*@RequestMapping(value="openAccount")
	@Transactional
	public ServerResult<String> openAccount(@JsonParam AccountInfoVO requestParam) {
		
		logger.info("账户开立...");
		logger.info(requestParam.toString());
		
		if(StringUtils.isBlank(requestParam.getAccountNo())) {
			return ServerResult.createByErrorMessage("增加账号失败，账号为空!");
		}
		if(StringUtils.isBlank(requestParam.getMachineCode())) {
			return ServerResult.createByErrorMessage("增加账号失败，机器码为空!");
		}
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
			//判断用户是否存在
			SysUserExample sysUserExample = new SysUserExample();
			sysUserExample.createCriteria().andUpUseridEqualTo(userId);
			int userNum = userMapper.countByExample(sysUserExample);
			if(userNum == 0) {
				//创建用户信息
				SysUser user = new SysUser();
				user.setUserid(userId);
				user.setPassword(Constant.DEFAULT_PASSWORD);
				user.setTokenPassword(Constant.DEFAULT_PASSWORD);
				user.setName(userId);
				user.setCreateTime(new Date(System.currentTimeMillis()));
				userMapper.insert(user);
			}
			
			
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
			}else {
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
			}
			
			
			
			logger.info("当前账号不存在，新增账号信息...");
			userAccount.setMachineCode(requestParam.getMachineCode());
			userAccount.setAccountNo(userId);
			userAccount.setCrtTime(new Date(System.currentTimeMillis()));
			userAccount.setHost(requestParam.getHost());
			userAccount.setFullHost(requestParam.getFullHost());
			userAccount.setCrtUser("crm");
			userAccountMapper.insert(userAccount);
			
			//新增小程序账号
			weChatAppletService.saveAccount(userId);
		}
		
		//删除当前账号与行业关系
		UserAccountIndustryRelationExample relationExample = new UserAccountIndustryRelationExample();
		relationExample.createCriteria().andAccountNoEqualTo(userId);
		relationMapper.deleteByExample(relationExample);
		UserAccountTradeRelationExample relationExample = new UserAccountTradeRelationExample();
		relationExample.createCriteria().andAccountNoEqualTo(userId);
		userAccountTradeRelationMapper.deleteByExample(relationExample);
		
		//新增账号与行业关系信息
		String tradeStr = "";
		if(null != requestParam.getList() && requestParam.getList().size() > 0) {
			for(BotSentenceTemplateIndustryVO industry : requestParam.getList()) {
				tradeStr = tradeStr + industry.getIndustryId() + ",";
			}
			logger.info("当前用户绑定行业: " + tradeStr);
			UserAccountTradeRelation relation = new UserAccountTradeRelation();
			relation.setAccountNo(userId);
			relation.setAccountName(userId);
			relation.setIndustryId(tradeStr.substring(0, tradeStr.length() - 1));
			relation.setCrtTime(new Date(System.currentTimeMillis()));
			relation.setCrtUser("crm");
			userAccountTradeRelationMapper.insert(relation);
		}
		
		logger.info("更新/添加账号【"+userId+"】成功...");
		
		return ServerResult.createBySuccess("更新/增加账户成功", userAccount.getUserId());
	}
	*/
	/**
	 * 获取二级行业列表
	 */
	@RequestMapping(value="getIndustryList")
	public ServerResult<List<BotSentenceTrade>> getIndustryList() {
		
		List<BotSentenceTrade> results = new ArrayList<>();
		
		BotSentenceTradeExample childExample = new BotSentenceTradeExample();
		childExample.createCriteria().andLevelEqualTo(2);
		results = botSentenceTradeMapper.selectByExample(childExample);
		
		//设置行业名称
		for(BotSentenceTrade trade : results) {
			trade.setIndustryName(trade.getParentName() + "_" + trade.getIndustryName());
		}
		return ServerResult.createBySuccess(results);
	}
	
	
	/**
	 * 回调自动化部署状态
	 * @param templat_id
	 */
	/*@RequestMapping(value="autoDeployCallback")
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
		    
		    //判断当前模板是否需要TTS合成
			BotSentenceTtsTaskExample ttsExample = new BotSentenceTtsTaskExample();
			ttsExample.createCriteria().andProcessIdEqualTo(botSentenceProcess.getProcessId());
			int num = botSentenceTtsTaskMapper.countByExample(ttsExample);
			
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
			    if(num > 0) {
			    	req.setIs_tts("1");	
			    }
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
		
	}*/
	
	
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
				ttsTask.setVoliceUrl(url);
				ttsTask.setStatus(Constant.TTS_FINISH);
				botSentenceTtsTaskMapper.updateByPrimaryKey(ttsTask);
				
				if("01".equals(ttsTask.getBusiType()) || "03".equals(ttsTask.getBusiType())) {
					//更新volice url
					VoliceInfo volice = voliceService.getVoliceInfo(new Long(ttsTask.getBusiId()));
					if(!url.equals(volice.getVoliceUrl())) {
						//判断是否合成录音，如果是合成录音，则不更新URL
						if(botSentenceTtsService.validateContainParam(volice.getContent())){
							logger.info("当前录音需要TTS合成，不需要更新录音 URL");
							volice.setVoliceUrl(null);
						}else {
							volice.setVoliceUrl(url);
							volice.setLstUpdateTime(new Date(System.currentTimeMillis()));
							volice.setLstUpdateUser("tts");
							volice.setNeedTts(false);
						}
						voliceInfoMapper.updateByPrimaryKeySelective(volice);
						logger.info("更新TTS合成URL成功...");
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
				}
				
			}
		}
		return ServerResult.createBySuccess();
	}
	
	
	@RequestMapping(value="queryAllIndustryList")
	public ServerResult<List<BotSentenceTemplateIndustryVO>> queryAllIndustryList() {
		List<BotSentenceTemplateIndustryVO> results = new ArrayList<>();
		BotSentenceTradeExample example = new BotSentenceTradeExample();
		example.createCriteria().andLevelEqualTo(1);
		List<BotSentenceTrade> industryList = botSentenceTradeMapper.selectByExample(example);
		if(null != industryList && industryList.size() > 0) {
			for(BotSentenceTrade industry : industryList) {
				BotSentenceTemplateIndustryVO vo = new BotSentenceTemplateIndustryVO();
				vo.setIndustryId(industry.getIndustryId());
				vo.setIndustryName(industry.getIndustryName());
				
				//查询下级分类
				BotSentenceTradeExample childExample = new BotSentenceTradeExample();
				childExample.createCriteria().andLevelEqualTo(2).andParentIdEqualTo(industry.getIndustryId());
				List<BotSentenceTrade> childIndustryList = botSentenceTradeMapper.selectByExample(childExample);
				if(null != childIndustryList && childIndustryList.size() > 0) {
					List<BotSentenceTemplateIndustryVO> childs = new ArrayList<>();
					for(BotSentenceTrade child : childIndustryList) {
						BotSentenceTemplateIndustryVO childVo = new BotSentenceTemplateIndustryVO();
						childVo.setIndustryId(child.getIndustryId());
						childVo.setIndustryName(child.getIndustryName());
						childs.add(childVo);
					}
					vo.setChilds(childs);
				}
				results.add(vo);
			}
		}
		
		return ServerResult.createBySuccess(results);
		
	}
	
	@RequestMapping(value="queryAllTradeList")
	public ServerResult<List<BotSentenceTemplateTradeVO>> queryAllTradeList() {
		List<BotSentenceTemplateTradeVO> list = botSentenceProcessService.queryAllIndustryList();
		return ServerResult.createBySuccess(list);
	}
	
	
	@RequestMapping(value="queryTradeByTradeId")
	public Result.ReturnData<BotSentenceTradeVO> queryTradeByTradeId(@RequestParam("tradeId") String tradeId) {
		logger.info("根据行业编号{}查询行业信息...", tradeId);
		if(StringUtils.isBlank(tradeId)) {
			throw new GuiyuException("行业编号为空");
		}
		BotSentenceTradeExample example = new BotSentenceTradeExample();
		example.createCriteria().andIndustryIdEqualTo(tradeId);
		List<BotSentenceTrade> list  = botSentenceTradeMapper.selectByExample(example);
		if(null != list && list.size() > 0) {
			BotSentenceTradeVO vo = new BotSentenceTradeVO();
			BeanUtil.copyProperties(list.get(0), vo);
			return Result.ok(vo);
		}else {
			throw new GuiyuException("该行业不存在");
		}
	}
	
	@RequestMapping(value="queryTradeListByTradeIdList")
	public ServerResult<List<BotSentenceTemplateTradeVO>> queryAllTradeList(@RequestParam("tradeIdList")List<String> tradeIdList) {
		logger.info("根据行业编号{}查询行业信息...", tradeIdList.toString());
		List<BotSentenceTemplateTradeVO> list = botSentenceProcessService.queryTradeListByTradeIdList(tradeIdList);
		return ServerResult.createBySuccess(list);
	}
	
}
