package com.guiji.dispatch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.guiji.common.model.Page;
import com.guiji.dispatch.bean.MessageDto;
import com.guiji.dispatch.dao.entity.BlackList;
import com.guiji.dispatch.dao.entity.BlackListRecords;
import com.guiji.dispatch.service.IBlackListService;
import com.guiji.dispatch.util.Log;

@RestController
public class BlackListController {

	@Autowired
	private IBlackListService blackListService;

	/**
	 * 查询
	 * @param blackList
	 * @param userId
	 * @param orgCode
	 * @return
	 */
	@PostMapping("saveBlackList")
	public boolean saveBlackList(@RequestBody BlackList blackList, @RequestHeader Long userId,@RequestHeader String orgCode) {
		return blackListService.save(blackList,userId,orgCode);
	}
	/**
	 * 删除号码
	 * @param phone
	 * @return 
	 */
	@PostMapping("deleteBlackListById")
	public boolean deleteBlackListById(@RequestParam(required = true, name = "id") String id) {
		return blackListService.delete(id);
	}
	/**
	 * 修改
	 * @param blackList
	 * @param userId
	 * @return
	 */
	@PostMapping("updateBlackList")
	public boolean updateBlackList(@RequestBody BlackList blackList,@RequestHeader Long userId) {
		return blackListService.update(blackList,userId);
	}
	
	/**
	 * 查询
	 * @param pagenum
	 * @param pagesize
	 * @param orgCode
	 * @return
	 */
	@PostMapping("selectBlackList")
	public Page<BlackList> selectBlackList(	@RequestParam(required = false, name = "phone")  String phone,@RequestParam(required = true, name = "pagenum") int pagenum,
			@RequestParam(required = true, name = "pagesize") int pagesize,@RequestHeader String orgCode){
		return blackListService.queryBlackListByParams(pagenum, pagesize,phone,orgCode);
	}
	
	
	@Log(info = "文件上传")
	@PostMapping("batchImportBlackList")
	public MessageDto batchImportBlackList(@RequestParam("file") MultipartFile file, @RequestHeader Long userId, @RequestHeader String orgCode) {
		String fileName = file.getOriginalFilename();
		MessageDto batchImport = new MessageDto();

		try {
			blackListService.batchPlanImport(fileName, userId, file,orgCode);
		} catch (Exception e) {
			batchImport.setResult(false);
			batchImport.setMsg(e.getMessage());
		}
		return batchImport;
	}
	
	
	@PostMapping("selectBlackListRecords")
	public Page<BlackListRecords> selectBlackListRecords(@RequestParam(required = true, name = "pagenum") int pagenum,
			@RequestParam(required = true, name = "pagesize") int pagesize,@RequestHeader String orgCode){
		return blackListService.queryBlackListRecords(pagenum, pagesize,orgCode);
	}

	
	
//	@PostMapping("deleteBlackListRecordsById")
//	public Page<BlackListRecords> deleteBlackListRecordsById(	@RequestParam(required = false, name = "userName")  String userName,@RequestParam(required = true, name = "pagenum") int pagenum,
//			@RequestParam(required = true, name = "pagesize") int pagesize,@RequestHeader String orgCode){
//		return blackListService.queryBlackListRecords(pagenum, pagesize,userName,orgCode);
//	}


}
