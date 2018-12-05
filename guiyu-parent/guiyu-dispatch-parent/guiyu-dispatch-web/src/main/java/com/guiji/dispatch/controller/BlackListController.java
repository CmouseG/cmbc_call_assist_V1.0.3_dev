package com.guiji.dispatch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.guiji.common.model.Page;
import com.guiji.dispatch.dao.entity.BlackList;
import com.guiji.dispatch.service.IBlackListService;

@RestController
public class BlackListController {

	@Autowired
	private IBlackListService blackListService;

	@PostMapping("saveBlackList")
	public boolean saveBlackList(@RequestBody BlackList blackList) {
		return blackListService.save(blackList);
	}
	@PostMapping("deleteBlackListById")
	public boolean deleteBlackListById(@RequestParam(required = true, name = "id") Long id) {
		return blackListService.delete(id);
	}
	
	@PostMapping("updateBlackList")
	public boolean updateBlackList(@RequestBody BlackList blackList) {
		return blackListService.update(blackList);
	}
	
	@PostMapping("selectBlackList")
	public Page<BlackList> selectBlackList(	@RequestParam(required = true, name = "pagenum") int pagenum,
			@RequestParam(required = true, name = "pagesize") int pagesize){
		return blackListService.queryBlackListByParams(pagenum, pagesize);
	}


}
