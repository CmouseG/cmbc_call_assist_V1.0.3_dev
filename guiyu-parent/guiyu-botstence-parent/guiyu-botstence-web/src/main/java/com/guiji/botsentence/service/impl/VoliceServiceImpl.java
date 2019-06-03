package com.guiji.botsentence.service.impl;

import com.google.common.collect.Lists;
import com.guiji.botsentence.constant.Constant;
import com.guiji.botsentence.dao.*;
import com.guiji.botsentence.dao.entity.*;
import com.guiji.botsentence.dao.ext.VoliceInfoExtMapper;
import com.guiji.botsentence.service.ITtsService;
import com.guiji.botsentence.service.IVoliceService;
import com.guiji.botsentence.util.BotSentenceUtil;
import com.guiji.botsentence.util.enums.TtsParamTypeEnum;
import com.guiji.botsentence.util.enums.TtsTaskParamEnum;
import com.guiji.botsentence.util.enums.TtsTaskTypeEnum;
import com.guiji.botsentence.vo.CommonDialogVO;
import com.guiji.botsentence.vo.RefuseVoliceVO;
import com.guiji.botsentence.vo.TtsBackup;
import com.guiji.common.exception.CommonException;
import com.guiji.common.model.SysFileReqVO;
import com.guiji.common.model.SysFileRspVO;
import com.guiji.common.model.process.ProcessTypeEnum;
import com.guiji.component.client.util.DateUtil;
import com.guiji.component.client.util.FileUtil;
import com.guiji.component.client.util.IOUtil;
import com.guiji.component.client.util.JsonBase64Crypter;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.dispatch.api.IDispatchPlanOut;
import com.guiji.process.api.IProcessSchedule;
import com.guiji.process.model.PublishBotstenceTaskVO;
import com.guiji.process.model.UpgrateResouceReq;
import com.guiji.utils.NasUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

@Service
public class VoliceServiceImpl implements IVoliceService {

	Logger logger = LoggerFactory.getLogger(VoliceServiceImpl.class);

	@Resource
	private VoliceInfoMapper voliceInfoMapper;

	@Resource
	private VoliceInfoExtMapper voliceInfoExtMapper;

	@Resource
	private BotSentenceProcessMapper botSentenceProcessMapper;

	@Resource
	private BotSentenceDomainMapper botSentenceDomainMapper;

	@Resource
	private BotSentenceBranchMapper botSentenceBranchMapper;

	@Resource
	private BotSentenceProcessServiceImpl botSentenceProcessService;

	@Resource
	private BusinessAnswerTaskServiceImpl businessAnswerTaskService;

	@Resource
	private BotSentenceTtsServiceImpl botSentenceTtsService;

	@Resource
	private BotSentenceTtsTaskMapper botSentenceTtsTaskMapper;
	
	@Resource
	private BotSentenceTtsBackupMapper botSentenceTtsBackupMapper;
	
	@Resource
	private BotsentenceVariableServiceImpl botsentenceVariableService;
	
	@Resource
	private IProcessSchedule iProcessSchedule;
	
	@Resource
	private IDispatchPlanOut iDispatchPlanOut;
	
	@Resource
	private BotSentenceApprovalServiceImpl botSentenceApprovalService;
	
	@Resource
	private VoliceServiceImpl voliceService;

	@Resource
	private ITtsService iTtsService;
	
	private static String NAS_UPLAOD_SYSTEM_CODE="09";
	
	private NasUtil nasUtile=new NasUtil();

	private static String FILE_SEPARATOR = System.getProperty("file.separator");

	// 服务器路径
	@Value("${sftp.path}")
	private String ftpPath;

	@Value("${local.upload.dir}")
	private String localUploadDir;
	
	@Value("${check.agent.dir}")
	private String agentDir;
	
	@Value("${upload.cfgs.dir}")
	private String uploadCfgDir;
	
	@Value("${create.file.tmp}")
	private String tempPath;
	
	@Value("${local.wav.dir}")
	private String localWavDir;
	
	@Value("${local.wav.dir.pre}")
	private String localWavDirPre;
	
	@Override
	@Transactional
	public long saveVoliceInfo(VoliceInfo voliceInfo, String userId) {
		if (null == voliceInfo) {
			throw new CommonException("参数为空!");
		}

		if (StringUtils.isBlank(voliceInfo.getContent())) {
			throw new CommonException("文案内容为空!");
		}

		if (StringUtils.isBlank(voliceInfo.getProcessId())) {
			throw new CommonException("话术流程编号为空");
		}

		if (StringUtils.isBlank(voliceInfo.getTemplateId())) {
			throw new CommonException("话术模板编号为空");
		}

		String oldContent = null;
		String newContent = voliceInfo.getContent();
		
		if (null != voliceInfo.getVoliceId()) {
			logger.info("更新一条录音信息" + voliceInfo.getVoliceId());

			oldContent = voliceInfoExtMapper.getContentByVoliceId(voliceInfo.getVoliceId().toString());
			voliceInfo.setNeedTts(false);

			if (BotSentenceUtil.validateContainParam(newContent)) {// 新的文案是否包含TTS变量，如果包含
				if (!newContent.equals(oldContent)) {
					logger.info("先删除原先文案的TTS合成任务记录...");
					BotSentenceTtsTaskExample ttsTaskExample = new BotSentenceTtsTaskExample();
					ttsTaskExample.createCriteria()
							.andBusiTypeEqualTo(TtsTaskTypeEnum.CALL_RECORD.getKey())
							.andBusiIdEqualTo(voliceInfo.getVoliceId().toString());
					botSentenceTtsTaskMapper.deleteByExample(ttsTaskExample);

					logger.info("更新当前录音的url为空");
					voliceInfo.setVoliceUrl(null);

					// 包含TTS变量，则需要保存tts任务表数据
					logger.info("当前文案需要TTS合成，需要拆分文案生成多个TTS任务");

					splitContentAndSave(voliceInfo, newContent, userId);

					logger.info("保存tts合成任务表结束...");
				}
				voliceInfo.setNeedTts(true);
			} else {// 如果不包含，则删除原来TTS合成任务
				BotSentenceTtsTaskExample ttsTaskExample = new BotSentenceTtsTaskExample();
				ttsTaskExample.createCriteria().andBusiTypeEqualTo(Constant.TTS_BUSI_TYPE_01)
						.andBusiIdEqualTo(voliceInfo.getVoliceId().toString());
				botSentenceTtsTaskMapper.deleteByExample(ttsTaskExample);
				
				//删除备用话术
				BotSentenceTtsBackupExample backExample = new BotSentenceTtsBackupExample();
				backExample.createCriteria().andProcessIdEqualTo(voliceInfo.getProcessId()).andVoliceIdEqualTo(voliceInfo.getVoliceId());
				botSentenceTtsBackupMapper.deleteByExample(backExample);
				
				//判断新的文案是否和之前一样，如果不一样，则设置录音url为空
				if(!oldContent.equals(voliceInfo.getContent())) {
					logger.info("修改了文案，则更新当前录音的url为空");
					voliceInfo.setVoliceUrl(null);
				}
			}
			voliceInfo.setLstUpdateTime(new Date(System.currentTimeMillis()));
			voliceInfo.setLstUpdateUser(userId);
			voliceInfoMapper.updateByPrimaryKey(voliceInfo);

		} else {
			logger.info("新增一条录音信息");
			voliceInfo.setCrtTime(new Date(System.currentTimeMillis()));
			voliceInfo.setCrtUser(userId);
			voliceInfo.setNeedTts(BotSentenceUtil.validateContainParam(newContent));
			voliceInfoMapper.insert(voliceInfo);
			
			if (voliceInfo.getNeedTts()) {// 包含TTS变量，则需要保存tts任务表数据
				logger.info("当前文案需要TTS合成，需要拆分文案生成多个TTS任务");
				logger.info("更新当前录音的url为空");
				
				splitContentAndSave(voliceInfo, newContent, userId);
				
				logger.info("保存tts合成任务表结束...");
			}
		}

		return voliceInfo.getVoliceId();
	}

	@Override
	public VoliceInfo getVoliceInfo(long voliceId) {
		return voliceInfoMapper.selectByPrimaryKey(voliceId);
	}

	@Override
	public void updateVoliceInfo(long voliceId, String type, String voliceUrl, int times, String userId) {
		logger.info("voliceId: " + voliceId);
		VoliceInfo volice = voliceInfoMapper.selectByPrimaryKey(voliceId);
		if (StringUtils.isNotBlank(voliceUrl)) {
			volice.setVoliceUrl(voliceUrl);
			volice.setTimes(times);
		}
		if (!"【新增】".equals(volice.getFlag())) {
			volice.setFlag("【修改】");
		}
		// 获取当前用户
		volice.setLstUpdateTime(new Date(System.currentTimeMillis()));
		volice.setLstUpdateUser(userId);

		voliceInfoMapper.updateByPrimaryKey(volice);

		botSentenceProcessService.updateProcessState(volice.getProcessId(), userId);
	}
	
