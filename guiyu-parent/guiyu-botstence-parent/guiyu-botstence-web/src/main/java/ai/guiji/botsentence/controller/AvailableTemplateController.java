package ai.guiji.botsentence.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.guiji.botsentence.dao.entity.BotAvailableTemplate;
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
	public ServerResult getAdminUserAvailableTemplate(@JsonParam Long userId){
		List<BotAvailableTemplate>  list=availableTemplateService.getUserAvailableTemplate(userId);
		return ServerResult.createBySuccess(list);
	}
	
	/**
	 * 用户添加可用话术
	 */
	@RequestMapping("addUserAvailableTemplate")
	public ServerResult addUserAvailableTemplate(@JsonParam Long userId,@JsonParam String availableIds){
		availableTemplateService.addUserAvailableTemplate(userId,availableIds);
		return ServerResult.createBySuccess();
	}
}
