package com.guiji.botsentence.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.security.MD5Encoder;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.guiji.auth.api.IAuth;
import com.guiji.botsentence.constant.Constant;
import com.guiji.botsentence.dao.BotPublishSentenceLogMapper;
import com.guiji.botsentence.dao.BotSentenceBranchMapper;
import com.guiji.botsentence.dao.BotSentenceDomainMapper;
import com.guiji.botsentence.dao.BotSentenceProcessMapper;
import com.guiji.botsentence.dao.BotSentenceTtsTaskMapper;
import com.guiji.botsentence.dao.entity.BotPublishSentenceLog;
import com.guiji.botsentence.dao.entity.BotSentenceBranch;
import com.guiji.botsentence.dao.entity.BotSentenceBranchExample;
import com.guiji.botsentence.dao.entity.BotSentenceDomain;
import com.guiji.botsentence.dao.entity.BotSentenceDomainExample;
import com.guiji.botsentence.dao.entity.BotSentenceProcess;
import com.guiji.botsentence.dao.entity.BotSentenceProcessExample;
import com.guiji.botsentence.dao.entity.BotSentenceTtsTaskExample;
import com.guiji.botsentence.dao.entity.VoliceInfo;
import com.guiji.botsentence.dao.entity.BotSentenceProcessExample.Criteria;
import com.guiji.botsentence.dao.ext.BotSentenceDomainExtMapper;
import com.guiji.botsentence.dao.ext.VoliceInfoExtMapper;
import com.guiji.botsentence.service.IBotSentenceApprovalService;
import com.guiji.botsentence.util.BotSentenceUtil;
import com.guiji.botsentence.util.HttpRequestUtils;
import com.guiji.botsentence.util.TarUtil;
import com.guiji.botsentence.vo.BotSentenceSellbotMachine;
import com.guiji.botsentence.vo.DomainVO;
import com.guiji.botsentence.vo.RequestCrmVO;
import com.guiji.botsentence.vo.ResponseCrmVO;
import com.guiji.component.client.config.SellbotMechine;
import com.guiji.component.client.util.BeanUtil;
import com.guiji.component.client.util.DateUtil;
import com.guiji.component.client.util.FileUtil;
import com.guiji.common.exception.CommonException;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.dispatch.api.IDispatchPlanOut;
import com.guiji.user.dao.entity.SysUser;
import com.jcraft.jsch.Logger;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.Session;

@Service
public class BotSentenceApprovalServiceImpl implements IBotSentenceApprovalService{
	
	private org.slf4j.Logger logger = LoggerFactory.getLogger(BotSentenceApprovalServiceImpl.class);
	
	@Autowired
	private BotSentenceProcessMapper botSentenceProcessMapper;
	
	@Autowired
	private FileGenerateServiceImpl fileGenerateService;
	
	@Autowired
	private BotSentenceDomainMapper botSentenceDomainMapper;
	
	@Autowired
	private BotSentenceProcessServiceImpl botSentenceProcessService;
	
	@Autowired
	private com.guiji.botsentence.dao.VoliceInfoMapper voliceInfoMapper;
	
	@Autowired
	private BotSentenceBranchMapper botSentenceBranchMapper;

	@Autowired
	private BotSentenceDomainExtMapper botSentenceDomainExtMapper;
	
	@Autowired
	private BotSentenceTtsTaskMapper botSentenceTtsTaskMapper;
	
	@Autowired
	private SellbotMechine sellbotMechine;
	
	@Autowired
	private VoliceInfoExtMapper voliceInfoExtMapper;
	
	@Autowired
	private BotSentenceTtsServiceImpl botSentenceTtsService;
	
	@Autowired
	private BotPublishSentenceLogMapper botPublishSentenceLogMapper;
	
	@Autowired
	private IDispatchPlanOut iDispatchPlanOut;
	
	@Autowired
	private IAuth iAuth;
	
	private static String FILE_SEPARATOR = System.getProperty("file.separator");
	
	@Value("${template.dir}")
	private String tempDir;
	
