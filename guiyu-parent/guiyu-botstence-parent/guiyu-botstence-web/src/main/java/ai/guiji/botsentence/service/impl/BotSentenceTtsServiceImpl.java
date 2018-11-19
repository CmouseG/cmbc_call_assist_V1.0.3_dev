package ai.guiji.botsentence.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

import ai.guiji.botsentence.constant.Constant;
import ai.guiji.botsentence.dao.BotSentenceProcessMapper;
import ai.guiji.botsentence.dao.BotSentenceTtsBackupMapper;
import ai.guiji.botsentence.dao.BotSentenceTtsContentMapper;
import ai.guiji.botsentence.dao.BotSentenceTtsParamMapper;
import ai.guiji.botsentence.dao.BotSentenceTtsTaskMapper;
import ai.guiji.botsentence.dao.VoliceInfoMapper;
import ai.guiji.botsentence.dao.entity.BotSentenceProcess;
import ai.guiji.botsentence.dao.entity.BotSentenceTtsBackup;
import ai.guiji.botsentence.dao.entity.BotSentenceTtsBackupExample;
import ai.guiji.botsentence.dao.entity.BotSentenceTtsParam;
import ai.guiji.botsentence.dao.entity.BotSentenceTtsParamExample;
import ai.guiji.botsentence.dao.entity.BotSentenceTtsTask;
import ai.guiji.botsentence.dao.entity.BotSentenceTtsTaskExample;
import ai.guiji.botsentence.dao.entity.VoliceInfo;
import ai.guiji.botsentence.dao.entity.VoliceInfoExample;
import ai.guiji.botsentence.service.IBotSentenceTtsService;
import ai.guiji.botsentence.util.HttpRequestUtils;
import ai.guiji.botsentence.vo.RequestTtsVO;
import ai.guiji.botsentence.vo.ResponseTtsVO;
import ai.guiji.botsentence.vo.TtsBackup;
import ai.guiji.botsentence.vo.TtsBackupVO;
import ai.guiji.botsentence.vo.TtsParam;
import ai.guiji.botsentence.vo.TtsParamVO;
import ai.guiji.component.client.util.BeanUtil;
import ai.guiji.component.client.util.FileUtil;
import ai.guiji.component.client.util.SFTPUtil;
import ai.guiji.component.exception.CommonException;

/**
 * tts合成相关服务
 * @author zhangpeng
 *
 */
@Service
public class BotSentenceTtsServiceImpl implements IBotSentenceTtsService {

	private Logger logger = LoggerFactory.getLogger(BotSentenceTtsServiceImpl.class);
	
	@Autowired
	private BotSentenceTtsParamMapper botSentenceTtsParamMapper;
	
	@Autowired
	private VoliceInfoMapper voliceInfoMapper;
	
	@Autowired
	private BotSentenceTtsBackupMapper botSentenceTtsBackupMapper;
	
	@Autowired
	private BotSentenceTtsTaskMapper botSentenceTtsTaskMapper;
	
	@Autowired
	private BotSentenceTtsContentMapper botSentenceTtsContentMapper;
	
	@Autowired
	private BotSentenceProcessServiceImpl botSentenceProcessServiceImpl;
	
	@Autowired
	private BotSentenceProcessMapper botSentenceProcessMapper;
	
	@Value("${tts.api.url}")
	private String ttsApiUrl;
	
