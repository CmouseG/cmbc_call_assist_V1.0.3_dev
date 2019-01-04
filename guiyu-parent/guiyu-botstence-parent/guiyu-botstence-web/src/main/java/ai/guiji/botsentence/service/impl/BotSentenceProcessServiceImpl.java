package ai.guiji.botsentence.service.impl;



import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.guiji.auth.api.IAuth;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.user.dao.entity.SysUser;

import ai.guiji.botsentence.constant.Constant;
import ai.guiji.botsentence.dao.BotPublishSentenceLogMapper;
import ai.guiji.botsentence.dao.BotSentenceAdditionMapper;
import ai.guiji.botsentence.dao.BotSentenceBranchMapper;
import ai.guiji.botsentence.dao.BotSentenceDomainMapper;
import ai.guiji.botsentence.dao.BotSentenceIntentMapper;
import ai.guiji.botsentence.dao.BotSentenceLabelMapper;
import ai.guiji.botsentence.dao.BotSentenceProcessMapper;
import ai.guiji.botsentence.dao.BotSentenceTemplateMapper;
import ai.guiji.botsentence.dao.BotSentenceTtsBackupMapper;
import ai.guiji.botsentence.dao.BotSentenceTtsTaskMapper;
import ai.guiji.botsentence.dao.entity.BotAvailableTemplate;
import ai.guiji.botsentence.dao.entity.BotSentenceAddition;
import ai.guiji.botsentence.dao.entity.BotSentenceBranch;
import ai.guiji.botsentence.dao.entity.BotSentenceBranchExample;
import ai.guiji.botsentence.dao.entity.BotSentenceDomain;
import ai.guiji.botsentence.dao.entity.BotSentenceDomainExample;
import ai.guiji.botsentence.dao.entity.BotSentenceIntent;
import ai.guiji.botsentence.dao.entity.BotSentenceIntentExample;
import ai.guiji.botsentence.dao.entity.BotSentenceLabel;
import ai.guiji.botsentence.dao.entity.BotSentenceLabelExample;
import ai.guiji.botsentence.dao.entity.BotSentenceProcess;
import ai.guiji.botsentence.dao.entity.BotSentenceProcessExample;
import ai.guiji.botsentence.dao.entity.BotSentenceProcessExample.Criteria;
import ai.guiji.botsentence.dao.entity.BotSentenceTemplate;
import ai.guiji.botsentence.dao.entity.BotSentenceTtsBackup;
import ai.guiji.botsentence.dao.entity.BotSentenceTtsBackupExample;
import ai.guiji.botsentence.dao.entity.BotSentenceTtsTask;
import ai.guiji.botsentence.dao.entity.BotSentenceTtsTaskExample;
import ai.guiji.botsentence.dao.entity.BusinessAnswerTaskExt;
import ai.guiji.botsentence.dao.entity.VoliceInfo;
import ai.guiji.botsentence.dao.entity.VoliceInfoExample;
import ai.guiji.botsentence.dao.entity.VoliceInfoExt;
import ai.guiji.botsentence.dao.entity.ext.IntentVO;
import ai.guiji.botsentence.dao.entity.ext.VoliceInfoVO;
import ai.guiji.botsentence.dao.ext.BotSentenceBranchExtMapper;
import ai.guiji.botsentence.dao.ext.BotSentenceDomainExtMapper;
import ai.guiji.botsentence.dao.ext.BotSentenceIntentExtMapper;
import ai.guiji.botsentence.dao.ext.BotSentenceLabelExtMapper;
import ai.guiji.botsentence.dao.ext.BotSentenceProcessExtMapper;
import ai.guiji.botsentence.dao.ext.VoliceInfoExtMapper;
import ai.guiji.botsentence.service.IBotSentenceProcessService;
import ai.guiji.botsentence.util.BotSentenceUtil;
import ai.guiji.botsentence.vo.BotSentenceProcessVO;
import ai.guiji.botsentence.vo.CommonDialogVO;
import ai.guiji.botsentence.vo.DomainVO;
import ai.guiji.botsentence.vo.FlowEdge;
import ai.guiji.botsentence.vo.FlowInfoVO;
import ai.guiji.botsentence.vo.FlowNode;
import ai.guiji.botsentence.vo.LevelVO;
import ai.guiji.botsentence.vo.RefuseBranchVO;
import ai.guiji.component.client.util.BeanUtil;
import ai.guiji.component.client.util.Pinyin4jUtil;
import ai.guiji.component.client.util.SystemUtil;
import ai.guiji.component.exception.CommonException;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

@Service
public class BotSentenceProcessServiceImpl implements IBotSentenceProcessService {

	private Logger logger = LoggerFactory.getLogger(BotSentenceProcessServiceImpl.class);
	
	@Autowired
	private BotSentenceProcessMapper botSentenceProcessMapper;
	
	@Autowired
	private BotSentenceDomainMapper botSentenceDomainMapper;
	
	@Autowired
	private BotSentenceBranchMapper botSentenceBranchMapper;
	
	@Autowired
	private BotSentenceIntentMapper botSentenceIntentMapper;
	
	@Autowired
	private BotSentenceBranchExtMapper botSentenceBranchExtMapper;
	
	@Autowired
	private BotSentenceDomainExtMapper botSentenceDomainExtMapper;
	
	@Autowired
	private BotSentenceIntentExtMapper botSentenceIntentExtMapper;
	
	@Autowired
	private ai.guiji.botsentence.dao.VoliceInfoMapper voliceInfoMapper;
	
	@Autowired
	private VoliceInfoExtMapper voliceInfoExtMapper;
	
	@Autowired
	private BotSentenceTemplateMapper botSentenceTemplateMapper;
	
	@Autowired
	private BotSentenceLabelMapper botSentenceLabelMapper;
	
	@Autowired
	private BotSentenceLabelExtMapper botSentenceLabelExtMapper;
	
	@Autowired
	private BotSentenceAdditionMapper botSentenceAdditionMapper;
	
	@Autowired
	private BusinessAnswerTaskServiceImpl businessAnswerTaskService;
	
	@Autowired
	private BotSentenceTtsTaskMapper botSentenceTtsTaskMapper;
	
	@Autowired
	private BotSentenceTtsServiceImpl botSentenceTtsService;
	
	@Autowired
	private BotSentenceTtsBackupMapper botSentenceTtsBackupMapper;
	
	@Autowired
	private VoliceServiceImpl voliceServiceImpl;

	
	private static final String domain_prefix = "分支";
	private static final String branch_prefix = "special_";
	private static final String line_prefix = "未命名";

	@Override
	public List<BotSentenceProcess> getTemplateByOrgCode(String orgCode) {

		BotSentenceProcessExample example = new BotSentenceProcessExample();
		Criteria criteria = example.createCriteria();
		criteria.andOrgCodeLike(orgCode+"%");
		criteria.andStateNotEqualTo("99");
		return botSentenceProcessMapper.selectByExample(example);
	}

	@Override
	public List<BotSentenceProcess> queryBotSentenceProcessList(int pageSize, int pageNo, String templateName, String accountNo,Long userId) {

		ReturnData<SysUser> data=iAuth.getUserById(userId);
		String orgCode=data.getBody().getOrgCode();
		
		BotSentenceProcessExample example = new BotSentenceProcessExample();
		Criteria criteria = example.createCriteria();
		if(StringUtils.isNotBlank(templateName)) {
			criteria.andTemplateNameLike("%" + templateName + "%");
		}
		
		criteria.andOrgCodeLike(orgCode+"%");
		
		criteria.andStateNotEqualTo("99");
		
		//计算分页
		int limitStart = (pageNo-1)*pageSize;
		int limitEnd = pageSize;
		example.setLimitStart(limitStart);
		example.setLimitEnd(limitEnd);
		example.setOrderByClause(" crt_time desc");
		
		return botSentenceProcessMapper.selectByExample(example);
	}

	@Override
	public int countBotSentenceProcess(String templateName, String accountNo,Long userId) {
		ReturnData<SysUser> data=iAuth.getUserById(userId);
		String orgCode=data.getBody().getOrgCode();
		BotSentenceProcessExample example = new BotSentenceProcessExample();
		Criteria criteria = example.createCriteria();
		
		if(StringUtils.isNotBlank(templateName)) {
			criteria.andTemplateNameLike("%" + templateName + "%");
		}
		criteria.andOrgCodeLike(orgCode+"%");
		criteria.andAccountNoEqualTo(String.valueOf(userId));
		
		criteria.andStateNotEqualTo("99");
		return botSentenceProcessMapper.countByExample(example);
	}

	@Autowired
	private IAuth iAuth;
	