	@Value("${selftest.sellbot.innerUrl}")
	private String innerUrl;
	
	@Value("${selftest.sellbot.username}")
	private String username;
	
	@Value("${selftest.sellbot.password}")
	private String password;
	
	@Value("${selftest.sellbot.path}")
	private String path;
	
	@Value("${selftest.sellbot.port}")
	private int port;
	
	
	@Override
	public List<BotSentenceProcess> getListApprovaling(int pageSize, int pageNo, String templateName, String accountNo) {
		
		BotSentenceProcessExample example = new BotSentenceProcessExample();
		Criteria criteria = example.createCriteria();
		Criteria criteria2 = example.createCriteria();
		List<String> states = new ArrayList<String>();
		states.add(Constant.APPROVE_ONLINE);
		states.add(Constant.APPROVE_NOTPASS);
		states.add(Constant.APPROVE_CHECKING);
		states.add(Constant.DEPLOYING);
		criteria.andStateIn(states);
		criteria2.andStateIn(states);
		if(StringUtils.isNotBlank(templateName)) {
			criteria.andTemplateNameLike("%" + templateName + "%");
			criteria2.andTemplateIdLike("%" + templateName + "%");
		}
		if(StringUtils.isNotBlank(accountNo)) {
			criteria.andAccountNoEqualTo(accountNo);
			criteria2.andAccountNoEqualTo(accountNo);
		}
		
		/*String userId = UserUtil.getUserId();
		String host = userId.split("-")[0];
		criteria.andCrtUserLike(host + "-%");*/
		
		//计算分页
		int limitStart = (pageNo-1)*pageSize;
		int limitEnd = pageSize;
		example.setLimitStart(limitStart);
		example.setLimitEnd(limitEnd);
		example.setOrderByClause(" lst_update_time desc");
		
		example.or(criteria2);
		
		return botSentenceProcessMapper.selectByExample(example);
	}

	@Override
	public int countApprovaling(String templateName) {
		
		BotSentenceProcessExample example = new BotSentenceProcessExample();
		Criteria criteria = example.createCriteria();
		Criteria criteria2 = example.createCriteria();
		if(StringUtils.isNotBlank(templateName)) {
			criteria.andTemplateNameLike("%" + templateName + "%");
			criteria2.andTemplateIdLike("%" + templateName + "%");
		}
		List<String> states = new ArrayList<String>();
		states.add(Constant.APPROVE_ONLINE);
		states.add(Constant.APPROVE_NOTPASS);
		states.add(Constant.APPROVE_CHECKING);
		states.add(Constant.DEPLOYING);
		criteria.andStateIn(states);
		criteria2.andStateIn(states);
		
		example.or(criteria2);
		
		return botSentenceProcessMapper.countByExample(example);
	}

	
	@Transactional
	@Override
	public void passApproval(String processId, List<DomainVO> selectedList, String userId) {
		//修改状态为审批通过
		BotSentenceProcess botSentenceProcess = botSentenceProcessMapper.selectByPrimaryKey(processId);
		botSentenceProcess.setProcessId(processId);
		botSentenceProcess.setState(Constant.APPROVE_PASS);
		botSentenceProcess.setLstUpdateTime(new Date());
		botSentenceProcess.setLstUpdateUser(userId);
		
		
		//把之前的com_domain全部设置为空
		botSentenceDomainExtMapper.batchUpdateComDomain(processId);
		
		//设置默认主流程
		for(int i = 0 ; i < selectedList.size() - 1 ; i++) {
			DomainVO vo = selectedList.get(i);
			BotSentenceDomain domain = botSentenceDomainMapper.selectByPrimaryKey(vo.getDomainId());
			domain.setComDomain(selectedList.get(i+1).getName());
			domain.setIsMainFlow("01");
			domain.setLstUpdateTime(new Date());
			domain.setLstUpdateUser(userId);
			botSentenceDomainMapper.updateByPrimaryKey(domain);
		}
		
		/*String templateId = botSentenceProcess.getTemplateId();
		String dirName = DateUtil.getCurrentTime2() + "-" + templateId;
	    File file = null;
		try {
			file = fileGenerateService.fileGenerate(processId, dirName, Constant.APPROVE_TYPE, userId);
		} catch (IOException e) {
			logger.error("生成模板文件异常", e);
			throw new CommonException("话术部署失败，生成模板文件失败!");
		}
	    if(null == file) {
	    	throw new CommonException("话术部署失败!");
	    }
	    boolean b = fileGenerateService.autoDeploy(file, dirName, processId, templateId, userId);
	    if(!b) {
	    	throw new CommonException("话术部署失败!");
	    }
	    botSentenceProcess.setState(Constant.DEPLOYING);//部署中
	    botSentenceProcessMapper.updateByPrimaryKeySelective(botSentenceProcess);*/
	}