	private void updateVoliceInfoOffline(String voliceId, String type, String voliceUrl, int times, String userId) {
		logger.info("voliceId: " + voliceId);
		String voliceId2 = voliceId.split("_")[0];
		VoliceInfo volice = this.getVoliceInfo(new Long(voliceId2));
		
		if(StringUtils.isNotBlank(voliceUrl)) {
			if(voliceUrl.contains(localWavDirPre)) {
				voliceUrl = voliceUrl.substring(localWavDirPre.length(), voliceUrl.length());
			}
		}
		
		if (Constant.VOLICE_TYPE_TTS.equals(type)) {
			// 更新tts任务表的url
			BotSentenceTtsTaskExample ttsTaskExample = new BotSentenceTtsTaskExample();
			ttsTaskExample.createCriteria().andProcessIdEqualTo(volice.getProcessId()).andBusiIdEqualTo(voliceId2)
					.andSeqEqualTo(voliceId);
			List<BotSentenceTtsTask> list = botSentenceTtsTaskMapper.selectByExample(ttsTaskExample);
			if (null != list && list.size() > 0) {
				BotSentenceTtsTask task = list.get(0);
				task.setVoliceUrl(voliceUrl);
				
				//设置音频时长
				task.setTimes(times);
				task.setLstUpdateTime(new Date(System.currentTimeMillis()));
				task.setLstUpdateUser(userId);
				botSentenceTtsTaskMapper.updateByPrimaryKey(task);
			}
		}else if(Constant.VOLICE_TYPE_BACKUP.equals(type)){
			//更新备用话术表URL
			//查询当前文案的备用文案
			BotSentenceTtsBackupExample backupExample = new BotSentenceTtsBackupExample();
			backupExample.createCriteria().andProcessIdEqualTo(volice.getProcessId()).andVoliceIdEqualTo(new Long(voliceId2));
			List<BotSentenceTtsBackup> backupList = botSentenceTtsBackupMapper.selectByExample(backupExample);
			if(null != backupList && backupList.size() > 0) {
				BotSentenceTtsBackup backup = backupList.get(0);
				backup.setUrl(voliceUrl);
				backup.setTimes(times);
				backup.setLstUpdateTime(new Date(System.currentTimeMillis()));
				backup.setLstUpdateUser(userId);
				botSentenceTtsBackupMapper.updateByPrimaryKey(backup);
			}
		}else {
			if(StringUtils.isNotBlank(voliceUrl)) {
				/*if(voliceUrl.contains(localWavDirPre)) {
					voliceUrl = voliceUrl.substring(localWavDirPre.length(), voliceUrl.length());
				}*/
				volice.setVoliceUrl(voliceUrl);
			}
			if(!"【新增】".equals(volice.getFlag())) {
				volice.setFlag("【修改】");
			}
			//获取当前用户
			volice.setLstUpdateTime(new Date(System.currentTimeMillis()));
			volice.setLstUpdateUser(userId);
			voliceInfoMapper.updateByPrimaryKey(volice);
		}
		botSentenceProcessService.updateProcessState(volice.getProcessId(), userId);
	}

	public List<VoliceInfoExt> queryVoliceInfoList(String processId) {
		List<VoliceInfoExt> voliceInfo_list = new ArrayList<>();
		List<String> voliceid_list = new ArrayList<>();
		// 查询主流程
		BotSentenceDomainExample domainExample = new BotSentenceDomainExample();
		domainExample.createCriteria().andProcessIdEqualTo(processId).andCategoryEqualTo("1");
		// domainExample.setOrderByClause(" level");
		List<BotSentenceDomain> domainList = botSentenceDomainMapper.selectByExample(domainExample);
		List<String> domainNameList = new ArrayList<>();
		if (null != domainList && domainList.size() > 0) {
			for (BotSentenceDomain temp : domainList) {
				// 查询当前domain的branch
				BotSentenceBranchExample branchExample = new BotSentenceBranchExample();
				branchExample.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo(temp.getDomainName())
						.andResponseIsNotNull().andResponseNotEqualTo("[]").andBranchNameEqualTo("enter_branch");
				List<BotSentenceBranch> branch_list = botSentenceBranchMapper.selectByExample(branchExample);
				if (null != branch_list && branch_list.size() > 0) {// 如果有多条，则取第一条，正常情况一个domain只有一条enter_branch数据
					BotSentenceBranch branch = branch_list.get(0);
					// for(BotSentenceBranch branch : branch_list) {
					String resp = branch.getResponse();
					String[] respArray = resp.substring(1, resp.length() - 1).split(",");
					// for(int i = 0 ; i < respArray.length ; i++) {
					if (null != respArray && respArray.length > 0) {// 如果当前Branch有多个Resp，则取第一条
						if (StringUtils.isNotBlank(respArray[0]) && !voliceid_list.contains(respArray[0])) {
							voliceid_list.add(respArray[0]);
							List<VoliceInfoExt> list = getVoliceInfoExtList(processId, new Long(respArray[0]),
									temp.getDomainName());
							voliceInfo_list.addAll(list);
						}
					}
				}

				domainNameList.add(temp.getDomainName());

				/*if (Constant.DOMAIN_TYPE_START.equals(temp.getType())) {// 如果是开场白，则添加解释开场白
					BotSentenceBranchExample startExplainBranchExample = new BotSentenceBranchExample();
					startExplainBranchExample.createCriteria().andProcessIdEqualTo(processId).andDomainEqualTo("解释开场白")
							.andResponseIsNotNull().andResponseNotEqualTo("[]").andBranchNameEqualTo("enter_branch");
					List<BotSentenceBranch> startExplainBranchLlist = botSentenceBranchMapper
							.selectByExample(startExplainBranchExample);
					if (null != startExplainBranchLlist && startExplainBranchLlist.size() > 0) {// 如果有多条，则取第一条，正常情况一个domain只有一条enter_branch数据
						BotSentenceBranch branch = startExplainBranchLlist.get(0);
						String resp = branch.getResponse();
						String[] respArray = resp.substring(1, resp.length() - 1).split(",");
						if (null != respArray && respArray.length > 0) {// 如果当前Branch有多个Resp，则取第一条
							if (StringUtils.isNotBlank(respArray[0]) && !voliceid_list.contains(respArray[0])) {
								voliceid_list.add(respArray[0]);
								List<VoliceInfoExt> list = getVoliceInfoExtList(processId, new Long(respArray[0]),
										"解释开场白");
								voliceInfo_list.addAll(list);
							}
						}
					}

				}*/
			}
		}

		// 查询挽回话术池
		VoliceInfoExample example = new VoliceInfoExample();
		example.createCriteria().andProcessIdEqualTo(processId).andTypeEqualTo(Constant.VOLICE_TYPE_REFUSE);
		example.setOrderByClause("volice_id");
		List<VoliceInfo> refuseVoliceList = voliceInfoMapper.selectByExample(example);
		if (null != refuseVoliceList && refuseVoliceList.size() > 0) {
			for (VoliceInfo volice : refuseVoliceList) {
				if (!voliceid_list.contains(volice.getVoliceId().toString())) {
					voliceid_list.add(volice.getVoliceId().toString());

					List<VoliceInfoExt> list = getVoliceInfoExtList(processId, volice.getVoliceId(), volice.getName());
					voliceInfo_list.addAll(list);
				}
			}
		}

		// 查询一般问题
		List<BusinessAnswerTaskExt> answerQAs = businessAnswerTaskService.queryBusinessAnswerListByPage(processId);
		if(!CollectionUtils.isEmpty(answerQAs)){
			answerQAs.forEach(answerQA ->{
				List<VoliceInfo> voiceInfoList = answerQA.getVoiceInfoList();
				if(CollectionUtils.isEmpty(voiceInfoList)){
					return;
				}

				voiceInfoList.forEach(voiceInfo -> {
					if(voliceid_list.contains(String.valueOf(voiceInfo.getVoliceId()))){
						return;
					}
					List<VoliceInfoExt> voiceInfoExtList = getVoliceInfoExtList(processId, voiceInfo.getVoliceId(), "业务问答" + answerQA.getIndex());
					voliceid_list.add(String.valueOf(voiceInfo.getVoliceId()));
					voliceInfo_list.addAll(voiceInfoExtList);
				});
			});
		}

		// 查询通用对话
		List<CommonDialogVO> commonDialog_list = botSentenceProcessService.queryCommonDialog(processId);
		if (null != commonDialog_list && commonDialog_list.size() > 0) {
			for (CommonDialogVO commonDialog : commonDialog_list) {
				if("拒绝".equals(commonDialog.getYujin())) {
					List<VoliceInfo> refuseList = commonDialog.getRefuseList();
					if(null != refuseList && refuseList.size() > 0) {
						for(VoliceInfo temp : refuseList) {
							List<VoliceInfoExt> extlist = getVoliceInfoExtList(processId, temp.getVoliceId(),
									commonDialog.getTitle());
							voliceInfo_list.addAll(extlist);
						}
					}
					
				}else {
					if (!"重复".equals(commonDialog.getYujin()) && commonDialog.getVoliceId() > 0) {
						String voliceId = commonDialog.getVoliceId() + "";
						if (StringUtils.isNotBlank(voliceId) && !voliceid_list.contains(voliceId)) {
							voliceid_list.add(voliceId);

							List<VoliceInfoExt> extlist = getVoliceInfoExtList(processId, new Long(voliceId),
									commonDialog.getTitle());
							voliceInfo_list.addAll(extlist);
						}
					}
				}
			}
		}

		// 查询备用话术
		List<TtsBackup> backupList = botSentenceTtsService.queryTtsBackupList(processId);
		if (null != backupList && backupList.size() > 0) {
			int index = 1;
			for (TtsBackup backup : backupList) {
				if(StringUtils.isNotBlank(backup.getBackup())) {
					VoliceInfoExt vo = new VoliceInfoExt();
					vo.setType(Constant.VOLICE_TYPE_BACKUP);
					vo.setProcessId(processId);
					vo.setVoliceId(backup.getVoliceId() + "_0");
					vo.setTitle("备用话术" + index);
					vo.setContent(backup.getBackup());
					vo.setVoliceUrl(backup.getUrl());
					if (StringUtils.isNotBlank(backup.getUrl())) {
						vo.setHasVolice("是");
					} else {
						vo.setHasVolice("否");
					}
					
					if(null != backup.getTimes()) {
						vo.setTimes(backup.getTimes());
					}else {
						if(StringUtils.isNotBlank(backup.getBackup())) {
							vo.setTimes(backup.getBackup().length()/6);
							if(vo.getTimes() == 0) {
								vo.setTimes(1);
							}
						}else {
							vo.setTimes(0);
						}
					}
					
					
					voliceInfo_list.add(vo);

					index++;
				}
			}
		}
		
		//查询打断规则文案
		BotSentenceOptions botSentenceOptions = botsentenceVariableService.getOptionsByProcessId(processId);
		if(null != botSentenceOptions && null != botSentenceOptions.getInterruptionConfigStart() &&
				botSentenceOptions.getInterruptionConfigStart() && StringUtils.isNotBlank(botSentenceOptions.getVoice())) {
			VoliceInfo volice = this.getVoliceInfo(new Long(botSentenceOptions.getVoice()));
			VoliceInfoExt vo = new VoliceInfoExt();
			vo.setProcessId(processId);
			vo.setVoliceId(botSentenceOptions.getVoice());
			vo.setTitle("打断规则");
			vo.setContent(volice.getContent());
			vo.setFlag(volice.getFlag());
			vo.setVoliceUrl(volice.getVoliceUrl());
			if (StringUtils.isNotBlank(volice.getVoliceUrl())) {
				vo.setHasVolice("是");
			} else {
				vo.setHasVolice("否");
			}
			
			if(null != volice.getTimes()) {
				vo.setTimes(volice.getTimes());
			}else {
				if(StringUtils.isNotBlank(volice.getContent())) {
					vo.setTimes(volice.getContent().length()/6);
					if(vo.getTimes() == 0) {
						vo.setTimes(1);
					}
				}else {
					vo.setTimes(0);
				}
			}
			
			voliceInfo_list.add(vo);
		}
		
		
		// 查询静音
		VoliceInfoExample silenceVoliceExample = new VoliceInfoExample();
		silenceVoliceExample.createCriteria().andProcessIdEqualTo(processId).andDomainNameEqualTo("静音");
		List<VoliceInfo> silenceVoliceList = voliceInfoMapper.selectByExample(silenceVoliceExample);
		if (null != silenceVoliceList && silenceVoliceList.size() > 0) {
			int index = 1;
			for (VoliceInfo voliceInfo : silenceVoliceList) {
				VoliceInfoExt vo = new VoliceInfoExt();
				vo.setProcessId(processId);
				vo.setVoliceId(voliceInfo.getVoliceId().toString());
				vo.setTitle("静音" + index);
				vo.setContent(voliceInfo.getContent());
				vo.setVoliceUrl(voliceInfo.getVoliceUrl());
				if (StringUtils.isNotBlank(voliceInfo.getVoliceUrl())) {
					vo.setHasVolice("是");
				} else {
					vo.setHasVolice("否");
				}
				
				if(null != voliceInfo.getTimes()) {
					vo.setTimes(voliceInfo.getTimes());
				}else {
					if(StringUtils.isNotBlank(voliceInfo.getContent())) {
						vo.setTimes(voliceInfo.getContent().length()/6);
						if(vo.getTimes() == 0) {
							vo.setTimes(1);
						}
					}else {
						vo.setTimes(0);
					}
				}
				
				voliceInfo_list.add(vo);
				index++;
			}
		}
		

		for (VoliceInfoExt temp : voliceInfo_list) {
			if (StringUtils.isNotBlank(temp.getContent()) && BotSentenceUtil.validateContainParam(temp.getContent())
					&& StringUtils.isBlank(temp.getType())) {
				temp.setType(Constant.VOLICE_TYPE_TTS);
			}
		}

		return voliceInfo_list;
	}

