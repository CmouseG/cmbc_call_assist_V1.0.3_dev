package ai.guiji.botsentence.service.impl;

import java.util.ArrayList;
import java.util.List;

import ai.guiji.botsentence.dao.BotSentenceTemplateMapper;
import ai.guiji.botsentence.dao.UserAccountIndustryRelationMapper;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.guiji.botsentence.dao.entity.BotSentenceTemplate;
import ai.guiji.botsentence.dao.entity.BotSentenceTemplateExample;
import ai.guiji.botsentence.dao.entity.UserAccountIndustryRelation;
import ai.guiji.botsentence.dao.entity.UserAccountIndustryRelationExample;
import ai.guiji.botsentence.service.IBotSentenceTemplateService;
import ai.guiji.botsentence.vo.BotSentenceIndustryChildren;
import ai.guiji.botsentence.vo.BotSentenceIndustryVO;

@Service
public class BotSentenceTemplateServiceImpl implements IBotSentenceTemplateService {

	@Autowired
	private BotSentenceTemplateMapper botSentenceTemplateMapper;
	
	@Autowired
	private UserAccountIndustryRelationMapper relationMapper;
	
	@Override
	public List<BotSentenceIndustryVO> queryIndustryTemplate(Long userId) {
		//获取当前登录账号信息
		
		List<BotSentenceIndustryVO> results = new ArrayList<>();
		
		//查询当前用户配置的行业列表
		UserAccountIndustryRelationExample example = new UserAccountIndustryRelationExample();
		example.createCriteria().andAccountNoEqualTo(userId.toString());
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
		
		//查询话术模板表个人导入的话术模板
		/*BotSentenceTemplateExample example2 = new BotSentenceTemplateExample();
		example2.createCriteria().andAccountNoEqualTo(userId);
		List<BotSentenceTemplate> list = botSentenceTemplateMapper.selectByExample(example2);
		if(null != list && list.size() > 0) {
			results.addAll(list);
		}*/
		
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
	public boolean validateHasTempalte(Long userId) {
		UserAccountIndustryRelationExample example = new UserAccountIndustryRelationExample();
		example.createCriteria().andAccountNoEqualTo(userId.toString());
		List<UserAccountIndustryRelation> relationList = relationMapper.selectByExample(example);
		if(null == relationList || relationList.size() < 1) {
			return false;
		}
		return true;
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
	public List<BotSentenceTemplate> queryIndustryTemplateOld(Long userId) {
		List<BotSentenceTemplate> results = new ArrayList<>();
		//获取当前登录账号信息

		//查询当前用户配置的行业列表
		List<String> industryNames = new ArrayList<>();
		UserAccountIndustryRelationExample example = new UserAccountIndustryRelationExample();
		example.createCriteria().andAccountNoEqualTo(userId.toString());
		List<UserAccountIndustryRelation> relationList = relationMapper.selectByExample(example);
		if(null != relationList && relationList.size() > 0) {
			for(UserAccountIndustryRelation rela : relationList) {
				//根据行业查询模板列表
				BotSentenceTemplate template = botSentenceTemplateMapper.selectByPrimaryKey(rela.getIndustryId());
				if(null != template) {
					results.add(template);
				}
			}
		}
		
		return results;
	
	}

}