	@Override
	public void notPassApproval(String processId, String userId) {
		BotSentenceProcess botSentenceProcess = new BotSentenceProcess();
		botSentenceProcess.setProcessId(processId);
		botSentenceProcess.setState(Constant.APPROVE_NOTPASS);
		botSentenceProcess.setLstUpdateTime(new Date());
		botSentenceProcess.setLstUpdateUser(userId);
		botSentenceProcessMapper.updateByPrimaryKeySelective(botSentenceProcess);
		
	}

	/**
	 * 查询当前话术流程所有的分支流程，以结束节点向上查找
	 */
	@Override
	public List queryComProcess(String processId) {
		//先查找所有domain的话术
		BotSentenceDomainExample allExample = new BotSentenceDomainExample();
		allExample.createCriteria().andProcessIdEqualTo(processId).andCategoryEqualTo("1");
		List<BotSentenceDomain> allList = botSentenceDomainMapper.selectByExample(allExample);
		Map<String, String> map = new HashMap<>();
		if(null != allList && allList.size() > 0) {
			for(BotSentenceDomain domain : allList) {
				BotSentenceBranch enterBranch = botSentenceProcessService.getEnterBranch(domain.getProcessId(), domain.getDomainName());
				String resp = enterBranch.getResponse();
				if(StringUtils.isNotBlank(resp) && !"[]".equals(resp.trim()) && resp.trim().startsWith("[") && resp.trim().endsWith("]")) {
					String[] respArray = resp.substring(1,resp.length()-1).split(",");
					if(respArray.length > 0) {
						long voliceId = new Long(respArray[0]);
						VoliceInfo volice = voliceInfoMapper.selectByPrimaryKey(voliceId);
						map.put(domain.getDomainId(), volice.getContent());
					}
				}
			}
		}
		//查找所有结束节点
		BotSentenceDomainExample domainExample = new BotSentenceDomainExample();
		domainExample.createCriteria().andProcessIdEqualTo(processId).andTypeEqualTo("end");
		List<BotSentenceDomain> domainList = botSentenceDomainMapper.selectByExample(domainExample);
		
		List<List<DomainVO>> results = new ArrayList<>();
		int num = 0 ; 
		if(null != domainList && domainList.size() > 0) {
			for(BotSentenceDomain domain : domainList) {
				
				List<DomainVO> list = new ArrayList<>();
				DomainVO vo = new DomainVO();
				vo.setDomainId(domain.getDomainId());
				vo.setName(domain.getDomainName());
				vo.setContent(map.get(domain.getDomainId()));
				list.add(vo);
				
				String domainName = domain.getDomainName();
				while(StringUtils.isNotBlank(domainName) && !"root".equals(domainName)) {
					if(num == 20) {
						break;
					}
					BotSentenceDomain parentDomain = getParentDomain(processId, domainName);
					if(null == parentDomain) {
						break;
					}
					
					DomainVO tempVO = new DomainVO();
					tempVO.setDomainId(parentDomain.getDomainId());
					tempVO.setName(parentDomain.getDomainName());
					tempVO.setContent(map.get(parentDomain.getDomainId()));
					list.add(tempVO);
					
					domainName = parentDomain.getDomainName();
					num++;
				}
				Collections.reverse(list);
				
				results.add(list);
			}
		}
		
		return results;
	}
	
	
	@Override
	public List queryComProcess2(String processId) {
		//先查找所有domain的话术
		BotSentenceDomainExample allExample = new BotSentenceDomainExample();
		allExample.createCriteria().andProcessIdEqualTo(processId).andCategoryEqualTo("1");
		allExample.setOrderByClause(" domain_id");
		List<BotSentenceDomain> allList = botSentenceDomainMapper.selectByExample(allExample);
		Map<String, String> map = new HashMap<>();
		int index = 0;
		List<String> sys = new ArrayList<>();
		List<DomainIndex> indexList = new ArrayList<>();
		List<String> domainNameList = new ArrayList<>();
		if(null != allList && allList.size() > 0) {
			for(BotSentenceDomain domain : allList) {
				domainNameList.add(domain.getDomainName());
				BotSentenceBranch enterBranch = botSentenceProcessService.getEnterBranch(domain.getProcessId(), domain.getDomainName());
				String resp = enterBranch.getResponse();
				if(StringUtils.isNotBlank(resp) && !"[]".equals(resp.trim()) && resp.trim().startsWith("[") && resp.trim().endsWith("]")) {
					String[] respArray = resp.substring(1,resp.length()-1).split(",");
					if(respArray.length > 0) {
						long voliceId = new Long(respArray[0]);
						VoliceInfo volice = voliceInfoMapper.selectByPrimaryKey(voliceId);
						map.put(domain.getDomainId(), volice.getContent());
					}
				}
				
				DomainIndex domainIndex = new DomainIndex();
				domainIndex.setDomainId(domain.getDomainId());
				domainIndex.setDomainName(domain.getDomainName());
				domainIndex.setIndex(index);
				indexList.add(domainIndex);
				sys.add(domainIndex.toString());
				System.out.println(domainIndex.toString());
				index++;
			}
		}
		
		int datas[] = new int[index];
		for(int i = 0 ; i < index ; i++) {
			datas[i] = i;
		}
		
		
		graph g = new graph(index);
		
		
		g.initVertext(datas);
		
		//获取所有边的数据
		BotSentenceBranchExample tempExample = new BotSentenceBranchExample();
		tempExample.createCriteria().andProcessIdEqualTo(processId).andIsShowEqualTo("1").andDomainIn(domainNameList);
		List<BotSentenceBranch> tempList = botSentenceBranchMapper.selectByExample(tempExample);
		
		
		for(int i = 0 ; i < tempList.size() ; i++) {
			int x = 0 ; 
			int y = 0 ;
			
			String from = tempList.get(i).getDomain();
			String to = tempList.get(i).getNext();
			
			for(DomainIndex temp : indexList) {
				if(from.equals(temp.getDomainName())) {
					x = temp.getIndex();
				}
				if(to.equals(temp.getDomainName())) {
					y = temp.getIndex();
				}
			}
			
			
			g.addEdge(x,y, 1);
		}
		
		
		List<Integer> endDomainList = new ArrayList<>();
		
		List<String> endDomainNameList = new ArrayList<>();
		
		
		if(null != allList && allList.size() > 0) {
			for(BotSentenceDomain domain : allList) {
				//BotSentenceBranchExample endBranchExample = new BotSentenceBranchExample();
				//endBranchExample.createCriteria().andProcessIdEqualTo(processId).andIsShowEqualTo("1").andDomainEqualTo(domain.getDomainName()).andNextIsNotNull();
				//int num = botSentenceBranchMapper.countByExample(endBranchExample);
				//if(num == 0) {
					//endDomainNameList.add(domain.getDomainName());
				//}
				if(Constant.DOMAIN_TYPE_END.equals(domain.getType())) {
					endDomainNameList.add(domain.getDomainName());
				}
			}
		}
		
		
		//根据结束节点获取对应的index
		for(String domainName : endDomainNameList) {
			for(DomainIndex temp : indexList) {
				if(domainName.equals(temp.getDomainName())) {
					endDomainList.add(temp.getIndex());
				}
			}
		}
		
		for(int i = 0 ; i < sys.size() ; i++) {
			System.out.println(sys.get(i));
		}
		
		
		System.out.println("所有的结束节点为: " + endDomainList.toString());
		
		
		
		//获取开场白的index
		int startIndex = 0;
		for(DomainIndex temp : indexList) {
			if("开场白".equals(temp.getDomainName())) {
				startIndex = temp.getIndex();
				break;
			}
		}
		System.out.println("开场白的序号: " + startIndex);
		
		List<String> processStrList = new ArrayList<>();
		
		for(int i = 0 ; i < endDomainList.size() ; i++) {
			g.visit(startIndex, endDomainList.get(i), processStrList);
		}
		
		List<List<DomainVO>> results = new ArrayList<>();
		
		if(null != processStrList && processStrList.size() > 0) {
			for(String processStr: processStrList) {
				String[] nameList = processStr.split(",");
				if(null != nameList && nameList.length > 0) {
					List<DomainVO> list = new ArrayList<>();
					
					
					for(String name : nameList) {
						
						for(DomainIndex temp : indexList) {
							if(new Integer(name) == temp.getIndex()) {
								DomainVO vo = new DomainVO();
								vo.setDomainId(temp.getDomainId());
								vo.setName(temp.getDomainName());
								vo.setContent(map.get(temp.getDomainId()));
								list.add(vo);
								
								break;
							}
						}
					}
					
					for(int i = 0 ; i < list.size()-1 ; i++) {
						String start = list.get(i).getName();
						String end = list.get(i+1).getName();
						BotSentenceBranchExample example = new BotSentenceBranchExample();
						example.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo(start).andNextEqualTo(end).andIsShowEqualTo("1");
						List<BotSentenceBranch> lineList = botSentenceBranchMapper.selectByExample(example);
						if(null != lineList && lineList.size() > 0) {
							list.get(i).setLineName(lineList.get(0).getLineName());
						}
					}
					
					results.add(list);
				}
			}
		}
		
		return results;
	}
	
	
	