	@Override
	@Transactional
	public String createBotSentenceTemplate(BotSentenceProcessVO paramVO,Long userId) {
		
		ReturnData<SysUser> data=iAuth.getUserById(userId);
		String userName=data.getBody().getUsername();
		String orgCode=paramVO.getOrgCode();
		String orgName=paramVO.getOrgName();
		if(org.springframework.util.StringUtils.isEmpty(paramVO.getOrgCode())){
			orgCode=data.getBody().getOrgCode();
			orgName=data.getBody().getOrgName();
		}
		long time1 = System.currentTimeMillis();
		logger.info("============" + time1);
		if(null == paramVO || StringUtils.isBlank(paramVO.getProcessId())) {
			throw new CommonException("创建模板失败，请求参数为空!");
		}
		//查询已生效的话术模板
		//BotSentenceProcess botSentenceTemplate = botSentenceProcessMapper.selectByPrimaryKey(paramVO.getProcessId());
		BotSentenceTemplate botSentenceTemplate = botSentenceTemplateMapper.selectByPrimaryKey(paramVO.getProcessId());
		if(null == botSentenceTemplate) {
			throw new CommonException("创建模板失败，行业模板不存在!");
		}
		
		//保存话术流程信息
		BotSentenceProcess process = new BotSentenceProcess();
		
		if("00".equals(paramVO.getFlag())) {//创建新话术
			logger.info("创建新话术模板");
			//生成模板编号
			Pinyin4jUtil util= new Pinyin4jUtil();
			String pingyin = "";
			try {
				pingyin = util.toPinYinLowercase(paramVO.getTemplateName());
			} catch (BadHanyuPinyinOutputFormatCombination e) {
				logger.error("生成首字母异常...", e);
				pingyin = SystemUtil.getSysJournalNo(5, false);
			}
			
			String templateId = pingyin + "_" + SystemUtil.getSysJournalNo(5, true) + "_en";
			process.setOrgCode(orgCode);
			process.setUserName(userName);
			process.setOrgName(orgName);
			process.setState("00");//制作中
			process.setVersion("0");//初始版本为0
			process.setAccountNo(userId.toString());//所属账号
			process.setIndustry(botSentenceTemplate.getIndustryName());//所属行业
			process.setTemplateId(templateId);//模板编号
			process.setTemplateName(paramVO.getTemplateName());
			process.setOldProcessId(paramVO.getProcessId());//设置依赖的流程编号
			process.setTemplateType("01");//自定义模板
			logger.info("生成的模板编号为: " + templateId);
		}else if("01".equals(paramVO.getFlag())) {//修改话术
			logger.info("修改话术模板");
			
			//判断当前是否存在制作中的话术
			BotSentenceProcessExample example = new BotSentenceProcessExample();
			example.createCriteria().andTemplateIdEqualTo(botSentenceTemplate.getTemplateId()).andStateEqualTo("00");
			List<BotSentenceProcess> list = botSentenceProcessMapper.selectByExample(example);
			if(null != list && list.size() > 0) {
				throw new CommonException("当前话术已存在一条制作中的记录!");
			}
			
			BeanUtil.copyProperties(botSentenceTemplate, process);
			process.setLstUpdateTime(new Date(System.currentTimeMillis()));
			process.setLstUpdateUser(userId.toString());
			process.setState("00");//制作中
			Integer newVersion = new Integer(botSentenceTemplate.getVersion())+1;
			process.setVersion(newVersion.toString());
			process.setProcessId(null);
			process.setOldProcessId(paramVO.getProcessId());
			
			//更新旧的信息为废弃状态
			BotSentenceProcess oldProcess = botSentenceProcessMapper.selectByPrimaryKey(paramVO.getProcessId());
			oldProcess.setState("99");//废弃状态
			oldProcess.setLstUpdateTime(new Date(System.currentTimeMillis()));
			oldProcess.setLstUpdateUser(userId.toString());
			botSentenceProcessMapper.updateByPrimaryKey(oldProcess);
			logger.info("更新旧的话术流程为废弃状态: " + paramVO.getProcessId());
		}
		process.setCrtTime(new Date(System.currentTimeMillis()));
		process.setCrtUser(userId.toString());
		
		botSentenceProcessMapper.insert(process);
		logger.info("保存话术流程信息成功...");
		logger.info("新生成的话术流程编号是: " + process.getProcessId());
		
		long time2 = System.currentTimeMillis();
		logger.info("======保存话术流程信息耗时======" + (time2-time1));
		
		//保存domain信息
		BotSentenceDomainExample example = new BotSentenceDomainExample();
		example.createCriteria().andProcessIdEqualTo(paramVO.getProcessId());
		List<BotSentenceDomain> domainList = botSentenceDomainMapper.selectByExample(example);
		
		List<BotSentenceDomain> domainList_new = new ArrayList<>();
		if(null != domainList && domainList.size() > 0) {
			for(BotSentenceDomain temp : domainList) {
				BotSentenceDomain vo = new BotSentenceDomain();
				BeanUtil.copyProperties(temp, vo);
				vo.setComDomain(null);//在审批时才需要设置默认主流程
				vo.setTemplateId(process.getTemplateId());
				vo.setCrtTime(new Date(System.currentTimeMillis()));
				vo.setCrtUser(userId.toString());
				vo.setProcessId(process.getProcessId());
				/*if("1".equals(vo.getCategory())) {
					vo.setIsMainFlow("01");
				}*/
				domainList_new.add(vo);
				//botSentenceDomainMapper.insert(vo);
			}
			botSentenceDomainExtMapper.batchInsert(domainList_new);
			logger.info("共保domain信息: " + domainList_new.size());
		}
		
		logger.info("保存domain信息成功...");
		
		long time3 = System.currentTimeMillis();
		logger.info("======保存domain信息耗时======" + (time3-time2));
		
		//保存brach信息
		BotSentenceBranchExample botSentenceBranchExample = new BotSentenceBranchExample();
		botSentenceBranchExample.createCriteria().andProcessIdEqualTo(paramVO.getProcessId());
		List<BotSentenceBranch> branchList = botSentenceBranchMapper.selectByExample(botSentenceBranchExample);
		List<BotSentenceBranch> branchList_new = new ArrayList<>();
		//Map<String, Integer> map = new HashMap<>();
		List<Long> respIdList = new ArrayList<>();
		List<Long> intentIdList = new ArrayList<>();
		
		if(null != branchList && branchList.size() > 0) {
			
			List<VoliceInfoVO> voliceList = new ArrayList<>();
			List<IntentVO> intentsList = new ArrayList<>();
			
			for(BotSentenceBranch temp : branchList) {
				BotSentenceBranch vo = new BotSentenceBranch();
				BeanUtil.copyProperties(temp, vo);
				//保存录音信息
				String resp = temp.getResponse();
				String resp_new = "";
				logger.info("当前录音信息为: " + resp);
				if(StringUtils.isNotBlank(resp) && !"[]".equals(resp.trim()) && resp.trim().startsWith("[") && resp.trim().endsWith("]")) {
					String[] respArray = resp.substring(1,resp.length()-1).split(",");
					for(int i = 0 ; i < respArray.length ; i++) {
						if(respIdList.contains(new Long(respArray[i]))) {
							continue;
						}
						respIdList.add(new Long(respArray[i]));
					}
					resp_new="[" + resp_new + "]";
				}else {
					resp_new = "[]";
				}
				
				
				vo.setCrtTime(new Date(System.currentTimeMillis()));
				vo.setCrtUser(userId.toString());
				vo.setProcessId(process.getProcessId());
				vo.setTemplateId(process.getTemplateId());
				vo.setBranchId(null);
				branchList_new.add(vo);
			}
			
			
			
			BotSentenceIntentExample intentExample = new BotSentenceIntentExample();
			//intentExample.createCriteria().andIdIn(intentIdList);
			intentExample.createCriteria().andProcessIdEqualTo(paramVO.getProcessId());
			List<BotSentenceIntent> intentTempList = botSentenceIntentMapper.selectByExampleWithBLOBs(intentExample);
			
			//复制一条意图信息
			for(BotSentenceIntent intentTemp : intentTempList) {
				IntentVO intentVO = new IntentVO();
				BeanUtil.copyProperties(intentTemp, intentVO);
				intentVO.setCrtTime(new Date(System.currentTimeMillis()));
				intentVO.setCrtUser(userId.toString());
				intentVO.setProcessId(process.getProcessId());
				intentVO.setTemplateId(process.getTemplateId());
				intentVO.setId(null);
				intentVO.setOldIntentId(intentTemp.getId());
				//intentVO.setName(intentVO.getName().replace(intentVO.getName().split("_")[1], process.getTemplateId().split("_")[0]));
				intentsList.add(intentVO);
			}
			
			
			VoliceInfoExample voliceExample = new VoliceInfoExample();
			voliceExample.createCriteria().andVoliceIdIn(respIdList);
			List<VoliceInfo> voliceTempList = voliceInfoMapper.selectByExample(voliceExample);
			
			for(VoliceInfo voliceTemp : voliceTempList) {
				VoliceInfoVO voliceInfoVO = new VoliceInfoVO();
				//VoliceInfo volicdInfo = voliceInfoMapper.selectByPrimaryKey(new Long(respArray[i]));
				
				voliceTemp.setCrtTime(new Date(System.currentTimeMillis()));
				voliceTemp.setCrtUser(userId.toString());
				voliceTemp.setProcessId(process.getProcessId());
				//voliceTemp.setVoliceId(null);
				voliceTemp.setType(Constant.VOLICE_TYPE_NORMAL);
				//voliceTemp.setDomainName(temp.getDomain());
				BeanUtil.copyProperties(voliceTemp, voliceInfoVO);
				//voliceInfoVO.setVoliceUrl(null);
				voliceInfoVO.setOldVoliceId(voliceTemp.getVoliceId());
				voliceInfoVO.setTemplateId(process.getTemplateId());
				voliceList.add(voliceInfoVO);
			}
			
			
			//批量保存意图数据
			intentsList = batchSaveIntent(intentsList);
			
			//批量保存录音数据
			voliceList = batchSaveVoliceInfo(voliceList);
			
			for(BotSentenceBranch branch : branchList_new) {
				
				//设置branch的intents数据
				String oldIntent = branch.getIntents();
				String newIntent = "";
				List<String> newIntentList = new ArrayList<>();
				List<String> newIntentList2 = new ArrayList<>();
				newIntentList.add(oldIntent);
				if(StringUtils.isNotBlank(oldIntent)) {
					String[] intentArray = oldIntent.split(",");
					for(int i = 0 ; i < intentArray.length ; i++) {
						for(IntentVO vo : intentsList) {
							if(newIntentList.contains((vo.getOldIntentId()) + "")) {
								if(!newIntentList2.contains(vo.getId().toString())) {
									newIntentList2.add(vo.getId().toString());
								}
							}
						}
					}
					
					if(null != newIntentList2 && newIntentList2.size() > 0) {
						for(int i = 0 ; i < newIntentList2.size() ; i++) {
							if(i == newIntentList2.size() - 1) {//最后一条
								newIntent = newIntent + newIntentList2.get(i);
							}else {
								newIntent = newIntent + newIntentList2.get(i) + ",";
							}
						}
					}else {
						newIntent = null;
					}
					
				}else {
					newIntent = null;
				}
				branch.setIntents(newIntent);
				
				
				
				//设置branch的resp数据
				String oldResp = branch.getResponse();
				String newResp = "";
				List<String> newRespList = new ArrayList<>();
				List<String> newRespList2 = new ArrayList<>();
				//newRespList.add(oldResp);
				if(StringUtils.isNotBlank(oldResp) && !"[]".equals(oldResp.trim()) && oldResp.trim().startsWith("[") && oldResp.trim().endsWith("]")) {
					String[] respArray = oldResp.substring(1,oldResp.length()-1).split(",");
					
					for(int i = 0 ; i < respArray.length ; i++) {
						newRespList.add(respArray[i]);
					}
					
					for(int i = 0 ; i < respArray.length ; i++) {
						for(VoliceInfoVO vo : voliceList) {
							if(newRespList.contains((vo.getOldVoliceId()) + "")) {
								if(!newRespList2.contains(vo.getVoliceId().toString())) {
									newRespList2.add(vo.getVoliceId().toString());
								}
								//newResp = newResp.replaceAll((vo.getOldVoliceId()) + "", vo.getVoliceId()+"");
							}
						}
					}
					
					if(null !=newRespList2 && newRespList2.size() > 0) {
						newResp = "[";
						for(int i = 0 ; i < newRespList2.size() ; i++) {
							if(i == newRespList2.size() - 1) {//最后一条
								newResp = newResp + newRespList2.get(i) + "]";
							}else {
								newResp = newResp + newRespList2.get(i) + ",";
							}
						}
					}else {
						newResp = "[]";
					}
					
					
					
					
				}else {
					newResp = "[]";
				}
				
				branch.setResponse(newResp);
				
			}
			
			
			
			botSentenceBranchExtMapper.batchInsert(branchList_new);
			logger.info("共保存brach信息: " + branchList_new.size());
		}
		logger.info("保存brach信息成功");
		
		long time4 = System.currentTimeMillis();
		logger.info("======保存brach信息耗时======" + (time4-time3));
		
		
		//保存意向标签数据
		BotSentenceLabelExample labelExample = new BotSentenceLabelExample();
		labelExample.createCriteria().andProcessIdEqualTo(paramVO.getProcessId());
		List<BotSentenceLabel> labelList = botSentenceLabelMapper.selectByExample(labelExample);
		if(null != labelList && labelList.size() > 0) {
			for(BotSentenceLabel label : labelList) {
				label.setLabelId(null);
				label.setProcessId(process.getProcessId());
				if(StringUtils.isBlank(label.getKeywords())) {
					label.setKeywords("[]");
				}else {
					label.setKeywords(label.getKeywords());
				}
				label.setCrtUser(userId.toString());
				label.setCrtTime(new Date(System.currentTimeMillis()));
				label.setLstUpdateTime(null);
				label.setLstUpdateUser(null);
			}
			botSentenceLabelExtMapper.batchInsert(labelList);
			logger.info("保存意向标签信息成功");
		}
		
		//保存附件相关信息
		//botSentenceTemplate
		BotSentenceAddition addition = botSentenceAdditionMapper.selectByPrimaryKey(botSentenceTemplate.getProcessId());
		if(null != addition) {
			BotSentenceAddition temp = new BotSentenceAddition();
			BeanUtil.copyProperties(addition, temp);
			temp.setProcessId(process.getProcessId());
			botSentenceAdditionMapper.insert(temp);
		}
		
		
		//创建流程时，默认创建一条挽回话术池--取domain为拒绝的negative数据
		BotSentenceBranchExample refuseExample = new BotSentenceBranchExample();
		refuseExample.createCriteria().andProcessIdEqualTo(process.getProcessId()).andDomainEqualTo("拒绝").andBranchNameEqualTo("negative");
		List<BotSentenceBranch> list = botSentenceBranchMapper.selectByExample(refuseExample);
		if(null != list && list.size() > 0) {
			BotSentenceBranch refuse = list.get(0);
			if(StringUtils.isNotBlank(refuse.getResponse()) && !"[]".equals(refuse.getResponse())) {
				String[] respArray = refuse.getResponse().substring(1,refuse.getResponse().length()-1).split(",");
				VoliceInfo oldVolice = voliceInfoMapper.selectByPrimaryKey(new Long(respArray[0]));
				
				VoliceInfo volice = new VoliceInfo();
				volice.setType(Constant.VOLICE_TYPE_REFUSE);//挽回话术
				volice.setContent(oldVolice.getContent().trim());
				volice.setProcessId(process.getProcessId());
				volice.setTemplateId(process.getTemplateId());
				volice.setName("默认挽回话术");
				volice.setVoliceUrl(oldVolice.getVoliceUrl());
				//voliceInfoMapper.insert(volice);
				voliceServiceImpl.saveVoliceInfo(volice,userId);
			}
		}
		logger.info("保存默认一条挽回话术池成功...");
		
		
		logger.info("创建新的话术模板成功,流程编号: " + process.getProcessId());
		
		return process.getProcessId();
	}
	
	
	private List<VoliceInfoVO> batchSaveVoliceInfo(List<VoliceInfoVO> list){
		
		if(null != list && list.size() > 0) {
			voliceInfoExtMapper.batchInsert(list);
			return list;
		}
		return null;
	}
	
	
	private List<IntentVO> batchSaveIntent(List<IntentVO> list){
		if(null != list && list.size() > 0) {
			botSentenceIntentExtMapper.batchInsert(list);
			return list;
		}
		return null;
	}

	@Override
	public void submit(String processId,Long userId) {
		
		if(StringUtils.isBlank(processId)) {
			throw new CommonException("话术流程编号为为空!");
		}
		logger.info("提交话术流程审核, 当前话术流程编号: " + processId);
		BotSentenceProcess process = botSentenceProcessMapper.selectByPrimaryKey(processId);
		if(null == process) {
			throw new CommonException("当前话术流程不存在!");
		}
		
		if(Constant.APPROVE_NOTPASS.equals(process.getState()) || Constant.APPROVE_ONLINE.equals(process.getState())) {
			throw new CommonException("话术未作任何修改,不允许重复提交");
		}
		
		if(!"00".equals(process.getState())) {
			throw new CommonException("话术流程状态不是制作中，不允许提交审核!");
		}
		
		
		//判断当前话术流程是否所有最后一个分支都是结束节点
		BotSentenceDomainExample domainExample = new BotSentenceDomainExample();
		domainExample.createCriteria().andProcessIdEqualTo(processId).andCategoryEqualTo("1");
		List<String> domainList = new ArrayList<>();
		
		List<String> endDomainList = new ArrayList<>();
		
		List<BotSentenceDomain> list = botSentenceDomainMapper.selectByExample(domainExample);
		if(null != list && list.size() > 0) {
			for(BotSentenceDomain domain : list) {
				domainList.add(domain.getDomainName());
				//判断是否所有节点都有连线(end节点除外)
				BotSentenceBranchExample branchExample = new BotSentenceBranchExample();
				branchExample.createCriteria().andProcessIdEqualTo(processId).andIsShowEqualTo("1").andDomainEqualTo(domain.getDomainName());
				int num = botSentenceBranchMapper.countByExample(branchExample);
				if(num < 1 && !Constant.DOMAIN_TYPE_END.equals(domain.getType())) {
					throw new CommonException("节点["+domain.getDomainName()+"]未连线,或修改为结束节点!");
				}
				if(Constant.DOMAIN_TYPE_END.equals(domain.getType())) {
					endDomainList.add(domain.getDomainName());
				}
				
			}
		}
		
		//校验所有结束节点只有指向，没有分支
		if(null != list && list.size() > 0) {
			for(String domainName : endDomainList) {
				BotSentenceBranchExample branchExample = new BotSentenceBranchExample();
				branchExample.createCriteria().andProcessIdEqualTo(processId).andIsShowEqualTo("1").andNextEqualTo(domainName);
				int num = botSentenceBranchMapper.countByExample(branchExample);
				if(num < 1) {
					throw new CommonException("节点["+domainName+"]未连线!");
				}
			}
		}
		
		
		
		//校验是否所有连线都已有连接
		BotSentenceBranchExample branchExample = new BotSentenceBranchExample();
		branchExample.createCriteria().andProcessIdEqualTo(processId).andIsShowEqualTo("1").andDomainIn(domainList);
		List<BotSentenceBranch> branchValidateList = botSentenceBranchMapper.selectByExample(branchExample);
		for(BotSentenceBranch branch : branchValidateList) {
			if(StringUtils.isBlank(branch.getNext())) {
				throw new CommonException("连线["+branch.getLineName()+"]未连接节点,或删除该连线!");
			}
			
			if(Constant.BRANCH_TYPE_NORMAL.equals(branch.getType())) {//如果是一般类型的分支，关键字不能为空
				String intent = branch.getIntents();
				if(StringUtils.isBlank(intent)) {
					throw new CommonException("连线["+branch.getLineName()+"]关键字为空!");	
				}
				BotSentenceIntent botSentenceIntent = botSentenceIntentMapper.selectByPrimaryKey(new Long(intent));
				if(StringUtils.isBlank(botSentenceIntent.getKeywords()) || "[]".equals(botSentenceIntent.getKeywords())) {
					throw new CommonException("连线["+branch.getLineName()+"]关键字为空!");	
				}
			}
			
		}
		
		//校验所有节点是否都有其它节点指向它（开始节点除外）
		for(BotSentenceDomain domain : list) {
			String domainName = domain.getDomainName();
			BotSentenceBranchExample notStartBranchExample = new BotSentenceBranchExample();
			notStartBranchExample.createCriteria().andProcessIdEqualTo(processId).andIsShowEqualTo("1").andNextEqualTo(domainName);
			List<BotSentenceBranch> notStartBranchList = botSentenceBranchMapper.selectByExample(notStartBranchExample);
			if((!Constant.DOMAIN_TYPE_START.equals(domain.getType())) && (null == notStartBranchList || notStartBranchList.size() < 1)) {
				throw new CommonException("节点[" + domainName + "]连线不完整!");
			}
		}
		
		for(String domainName : domainList) {
			BotSentenceBranch branch = getEnterBranch(processId, domainName);
			if(StringUtils.isBlank(branch.getResponse())) {
				throw new CommonException("节点[" + domainName + "]文案不完整!");
			}
			 
			String response = branch.getResponse().substring(1,branch.getResponse().length()-1).split(",")[0];
			VoliceInfo volice = voliceInfoMapper.selectByPrimaryKey(new Long(response));
			if(StringUtils.isBlank(volice.getContent())) {
				throw new CommonException("节点[" + domainName + "]文案不完整!");
			}
		}
		
		//判断当前话术流程是否全部都已上传了录音信息
		BotSentenceBranchExample example = new BotSentenceBranchExample();
		example.createCriteria().andProcessIdEqualTo(processId).andResponseIsNotNull().andResponseNotEqualTo("[]");
		List<BotSentenceBranch> branchList = botSentenceBranchMapper.selectByExample(example);
		for(BotSentenceBranch temp : branchList) {
			String [] array = BotSentenceUtil.getResponses(temp.getResponse());
			if(null != array && array.length > 0) {
				for(int i = 0 ; i < array.length ; i++) {
					VoliceInfo volice = voliceInfoMapper.selectByPrimaryKey(new Long(array[i]));
					//如果当前录音是TTS合成，则不需要校验
					boolean tts = botSentenceTtsService.validateContainParam(volice.getContent());
					if(!tts) {
						if(null == volice || StringUtils.isBlank(volice.getVoliceUrl())) {
							throw new CommonException("存在未上传录音的文案!");
						}
					}
				}
			}
		}
		
		
		//判断挽回话术池是否上传录音
		VoliceInfoExample refuseExample = new VoliceInfoExample();
		refuseExample.createCriteria().andProcessIdEqualTo(processId).andTypeEqualTo(Constant.VOLICE_TYPE_REFUSE);
		List<VoliceInfo> refuseVoliceList = voliceInfoMapper.selectByExample(refuseExample);
		if(null != refuseVoliceList && refuseVoliceList.size() > 0) {
			for(VoliceInfo volice : refuseVoliceList) {
				if(StringUtils.isBlank(volice.getVoliceUrl())) {
					throw new CommonException("存在未上传录音的挽回话术文案!");
				}
			}
		}
		
		
		boolean needTts = false;
		
		//校验TTS录音是否已上传
		BotSentenceTtsTaskExample ttsExample = new BotSentenceTtsTaskExample();
		ttsExample.createCriteria().andProcessIdEqualTo(processId).andIsParamEqualTo(Constant.IS_PARAM_FALSE);
		List<BotSentenceTtsTask> ttsList =  botSentenceTtsTaskMapper.selectByExample(ttsExample);
		
		if(null != ttsList && ttsList.size() > 0) {
			needTts = true;
			for(BotSentenceTtsTask task : ttsList) {
				if(StringUtils.isBlank(task.getVoliceUrl())) {
					throw new CommonException("存在未上传录音的tts文案!");
				}
				
				String voliceId = task.getBusiId();
				VoliceInfo volice = voliceInfoMapper.selectByPrimaryKey(new Long(voliceId));
				//校验是否编辑备用话术
				if(StringUtils.isNotBlank(volice.getContent())) {
					String content = volice.getContent();
					if(BotSentenceUtil.validateContainParam(content)) {
						//根据voliceId查询用话术
						BotSentenceTtsBackupExample backupExample = new BotSentenceTtsBackupExample();
						backupExample.createCriteria().andProcessIdEqualTo(processId).andVoliceIdEqualTo(volice.getVoliceId());
						List<BotSentenceTtsBackup> backupList = botSentenceTtsBackupMapper.selectByExample(backupExample);
						if(null != backupList && backupList.size() > 0) {
							if(StringUtils.isBlank(backupList.get(0).getContent())) {
								throw new CommonException("备用话术未维护!");
							}
						}else {
							throw new CommonException("备用话术未维护!");
						}
						
					}
				}
			}
		}
		
		//校验通用对话录音是否已上传
		BotSentenceTtsBackupExample backupExample = new BotSentenceTtsBackupExample();
		backupExample.createCriteria().andProcessIdEqualTo(processId);
		List<BotSentenceTtsBackup> backupList = botSentenceTtsBackupMapper.selectByExample(backupExample);
		if(null != backupList && backupList.size() > 0) {
			for(BotSentenceTtsBackup backup : backupList) {
				if(null != backup.getContent() && StringUtils.isNotBlank(backup.getContent().trim()) && StringUtils.isBlank(backup.getUrl())) {
					throw new CommonException("存在未上传录音的备用话术!");
				}
			}
		}
		
		//校验是否保存录音师
		if(needTts && StringUtils.isBlank(process.getSoundType())) {
			throw new CommonException("当前话术需要TTS，请选择配音师!");
		}
		
		
		process.setState("01");//审核中
		process.setLstUpdateTime(new Date(System.currentTimeMillis()));
		process.setLstUpdateUser(userId.toString());
		botSentenceProcessMapper.updateByPrimaryKey(process);
		logger.info("提交审核成功...");
		
		BotAvailableTemplate botAvailableTemplate=new BotAvailableTemplate();
	    botAvailableTemplate.setTemplateId(process.getTemplateId());
	    botAvailableTemplate.setUserId(Long.valueOf(process.getCrtUser()));
	    botPublishSentenceLogMapper.deleteAvailableTemplate(botAvailableTemplate);
	}
	
