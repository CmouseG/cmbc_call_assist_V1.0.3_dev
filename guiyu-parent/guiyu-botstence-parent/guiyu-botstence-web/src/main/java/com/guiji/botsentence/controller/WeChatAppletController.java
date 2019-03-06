package com.guiji.botsentence.controller;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.guiji.botsentence.constant.Constant;
import com.guiji.botsentence.dao.VoliceInfoMapper;
import com.guiji.botsentence.dao.entity.BotSentenceProcess;
import com.guiji.botsentence.dao.entity.BotSentenceTtsBackup;
import com.guiji.botsentence.dao.entity.BotSentenceTtsBackupExample;
import com.guiji.botsentence.dao.entity.BotSentenceTtsTask;
import com.guiji.botsentence.dao.entity.BotSentenceTtsTaskExample;
import com.guiji.botsentence.dao.entity.VoliceInfo;
import com.guiji.botsentence.dao.entity.VoliceInfoExt;
import com.guiji.botsentence.dao.ext.VoliceInfoExtMapper;
import com.guiji.botsentence.service.IBotSentenceLogService;
import com.guiji.botsentence.service.ISelfTestService;
import com.guiji.botsentence.service.IVoliceService;
import com.guiji.botsentence.service.IWeChatAppletService;
import com.guiji.botsentence.service.impl.BotSentenceApprovalServiceImpl;
import com.guiji.botsentence.service.impl.BotSentenceProcessServiceImpl;
import com.guiji.botsentence.service.impl.VoliceServiceImpl;
import com.guiji.botsentence.util.AudioConvertUtil;
import com.guiji.botsentence.util.BotSentenceUtil;
import com.guiji.botsentence.vo.BotSentenceProcessVO;
import com.guiji.botsentence.vo.ResponseSelfTestVO;
import com.guiji.botsentence.vo.SelfTestVO;
import com.guiji.component.client.config.JsonParam;
import com.guiji.component.client.util.BeanUtil;
import com.guiji.component.client.util.DateUtil;
import com.guiji.common.exception.CommonException;
import com.guiji.component.result.ServerResult;

/**
 * 微信小程序相关前后台服务类
 * @author 张朋
 *
 */
@RequestMapping(value="wechat")
@RestController
public class WeChatAppletController {
	
	private Logger logger = LoggerFactory.getLogger(WeChatAppletController.class);
	
	@Autowired
	private IWeChatAppletService weChatAppletService;
	
	@Autowired
	private BotSentenceProcessServiceImpl botSentenceProcessService;
	
	@Autowired
	private VoliceServiceImpl voliceService;
	
	@Autowired
	private IVoliceService service;
	
	@Autowired
	private VoliceInfoExtMapper voliceInfoExtMapper;
	
	@Autowired
	private IBotSentenceLogService botSentenceLogService;
	
	@Autowired
    ISelfTestService selfTestService;
    
    @Autowired
    BotSentenceApprovalServiceImpl botSentenceApprovalService;
	
	private static String FILE_SEPARATOR = System.getProperty("file.separator");
	
	@Value("${create.file.tmp}")
	private String tempFileDir;
	
