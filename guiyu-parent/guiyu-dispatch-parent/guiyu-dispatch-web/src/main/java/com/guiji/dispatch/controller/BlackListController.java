package com.guiji.dispatch.controller;

import com.guiji.component.jurisdiction.Jurisdiction;
import com.guiji.dispatch.dto.QueryBlackListDto;
import com.guiji.dispatch.service.GetAuthUtil;
import com.guiji.dispatch.sys.ResultPage;
import com.guiji.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.guiji.common.model.Page;
import com.guiji.dispatch.bean.MessageDto;
import com.guiji.dispatch.dao.entity.BlackList;
import com.guiji.dispatch.dao.entity.BlackListRecords;
import com.guiji.dispatch.service.IBlackListService;
import com.guiji.dispatch.util.Log;

@RestController
public class BlackListController {

	private Logger logger = LoggerFactory.getLogger(BlackListController.class);

	@Autowired
	private IBlackListService blackListService;

	@Autowired
	private GetAuthUtil getAuthUtil;

	/**
	 * 保存黑名单
	 * @param blackList
	 * @param userId
	 * @param orgCode
	 * @return
	 */
	@Jurisdiction("taskCenter_blackList_add")
	@PostMapping("saveBlackList")
	public boolean saveBlackList(@RequestBody BlackList blackList, @RequestHeader Long userId,@RequestHeader String orgCode) {
		return blackListService.save(blackList,userId,orgCode);
	}
	/**
	 * 删除号码
	 * @param id
	 * @return 
	 */
	@Jurisdiction("taskCenter_blackList_delete")
	@PostMapping("deleteBlackListById")
	public boolean deleteBlackListById(@RequestParam(required = true, name = "id") String id) {
		return blackListService.delete(id);
	}
	/**
	 * 修改黑名单列表
	 * @param blackList
	 * @param userId
	 * @return
	 */
	@Jurisdiction("taskCenter_blackList_edit")
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
			@RequestParam(required = true, name = "pagesize") int pagesize,@RequestHeader String orgCode,
			@RequestHeader Integer isDesensitization,@RequestHeader Long userId, @RequestHeader Integer authLevel){
		return blackListService.queryBlackListByParams(pagenum, pagesize,phone,orgCode,isDesensitization,userId, authLevel);
	}

	/**
	 * 上传导入黑名单文件
	 * @param file
	 * @param userId
	 * @param orgCode
	 * @return
	 */
	@Jurisdiction("taskCenter_blackList_batchImport")
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

	/**
	 * 查询黑名单导入列表
	 * @param pagenum
	 * @param pagesize
	 * @param orgCode
	 * @return
	 */
//	@Jurisdiction("taskCenter_blackList_batchImportDetail")
	@Log(info = "查询黑名单导入列表")
	@RequestMapping(value = "selectBlackListRecords", method = {RequestMethod.POST, RequestMethod.GET})
	public ResultPage<BlackListRecords> selectBlackListRecords(@RequestHeader String userId, @RequestHeader String orgCode, @RequestHeader Integer authLevel,
															   @RequestBody QueryBlackListDto queryBlackParam){
		if(queryBlackParam == null){
			queryBlackParam = new QueryBlackListDto();
		}

		//过滤权限
		userId = getAuthUtil.getUserIdByAuthLevel(authLevel, userId+"");//获取用户ID
		orgCode = getAuthUtil.getOrgCodeByAuthLevel(authLevel, orgCode);//获取企业组织编码
		queryBlackParam.setUserId(null != userId ?Integer.valueOf(userId):null);
		queryBlackParam.setOrgCode(orgCode);
		queryBlackParam.setAuthLevel(authLevel);

		logger.info("/selectBlackListRecords:{}", JsonUtils.bean2Json(queryBlackParam));
		return blackListService.queryBlackListRecords(queryBlackParam);
	}

	
	
//	@PostMapping("deleteBlackListRecordsById")
//	public Page<BlackListRecords> deleteBlackListRecordsById(	@RequestParam(required = false, name = "userName")  String userName,@RequestParam(required = true, name = "pagenum") int pagenum,
//			@RequestParam(required = true, name = "pagesize") int pagesize,@RequestHeader String orgCode){
//		return blackListService.queryBlackListRecords(pagenum, pagesize,userName,orgCode);
//	}


}