	@Autowired
	private BotPublishSentenceLogMapper botPublishSentenceLogMapper;

	/**
	 * 修改话术模板
	 */
	@Override
	public String updateBotSentenceTemplate(String processId, String templateName, String industry,Long userId) {
		if(StringUtils.isBlank(templateName) || StringUtils.isBlank(processId)) {
			throw new CommonException("修改话术失败，请求参数为空!");
		}
		BotSentenceProcess process = botSentenceProcessMapper.selectByPrimaryKey(processId);
		process.setTemplateName(templateName);
		if(StringUtils.isNotBlank(industry)) {
			process.setIndustry(industry);
		}
		process.setLstUpdateTime(new Date(System.currentTimeMillis()));
		process.setLstUpdateUser(userId.toString());
		botSentenceProcessMapper.updateByPrimaryKey(process);
		
		updateProcessState(processId,userId);
		
		return null;
	}

	/**
	 * 删除一条制作中的话术
	 */
	@Override
	public void delete(String processId,Long userId) {
		if(StringUtils.isBlank(processId)) {
			throw new CommonException("修改话术失败，请求参数为空!");
		}
		BotSentenceProcess process = botSentenceProcessMapper.selectByPrimaryKey(processId);
		if(null != process && ("00".equals(process.getState()) || "02".equals(process.getState()) || "03".equals(process.getState())) && !"00".equals(process.getTemplateType())) {
			logger.info("删除话术流程信息...");
			process.setState("99");//状态修改为作废
			process.setLstUpdateTime(new Date(System.currentTimeMillis()));
			process.setLstUpdateUser(userId.toString());
			botSentenceProcessMapper.updateByPrimaryKey(process);
			
			logger.info("删除话术数据成功...");
		}else {
			throw new CommonException("当前状态不是制作中，不允许删除!");
		}
		 
	}

	
	@Override
	public List<CommonDialogVO> queryCommonDialog(String processId) {
		List<CommonDialogVO> branchList = new ArrayList<>();
		List<CommonDialogVO> resultList = new ArrayList<>();
		List<Long> voliceIdList = new ArrayList<>();
		
		
		//重复
		CommonDialogVO repeat = new CommonDialogVO();
		repeat.setHuashu("重复上一句");//话术
		repeat.setLuoji("重复3次后走邀约失败");//逻辑
		repeat.setYujin("重复");//语境
		repeat.setTitle("重复");
		branchList.add(repeat);
		
		
		//挽回（关键词：拒绝domain，name为negative的intent
		logger.info("查询挽回话术...");
		BotSentenceBranchExample example1 = new BotSentenceBranchExample();
		example1.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo("拒绝").andBranchNameEqualTo("negative");
		List<BotSentenceBranch> list1 = botSentenceBranchMapper.selectByExample(example1);
		if(null != list1 && list1.size() > 0) {
			BotSentenceBranch branch = list1.get(0);
			CommonDialogVO vo = new CommonDialogVO();
			if(StringUtils.isNotBlank(branch.getResponse()) && !"[]".equals(branch.getResponse().trim()) 
					&& branch.getResponse().trim().startsWith("[") && branch.getResponse().trim().endsWith("]")) {
				String[] respArray = branch.getResponse().substring(1,branch.getResponse().length()-1).split(",");
				VoliceInfo volice = voliceInfoMapper.selectByPrimaryKey(new Long(respArray[0]));
				if(null != volice) {
					vo.setHuashu(volice.getContent());//话术
					vo.setVoliceUrl(volice.getVoliceUrl());//录音URL
					vo.setVoliceId(volice.getVoliceId());
					vo.setFlag(volice.getFlag());
					voliceIdList.add(volice.getVoliceId());
				}
				
				//vo.setLuoji("命中在忙关键词,走在忙结束,机器人主动挂断");//逻辑
				vo.setYujin("全局挽回");//语境
				vo.setBranchId(branch.getBranchId());
				vo.setTemplateId(branch.getTemplateId());
				vo.setProcessId(processId);
				vo.setBranchName(branch.getBranchName());
				vo.setTitle("全局挽回");
				branchList.add(vo);
			}
		}
		
		//失败邀约放到全局语境里来
		BotSentenceBranchExample example5 = new BotSentenceBranchExample();
		example5.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo("邀约").andBranchNameEqualTo("failed_enter_branch");
		List<BotSentenceBranch> list5 = botSentenceBranchMapper.selectByExample(example5);
		if(null != list5 && list5.size() > 0) {
			BotSentenceBranch branch = list5.get(0);
			CommonDialogVO vo = new CommonDialogVO();
			if(StringUtils.isNotBlank(branch.getResponse()) && !"[]".equals(branch.getResponse().trim()) 
					&& branch.getResponse().trim().startsWith("[") && branch.getResponse().trim().endsWith("]")) {
				String[] respArray = branch.getResponse().substring(1,branch.getResponse().length()-1).split(",");
				VoliceInfo volice = voliceInfoMapper.selectByPrimaryKey(new Long(respArray[0]));
				
				vo.setHuashu(volice.getContent());//话术
				vo.setVoliceUrl(volice.getVoliceUrl());//录音URL
				vo.setYujin("失败邀约");//语境
				vo.setTitle("失败邀约");
				vo.setBranchId(branch.getBranchId());
				vo.setVoliceId(volice.getVoliceId());
				vo.setTemplateId(branch.getTemplateId());
				vo.setProcessId(processId);
				vo.setBranchName(branch.getBranchName());
				vo.setVoliceName(volice.getName());
				vo.setFlag(volice.getFlag());
				voliceIdList.add(volice.getVoliceId());
				branchList.add(vo);
			}
		}
		
		//失败结束放到全局语境里来
		BotSentenceBranchExample example6 = new BotSentenceBranchExample();
		example6.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo("结束").andBranchNameEqualTo("failed_enter_branch");
		List<BotSentenceBranch> list6 = botSentenceBranchMapper.selectByExample(example6);
		if(null != list6 && list6.size() > 0) {
			BotSentenceBranch branch = list6.get(0);
			CommonDialogVO vo = new CommonDialogVO();
			if(StringUtils.isNotBlank(branch.getResponse()) && !"[]".equals(branch.getResponse().trim()) 
					&& branch.getResponse().trim().startsWith("[") && branch.getResponse().trim().endsWith("]")) {
				String[] respArray = branch.getResponse().substring(1,branch.getResponse().length()-1).split(",");
				VoliceInfo volice = voliceInfoMapper.selectByPrimaryKey(new Long(respArray[0]));
				
				vo.setHuashu(volice.getContent());//话术
				vo.setVoliceUrl(volice.getVoliceUrl());//录音URL
				//vo.setLuoji("失败结束");//逻辑
				vo.setYujin("失败结束");//语境
				vo.setTitle("失败结束");
				vo.setBranchId(branch.getBranchId());
				vo.setVoliceId(volice.getVoliceId());
				vo.setTemplateId(branch.getTemplateId());
				vo.setProcessId(processId);
				vo.setBranchName(branch.getBranchName());
				vo.setVoliceName(volice.getName());
				vo.setFlag(volice.getFlag());
				voliceIdList.add(volice.getVoliceId());
				branchList.add(vo);
			}
		}
		
		
		//未识别 （domain未为匹配响应的branch，special
		BotSentenceBranchExample example3 = new BotSentenceBranchExample();
		example3.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo("未匹配响应").andBranchNameEqualTo("special");
		List<BotSentenceBranch> list3 = botSentenceBranchMapper.selectByExample(example3);
		int index = 1;
		if(null != list3 && list3.size() > 0) {
			for(BotSentenceBranch branch : list3) {
				
				if(StringUtils.isNotBlank(branch.getResponse()) && !"[]".equals(branch.getResponse().trim()) 
						&& branch.getResponse().trim().startsWith("[") && branch.getResponse().trim().endsWith("]")) {
					String[] respArray = branch.getResponse().substring(1,branch.getResponse().length()-1).split(",");
					
					for(int i = 0 ; i < respArray.length ; i++) {
						CommonDialogVO vo = new CommonDialogVO();
						VoliceInfo volice = voliceInfoMapper.selectByPrimaryKey(new Long(respArray[i]));
						
						vo.setHuashu(volice.getContent());//话术
						vo.setVoliceUrl(volice.getVoliceUrl());//录音URL
						vo.setLuoji("未识别" + index);//逻辑
						vo.setYujin("未识别");//语境
						vo.setTitle("未识别" + index);
						vo.setBranchId(branch.getBranchId());
						vo.setVoliceId(volice.getVoliceId());
						vo.setTemplateId(branch.getTemplateId());
						vo.setProcessId(processId);
						vo.setBranchName(branch.getBranchName());
						vo.setVoliceName(volice.getName());
						vo.setFlag(volice.getFlag());
						voliceIdList.add(volice.getVoliceId());
						branchList.add(vo);
						index++;
					}
				}
				
			}
		}
		
		//在忙，话术为domain为结束_在忙的enter，取resp，关键词为domain为结束_在忙的enter，取intents
		BotSentenceBranchExample example4 = new BotSentenceBranchExample();
		example4.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo("结束_在忙").andBranchNameEqualTo("enter_branch");
		List<BotSentenceBranch> list4 = botSentenceBranchMapper.selectByExample(example4);
		if(null != list4 && list4.size() > 0) {
			BotSentenceBranch branch = list4.get(0);
			CommonDialogVO vo = new CommonDialogVO();
			if(StringUtils.isNotBlank(branch.getResponse()) && !"[]".equals(branch.getResponse().trim()) 
					&& branch.getResponse().trim().startsWith("[") && branch.getResponse().trim().endsWith("]")) {
				String[] respArray = branch.getResponse().substring(1,branch.getResponse().length()-1).split(",");
				VoliceInfo volice = voliceInfoMapper.selectByPrimaryKey(new Long(respArray[0]));
				
				vo.setHuashu(volice.getContent());//话术
				vo.setVoliceUrl(volice.getVoliceUrl());//录音URL
				vo.setLuoji("命中在忙关键词,走在忙结束,机器人主动挂断");//逻辑
				vo.setYujin("在忙");//语境
				vo.setTitle("在忙");
				vo.setBranchId(branch.getBranchId());
				vo.setVoliceId(volice.getVoliceId());
				vo.setTemplateId(branch.getTemplateId());
				vo.setProcessId(processId);
				vo.setBranchName(branch.getBranchName());
				vo.setVoliceName(volice.getName());
				vo.setFlag(volice.getFlag());
				voliceIdList.add(volice.getVoliceId());
				branchList.add(vo);
			}
		}
		
		
		//出错，话术domain为”出错“的enter
		BotSentenceBranch chucuoBranch = getEnterBranch(processId, "出错");
		if(null != chucuoBranch) {
			BotSentenceBranch branch = chucuoBranch;
			CommonDialogVO vo = new CommonDialogVO();
			if(StringUtils.isNotBlank(branch.getResponse()) && !"[]".equals(branch.getResponse().trim()) 
					&& branch.getResponse().trim().startsWith("[") && branch.getResponse().trim().endsWith("]")) {
				String[] respArray = branch.getResponse().substring(1,branch.getResponse().length()-1).split(",");
				VoliceInfo volice = voliceInfoMapper.selectByPrimaryKey(new Long(respArray[0]));
				
				vo.setHuashu(volice.getContent());//话术
				vo.setVoliceUrl(volice.getVoliceUrl());//录音URL
				vo.setLuoji("触发\"出错\"流程");//逻辑
				vo.setYujin("其他");//语境
				vo.setTitle("其他1");
				vo.setBranchId(branch.getBranchId());
				vo.setVoliceId(volice.getVoliceId());
				vo.setTemplateId(branch.getTemplateId());
				vo.setProcessId(processId);
				vo.setBranchName(branch.getBranchName());
				vo.setVoliceName(volice.getName());
				vo.setFlag(volice.getFlag());
				voliceIdList.add(volice.getVoliceId());
				branchList.add(vo);
			}
		}
		
		//强制结束，话术domain为”强制结束“的enter
		BotSentenceBranch endBranch = getEnterBranch(processId, "强制结束");
		if(null != endBranch) {
			BotSentenceBranch branch = endBranch;
			CommonDialogVO vo = new CommonDialogVO();
			if(StringUtils.isNotBlank(branch.getResponse()) && !"[]".equals(branch.getResponse().trim()) 
					&& branch.getResponse().trim().startsWith("[") && branch.getResponse().trim().endsWith("]")) {
				String[] respArray = branch.getResponse().substring(1,branch.getResponse().length()-1).split(",");
				VoliceInfo volice = voliceInfoMapper.selectByPrimaryKey(new Long(respArray[0]));
				
				vo.setHuashu(volice.getContent());//话术
				vo.setVoliceUrl(volice.getVoliceUrl());//录音URL
				vo.setLuoji("触发\"强制结束\"流程");//逻辑
				vo.setYujin("其他");//语境
				vo.setTitle("其他2");
				vo.setBranchId(branch.getBranchId());
				vo.setVoliceId(volice.getVoliceId());
				vo.setTemplateId(branch.getTemplateId());
				vo.setProcessId(processId);
				vo.setBranchName(branch.getBranchName());
				vo.setVoliceName(volice.getName());
				vo.setFlag(volice.getFlag());
				voliceIdList.add(volice.getVoliceId());
				branchList.add(vo);
			}
		}
		
		//结束_未匹配，话术domain为”结束_未匹配“的enter
		BotSentenceBranch endWithoutMatchBranch = getEnterBranch(processId, "结束_未匹配");
		if(null != endWithoutMatchBranch) {
			BotSentenceBranch branch = endWithoutMatchBranch;
			CommonDialogVO vo = new CommonDialogVO();
			if(StringUtils.isNotBlank(branch.getResponse()) && !"[]".equals(branch.getResponse().trim()) 
					&& branch.getResponse().trim().startsWith("[") && branch.getResponse().trim().endsWith("]")) {
				String[] respArray = branch.getResponse().substring(1,branch.getResponse().length()-1).split(",");
				VoliceInfo volice = voliceInfoMapper.selectByPrimaryKey(new Long(respArray[0]));
				
				vo.setHuashu(volice.getContent());//话术
				vo.setVoliceUrl(volice.getVoliceUrl());//录音URL
				vo.setLuoji("触发\"结束_未匹配\"流程");//逻辑
				vo.setYujin("其他");//语境
				vo.setTitle("其他3");
				vo.setBranchId(branch.getBranchId());
				vo.setVoliceId(volice.getVoliceId());
				vo.setTemplateId(branch.getTemplateId());
				vo.setProcessId(processId);
				vo.setBranchName(branch.getBranchName());
				vo.setVoliceName(volice.getName());
				vo.setFlag(volice.getFlag());
				voliceIdList.add(volice.getVoliceId());
				branchList.add(vo);
			}
		}
		
		
		//其他所有category为3的branch
		BotSentenceDomainExample example = new BotSentenceDomainExample();
		List<String> domains = new ArrayList<>();
		
		domains.add("拒绝");
		domains.add("邀约");
		domains.add("结束");
		domains.add("未匹配响应");
		domains.add("结束_在忙");
		domains.add("出错");
		domains.add("强制结束");
		domains.add("结束_未匹配");
		domains.add("一般问题");
		domains.add("解释开场白");
		
		example.createCriteria().andProcessIdEqualTo(processId).andDomainNameNotIn(domains).andCategoryEqualTo("3");
		List<BotSentenceDomain> domainList = botSentenceDomainMapper.selectByExample(example);
		if(null != domainList && domainList.size() > 0) {
			List<String> domainNameList = new ArrayList<>();
			for(BotSentenceDomain domain : domainList) {
				domainNameList.add(domain.getDomainName());
			}
			
			BotSentenceBranchExample example7 = new BotSentenceBranchExample();
			example7.createCriteria().andProcessIdEqualTo(processId).andDomainIn(domainNameList).andResponseIsNotNull().andResponseNotEqualTo("[]");
			List<BotSentenceBranch> list7 = botSentenceBranchMapper.selectByExample(example7);
			if(null != list7 && list7.size() > 0) {
				for(BotSentenceBranch branch : list7) {
					CommonDialogVO vo = new CommonDialogVO();
					if(StringUtils.isNotBlank(branch.getResponse()) && !"[]".equals(branch.getResponse().trim()) 
							&& branch.getResponse().trim().startsWith("[") && branch.getResponse().trim().endsWith("]")) {
						String[] respArray = branch.getResponse().substring(1,branch.getResponse().length()-1).split(",");
						VoliceInfo volice = voliceInfoMapper.selectByPrimaryKey(new Long(respArray[0]));
						vo.setHuashu(volice.getContent());//话术
						vo.setVoliceUrl(volice.getVoliceUrl());//录音URL
						vo.setLuoji("触发\"" + branch.getDomain() + "\"流程");//逻辑
						vo.setYujin(volice.getDomainName());//语境
						vo.setBranchId(branch.getBranchId());
						vo.setVoliceId(volice.getVoliceId());
						vo.setTemplateId(branch.getTemplateId());
						vo.setProcessId(processId);
						vo.setBranchName(branch.getBranchName());
						vo.setTitle(volice.getDomainName());
						voliceIdList.add(volice.getVoliceId());
						branchList.add(vo);
					}
				}
			}
		}
		
		
		
		//根据录音ID去重
		if(branchList.size() > 1) {
			for(int i = 0 ; i < branchList.size() ; i++) {
				boolean exist = false;
				for(CommonDialogVO vo : resultList) {
					if(vo.getVoliceId() == branchList.get(i).getVoliceId()) {
						exist = true;
						break;
					}
				}
				if(!exist) {
					resultList.add(branchList.get(i));
				}
			}
		}
		
		return resultList;
	}