	/**
	 * 保存TTS变量及变量类型
	 */
	@Override
	@Transactional
	public void saveTtsParam(TtsParamVO param,Long userId) {
		if(null != param && null != param.getParams() && param.getParams().size() > 0 && StringUtils.isNotBlank(param.getProcessId())) {
			
			BotSentenceTtsParamExample example = new BotSentenceTtsParamExample();
			example.createCriteria().andProcessIdEqualTo(param.getProcessId());
			botSentenceTtsParamMapper.deleteByExample(example);
			
			for(TtsParam temp : param.getParams()) {
				BotSentenceTtsParam ttsParam = new BotSentenceTtsParam();
				ttsParam.setProcessId(param.getProcessId());
				ttsParam.setTemplateId(param.getTemplateId());
				
				ttsParam.setParamKey(temp.getParamKey());
				ttsParam.setParamType(temp.getParamType());
				
				ttsParam.setCrtTime(new Date(System.currentTimeMillis()));
				ttsParam.setCrtUser(userId.toString());
				
				botSentenceTtsParamMapper.insert(ttsParam);
			}
		}
		botSentenceProcessServiceImpl.updateProcessState(param.getProcessId(),userId);
	}

	/**
	 * 保存TTS话术的备份话术
	 */
	@Override
	@Transactional
	public void saveTtsBackup(TtsBackupVO param,Long userId) {
		if(null != param && null != param.getBackups() && param.getBackups().size() > 0 && StringUtils.isNotBlank(param.getProcessId())) {
			//先删除当前话术的备用话术
			BotSentenceTtsBackupExample example = new BotSentenceTtsBackupExample();
			example.createCriteria().andProcessIdEqualTo(param.getProcessId());
			botSentenceTtsBackupMapper.deleteByExample(example);
			
			for(TtsBackup temp : param.getBackups()) {
				//校验备用话术里面是否包含变量
				if(StringUtils.isNotBlank(temp.getBackup())){
					if(this.validateContainParam(temp.getBackup())) {
						throw new CommonException("备用话术不允许存在变量");
					}
					BotSentenceTtsBackup backup = new BotSentenceTtsBackup();
					backup.setProcessId(param.getProcessId());
					backup.setTemplateId(param.getTemplateId());
					
					backup.setContent(temp.getBackup());
					backup.setVoliceId(new Long(temp.getVoliceId()));
					
					backup.setCrtTime(new Date(System.currentTimeMillis()));
					backup.setCrtUser(userId.toString());
					
					botSentenceTtsBackupMapper.insert(backup);
				}
			}
			botSentenceProcessServiceImpl.updateProcessState(param.getProcessId(),userId);
		}
		
		
		
		
	}

	/**
	 * 查询TTS参数列表
	 */
	@Override
	public List<TtsParam> queryTtsParamList(String processId) {
		List<String> paramKeys = new ArrayList<>();
		List<TtsParam> results = new ArrayList<>();
		
		BotSentenceProcess process = botSentenceProcessMapper.selectByPrimaryKey(processId);
		
		BotSentenceTtsTaskExample ttsExample = new BotSentenceTtsTaskExample();
		ttsExample.createCriteria().andProcessIdEqualTo(processId).andIsParamEqualTo(Constant.IS_PARAM_TRUE);
		
		List<BotSentenceTtsTask> list =  botSentenceTtsTaskMapper.selectByExample(ttsExample);
		
		if(null != list && list.size() > 0) {
			for(BotSentenceTtsTask task : list) {
				String paramKey = task.getContent();
				if(!paramKeys.contains(paramKey)) {
					paramKeys.add(paramKey);
				}
			}
		}
		
		Collections.sort(paramKeys);
		
		for(String key : paramKeys) {
			TtsParam param = new TtsParam();
			param.setParamKey(key);
			
			BotSentenceTtsParamExample paramExample = new BotSentenceTtsParamExample();
			paramExample.createCriteria().andProcessIdEqualTo(processId).andParamKeyEqualTo(key);
			List<BotSentenceTtsParam> paramList = botSentenceTtsParamMapper.selectByExample(paramExample);
			if(null != paramList && paramList.size() > 0) {
				if(StringUtils.isNotBlank(paramList.get(0).getParamType())) {
					param.setParamType(paramList.get(0).getParamType());
				}else {
					param.setParamType("normal");
				}
				
			}else {
				param.setParamType("normal");
			}
			param.setSoundType(process.getSoundType());
			results.add(param);
		}
		
		return results;
	}