	@Override
	public List<VoliceInfoExt> queryVoliceInfoListByIds(String processId, String[] voliceIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("processId", processId);
		map.put("voliceIds", voliceIds);
		return voliceInfoExtMapper.queryVoliceInfoListByIds(map);
	}

	public List<String> uploadVoliceZip(String processId, InputStream inStream, String userId) {
		List<String> voliceIds = voliceInfoExtMapper.queryAllVoliceId(processId);
		List<String> successUpdate = new ArrayList<String>();

		int successNum = 0;
		List<String> formatFileList = new ArrayList<>();
		List<String> sizeFileList = new ArrayList<>();
		List<String> notmatchFileList = new ArrayList<>();
		List<String> successFileList = new ArrayList<>();

		try {
			File dir = new File(tempPath);
			File file = File.createTempFile(String.valueOf(System.currentTimeMillis()), "zip", dir);
			writeFile(inStream, file);
			ZipFile zipFile = new ZipFile(file, Charset.forName("gbk"));

			File descFile = new File(UUID.randomUUID().toString());
			descFile.mkdirs();

			unZipFiles(zipFile, descFile.getPath());

			List<File> listFile = new ArrayList<File>();
			FileUtil.getAllFilePaths(descFile, listFile);

			if (null != listFile && listFile.size() > 0) {
				for (int i = 0; i < listFile.size(); i++) {
					File temp = listFile.get(i);
					if (!temp.isDirectory()) {
						String name = temp.getName();

						boolean needTts = false;

						String[] arrays = name.split("\\.");
						String voliceId = arrays[0];
						String voiceIdWithIndex = null;
						Integer index = null;
						if (voliceId.indexOf("_") > 0) {// 表示是需要TTS合成的话术
							voiceIdWithIndex = voliceId;
							voliceId = voliceId.split("_")[0];
							index = new Integer(arrays[0].split("_")[1]);
							needTts = true;
						}

						String suffix = name.substring(name.lastIndexOf(".") + 1);

						if (!"wav".equals(suffix)) {
							formatFileList.add(name);
							//entryInStream.close();
							continue;
						}

						long size = temp.length();
						if (size > 1 * 1024 * 1024) {
							sizeFileList.add(name);
							//entryInStream.close();
							continue;
							// throw new CommonException("序号"+voliceId+"文件大小超过1M,请您压缩后重新上传");
						}

						//获取音频时长
						int times=0;
						try {
							times = BotSentenceUtil.getVideoTime(temp.getPath());
							logger.info("录音时长: " + times);
						} catch (Exception e) {
							logger.error("获取录音时长异常...", e);
						}
						if (voliceIds.contains(voliceId)) {
							String url = BotSentenceUtil.updloadNas(processId, userId, temp);

							if (needTts) {
								if (index > 0) {
									logger.info("当前话术是TTS合成录音 ...");
									// 更新tts任务表的url
									BotSentenceTtsTaskExample ttsTaskExample = new BotSentenceTtsTaskExample();
									ttsTaskExample.createCriteria()
											.andProcessIdEqualTo(processId)
											.andBusiIdEqualTo(voliceId)
											.andSeqEqualTo(voiceIdWithIndex);
									List<BotSentenceTtsTask> list = botSentenceTtsTaskMapper.selectByExample(ttsTaskExample);
									if (null != list && list.size() > 0) {
										BotSentenceTtsTask task = list.get(0);
										task.setVoliceUrl(url);
										task.setTimes(times);
										task.setLstUpdateTime(new Date(System.currentTimeMillis()));
										task.setLstUpdateUser(userId);
										botSentenceTtsTaskMapper.updateByPrimaryKey(task);
									}
								}else if(index == 0){
									logger.info("当前话术是备用话术的录音 ...");
									//更新备用话术表URL
									//查询当前文案的备用文案
									BotSentenceTtsBackupExample backupExample = new BotSentenceTtsBackupExample();
									backupExample.createCriteria().andProcessIdEqualTo(processId).andVoliceIdEqualTo(new Long(voliceId));
									List<BotSentenceTtsBackup> backupList = botSentenceTtsBackupMapper.selectByExample(backupExample);
									if(null != backupList && backupList.size() > 0) {
										BotSentenceTtsBackup backup = backupList.get(0);
										backup.setUrl(url);
										backup.setTimes(times);
										backup.setLstUpdateTime(new Date(System.currentTimeMillis()));
										backup.setLstUpdateUser(userId);
										botSentenceTtsBackupMapper.updateByPrimaryKey(backup);
									}
								}else {
									VoliceInfo volice = this.getVoliceInfo(new Long(voliceId));
									this.updateVoliceInfo(new Long(voliceId), volice.getType(), url, times, userId);
								}
								
								
								// 更新tts任务表的url
								/*BotSentenceTtsTaskExample ttsTaskExample = new BotSentenceTtsTaskExample();
								ttsTaskExample.createCriteria().andProcessIdEqualTo(processId)
										.andBusiIdEqualTo(voliceId).andSeqEqualTo(seq);
								List<BotSentenceTtsTask> list = botSentenceTtsTaskMapper
										.selectByExample(ttsTaskExample);
								if (null != list && list.size() > 0) {
									BotSentenceTtsTask task = list.get(0);
									task.setVoliceUrl(url);
									task.setLstUpdateTime(new Date(System.currentTimeMillis()));
									task.setLstUpdateUser(UserUtil.getUserId());
									botSentenceTtsTaskMapper.updateByPrimaryKey(task);
								}*/

							} else {
								VoliceInfo volice = voliceInfoMapper.selectByPrimaryKey(new Long(voliceId));

								if (!"【新增】".equals(volice.getFlag())) {
									voliceInfoExtMapper.updateVoliceUrlAndNameById(voliceId, url, "【修改】", times);
								} else {
									// 插入数据库
									voliceInfoExtMapper.updateVoliceUrlById(voliceId, url, times);
								}
							}
							successNum++;
							successFileList.add(name);
						} else {
							notmatchFileList.add(name);
						}
						//entryInStream.close();
					}
				}
			}

			zipFile.close();
			FileUtils.deleteDirectory(descFile);

			// delAllFile(descFile.getPath());

			/*
			 * Enumeration<?> entries = zipFile.entries(); while(entries.hasMoreElements()){
			 * ZipEntry entry = (ZipEntry)entries.nextElement(); InputStream
			 * entryInStream=zipFile.getInputStream(entry); String name=entry.getName();
			 * 
			 * String[] arrays= name.split("\\."); String voliceId=arrays[0];
			 * 
			 * String suffix = name.substring(name.lastIndexOf(".") + 1);
			 * 
			 * if(!"wav".equals(suffix)) { formatFileList.add(name); continue; }
			 * 
			 * long size = entry.getSize(); if(size > 1 * 1024 * 1024) {
			 * sizeFileList.add(name); continue; //throw new
			 * CommonException("序号"+voliceId+"文件大小超过1M,请您压缩后重新上传"); }
			 * 
			 * if(voliceIds.contains(voliceId)){ String
			 * url=qiuniuUploadUtil.upload(entryInStream, null); VoliceInfo volice =
			 * voliceInfoMapper.selectByPrimaryKey(new Long(voliceId));
			 * 
			 * if(!"【新增】".equals(volice.getFlag())) {
			 * voliceInfoExtMapper.updateVoliceUrlAndNameById(voliceId, url, "【修改】"); }else
			 * { //插入数据库 voliceInfoExtMapper.updateVoliceUrlById(voliceId,url); }
			 * successNum++; successFileList.add(name); //successUpdate.add(voliceId); }else
			 * { notmatchFileList.add(name); } }
			 */
			// 更新流程状态
			botSentenceProcessService.updateProcessState(processId, userId);

		} catch (IOException e) {
			e.printStackTrace();
		}

		successUpdate.add("成功更新{" + successNum + "}条");

		if (formatFileList.size() > 0) {
			successUpdate.add("文件:" + formatFileList.toString() + "格式不正确");
		}

		if (sizeFileList.size() > 0) {
			successUpdate.add("文件:" + sizeFileList.toString() + "大小超过1M");
		}

		if (notmatchFileList.size() > 0) {
			successUpdate.add("文件:" + notmatchFileList.toString() + "无匹配语音");
		}

		if (successFileList.size() > 0) {
			successUpdate.add("文件:" + successFileList.toString() + "语音已上传成功");
		}

		return successUpdate;
	}

