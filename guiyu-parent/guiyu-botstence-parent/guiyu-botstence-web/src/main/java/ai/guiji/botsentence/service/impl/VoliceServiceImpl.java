package ai.guiji.botsentence.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.io.Files;
import com.guiji.common.model.SysFileReqVO;
import com.guiji.common.model.SysFileRspVO;
import com.guiji.common.model.process.ProcessTypeEnum;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.dispatch.api.IDispatchPlanOut;
import com.guiji.process.api.IProcessSchedule;
import com.guiji.process.model.UpgrateResouceReq;
import com.guiji.utils.NasUtil;

import ai.guiji.botsentence.constant.Constant;
import ai.guiji.botsentence.dao.BotSentenceBranchMapper;
import ai.guiji.botsentence.dao.BotSentenceDomainMapper;
import ai.guiji.botsentence.dao.BotSentenceProcessMapper;
import ai.guiji.botsentence.dao.BotSentenceTtsTaskMapper;
import ai.guiji.botsentence.dao.VoliceInfoMapper;
import ai.guiji.botsentence.dao.entity.BotSentenceBranch;
import ai.guiji.botsentence.dao.entity.BotSentenceBranchExample;
import ai.guiji.botsentence.dao.entity.BotSentenceDomain;
import ai.guiji.botsentence.dao.entity.BotSentenceDomainExample;
import ai.guiji.botsentence.dao.entity.BotSentenceProcess;
import ai.guiji.botsentence.dao.entity.BotSentenceTtsTask;
import ai.guiji.botsentence.dao.entity.BotSentenceTtsTaskExample;
import ai.guiji.botsentence.dao.entity.BusinessAnswerTaskExt;
import ai.guiji.botsentence.dao.entity.VoliceInfo;
import ai.guiji.botsentence.dao.entity.VoliceInfoExample;
import ai.guiji.botsentence.dao.entity.VoliceInfoExt;
import ai.guiji.botsentence.dao.ext.VoliceInfoExtMapper;
import ai.guiji.botsentence.service.IVoliceService;
import ai.guiji.botsentence.util.BotSentenceUtil;
import ai.guiji.botsentence.vo.CommonDialogVO;
import ai.guiji.botsentence.vo.RefuseVoliceVO;
import ai.guiji.botsentence.vo.TtsBackup;
import ai.guiji.component.client.util.DateUtil;
import ai.guiji.component.client.util.FileUtil;
import ai.guiji.component.client.util.IOUtil;
import ai.guiji.component.client.util.JsonBase64Crypter;
import ai.guiji.component.client.util.QiuniuUploadUtil;
import ai.guiji.component.exception.CommonException;

@Service
public class VoliceServiceImpl implements IVoliceService {

	Logger logger = LoggerFactory.getLogger(VoliceServiceImpl.class);

	@Autowired
	private VoliceInfoMapper voliceInfoMapper;

	@Autowired
	private VoliceInfoExtMapper voliceInfoExtMapper;

	@Autowired
	private QiuniuUploadUtil qiuniuUploadUtil;

	@Autowired
	private BotSentenceProcessMapper botSentenceProcessMapper;

	@Autowired
	private BotSentenceDomainMapper botSentenceDomainMapper;

	@Autowired
	private BotSentenceBranchMapper botSentenceBranchMapper;

	@Autowired
	private BotSentenceProcessServiceImpl botSentenceProcessService;

	@Autowired
	private BusinessAnswerTaskServiceImpl businessAnswerTaskService;

	@Autowired
	private BotSentenceTtsServiceImpl botSentenceTtsService;

	@Autowired
	private BotSentenceTtsTaskMapper botSentenceTtsTaskMapper;

	private static String FILE_SEPARATOR = System.getProperty("file.separator");

	// 服务器路径
	@Value("${sftp.path}")
	private String ftpPath;

	@Value("${local.upload.dir}")
	private String localUploadDir;
	
	private NasUtil nasUtile=new NasUtil();
	
	private static String NAS_UPLAOD_SYSTEM_CODE="09";
	
	@Autowired
	private IProcessSchedule iProcessSchedule;
	
	@Autowired
	private IDispatchPlanOut iDispatchPlanOut;

