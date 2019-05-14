package com.guiji.botsentence.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONObject;
import com.guiji.botsentence.constant.Constant;
import com.guiji.botsentence.dao.BotSentenceDomainMapper;
import com.guiji.botsentence.dao.BotSentenceProcessMapper;
import com.guiji.botsentence.dao.entity.BotSentenceDomain;
import com.guiji.botsentence.dao.entity.BotSentenceProcess;
import com.guiji.botsentence.dao.entity.VoliceInfoExt;
import com.guiji.botsentence.dao.ext.BotSentenceDomainExtMapper;
import com.guiji.botsentence.service.IBotSentenceApprovalService;
import com.guiji.botsentence.service.impl.FileGenerateServiceImpl;
import com.guiji.botsentence.service.impl.VoliceServiceImpl;
import com.guiji.botsentence.vo.BotSentenceProcessVO;
import com.guiji.botsentence.vo.DomainVO;
import com.guiji.component.client.config.JsonParam;
import com.guiji.component.client.util.BeanUtil;
import com.guiji.component.client.util.DateUtil;
import com.guiji.component.jurisdiction.Jurisdiction;
import com.guiji.common.exception.CommonException;
import com.guiji.component.model.Page;
import com.guiji.component.result.ServerResult;

/**
 * 
 * @Description:话术审批
 * @author zhangpeng  
 * @date 2018年8月16日  
 *
 */
@RestController
@RequestMapping(value="approval")
public class BotSentenceApprovalController {
	
	@Autowired
	private IBotSentenceApprovalService botSentenceApprovalService;
	
	@Autowired
	private FileGenerateServiceImpl fileGenerateService;
	
	@Autowired
	private BotSentenceProcessMapper botSentenceProcessMapper;
	
	@Autowired
	private VoliceServiceImpl voliceServiceImpl;
	
	@Autowired
	private BotSentenceDomainExtMapper botSentenceDomainExtMapper;
	
	@Autowired
	private BotSentenceDomainMapper botSentenceDomainMapper;

	@Value("${offline}")
	private boolean offline;
	
	/**
	 *  获取待审核话术列表
	 */
	@RequestMapping(value="getListApproving")
	public ServerResult<Page<BotSentenceProcessVO>> getListApproving(@JsonParam int pageSize,
			@JsonParam int pageNo, @JsonParam String templateName, @JsonParam String accountNo) {
		
		Page<BotSentenceProcessVO> page = new Page<BotSentenceProcessVO>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		List<BotSentenceProcess> list = botSentenceApprovalService.getListApprovaling(pageSize, pageNo, templateName, accountNo);
		
		int totalNum = botSentenceApprovalService.countApprovaling(templateName);
		if(null != list) {
			
			List<BotSentenceProcessVO> results = new ArrayList<>();
			
			for(BotSentenceProcess temp : list) {
				BotSentenceProcessVO vo = new BotSentenceProcessVO();
				BeanUtil.copyProperties(temp, vo);
				if(Constant.APPROVE_MAEKING.equals(temp.getState())) {
					vo.setStateName("制作中");
				}else if(Constant.APPROVE_CHECKING.equals(temp.getState())) {
					vo.setStateName("审核中");
				}else if(Constant.APPROVE_PASS.equals(temp.getState())) {
					vo.setStateName("审核通过");
				}else if(Constant.APPROVE_NOTPASS.equals(temp.getState())) {
					vo.setStateName("审核不通过");
				}else if(Constant.APPROVE_ONLINE.equals(temp.getState())) {
					vo.setStateName("已上线");
				}else if(Constant.DEPLOYING.equals(temp.getState())) {
					vo.setStateName("部署中");
				}else if(Constant.ERROR.equals(temp.getState())) {
					vo.setStateName("部署失败");
				}
				
				if(null != temp.getCrtTime()) {
					vo.setCrtTimeStr(DateUtil.dateToString(temp.getCrtTime(), DateUtil.ymdhms));
				}
				if(null != temp.getLstUpdateTime()) {
					vo.setLstUpdateTimeStr(DateUtil.dateToString(temp.getLstUpdateTime(), DateUtil.ymdhms));
				}
				if(null != temp.getApproveTime()) {
					vo.setApproveTimeStr(DateUtil.dateToString(temp.getApproveTime(), DateUtil.ymdhms));
				}
				
				
				//设置域名、机器码
				/*UserAccountExample example = new UserAccountExample();
				example.createCriteria().andAccountNoEqualTo(temp.getCrtUser());
				List<UserAccount> accountList = userAccountMapper.selectByExample(example);
				if(null != accountList && accountList.size() > 0) {
					vo.setHost(accountList.get(0).getHost());
					vo.setAccountNo(accountList.get(0).getAccountNo().split(accountList.get(0).getHost() + "-")[1]);
					vo.setMachineCode(accountList.get(0).getMachineCode());
				}*/
				results.add(vo);
			}
			
			page.setRecords(results);
			page.setTotal(totalNum);
		}
		return ServerResult.createBySuccess(page);
		
	}
	
	
	/**
	 *  审批话术不通过
	 */
	@RequestMapping(value="notPassApproval")
	public ServerResult notPassApproval(@JsonParam String processId, @RequestHeader String userId) {
		
		if(StringUtils.isBlank(processId)) {
			throw new CommonException("缺少参数！");
		}
		botSentenceApprovalService.notPassApproval(processId, userId);
		return ServerResult.createBySuccess();
	}

