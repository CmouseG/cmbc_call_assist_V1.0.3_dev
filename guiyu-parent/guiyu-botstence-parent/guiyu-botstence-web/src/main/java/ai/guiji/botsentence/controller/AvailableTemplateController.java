package ai.guiji.botsentence.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guiji.common.model.process.ProcessTypeEnum;
import com.guiji.guiyu.message.model.PublishBotstenceResultMsgVO;

import ai.guiji.botsentence.dao.entity.BotAvailableTemplate;
import ai.guiji.botsentence.receiver.UpdateReceiverResolver;
import ai.guiji.botsentence.service.impl.AvailableTemplateService;
import ai.guiji.component.client.config.JsonParam;
import ai.guiji.component.model.ServerResult;

@RestController
@RequestMapping("available")
public class AvailableTemplateController {
	
	@Autowired
	private AvailableTemplateService availableTemplateService;

	/**
	 * 企业可用话术
	 */
	@RequestMapping("getOrgAvailableTemplate")
	public ServerResult getOrgAvailableTemplate(@RequestHeader Long userId){
		List<BotAvailableTemplate> list=availableTemplateService.getOrgAvailableTemplate(userId);
		return ServerResult.createBySuccess(list);
	}
	
	/**
	 * 企业可用话术
	 */
	@RequestMapping("getAdminOrgAvailableTemplate")
	public ServerResult getAdminOrgAvailableTemplate(@JsonParam Long userId){
		List<BotAvailableTemplate> list=availableTemplateService.getOrgAvailableTemplate(userId);
		return ServerResult.createBySuccess(list);
	}


	/**
	 * 用户管理，根据组织机构查询企业的话术
	 */
	@GetMapping("getTemplateByOrgCode")
	public ServerResult getTemplateByOrgCode(@JsonParam String orgCode){
		List<BotAvailableTemplate> list=availableTemplateService.getTemplateByOrgCode(orgCode);
		Map map = new HashMap();
		map.put("list",list);
		if(list!=null){
			map.put("count",list.size());
		}else{
			map.put("count",0);
		}
		return ServerResult.createBySuccess(list);
	}
	
	/**
	 * 用户可用话术
	 */
	@RequestMapping("getUserAvailableTemplate")
	public ServerResult getUserAvailableTemplate(@RequestHeader Long userId){
		List<BotAvailableTemplate>  list=availableTemplateService.getUserAvailableTemplate(userId);
		return ServerResult.createBySuccess(list);
	}
	
	
	
	/**
	 * 管理员查找用户可用话术
	 */
	@RequestMapping("getAdminUserAvailableTemplate")
	public ServerResult getAdminUserAvailableTemplate(Long userId){
		List<BotAvailableTemplate>  list=availableTemplateService.getUserAvailableTemplate(userId);
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
