package ai.guiji.botsentence.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guiji.auth.api.IAuth;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.dispatch.api.IDispatchPlanOut;
import com.guiji.user.dao.entity.SysUser;

import ai.guiji.botsentence.constant.Constant;
import ai.guiji.botsentence.dao.BotPublishSentenceLogMapper;
import ai.guiji.botsentence.dao.BotSentenceBranchMapper;
import ai.guiji.botsentence.dao.BotSentenceDomainMapper;
import ai.guiji.botsentence.dao.BotSentenceProcessMapper;
import ai.guiji.botsentence.dao.UserAccountMapper;
import ai.guiji.botsentence.dao.entity.BotPublishSentenceLog;
import ai.guiji.botsentence.dao.entity.BotSentenceBranch;
import ai.guiji.botsentence.dao.entity.BotSentenceBranchExample;
import ai.guiji.botsentence.dao.entity.BotSentenceDomain;
import ai.guiji.botsentence.dao.entity.BotSentenceDomainExample;
import ai.guiji.botsentence.dao.entity.BotSentenceProcess;
import ai.guiji.botsentence.dao.entity.BotSentenceProcessExample;
import ai.guiji.botsentence.dao.entity.BotSentenceProcessExample.Criteria;
import ai.guiji.botsentence.dao.entity.VoliceInfo;
import ai.guiji.botsentence.dao.ext.BotSentenceDomainExtMapper;
import ai.guiji.botsentence.service.IBotSentenceApprovalService;
import ai.guiji.botsentence.vo.DomainVO;
import ai.guiji.component.client.util.DateUtil;
import ai.guiji.component.exception.CommonException;

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
	private ai.guiji.botsentence.dao.VoliceInfoMapper voliceInfoMapper;
	
	@Autowired
	private BotSentenceBranchMapper botSentenceBranchMapper;

	@Autowired
	private BotSentenceDomainExtMapper botSentenceDomainExtMapper;
	
	@Autowired
	private UserAccountMapper userAccountMapper;
	
	@Autowired
	private IDispatchPlanOut iDispatchPlanOut;
	
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
	public void passApproval(String processId, List<DomainVO> selectedList,Long userId) {
		//修改状态为审批通过
		BotSentenceProcess botSentenceProcess = botSentenceProcessMapper.selectByPrimaryKey(processId);
		botSentenceProcess.setProcessId(processId);
		botSentenceProcess.setState(Constant.APPROVE_PASS);
		botSentenceProcess.setLstUpdateTime(new Date());
		botSentenceProcess.setLstUpdateUser(userId.toString());
		
		botSentenceProcessMapper.updateByPrimaryKey(botSentenceProcess);
		//把之前的com_domain全部设置为空
		botSentenceDomainExtMapper.batchUpdateComDomain(processId);
		
		//设置默认主流程
		for(int i = 0 ; i < selectedList.size() - 1 ; i++) {
			DomainVO vo = selectedList.get(i);
			BotSentenceDomain domain = botSentenceDomainMapper.selectByPrimaryKey(vo.getDomainId());
			domain.setComDomain(selectedList.get(i+1).getName());
			domain.setIsMainFlow("01");
			domain.setLstUpdateTime(new Date());
			domain.setLstUpdateUser(userId.toString());
			botSentenceDomainMapper.updateByPrimaryKey(domain);
		}
		
		
	}
	
	@Autowired
	private BotPublishSentenceLogMapper botPublishSentenceLogMapper;
	
	@Autowired
	private IAuth iAuth;
	
	public void publishSentence(String processId,Long userId){
		BotSentenceProcess botSentenceProcess = botSentenceProcessMapper.selectByPrimaryKey(processId);
		String templateId = botSentenceProcess.getTemplateId();
		iDispatchPlanOut.receiveRobotId(templateId);
		
		ReturnData<SysUser> data=iAuth.getUserById(userId);
		data.getBody().getUsername();
		BotPublishSentenceLog record=new BotPublishSentenceLog();
		record.setCreateId(userId);
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
			file = fileGenerateService.fileGenerate(processId, dirName);
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

	@Override
	public void notPassApproval(String processId,Long userId) {
		BotSentenceProcess botSentenceProcess = new BotSentenceProcess();
		botSentenceProcess.setProcessId(processId);
		botSentenceProcess.setState(Constant.APPROVE_NOTPASS);
		botSentenceProcess.setLstUpdateTime(new Date());
		botSentenceProcess.setLstUpdateUser(userId.toString());
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
				BotSentenceBranchExample endBranchExample = new BotSentenceBranchExample();
				endBranchExample.createCriteria().andProcessIdEqualTo(processId).andIsShowEqualTo("1").andDomainEqualTo(domain.getDomainName()).andNextIsNotNull();
				int num = botSentenceBranchMapper.countByExample(endBranchExample);
				if(num == 0) {
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
	
//	private List<String> getNext(String processId, String domainName, List<List<String>> results, List<String> list){
//		list.add(domainName);
//		
//		
//		//获取next列表
//		BotSentenceBranchExample tempExample = new BotSentenceBranchExample();
//		tempExample.createCriteria().andProcessIdEqualTo(processId).andIsShowEqualTo("1").andDomainEqualTo(domainName);
//		List<BotSentenceBranch> tempList = botSentenceBranchMapper.selectByExample(tempExample);
//		List<String> nextList = new ArrayList<>();
//		for(BotSentenceBranch temp : tempList) {
//			if(StringUtils.isNotBlank(temp.getNext())) {
//				nextList.add(temp.getNext());
//			}
//		}
//		return nextList;
//	}
	
	
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

	
	
	
}