	/**
	 * 查询备用话术列表
	 */
	@Override
	public List<TtsBackup> queryTtsBackupList(String processId) {
		List<TtsBackup> results = new ArrayList<>();
		
		VoliceInfoExample example = new VoliceInfoExample();
		example.createCriteria().andProcessIdEqualTo(processId);
		List<VoliceInfo> list = voliceInfoMapper.selectByExample(example);
		if(null != list && list.size() > 0) {
			for(VoliceInfo volice : list) {
				if(StringUtils.isNotBlank(volice.getContent())) {
					String content = volice.getContent();
					if(validateContainParam(content)) {
						TtsBackup backup = new TtsBackup();
						backup.setContent(volice.getContent());
						
						//根据voliceId查询用话术
						BotSentenceTtsBackupExample backupExample = new BotSentenceTtsBackupExample();
						backupExample.createCriteria().andProcessIdEqualTo(processId).andVoliceIdEqualTo(volice.getVoliceId());
						List<BotSentenceTtsBackup> backupList = botSentenceTtsBackupMapper.selectByExample(backupExample);
						if(null != backupList && backupList.size() > 0) {
							backup.setBackup(backupList.get(0).getContent());
							backup.setUrl(backupList.get(0).getUrl());
						}
						backup.setProcessId(volice.getProcessId());
						backup.setTemplateId(volice.getTemplateId());
						backup.setVoliceId(volice.getVoliceId().toString());
						results.add(backup);
					}
				}
			}
		}
		
		return results;
	}
	
	
	
	/**
	 * 校验是否包含变量，例如：$1000
	 * @param str
	 * @return
	 */
	public boolean validateContainParam (String str) {
		 String regEx = Constant.TTS_REG_EX;
	    // 编译正则表达式
	    Pattern pattern = Pattern.compile(regEx);
	    Matcher matcher = pattern.matcher(str);
	    // 字符串是否与正则表达式相匹配
	    boolean rs = matcher.find();
	    return rs;
	}

	public static void main(String[] args) {
		String str = "$0001我们每个项目$0000都会分配至少$3333的团队。";
		 String regEx = "\\$[0-9]{4}";
		    // 编译正则表达式
		    Pattern pattern = Pattern.compile(regEx);
		    Matcher matcher = pattern.matcher(str);
		    // 字符串是否与正则表达式相匹配
		   // boolean rs = matcher.find();
		    
		    System.out.println(str.indexOf("$3333"));
		    
		    while(matcher.find()) {
		    	 String match = matcher.group();
				    System.out.println(match);
		    }
		   
		    
		   String [] array =  str.split(regEx);
		   if(null != array && array.length > 0) {
			  for(int i = 0 ; i < array.length ; i++) {
				  System.out.println(array[i]);
			  }
		   }
		   
	}
	

