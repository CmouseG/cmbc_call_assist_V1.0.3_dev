package ai.guiji.botsentence.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ai.guiji.botsentence.dao.entity.BusinessAnswerTaskExt;
import ai.guiji.botsentence.service.BusinessAnswerTaskService;
import ai.guiji.botsentence.vo.BusinessAnswerVo;
import ai.guiji.component.client.config.JsonParam;
import ai.guiji.component.model.ServerResult;

@RestController
@RequestMapping("businessAnswerTask")
public class BusinessAnswerTaskController {

	@Autowired
	private BusinessAnswerTaskService service;
	
	private Logger logger = LoggerFactory.getLogger(BusinessAnswerTaskController.class);
	
	/**
	 * 分页查询当前话术流程的业务问答信息
	 */
	@RequestMapping(value="/queryBusinessAnswerList")
	public ServerResult<List<BusinessAnswerTaskExt>> queryBusinessAnswerListByPage(@JsonParam String processId) {
		logger.info("当前请求参数: processId= " + processId);
		/*logger.info("当前请求参数: pageSize= " + pageSize);
		logger.info("当前请求参数: pageNo = " + pageNo);*/
		if(StringUtils.isBlank(processId)) {
			return ServerResult.createByErrorMessage("请求参数为空!");
		}
		List<BusinessAnswerTaskExt> list = service.queryBusinessAnswerListByPage(processId);
		
		/*Page<BusinessAnswerTaskExt> page = new Page<BusinessAnswerTaskExt>();
		page.setPageSize(pageSize);
		page.setPageNo(pageNo);
		
		//计算总记录数量
		int totalNum = service.countBusinessAnswerNum(processId);
		page.setTotal(totalNum);
		page.setRecords(list);*/
		
		return ServerResult.createBySuccess(list);
	}
	
	/**
	 * 添加当前话术流程的业务问答信息
	 * @param param
	 * @return
	 */
	@RequestMapping(value="/addBusinessAnswer")
	public ServerResult addBusinessAnswer(@JsonParam BusinessAnswerVo param,@RequestHeader Long userId){
		service.addBusinessAnswer(param,userId);
		//List<BusinessAnswerTaskExt> list = service.queryBusinessAnswerListByPage(param.getProcessId());
		return ServerResult.createBySuccess();
	}
	
	/**
	 * 删除当前话术流程的业务问答信息
	 * @param
	 * @return
	 */
	@RequestMapping(value="/delBusinessAnswer")
	public ServerResult delBusinessAnswer(@JsonParam String processId, @JsonParam String branchId,@RequestHeader Long userId){
		service.delBusinessAnswer(branchId,userId);
		//List<BusinessAnswerTaskExt> list = service.queryBusinessAnswerListByPage(processId);
		return ServerResult.createBySuccess();
	}
	
	/**
	 * 更新当前话术流程的业务问答信息
	 * @param
	 * @return
	 */
	@RequestMapping(value="/updateBusinessAnswerById")
	public ServerResult updateBusinessAnswerById(@JsonParam BusinessAnswerVo record,@RequestHeader Long userId){
		service.updateBusinessAnswer(record,userId);
		//List<BusinessAnswerTaskExt> list = service.queryBusinessAnswerListByPage(record.getProcessId());
		return ServerResult.createBySuccess();
	}
}