	@RequestMapping(value="updateAccount")
	public ServerResult<String> updateAccount(@JsonParam String accountNo, @JsonParam String password) {
		if(StringUtils.isNotBlank(accountNo) && StringUtils.isNotBlank(password)) {
			weChatAppletService.updateAccount(accountNo, password);
			return ServerResult.createBySuccess("更新成功");
		}
		return ServerResult.createByErrorMessage("账号或者密码为空!");
	}

	
	/*@RequestMapping(value="login")
	public ServerResult<JwtAccount> login(@JsonParam String accountNo, @JsonParam String password) {
		if(StringUtils.isNotBlank(accountNo) && StringUtils.isNotBlank(password)) {
			JwtAccount jwtAccount = weChatAppletService.login(accountNo, password);
			if(null != jwtAccount) {
				return ServerResult.createBySuccess(jwtAccount);
			}else {
				return ServerResult.createByErrorMessage("登录失败，账号或密码错误 ");
			}
		}
		return ServerResult.createByErrorMessage("账号或者密码为空!");
	}*/
	
	
	@RequestMapping(value="queryBotSentenceProcessListByAccountNo")
	public ServerResult<List<BotSentenceProcessVO>> queryBotSentenceProcessListByAccountNo(@RequestParam("userId") String userId) {
		if(StringUtils.isBlank(userId)) {
			throw new CommonException("用户账号为空");
		}
		
		List<BotSentenceProcess> list = botSentenceProcessService.queryBotSentenceProcessList(100, 1, null, userId, userId);
		//List<BotSentenceProcess> list = botSentenceProcessService.queryBotSentenceProcessListByAccountNo(accountNo);
		List<BotSentenceProcessVO> results = new ArrayList<>();
		if(null != list) {
			for(BotSentenceProcess temp : list) {
				BotSentenceProcessVO vo = new BotSentenceProcessVO();
				BeanUtil.copyProperties(temp, vo);
				
				if(null != temp.getCrtTime()) {
					vo.setCrtTimeStr(DateUtil.dateToString(temp.getCrtTime(), DateUtil.ymdhms));
				}else {
					vo.setCrtTimeStr(DateUtil.dateToString(temp.getCrtTime(), DateUtil.ymdhms));
				}
				
				//获取每个模板对应的录音百分比
				//int finish = voliceService.countFinishNum(temp.getProcessId());
				//int unfinish = voliceService.countUnFinishNum(temp.getProcessId());
				int unfinish = voliceInfoExtMapper.countUnFinishNum(temp.getProcessId());
				int finish = voliceInfoExtMapper.countFinishNum(temp.getProcessId());
				int total = finish + unfinish;
				String volicedRate = null;
				
				vo.setFinish(finish);
				vo.setUnfinish(unfinish);
				
				if(finish == 0) {
					volicedRate = "0";
				}else {
					BigDecimal rate = new BigDecimal(finish).divide(new BigDecimal(total), 2, RoundingMode.HALF_DOWN).multiply(new BigDecimal(100));
					volicedRate = rate.intValue()+"";
				}
				vo.setVolicedRate(volicedRate);
				
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
				
				results.add(vo);
			}
		}
		return ServerResult.createBySuccess(results);
	}