	@Override
	@Transactional
	public void updateCommonDialog(String voliceId, String content, String keywords, String intentId, String branchId,Long userId) {
		if(StringUtils.isBlank(voliceId) || StringUtils.isBlank(content)) {
			throw new CommonException("更新失败，请求数据为空!");
		}
		VoliceInfo volice = voliceInfoMapper.selectByPrimaryKey(new Long(voliceId));
		volice.setContent(content.replace("\n", "").trim());
		volice.setLstUpdateTime(new Date(System.currentTimeMillis()));
		volice.setLstUpdateUser(userId.toString());
		if(!"【新增】".equals(volice.getFlag())) {
			volice.setFlag("【修改】");
		}
		voliceServiceImpl.saveVoliceInfo(volice,userId);
		
		updateProcessState(volice.getProcessId(),userId);
		
		//更新意图关键字
		
		String keysString=null;
		//更新意图关键字
		if(StringUtils.isNotBlank(keywords)){
			String replaceKeyWords = keywords.replaceAll("，", ",");
			replaceKeyWords = replaceKeyWords.replace("\n", "");
			replaceKeyWords = replaceKeyWords.trim();
			String[] keys=replaceKeyWords.split(",");
			keysString=JSONObject.toJSONString(keys);
			keysString  = keysString.substring(1, keysString.length()-1);
		}

		if(StringUtils.isNotBlank(intentId)) {
			//更新意图
			BotSentenceIntent intent=botSentenceIntentMapper.selectByPrimaryKey(new Long(intentId));
			
			List<String> keywordList = BotSentenceUtil.getKeywords(intent.getKeywords());
			if(null != keywordList && keywordList.size() > 0 && StringUtils.isNotBlank(keywordList.get(1))&& keysString!=null) {
				intent.setKeywords("[" + keysString + "," + keywordList.get(1).replace("\n", "") + "]");
			}
			intent.setLstUpdateTime(new Date(System.currentTimeMillis()));
			intent.setLstUpdateUser(userId.toString());
			botSentenceIntentMapper.updateByPrimaryKeyWithBLOBs(intent);
		}else {
			//更新branch
			if(StringUtils.isNotBlank(branchId)) {
				BotSentenceBranch branch = botSentenceBranchMapper.selectByPrimaryKey(branchId);
				if(null != branch) {
					//新增意图
					BotSentenceIntent intent = new BotSentenceIntent();
					intent.setCrtTime(new Date(System.currentTimeMillis()));
					intent.setCrtUser(userId.toString());
					intent.setProcessId(branch.getProcessId());
					intent.setDomainName(branch.getDomain());
					if(keysString!=null){
						intent.setKeywords("[" + keysString + "]");
					}else{
						intent.setKeywords("[]");
					}
					intent.setTemplateId(branch.getTemplateId());
					intent.setForSelect(0);
					botSentenceIntentMapper.insert(intent);
					
					branch.setIntents(intent.getId().toString());
					branch.setLstUpdateTime(new Date(System.currentTimeMillis()));
					branch.setLstUpdateUser(userId.toString());
					botSentenceBranchMapper.updateByPrimaryKey(branch);
				}
			}
		}
	}



	public boolean hasChild(String domainName, String processId) {
		BotSentenceDomainExample example = new BotSentenceDomainExample();
		
		example.createCriteria().andProcessIdEqualTo(processId).andCategoryEqualTo("1").andParentEqualTo(domainName);
		
		int num = botSentenceDomainMapper.countByExample(example);
		if(num > 0) {
			return true;
		}else {
			return false;
		}
	}
	
	public int getParentXnum(String domainName, List<LevelVO> levelList, String processId) {
		//获取parent
		BotSentenceDomainExample example = new BotSentenceDomainExample();
		
		example.createCriteria().andProcessIdEqualTo(processId).andCategoryEqualTo("1").andDomainNameEqualTo(domainName);
		
		List<BotSentenceDomain> domainList = botSentenceDomainMapper.selectByExample(example);
		BotSentenceDomain parent = domainList.get(0);
		
		for(int i = 0 ; i < levelList.size() ; i++) {
			List<DomainVO> list = levelList.get(i).getArr();
			for(int j = 0 ; j < list.size() ; j++) {
				if(parent.getParent().equals(list.get(j).getName())) {
					return list.get(j).getxNum();
				}
			}
		}
		return 0;
	}
	
	public int getMaxChildLength(String processId, String domainName) {
		
		List<String> domainNameList = new ArrayList<>();
		List<String> allDomainNameList = new ArrayList<>();
		
		domainNameList.add(domainName);
		allDomainNameList.add(domainName);
		
		while(null != domainNameList && domainNameList.size() > 0) {//如果上级有值，则继续遍历
			BotSentenceDomainExample example = new BotSentenceDomainExample();
			
			example.createCriteria().andProcessIdEqualTo(processId).andCategoryEqualTo("1").andParentIn(domainNameList);
			
			List<BotSentenceDomain> levelList = botSentenceDomainMapper.selectByExample(example);
			
			domainNameList = new ArrayList<>();
			
			if(null != levelList && levelList.size() > 0) {
				
				for(BotSentenceDomain temp : levelList) {
					//vo.setxNum(xnum);
					
					if(!domainNameList.contains(temp.getDomainName())) {
						allDomainNameList.add(temp.getDomainName());
						domainNameList.add(temp.getDomainName());
					}
				}
			}
			
		}
		
		int branchNum = 0;
		
		for(int i = 0 ; i < allDomainNameList.size() ; i++) {
			BotSentenceDomainExample example = new BotSentenceDomainExample();
			example.createCriteria().andProcessIdEqualTo(processId).andCategoryEqualTo("1").andParentEqualTo(allDomainNameList.get(i));
			int num = botSentenceDomainMapper.countByExample(example);
			if(num > 1) {
				branchNum = branchNum + num - 1;
			}
		}
		return branchNum;
	}
	
	
	private int getNextDomainNum(int level ,List<LevelVO> results) {
		int count = 0;
		LevelVO last = results.get(0);
		for(LevelVO temp : results) {
			if(level == temp.getId()) {
				
			}
		}
		
		return count;
	}
	
	
	@Transactional
	@Override
	public BotSentenceDomain saveNode(FlowNode blankDomain,Long userId) {
		
		if(null == blankDomain){
			throw new CommonException("保存失败，请求参数不完整!");
		}
		if(StringUtils.isBlank(blankDomain.getProcessId())) {
			throw new CommonException("保存失败，话术流程编号为空!");
		}
		
		if(StringUtils.isBlank(blankDomain.getLabel())) {
			throw new CommonException("保存失败，节点名称为空!");
		}
		
		logger.info("新增domain卡片");

		Date date = new Date(System.currentTimeMillis());
		
		BotSentenceProcess process = botSentenceProcessMapper.selectByPrimaryKey(blankDomain.getProcessId());
		
		String domainName = "";
		
		//先判断当前domain人名字是否重复
		BotSentenceDomainExample valicateExample = new BotSentenceDomainExample();
		valicateExample.createCriteria().andProcessIdEqualTo(blankDomain.getProcessId()).andDomainNameEqualTo(blankDomain.getLabel());
		int num = botSentenceDomainMapper.countByExample(valicateExample);
		if(num > 0) {
			throw new CommonException("当前名称名已存在,请输入新的节点名称");
		}
		domainName = blankDomain.getLabel();
		
		//新增一个domain
		BotSentenceDomain domain = new BotSentenceDomain();
		domain.setCategory(Constant.CATEGORY_TYPE_1);//主流程
		domain.setProcessId(process.getProcessId());//话术流程编号
		domain.setTemplateId(process.getTemplateId());//话术模板编号
		domain.setDomainName(domainName);//名称
		domain.setType(blankDomain.getType());
		domain.setPositionX(blankDomain.getX());
		domain.setPositionY(blankDomain.getY());
		domain.setCrtTime(date);//创建时间
		domain.setCrtUser(userId.toString());//创建人
		botSentenceDomainMapper.insert(domain);
		
		//第五步：保存录音信息
		VoliceInfo volice = new VoliceInfo();
		volice.setType(Constant.VOLICE_TYPE_NORMAL);//话术流程domain录音
		volice.setDomainName(domainName);
		if(StringUtils.isNotBlank(blankDomain.getContent())) {
			volice.setContent(blankDomain.getContent().replace("\n", "").trim());//录音内容
		}
		
		volice.setProcessId(blankDomain.getProcessId());
		volice.setTemplateId(process.getTemplateId());
		volice.setFlag("【新增】");
		//voliceInfoMapper.insert(volice);
		voliceServiceImpl.saveVoliceInfo(volice,userId);
		
		
		//保存branch信息--enter_branch
		BotSentenceBranch enterBranch = new BotSentenceBranch();
		enterBranch.setDomain(domainName);
		enterBranch.setBranchName("enter_branch");
		enterBranch.setTemplateId(process.getTemplateId());
		enterBranch.setCrtTime(date);
		enterBranch.setCrtUser(userId.toString());
		enterBranch.setProcessId(blankDomain.getProcessId());
		enterBranch.setResponse("["+volice.getVoliceId()+"]");
		botSentenceBranchMapper.insert(enterBranch);
		
		//保存branch信息--failed_enter_branch
		BotSentenceBranch failedEnterBranch = new BotSentenceBranch();
		BeanUtil.copyProperties(enterBranch, failedEnterBranch);
		failedEnterBranch.setBranchId(null);
		failedEnterBranch.setBranchName("failed_enter_branch");
		botSentenceBranchMapper.insert(failedEnterBranch);
		
		//保存negative信息
		BotSentenceBranch negativeBranch = new BotSentenceBranch();
		negativeBranch.setDomain(domainName);
		negativeBranch.setBranchName("negative");
		negativeBranch.setCrtTime(date);
		negativeBranch.setCrtUser(userId.toString());
		negativeBranch.setProcessId(blankDomain.getProcessId());
		enterBranch.setTemplateId(process.getTemplateId());
		negativeBranch.setResponse("[]");
		negativeBranch.setNext("拒绝");
		negativeBranch.setEnd("拒绝");
		botSentenceBranchMapper.insert(negativeBranch);
			
		//如果当前状态为审批通过、已上线，则把状态修改为“制作中”
		updateProcessState(blankDomain.getProcessId(),userId);
		
		return domain;
	}
	
	/**
	 * 更新一个domain卡片
	 * @param
	 */
	@Transactional
	private BotSentenceDomain updateNode(FlowNode blankDomain, String domainId,Long userId) {

		//第一步，更新domain信息的名称
		BotSentenceDomain domain = botSentenceDomainMapper.selectByPrimaryKey(blankDomain.getId());
		String oldDomainName = domain.getDomainName();
		String newDomainName = blankDomain.getLabel();
		//先判断当前domain人名字是否重复
		if(!domain.getDomainName().trim().equals(blankDomain.getLabel().trim())) {
			BotSentenceDomainExample valicateExample = new BotSentenceDomainExample();
			valicateExample.createCriteria().andProcessIdEqualTo(blankDomain.getProcessId()).andDomainNameEqualTo(blankDomain.getLabel());
			List<BotSentenceDomain> validateList = botSentenceDomainMapper.selectByExample(valicateExample);
			if(null != validateList && validateList.size() > 0) {
				throw new CommonException("当前名称名已存在,请输入新的节点名称");
			}
		}
		
		domain.setType(blankDomain.getType());
		domain.setDomainName(blankDomain.getLabel());
		domain.setPositionY(blankDomain.getY());
		domain.setPositionX(blankDomain.getX());
		domain.setLstUpdateTime(new Date(System.currentTimeMillis()));
		domain.setLstUpdateUser(userId.toString());
		botSentenceDomainMapper.updateByPrimaryKey(domain);
		
		
		if(StringUtils.isNotBlank(domainId) && domainId.equals(blankDomain.getId())) {//如果修改的是当前domain才需要更新以下数据，否则只要更新上面的坐标即可
			//第三步：更新branch表的domain
			BotSentenceBranchExample branchExample1 = new BotSentenceBranchExample();
			branchExample1.createCriteria().andProcessIdEqualTo(blankDomain.getProcessId()).andDomainEqualTo(oldDomainName);
			List<BotSentenceBranch> branchList1 = botSentenceBranchMapper.selectByExample(branchExample1);
			if(null != branchList1 && branchList1.size() > 0) {
				for(BotSentenceBranch temp : branchList1) {
					temp.setDomain(newDomainName);
					temp.setLstUpdateTime(new Date(System.currentTimeMillis()));
					temp.setLstUpdateUser(userId.toString());
					botSentenceBranchMapper.updateByPrimaryKey(temp);
				}
			}
			
			//第四步：更新branch表的next
			BotSentenceBranchExample branchExample2 = new BotSentenceBranchExample();
			branchExample2.createCriteria().andProcessIdEqualTo(blankDomain.getProcessId()).andNextEqualTo(oldDomainName);
			List<BotSentenceBranch> branchList2 = botSentenceBranchMapper.selectByExample(branchExample2);
			if(null != branchList2 && branchList2.size() > 0) {
				for(BotSentenceBranch temp : branchList2) {
					temp.setNext(blankDomain.getLabel());
					temp.setLstUpdateTime(new Date(System.currentTimeMillis()));
					temp.setLstUpdateUser(userId.toString());
					botSentenceBranchMapper.updateByPrimaryKey(temp);
				}
			}
			
			
			//第五步：更新branch表的end
			BotSentenceBranchExample branchExample3 = new BotSentenceBranchExample();
			branchExample3.createCriteria().andProcessIdEqualTo(blankDomain.getProcessId()).andEndEqualTo(oldDomainName);
			List<BotSentenceBranch> branchList3 = botSentenceBranchMapper.selectByExample(branchExample3);
			if(null != branchList3 && branchList3.size() > 0) {
				for(BotSentenceBranch temp : branchList3) {
					temp.setEnd(blankDomain.getLabel());
					temp.setLstUpdateTime(new Date(System.currentTimeMillis()));
					temp.setLstUpdateUser(userId.toString());
					botSentenceBranchMapper.updateByPrimaryKey(temp);
				}
			}
			
			
			//更新volice表的content字段（文案）
			if(StringUtils.isNotBlank(blankDomain.getContent())) {
				BotSentenceBranch enterBranch = this.getEnterBranch(domain.getProcessId(), newDomainName);
				String resp = enterBranch.getResponse();
				if(StringUtils.isNotBlank(resp) && !"[]".equals(resp.trim()) && resp.trim().startsWith("[") && resp.trim().endsWith("]")) {
					String[] respArray = resp.substring(1,resp.length()-1).split(",");
					if(respArray.length > 0) {
						long voliceId = new Long(respArray[0]);
						VoliceInfo volice = voliceInfoMapper.selectByPrimaryKey(voliceId);
						
						if(StringUtils.isNotBlank(volice.getContent()) && !volice.getContent().equals(blankDomain.getContent()) && !"【新增】".equals(volice.getFlag())) {
							volice.setFlag("【修改】");
						}
						
						if(StringUtils.isNotBlank(blankDomain.getContent())) {
							volice.setContent(blankDomain.getContent().replace("\n", "").trim());
						}else {
							volice.setContent(blankDomain.getContent());
						}
						
						volice.setLstUpdateTime(new Date(System.currentTimeMillis()));
						volice.setLstUpdateUser(userId.toString());
						volice.setDomainName(blankDomain.getLabel());
						
						//voliceInfoMapper.updateByPrimaryKey(volice);
						voliceServiceImpl.saveVoliceInfo(volice,userId);
					}
				}
			}
			//更新意图表的domain名称和关键字
			BotSentenceIntentExample intentExample = new BotSentenceIntentExample();
			intentExample.createCriteria().andProcessIdEqualTo(domain.getProcessId()).andDomainNameEqualTo(oldDomainName);
			List<BotSentenceIntent> intentList = botSentenceIntentMapper.selectByExample(intentExample);
			if(null != intentList && intentList.size() > 0) {
				for(BotSentenceIntent intent : intentList) {
					intent.setDomainName(newDomainName);
					intent.setLstUpdateTime(new Date(System.currentTimeMillis()));
					intent.setLstUpdateUser(userId.toString());
					botSentenceIntentMapper.updateByPrimaryKey(intent);
				}
			}
			
			
			VoliceInfoExample voliceExample = new VoliceInfoExample();
			voliceExample.createCriteria().andProcessIdEqualTo(domain.getProcessId()).andDomainNameEqualTo(oldDomainName);
			List<VoliceInfo> voliceList = voliceInfoMapper.selectByExample(voliceExample);
			if(null != voliceList && voliceList.size() > 0) {
				for(VoliceInfo volice : voliceList) {
					volice.setDomainName(newDomainName);
					volice.setLstUpdateTime(new Date(System.currentTimeMillis()));
					volice.setLstUpdateUser(userId.toString());
					voliceInfoMapper.updateByPrimaryKey(volice);
				}
			}
		}
		return domain;
	
	}
	
