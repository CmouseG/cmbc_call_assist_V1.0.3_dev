package com.guiji.botsentence.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.auth.api.IAuth;
import com.guiji.auth.api.IOrg;
import com.guiji.botsentence.dao.BotSentenceIndustryMapper;
import com.guiji.botsentence.dao.BotSentenceTemplateMapper;
import com.guiji.botsentence.dao.entity.BotSentenceIndustry;
import com.guiji.botsentence.dao.entity.BotSentenceIndustryExample;
import com.guiji.botsentence.dao.entity.BotSentenceTemplate;
import com.guiji.botsentence.dao.entity.BotSentenceTemplateExample;
import com.guiji.botsentence.service.IBotSentenceTemplateService;
import com.guiji.botsentence.vo.BotSentenceIndustryChildren;
import com.guiji.botsentence.vo.BotSentenceIndustryVO;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.user.dao.entity.SysUser;

@Service
public class BotSentenceTemplateServiceImpl implements IBotSentenceTemplateService {

	@Autowired
	private BotSentenceTemplateMapper botSentenceTemplateMapper;
	
	@Autowired
	private IAuth iAuth;
	
	@Autowired
	private IOrg orgService;
	
	@Override
	public List<BotSentenceIndustryVO> queryIndustryTemplate(String userId) {
		List<BotSentenceIndustryVO> results = new ArrayList<>();
		
		//查询当前用户配置的行业列表
		/*UserAccountIndustryRelationExample example = new UserAccountIndustryRelationExample();
		example.createCriteria().andAccountNoEqualTo(userId);
		List<UserAccountIndustryRelation> relationList = relationMapper.selectByExample(example);
		if(null != relationList && relationList.size() > 0) {
			for(UserAccountIndustryRelation rela : relationList) {
				
				BotSentenceIndustryVO vo = new BotSentenceIndustryVO();
				vo.setLabel(rela.getIndustryName());
				vo.setValue(rela.getIndustryId());
				List<BotSentenceIndustryChildren> childList = new ArrayList<>();
				//根据行业查询模板列表
				BotSentenceTemplateExample example2 = new BotSentenceTemplateExample();
				example2.createCriteria().andIndustryIdEqualTo(rela.getIndustryId()+"");
				List<BotSentenceTemplate> list = botSentenceTemplateMapper.selectByExample(example2);
				if(null != list && list.size() > 0) {
					for(BotSentenceTemplate template : list) {
						BotSentenceIndustryChildren temp = new BotSentenceIndustryChildren();
						temp.setLabel(template.getTemplateName());
						temp.setValue(template.getProcessId());
						childList.add(temp);
					}
				}
				
				
				vo.setChildren(childList);
				results.add(vo);
			
			}
		}
		*/
		return results;
	}

	@Override
	public BotSentenceTemplate getBotSentenceTemplate(String templateId, String version) {
		if(StringUtils.isNotBlank(templateId) && StringUtils.isNotBlank(version)) {
			BotSentenceTemplateExample example = new BotSentenceTemplateExample();
			example.createCriteria().andTemplateIdEqualTo(templateId).andVersionEqualTo(version);
			List<BotSentenceTemplate> list = botSentenceTemplateMapper.selectByExample(example);
			if(null != list && list.size() > 0) {
				return list.get(0);
			}
		}
		return null;
	}

	@Override
	public BotSentenceTemplate getBotSentenceTemplate(String processId) {
		return botSentenceTemplateMapper.selectByPrimaryKey(processId);
	}

	@Override
	public boolean validateHasTempalte(String userId) {
		
		/*UserAccountTradeRelationExample example = new UserAccountTradeRelationExample();
		example.createCriteria().andAccountNoEqualTo(userId);
		List<UserAccountTradeRelation> relationList = userAccountTradeRelationMapper.selectByExample(example);
		if(null == relationList || relationList.size() < 1) {
			return false;
		}
		UserAccountTradeRelation relation = relationList.get(0);
		if(StringUtils.isNotBlank(relation.getIndustryId())) {
			return true;
		}*/
		ReturnData<SysUser> data=iAuth.getUserById(new Long(userId));
		String orgCode=data.getBody().getOrgCode();
		ReturnData<List<String>> returnData= orgService.getIndustryByOrgCode(orgCode);
		List<String> industryIdList = returnData.getBody();
		if(null != industryIdList && industryIdList.size() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public List<BotSentenceTemplate> queryMyTemplate(int pageSize, int pageNo) {

		List<BotSentenceTemplate> results = new ArrayList<>();
		//获取当前登录账号信息
		//String userId = UserUtil.getUserId();
		
		//计算分页
		int limitStart = (pageNo-1)*pageSize;
		int limitEnd = pageSize;
		
		
		//查询话术模板表个人导入的话术模板
		BotSentenceTemplateExample example2 = new BotSentenceTemplateExample();
		//example2.createCriteria().andAccountNoEqualTo(userId);
		
		example2.setLimitStart(limitStart);
		example2.setLimitEnd(limitEnd);
		example2.setOrderByClause(" lst_update_time desc");
		
		List<BotSentenceTemplate> list = botSentenceTemplateMapper.selectByExample(example2);
		if(null != list && list.size() > 0) {
			results.addAll(list);
		}
		
		return results;
	}

	@Override
	public int countMyTemplate() {
		//String userId = UserUtil.getUserId();
		BotSentenceTemplateExample example2 = new BotSentenceTemplateExample();
		//example2.createCriteria().andAccountNoEqualTo(userId);
		return botSentenceTemplateMapper.countByExample(example2);
	}

	@Override
	public List<BotSentenceTemplate> queryIndustryTemplateOld(String userId) {
		List<BotSentenceTemplate> results = new ArrayList<>();
		
		//查询当前用户配置的行业列表
		/*UserAccountIndustryRelationExample example = new UserAccountIndustryRelationExample();
		example.createCriteria().andAccountNoEqualTo(userId);
		List<UserAccountIndustryRelation> relationList = relationMapper.selectByExample(example);
		if(null != relationList && relationList.size() > 0) {
			for(UserAccountIndustryRelation rela : relationList) {
				//根据行业查询模板列表
				BotSentenceTemplate template = botSentenceTemplateMapper.selectByPrimaryKey(rela.getIndustryId());
				if(null != template) {
					results.add(template);
				}
			}
		}*/
		
		return results;
	
	}

	@Override
	public List<BotSentenceTemplate> queryTemplateByIndustry(String industryId) {
		BotSentenceTemplateExample example = new BotSentenceTemplateExample();
		example.createCriteria().andIndustryIdEqualTo(industryId);
		example.setOrderByClause(" crt_time");
		return botSentenceTemplateMapper.selectByExample(example);
	}
	
	@Override
	public BotSentenceTemplate getBotSentenceTemplateByTemplateId(String templateId) {
		if(StringUtils.isNotBlank(templateId)) {
			BotSentenceTemplateExample example = new BotSentenceTemplateExample();
			example.createCriteria().andTemplateIdEqualTo(templateId);
			List<BotSentenceTemplate> list = botSentenceTemplateMapper.selectByExample(example);
			if(null != list && list.size() > 0) {
				return list.get(0);
			}
		}
		return null;
	}

}