	private void writeFile(InputStream in, File file) {
		OutputStream out = null;
		try {
			out = new FileOutputStream(file);
			byte[] b = new byte[1024];
			int index = -1;
			while ((index = in.read(b)) != -1) {
				out.write(b, 0, index);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtil.close(out);
			IOUtil.close(in);
		}
	}

	private void zip(ZipOutputStream out, File f, String base) throws Exception {
		if (f.isDirectory()) {
			File[] files = f.listFiles();
			base = (base.length() == 0 ? "" : base + "/");
			for (int i = 0; i < files.length; i++) {
				zip(out, files[i], base + files[i].getName());
			}
		} else {
			out.putNextEntry(new ZipEntry(base));
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(f));
			int c;

			while ((c = in.read()) != -1) {
				out.write(c);
			}
			in.close();
		}
	}

	public void zip(File inputFileName, String zipFileName) throws Exception {
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
		zip(out, inputFileName, "");
		out.close();
	}

	// 压缩文件，inputFileName表示要压缩的文件（可以为目录）,zipFileName表示压缩后的zip文件
	public void zip(String inputFileName, String zipFileName) throws Exception {
		zip(new File(inputFileName), zipFileName);
	}

	@Override
	public boolean uploadVoliceJsonZip(File dir, String fileName, String processId, String templateId, String userId) {
		//查詢任務中心,是否可以发布
		ReturnData<Boolean> checkResult=iDispatchPlanOut.receiveRobotId(templateId);
		if(checkResult.getBody()){
			// 获取未加密的replace.json文件
			List<File> listFile = new ArrayList<File>();
			FileUtil.getAllFilePaths(dir, listFile);

			File replaceFile = new File("replace.json");
			for (File temp : listFile) {
				if (temp.getName().equals("replace.json")) {
					try {
						FileUtil.copyFile(temp.getPath(), replaceFile.getPath());
					} catch (IOException e) {
						logger.error("复制replace.json文件异常", e);
						return false;
					}
					break;
				}
			}
			
			String zipFileName = fileName;
			// 打包成规定的格式
			File zipFile;
			
			try {
				zipFileName = zipFileName.replaceAll("_en", "");
				fileName = templateId.replaceAll("_en", "");
				zipFile = File.createTempFile(fileName, ".zip");
				this.zip(dir, zipFile.getPath());
			} catch (Exception e) {
				logger.error("压缩打包json文件异常>..", e);
				return false;
			}
			
			// 上传
			SysFileReqVO sysFileReqVO=new SysFileReqVO();
			sysFileReqVO.setBusiId(templateId);
			sysFileReqVO.setSysCode(NAS_UPLAOD_SYSTEM_CODE);
			SysFileRspVO sysFileRspVO=nasUtile.uploadNas(sysFileReqVO, zipFile);
			String uplaodFileName=sysFileRspVO.getSkUrl();
			
			UpgrateResouceReq resouceReq=new UpgrateResouceReq();
			resouceReq.setUserId(new Long(userId));
			resouceReq.setFile(uplaodFileName);
			resouceReq.setTmplId(templateId);
			resouceReq.setProcessTypeEnum(ProcessTypeEnum.ROBOT);
			
			List<String> allList = new ArrayList<>();
			
			UUID jobId = UUID.randomUUID();
			
			ReturnData<PublishBotstenceTaskVO> robot_result = iProcessSchedule.publishResource(resouceReq);
			if("0".equals(robot_result.getCode()) && null != robot_result.getBody().getSubJobIds() && robot_result.getBody().getSubJobIds().size() > 0) {
				//allList.addAll(robot_result.getBody().getSubJobIds());
				logger.info("保存robot任务...");
				botSentenceApprovalService.saveDeploy(robot_result.getBody().getSubJobIds(), jobId.toString(), processId, templateId, userId);
			}
			resouceReq.setProcessTypeEnum(ProcessTypeEnum.FREESWITCH);
			ReturnData<PublishBotstenceTaskVO> freeswitch_result = iProcessSchedule.publishResource(resouceReq);
			if("0".equals(freeswitch_result.getCode()) && null != freeswitch_result.getBody().getSubJobIds() && freeswitch_result.getBody().getSubJobIds().size() > 0) {
				//allList.addAll(freeswitch_result.getBody().getSubJobIds());
				logger.info("保存freeswitch任务...");
				botSentenceApprovalService.saveDeploy(freeswitch_result.getBody().getSubJobIds(), jobId.toString(), processId, templateId, userId);
			}
			
			// 加密
			fileCrypter(dir);
			
			try {
				zipFileName = zipFileName.replaceAll("_en", "");
				fileName = templateId.replaceAll("_en", "");
				zipFile = File.createTempFile(fileName, ".zip");
				this.zip(dir, zipFile.getPath());
			} catch (Exception e) {
				logger.error("压缩打包json文件异常>..", e);
				return false;
			}

			if (zipFile != null) {
				// 上传
				sysFileReqVO.setBusiId(templateId);
				sysFileReqVO.setSysCode(NAS_UPLAOD_SYSTEM_CODE);
				sysFileRspVO=nasUtile.uploadNas(sysFileReqVO, zipFile);
				uplaodFileName=sysFileRspVO.getSkUrl();
				//部署
				
				resouceReq.setProcessTypeEnum(ProcessTypeEnum.SELLBOT);
				ReturnData<PublishBotstenceTaskVO> sellbot_result = iProcessSchedule.publishResource(resouceReq);
				
				if("0".equals(sellbot_result.getCode()) && null != sellbot_result.getBody().getSubJobIds() && sellbot_result.getBody().getSubJobIds().size() > 0) {
					//allList.addAll(sellbot_result.getBody().getSubJobIds());
					logger.info("保存sellbot任务...");
					botSentenceApprovalService.saveDeploy(sellbot_result.getBody().getSubJobIds(), jobId.toString(), processId, templateId, userId);
				}
				
				
				/*if(null != allList && allList.size() > 0) {
					logger.info("共返回" + allList.size() + "条任务");
					int index = 1;
					for(String temp : allList) {
						logger.info("任务【" + index + "】的任务号:  " + temp) ;
						BotSentenceDeploy deploy = new BotSentenceDeploy();
						deploy.setJobId(uuid.toString());
						deploy.setSubJobId(temp);
						deploy.setStatus("1");//默认表示失败
						deploy.setCrtTime(new Date(System.currentTimeMillis()));
						deploy.setCrtUser(userId);
						deploy.setProcessId(processId);
						deploy.setTemplateId(templateId);
						botSentenceDeployMapper.insert(deploy);
						index++;
					}
				}*/
				return true;
			}
			
		}
		return false;
	}