	/**
	 * 更新开场白信息
	 * @param blankDomain
	 * @return
	 */
	@Transactional
	private void saveStartExplain(FlowNode blankDomain,String domainId,Long userId) {
		//如果当前节点为开场白，则需要同时更新解释开场白信息
		if(Constant.DOMAIN_TYPE_START.equals(blankDomain.getType()) && StringUtils.isNotBlank(blankDomain.getStartExplainText())
				&& StringUtils.isNotBlank(blankDomain.getStartExplainKeywords())) {
			
			VoliceInfo volice = getStartExplain(blankDomain.getProcessId());
			//判断解释开场白的文案是否有变化，如果有，则设置volice-flag【修改】
			if(StringUtils.isNotBlank(volice.getContent()) && !volice.getContent().equals(blankDomain.getStartExplainText()) && !"【新增】".equals(volice.getFlag())) {
				volice.setFlag("【修改】");
			}
			
			volice.setContent(blankDomain.getStartExplainText().replace("\n", "").trim());
			volice.setLstUpdateTime(new Date(System.currentTimeMillis()));
			volice.setLstUpdateUser(userId.toString());
			
			//voliceInfoMapper.updateByPrimaryKey(volice);
			voliceServiceImpl.saveVoliceInfo(volice,userId);
			
			
			if(StringUtils.isNotBlank(domainId) && domainId.equals(blankDomain.getId())) {//只有保存开场白卡片才需要校验关键字是否重复

				//获取同级分支的关键字列表
				Map<String, String> keywords = new HashMap<>(); 
				
				BotSentenceBranch explainBranch = getStartExplainBranch(blankDomain.getProcessId());
						
				BotSentenceBranchExample branchexample = new BotSentenceBranchExample();
				branchexample.createCriteria().andProcessIdEqualTo(blankDomain.getProcessId()).andIsShowEqualTo("1").andDomainEqualTo(explainBranch.getDomain()).andBranchIdNotEqualTo(explainBranch.getBranchId());
				List<BotSentenceBranch> branchList = botSentenceBranchMapper.selectByExample(branchexample);
				if(null != branchList && branchList.size() > 0) {
					for(BotSentenceBranch branch : branchList) {
						String intents = branch.getIntents();
						if(StringUtils.isNotBlank(intents)) {
							String [] array = intents.split(",");
							for(int i = 0 ; i < array.length ; i++) {
								BotSentenceIntent temp = botSentenceIntentMapper.selectByPrimaryKey(new Long(array[i]));
								if(null != temp) {
									String[] keywordList = BotSentenceUtil.getKeywords(temp.getKeywords()).get(0).replace("\"", "").split(",");
									if(null != keywordList && keywordList.length > 0) {
										for(String str : keywordList) {
											str = str.replace("\"", "");
											keywords.put(str, branch.getLineName());
										}
									}
								}
								
							}
						}
					}
				}
				
				//校验关键字是否重复
				//获取流程图中各分支的关键字（除了开场白分支 ）
				List<Long> intentIds = new ArrayList<>();
				BotSentenceBranchExample example = new BotSentenceBranchExample();
				example.createCriteria().andProcessIdEqualTo(blankDomain.getProcessId()).andIsShowEqualTo("1").andDomainNotEqualTo("开场白");
				List<BotSentenceBranch> list = botSentenceBranchMapper.selectByExample(example);
				if(null != list && list.size() > 0) {
					for(BotSentenceBranch branch : list) {
						String intents = branch.getIntents();
						if(StringUtils.isNotBlank(intents)) {
							String [] array = intents.split(",");
							for(int i = 0 ; i < array.length ; i++) {
								intentIds.add(new Long(array[i]));
							}
						}
					}
				}
				
				//获取解释开场白本身的关键字
				/*BotSentenceBranch explainBranch = getStartExplainBranch(blankDomain.getProcessId());
				if(null != explainBranch) {
					String intents = explainBranch.getIntents();
					if(StringUtils.isNotBlank(intents)) {
						String [] array = intents.split(",");
						for(int i = 0 ; i < array.length ; i++) {
							intentIds.add(new Long(array[i]));
						}
					}
				}*/
				
				if(StringUtils.isNotBlank(blankDomain.getStartExplainKeywords())) {
					String message = "";
					String [] keywords_array= blankDomain.getStartExplainKeywords().split(",");
					
					//Map<String, String> keywords = getAllMainFlowKeywords(blankDomain.getProcessId(), intentIds);
					for(int j = 0 ; j < keywords_array.length ; j++) {
						if(keywords.containsKey(keywords_array[j])) {
							String	repeat = "【" + keywords_array[j] + "】 与 【" + keywords.get(keywords_array[j]) + "】的关键字重复了";
							message = message + repeat + "<br/>";
						}
					}
					
					if(StringUtils.isNotBlank(message)) {
						throw new CommonException(message);
					}
				}
			}
				
			BotSentenceProcess process = botSentenceProcessMapper.selectByPrimaryKey(blankDomain.getProcessId());
			
			BotSentenceBranch branch = getStartExplainBranch(blankDomain.getProcessId());//开场白解释开场白的分支
			
			//更新branch的意图字段
			if(StringUtils.isBlank(branch.getIntents())) {//如果没有意图信息，则新增
				//新增解释开场白的意图
				BotSentenceIntent intent = new BotSentenceIntent();
				intent.setCrtTime(new Date(System.currentTimeMillis()));
				intent.setCrtUser(userId.toString());
				intent.setProcessId(blankDomain.getProcessId());
				String key_str = process.getTemplateId().split("_")[0];
				intent.setTemplateId(key_str);
				intent.setForSelect(0);
				intent.setName("trade_" + key_str + "_" + "解释开场白");
				intent.setId(null);
				String keywords = blankDomain.getStartExplainKeywords().replace("\n", "");//替换换行符
				intent.setKeywords(BotSentenceUtil.generateKeywords(keywords));
				intent.setDomainName(blankDomain.getLabel());
				botSentenceIntentMapper.insert(intent);
				
				branch.setIntents(intent.getId() + "");
				
				botSentenceBranchMapper.updateByPrimaryKey(branch);
				
			}else {
				String intentId = branch.getIntents().split(",")[0];
				BotSentenceIntent intent = botSentenceIntentMapper.selectByPrimaryKey(new Long(intentId));
				String newKeywords = blankDomain.getStartExplainKeywords().replace("\n", "");//替换换行符
				intent.setKeywords(BotSentenceUtil.generateKeywords(newKeywords));
				intent.setLstUpdateTime(new Date(System.currentTimeMillis()));
				intent.setLstUpdateUser(userId.toString());
				botSentenceIntentMapper.updateByPrimaryKeyWithBLOBs(intent);
			}
		}
	}
	
	
	/**
	 * 保存线条
	 */
	@Transactional
	@Override
	public void saveEdge(String processId, FlowEdge edge,Long userId) {
		if(null == edge ) {
			throw new CommonException("保存失败，请求参数不完整!");
		}
		
		if(StringUtils.isBlank(edge.getLabel())) {
			throw new CommonException("保存失败，线条名称为空!");
		}
		
		if(StringUtils.isBlank(edge.getType())) {
			throw new CommonException("保存失败，分支类型为空!");
		}
		
		if(StringUtils.isBlank(edge.getSource()) || StringUtils.isBlank(edge.getTarget())) {
			throw new CommonException("保存失败，线条[" + edge.getLabel() + "]未连接!");
		}
		
		/*if(StringUtils.isBlank(edge.getKeyWords())) {
			throw new CommonException("保存失败，关键字内容为!");
		}*/

		Date date = new Date(System.currentTimeMillis());
		BotSentenceProcess process = botSentenceProcessMapper.selectByPrimaryKey(processId);
		
		BotSentenceDomain source = botSentenceDomainMapper.selectByPrimaryKey(edge.getSource());
		BotSentenceDomain target = botSentenceDomainMapper.selectByPrimaryKey(edge.getTarget());
		
		logger.info("新增连线数据...");
		//在当前节点新增一个Branch
		BotSentenceBranch newBranch = new BotSentenceBranch();
			
		//获取新增分支的名称，统一以branch_开头，后面接数字
		String branchName = getNewBranchName(processId);
		String message = "";
		
		if(Constant.BRANCH_TYPE_NORMAL.equals(edge.getType())) {
			//新增一个意图
			BotSentenceIntent intent = new BotSentenceIntent();
			intent.setCrtTime(date);
			intent.setCrtUser(userId.toString());
			intent.setProcessId(processId);
			
			if(StringUtils.isNotBlank(edge.getKeyWords())) {
				String keywords = edge.getKeyWords().replace("\n", "");//替换换行符
				intent.setKeywords(keywords);
			}else {
				intent.setKeywords("[]");
			}
			
			String key_str = process.getTemplateId().split("_")[0];
			intent.setTemplateId(key_str);
			intent.setForSelect(0);
			intent.setName("trade_" + key_str + "_" + branchName + "_" + branchName);
			String new_key_words = "[";
			if(StringUtils.isNotBlank(edge.getKeyWords())) {
				String replaceKeyWords = edge.getKeyWords().replaceAll("，", ",");
				String []keywords_array = replaceKeyWords.split(",");
				
				//获取流程图中当前同级分支的关键字
				Map<String, String> keywords = new HashMap<>();
				
				//List<Long> intentIds = new ArrayList<>();
				BotSentenceBranchExample example = new BotSentenceBranchExample();
				example.createCriteria().andProcessIdEqualTo(processId).andIsShowEqualTo("1").andDomainEqualTo(source.getDomainName());
				List<BotSentenceBranch> list = botSentenceBranchMapper.selectByExample(example);
				
				//开场白于解释开场白分支关键字校验
				if(Constant.DOMAIN_TYPE_START.equals(source.getType())) {
					BotSentenceBranchExample startExample = new BotSentenceBranchExample();
					startExample.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo(source.getDomainName()).andNextEqualTo("解释开场白");
					List<BotSentenceBranch> startList = botSentenceBranchMapper.selectByExample(startExample);
					list.addAll(startList);
				}
				
				
				if(null != list && list.size() > 0) {
					for(BotSentenceBranch branch : list) {
						String intents = branch.getIntents();
						if(StringUtils.isNotBlank(intents)) {
							String [] array = intents.split(",");
							for(int i = 0 ; i < array.length ; i++) {
								//intentIds.add(new Long(array[i]));
								BotSentenceIntent temp = botSentenceIntentMapper.selectByPrimaryKey(new Long(array[i]));
								if(null != temp) {
									String[] keywordList = BotSentenceUtil.getKeywords(temp.getKeywords()).get(0).replace("\"", "").split(",");
									//List<String> keywordList = BotSentenceUtil.getKeywords(temp.getKeywords());
									if(null != keywordList && keywordList.length > 0) {
										for(String str : keywordList) {
											str = str.replace("\"", "");
											if(StringUtils.isNotBlank(branch.getLineName())) {
												keywords.put(str, branch.getLineName());
											}else {
												keywords.put(str, branch.getNext());
											}
										}
									}
								}
							}
						}
					}
				}
				
				//校验关键字是否重复
				//Map<String, String> keywords = getAllMainFlowKeywords(processId, intentIds);
				for(int j = 0 ; j < keywords_array.length ; j++) {
					if(keywords.containsKey(keywords_array[j])) {
						String repeat = "【" + keywords_array[j] + "】 与 【" + keywords.get(keywords_array[j]) + "】的关键字重复了";
						message = message + repeat + "<br/>";
						//throw new CommonException("关键字【" + keywords_array[j] + "】重复");
					}
					
					
					new_key_words = new_key_words + "\"" + keywords_array[j] + "\"" + ",";
				}
				new_key_words = new_key_words.substring(0, new_key_words.length() -1);
			}
			
			if(StringUtils.isNotBlank(message)) {
				throw new CommonException(message);
			}
			
			new_key_words = new_key_words+"]";
			intent.setKeywords(new_key_words);
			intent.setId(null);
			intent.setDomainName(source.getDomainName());
			
			botSentenceIntentMapper.insert(intent);
			newBranch.setEnd(target.getDomainName());
			newBranch.setIntents(intent.getId().toString());//保存意图ID
		}else if(Constant.BRANCH_TYPE_POSITIVE.equals(edge.getType())){
			//判断当前卡片向下是否已存在未拒绝的分支，如果存在，则不允许新增
			BotSentenceBranchExample example = new BotSentenceBranchExample();
			example.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo(source.getDomainName()).andTypeEqualTo(Constant.BRANCH_TYPE_POSITIVE);
			int num = botSentenceBranchMapper.countByExample(example);
			if(num > 0) {
				throw new CommonException("当前节点【" + source.getDomainName() + "】已存在'未拒绝'的分支");
			}
			branchName = "positive";
		}
		
		newBranch.setDomain(source.getDomainName());
		newBranch.setCrtTime(date);
		newBranch.setCrtUser(userId.toString());
		newBranch.setProcessId(processId);
		newBranch.setTemplateId(process.getTemplateId());
		newBranch.setResponse("[]");
		newBranch.setLineName(edge.getLabel());
		newBranch.setIsShow("1");//1-显示
		newBranch.setNext(target.getDomainName());
		newBranch.setBranchName(branchName);
		newBranch.setType(edge.getType());
		
		botSentenceBranchMapper.insert(newBranch);
			
		updateProcessState(processId,userId);
	}
	
	
	/**
	 * 更新连接线
	 */
	@Transactional
	public void updateEdge(String processId, FlowEdge edge, BotSentenceBranch botSentenceBranch, Map<String, String> map, String branchId,Long userId) {
		logger.info("更新连线数据");
		//获取原先该连线的source和target值，如有更改，则需要更新；如无更改，则不用处理
		BotSentenceDomain old_sourcedomain = getDomain(processId, botSentenceBranch.getDomain());
		BotSentenceDomain old_targetdomain = getDomain(processId, botSentenceBranch.getNext());
		
		BotSentenceDomain new_sourceDomain = botSentenceDomainMapper.selectByPrimaryKey(map.get(edge.getSource()));
		BotSentenceDomain new_targetDomain = botSentenceDomainMapper.selectByPrimaryKey(map.get(edge.getTarget()));
		
		//判断如果线的指和原来完全一致，则不需要处理
		if(old_sourcedomain.getDomainId().equals(new_sourceDomain.getDomainId()) && 
				old_targetdomain.getDomainId().equals(new_targetDomain.getDomainId())) {
			logger.info(botSentenceBranch.getBranchId() + "当前连接线不需要处理...");
		}else {
			//如果是source有变动，则需要修改原先连接线的domain为新的domain值
			if(!old_sourcedomain.getDomainId().equals(new_sourceDomain.getDomainId())) {
				botSentenceBranch.setDomain(new_sourceDomain.getDomainName());
				botSentenceBranch.setNext(new_targetDomain.getDomainName());
				logger.info("更新连线由"+ new_sourceDomain.getDomainName() + "---》 " + new_targetDomain.getDomainName());
			}
		}

		botSentenceBranch.setLineName(edge.getLabel());//更新线的名称
		botSentenceBranch.setLstUpdateTime(new Date(System.currentTimeMillis()));
		botSentenceBranch.setLstUpdateUser(userId.toString());
		
		
		processId = botSentenceBranch.getProcessId();
		
		if(StringUtils.isNotBlank(branchId) && branchId.equals(botSentenceBranch.getBranchId())) {//如果编辑的是当前连线，才需要更新意图关键字，其它情况只需要更新连线即可

			
			//获取同级分支的关键字列表
			Map<String, String> keywords = new HashMap<>(); 
					
			BotSentenceBranchExample branchexample = new BotSentenceBranchExample();
			branchexample.createCriteria().andProcessIdEqualTo(processId).andIsShowEqualTo("1").andDomainEqualTo(botSentenceBranch.getDomain()).andBranchIdNotEqualTo(botSentenceBranch.getBranchId());
			List<BotSentenceBranch> list = botSentenceBranchMapper.selectByExample(branchexample);
			
			//开场白于解释开场白分支关键字校验
			if(Constant.DOMAIN_TYPE_START.equals(new_sourceDomain.getType())) {
				BotSentenceBranchExample startExample = new BotSentenceBranchExample();
				startExample.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo(new_sourceDomain.getDomainName()).andNextEqualTo("解释开场白");
				List<BotSentenceBranch> startList = botSentenceBranchMapper.selectByExample(startExample);
				list.addAll(startList);
			}
			
			if(null != list && list.size() > 0) {
				for(BotSentenceBranch branch : list) {
					String intents = branch.getIntents();
					if(StringUtils.isNotBlank(intents)) {
						String [] array = intents.split(",");
						for(int i = 0 ; i < array.length ; i++) {
							BotSentenceIntent temp = botSentenceIntentMapper.selectByPrimaryKey(new Long(array[i]));
							if(null != temp) {
								String[] keywordList = BotSentenceUtil.getKeywords(temp.getKeywords()).get(0).replace("\"", "").split(",");
								if(null != keywordList && keywordList.length > 0) {
									for(String str : keywordList) {
										str = str.replace("\"", "");
										if(StringUtils.isNotBlank(branch.getLineName())) {
											keywords.put(str, branch.getLineName());
										}else {
											keywords.put(str, branch.getNext());
										}
									}
								}
							}
							
						}
					}
				}
			}
			
			
			if(Constant.BRANCH_TYPE_NORMAL.equals(edge.getType())) {//如果是一般类型
				String message = "";
				//如果原来有意图，则更新意图内容
				if(StringUtils.isNotBlank(botSentenceBranch.getIntents())) {
					//更新意图表的关键词
					String intent = botSentenceBranch.getIntents();
					if(StringUtils.isNotBlank(intent)) {
						BotSentenceIntent botSentenceIntent = botSentenceIntentMapper.selectByPrimaryKey(new Long(intent));
						
						List<Long> intentIds = new ArrayList<>();
						intentIds.add(new Long(intent));
						
						String new_key_words = "[";
						if(StringUtils.isNotBlank(edge.getKeyWords())) {
							String replaceKeyWords = edge.getKeyWords().replaceAll("，", ",");
							String []keywords_array = replaceKeyWords.split(",");
							
							//校验关键字是否重复
							for(int j = 0 ; j < keywords_array.length ; j++) {
								//Map<String, String> keywords = getAllMainFlowKeywords(processId, intentIds);
								if(keywords.containsKey(keywords_array[j])) {
									String repeat = "【" + keywords_array[j] + "】 与 【" + keywords.get(keywords_array[j]) + "】的关键字重复了";
									message = message + repeat + "<br/>";
								}
								
								new_key_words = new_key_words + "\"" + keywords_array[j] + "\"" + ",";
							}
							new_key_words = new_key_words.substring(0, new_key_words.length() -1);
						}
						
						if(StringUtils.isNotBlank(message)) {
							throw new CommonException(message);
						}
						
						new_key_words = new_key_words+"]";
						new_key_words = new_key_words.replace("\n", "");//替换换行符
						botSentenceIntent.setKeywords(new_key_words);
						botSentenceIntent.setLstUpdateTime(new Date(System.currentTimeMillis()));
						botSentenceIntent.setLstUpdateUser(userId.toString());
						botSentenceIntentMapper.updateByPrimaryKeyWithBLOBs(botSentenceIntent);
					}
				}else {//如果原来没有意图，则需要新增一条意图信息
					BotSentenceIntent intent = new BotSentenceIntent();
					intent.setCrtTime(new Date(System.currentTimeMillis()));
					intent.setCrtUser(userId.toString());
					intent.setProcessId(processId);
					intent.setKeywords(edge.getKeyWords());
					String key_str = old_sourcedomain.getTemplateId().split("_")[0];
					intent.setTemplateId(key_str);
					intent.setForSelect(0);
					intent.setName("trade_" + key_str + "_" + botSentenceBranch.getBranchName() + "_" + botSentenceBranch.getBranchName());
					String new_key_words = "[";
					if(StringUtils.isNotBlank(edge.getKeyWords())) {
						String replaceKeyWords = edge.getKeyWords().replaceAll("，", ",");
						replaceKeyWords = replaceKeyWords.replace("\n", "");//替换换行符
						
						String []keywords_array = replaceKeyWords.split(",");
						for(int j = 0 ; j < keywords_array.length ; j++) {
							//校验关键字是否重复
							//Map<String, String> keywords = getAllMainFlowKeywords(processId, null);
							if(keywords.containsKey(keywords_array[j])) {
								String repeat = "【" + keywords_array[j] + "】 与 【" + keywords.get(keywords_array[j]) + "】的关键字重复了";
								message = message + repeat + "<br/>";
							}
							
							new_key_words = new_key_words + "\"" + keywords_array[j] + "\"" + ",";
						}
						
						if(StringUtils.isNotBlank(message)) {
							throw new CommonException(message);
						}
						
						new_key_words = new_key_words.substring(0, new_key_words.length() -1);
					}
					
					new_key_words = new_key_words+"]";
					intent.setKeywords(new_key_words);
					intent.setId(null);
					intent.setDomainName(botSentenceBranch.getBranchName());
					
					botSentenceIntentMapper.insert(intent);
					
					botSentenceBranch.setIntents(intent.getId().toString());//保存意图ID
				
				}
				
				//如果原来是未拒绝，则需要更改branchname名称
				if(Constant.BRANCH_TYPE_POSITIVE.equals(botSentenceBranch.getType())) {
					//更新Branchname
					String branchName = getNewBranchName(processId);
					botSentenceBranch.setBranchName(branchName);
				}
				botSentenceBranch.setEnd(botSentenceBranch.getNext());
			}else if(Constant.BRANCH_TYPE_POSITIVE.equals(edge.getType())) {//如果是未拒绝
				//判断当前卡片向下是否已存在未拒绝的分支，如果存在，则不允许新增
				BotSentenceBranchExample example = new BotSentenceBranchExample();
				example.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo(botSentenceBranch.getDomain()).andTypeEqualTo(Constant.BRANCH_TYPE_POSITIVE);
				int num = botSentenceBranchMapper.countByExample(example);
				if(num > 0) {
					throw new CommonException("当前节点【" + botSentenceBranch.getDomain() + "】已存在'未拒绝'的分支");
				}
				
				//如果原来是一般类型，则需要删除意图信息
				if(StringUtils.isNotBlank(botSentenceBranch.getIntents())) {
					String []intents = botSentenceBranch.getIntents().split(",");
					for(int i = 0 ; i < intents.length ; i++) {
						botSentenceIntentMapper.deleteByPrimaryKey(new Long(intents[i]));
					}
					
					//更新Branchname
					botSentenceBranch.setBranchName("positive");
				}
				botSentenceBranch.setIntents(null);
				botSentenceBranch.setEnd(null);
			}
			
		}
		botSentenceBranch.setType(edge.getType());
		botSentenceBranchMapper.updateByPrimaryKey(botSentenceBranch);
		
	}
	
	
	/**
	 * 获取当前domain的主分支
	 * @param processId
	 * @param domain
	 * @return
	 */
	private BotSentenceBranch getMainBranch(String processId, String domain) {
		BotSentenceBranchExample mainExample = new BotSentenceBranchExample();
		mainExample.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo(domain).andBranchNameEqualTo("positive");
		List<BotSentenceBranch> mainBranchList = botSentenceBranchMapper.selectByExample(mainExample);
		if(null != mainBranchList && mainBranchList.size() > 0) {
			return mainBranchList.get(0);
		}
		return null;
	}
	
