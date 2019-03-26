package com.guiji.botsentence.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.guiji.user.dao.entity.SysOrganization;
import com.guiji.user.dao.entity.SysUser;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.guiji.auth.api.IAuth;
import com.guiji.botsentence.dao.BotAvailableTemplateMapper;
import com.guiji.botsentence.dao.entity.BotAvailableTemplate;
import com.guiji.botsentence.dao.entity.BotAvailableTemplateExample;
import com.guiji.botsentence.dao.entity.BotSentenceProcess;
import com.guiji.botsentence.service.AuthService;
import com.guiji.botsentence.service.IBotSentenceProcessService;
import com.guiji.component.result.Result.ReturnData;

@Service
public class AvailableTemplateService {
	
	@Autowired
	private IAuth iAuth;
	
	@Autowired
	private BotAvailableTemplateMapper botAvailableTemplateMapper;
	@Autowired
	AuthService authService;
	@Autowired
	IBotSentenceProcessService botSentenceProcessService;
	
	/**
	 * 企业可用话术
	 */
	public List<BotAvailableTemplate>  getOrgAvailableTemplate(Long userId){
		ReturnData<SysOrganization> data=iAuth.getOrgByUserId(userId);
		//ReturnData<SysUser> data=iAuth.getUserById(userId);
		String orgCode=data.getBody().getCode();
		BotAvailableTemplateExample example=new BotAvailableTemplateExample();
		example.createCriteria().andOrgCodeLike(orgCode+"%");
		List<BotAvailableTemplate> list = botAvailableTemplateMapper.selectByExample(example);
		if(null != list && list.size() > 0) {
			for(BotAvailableTemplate template : list) {
				BotSentenceProcess process = botSentenceProcessService.getBotsentenceProcessByTemplateId(template.getTemplateId());
				template.setTemplateName(process.getTemplateName());
			}
		}
		return list;
	}
	
	/**
	 * 用户可用话术
	 * @return 
	 */
	public List<BotAvailableTemplate> getUserAvailableTemplate( Long userId, String orgCode){
		List<BotAvailableTemplate> list = new ArrayList<>();
		if(authService.isAgent(userId)){
			BotAvailableTemplateExample example = new BotAvailableTemplateExample();
			example.createCriteria()
					.andOrgCodeLike(orgCode+"%");
			list = botAvailableTemplateMapper.selectByExample(example);
		}else{
			list = botAvailableTemplateMapper.getUserAvailableTemplate(userId);
		}
		
		if(null != list && list.size() > 0) {
			for(BotAvailableTemplate template : list) {
				if(StringUtils.isNotBlank(template.getTemplateId())) {
					BotSentenceProcess process = botSentenceProcessService.getBotsentenceProcessByTemplateId(template.getTemplateId());
					if(null != process) {
						template.setTemplateName(process.getTemplateName());
					}
				}
			}
		}
		return list;
	}
	
	/**
	 * 用户添加可用话术
	 */
	public void addUserAvailableTemplate(Long userId,String availableId){
		botAvailableTemplateMapper.addUserAvailableTemplate( userId,availableId.split(","));
	}

    public List<BotAvailableTemplate> getTemplateByOrgCode(String orgCode) {
        BotAvailableTemplateExample example=new BotAvailableTemplateExample();
        example.createCriteria().andOrgCodeLike(orgCode+"%");
        List<BotAvailableTemplate> list =  botAvailableTemplateMapper.selectByExample(example);
        
        if(null != list && list.size() > 0) {
			for(BotAvailableTemplate template : list) {
				BotSentenceProcess process = botSentenceProcessService.getBotsentenceProcessByTemplateId(template.getTemplateId());
				template.setTemplateName(process.getTemplateName());
			}
		}
        return list;
    }
}