	@RequestMapping(value="queryBotSentenceProcessByProcessId")
	public ServerResult<BotSentenceProcessVO> queryBotSentenceProcessByProcessId(@RequestParam("processId") String processId) {
		BotSentenceProcessVO vo = new BotSentenceProcessVO();
		BotSentenceProcess process = botSentenceProcessService.queryBotsentenceProcessInfo(processId);
		BeanUtil.copyProperties(process, vo);
		
		//获取每个模板对应的录音百分比
		//int finish = voliceService.countFinishNum(process.getProcessId());
		//int unfinish = voliceService.countUnFinishNum(process.getProcessId());
		int unfinish = voliceInfoExtMapper.countUnFinishNum(process.getProcessId());
		int finish = voliceInfoExtMapper.countFinishNum(process.getProcessId());
		int total = finish + unfinish;
		String volicedRate = null;
		
		vo.setFinish(finish);
		vo.setUnfinish(unfinish);
		
		if(finish == 0) {
			volicedRate = "0";
		}else {
			BigDecimal rate = new BigDecimal(finish).divide(new BigDecimal(total), 2, RoundingMode.HALF_DOWN).multiply(new BigDecimal(100));
			volicedRate = rate.intValue()+"";
		}
		vo.setVolicedRate(volicedRate);
		
		if(null != process.getCrtTime()) {
			vo.setCrtTimeStr(DateUtil.dateToString(process.getCrtTime(), DateUtil.ymdhms));
		}else {
			vo.setCrtTimeStr(DateUtil.dateToString(process.getCrtTime(), DateUtil.ymdhms));
		}
		
		if(Constant.APPROVE_MAEKING.equals(process.getState())) {
			vo.setStateName("制作中");
		}else if(Constant.APPROVE_CHECKING.equals(process.getState())) {
			vo.setStateName("审核中");
		}else if(Constant.APPROVE_PASS.equals(process.getState())) {
			vo.setStateName("审核通过");
		}else if(Constant.APPROVE_NOTPASS.equals(process.getState())) {
			vo.setStateName("审核不通过");
		}else if(Constant.APPROVE_ONLINE.equals(process.getState())) {
			vo.setStateName("已上线");
		}else if(Constant.DEPLOYING.equals(process.getState())) {
			vo.setStateName("部署中");
		}
		
		return ServerResult.createBySuccess(vo);
	}
	
	
	@RequestMapping(value="uploadOneVolice")
	@Transactional
	public ServerResult<VoliceInfoExt> uploadOneVolice(MultipartFile multipartFile,@RequestParam("processId") String processId, @RequestParam("voliceId") String voliceId,@RequestParam("type") String type, @RequestHeader String userId) {
		VoliceInfoExt vo = new VoliceInfoExt();
		File mp3File = null;
		File wavFile = null;
		OutputStream os = null;
		InputStream in = null;
		botSentenceLogService.saveLog(Constant.OPERATOR_UPLOAD_VOLICE_BY_WECHAT, userId);
		try {
			if(null != multipartFile && StringUtils.isNotBlank(voliceId)) {
				String fileName = multipartFile.getOriginalFilename();
				String suffix =  fileName.substring(fileName.lastIndexOf(".") + 1);
				String voliceUrl = null;
				int times=0;
				
				long size = multipartFile.getSize();
				logger.info("当前上传录音的大小: " + size);
				if(size > 1 * 1024 * 1024) {
					return ServerResult.createByErrorMessage("文件大小超过1M,请重新上传");
				}
				
				
				if(!("wav".equals(suffix) || "mp3".equals(suffix))) {
					return ServerResult.createByErrorMessage("请上传wav或mp3格式音频文件!");
				}
				
				InputStream inputStream = multipartFile.getInputStream();
				
				if(!"wav".equals(suffix)) {
					//保存文件到本地临时目录
					mp3File = new File(tempFileDir + FILE_SEPARATOR + multipartFile.getOriginalFilename());
					fileName = fileName.substring(0, fileName.lastIndexOf("."));
					
			        os = new FileOutputStream(mp3File.getPath());
					 byte[] bs = new byte[1024];
			            // 读取到的数据长度
			            int len;
			            // 输出的文件流保存到本地文件
			            
			            // 开始读取
			            while ((len = inputStream.read(bs)) != -1) {
			                os.write(bs, 0, len);
			            }
			            os.close();
					/*FileOutputStream out = new FileOutputStream(mp3File);
					BufferedReader read = new BufferedReader(new InputStreamReader(multipartFile.getInputStream()));
					String str = null;
					while((str = read.readLine()) != null) {
		                out.write(str.getBytes());
					}
					out.close();        //关闭流
					read.close();
					fileName = fileName.substring(0, fileName.lastIndexOf("."));
					File wavFile = new File(tempFileDir + FILE_SEPARATOR + fileName + ".wav");
					*/
			        wavFile = new File(tempFileDir + FILE_SEPARATOR + fileName + ".wav");
			        if(wavFile.exists()) {
			        	logger.info("当前文件已存在，删除");
						wavFile.delete();
					}
			        
					boolean flag = AudioConvertUtil.converWav(mp3File.getPath(), wavFile.getPath());
					if(!flag) {
						throw new CommonException("转换wav失败"+mp3File.getName());
					}
					logger.info("wav文件大小: " + wavFile.length());
					
					if(wavFile.length() > 1 * 1024 * 1024) {
						return ServerResult.createByErrorMessage("文件大小超过1M,请重新上传");
					}
					
					in = new FileInputStream(wavFile);
					
					
					try {
						//times = BotSentenceUtil.getMediaDuration(wavFile);
						times = BotSentenceUtil.getVideoTime(wavFile.getPath());
						logger.info("录音时长: " + times);
					} catch (Exception e) {
						logger.error("获取录音时长异常...", e);
					}
					
					voliceUrl = service.uploadOneVolice(processId, voliceId, in, type, times, userId);
					in.close();
					inputStream.close();
					mp3File.delete();
					wavFile.delete();
				}else {
					voliceUrl = service.uploadOneVolice(processId, voliceId, multipartFile, type, userId);
				}
				vo.setVoliceUrl(voliceUrl);
				vo.setTimes(times);
				
				botSentenceProcessService.updateProcessState(processId, userId);
				
				return ServerResult.createBySuccess(vo);
			}else {
				return ServerResult.createByErrorMessage("请求参数不完整!");
			}
			
		} catch (IOException e) {
			try {
				if(null != in) {
					in.close();
				}
				if(null != os) {
					os.close();
				}
			} catch (IOException e1) {
				logger.error("关闭文件流异常", e1);
			}
			mp3File.delete();
			wavFile.delete();
			logger.error("上传录音异常", e);
		}
		return ServerResult.createByErrorMessage("上传录音异常");
	}
	
	
	@RequestMapping(value="queryVoliceListSimple")
	public ServerResult<List<VoliceInfoExt>> queryVoliceListSimple(@JsonParam String processId) {
		//List<VoliceInfoExt> list = service.queryVoliceListSimple(processId);
		List<VoliceInfoExt> list=service.queryVoliceInfoList(processId);
		return ServerResult.createBySuccess(list);
	}
	
	
	/**
	 * 查询下一页，上一页
	 * @param accountNo
	 * @return
	 */
	@RequestMapping(value="queryNextPage")
	public ServerResult<BotSentenceProcessVO> queryNextPage(@JsonParam String accountNo, @JsonParam String processId, @JsonParam String currentVoliceId, @JsonParam String type) {
		List<VoliceInfoExt> list = service.queryVoliceListSimple(processId);
		BotSentenceProcessVO results = new BotSentenceProcessVO();
		
		
		return null;
	}
	
	
	@RequestMapping(value="deleteAllVolice")
	public ServerResult<String> deleteAllVolice(@RequestParam("processId") String processId) {
		if(StringUtils.isBlank(processId)) {
			return ServerResult.createByErrorMessage("请求参数不完整!");
		}
		service.deleteAllVolice(processId);
		return ServerResult.createBySuccess();
	}
	
	
	 @PostMapping("/selftest")
	    public ServerResult<ResponseSelfTestVO> selfTest(@RequestBody SelfTestVO request, String userId){
		 logger.info("收到自测请求[{}]", request);
	        if(StringUtils.isNotBlank(request.getTempId())) {
	        	request.setTempId(request.getTempId().replace("_en", ""));
	        }else {
	        	return ServerResult.createByErrorMessage("话术模板为空!");
	        }
	        
	        if(StringUtils.isBlank(request.getProcessId())) {
	        	return ServerResult.createByErrorMessage("话术流程编号为空!");
	        }
	        ServerResult serverResult = null;
	        
	        //判断当前模板是否需要重新部署
	        BotSentenceProcess process = botSentenceProcessService.queryBotsentenceProcessInfo(request.getProcessId());
	        if(Constant.TEST_STATE_ONLINE.equals(process.getTestState())) {
	        	logger.info("当前话术模板已部署测试环境，可直接进行测试...");
	        }else {
	        	logger.info("当前话术模板做过修改，需要重新部署...");
	        	botSentenceApprovalService.deployTestSellbotByScp(request.getProcessId(), userId);
	        }
	        try{
	            ResponseSelfTestVO result = selfTestService.makeTest(request, userId);
	            logger.info("测试返回结果为[{}]", result);
	            serverResult = ServerResult.createBySuccess(result);
	        }catch (Exception ex){
	        	logger.warn("自测产生异常", ex);
	        	logger.info("释放机器人..." + request.getUuid());
	            selfTestService.endTest(request.getUuid());
	            serverResult = ServerResult.createByErrorMessage(ex.getMessage());
	        }

	        logger.info("自测返回结果[{}]", serverResult);
	        return serverResult;
	    }

	    @PostMapping("/endtest")
	    public ServerResult<String> endTest(@RequestBody SelfTestVO request){
	    	logger.info("收到关闭自测请求[{}]", request);
	        ServerResult serverResult = null;
	        try{
	            selfTestService.endTest(request.getUuid());
	            serverResult = ServerResult.createBySuccess();
	        }catch (Exception ex){
	        	logger.warn("结束自测产生异常", ex);
	            serverResult = ServerResult.createByErrorMessage(ex.getMessage());
	        }

	        logger.info("结束自测返回结果[{}]", serverResult);
	        return serverResult.createBySuccess("结束测试...");
	    }
	
	
	
}