	private VoliceInfo getStartExplain(String processId) {
		//获取解释开场白
		BotSentenceBranchExample example = new BotSentenceBranchExample();
		example.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo("解释开场白").andBranchNameEqualTo("enter_branch");
		List<BotSentenceBranch> branchList = botSentenceBranchMapper.selectByExample(example);
		if(null != branchList && branchList.size() > 0) {
			BotSentenceBranch branch = branchList.get(0);
			//获取当前branch对应的话术内容
			String resp = branch.getResponse();
			if(StringUtils.isNotBlank(resp) && !"[]".equals(resp)) {
				String[] respArray = resp.substring(1,resp.length()-1).split(",");
				VoliceInfo volice = voliceInfoMapper.selectByPrimaryKey(new Long(respArray[0]));
				return volice;
			}
		}
		return null;
	}
	
	
	public BotSentenceBranch getStartExplainBranch(String processId) {
		//获取解释开场白
		BotSentenceBranchExample startExample = new BotSentenceBranchExample();
		startExample.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo("开场白").andNextEqualTo("解释开场白");
		List<BotSentenceBranch> startBranchList = botSentenceBranchMapper.selectByExample(startExample);
		
		if(null != startBranchList && startBranchList.size() > 0) {
			BotSentenceBranch branch = startBranchList.get(0);
			return branch;
		}
		return null;
	}
	
	
	/**
	 * 获取当前domain的enter_branch分支
	 * @param processId
	 * @param domain
	 * @return
	 */
	public BotSentenceBranch getEnterBranch(String processId, String domain) {
		BotSentenceBranchExample mainExample = new BotSentenceBranchExample();
		mainExample.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo(domain).andBranchNameEqualTo("enter_branch");
		List<BotSentenceBranch> mainBranchList = botSentenceBranchMapper.selectByExample(mainExample);
		if(null != mainBranchList && mainBranchList.size() > 0) {
			return mainBranchList.get(0);
		}
		return null;
	}
	
	
	/**
	 * 获取当前domain的positive分支
	 * @param processId
	 * @param domain
	 * @return
	 */
	private BotSentenceBranch getPositiveBranch(String processId, String domain) {
		BotSentenceBranchExample mainExample = new BotSentenceBranchExample();
		mainExample.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo(domain).andBranchNameEqualTo("positive");
		List<BotSentenceBranch> mainBranchList = botSentenceBranchMapper.selectByExample(mainExample);
		if(null != mainBranchList && mainBranchList.size() > 0) {
			return mainBranchList.get(0);
		}
		return null;
	}
	
	/**
	 * 生成新的lineName
	 * @param processId
	 * @return
	 */
	private String getNewLineName(String processId) {
		String lineName = line_prefix + "1";
		BotSentenceBranchExample queryExample = new BotSentenceBranchExample();
		queryExample.createCriteria().andProcessIdEqualTo(processId).andLineNameLike(line_prefix + "%");
		List<BotSentenceBranch> queryList = botSentenceBranchMapper.selectByExample(queryExample);
		if(null != queryList && queryList.size() > 0) {
			List<Integer> list = new ArrayList<>();
			for(BotSentenceBranch temp : queryList) {
				String currentName = temp.getLineName();
				int currentNum = new Integer(currentName.substring(line_prefix.length(), currentName.length()));
				list.add(currentNum);
			}
			
			int maxLineNum = Collections.max(list) + 1;
			lineName = line_prefix + maxLineNum;
		}
		return lineName;
	}
	
	
	/**
	 * 生成新的branchName
	 * @param processId
	 * @return
	 */
	private String getNewBranchName(String processId) {
		String branchName = branch_prefix + "1";
		List<String> queryList = botSentenceBranchExtMapper.querySpecialBranchoList(processId);
		
		if(null != queryList && queryList.size() > 0) {
			List<Integer> list = new ArrayList<>();
			for(String temp : queryList) {
				String currentName = temp;
				int currentNum = new Integer(currentName.substring(branch_prefix.length(), currentName.length()));
				list.add(currentNum);
			}
			
			int maxNameNum = Collections.max(list) + 1;
			branchName = branch_prefix + maxNameNum;
		}
		return branchName;
	}
	
	/**
	 * 生成新的domain名称
	 * @param processId
	 * @return
	 */
	private String getNewDomainName(String processId, boolean isMainFlow) {
		String blankDomainName = domain_prefix + "1";
		BotSentenceDomainExample example = new BotSentenceDomainExample();
		example.createCriteria().andProcessIdEqualTo(processId).andDomainNameLike(domain_prefix + "%");
		List<BotSentenceDomain> queryList = botSentenceDomainMapper.selectByExample(example);
		if(null != queryList && queryList.size() > 0) {
			List<Integer> list = new ArrayList<>();
			for(BotSentenceDomain temp : queryList) {
				String currentName = temp.getDomainName();
				int currentNum = new Integer(currentName.substring(domain_prefix.length(), currentName.length()));
				list.add(currentNum);
			}
			int maxNameNum = Collections.max(list) + 1;
			blankDomainName = domain_prefix + maxNameNum;
		}
		return blankDomainName;
	}

	
	/**
	 * 保存挽回话术，最多三个
	 */
	@Override
	@Transactional
	public void saveRefuseBranch(String processId, String domainName, List<String> voliceIdList,Long userId) {
		BotSentenceProcess process = botSentenceProcessMapper.selectByPrimaryKey(processId);
		
		if(StringUtils.isBlank(processId) || StringUtils.isBlank(domainName)) {
			throw new CommonException("保存挽回话术失败，请求参数不完整!");
		}
		
		if(null != voliceIdList && voliceIdList.size() > 0) {
			if(voliceIdList.size() > 3) {
				throw new CommonException("挽回话术超过3条!");
			}

			
			//先把原先挽回话术删除
			BotSentenceBranchExample deleteBranch = new BotSentenceBranchExample();
			deleteBranch.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo(domainName).andBranchNameLike("refuse_%");
			botSentenceBranchMapper.deleteByExample(deleteBranch);
			
			//新增挽回话术
			int index = 1;
			for(String temp : voliceIdList) {
				if(StringUtils.isBlank(temp)) {
					continue;
				}
				BotSentenceBranch branch = new BotSentenceBranch();
				branch.setResponse("[" + temp + "]");
				branch.setRespname(domainName + "-" + "挽回" + index);
				branch.setBranchName("refuse_" + domainName);
				branch.setCrtTime(new Date(System.currentTimeMillis()));
				branch.setCrtUser(userId.toString());
				branch.setDomain(domainName);
				branch.setIsShow("0");
				branch.setNext(domainName);
				branch.setEnd("结束");
				branch.setTemplateId(process.getTemplateId());
				branch.setProcessId(processId);
				
				//获取意图信息：来源：domin为拒绝的negative分支的intent
				BotSentenceBranchExample intentExample = new BotSentenceBranchExample();
				intentExample.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo("拒绝").andBranchNameEqualTo("negative");
				List<BotSentenceBranch> intentList = botSentenceBranchMapper.selectByExample(intentExample);
				if(null != intentList && intentList.size() > 0) {
					branch.setIntents(intentList.get(0).getIntents());
				}
				
				botSentenceBranchMapper.insert(branch);
				index++;
			}
			
			
			
			/*String resp = "[";
			String respname = "[";
			for(String temp : voliceIdList) {
				if(StringUtils.isBlank(temp)) {
					continue;
				}
				resp = resp + temp + ",";
				
				VoliceInfo volice = voliceInfoMapper.selectByPrimaryKey(new Long(temp));
				respname = respname + volice.getName() + ",";
				
			}
			resp = "[" + resp.substring(0, resp.length() -1) + "]";
			respname = "[" + respname.substring(0, respname.length() -1) + "]";
			
			String userId = UserUtil.getUserId();
			BotSentenceBranchExample example = new BotSentenceBranchExample();
			example.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo(domainName).andBranchNameEqualTo("refuse_" + domainName);
			List<BotSentenceBranch> list = botSentenceBranchMapper.selectByExample(example);
			if(null != list && list.size() > 0) {
				branch = list.get(0);
				branch.setResponse(resp);
				branch.setRespname(respname);
				branch.setLstUpdateTime(new Date(System.currentTimeMillis()));
				branch.setLstUpdateUser(userId);
				botSentenceBranchMapper.updateByPrimaryKey(branch);
			}else {
				branch.setResponse(resp);
				branch.setRespname(respname);
				branch.setBranchName("refuse_" + domainName);
				branch.setCrtTime(new Date(System.currentTimeMillis()));
				branch.setCrtUser(userId);
				branch.setDomain(domainName);
				branch.setIsShow("0");
				branch.setNext(domainName);
				branch.setEnd("结束");
				branch.setTemplateId(process.getTemplateId());
				branch.setProcessId(processId);
				
				//获取意图信息：来源：domin为拒绝的enter分支的intent
				BotSentenceBranchExample intentExample = new BotSentenceBranchExample();
				intentExample.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo("拒绝").andBranchNameEqualTo("enter_branch");
				List<BotSentenceBranch> intentList = botSentenceBranchMapper.selectByExample(intentExample);
				if(null != intentList && intentList.size() > 0) {
					branch.setIntents(intentList.get(0).getIntents());
				}
				
				botSentenceBranchMapper.insert(branch);
			}*/
			
		}else {
			throw new CommonException("挽回话术列表为空!");
		}
		
		updateProcessState(processId,userId);
		
	}