	@Transactional
	public void saveTTSTask(BotSentenceTtsTask temp, String processId,Long userId) {
		//保存本地生成TTS任务
		Long taskId = null;
		BotSentenceTtsTask ttsTask = new BotSentenceTtsTask();
		
		//查询当前文案内容是否已经合成过，如果合成过，则不再重新合成
		/*BotSentenceTtsContentExample example = new BotSentenceTtsContentExample();
		example.createCriteria().andUrlIsNotNull().andContentEqualTo(temp.getContent().trim()).andSoundTypeEqualTo(temp.getSoundType());
		List<BotSentenceTtsContent> contentList = botSentenceTtsContentMapper.selectByExample(example);
		if(null != contentList && contentList.size() > 0) {
			logger.info("当前录音已合成过，可直接使用该url,并直接设置任务状态为已完成....");
			ttsTask.setStatus(Constant.TTS_TASK_FINISH);
			ttsTask.setVoliceUrl(contentList.get(0).getUrl());
		}else {
			ttsTask.setStatus(Constant.TTS_TASK_NEW);
		}*/
		ttsTask.setContent(temp.getContent().trim());
		ttsTask.setProcessId(processId);
		ttsTask.setBusiId(temp.getBusiId());
		ttsTask.setBusiType(temp.getBusiType());
		ttsTask.setCrtTime(new Date(System.currentTimeMillis()));
		ttsTask.setCrtUser(userId.toString());
		ttsTask.setSeq(temp.getSeq());
		ttsTask.setIsParam(temp.getIsParam());
		botSentenceTtsTaskMapper.insert(ttsTask);
		taskId = ttsTask.getId();
		logger.info("保存tts合成任务表结束..."+ taskId);
		
	}
	
	
	@Transactional
	@Override
	public void generateTTS(String processId,Long userId) {
		logger.info("发送tts任务");
		BotSentenceTtsTaskExample example = new BotSentenceTtsTaskExample();
		example.createCriteria().andStatusEqualTo(Constant.TTS_TASK_NEW).andProcessIdEqualTo(processId);
		List<BotSentenceTtsTask> taskList = botSentenceTtsTaskMapper.selectByExample(example);
		if(null != taskList && taskList.size() > 0) {
			for(BotSentenceTtsTask task : taskList) {
				//发送tts任务
				logger.info("发送tts任务" + task.getId());
				RequestTtsVO req = new RequestTtsVO();
			    req.setTask_id(task.getId().toString());
			    req.setSentence(task.getContent());
			    logger.info("请求参数: " + req.toString());
				String jsonResult;
				try {
					jsonResult = HttpRequestUtils.httpPost(ttsApiUrl, BeanUtil.bean2Map(req));
					logger.info("返回参数: " + jsonResult);
					Gson gson = new Gson();
					ResponseTtsVO rsp = gson.fromJson(jsonResult, ResponseTtsVO.class);
					if(null != rsp && "received".equals(rsp.getStatus())) {
						logger.info("推送tts数据成功...");
					}else {
						logger.error("推送tts数据异常，请联系管理员!");
						throw new CommonException("推送tts数据异常，请联系管理员!");
					}
					
				} catch (UnsupportedEncodingException e) {
					logger.error("推送tts数据异常...", e);
					throw new CommonException("推送tts数据异常，请联系管理员!");
				}
				
				task.setStatus(Constant.TTS_TASK_ING);
				task.setLstUpdateTime(new Date(System.currentTimeMillis()));
				task.setLstUpdateUser(userId.toString());
				botSentenceTtsTaskMapper.updateByPrimaryKey(task);
			}
		}
		logger.info("发送tts任务结束...");
	}
	
	public boolean uploadTtsReplaceJson(File file, String templateId) throws JSchException, IOException, SftpException {
		//上传replace.json到tts服务器
	    
		if(null != file) {
			SFTPUtil sftpUtil = new SFTPUtil("root", "50xfswsscqdb!","47.96.79.114", 22);
		    sftpUtil.login();
		    ChannelSftp sftp = sftpUtil.getSftp();
		    String dirPath =  "/root/cfgs" + "/" + templateId.replace("_en", "");
		    
		    boolean flag = false;
		    try {
		    	flag = sftpUtil.existFile(dirPath);
		    }catch(SftpException e) {
		    	flag = false;
		    }
		    
		    if(!flag) {
		    	sftp.mkdir(dirPath);
		    }
		    
		    List<File> listFile = new ArrayList<File>();
			FileUtil.getAllFilePaths(file, listFile);
			
			sftpUtil.upload(dirPath, "replace.json", new FileInputStream(file));
			
			sftpUtil.logout();
			return true;
		}
		return false;
		
	}

	@Override
	public boolean validateProcessHasTTs(String processId) {
		BotSentenceTtsTaskExample example = new BotSentenceTtsTaskExample();
		
		return false;
	}
}