	@RequestMapping(value="passAudit")
	public ServerResult passAudit(@JsonParam String processId, @RequestHeader String userId) {

		if(StringUtils.isBlank(processId)) {
			throw new CommonException("缺少参数！");
		}

		if(StringUtils.isBlank(userId)) {
			throw new CommonException("未知用户！");
		}

		botSentenceApprovalService.passAudit(processId, userId);

		botSentenceApprovalService.publishSentence(processId,userId);

		return ServerResult.createBySuccess();
	}

	/**
	 *  审批话术通过
	 */
	@RequestMapping(value="passApproval")
	public ServerResult passApproval(@JsonParam String processId, @JsonParam List<JSONObject> selectedList, @RequestHeader String userId) {
		
		if(StringUtils.isBlank(processId)) {
			throw new CommonException("缺少参数！");
		}
		
		if(null == selectedList || selectedList.size() < 1) {
			throw new CommonException("请选择默认主流程！");
		}
		List<DomainVO> list = new ArrayList<>();
		
		for(int i = 0 ; i < selectedList.size() ; i++) {
			JSONObject jsonObject = selectedList.get(i);
			DomainVO domain = JSONObject.toJavaObject(jsonObject, DomainVO.class);
			list.add(domain);
		}
		botSentenceApprovalService.passApproval(processId, list, userId);

		botSentenceApprovalService.publishSentence(processId,userId);
		
		return ServerResult.createBySuccess();
	}
	
	/**
	 * 查询话术流程所有的分支流程
	 * @param processId
	 * @return
	 */
	@RequestMapping(value="queryComProcessList")
	public ServerResult<List> queryComProcessList(@JsonParam String processId){
		if(StringUtils.isBlank(processId)) {
			throw new CommonException("缺少参数！");
		}
		
		List list = botSentenceApprovalService.queryComProcess2(processId);
		return ServerResult.createBySuccess(list);
	}
	
	
	