	/**
	 * 遍历加密
	 * 
	 * @param file
	 */
	private void fileCrypter(File file) {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File fileItem : files) {
				fileCrypter(fileItem);
			}
		} else {
			String fileName = file.getName();
			String path = file.getPath();
			// if(path.indexOf("new_domain_cfg") > 0) {
			if (fileName.endsWith("json")) {
				System.out.println(fileName);
				JsonBase64Crypter.encodeFile(file);
			}
			// }
		}
	}

	public File creatStandardZipStructure(File file, String fileName, String templateId) {
		InputStream pyFileInput = null;
		InputStream zipFileInput = null;
		ZipOutputStream out = null;
		InputStream executeFileInput = null;
		try {
			File zip = File.createTempFile(
					String.valueOf(
							DateUtil.yyyyMMddHHmmss2.format(new Date(System.currentTimeMillis())) + "_" + templateId),
					".zip");
			out = new ZipOutputStream(new FileOutputStream(zip));
			String temp_templateId = templateId.replaceAll("_en", "");
			out.putNextEntry(new ZipEntry(fileName + "/" + temp_templateId + ".zip"));
			// 写入zip包
			zipFileInput = new FileInputStream(file);
			byte[] buf = new byte[1024];
			int index = 0;
			while ((index = zipFileInput.read(buf)) != -1) {
				out.write(buf, 0, index);
			}

			out.putNextEntry(new ZipEntry(fileName + "/up_cfgs.py"));
			// 写入python脚本文件
			// pyFileInput=Thread.currentThread().getContextClassLoader().getResourceAsStream("copydir/up_cfgs.py");
			pyFileInput = new FileInputStream(new File(uploadCfgDir + "/up_cfgs.py"));
			while ((index = pyFileInput.read(buf)) != -1) {
				out.write(buf, 0, index);
			}

			//
			File executeFile = new File("execute.sh");
			FileOutputStream executeput = null;
			BufferedOutputStream Buff = null;
			executeput = new FileOutputStream(executeFile);
			Buff = new BufferedOutputStream(executeput);

			// #!/bin/bash
			// python up_cfgs.py

			Buff.write("#!/bin/bash\r\n".getBytes());
			Buff.write(("python up_cfgs.py " + temp_templateId + " " + fileName).getBytes());
			Buff.flush();
			Buff.close();
			executeput.close();

			// 写入execute脚本文件
			out.putNextEntry(new ZipEntry(fileName + "/execute.sh"));
			// executeFileInput=Thread.currentThread().getContextClassLoader().getResourceAsStream("copydir/execute.sh");
			executeFileInput = new FileInputStream(executeFile);
			while ((index = executeFileInput.read(buf)) != -1) {
				out.write(buf, 0, index);
			}

			executeFile.delete();

			return zip;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtil.close(executeFileInput);
			IOUtil.close(pyFileInput);
			IOUtil.close(zipFileInput);
			IOUtil.close(out);
		}

		return null;
	}

	@Override
	@Transactional
	public void saveRefuseVolice(String processId, List<RefuseVoliceVO> contents, String userId) {
		if (StringUtils.isNotBlank(processId) && null != contents && contents.size() > 0) {
			BotSentenceProcess process = botSentenceProcessMapper.selectByPrimaryKey(processId);

			List<String> names = new ArrayList<>();

			if (null != process) {
				for (RefuseVoliceVO content : contents) {

					if (StringUtils.isBlank(content.getName())) {
						throw new CommonException("挽回话术名称不能为空!");
					}
					if (StringUtils.isBlank(content.getContent())) {
						throw new CommonException("挽回话术内容不能为空!");
					}

					if (names.contains(content.getName())) {
						throw new CommonException("挽回话术名[" + content.getName() + "]重复!");
					}

					names.add(content.getName());

					// 先判断当前挽回话术名称是否已存在
					/*
					 * VoliceInfoExample example = new VoliceInfoExample();
					 * example.createCriteria().andProcessIdEqualTo(processId).andNameEqualTo(
					 * content.getName()); List<VoliceInfo> list =
					 * voliceInfoMapper.selectByExample(example); if(null != list && list.size() >
					 * 1) { throw new CommonException("挽回话术["+ content.getName() +"]已存在,请重新录入!"); }
					 * 
					 * if(null != list && list.size() == 1 &&
					 * !list.get(0).getVoliceId().equals(content.getVoliceId())) { throw new
					 * CommonException("挽回话术["+ content.getName() +"]已存在,请重新录入!"); }
					 */

					if (StringUtils.isNotBlank(content.getVoliceId())) {
						VoliceInfo volice = voliceInfoMapper.selectByPrimaryKey(new Long(content.getVoliceId()));
						volice.setContent(content.getContent().replace("\n", "").trim());
						volice.setLstUpdateTime(new Date(System.currentTimeMillis()));
						volice.setLstUpdateUser(userId);
						volice.setName(content.getName());
						// voliceInfoMapper.updateByPrimaryKey(volice);
						this.saveVoliceInfo(volice, userId);
					} else {

						VoliceInfo volice = new VoliceInfo();
						volice.setType(Constant.VOLICE_TYPE_REFUSE);// 挽回话术
						volice.setContent(content.getContent().replace("\n", "").trim());
						volice.setProcessId(processId);
						volice.setTemplateId(process.getTemplateId());
						volice.setName(content.getName());
						volice.setDomainName(content.getName());
						volice.setFlag("【新增】");
						// voliceInfoMapper.insert(volice);
						this.saveVoliceInfo(volice, userId);
					}

				}
			}
		}
	}

	@Override
	public List<VoliceInfo> queryRefuseVoliceList(String processId) {
		if (StringUtils.isNotBlank(processId)) {
			VoliceInfoExample example = new VoliceInfoExample();
			example.createCriteria().andProcessIdEqualTo(processId).andTypeEqualTo(Constant.VOLICE_TYPE_REFUSE);
			return voliceInfoMapper.selectByExample(example);
		}
		return null;
	}

	@Override
	@Transactional
	public void deleteRefuseVolice(String processId, String voliceId, String domainName) {
		if (StringUtils.isNotBlank(processId) && StringUtils.isNotBlank(voliceId)) {
			
			voliceService.deleteVolice(processId, voliceId);
			
			// 删除话术流程对当前挽回话术的依赖关系
			botSentenceProcessService.deleteRefuseBranch(processId, domainName, voliceId);

		} else {
			throw new CommonException("删除失败,请求参数不完整!");
		}

	}

	public static void main(String[] args) {
		String regEx = Constant.TTS_REG_EX;// 正则表达式
		// 获取变量列表
		String str = "$0001我们$1111$6666项目的具体地$1111址在南京雨花台，就在南京南火车站附$2222近，交通还是蛮方便的啊！$0002";
		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher = pattern.matcher(str);
		List<String> list = new ArrayList<>();
		
		while (matcher.find()) {
			String match = matcher.group();
			int i = matcher.start();
			str.substring(0, i);
			//String [] array = str.split("[$]"+match.substring(1, match.length()));
			String [] array = new String[2];
			array[0] = str.substring(0, i);
			array[1] = str.substring(i+5);
			list.add(array[0]);
			list.add(match);
			str = array[1];
			if(!BotSentenceUtil.validateContainParam(str)) {
				list.add(str);
				break;
			}
			matcher = pattern.matcher(str);
		}
		
		for(String temp : list) {
			if(StringUtils.isNotBlank(temp)) {
				System.out.println(temp);
			}
		}
		
		
	}

	@SuppressWarnings("rawtypes")
	public static void unZipFiles(ZipFile zip, String descDir) throws IOException {
		File pathFile = new File(descDir);
		if (!pathFile.exists()) {
			pathFile.mkdirs();
		}
		for (Enumeration entries = zip.entries(); entries.hasMoreElements();) {
			ZipEntry entry = (ZipEntry) entries.nextElement();

			String zipEntryName = entry.getName();
			InputStream in = zip.getInputStream(entry);
			// String outPath = (descDir+ FILE_SEPARATOR +zipEntryName).replaceAll("\\*",
			// "/");;
			String outPath = (descDir + FILE_SEPARATOR + zipEntryName);

			// 判断路径是否存在,不存在则创建文件路径
			File file = new File(outPath.substring(0, outPath.lastIndexOf(FILE_SEPARATOR)));
			if (!file.exists()) {
				file.mkdirs();
			}
			// 判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
			if (new File(outPath).isDirectory()) {
				continue;
			}
			// 输出文件路径信息
			System.out.println(outPath);

			OutputStream out = new FileOutputStream(outPath);
			byte[] buf1 = new byte[1024];
			int len;
			while ((len = in.read(buf1)) > 0) {
				out.write(buf1, 0, len);
			}
			in.close();
			out.close();
		}
		System.out.println("******************解压完毕********************");
	}

	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}

	// 删除文件夹
	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			System.out.println(myFilePath.delete()); // 删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private List<VoliceInfoExt> getVoliceInfoExtList(String processId, Long voliceId, String title) {
		List<VoliceInfoExt> voliceInfo_list = new ArrayList<>();
		VoliceInfo volice = voliceInfoMapper.selectByPrimaryKey(voliceId);
		if (null != volice.getNeedTts() && volice.getNeedTts()) {
			// 查询tts合成任务表
			BotSentenceTtsTaskExample ttsTaskExample = new BotSentenceTtsTaskExample();
			ttsTaskExample.createCriteria().andProcessIdEqualTo(processId)
					.andBusiIdEqualTo(volice.getVoliceId().toString()).andBusiTypeEqualTo(Constant.TTS_BUSI_TYPE_01)
					.andIsParamEqualTo(Constant.IS_PARAM_FALSE);
			List<BotSentenceTtsTask> taskList = botSentenceTtsTaskMapper.selectByExample(ttsTaskExample);
			if (null != taskList && taskList.size() > 0) {
				for (BotSentenceTtsTask task : taskList) {
					// 查询当前录音信息
					VoliceInfoExt vo = new VoliceInfoExt();
					vo.setProcessId(processId);
					vo.setVoliceId(task.getSeq());
					vo.setTitle(title);
					vo.setContent(task.getContent());
					vo.setVoliceUrl(task.getVoliceUrl());
					vo.setName(volice.getName());
					vo.setFlag(volice.getFlag());
					vo.setType(Constant.VOLICE_TYPE_TTS);
					if (StringUtils.isNotBlank(task.getVoliceUrl())) {
						vo.setHasVolice("是");
					} else {
						vo.setHasVolice("否");
					}
					
					if(null != task.getTimes()) {
						vo.setTimes(task.getTimes());
					}else {
						if(StringUtils.isNotBlank(task.getContent())) {
							vo.setTimes(task.getContent().length()/6);
							if(vo.getTimes() == 0) {
								vo.setTimes(1);
							}
						}else {
							vo.setTimes(0);
						}
					}
					voliceInfo_list.add(vo);
				}
			}
		} else {
			// 查询当前录音信息
			VoliceInfoExt vo = new VoliceInfoExt();
			vo.setProcessId(processId);
			vo.setVoliceId(voliceId.toString());
			vo.setTitle(title);

			if (null != volice) {
				vo.setContent(volice.getContent());
				vo.setVoliceUrl(volice.getVoliceUrl());
				vo.setName(volice.getName());
				vo.setFlag(volice.getFlag());
				if (StringUtils.isNotBlank(volice.getVoliceUrl())) {
					vo.setHasVolice("是");
				} else {
					vo.setHasVolice("否");
				}
				if(null != volice.getTimes()) {
					vo.setTimes(volice.getTimes());
				}else {
					if(StringUtils.isNotBlank(volice.getContent())) {
						vo.setTimes(volice.getContent().length()/6);
						if(vo.getTimes() == 0) {
							vo.setTimes(1);
						}
					}else {
						vo.setTimes(0);
					}
				}
			}
			voliceInfo_list.add(vo);
		}
		return voliceInfo_list;
	}

	@Override
	public String uploadOneVolice(String processId, String voliceId, File file, String type, int times, String userId) {
//		BotSentenceProcess process = botSentenceProcessMapper.selectByPrimaryKey(processId);
//		String keyName = "upload_by_template_gui/"
//				+ DateUtil.yyyyMMddHHmmss2.format(new Date(System.currentTimeMillis())) + "_" + process.getTemplateId()
//				+ ".wav";
		logger.info("开始上传录音 ");
		
		String voliceUrl = BotSentenceUtil.updloadNas(processId, userId, file);
		//String voliceUrl = qiuniuUploadUtil.upload(inStream, keyName);

		logger.info("上传录音成功: " + voliceUrl);
		String voliceId2 = voliceId.split("_")[0];
		if (Constant.VOLICE_TYPE_TTS.equals(type)) {
			// 更新tts任务表的url
			BotSentenceTtsTaskExample ttsTaskExample = new BotSentenceTtsTaskExample();
			ttsTaskExample.createCriteria().andProcessIdEqualTo(processId).andBusiIdEqualTo(voliceId2)
					.andSeqEqualTo(voliceId);
			List<BotSentenceTtsTask> list = botSentenceTtsTaskMapper.selectByExample(ttsTaskExample);
			if (null != list && list.size() > 0) {
				BotSentenceTtsTask task = list.get(0);
				task.setVoliceUrl(voliceUrl);
				
				//设置音频时长
				task.setTimes(times);
				task.setLstUpdateTime(new Date(System.currentTimeMillis()));
				task.setLstUpdateUser(userId);
				botSentenceTtsTaskMapper.updateByPrimaryKey(task);
			}
		}else if(Constant.VOLICE_TYPE_BACKUP.equals(type)){
			//更新备用话术表URL
			//查询当前文案的备用文案
			BotSentenceTtsBackupExample backupExample = new BotSentenceTtsBackupExample();
			backupExample.createCriteria().andProcessIdEqualTo(processId).andVoliceIdEqualTo(new Long(voliceId2));
			List<BotSentenceTtsBackup> backupList = botSentenceTtsBackupMapper.selectByExample(backupExample);
			if(null != backupList && backupList.size() > 0) {
				BotSentenceTtsBackup backup = backupList.get(0);
				backup.setUrl(voliceUrl);
				backup.setTimes(times);
				backup.setLstUpdateTime(new Date(System.currentTimeMillis()));
				backup.setLstUpdateUser(userId);
				botSentenceTtsBackupMapper.updateByPrimaryKey(backup);
			}
		}else {
			VoliceInfo volice = this.getVoliceInfo(new Long(voliceId));
			this.updateVoliceInfo(new Long(voliceId), volice.getType(), voliceUrl, times, userId);
		}
		return voliceUrl;
	}
	
	private void splitContentAndSave(VoliceInfo voliceInfo, String newContent, String userId) {
		List<String> paramList = new ArrayList<>();
		String regEx = Constant.TTS_REG_EX;// 正则表达式
		// 获取变量列表
		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher = pattern.matcher(newContent);
		while (matcher.find()) {
			String match = matcher.group();
			if(!paramList.contains(match)) {
				paramList.add(match);
			}
		}
		
		// 获取变量列表
		List<String> splitContentList = Lists.newArrayList();
		Matcher matcher2 = pattern.matcher(newContent);
		while (matcher2.find()) {
			String match = matcher2.group();
			int i = matcher2.start();
			String [] array = new String[2];
			array[0] = newContent.substring(0, i);
			array[1] = newContent.substring(i+5);
			splitContentList.add(array[0]);
			splitContentList.add(match);
			newContent = array[1];
			if(!BotSentenceUtil.validateContainParam(newContent)) {
				splitContentList.add(newContent);
				break;
			}
			matcher2 = pattern.matcher(newContent);
		}
		
		int index = 1;
		for(String splitContent : splitContentList) {
			if(StringUtils.isNotBlank(splitContent) && !"。".equals(splitContent) && !".".equals(splitContent)
					&& !"，".equals(splitContent) && !",".equals(splitContent) && !"！".equals(splitContent)
					&& !"!".equals(splitContent) && !"？".equals(splitContent) && !"?".equals(splitContent)) {

				String seq = voliceInfo.getVoliceId() + "_" + index;

				BotSentenceTtsTask ttsTask = new BotSentenceTtsTask();
				ttsTask.setBusiId(voliceInfo.getVoliceId().toString());
				ttsTask.setBusiType(TtsTaskTypeEnum.CALL_RECORD.getKey());
				ttsTask.setContent(splitContent);
				ttsTask.setSeq(seq);
				if(paramList.contains(splitContent)) {
					ttsTask.setIsParam(TtsTaskParamEnum.IS_PARAM.getKey());
					iTtsService.saveTtsParam(splitContent, TtsParamTypeEnum.NORMAL, voliceInfo.getProcessId(), userId);
				}else {
					ttsTask.setIsParam(Constant.IS_PARAM_FALSE);
				}
				ttsTask.setProcessId(voliceInfo.getProcessId());
				ttsTask.setCrtTime(new Date());
				ttsTask.setCrtUser(userId);
				botSentenceTtsTaskMapper.insert(ttsTask);
				botSentenceProcessService.updateProcessState(voliceInfo.getProcessId(), userId);
				index++;
			}
		}
	}

	@Override
	public int countFinishNum(String processId) {
		//一般录音已完成数量
		List<String> ignoreDomainList = new ArrayList<>();
		ignoreDomainList.add("不清楚");
		ignoreDomainList.add("不知道");
		ignoreDomainList.add("等待");
		ignoreDomainList.add("用户不清楚");
		ignoreDomainList.add("自由介绍");
		VoliceInfoExample example = new VoliceInfoExample();
		example.createCriteria().andProcessIdEqualTo(processId).andVoliceUrlIsNotNull().andDomainNameNotIn(ignoreDomainList).andNeedTtsNotEqualTo(true);
		Long num = voliceInfoMapper.countByExample(example);
		
		//tts录音已完成数量
		BotSentenceTtsTaskExample ttsTaskExample = new BotSentenceTtsTaskExample();
		ttsTaskExample.createCriteria().andProcessIdEqualTo(processId).andIsParamEqualTo(Constant.IS_PARAM_FALSE).andVoliceUrlIsNotNull();

		int ttsNum = botSentenceTtsTaskMapper.countByExample(ttsTaskExample);
			
		//备用话术已完成数量
		BotSentenceTtsBackupExample backupExample = new BotSentenceTtsBackupExample();
		backupExample.createCriteria().andProcessIdEqualTo(processId).andUrlIsNotNull();
		int backNum = botSentenceTtsBackupMapper.countByExample(backupExample);	
		
		return num.intValue() + ttsNum + backNum;
	}

	@Override
	public int countUnFinishNum(String processId) {
		List<String> ignoreDomainList = new ArrayList<>();
		ignoreDomainList.add("不清楚");
		ignoreDomainList.add("不知道");
		ignoreDomainList.add("等待");
		ignoreDomainList.add("用户不清楚");
		ignoreDomainList.add("自由介绍");
		VoliceInfoExample example = new VoliceInfoExample();
		example.createCriteria().andProcessIdEqualTo(processId).andVoliceUrlIsNull().andDomainNameNotIn(ignoreDomainList).andNeedTtsNotEqualTo(true);;
		Long num = voliceInfoMapper.countByExample(example);
		
		//tts录音已完成数量
		BotSentenceTtsTaskExample ttsTaskExample = new BotSentenceTtsTaskExample();
		ttsTaskExample.createCriteria().andProcessIdEqualTo(processId).andIsParamEqualTo(Constant.IS_PARAM_FALSE).andVoliceUrlIsNull();

		int ttsNum = botSentenceTtsTaskMapper.countByExample(ttsTaskExample);
			
		//备用话术已完成数量
		BotSentenceTtsBackupExample backupExample = new BotSentenceTtsBackupExample();
		backupExample.createCriteria().andProcessIdEqualTo(processId).andUrlIsNull();
		int backNum = botSentenceTtsBackupMapper.countByExample(backupExample);	
		
		return num.intValue() + ttsNum + backNum;
	}

	
	@Override
	public List<VoliceInfoExt> queryVoliceListSimple(String processId) {
		List<VoliceInfoExt> voliceInfo_list = new ArrayList<>();
		
		VoliceInfoExample voliceInfoExample = new VoliceInfoExample();
		List<String> ignoreDomainList = new ArrayList<>();
		ignoreDomainList.add("不清楚");
		ignoreDomainList.add("不知道");
		ignoreDomainList.add("等待");
		ignoreDomainList.add("用户不清楚");
		ignoreDomainList.add("自由介绍");
		
		List<BotSentenceDomain> domainList = botSentenceProcessService.getAllDomainList(processId);
		String orderStr = " FIELD(domain_name";
		for(BotSentenceDomain domain : domainList) {
			orderStr = orderStr + "," + "'" + domain.getDomainName() + "'";
		}
		orderStr = orderStr + ")";
		
		voliceInfoExample.createCriteria().andProcessIdEqualTo(processId).andDomainNameNotIn(ignoreDomainList);
		voliceInfoExample.setOrderByClause(orderStr);
		List<VoliceInfo> list = voliceInfoMapper.selectByExample(voliceInfoExample);
		
		for(BotSentenceDomain domain : domainList) {
			if(null != list && list.size() > 0) {
				for(VoliceInfo volice : list) {
					if(domain.getDomainName().equals(volice.getDomainName())) {
						if (null != volice.getNeedTts() && volice.getNeedTts()) {
							// 查询tts合成任务表
							BotSentenceTtsTaskExample ttsTaskExample = new BotSentenceTtsTaskExample();
							ttsTaskExample.createCriteria().andProcessIdEqualTo(processId)
									.andBusiIdEqualTo(volice.getVoliceId().toString()).andBusiTypeEqualTo(Constant.TTS_BUSI_TYPE_01)
									.andIsParamEqualTo(Constant.IS_PARAM_FALSE);
							List<BotSentenceTtsTask> taskList = botSentenceTtsTaskMapper.selectByExample(ttsTaskExample);
							if (null != taskList && taskList.size() > 0) {
								for (BotSentenceTtsTask task : taskList) {
									// 查询当前录音信息
									VoliceInfoExt vo = new VoliceInfoExt();
									vo.setProcessId(processId);
									vo.setVoliceId(task.getSeq());
									vo.setTitle(volice.getDomainName());
									vo.setContent(task.getContent());
									vo.setVoliceUrl(task.getVoliceUrl());
									vo.setName(volice.getName());
									vo.setFlag(volice.getFlag());
									vo.setType(Constant.VOLICE_TYPE_TTS);
									if(null != task.getTimes()) {
										vo.setTimes(task.getTimes());
									}
									
									if (StringUtils.isNotBlank(task.getVoliceUrl())) {
										vo.setHasVolice("是");
									} else {
										vo.setHasVolice("否");
									}
									voliceInfo_list.add(vo);
								}
							}
							continue;
						}
						
						
						
						
						// 查询当前录音信息
						VoliceInfoExt vo = new VoliceInfoExt();
						vo.setProcessId(processId);
						vo.setVoliceId(volice.getVoliceId().toString());
						vo.setTitle(volice.getDomainName());
						vo.setContent(volice.getContent());
						vo.setVoliceUrl(volice.getVoliceUrl());
						vo.setName(volice.getName());
						vo.setFlag(volice.getFlag());
						vo.setType(volice.getType());
						if(null != volice.getTimes()) {
							vo.setTimes(volice.getTimes());
						}
						
						if (StringUtils.isNotBlank(volice.getVoliceUrl())) {
							vo.setHasVolice("是");
						} else {
							vo.setHasVolice("否");
						}
						voliceInfo_list.add(vo);
						/*if(null != map.get(volice.getDomainName())) {
							List<VoliceInfoExt> list2 = map.get(volice.getDomainName());
							list2.add(vo);
						}*/
					}
					
				}
			}
		}
		
		
		//查询挽回话术
		VoliceInfoExample refuseVoliceInfoExample = new VoliceInfoExample();
		refuseVoliceInfoExample.createCriteria().andProcessIdEqualTo(processId).andTypeEqualTo(Constant.VOLICE_TYPE_REFUSE);
		List<VoliceInfo> refulseList = voliceInfoMapper.selectByExample(refuseVoliceInfoExample);
		if(null != refulseList) {
			for(VoliceInfo volice : refulseList) {
				VoliceInfoExt vo = new VoliceInfoExt();
				vo.setProcessId(processId);
				vo.setVoliceId(volice.getVoliceId().toString());
				vo.setTitle(volice.getDomainName());
				vo.setContent(volice.getContent());
				vo.setVoliceUrl(volice.getVoliceUrl());
				vo.setName(volice.getName());
				vo.setFlag(volice.getFlag());
				vo.setType(volice.getType());
				if(null != volice.getTimes()) {
					vo.setTimes(volice.getTimes());
				}
				
				if (StringUtils.isNotBlank(volice.getVoliceUrl())) {
					vo.setHasVolice("是");
				} else {
					vo.setHasVolice("否");
				}
				voliceInfo_list.add(vo);
			}
		}
		
		// 查询备用话术
		List<TtsBackup> backupList = botSentenceTtsService.queryTtsBackupList(processId);
		if (null != backupList && backupList.size() > 0) {
			int index = 1;
			for (TtsBackup backup : backupList) {
				if(StringUtils.isNotBlank(backup.getBackup())) {
					VoliceInfoExt vo = new VoliceInfoExt();
					vo.setType(Constant.VOLICE_TYPE_BACKUP);
					vo.setProcessId(processId);
					vo.setVoliceId(backup.getVoliceId() + "_0");
					vo.setTitle("备用话术" + index);
					vo.setContent(backup.getBackup());
					vo.setVoliceUrl(backup.getUrl());
					if(null != backup.getTimes()) {
						vo.setTimes(backup.getTimes());
					}
					
					if (StringUtils.isNotBlank(backup.getUrl())) {
						vo.setHasVolice("是");
					} else {
						vo.setHasVolice("否");
					}
					voliceInfo_list.add(vo);

					index++;
				}
			}
		}
		
		/*
		if (null != volice.getNeedTts() && volice.getNeedTts()) {
			// 查询tts合成任务表
			BotSentenceTtsTaskExample ttsTaskExample = new BotSentenceTtsTaskExample();
			ttsTaskExample.createCriteria().andProcessIdEqualTo(processId)
					.andBusiIdEqualTo(volice.getVoliceId().toString()).andBusiTypeEqualTo(Constant.TTS_BUSI_TYPE_01)
					.andIsParamEqualTo(Constant.IS_PARAM_FALSE);
			List<BotSentenceTtsTask> taskList = botSentenceTtsTaskMapper.selectByExample(ttsTaskExample);
			if (null != taskList && taskList.size() > 0) {
				for (BotSentenceTtsTask task : taskList) {
					// 查询当前录音信息
					VoliceInfoExt vo = new VoliceInfoExt();
					vo.setProcessId(processId);
					vo.setVoliceId(task.getSeq());
					vo.setTitle(title);
					vo.setContent(task.getContent());
					vo.setVoliceUrl(task.getVoliceUrl());
					vo.setName(volice.getName());
					vo.setFlag(volice.getFlag());
					vo.setType(Constant.VOLICE_TYPE_TTS);
					if (StringUtils.isNotBlank(task.getVoliceUrl())) {
						vo.setHasVolice("是");
					} else {
						vo.setHasVolice("否");
					}
					voliceInfo_list.add(vo);
				}
			}
		} else {
			// 查询当前录音信息
			VoliceInfoExt vo = new VoliceInfoExt();
			vo.setProcessId(processId);
			vo.setVoliceId(voliceId.toString());
			vo.setTitle(title);

			if (null != volice) {
				vo.setContent(volice.getContent());
				vo.setVoliceUrl(volice.getVoliceUrl());
				vo.setName(volice.getName());
				vo.setFlag(volice.getFlag());
				if (StringUtils.isNotBlank(volice.getVoliceUrl())) {
					vo.setHasVolice("是");
				} else {
					vo.setHasVolice("否");
				}
			}
			voliceInfo_list.add(vo);
		}*/
		return voliceInfo_list;
	}

	/**
	 * 删除所有话术的录音
	 */
	@Override
	public void deleteAllVolice(String processId) {
		if(StringUtils.isNotBlank(processId)) {
			voliceInfoExtMapper.deleteAllVoliceUrl(processId);
		}
	}

	@Override
	public String uploadOneVolice(String processId, String voliceId, MultipartFile multipartFile, String type, String userId) throws IOException {
		File dir = new File(tempPath);
		logger.info("临时目录: " + tempPath);
		File file = File.createTempFile(voliceId + "-" + String.valueOf(System.currentTimeMillis()), ".wav", dir);
		FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), file);
		int times = 0;
		try {
			//times = BotSentenceUtil.getMediaDuration(file);
			times = BotSentenceUtil.getVideoTime(file.getPath());
			logger.info("录音时长: " + times);
		} catch (Exception e) {
			logger.error("获取录音时长异常...", e);
		}
		
		//BotSentenceProcess process = botSentenceProcessMapper.selectByPrimaryKey(processId);