	private List<String> getNext(String processId, String domainName, List<List<String>> results, List<String> list){
		list.add(domainName);
		
		
		//获取next列表
		BotSentenceBranchExample tempExample = new BotSentenceBranchExample();
		tempExample.createCriteria().andProcessIdEqualTo(processId).andIsShowEqualTo("1").andDomainEqualTo(domainName);
		List<BotSentenceBranch> tempList = botSentenceBranchMapper.selectByExample(tempExample);
		List<String> nextList = new ArrayList<>();
		for(BotSentenceBranch temp : tempList) {
			if(StringUtils.isNotBlank(temp.getNext())) {
				nextList.add(temp.getNext());
			}
		}
		/*
		if(nextList.size() > 0) {
			for(String tempName : nextList) {
				getNext(processId, tempName,results, list);
			}
		}else {
			results.add(list);
			System.out.println(results.toString());
			list = new ArrayList<>();
		}*/
		
		
		return nextList;
	}
	
	
	private BotSentenceDomain getParentDomain(String processId, String domainName) {
		BotSentenceDomainExample domainExample = new BotSentenceDomainExample();
		domainExample.createCriteria().andProcessIdEqualTo(processId).andDomainNameEqualTo(domainName);
		List<BotSentenceDomain> domainList = botSentenceDomainMapper.selectByExample(domainExample);
		if(null != domainList && domainList.size() > 0) {
			String parent = domainList.get(0).getParent();
			BotSentenceDomainExample domainExample2 = new BotSentenceDomainExample();
			domainExample2.createCriteria().andProcessIdEqualTo(processId).andDomainNameEqualTo(parent);
			List<BotSentenceDomain> domainList2 = botSentenceDomainMapper.selectByExample(domainExample2);
			if(null != domainList2 && domainList2.size() > 0) {
				return domainList2.get(0); 
			}
		}
		return null;
	}


