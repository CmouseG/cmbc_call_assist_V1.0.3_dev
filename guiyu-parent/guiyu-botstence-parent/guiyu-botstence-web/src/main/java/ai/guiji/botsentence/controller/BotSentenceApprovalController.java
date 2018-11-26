package ai.guiji.botsentence.controller;

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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import ai.guiji.botsentence.constant.Constant;
import ai.guiji.botsentence.dao.BotSentenceDomainMapper;
import ai.guiji.botsentence.dao.BotSentenceProcessMapper;
import ai.guiji.botsentence.dao.UserAccountMapper;
import ai.guiji.botsentence.dao.entity.BotSentenceDomain;
import ai.guiji.botsentence.dao.entity.BotSentenceProcess;
import ai.guiji.botsentence.dao.entity.UserAccount;
import ai.guiji.botsentence.dao.entity.UserAccountExample;
import ai.guiji.botsentence.dao.ext.BotSentenceDomainExtMapper;
import ai.guiji.botsentence.service.IBotSentenceApprovalService;
import ai.guiji.botsentence.service.impl.FileGenerateServiceImpl;
import ai.guiji.botsentence.service.impl.VoliceServiceImpl;
import ai.guiji.botsentence.vo.BotSentenceProcessVO;
import ai.guiji.botsentence.vo.DomainVO;
import ai.guiji.component.client.config.JsonParam;
import ai.guiji.component.client.util.BeanUtil;
import ai.guiji.component.client.util.DateUtil;
import ai.guiji.component.exception.CommonException;
import ai.guiji.component.model.Page;
import ai.guiji.component.model.ServerResult;

/**
 * 
 * @Description:话术审批
 * @author liyang  
 * @date 2018年8月16日  
 *
 */
@RestController
@RequestMapping(value="approval")
public class BotSentenceApprovalController {
	
	@Autowired
	private IBotSentenceApprovalService botSentenceApprovalService;
	
	@Autowired
	private UserAccountMapper userAccountMapper;
	
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

	/**
	 *  获取待审核话术列表
	 */
	@RequestMapping(value="getListApproving")
	public ServerResult<Page<BotSentenceProcessVO>> getListApproving(@JsonParam int pageSize,
			@JsonParam int pageNo, @JsonParam String templateName, @JsonParam String accountNo,@RequestHeader Long userId) {
		
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
				UserAccountExample example = new UserAccountExample();
				example.createCriteria().andAccountNoEqualTo(temp.getCrtUser());
				List<UserAccount> accountList = userAccountMapper.selectByExample(example);
				if(null != accountList && accountList.size() > 0) {
					vo.setHost(accountList.get(0).getHost());
					vo.setAccountNo(accountList.get(0).getAccountNo().split(accountList.get(0).getHost() + "-")[1]);
					vo.setMachineCode(accountList.get(0).getMachineCode());
				}
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
	public ServerResult notPassApproval(@JsonParam String processId,@RequestHeader Long userId) {
		
		if(StringUtils.isBlank(processId)) {
			throw new CommonException("缺少参数！");
		}
		botSentenceApprovalService.notPassApproval(processId,userId);
		return ServerResult.createBySuccess();
	}
	
	/**
	 *  审批话术通过
	 */
	@RequestMapping(value="passApproval")
	public ServerResult passApproval(@JsonParam String processId, @JsonParam List<JSONObject> selectedList,@RequestHeader Long userId) {
		
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
		
		botSentenceApprovalService.passApproval(processId, list,userId);
		return ServerResult.createBySuccess();
	}
	
	/**
	 * 发布话术
	 * @return
	 */
	@RequestMapping(value="publishSentence")
	public void publishSentence(@JsonParam String processId,@RequestHeader Long userId){
		botSentenceApprovalService.publishSentence(processId,userId);
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
	public void downloadTemplate(@JsonParam String processId, @JsonParam String selectedList, HttpServletResponse resp) throws Exception{
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
		
		
		File file = fileGenerateService.fileGenerate(processId, dirName);
		
		
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
	}
}