	@Override
	@Transactional
	public long saveVoliceInfo(VoliceInfo voliceInfo,Long userId) {
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
					ttsTaskExample.createCriteria().andBusiTypeEqualTo(Constant.TTS_BUSI_TYPE_01)
							.andBusiIdEqualTo(voliceInfo.getVoliceId().toString());
					botSentenceTtsTaskMapper.deleteByExample(ttsTaskExample);

					logger.info("更新当前录音的url为空");
					voliceInfo.setVoliceUrl(null);

					// 包含TTS变量，则需要保存tts任务表数据
					logger.info("当前文案需要TTS合成，需要拆分文案生成多个TTS任务");

					splitContentAndSave(voliceInfo, newContent,userId);

					logger.info("保存tts合成任务表结束...");
				}
				voliceInfo.setNeedTts(true);
			} else {// 如果不包含，则删除原来TTS合成任务
				BotSentenceTtsTaskExample ttsTaskExample = new BotSentenceTtsTaskExample();
				ttsTaskExample.createCriteria().andBusiTypeEqualTo(Constant.TTS_BUSI_TYPE_01)
						.andBusiIdEqualTo(voliceInfo.getVoliceId().toString());
				botSentenceTtsTaskMapper.deleteByExample(ttsTaskExample);
			}
			voliceInfo.setLstUpdateTime(new Date(System.currentTimeMillis()));
			voliceInfo.setLstUpdateUser(userId.toString());
			voliceInfoMapper.updateByPrimaryKeySelective(voliceInfo);

		} else {
			logger.info("新增一条录音信息");
			voliceInfo.setCrtTime(new Date(System.currentTimeMillis()));
			voliceInfo.setCrtUser(userId.toString());
			voliceInfo.setNeedTts(false);
			if (BotSentenceUtil.validateContainParam(newContent)) {// 包含TTS变量，则需要保存tts任务表数据
				voliceInfo.setNeedTts(true);
			}
			
			voliceInfoMapper.insert(voliceInfo);
			
			if (BotSentenceUtil.validateContainParam(newContent)) {// 包含TTS变量，则需要保存tts任务表数据
				logger.info("当前文案需要TTS合成，需要拆分文案生成多个TTS任务");
				logger.info("更新当前录音的url为空");
				
				splitContentAndSave(voliceInfo, newContent,userId);
				
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
	public void updateVoliceInfo(long voliceId, String type, String voliceUrl,Long userId) {
		logger.info("voliceId: " + voliceId);
		VoliceInfo volice = voliceInfoMapper.selectByPrimaryKey(voliceId);
		if (StringUtils.isNotBlank(voliceUrl)) {
			volice.setVoliceUrl(voliceUrl);
		}
		if (!"【新增】".equals(volice.getFlag())) {
			volice.setFlag("【修改】");
		}
		volice.setLstUpdateTime(new Date(System.currentTimeMillis()));
		volice.setLstUpdateUser(userId.toString());

		voliceInfoMapper.updateByPrimaryKey(volice);

		botSentenceProcessService.updateProcessState(volice.getProcessId(),userId);
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
						if (!voliceid_list.contains(respArray[0])) {
							voliceid_list.add(respArray[0]);
							List<VoliceInfoExt> list = getVoliceInfoExtList(processId, new Long(respArray[0]),
									temp.getDomainName());
							voliceInfo_list.addAll(list);
						}
					}
				}

				domainNameList.add(temp.getDomainName());

				if (Constant.DOMAIN_TYPE_START.equals(temp.getType())) {// 如果是开场白，则添加解释开场白
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
							if (!voliceid_list.contains(respArray[0])) {
								voliceid_list.add(respArray[0]);
								List<VoliceInfoExt> list = getVoliceInfoExtList(processId, new Long(respArray[0]),
										"解释开场白");
								voliceInfo_list.addAll(list);
							}
						}
					}

				}
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
		List<BusinessAnswerTaskExt> list = businessAnswerTaskService.queryBusinessAnswerListByPage(processId);
		if (null != list && list.size() > 0) {
			for (BusinessAnswerTaskExt temp : list) {
				if (!voliceid_list.contains(temp.getVoliceId())) {
					voliceid_list.add(temp.getVoliceId());

					List<VoliceInfoExt> extlist = getVoliceInfoExtList(processId, new Long(temp.getVoliceId()),
							"业务问答" + temp.getIndex());
					voliceInfo_list.addAll(extlist);
				}
			}
		}

		// 查询通用对话
		List<CommonDialogVO> commonDialog_list = botSentenceProcessService.queryCommonDialog(processId);
		if (null != commonDialog_list && commonDialog_list.size() > 0) {
			for (CommonDialogVO commonDialog : commonDialog_list) {
				if (!"重复".equals(commonDialog.getYujin())) {
					String voliceId = commonDialog.getVoliceId() + "";
					if (!voliceid_list.contains(voliceId)) {
						voliceid_list.add(voliceId);

						List<VoliceInfoExt> extlist = getVoliceInfoExtList(processId, new Long(voliceId),
								commonDialog.getTitle());
						voliceInfo_list.addAll(extlist);
					}
				}
			}
		}

		// 查询其它
		VoliceInfoExample voliceInfoExample = new VoliceInfoExample();
		voliceInfoExample.createCriteria().andProcessIdEqualTo(processId);
		List<VoliceInfo> voliceList = voliceInfoMapper.selectByExample(voliceInfoExample);

		for (int i = voliceList.size() - 1; i >= 0; i--) {
			VoliceInfo temp = voliceList.get(i);
			if (voliceid_list.contains(temp.getVoliceId().toString())) {
				voliceList.remove(i);
			}
		}

		for (VoliceInfo volice : voliceList) {
			List<VoliceInfoExt> extlist = getVoliceInfoExtList(processId, volice.getVoliceId(), volice.getDomainName());
			voliceInfo_list.addAll(extlist);
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
					voliceInfo_list.add(vo);

					index++;
				}
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

	public List<String> uploadVoliceZip(String processId, InputStream inStream,Long userId) {
		List<String> voliceIds = voliceInfoExtMapper.queryAllVoliceId(processId);
		List<String> successUpdate = new ArrayList<String>();

		int successNum = 0;
		List<String> formatFileList = new ArrayList<>();
		List<String> sizeFileList = new ArrayList<>();
		List<String> notmatchFileList = new ArrayList<>();
		List<String> successFileList = new ArrayList<>();

		/*
		 * String formatFile = "文件:{format_file_name}格式不正确"; String sizeFile =
		 * "文件:{size_file_name}大小超过1M"; String notmatchFile =
		 * "文件:{notmatch_file_name}无匹配语音"; String successFile =
		 * "文件:{success_file_name}语音已上传成功";
		 */

		try {
			File file = File.createTempFile(String.valueOf(System.currentTimeMillis()), "zip");
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
						// listFile.remove(temp);
						// ZipEntry entry = (ZipEntry)entries.nextElement();
						InputStream entryInStream = new FileInputStream(temp);
						String name = temp.getName();

						boolean needTts = false;
						String seq = "";

						String[] arrays = name.split("\\.");
						String voliceId = arrays[0];
						if (voliceId.indexOf("_") > 0) {// 表示是需要TTS合成的话术
							seq = voliceId;
							voliceId = voliceId.split("_")[0];
							needTts = true;
						}

						String suffix = name.substring(name.lastIndexOf(".") + 1);

						if (!"wav".equals(suffix)) {
							formatFileList.add(name);
							entryInStream.close();
							continue;
						}

						long size = temp.length();
						if (size > 1 * 1024 * 1024) {
							sizeFileList.add(name);
							entryInStream.close();
							continue;
							// throw new CommonException("序号"+voliceId+"文件大小超过1M,请您压缩后重新上传");
						}

						if (voliceIds.contains(voliceId)) {
							String url = qiuniuUploadUtil.upload(entryInStream, null);

							if (needTts) {
								// 更新tts任务表的url
								BotSentenceTtsTaskExample ttsTaskExample = new BotSentenceTtsTaskExample();
								ttsTaskExample.createCriteria().andProcessIdEqualTo(processId)
										.andBusiIdEqualTo(voliceId).andSeqEqualTo(seq);
								List<BotSentenceTtsTask> list = botSentenceTtsTaskMapper
										.selectByExample(ttsTaskExample);
								if (null != list && list.size() > 0) {
									BotSentenceTtsTask task = list.get(0);
									task.setVoliceUrl(url);
									task.setLstUpdateTime(new Date(System.currentTimeMillis()));
									task.setLstUpdateUser(userId.toString());
									botSentenceTtsTaskMapper.updateByPrimaryKey(task);
								}

							} else {
								VoliceInfo volice = voliceInfoMapper.selectByPrimaryKey(new Long(voliceId));

								if (!"【新增】".equals(volice.getFlag())) {
									voliceInfoExtMapper.updateVoliceUrlAndNameById(voliceId, url, "【修改】");
								} else {
									// 插入数据库
									voliceInfoExtMapper.updateVoliceUrlById(voliceId, url);
								}
							}
							successNum++;
							successFileList.add(name);
						} else {
							notmatchFileList.add(name);
						}
						entryInStream.close();
					}
				}
			}

			zipFile.close();
			FileUtils.deleteDirectory(descFile);
			// 更新流程状态
			botSentenceProcessService.updateProcessState(processId,userId);

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
	public boolean uploadVoliceJsonZip(File dir, String fileName, String processId, String templateId) {
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

			// 加密
			fileCrypter(dir);
			// 打成zip包

			File standardZip = null;
			String zipFileName = fileName;
			// 打包成规定的格式
			try {
				File zipFile;
				zipFileName = zipFileName.replaceAll("_en", "");
				fileName = templateId.replaceAll("_en", "");
				zipFile = File.createTempFile(fileName, ".zip");
				this.zip(dir, zipFile.getPath());
				standardZip = creatStandardZipStructure(zipFile, zipFileName, templateId);
			} catch (Exception e) {
				logger.error("压缩打包json文件异常>..", e);
				return false;
			}

			if (standardZip != null) {
				// 本地保存zip文件
				File localZipfile = new File(localUploadDir + zipFileName + ".zip");
				try {
					Files.copy(standardZip, localZipfile);

					// 调用python工具
					String os = System.getProperties().getProperty("os.name").toLowerCase();
					if (os.startsWith("win")) {
						logger.info("windows暂不支持");
					} else {
						String command = " python /usr/local/botsentence/check_client_pkg/check_client/polaris_console.py";
						try {
							Process process = Runtime.getRuntime().exec(command);

							LineNumberReader br = new LineNumberReader(new InputStreamReader(process.getInputStream()));
							StringBuffer sb = new StringBuffer();
							String line;
							while ((line = br.readLine()) != null) {
								sb.append(line).append("\n");
							}
							logger.info("执行python命令结果....");
							logger.info(sb.toString());

							if (sb.toString().contains("add")) {
								logger.info("执行python脚本成功...");
							} else {
								logger.info("执行python脚本失败...");
								return false;
							}

						} catch (IOException e) {
							logger.error("执行python工具异常...", e);
							return false;
						}
					}

					// 删除本地临时文件
					// localZipfile.delete();

				} catch (IOException e) {
					logger.error("复制压缩zip包数据异常...", e);
					return false;
				} catch (Exception e) {
					logger.error("调用agent异常...", e);
					return false;
				}

				logger.info("待上传文件standardZip大小: " + standardZip.length());
				logger.info("待上传文件localZipfile大小: " + localZipfile.length());
				
				
				// 上传
				SysFileReqVO sysFileReqVO=new SysFileReqVO();
				sysFileReqVO.setBusiId(processId);
				sysFileReqVO.setSysCode(NAS_UPLAOD_SYSTEM_CODE);
				SysFileRspVO sysFileRspVO=nasUtile.uploadNas(sysFileReqVO, localZipfile);
				String uplaodFileName=sysFileRspVO.getFileName();
				//部署
				UpgrateResouceReq resouceReq=new UpgrateResouceReq();
				resouceReq.setFile(uplaodFileName);
				
				resouceReq.setProcessTypeEnum(ProcessTypeEnum.TTS);
				ReturnData<Boolean> result=iProcessSchedule.publishResource(resouceReq);
				return result.getBody();
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
			if (fileName.endsWith("json")) {
				System.out.println(fileName);
				JsonBase64Crypter.encodeFile(file);
			}
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
			pyFileInput = new FileInputStream(new File("/apps/cfgs/up_cfgs.py"));
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
	public void saveRefuseVolice(String processId, List<RefuseVoliceVO> contents,Long userId) {
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
						volice.setLstUpdateUser(userId.toString());
						volice.setName(content.getName());
						// voliceInfoMapper.updateByPrimaryKey(volice);
						this.saveVoliceInfo(volice,userId);
					} else {

						VoliceInfo volice = new VoliceInfo();
						volice.setType(Constant.VOLICE_TYPE_REFUSE);// 挽回话术
						volice.setContent(content.getContent().replace("\n", "").trim());
						volice.setProcessId(processId);
						volice.setTemplateId(process.getTemplateId());
						volice.setName(content.getName());
						volice.setFlag("【新增】");
						// voliceInfoMapper.insert(volice);
						this.saveVoliceInfo(volice,userId);
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
			voliceInfoMapper.deleteByPrimaryKey(new Long(voliceId));

			// 删除话术流程对当前挽回话术的依赖关系
			botSentenceProcessService.deleteRefuseBranch(processId, domainName, voliceId);

		} else {
			throw new CommonException("删除失败,请求参数不完整!");
		}

	}

	public static void main(String[] args) {
		/*
		 * List<String> formatFileList = new ArrayList<>();
		 * 
		 * List<String> list1 = new ArrayList<>(); list1.add("11"); list1.add("22");
		 * list1.add("33"); list1.add("44"); list1.add("11"); list1.add("55");
		 * list1.add("11"); for (int i = list1.size() - 1; i >= 0; i--) { String str =
		 * list1.get(i); if (str.equals("11")) { list1.remove(str); } }
		 */

		String aa = "$0001$0001我们的公司地址是长江西路拓基城市广场金座B3楼$0001";
		String[] array = aa.trim().split("\\$");
		//System.out.println(aa.length()-5);
		//System.out.println(aa.indexOf("$0001"));
		
		String regEx = Constant.TTS_REG_EX;// 正则表达式
		// 获取变量列表
		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher = pattern.matcher(aa);
		//int num = matcher.end();
		System.out.println(aa.length()-5);
		System.out.println(aa.indexOf("$0001"));
		System.out.println(aa.lastIndexOf("$0001"));
		/*String bb = "afdasasdf$我们的公司地$址是长江西路拓基城市广场金座B3楼33311111111$ ";
		String[] array2 = bb.trim().split("\\$");
		System.out.println(array2);*/
		
		
		
		//校验两个变量是否挨着
		String regEx2 = "\\$[0-9]{4}\\$[0-9]{4}";// 正则表达式
		// 获取变量列表
		Pattern pattern2 = Pattern.compile(regEx2);
		Matcher matcher2 = pattern2.matcher(aa);
		System.out.println(matcher2.find());
		if(matcher2.find()) {
			throw new CommonException("两个tts变量中间必须有文案!");
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
		}
		return voliceInfo_list;
	}

	@Override
	public String uploadOneVolice(String processId, String voliceId, InputStream inStream, String type,Long userId) {
		BotSentenceProcess process = botSentenceProcessMapper.selectByPrimaryKey(processId);
		String keyName = "upload_by_template_gui/"
				+ DateUtil.yyyyMMddHHmmss2.format(new Date(System.currentTimeMillis())) + "_" + process.getTemplateId()
				+ ".wav";
		String voliceUrl = qiuniuUploadUtil.upload(inStream, keyName);
		if (Constant.VOLICE_TYPE_BACKUP.equals(type) || Constant.VOLICE_TYPE_TTS.equals(type)) {
			String voliceId2 = voliceId.split("_")[0];
			// 更新tts任务表的url
			BotSentenceTtsTaskExample ttsTaskExample = new BotSentenceTtsTaskExample();
			ttsTaskExample.createCriteria().andProcessIdEqualTo(processId).andBusiIdEqualTo(voliceId2)
					.andSeqEqualTo(voliceId);
			List<BotSentenceTtsTask> list = botSentenceTtsTaskMapper.selectByExample(ttsTaskExample);
			if (null != list && list.size() > 0) {
				BotSentenceTtsTask task = list.get(0);
				task.setVoliceUrl(voliceUrl);
				task.setLstUpdateTime(new Date(System.currentTimeMillis()));
				task.setLstUpdateUser(userId.toString());
				botSentenceTtsTaskMapper.updateByPrimaryKey(task);
			}
		} else {
			VoliceInfo volice = this.getVoliceInfo(new Long(voliceId));
			this.updateVoliceInfo(new Long(voliceId), volice.getType(), voliceUrl,userId);
		}
		return voliceUrl;
	}
	
	private void splitContentAndSave(VoliceInfo voliceInfo, String newContent,Long userId) {
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
		// 判断是否变量是第一个
		boolean isStart = false;
		boolean isEnd = false;
		for (int i = 0; i < paramList.size(); i++) {
			int index = newContent.indexOf(paramList.get(i));
			if (index == 0) {
				isStart = true;
				break;
			}
			
			int endIndex = newContent.lastIndexOf(paramList.get(i));
			if(endIndex == (newContent.length() - 5)) {
				isEnd = true;
				break;
			}
		}
		if(isStart) {//如果变量在开头，则不允许保存
			throw new CommonException("tts变量不允许出现在文案开头!");
		}
		
		if(isEnd) {//如果变量在结尾，则不允许保存
			throw new CommonException("tts变量不允许出现在文案结尾!");
		}

		
		//校验两个变量是否挨着
		String regEx2 = "\\$[0-9]{4}\\$[0-9]{4}";// 正则表达式
		// 获取变量列表
		Pattern pattern2 = Pattern.compile(regEx2);
		Matcher matcher2 = pattern2.matcher(newContent);
		
		if(matcher2.find()) {
			throw new CommonException("两个tts变量中间必须有文案!");
		}
		
		
		String[] array = voliceInfo.getContent().split(regEx);
		List<String> contentList = new ArrayList<>();

		// 使用变量切割成的文案列表
		if (null != array && array.length > 0) {
			for (int i = 0; i < array.length; i++) {
				if (StringUtils.isNotBlank(array[i]) && !"。".equals(array[i]) && !".".equals(array[i])
						&& !"，".equals(array[i]) && !",".equals(array[i]) && !"！".equals(array[i])
						&& !"!".equals(array[i]) && !"？".equals(array[i]) && !"?".equals(array[i])) {
					contentList.add(array[i]);
				}
			}
		}

		
		if (isStart) {
			for (int i = 0; i < paramList.size(); i++) {
				String seq = voliceInfo.getVoliceId() + "_" + ((i + 1) * 2 - 1);
				BotSentenceTtsTask ttsTask = new BotSentenceTtsTask();
				ttsTask.setBusiId(voliceInfo.getVoliceId().toString());
				ttsTask.setBusiType(Constant.TTS_BUSI_TYPE_01);
				ttsTask.setContent(paramList.get(i));
				ttsTask.setSeq(seq);
				ttsTask.setIsParam(Constant.IS_PARAM_TRUE);
				botSentenceTtsService.saveTTSTask(ttsTask, voliceInfo.getProcessId(),userId);
			}

			for (int i = 0; i < contentList.size(); i++) {
				String seq = voliceInfo.getVoliceId() + "_" + ((i + 1) * 2);
				BotSentenceTtsTask ttsTask = new BotSentenceTtsTask();
				ttsTask.setBusiId(voliceInfo.getVoliceId().toString());
				ttsTask.setBusiType(Constant.TTS_BUSI_TYPE_01);
				ttsTask.setContent(contentList.get(i).trim());
				ttsTask.setSeq(seq);
				ttsTask.setIsParam(Constant.IS_PARAM_FALSE);
				botSentenceTtsService.saveTTSTask(ttsTask, voliceInfo.getProcessId(),userId);
			}
		} else {
			// 如果第一个是文案，则从文案开始为第1个，变量从第2开始依次排序
			for (int i = 0; i < contentList.size(); i++) {
				String seq = voliceInfo.getVoliceId() + "_" + ((i + 1) * 2 - 1);
				BotSentenceTtsTask ttsTask = new BotSentenceTtsTask();
				ttsTask.setBusiId(voliceInfo.getVoliceId().toString());
				ttsTask.setBusiType(Constant.TTS_BUSI_TYPE_01);
				ttsTask.setContent(contentList.get(i).trim());
				ttsTask.setSeq(seq);
				ttsTask.setIsParam(Constant.IS_PARAM_FALSE);
				botSentenceTtsService.saveTTSTask(ttsTask, voliceInfo.getProcessId(),userId);
			}
			for (int i = 0; i < paramList.size(); i++) {
				String seq = voliceInfo.getVoliceId() + "_" + (i + 1) * 2;
				BotSentenceTtsTask ttsTask = new BotSentenceTtsTask();
				ttsTask.setBusiId(voliceInfo.getVoliceId().toString());
				ttsTask.setBusiType(Constant.TTS_BUSI_TYPE_01);
				ttsTask.setContent(paramList.get(i));
				ttsTask.setSeq(seq);
				ttsTask.setIsParam(Constant.IS_PARAM_TRUE);
				botSentenceTtsService.saveTTSTask(ttsTask, voliceInfo.getProcessId(),userId);
			}
		}
	}

}