	@Override
	@Transactional
	public void deployTestSellbotByScp(String processId, String userId) {
		//默认选择一条主流程
		List<List<DomainVO>> list = this.queryComProcess2(processId);
		if(null == list || list.size() < 1) {
			throw new CommonException("话术流程不完整，请确保开场白到结束结点之间连续连线!");
		}
		
		
		List<DomainVO> selectedList = new ArrayList<>();
		
		int finalNum = 0;
		
		for(int i = 0 ; i < list.size() ; i++) {
			int num = 0;
			List<DomainVO> tempList = list.get(i);
			
			for(DomainVO domain : tempList) {
				if("未拒绝".equals(domain.getLineName())) {
					num++;
				}
			}
			
			if(num > finalNum) {
				finalNum = num;
				selectedList = tempList;
			}
		}
		
		
		for(int i = 0 ; i < selectedList.size() - 1 ; i++) {
			DomainVO vo = selectedList.get(i);
			BotSentenceDomain domain = botSentenceDomainMapper.selectByPrimaryKey(vo.getDomainId());
			domain.setComDomain(selectedList.get(i+1).getName());
			domain.setIsMainFlow("01");
			domain.setLstUpdateTime(new Date());
			domain.setLstUpdateUser(userId);
			botSentenceDomainMapper.updateByPrimaryKey(domain);
		}
		
		
		//修改状态为审批通过
		BotSentenceProcess botSentenceProcess = botSentenceProcessMapper.selectByPrimaryKey(processId);
		String templateId = botSentenceProcess.getTemplateId();
		String dirName = DateUtil.getCurrentTime2() + "-" + templateId;
		File file = null;
		try {
			file = fileGenerateService.fileGenerate(processId, dirName, Constant.APPROVE_TYPE, userId);
		}catch(Exception e) {
			logger.error("生成模板异常...", e);
		}
	    
		if(null == file) {
	    	throw new CommonException("生成模板文件失败...");
	    }
	    
	   logger.info("文件加密处理");
	   //BotSentenceUtil.fileCrypter(file);
	   logger.info("文件加密结束...");
	    
	    String cfgsFilePath = null;
	    String wavFilePath = null;
	    
	    File[] files = file.listFiles();
	    for (int i = 0; i < files.length; i++) {
	    	File temp = files[i];
			if (temp.isDirectory() && temp.getName().indexOf("_en") > 0) {//cfgs
				cfgsFilePath = temp.getPath();
			}
			if (temp.isDirectory() && temp.getName().indexOf("_rec") > 0) {//wav
				wavFilePath = temp.getPath();
			}
		}
	    logger.info("cfgs文件目录: " + cfgsFilePath);
	    logger.info("wav文件目录: " + wavFilePath);
	    
	    File ver= null;
	    try {
	    	//创建ver.txt
	    	ver=File.createTempFile(String.valueOf(System.currentTimeMillis()), "txt");
		    //File ver = new File(/"+ "ver.txt");
		    if(!ver.exists()) {
		    	ver.createNewFile();
		    }
		    FileWriter fw = new FileWriter(ver);
			fw.write(DateUtil.getCurrentTime2() + "\n");
			fw.close();
		} catch (IOException e1) {
			logger.error("创建ver.txt异常", e1);
			throw new CommonException("部署话术失败，创建模板号失败...");
		}
	    
	    //通过scp复制文件到Sellbot机器
	    //获取sellbot测试机器
	    List<BotSentenceSellbotMachine> machines = new ArrayList<>();
	    
		BotSentenceSellbotMachine botSentenceSellbotMachine = new BotSentenceSellbotMachine();
		botSentenceSellbotMachine.setIp(innerUrl);
		botSentenceSellbotMachine.setUsername(username);
		botSentenceSellbotMachine.setPassword(password);
		botSentenceSellbotMachine.setPath(path);
		machines.add(botSentenceSellbotMachine);
	    
	    String message = "";
	    
	    String cfgsname = templateId;
	    String wavname = templateId.replace("_en", "_rec");
	    
	    String cfgs_tar_name = cfgsname + ".tar";
	    String wav_tar_name = wavname + ".tar";
	    
	    TarUtil tarBuilder1 = new TarUtil(cfgsFilePath, tempDir, cfgs_tar_name, false);
		tarBuilder1.build();
		
		TarUtil tarBuilder2 = new TarUtil(wavFilePath, tempDir, wav_tar_name, false);
		tarBuilder2.build();
		int num = 1;
	    if(null != machines && machines.size() > 0) {
	    	for(BotSentenceSellbotMachine machine : machines) {
	    		logger.info("开始部署第" + num + " 台机器");
	    		logger.info("部署IP: " + machine.getIp());
	    		String cfgsPath =  machine.getPath() + "/" + "cfgs" + "/" + cfgsname;
	    		String wavPath =  machine.getPath() + "/" + "wav" + "/" + wavname;
	    		
	    		String cfgs_tar_path = machine.getPath() + "/" + "cfgs" + "/" + cfgs_tar_name;
	    		String wav_tar_path = machine.getPath() + "/" + "wav" + "/" + wav_tar_name;
	    		
	    		
	    	    Connection conn = new Connection(machine.getIp(), port);
	    	    try {
	    			conn.connect();
	    			boolean isAuth = conn.authenticateWithPassword(machine.getUsername(), machine.getPassword());
	    		    if(!isAuth) {
	    		    	throw new CommonException("Authentication failed");
	    		    }
	    		    SCPClient scpClient = new SCPClient(conn);
	    		    Session session = conn.openSession();
	    		    //备份cfgs下该模板目录 
	    		    logger.info("备份该模板的cfgs文件");
	    		    String bakpath_cfgs = cfgsPath + "_" + DateUtil.getCurrentTime2();
	    		    session.execCommand("mv " + cfgsPath + " " + bakpath_cfgs);
	    		    session.close();
	    		    
	    		    //备份wav下该模板目录
	    		    session = conn.openSession();
	    		    logger.info("备份该模板的wav文件");
	    		    String bakpath_wav = wavPath + "_" + DateUtil.getCurrentTime2();
	    		    session.execCommand("mv " + wavPath + " " + bakpath_wav);
	    		    session.close();
	    		    
	    		    
	    		    session = conn.openSession();
	    		    session.execCommand("mkdir " + cfgsPath);
	    		    session.close();
	    		    logger.info("创建远程cfgs目录: " + cfgsPath);
	    		    
	    		    
	    		    logger.info("上传cfgs模板文件");
	    		    scpClient.put(tempDir + "/" + cfgs_tar_name , machine.getPath() + "/" + "cfgs");
	    		    logger.info("上传cfgs模板文件success...");
	    		    
	    		    
	    		    logger.info("解压缩并删除相应压缩包");
	    		    session = conn.openSession();
	    		    session.execCommand("tar -xf " + machine.getPath() + "/" + "cfgs" + "/" + cfgs_tar_name + " -C " + cfgsPath + " && rm -rf " + cfgs_tar_path);
	    		    session.close();
	    		    logger.info("解压缩并删除相应压缩包success...");
	    		    
	    		    
	    		    //创建Wav目录
	    		    session = conn.openSession();
	    		    session.execCommand("mkdir " + wavPath);
	    		    session.close();
	    		    logger.info("创建远程wav目录: " + wavPath);
	    		    
	    		    //上传wav目录
	    		    logger.info("上传wav录音文件");
	    		    scpClient.put(tempDir + "/" + wav_tar_name , machine.getPath() + "/" + "wav");
	    		    logger.info("上传wav录音文件success...");
	    		    
	    		    logger.info("解压缩并删除相应压缩包");
	    		    session = conn.openSession();
	    		    session.execCommand("tar -xf " + machine.getPath() + "/" + "wav" + "/" + wav_tar_name + " -C " + wavPath + " && rm -rf " + wav_tar_path);
	    		    session.close();
	    		    logger.info("解压缩并删除相应压缩包success...");
	    		    
	    		    logger.info("上传ver.txt文件");
	    		    logger.info(ver.getPath());
	    		    logger.info(machine.getPath() + "/" + "cfgs" + "/" + cfgsname);
	    		    scpClient.put(ver.getPath() , machine.getPath() + "/" + "cfgs" + "/" + cfgsname);
	    		    logger.info("上传ver.txt文件成功");
	    		    
	    		    logger.info("更改文件名称: " + "mv " + cfgsPath + "/" + ver.getName() +  " "+ cfgsPath + "/" +"ver.txt");
	    		    session = conn.openSession();
	    		    session.execCommand("mv " + cfgsPath + "/" + ver.getName() +  " "+ cfgsPath + "/" +"ver.txt");
	    		    session.close();
	    		    
	    		} catch (Exception e) {
	    			message = message + machine.getIp() + ",";
	    			logger.error("scp部署到sellbot失败", e);
	    		}
	    	    
	    	    num++;
	    	}
	    }
	    
	    File cfg_tar_name_file = new File(tempDir, cfgs_tar_name);
	    if(cfg_tar_name_file.exists()) {
	    	logger.info("删除临时cfg tar文件");
	    	cfg_tar_name_file.delete();
	    }
	    
	    File wav_tar_name_file = new File(tempDir, wav_tar_name);
	    if(wav_tar_name_file.exists()) {
	    	logger.info("删除临时wav tar文件");
	    	wav_tar_name_file.delete();
	    }
	    ver.delete();
	    if(StringUtils.isNotBlank(message)) {
	    	throw new CommonException("机器" + message + "部署失败!");
	    }
	    
	    botSentenceProcess.setTestState(Constant.TEST_STATE_ONLINE);//已上线
	    botSentenceProcessMapper.updateByPrimaryKeySelective(botSentenceProcess);
	}