//		String keyName = "upload_by_template_gui/"
//				+ DateUtil.yyyyMMddHHmmss2.format(new Date(System.currentTimeMillis())) + "_" + process.getTemplateId()
//				+ ".wav";
		logger.info("开始上传录音 ");
		String voliceUrl;
		voliceUrl = BotSentenceUtil.updloadNas(processId, userId, file);
		
		//voliceUrl = qiuniuUploadUtil.upload(new FileInputStream(file), keyName);

		logger.info("上传录音成功: " + voliceUrl);
		String voliceId2 = voliceId.split("_")[0];
		if (Constant.VOLICE_TYPE_TTS.equals(type)) {
			// 更新tts任务表的url
			BotSentenceTtsTaskExample ttsTaskExample = new BotSentenceTtsTaskExample();
			ttsTaskExample.createCriteria().andProcessIdEqualTo(processId).andBusiIdEqualTo(voliceId2)
					.andSeqEqualTo(voliceId);
			List<BotSentenceTtsTask> list = botSentenceTtsTaskMapper.selectByExample(ttsTaskExample);
			if (null != list && list.size() > 0) {
				BotSentenceTtsTask task = list.get(0);
				task.setVoliceUrl(voliceUrl);
				
				//设置音频时长
				task.setTimes(times);
				task.setLstUpdateTime(new Date(System.currentTimeMillis()));
				task.setLstUpdateUser(userId);
				botSentenceTtsTaskMapper.updateByPrimaryKey(task);
			}
		}else if(Constant.VOLICE_TYPE_BACKUP.equals(type)){
			//更新备用话术表URL
			//查询当前文案的备用文案
			BotSentenceTtsBackupExample backupExample = new BotSentenceTtsBackupExample();
			backupExample.createCriteria().andProcessIdEqualTo(processId).andVoliceIdEqualTo(new Long(voliceId2));
			List<BotSentenceTtsBackup> backupList = botSentenceTtsBackupMapper.selectByExample(backupExample);
			if(null != backupList && backupList.size() > 0) {
				BotSentenceTtsBackup backup = backupList.get(0);
				backup.setUrl(voliceUrl);
				backup.setTimes(times);
				backup.setLstUpdateTime(new Date(System.currentTimeMillis()));
				backup.setLstUpdateUser(userId);
				botSentenceTtsBackupMapper.updateByPrimaryKey(backup);
			}
		}else {
			VoliceInfo volice = this.getVoliceInfo(new Long(voliceId));
			this.updateVoliceInfo(new Long(voliceId), volice.getType(), voliceUrl, times, userId);
		}
		
		file.delete();
		
		return voliceUrl;
	
	}

	@Override
	public String uploadOneVoliceOffline(String processId, String voliceId, MultipartFile multipartFile, String type, String userId)
			throws IOException {
		logger.info("voliceId: " + voliceId);
		BotSentenceProcess process = botSentenceProcessMapper.selectByPrimaryKey(processId);
		if(null != multipartFile && StringUtils.isNotBlank(voliceId)) {
			String fileName = multipartFile.getOriginalFilename();
			String suffix =  fileName.substring(fileName.lastIndexOf(".") + 1);
			
			long size = multipartFile.getSize();
			if(size > 1 * 1024 * 1024) {
				throw new CommonException("文件大小超过1M,请重新上传");
			}
			
			if(!"wav".equals(suffix)) {
				throw new CommonException("请上传wav格式音频文件!");
			}
			
			InputStream inStream=multipartFile.getInputStream();
			
			//保存到本地存储目录 
			String dir = localWavDir + process.getTemplateId();
			File wav_dir = new File(dir);
			if(!wav_dir.exists() && !wav_dir.isDirectory()) {
				wav_dir.mkdirs();
			}
			
			String file_path = dir + FILE_SEPARATOR + fileName;
			File wav_file = new File(file_path);
			if(wav_file.exists()) {
				wav_file.delete();
			}
			
			FileOutputStream fos = new FileOutputStream(wav_file);
			
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = inStream.read(buffer)) != -1) {
				fos.write(buffer, 0, len);
			}
			
			this.updateVoliceInfoOffline(voliceId, type, file_path, 0, userId);
			
			inStream.close();
			fos.close();
			return file_path;
		}else {
			throw new CommonException("请求参数不完整!");
		}
	}

	@Override
	public List<String> uploadVoliceZipOffline(String processId, InputStream inStream, String userId) {
		  
		List<String> voliceIds =voliceInfoExtMapper.queryAllVoliceId(processId); 
		List<String> successUpdate=new ArrayList<String>();
		
		int successNum = 0;
		List<String> formatFileList= new ArrayList<>();
		List<String> sizeFileList= new ArrayList<>();
		List<String> notmatchFileList= new ArrayList<>();
		List<String> successFileList= new ArrayList<>();
		
		/*String formatFile = "文件:{format_file_name}格式不正确";
		String sizeFile = "文件:{size_file_name}大小超过1M";
		String notmatchFile = "文件:{notmatch_file_name}无匹配语音";
		String successFile = "文件:{success_file_name}语音已上传成功";*/
		BotSentenceProcess process = botSentenceProcessMapper.selectByPrimaryKey(processId);
		
		 try {
			//保存到本地存储目录 
			String dir = localWavDir + process.getTemplateId();
			File wav_dir = new File(dir);
			if(!wav_dir.exists() && !wav_dir.isDirectory()) {
				wav_dir.mkdirs();
			}
			
			File file_temp = new File(tempPath);
			if(!file_temp.exists()) {
				file_temp.mkdirs();
			}
			File file=File.createTempFile(String.valueOf(System.currentTimeMillis()), "zip", new File(tempPath));
			 writeFile(inStream,file);
			 ZipFile zipFile=new ZipFile(file, Charset.forName("gbk"));
			 
			
			File descFile = new File(UUID.randomUUID().toString());
			descFile.mkdirs();
			
			unZipFiles(zipFile, descFile.getPath());
			
			List<File> listFile = new ArrayList<File>();
			FileUtil.getAllFilePaths(descFile, listFile);
			 
			if(null != listFile && listFile.size() > 0) {
				for(int i = 0 ; i < listFile.size() ; i++) {
					File temp = listFile.get(i);
					if(!temp.isDirectory()) {
						//listFile.remove(temp);
					     //ZipEntry entry = (ZipEntry)entries.nextElement();  
					     InputStream entryInStream=new FileInputStream(temp);
					     String name=temp.getName();
					     
					     String[] arrays= name.split("\\.");
					     String voliceId=arrays[0];
					     
						String suffix =  name.substring(name.lastIndexOf(".") + 1);
						
						if(!"wav".equals(suffix)) {
							formatFileList.add(name);
							entryInStream.close();
							continue;
						}
					     
						long size = temp.length();
						if(size > 1 * 1024 * 1024) {
							sizeFileList.add(name);
							entryInStream.close();
							continue;
						}
					     
					     if(voliceIds.contains(voliceId)){
					    	String file_path = dir + FILE_SEPARATOR + name;
							File wav_file = new File(file_path);
							if(wav_file.exists()) {
								wav_file.delete();
							}
							FileOutputStream fos = new FileOutputStream(wav_file);
							
							byte[] buffer = new byte[1024];
							int len = 0;
							while ((len = entryInStream.read(buffer)) != -1) {
								fos.write(buffer, 0, len);
							}
					    	 
					    	 //String url=qiuniuUploadUtil.upload(entryInStream, null);
					    	 VoliceInfo volice = voliceInfoMapper.selectByPrimaryKey(new Long(voliceId));
					    	 
					    	 if(file_path.contains(localWavDirPre)) {
				    			 file_path = file_path.substring(localWavDirPre.length(), file_path.length());
				 			}
					    	 
					    	 if("【新增】".equals(volice.getFlag())) {
					    		 voliceInfoExtMapper.updateVoliceUrlById(voliceId,file_path, 0);
					 		}else {
					 			voliceInfoExtMapper.updateVoliceUrlAndNameById(voliceId, file_path, "【修改】", 0);
					 		}
					    	 successNum++;
					    	 successFileList.add(name);
					    	 //successUpdate.add(voliceId);
					    	 
					    	 fos.close();
					     }else {
					    	 notmatchFileList.add(name);
					     }
					     entryInStream.close();
					}
				}
			}
			
			zipFile.close();
			FileUtils.deleteDirectory(descFile);
			
			//delAllFile(descFile.getPath());
			
			 /*Enumeration<?> entries = zipFile.entries();
			 while(entries.hasMoreElements()){
			     ZipEntry entry = (ZipEntry)entries.nextElement();  
			     InputStream entryInStream=zipFile.getInputStream(entry);
			     String name=entry.getName();
			     
			     String[] arrays= name.split("\\.");
			     String voliceId=arrays[0];
			     
				String suffix =  name.substring(name.lastIndexOf(".") + 1);
				
				if(!"wav".equals(suffix)) {
					formatFileList.add(name);
					continue;
				}
			     
				long size = entry.getSize();
				if(size > 1 * 1024 * 1024) {
					sizeFileList.add(name);
					continue;
					//throw new CommonException("序号"+voliceId+"文件大小超过1M,请您压缩后重新上传");
				}
			     
			     if(voliceIds.contains(voliceId)){
			    	 String url=qiuniuUploadUtil.upload(entryInStream, null);
			    	 VoliceInfo volice = voliceInfoMapper.selectByPrimaryKey(new Long(voliceId));
			    	 
			    	 if(!"【新增】".equals(volice.getFlag())) {
			    		 voliceInfoExtMapper.updateVoliceUrlAndNameById(voliceId, url, "【修改】");
			 		}else {
			 			//插入数据库
				    	 voliceInfoExtMapper.updateVoliceUrlById(voliceId,url);
			 		}
			    	 successNum++;
			    	 successFileList.add(name);
			    	 //successUpdate.add(voliceId);
			     }else {
			    	 notmatchFileList.add(name);
			     }
			 }*/
			 //更新流程状态
			 botSentenceProcessService.updateProcessState(processId, userId);
			 
		} catch (IOException e) {
			e.printStackTrace();
		}  
		 
	 
		successUpdate.add("成功更新{"+successNum+"}条");
		
		if(formatFileList.size() > 0) {
			successUpdate.add("文件:"+ formatFileList.toString() +"格式不正确");
		}
		
		if(sizeFileList.size() > 0) {
			successUpdate.add("文件:"+ sizeFileList.toString() +"大小超过1M");
		}
		
		if(notmatchFileList.size() > 0) {
			successUpdate.add("文件:"+ notmatchFileList.toString() +"无匹配语音");
		}
		
		if(successFileList.size() > 0) {
			successUpdate.add("文件:"+ successFileList.toString() +"语音已上传成功");
		}
			
		return successUpdate;
    
	}

	@Override
	public VoliceInfo getVoliceByWavName(String templateId, String wavName) {
		if(StringUtils.isNotBlank(templateId) && StringUtils.isNotBlank(wavName)) {
			templateId = templateId + "_en";
			int index = wavName.lastIndexOf("/");
			wavName = wavName.substring(index+1, wavName.length());
			wavName = wavName.replace(".wav", "");
			
			VoliceInfoExample example = new VoliceInfoExample();
			example.createCriteria().andTemplateIdEqualTo(templateId).andWavNameEqualTo(wavName);
			List<VoliceInfo> list = voliceInfoMapper.selectByExample(example);
			if(null != list && list.size() > 0) {
				return list.get(0);
			}
		}
		
		return null;
	}

	/**
	 * 删除一个录音信息
	 */
	@Override
	public void deleteVolice(String processId, String voliceId) {
		logger.info("删除录音信息: " + voliceId);
		voliceInfoMapper.deleteByPrimaryKey(new Long(voliceId));
		
		//删除TTS任务信息
		BotSentenceTtsTaskExample ttsExample = new BotSentenceTtsTaskExample();
		ttsExample.createCriteria().andProcessIdEqualTo(processId).andBusiIdEqualTo(voliceId);
		botSentenceTtsTaskMapper.deleteByExample(ttsExample);
		logger.info("删除TTS任务信息");
		
		//删除备用话术信息
		BotSentenceTtsBackupExample backExample = new BotSentenceTtsBackupExample();
		backExample.createCriteria().andProcessIdEqualTo(processId).andVoliceIdEqualTo(new Long(voliceId));
		botSentenceTtsBackupMapper.deleteByExample(backExample);
		logger.info("删除备用话术信息");
	}

}
