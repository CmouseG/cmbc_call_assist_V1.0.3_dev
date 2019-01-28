package com.guiji.botsentence.service.impl;

import java.util.List;

import com.guiji.user.dao.entity.SysOrganization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.auth.api.IAuth;
import com.guiji.botsentence.dao.BotAvailableTemplateMapper;
import com.guiji.botsentence.dao.entity.BotAvailableTemplate;
import com.guiji.botsentence.dao.entity.BotAvailableTemplateExample;
import com.guiji.botsentence.service.AuthService;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.user.dao.entity.SysUser;

@Service
public class AvailableTemplateService {
	
	@Autowired
	private IAuth iAuth;
	
	@Autowired
	private BotAvailableTemplateMapper botAvailableTemplateMapper;
	@Autowired
	AuthService authService;
	
	/**
	 * 企业可用话术
	 */
	public List<BotAvailableTemplate>  getOrgAvailableTemplate(Long userId){
		ReturnData<SysOrganization> data=iAuth.getOrgByUserId(userId);
		String orgCode=data.getBody().getCode();
		BotAvailableTemplateExample example=new BotAvailableTemplateExample();
		example.createCriteria().andOrgCodeLike(orgCode+"%");
		return botAvailableTemplateMapper.selectByExample(example);
	}
	
	/**
	 * 用户可用话术
	 * @return 
	 */
	public List<BotAvailableTemplate> getUserAvailableTemplate( Long userId, String orgCode){

		if(authService.isAgent(userId)){
			BotAvailableTemplateExample example = new BotAvailableTemplateExample();
			example.createCriteria()
					.andOrgCodeLike(orgCode+"%");
			return botAvailableTemplateMapper.selectByExample(example);
		}else{
			return botAvailableTemplateMapper.getUserAvailableTemplate(userId);
		}

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
        return botAvailableTemplateMapper.selectByExample(example);
    }
}