	@Override
	@Transactional
	public void deleteRefuseBranch(String processId, String domainName, String voliceId) {
		BotSentenceBranchExample example = new BotSentenceBranchExample();
		//example.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo(domainName).andBranchNameEqualTo("refuse_" + domainName).andResponseLike("%"+voliceId+"%");
		example.createCriteria().andProcessIdEqualTo(processId).andBranchNameLike("refuse_%").andResponseLike("%"+voliceId+"%");
		botSentenceBranchMapper.deleteByExample(example);
		
		
		/*List<BotSentenceBranch> list = botSentenceBranchMapper.selectByExample(example);
		if(null != list && list.size() > 0) {
			BotSentenceBranch refuseBranch = list.get(0);
			String resp = refuseBranch.getResponse();
			if(StringUtils.isNotBlank(resp) && !"[]".equals(resp.trim()) && resp.trim().startsWith("[") && resp.trim().endsWith("]")) {
				String[] respArray = resp.substring(1,resp.length()-1).split(",");
				String resp_new = "[";
				int index = -1;
				
				if(respArray.length ==1) {
					resp_new = "[]";
				}else {
					for(int i = 0 ; i < respArray.length ; i++) {
						if(voliceId.equals(respArray[i])) {
							index = i;
						}else {
							resp_new = resp_new + respArray[i] + ",";
						}
					}
					resp_new = resp_new.substring(0, resp_new.length() -1)  + "]";
				}
				
				
				//更新resp和respname值
				String respname = refuseBranch.getRespname();
				String respname_new = "[";
				if(StringUtils.isNotBlank(respname) && !"[]".equals(respname.trim()) && respname.trim().startsWith("[") && respname.trim().endsWith("]")) {
					String[] respnameArray = respname.substring(1,respname.length()-1).split(",");
					
					if(respnameArray.length == 1) {
						respname_new = "[]";
					}else {
						for(int i = 0 ; i < respnameArray.length ; i++) {
							if(i != index) {
								respname_new = respname_new + respnameArray[i] + ",";
							}
						}
					}
				}
				respname_new = respname_new.substring(0, respname_new.length() -1)  + "]";
				
				refuseBranch.setResponse(resp_new);
				refuseBranch.setRespname(respname_new);
				botSentenceBranchMapper.updateByPrimaryKey(refuseBranch);
		
			}
		}*/
	}

	@Override
	@Transactional
	public void deleteDomain(String processId, String domainId) {
		List<String> domainNames = new ArrayList<>();
		
		//删除domain本身
		BotSentenceDomain domain = botSentenceDomainMapper.selectByPrimaryKey(domainId);
		domainNames.add(domain.getDomainName());
		
		if(null != domainNames && domainNames.size() > 0) {
			//删除branch信息
			BotSentenceBranchExample branchExample = new BotSentenceBranchExample();
			branchExample.createCriteria().andProcessIdEqualTo(processId).andDomainIn(domainNames);
			botSentenceBranchMapper.deleteByExample(branchExample);
			
			//删除原来其它domain指向当前节点的next数据
			BotSentenceBranchExample branchExample2 = new BotSentenceBranchExample();
			branchExample2.createCriteria().andProcessIdEqualTo(processId).andNextIn(domainNames);
			botSentenceBranchMapper.deleteByExample(branchExample2);
			
			//删除录音信息
			VoliceInfoExample voliceExample = new VoliceInfoExample();
			voliceExample.createCriteria().andProcessIdEqualTo(processId).andDomainNameIn(domainNames);
			voliceInfoMapper.deleteByExample(voliceExample);
			
			//删除意图信息
			BotSentenceIntentExample intentExample = new BotSentenceIntentExample();
			intentExample.createCriteria().andProcessIdEqualTo(processId).andDomainNameIn(domainNames);
			botSentenceIntentMapper.deleteByExample(intentExample);
		}
		botSentenceDomainMapper.deleteByPrimaryKey(domainId);
	}

	@Override
	@Transactional
	public void deleteBranch(String processId, String branchId) {
		//删除branch本身
		//BotSentenceBranch branch = botSentenceBranchMapper.selectByPrimaryKey(branchId);
		botSentenceBranchMapper.deleteByPrimaryKey(branchId);
		//String userId = UserUtil.getUserId();
		//如果当前branch为positive，则更新domain表的com_domain为空
		/*if("positive".equals(branch.getBranchName())) {
			BotSentenceDomainExample updateDomainExample = new BotSentenceDomainExample();
			updateDomainExample.createCriteria().andProcessIdEqualTo(processId).andComDomainEqualTo(branch.getDomain());
			List<BotSentenceDomain> domains = botSentenceDomainMapper.selectByExample(updateDomainExample);
			if(null != domains && domains.size() > 0) {
				BotSentenceDomain domain = domains.get(0);
				domain.setComDomain(null);
				domain.setLstUpdateTime(new Date(System.currentTimeMillis()));
				domain.setLstUpdateUser(userId);
				botSentenceDomainMapper.updateByPrimaryKey(domain);
			}
		}*/
	}

	
	public BotSentenceDomain getDomain(String processId, String domainName) {
		BotSentenceDomainExample domaninExample = new BotSentenceDomainExample();
		domaninExample.createCriteria().andProcessIdEqualTo(processId).andDomainNameEqualTo(domainName);
		List<BotSentenceDomain> domainList = botSentenceDomainMapper.selectByExample(domaninExample);
		if(null != domainList && domainList.size() > 0) {
			return domainList.get(0);
		}
		return null;
	}
	
	
	public List<BotSentenceDomain> getChildDomainList(String domainId) {
		BotSentenceDomain domain = botSentenceDomainMapper.selectByPrimaryKey(domainId);
		
		BotSentenceDomainExample domaninExample = new BotSentenceDomainExample();
		domaninExample.createCriteria().andProcessIdEqualTo(domain.getProcessId()).andCategoryEqualTo("1").andIsMainFlowEqualTo("01");
		List<BotSentenceDomain> domainList = botSentenceDomainMapper.selectByExample(domaninExample);
		if(null != domainList && domainList.size() > 0) {
			return domainList;
		}
		return null;
	}

	/*@Override
	@Transactional
	public String deleteDomainSimple(String domainId) {
		//获取当前domain向下的所有domain列表，并逐个删除
		List<BotSentenceBranch> list = new ArrayList<>(); 
		List<String> domainIdList = new ArrayList<>();
				
		BotSentenceDomain domain = botSentenceDomainMapper.selectByPrimaryKey(domainId);
		String processId = domain.getProcessId();
		
		//添加当前domain本身
		domainIdList.add(domain.getDomainName());
		
		if(StringUtils.isBlank(domain.getComDomain())) {
			deleteOneDomain(processId, domainId);
		}else {
			//判断是否有其它domain与当前domain指向同一个下级节点，如果有，则只删除当前domain本身，如果没有，则删除向下所有节点
			BotSentenceDomainExample existExample = new BotSentenceDomainExample();
			existExample.createCriteria().andProcessIdEqualTo(processId).andComDomainEqualTo(domain.getComDomain()).andCategoryEqualTo("1");
			int num = botSentenceDomainMapper.countByExample(existExample);
			if(num > 1) {
				deleteOneDomain(processId, domainId);
			}else {
				//先查询当前domain所有指向的next节点
				BotSentenceBranchExample branchExample = new BotSentenceBranchExample();
				branchExample.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo(domain.getDomainName());
				list = botSentenceBranchMapper.selectByExample(branchExample);

				while(null != list && list.size() > 0) {
					
					if(null != list && list.size() > 0) {
						List<String> domainNameList = new ArrayList<>();
						
						for(BotSentenceBranch branch : list) {
							if(StringUtils.isNotBlank(branch.getNext())) {
								
								if(!domainIdList.contains(branch.getNext())) {
									domainIdList.add(branch.getNext());
									domainNameList.add(branch.getNext());
								}
							}
						}
						if(domainNameList.size()>0) {
							BotSentenceBranchExample branchExample2 = new BotSentenceBranchExample();
							branchExample2.createCriteria().andProcessIdEqualTo(processId).andDomainIn(domainNameList);
							list = botSentenceBranchMapper.selectByExample(branchExample2);
						}else {
							break;
						}
					}
				}
				
				BotSentenceDomainExample domainExample = new BotSentenceDomainExample();
				domainExample.createCriteria().andProcessIdEqualTo(processId).andDomainNameIn(domainIdList).andCategoryEqualTo("1");
				List<BotSentenceDomain> domain_list = botSentenceDomainMapper.selectByExample(domainExample);
				if(null != domain_list && domain_list.size() > 0) {
					for(BotSentenceDomain temp : domain_list) {
						deleteOneDomain(processId, temp.getDomainId());
					}
				}
			}
		}
			
		
		
		return processId;
	}*/
	
	
	@Override
	@Transactional
	public String deleteDomainSimple(String domainId) {
		//获取当前domain向下的所有domain列表，并逐个删除
		List<String> domainIdList = new ArrayList<>();
				
		BotSentenceDomain domain = botSentenceDomainMapper.selectByPrimaryKey(domainId);
		String processId = domain.getProcessId();
		
		//添加当前domain本身
		domainIdList.add(domain.getDomainId());
		
		//查询当前节点是否有下级节点
		BotSentenceDomainExample existChildExample = new BotSentenceDomainExample();
		existChildExample.createCriteria().andProcessIdEqualTo(processId).andParentEqualTo(domain.getDomainName());
		List<BotSentenceDomain> childList = botSentenceDomainMapper.selectByExample(existChildExample);
		if(null != childList && childList.size() > 0) {
			for(BotSentenceDomain temp : childList) {
				domainIdList.add(temp.getDomainId());//添加当前节点的直属下级节点
			}
		}
		 
		
		if(null == childList || childList.size() == 0) {//当前节点没有下级节点，则删除当前节点本身
			deleteOneDomain(processId, domainId);
		}else {

			while(null != childList && childList.size() > 0) {
				
				List<String> domainNameList = new ArrayList<>();
				
				for(BotSentenceDomain temp : childList) {
					if(!domainIdList.contains(temp.getDomainId())) {
						domainIdList.add(temp.getDomainId());
					}
					
					if(!domainNameList.contains(temp.getDomainName())) {
						domainNameList.add(temp.getDomainName());
					}
					
				}
				if(domainNameList.size()>0) {
					BotSentenceDomainExample tempExample = new BotSentenceDomainExample();
					tempExample.createCriteria().andProcessIdEqualTo(processId).andParentIn(domainNameList);
					childList = botSentenceDomainMapper.selectByExample(tempExample);
				}else {
					break;
				}
			}
			
			for(String tempDomainId : domainIdList) {
				logger.info("删除节点: " + tempDomainId);
				deleteOneDomain(processId, tempDomainId);
			}
			
		}
			
		
		
		return processId;
	}
	
	
	private void deleteOneDomain(String processId, String domainId) {
		//删除domain本身
		BotSentenceDomain domain = botSentenceDomainMapper.selectByPrimaryKey(domainId);
		botSentenceDomainMapper.deleteByPrimaryKey(domainId);
		
		//删除原来comdomain指向当前domain的数据
		/*BotSentenceDomainExample domaninExample = new BotSentenceDomainExample();
		domaninExample.createCriteria().andProcessIdEqualTo(processId).andComDomainEqualTo(domain.getDomainName());
		List<BotSentenceDomain> domainList = botSentenceDomainMapper.selectByExample(domaninExample);
		if(null != domainList && domainList.size() > 0) {
			for(BotSentenceDomain temp : domainList) {
				temp.setComDomain(null);
				temp.setLstUpdateTime(new Date(System.currentTimeMillis()));
				temp.setLstUpdateUser(UserUtil.getUserId());
				botSentenceDomainMapper.updateByPrimaryKey(temp);
			}
		}*/
		
		
		
		//删除branch信息
		BotSentenceBranchExample branchExample = new BotSentenceBranchExample();
		branchExample.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo(domain.getDomainName());
		botSentenceBranchMapper.deleteByExample(branchExample);
		
		//删除录音信息
		VoliceInfoExample voliceExample = new VoliceInfoExample();
		voliceExample.createCriteria().andProcessIdEqualTo(processId).andDomainNameEqualTo(domain.getDomainName());
		voliceInfoMapper.deleteByExample(voliceExample);
		
		//删除意图信息
		BotSentenceIntentExample intentExample = new BotSentenceIntentExample();
		intentExample.createCriteria().andProcessIdEqualTo(processId).andDomainNameEqualTo(domain.getDomainName());
		botSentenceIntentMapper.deleteByExample(intentExample);
		
		//删除原来其它domain指向当前节点的next数据
		BotSentenceBranchExample branchExample2 = new BotSentenceBranchExample();
		branchExample2.createCriteria().andProcessIdEqualTo(processId).andNextEqualTo(domain.getDomainName());
		botSentenceBranchMapper.deleteByExample(branchExample2);
		
	}

	@Override
	public void updateProcessState(String processId,Long userId) {
		BotSentenceProcess process = botSentenceProcessMapper.selectByPrimaryKey(processId);
		if(Constant.APPROVE_PASS.equals(process.getState()) || Constant.APPROVE_NOTPASS.equals(process.getState()) || Constant.APPROVE_ONLINE.equals(process.getState())) {
			process.setState(Constant.APPROVE_MAEKING);
		}
		process.setLstUpdateTime(new Date(System.currentTimeMillis()));
		process.setLstUpdateUser(userId.toString());
		botSentenceProcessMapper.updateByPrimaryKey(process);
	}

	@Override
	public String getEndDomainName(String processId, String domainId) {
		List<Integer> seqList = new ArrayList<>();
		//查询当前节点是否有下级节点
		BotSentenceDomainExample example = new BotSentenceDomainExample();
		example.createCriteria().andProcessIdEqualTo(processId).andDomainNameLike("%结束_%").andCategoryEqualTo("1");
		List<BotSentenceDomain> list = botSentenceDomainMapper.selectByExample(example);
		if(null != list && list.size() > 0) {
			for(BotSentenceDomain temp : list) {
				try {
					seqList.add(new Integer(temp.getDomainName().split("_")[1]));
				}catch(NumberFormatException e) {
					continue;
				}
			}
		}else {
			return "结束_1";
		}
		Integer seq = Collections.max(seqList) + 1;
		return "结束_"+seq;
	}