	/**
	 * 导出模板文件
	 * @param processId
	 * @throws Exception 
	 */
	@RequestMapping("downloadTemplate")
	@Transactional
	@Jurisdiction("botsentence_approve_export")
	public void downloadTemplate(@JsonParam String processId, @JsonParam String selectedList, HttpServletResponse resp, @RequestHeader String userId) throws Exception{
		BotSentenceProcess botSentenceProcess = botSentenceProcessMapper.selectByPrimaryKey(processId);
		String templateId = botSentenceProcess.getTemplateId();
		String dirName = templateId;
		
		if(StringUtils.isBlank(selectedList)) {
			throw new CommonException("请选择默认主流程！");
		}
		
		String [] array = selectedList.split("-");
		
		//把之前的com_domain全部设置为空
		botSentenceDomainExtMapper.batchUpdateComDomain(processId);
		
		List<BotSentenceDomain> list = new ArrayList<>();
		
		for(int i = 0 ; i < array.length ; i++) {
			String domainId = array[i];
			BotSentenceDomain domain = botSentenceDomainMapper.selectByPrimaryKey(domainId);
			list.add(domain);
		}
		
		//设置默认主流程
		for(int i = 0 ; i < list.size() - 1 ; i++) {
			BotSentenceDomain domain = list.get(i);
			domain.setComDomain(list.get(i+1).getDomainName());
			domain.setIsMainFlow("01");
			domain.setLstUpdateTime(new Date());
			//domain.setLstUpdateUser(UserUtil.getUserId());
			botSentenceDomainMapper.updateByPrimaryKey(domain);
		}
		
		File file = null;
		try {
			file = fileGenerateService.fileGenerate(processId, dirName, null, userId);
		}catch(CommonException e) {
			OutputStream out = resp.getOutputStream();
			resp.addHeader("Access-Control-Expose-Headers", "errorcode");
			resp.setHeader("content-type", "text/html;charset=UTF-8");
			resp.setHeader("errorcode", "downloand failed" );
			resp.setCharacterEncoding("UTF-8");
			
            //out.flush();
            //out.close();
			//resp.sendError(500, e.getMessage());
			//resp.setStatus(422, e.getMessage());
			//resp.setHeader("zhouqi", e.getMessage());
			return;
		}
		
		
		
		OutputStream out=null;
		try {
			out = resp.getOutputStream();
			
			File zipFile;
			String fileName = templateId;
			zipFile = File.createTempFile(fileName, ".zip");
			voliceServiceImpl.zip(file, zipFile.getPath());
			
			byte[] buffer = null;
            FileInputStream fis = new FileInputStream(zipFile);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1)
            {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
			
			resp.reset();
            resp.setHeader("Content-Disposition","attachment;fileName="+fileName+".zip");
            resp.setContentType("application/octet-stream;charset=UTF-8");
            resp.setHeader("Access-Control-Allow-Origin", "*");
            IOUtils.write(buffer, out);
            out.flush();
            out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(out!=null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		file.delete();
	}

	/**
	 * 导出模板文件
	 */
	@RequestMapping("exportSpeechFile")
	@Transactional
	@Jurisdiction("botsentence_approve_export")
	public void exportSpeechFile(@JsonParam String processId, HttpServletResponse resp, @RequestHeader String userId) throws Exception{
		BotSentenceProcess process = botSentenceProcessMapper.selectByPrimaryKey(processId);

		if(null == process){
			throw new CommonException("话术不存在！");
		}

		botSentenceApprovalService.resetComDomain(processId, userId);

		File file = null;
		try {
			file = fileGenerateService.fileGenerate(processId, process.getTemplateId(), null, userId);
		}catch(CommonException e) {
			OutputStream out = resp.getOutputStream();
			resp.addHeader("Access-Control-Expose-Headers", "errorcode");
			resp.setHeader("content-type", "text/html;charset=UTF-8");
			resp.setHeader("errorcode", "downloand failed" );
			resp.setCharacterEncoding("UTF-8");
			return;
		}
		OutputStream out=null;
		try {
			out = resp.getOutputStream();
			File zipFile;
			String fileName = process.getTemplateId();
			zipFile = File.createTempFile(fileName, ".zip");
			voliceServiceImpl.zip(file, zipFile.getPath());
			byte[] buffer = null;
			FileInputStream fis = new FileInputStream(zipFile);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			int n;
			while ((n = fis.read(b)) != -1)
			{
				bos.write(b, 0, n);
			}
			fis.close();
			bos.close();
			buffer = bos.toByteArray();

			resp.reset();
			resp.setHeader("Content-Disposition","attachment;fileName="+fileName+".zip");
			resp.setContentType("application/octet-stream;charset=UTF-8");
			resp.setHeader("Access-Control-Allow-Origin", "*");
			IOUtils.write(buffer, out);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(out!=null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		file.delete();
	}
	
	@RequestMapping(value="deployTestSellbot")
	public ServerResult deployTestSellbot(@JsonParam String processId, @RequestHeader String userId) {
		botSentenceApprovalService.deployTestSellbotByScp(processId, userId);
		return ServerResult.createBySuccess();
	}
}
