package com.guiji.botsentence.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guiji.auth.api.IAuth;
import com.guiji.auth.model.UserAuth;
import com.guiji.botsentence.dao.entity.BotAvailableTemplate;
import com.guiji.botsentence.dao.entity.BotSentenceProcess;
import com.guiji.botsentence.receiver.UpdateReceiverResolver;
import com.guiji.botsentence.service.IBotSentenceProcessService;
import com.guiji.botsentence.service.impl.AvailableTemplateService;
import com.guiji.common.model.process.ProcessTypeEnum;
import com.guiji.component.client.config.JsonParam;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.component.result.ServerResult;
import com.guiji.guiyu.message.model.PublishBotstenceResultMsgVO;

@RestController
@RequestMapping("available")
public class AvailableTemplateController {
	
	@Autowired
	private AvailableTemplateService availableTemplateService;
	@Autowired
	IBotSentenceProcessService botSentenceProcessService;
	@Autowired
	private IAuth iAuth;

	/**
	 * 企业可用话术
	 */
	@RequestMapping("getOrgAvailableTemplate")
	public ServerResult getOrgAvailableTemplate(@RequestHeader Long userId, @RequestHeader Integer authLevel){
		List<BotAvailableTemplate> list=availableTemplateService.getOrgAvailableTemplate(userId, authLevel);
		return ServerResult.createBySuccess(list);
	}
	
	/**
	 * 企业可用话术
	 */
	@RequestMapping("getAdminOrgAvailableTemplate")
	public ServerResult getAdminOrgAvailableTemplate(@JsonParam Long userId, @RequestHeader Integer authLevel){
		ReturnData<UserAuth> data=iAuth.queryUserDataAuth(new Long(userId));
		List<BotAvailableTemplate> list=availableTemplateService.getOrgAvailableTemplate(userId, data.getBody().getAuthLevel());
		return ServerResult.createBySuccess(list);
	}


    /**
     * 用户管理，根据组织机构查询企业的话术
     */
    @GetMapping("getTemplateByOrgCode")
    public ServerResult getTemplateByOrgCode(@JsonParam String orgCode){
		List<BotSentenceProcess> list=botSentenceProcessService.getTemplateByOrgCode(orgCode);
        return ServerResult.createBySuccess(list);
    }
    
    /**
     * 用户管理，根据组织机构查询企业的话术数量
     */
    @GetMapping("countTemplateByOrgCode")
    public ServerResult<Integer> countTemplateByOrgCode(@JsonParam String orgCode){
    	if(StringUtils.isBlank(orgCode)) {
    		return ServerResult.createByErrorMessage("机构号为空!");
    	}
		int num = botSentenceProcessService.countTemplateByOrgCode(orgCode);
        return ServerResult.createBySuccess(num);
    }
    
	/**
	 * 用户可用话术
	 */
	@RequestMapping("getUserAvailableTemplate")
	public ServerResult getUserAvailableTemplate(@RequestHeader Long userId,@RequestHeader String orgCode, @RequestHeader Integer authLevel){
		List<BotAvailableTemplate>  list=availableTemplateService.getUserAvailableTemplate(userId,orgCode,authLevel);
		return ServerResult.createBySuccess(list);
	}
	
	
	
	/**
	 * 管理员查找用户可用话术
	 */
	@RequestMapping("getAdminUserAvailableTemplate")
	public ServerResult getAdminUserAvailableTemplate(Long userId,@RequestHeader String orgCode, @RequestHeader Integer authLevel){
		List<BotAvailableTemplate>  list=availableTemplateService.getUserSelectedTemplate(userId);
		return ServerResult.createBySuccess(list);
	}
	
	/**
	 * 用户添加可用话术
	 */
	@RequestMapping("addUserAvailableTemplate")
	public ServerResult addUserAvailableTemplate(Long userId,String availableIds){
		availableTemplateService.addUserAvailableTemplate(userId,availableIds);
		return ServerResult.createBySuccess();
	}
	
	@Autowired
	private UpdateReceiverResolver updateReceiverResolver;
	
	@RequestMapping("test")
	public boolean test(String name){
		PublishBotstenceResultMsgVO param=new PublishBotstenceResultMsgVO();
		param.setResult(0);
		param.setTmplId(name);
		param.setProcessTypeEnum(ProcessTypeEnum.SELLBOT);
		updateReceiverResolver.resolver(param);
		param.setProcessTypeEnum(ProcessTypeEnum.ROBOT);
		updateReceiverResolver.resolver(param);
		param.setProcessTypeEnum(ProcessTypeEnum.FREESWITCH);
		updateReceiverResolver.resolver(param);
		return true;
	}
}