	@Override
	public FlowInfoVO initFlowInfo(String processId) {
		FlowInfoVO flow = new FlowInfoVO();
		
		List<FlowNode> nodeList = new ArrayList<>();
		
		List<FlowEdge> edgeList = new ArrayList<>();
		
		
		
		
		BotSentenceDomainExample example = new BotSentenceDomainExample();
		example.createCriteria().andProcessIdEqualTo(processId).andCategoryEqualTo("1");
		List<BotSentenceDomain> levelList = botSentenceDomainMapper.selectByExample(example);
		
		if(null != levelList && levelList.size() > 0) {
			//获取解释开场白
			VoliceInfo startExplainVolice = getStartExplain(processId);
			for(BotSentenceDomain temp : levelList) {
				
				FlowNode node = new FlowNode();
				node.setId(temp.getDomainId());
				node.setLabel(temp.getDomainName());
				node.setX(temp.getPositionX());
				node.setY(temp.getPositionY());
				
				//DomainVO vo = new DomainVO();
				node.setType(temp.getType());//节点类型
				//node.setDomainName(temp.getDomainName());//设置domain名称
				node.setLabel(temp.getDomainName());//设置domain名称
				//如果当前节点为场白，则把解释开场白作为开场白的的一个属性
				if(null != startExplainVolice && Constant.DOMAIN_TYPE_START.equals(temp.getType())) {
					node.setStartExplainText(startExplainVolice.getContent());
					node.setStartExplainUrl(startExplainVolice.getVoliceUrl());
					
					//如果当前节点是开始节点，则查询解释开场白的意图信息
					BotSentenceBranch branch = getStartExplainBranch(processId);
					
					if(null != branch && StringUtils.isNotBlank(branch.getIntents())) {
						String intentId = branch.getIntents().split(",")[0];
						BotSentenceIntent intent = botSentenceIntentMapper.selectByPrimaryKey(new Long(intentId));
						node.setStartExplainKeywords(BotSentenceUtil.generateShowKeywords(intent.getKeywords()));
					}
					
					//设置解释开场白的挽回列表add by 2018-09-29
					node.setStartExplainDomainName("解释开场白");
					
					List<RefuseBranchVO> refuses = new ArrayList<>();
					BotSentenceBranchExample refuseBranchExample = new BotSentenceBranchExample();
					refuseBranchExample.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo("解释开场白").andBranchNameEqualTo("refuse_解释开场白");
					List<BotSentenceBranch> refuseBranchs = botSentenceBranchMapper.selectByExample(refuseBranchExample);
					if(null != refuseBranchs && refuseBranchs.size() > 0) {
						for(BotSentenceBranch refuseBranch : refuseBranchs) {
							String respStr = refuseBranch.getResponse();
							
							if(StringUtils.isNotBlank(respStr) && !"[]".equals(respStr.trim()) && respStr.trim().startsWith("[") && respStr.trim().endsWith("]")) {
								String resp = respStr.substring(1,respStr.length()-1);
								long voliceId_new = new Long(resp);
								VoliceInfo volice = voliceInfoMapper.selectByPrimaryKey(voliceId_new);
								if(null != volice) {
									RefuseBranchVO refuse = new RefuseBranchVO();
									refuse.setContent(volice.getContent());
									refuse.setVoliceId(volice.getVoliceId().toString());
									refuse.setName(volice.getName());
									refuses.add(refuse);
								}
							}
						}
					}
					node.setStartExplainRefuses(refuses);
				}
				
				
				//查询当前domain的enter话术branch
				BotSentenceBranchExample branchExample = new BotSentenceBranchExample();
				branchExample.createCriteria().andDomainEqualTo(temp.getDomainName()).andProcessIdEqualTo(processId).andBranchNameEqualTo("enter_branch");
				List<BotSentenceBranch> branchList = botSentenceBranchMapper.selectByExample(branchExample);
				if(null != branchList && branchList.size() > 0) {
					BotSentenceBranch branch = branchList.get(0);
					//获取当前branch对应的话术内容
					String resp = branch.getResponse();
					if(StringUtils.isNotBlank(resp) && !"[]".equals(resp)) {
						String[] respArray = resp.substring(1,resp.length()-1).split(",");
						VoliceInfo volice = voliceInfoMapper.selectByPrimaryKey(new Long(respArray[0]));
						node.setContent(volice.getContent());//设置文案内容
						node.setContentUrl(volice.getVoliceUrl());//设置录音URL
					}
				}
				
				//获取当前domain的挽回话术列表
				List<RefuseBranchVO> refuses = new ArrayList<>();
				BotSentenceBranchExample refuseBranchExample = new BotSentenceBranchExample();
				refuseBranchExample.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo(temp.getDomainName()).andBranchNameEqualTo("refuse_" + temp.getDomainName());
				List<BotSentenceBranch> refuseBranchs = botSentenceBranchMapper.selectByExample(refuseBranchExample);
				if(null != refuseBranchs && refuseBranchs.size() > 0) {
					for(BotSentenceBranch refuseBranch : refuseBranchs) {
						String respStr = refuseBranch.getResponse();
						
						if(StringUtils.isNotBlank(respStr) && !"[]".equals(respStr.trim()) && respStr.trim().startsWith("[") && respStr.trim().endsWith("]")) {
							String resp = respStr.substring(1,respStr.length()-1);
							long voliceId_new = new Long(resp);
							VoliceInfo volice = voliceInfoMapper.selectByPrimaryKey(voliceId_new);
							if(null != volice) {
								RefuseBranchVO refuse = new RefuseBranchVO();
								refuse.setContent(volice.getContent());
								refuse.setVoliceId(volice.getVoliceId().toString());
								refuse.setName(volice.getName());
								refuses.add(refuse);
							}
						}
					}
				}
				node.setRefuses(refuses);
				node.setShape("add_node");
				node.setProcessId(processId);
				
				nodeList.add(node);

				//查询当前domain的连线branch
				BotSentenceBranchExample lineExample = new BotSentenceBranchExample();
				lineExample.createCriteria().andDomainEqualTo(temp.getDomainName()).andProcessIdEqualTo(processId).andIsShowEqualTo("1");
				lineExample.setOrderByClause(" crt_time");
				List<BotSentenceBranch> lineBranchList = botSentenceBranchMapper.selectByExample(lineExample);
				if(null != lineBranchList && lineBranchList.size() > 0) {
					
					for(BotSentenceBranch lineBranch : lineBranchList) {
						FlowEdge edge = new FlowEdge();
						
						String intents = lineBranch.getIntents();
						if(StringUtils.isNotBlank(intents)) {
							String intent = intents.split(",")[0];
							BotSentenceIntent botSentenceIntent = botSentenceIntentMapper.selectByPrimaryKey(new Long(intent));
							
							String keyword = botSentenceIntent.getKeywords();
							
							if(StringUtils.isNotBlank(keyword)) {

								List<String> keyword_list = BotSentenceUtil.getKeywords(keyword);
								if(null != keyword_list && keyword_list.size() > 0) {
									//edge.setText(keyword_list.get(0).replace("\"", ""));
									edge.setKeyWords(keyword_list.get(0).replace("\"", ""));
								}
							}
						}
						
						edge.setId(lineBranch.getBranchId());
						edge.setSource(temp.getDomainId());
						edge.setLabel(lineBranch.getLineName());
						edge.setProcessId(processId);
						edge.setType(lineBranch.getType());
						for(BotSentenceDomain parentDomain : levelList) {
							if(parentDomain.getDomainName().equals(lineBranch.getNext())) {
								edge.setTarget(parentDomain.getDomainId());
							}
						}
						edgeList.add(edge);
					}
				}
			}
		}
		
		flow.setNodes(nodeList);
		flow.setEdges(edgeList);
		flow.setProcessId(processId);
		
		return flow;
	}

	/**
	 * 保存完整流程信息,包括新建卡片，新建连线，坐标信息
	 */
	@Override
	@Transactional
	public void saveFlow(FlowInfoVO flow,Long userId) {
		Map<String, String> map = new HashMap<>();
		
		//流程图校验
		//1.两个节点之间只能有一条线、自己不能连接自己
		List<String> sources = new ArrayList<>();
		List<String> targets = new ArrayList<>();
		
		if(null != flow.getEdges() && flow.getEdges().size() > 0) {
			List<String> list = new ArrayList<>();
			for(FlowEdge edge : flow.getEdges()) {
				if(list.contains(edge.getSource()+edge.getTarget()) || list.contains(edge.getTarget() + edge.getSource())) {
					throw new CommonException("分支[" + edge.getLabel() + "]连线重复，两个节点之间只能有一条连线!");
				}
				
				if(edge.getSource().equals(edge.getTarget())) {
					throw new CommonException("分支[" + edge.getLabel() + "]不能自己连接自己!");
				}
				
				sources.add(edge.getSource());
				targets.add(edge.getTarget());
				list.add(edge.getSource()+edge.getTarget());
			}
		}
		
		
		//3.结束节点不能有分支连线，其它节点不能指向开始节点
		if(null != flow.getNodes() && flow.getNodes().size() > 0) {
			for(FlowNode node : flow.getNodes()) {
				if(Constant.DOMAIN_TYPE_END.equals(node.getType()) && sources.contains(node.getId())) {
					throw new CommonException("节点[" + node.getLabel() + "]是结束节点，不能有分支连线!");
				}
				
				if(Constant.DOMAIN_TYPE_START.equals(node.getType()) && targets.contains(node.getId())) {
					throw new CommonException("节点[" + node.getLabel() + "]是开始节点，不能指向它!");
				}
			}
		}
		
		
		//4.非开场白节点，其它节点都应该有其它节点指向它
		/*if(null != flow.getNodes() && flow.getNodes().size() > 0) {
			//处理卡片信息
			for(FlowNode node : flow.getNodes()) {
				if(!targets.contains(node.getId()) && !Constant.DOMAIN_TYPE_START.equals(node.getType())) {
					throw new CommonException("节点[" + node.getLabel() + "]连线不完整!");
				}
			}
		}*/
		
		
		
		if(null != flow.getNodes() && flow.getNodes().size() > 0) {
			//处理卡片信息
			for(FlowNode node : flow.getNodes()) {
				String id = node.getId();
				//判断当前ID是否为前台自动生成，如是，则为新建卡片
				BotSentenceDomain domain = botSentenceDomainMapper.selectByPrimaryKey(id);
				if(null == domain) {//为新增节点
					//新建卡片 
					//node.setDomainName(node.getLabel());
					node.setProcessId(flow.getProcessId());
					BotSentenceDomain newdomain = saveNode(node,userId);
					map.put(node.getId(), newdomain.getDomainId());
				
				}else {//更新节点
					//更新卡片坐标信息
					node.setProcessId(flow.getProcessId());
					domain = updateNode(node, flow.getDomainId(),userId);
					map.put(node.getId(), domain.getDomainId());
				}
				
				//保存/更新开场白信息
				saveStartExplain(node, flow.getDomainId(),userId);
				
			}
		}
		
		if(null != flow.getEdges() && flow.getEdges().size() > 0) {
			//处理连接线信息
			for(FlowEdge edge : flow.getEdges()) {
				BotSentenceBranch branch = botSentenceBranchMapper.selectByPrimaryKey(edge.getId());
				if(null == branch) {//新建连线
					FlowEdge newEdge = new FlowEdge();
					BeanUtil.copyProperties(edge, newEdge);
					newEdge.setSource(map.get(edge.getSource()));
					newEdge.setTarget(map.get(edge.getTarget()));
					saveEdge(flow.getProcessId(), newEdge,userId);
				}else {//更新连线的指向
					updateEdge(flow.getProcessId(), edge, branch, map, flow.getBranchId(),userId);
				}
			}
		}
		updateProcessState(flow.getProcessId(),userId);
	}
	
	
	public Map<String, String> getAllMainFlowKeywords(String processId, List<Long> intentIds){
		Map<String, String> map = new HashMap<>();
		//List<String> list = new ArrayList<>();
		
		List<BusinessAnswerTaskExt> businessAnswerList = businessAnswerTaskService.queryBusinessAnswerListByPage(processId);
		
		BotSentenceBranchExample example = new BotSentenceBranchExample();
		example.createCriteria().andProcessIdEqualTo(processId);
		List<BotSentenceBranch> branchList = botSentenceBranchMapper.selectByExample(example);
		if(null != branchList && branchList.size() > 0) {
			for(BotSentenceBranch branch : branchList) {
				if(StringUtils.isNotBlank(branch.getIntents())) {
					String [] array = branch.getIntents().split(",");
					for(int i = 0 ; i < array.length ; i++) {
						BotSentenceIntent intent = botSentenceIntentMapper.selectByPrimaryKey(new Long(array[i]));
						if(null != intentIds && intentIds.contains(intent.getId())) {//如果为当前需要过滤的意图，则跳过
							continue;
						}
						List<String> keywordList = BotSentenceUtil.getKeywords(intent.getKeywords());
						System.out.println(keywordList.get(0));
						String []keyword_array = keywordList.get(0).split(",");
						for(int j = 0 ; j < keyword_array.length ; j++) {
							if(StringUtils.isNotBlank(keyword_array[j])) {
								//list.add(keyword_array[j]);
								if(StringUtils.isNotBlank(branch.getLineName())) {
									map.put(keyword_array[j].replace("\"", ""), branch.getDomain() + "(" + branch.getLineName() + ")");
								}else {
									if("一般问题".equals(branch.getDomain())) {
										for(BusinessAnswerTaskExt businessAnswer: businessAnswerList) {
											if(branch.getBranchId().equals(businessAnswer.getBranchId())) {
												map.put(keyword_array[j].replace("\"", ""), branch.getDomain() + businessAnswer.getIndex());
											}
										}
										
									}else {
										map.put(keyword_array[j].replace("\"", ""), branch.getDomain());
									}
									
								}
								
							}
						}
					}
				}
			}
		}
		return map;
	}


	@Override
	public boolean queryTTSStatus(List<VoliceInfoExt> list, String processId) {
		boolean flag = false;
		
		if(null != list && list.size() > 0) {
			List<String> voliceIds = new ArrayList<>();
			for(VoliceInfoExt volice : list) {
				voliceIds.add(volice.getVoliceId());
			}
			
			BotSentenceTtsTaskExample example = new BotSentenceTtsTaskExample();
			example.createCriteria().andProcessIdEqualTo(processId).andStatusEqualTo(Constant.TTS_TASK_ING);
			int unfinish_num = botSentenceTtsTaskMapper.countByExample(example);
			logger.info("还剩下" + unfinish_num + "个未合成...");
			if(unfinish_num == 0) {
				flag = true;
			}
		}else {
			flag = true;
		}
		
		return flag;
	}

	@Override
	public void saveSoundType(String processId, String soundType,Long userId) {
		BotSentenceProcess process = botSentenceProcessMapper.selectByPrimaryKey(processId);
		process.setSoundType(soundType);
		process.setState(Constant.APPROVE_MAEKING);
		process.setLstUpdateTime(new Date(System.currentTimeMillis()));
		process.setLstUpdateUser(userId.toString());
		botSentenceProcessMapper.updateByPrimaryKey(process);
	}

	@Override
	public BotSentenceProcess queryBotsentenceProcessInfo(String processId) {
		return botSentenceProcessMapper.selectByPrimaryKey(processId);
	}

	@Override
	public BotSentenceIntent queryKeywordsListByBranchId(String branchId) {
		BotSentenceIntent result = new BotSentenceIntent();
		BotSentenceBranch branch = botSentenceBranchMapper.selectByPrimaryKey(branchId);
		if(null != branch && StringUtils.isNotBlank(branch.getIntents())) {
			String intentId=branch.getIntents().split(",")[0].trim();
			if(intentId!=null){
				BotSentenceIntent intent = botSentenceIntentMapper.selectByPrimaryKey(new Long(intentId));
				if(null != intent) {
					result.setId(intent.getId());
					String keywords=intent.getKeywords();
					if(org.apache.commons.lang.StringUtils.isNotBlank(keywords)) {
						List<String> keywordList = BotSentenceUtil.getKeywords(keywords);
						if(null != keywordList && keywordList.size() > 0) {
							result.setKeywords(keywordList.get(0).replace("\"", ""));
						}
					}
				}
			}
		}
		
		return result;
	}

	@Autowired
	private BotSentenceProcessExtMapper botSentenceProcessExtMapper;
	
	@Override
	public List<BotSentenceProcess> getTemplateBySelf(String accountNo) {
		return botSentenceProcessExtMapper.getTemplateBySelf(accountNo);
	}
	
	@Override
	public List<BotSentenceProcess> getTemplateById(String templateId) {
		return botSentenceProcessExtMapper.getTemplateById(templateId);
	}
	
	
	public List<Object> getAvailableTemplateBySelf(String accountNo) {
		return botSentenceProcessExtMapper.getAvailableTemplateBySelf(accountNo);
	}
	
}