	@Override
	public void deployTestSellbotByAgent(String processId) {
		
	}

	
	public static void main(String[] args) throws IOException {
		File ver=File.createTempFile(String.valueOf(System.currentTimeMillis()), ".txt");
		String suffix =  ver.getName().substring(0, ver.getName().indexOf("."));
		System.out.println(suffix);
	}
	
	
	@Override
	@Async
	@Transactional
	public void publishSentence(String processId,String userId){
		BotSentenceProcess botSentenceProcess = botSentenceProcessMapper.selectByPrimaryKey(processId);
		String templateId = botSentenceProcess.getTemplateId();
		iDispatchPlanOut.receiveRobotId(templateId);
		
		ReturnData<SysUser> data=iAuth.getUserById(new Long(userId));
		data.getBody().getUsername();
		BotPublishSentenceLog record=new BotPublishSentenceLog();
		record.setCreateId(new Long(userId));
		record.setCreateTime(new Date());
		record.setProcessId(processId);
		record.setTemplateId(templateId);
		record.setTempName(botSentenceProcess.getTemplateName());
		record.setCreateName(data.getBody().getUsername());
		record.setStatus("1");
		botPublishSentenceLogMapper.insert(record);
		
		String dirName = DateUtil.getCurrentTime2() + "-" + templateId;
	    File file = null;
		try {
			file = fileGenerateService.fileGenerate(processId, dirName, Constant.APPROVE_TYPE, userId);
		} catch (IOException e) {
			logger.error("生成模板文件异常", e);
			throw new CommonException("话术部署失败，生成模板文件失败!");
		}
	    if(null == file) {
	    	throw new CommonException("话术部署失败!");
	    }
	    botSentenceProcess.setState(Constant.DEPLOYING);//部署中
	    botSentenceProcessMapper.updateByPrimaryKeySelective(botSentenceProcess);
	    boolean b = fileGenerateService.autoDeploy(file, dirName, processId, templateId,userId);
	    if(!b) {
	    	throw new CommonException("话术部署失败!");
	    }
	    
	}
}
