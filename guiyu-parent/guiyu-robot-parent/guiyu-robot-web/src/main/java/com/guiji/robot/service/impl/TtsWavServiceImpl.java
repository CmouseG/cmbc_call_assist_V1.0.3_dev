package com.guiji.robot.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.guiji.ai.api.ITts;
import com.guiji.ai.vo.TtsReqVO;
import com.guiji.ai.vo.TtsRspVO;
import com.guiji.common.model.SysFileReqVO;
import com.guiji.common.model.SysFileRspVO;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.robot.constants.RobotConstants;
import com.guiji.robot.dao.TtsCallbackHisMapper;
import com.guiji.robot.dao.TtsWavHisMapper;
import com.guiji.robot.dao.entity.TtsCallbackHis;
import com.guiji.robot.dao.entity.TtsCallbackHisExample;
import com.guiji.robot.dao.entity.TtsWavHis;
import com.guiji.robot.dao.entity.TtsWavHisExample;
import com.guiji.robot.exception.AiErrorEnum;
import com.guiji.robot.exception.RobotException;
import com.guiji.robot.model.HsParam;
import com.guiji.robot.model.TtsCallback;
import com.guiji.robot.model.TtsVoice;
import com.guiji.robot.model.TtsVoiceReq;
import com.guiji.robot.service.ITtsWavService;
import com.guiji.robot.service.vo.HsReplace;
import com.guiji.robot.service.vo.TtsTempData;
import com.guiji.robot.util.ListUtil;
import com.guiji.robot.util.SystemUtil;
import com.guiji.robot.util.WavMergeUtil;
import com.guiji.utils.BeanUtil;
import com.guiji.utils.JsonUtils;
import com.guiji.utils.NasUtil;
import com.guiji.utils.NetFileDownUtil;
import com.guiji.utils.StrUtils;

/** 
* @ClassName: TtsWavServiceImpl 
* @Description: TTS语音合成服务
* @date 2018年11月20日 下午1:24:05 
* @version V1.0  
*/
@Service
public class TtsWavServiceImpl implements ITtsWavService{
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	TtsWavHisMapper ttsWavHisMapper;
	@Autowired
	ITts iTts;
	@Autowired
	AiAsynDealService aiAsynDealService;
	@Autowired
	AiCacheService aiCacheService;
	@Autowired
	AiNewTransService aiNewTransService;
	@Autowired
	TtsCallbackHisMapper ttsCallbackHisMapper;
	@Value("${file.tmpPath:apps/tmp/}")
    private String tempFilePath;	//文件临时目录
	@Value("${file.hushuDir:home/botstence_tmpl/}")
    private String hushuDir;	//话术模板存放目录
	
	
	/**
	 * 保存或者更新一TTS合成信息
	 * 同时记录历史
	 * @param ttsWavHis
	 * @return
	 */
	@Transactional
	@Override
	public TtsWavHis saveOrUpdate(TtsWavHis ttsWavHis) {
		if(ttsWavHis != null) {
			if(StrUtils.isEmpty(ttsWavHis.getId())) {
				//如果主键为空，那么新增一条信息
				ttsWavHis.setCrtTime(new Date());
				ttsWavHisMapper.insert(ttsWavHis);
			}else {
				//主键不为空，更新信息
				ttsWavHisMapper.updateByPrimaryKey(ttsWavHis);
			}
		}
		return ttsWavHis;
	}
	
	
	/**
	 * 根据业务id查询tts合成情况
	 * @param busiId
	 * @return
	 */
	@Override
	public TtsWavHis queryTtsWavByBusiId(String busiId) {
		if(StrUtils.isNotEmpty(busiId)) {
			TtsWavHisExample example = new TtsWavHisExample();
			example.createCriteria().andBusiIdEqualTo(busiId);
			example.setOrderByClause(" crt_time desc");
			List<TtsWavHis> list = ttsWavHisMapper.selectByExampleWithBLOBs(example);
			if(ListUtil.isNotEmpty(list)) {
				return list.get(0);
			}
		}
		return null;
	}
	
	
	/**
	 * 根据业务ID查询TTS回调历史
	 * @param busiId
	 * @return
	 */
	@Override
	public TtsCallbackHis queryTtsCallbackHisByBusiId(String busiId) {
		if(StrUtils.isNotEmpty(busiId)) {
			TtsCallbackHisExample example = new TtsCallbackHisExample();
			example.createCriteria().andBusiIdEqualTo(busiId);
			example.setOrderByClause(" crt_time desc");
			List<TtsCallbackHis> list = ttsCallbackHisMapper.selectByExampleWithBLOBs(example);
			if(ListUtil.isNotEmpty(list)) {
				return list.get(0);
			}
		}
		return null;
	}
	
	/**
	 * 根据条件查询TTS已合成的语音数据
	 * @param seqId
	 * @return
	 */
	@Override
	public TtsWavHis queryTtsWavBySeqId(String seqId) {
		if(StrUtils.isNotEmpty(seqId)) {
			TtsWavHisExample example = new TtsWavHisExample();
			example.createCriteria().andSeqIdEqualTo(seqId);
			example.setOrderByClause(" crt_time desc");
			//selectByExampleWithBLOBs ，因为有大字段，所以要使用withBlobs
			List<TtsWavHis> list = ttsWavHisMapper.selectByExampleWithBLOBs(example);
			if(ListUtil.isNotEmpty(list)) {
				//如果只有一条数据，直接返回，如果有多条，那么检查是否有完成的状态，有完成的状态，返回完成的数据，表示调用了多次tts合成
				if(list.size()>1) {
					for(TtsWavHis ttsWav : list) {
						if(RobotConstants.TTS_STATUS_S.equals(ttsWav.getStatus())){
							return ttsWav;
						}
					}
				}
				return list.get(0);
			}
		}
		return null;
	}
	
	
	/**
	 * 异步TTS合成操作
	 * @param ttsVoiceReq
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@Async
	public void asynTtsCompose(HsParam hsChecker) {
		TtsVoiceReq ttsVoiceReq = new TtsVoiceReq();
		BeanUtil.copyProperties(hsChecker, ttsVoiceReq);
		//1、TTS合成记录，初始-合成中状态
		TtsWavHis ttsWavHis = new TtsWavHis();
		ttsWavHis.setSeqId(hsChecker.getSeqid());
		ttsWavHis.setTemplateId(hsChecker.getTemplateId());
		ttsWavHis.setStatus(RobotConstants.TTS_STATUS_P); //合成中
		try {
			//2、异步调用tts工具合成
			this.ttsCompose(ttsWavHis,ttsVoiceReq);
		} catch (Exception e) {
			logger.error("TTS合成失败！模板ID:{},会话ID:{}",hsChecker.getTemplateId(),hsChecker.getSeqid(),e);
			//4、合成后更新为合成状态
			ttsWavHis.setStatus(RobotConstants.TTS_STATUS_F); //合成失败
			ttsWavHis.setErrorType(RobotConstants.TTS_ERROR_TYPE_P); //调用接口失败
			String errorMsg = e.getMessage();
			if(StrUtils.isNotEmpty(errorMsg)) {
				if(errorMsg.length()>1024) {
					errorMsg = errorMsg.substring(0, 1024);
				}
				ttsWavHis.setErrorMsg(errorMsg);
			}else {
				ttsWavHis.setErrorMsg("TTS合成失败,发生异常...");
			}
		}
		//独立事务保存
		ttsWavHis = aiNewTransService.recordTtsWav(ttsWavHis);
	}
	
	
	/**
	 * TTS语音合成
	 * 1、必输校验
	 * 2、根据话术模板读取本地json文件，解析json文件获取话术模板
	 * 3、校验参数是否有缺失
	 * 4、合成完整语句，并调用TTS工具服务，生成语音文件
	 * @param  ttsVoiceReq
	 * @return 合成后的语音列表
	 */
	@Transactional
	@Override
	public void ttsCompose(TtsWavHis ttsWavHis,TtsVoiceReq ttsVoiceReq){
		//1、必输校验
		if(ttsVoiceReq == null 
				|| StrUtils.isEmpty(ttsVoiceReq.getSeqid())
				|| StrUtils.isEmpty(ttsVoiceReq.getTemplateId())
				|| ttsVoiceReq.getParamMap()==null
				|| ttsVoiceReq.getParamMap().isEmpty()) {
			throw new RobotException(AiErrorEnum.AI00060001.getErrorCode(),AiErrorEnum.AI00060001.getErrorMsg());
		}
		//2、根据话术模板读取本地json文件
		//获取话术模板配置文件
		HsReplace hsReplace = aiCacheService.queyHsReplace(ttsVoiceReq.getTemplateId());
		if(!hsReplace.isTemplate_tts_flag()) {
			//如果不需要TTS合成,直接返回null
			throw new RobotException(AiErrorEnum.AI00060030.getErrorCode(),AiErrorEnum.AI00060030.getErrorMsg());
		}
		//3、校验参数是否有缺失
		if(!this.checkTtsParams(hsReplace, ttsVoiceReq)) {
			logger.error("TTS参数校验失败，抛出异常！");
			throw new RobotException(AiErrorEnum.AI00060009.getErrorCode(),AiErrorEnum.AI00060009.getErrorMsg());
		}
		//4、调用TTS接口，发送要合成的语音文本，并返回模板中对应的 key-文本内容，落地
		String busiId = com.guiji.utils.SystemUtil.getBusiSerialNo("TTS"); //调用TTS工具的唯一编号
		Map<String,String> ttsTempTxtMap = this.fillParamAndTranslate(busiId,hsReplace, ttsVoiceReq);
		//5、保存请求文本信息
		String jsonTxtStr = JSON.toJSONString(ttsTempTxtMap);	//key-要合成的txt文本 json
		ttsWavHis.setBusiId(busiId);
		ttsWavHis.setTtsTxtJsonData(jsonTxtStr);
	}
	
	
	/**
	 * 校验参数是否有缺失
	 * @return
	 */
	private boolean checkTtsParams(HsReplace hsReplace,TtsVoiceReq ttsVoiceReq){
		//本模板需要替代的变量
		String[] replaceVariables = hsReplace.getReplace_variables_flag();
		if(replaceVariables != null && replaceVariables.length>0) {
			for(String variable : replaceVariables) {
				//逐个参数校验
				String paramValue = ttsVoiceReq.getParamMap().get(variable);
				if(StrUtils.isEmpty(paramValue)) {
					logger.error("模板{}的参数{}不存在本次申请中，TTS合成参数校验失败！",ttsVoiceReq.getTemplateId(),variable);
					return false;
				}
			}
		}
		return true;
	}
	
	
	
	/**
	 * 异步调用TTS合成服务，完成后回调通知
	 * TTS合成后的回调服务
	 * @param ttsCallbackHisList
	 */
	@Transactional
	@Async
	public void asynTtsCallback(List<TtsCallback> ttsCallbackList) {
		String tmpFilePath = SystemUtil.getRootPath()+tempFilePath; //临时文件存放目录
		String hushuDirPath = SystemUtil.getRootPath()+hushuDir; //话术模板存放目录
		if(ListUtil.isNotEmpty(ttsCallbackList)) {
			for(TtsCallback ttsCallback : ttsCallbackList) {
				String busiId = ttsCallback.getBusiId();	//业务ID
				TtsWavHis ttsWavHis = null;
				if(StrUtils.isNotEmpty(busiId)) {
					ttsWavHis = this.queryTtsWavByBusiId(busiId);
					if(ttsWavHis != null) {
						if(RobotConstants.TTS_STATUS_S.equals(ttsCallback.getStatus())) {
							//**开始从callback回调的数据，拼接wav文件，完成TTS合成**//
							try {
								//获取话术模板配置文件
								HsReplace hsReplace = aiCacheService.queyHsReplace(ttsWavHis.getTemplateId());
								//获取到key-tts合成的txt文本
								Map<String,String> ttsTextMap = JsonUtils.json2Bean(ttsWavHis.getTtsTxtJsonData(), Map.class);
								//下载tts合成的wav文件到本地，返回本地路径
								Map<String,TtsTempData> ttsTempDataMap = this.ttsWavDownload(ttsWavHis.getTemplateId(),tmpFilePath, hsReplace, ttsCallback.getAudios(), ttsTextMap);
								//合成语音，生成.wav文件，并上传文件服务器
								List<TtsVoice> ttsVoiceList = this.wavMerge(hushuDirPath, tmpFilePath, ttsTempDataMap, hsReplace, ttsWavHis.getTemplateId());
								//3、合成后更新为合成状态
								if(ListUtil.isNotEmpty(ttsVoiceList)) {
									//转json保存
									String jsonStr = JSON.toJSONString(ttsVoiceList);
									ttsWavHis.setTtsJsonData(jsonStr);
									ttsWavHis.setStatus(RobotConstants.TTS_STATUS_S); //完成
									logger.info("会话:{}TTS合成完毕...",ttsWavHis.getSeqId());
								}else {
									logger.error("TTS合成失败，无合成文件,{}",ttsWavHis);
									throw new RobotException(AiErrorEnum.AI00060019.getErrorCode(),AiErrorEnum.AI00060019.getErrorMsg());
								}
							}catch (Exception e) {
								ttsWavHis.setStatus(RobotConstants.TTS_STATUS_F); //合成失败
								ttsWavHis.setErrorType(RobotConstants.TTS_ERROR_TYPE_L); //回调后本地处理失败
								ttsWavHis.setErrorTryNum((ttsWavHis.getErrorTryNum()==null?0:ttsWavHis.getErrorTryNum())+1);  //失败次数
								String errorMsg = e.getMessage();
								if(StrUtils.isNotEmpty(errorMsg)) {
									if(errorMsg.length()>1024) {
										errorMsg = errorMsg.substring(0, 1024);
									}
									ttsWavHis.setErrorMsg(errorMsg);
								}else {
									ttsWavHis.setErrorMsg("TTS合成失败,发生异常...");
								}
								logger.error("TTS回调处理本地WAV文件合成失败",e);
							}finally {
								//独立事务保存
								aiNewTransService.recordTtsWav(ttsWavHis);
							}
						}else {
							logger.error("TTS AI服务返回合成失败..");
							ttsWavHis.setStatus(RobotConstants.TTS_STATUS_F); //合成失败
							ttsWavHis.setErrorType(RobotConstants.TTS_ERROR_TYPE_T); //TTS接口本身返回失败状态
							ttsWavHis.setErrorMsg("TTS服务回调状态失败");
							//独立事务保存
							aiNewTransService.recordTtsWav(ttsWavHis);
						}
						
					}else {
						logger.error("根据busiId:{}本地无数据..",busiId);
					}
				}else {
					logger.error("busiId不能为空..");
				}
			}
		}
	}
	
	
	
	/**
	 * 填充参数并调用TTS工具合成
	 * @param busiId :业务唯一id
	 * @param tmpFilePath 临时文件目录
	 * @param hsReplace replate.json文件
	 * @param ttsVoiceReq 请求信息
	 */
	public Map<String,String> fillParamAndTranslate(String busiId,HsReplace hsReplace,TtsVoiceReq ttsVoiceReq){
		//调用TTS语音合成服务，合成语音
		Map<String,String> ttsPos = hsReplace.getTts_pos(); //需要转语音的文本
		List<String> contents = new ArrayList<String>();	//参数替换后的文本
		Map<String,String> ttsTempDataMap = new HashMap<String,String>();   //key-文本 ，如：<8_1,"你好，请问您是张三吗">
		for (Map.Entry<String,String> ttsPosEntry : ttsPos.entrySet()) {
			TtsTempData data = new TtsTempData();
			data.setTemplateId(ttsVoiceReq.getTemplateId()); //话术模板
			data.setTtsPosKey(ttsPosEntry.getKey());
			String content = ttsPosEntry.getValue();	//替换参数前文本
			for(String param : hsReplace.getReplace_variables_flag()) {
				//将参数逐个替换，替换参数含 $特殊符号，使用Matcher.quoteReplacement消除字符的特殊含义
				content = content.replaceAll(Matcher.quoteReplacement(param), ttsVoiceReq.getParamMap().get(param));
			}
			if(content.contains("$")){
				logger.error("模板{}的参数{}参数替换失败！",ttsVoiceReq.getTemplateId());
				throw new RobotException(AiErrorEnum.AI00060010.getErrorCode(),AiErrorEnum.AI00060010.getErrorMsg());
			}
			data.setTtsContent(content);
			ttsTempDataMap.put(ttsPosEntry.getKey(), content);
			contents.add(content);
		}
		//遍历将生成的wav落地
		try {
			//数据准备后调用TTS工具批量生成语音
			TtsReqVO ttsReqVO = new TtsReqVO();
			ttsReqVO.setBusId(busiId);	//唯一key
			ttsReqVO.setModel(hsReplace.getUse_speaker_flag());
			ttsReqVO.setContents(contents);
			//调用TTS工具
			logger.info("开始调用TTS工具，请求参数:{}...",ttsReqVO);
			ReturnData<String> ttsRspData = iTts.translate(ttsReqVO);
			
			logger.info("完成TTS工具调用,返回参数:{}",ttsRspData);
			if(ttsRspData == null || !RobotConstants.RSP_CODE_SUCCESS.equals(ttsRspData.getCode())){
				logger.error("调用TTS工具生成语音失败，返回数据：{}"+ttsRspData);
				throw new RobotException(ttsRspData.getCode(),ttsRspData.getMsg());
			}
			if(!RobotConstants.TTS_RSP_SUCCESS.equals(ttsRspData.getBody())) {
				//如果异步方法返回直接失败，那么这条TTS合成记录抛出异常
				logger.error("调用TTS工具生成语音返回状态失败，返回数据：{}"+ttsRspData);
				throw new RobotException(AiErrorEnum.AI00060029.getErrorCode(),ttsRspData.getBody());
			}
		}catch (Exception e) {
			logger.error("调用TTS工具接口异常",e);
			throw new RobotException(AiErrorEnum.AI00060029.getErrorCode(),AiErrorEnum.AI00060029.getErrorMsg());
		}
		return ttsTempDataMap;
	}
	
	
	
	/**
	 * 填充参数并调用TTS工具合成
	 * @param templateId 模板id
	 * @param tmpFilePath 临时文件目录
	 * @param hsReplace replate.json文件
	 * @param ttsVoiceReq 请求信息
	 */
	public Map<String,TtsTempData> ttsWavDownload(String templateId,String tmpFilePath,HsReplace hsReplace,Map<String,String> ttsAudioMap,Map<String,String> ttsTxtMap){
		//遍历将生成的wav落地
		Map<String,TtsTempData> ttsTempDataMap = new HashMap<String,TtsTempData>();
		try {
			for (Map.Entry<String,String> ttsTxtEntry : ttsTxtMap.entrySet()) {
				String ttsPosKey = ttsTxtEntry.getKey(); //需要合成tts的某段文本对应的key
				String content = ttsTxtEntry.getValue(); //需要根据要转的文本和返回数据做匹配
				//重新设置下返回的data
				TtsTempData data = new TtsTempData();
				data.setTemplateId(templateId);  //模板
				data.setTtsPosKey(ttsPosKey);	//合成tts的key
				data.setTtsContent(content);	//合成tts的txt文本
				String url = ttsAudioMap.get(content);
				if(StrUtils.isEmpty(url)) {
					logger.error("文本{}调用TTS工具转语音缺失！",content);
					throw new RobotException(AiErrorEnum.AI00060011.getErrorCode(),AiErrorEnum.AI00060011.getErrorMsg());
				}
				data.setAudioFilePath(url);
				//开始调用本地服务，下载wav文件并落地
				String wavFilePath = tmpFilePath + com.guiji.utils.SystemUtil.getBusiSerialNo(ttsTxtEntry.getKey())+".wav";
				try {
					new NetFileDownUtil(url,new File(wavFilePath)).downfile();
				} catch (Exception e) {
					logger.error("调用TTS语音文件{}落地异常！",url);
					throw new RobotException(AiErrorEnum.AI00060012.getErrorCode(),AiErrorEnum.AI00060012.getErrorMsg());
				}
				data.setAudioFilePath(wavFilePath);
				ttsTempDataMap.put(ttsPosKey, data);
			}
		}catch (RobotException e) {
			//发生异常后，删除临时文件，不要再finally里清理资源是因为如果不报错是不需要再这里删除的，后续还会有合并需要该文件，报错就再这里直接删除掉临时文件
			//删除临时文件
			this.delTempFile(ttsTempDataMap);
			throw e;
		}catch (Exception e1) {
			logger.error("遍历生成wav文件落地异常",e1);
			//发生异常后，删除临时文件，不要再finally里清理资源是因为如果不报错是不需要再这里删除的，后续还会有合并需要该文件，报错就再这里直接删除掉临时文件
			//删除临时文件
			this.delTempFile(ttsTempDataMap);
			throw new RobotException(AiErrorEnum.AI00060012.getErrorCode(),AiErrorEnum.AI00060012.getErrorMsg());
		}
		return ttsTempDataMap;
	}
	
	
	/**
	 * 将几段语音合成一段.wav语音，并上传文件服务器
	 * @param hushuDirPath 话术模板目录
	 * @param tmpFilePath 临时文件目录
	 * @param ttsTempDataMap //TTS工具合成的临时文件
	 * @param hsReplace replace.json
	 */
	private List<TtsVoice> wavMerge(String hushuDirPath,String tmpFilePath,Map<String,TtsTempData> ttsTempDataMap,HsReplace hsReplace,String templateId){
		List<TtsVoice> ttsList = new ArrayList<TtsVoice>();
		//获取哪些语音需要合成
		Map<String,String[]> ttsMergeWavMap = hsReplace.getRec_tts_wav();
		List<String> ttsFilePathList = new ArrayList<String>();	//合成后的完整wav文件本地路径，后续用来删除本地路径
		try {
			for (Map.Entry<String,String[]> ttsMergeWavEntry : ttsMergeWavMap.entrySet()) {
				String ttsKey = ttsMergeWavEntry.getKey(); //合成后语音文件的key
				String[] splitWavFileKey = ttsMergeWavEntry.getValue();	//需要合成的几个语音文件key
				List<String> wavArr = new ArrayList<String>();
				for(String wavTempKey : splitWavFileKey) {
					if(ttsTempDataMap.get(wavTempKey)!=null) {
						//不为空，说明是调用TTS工具合成的语音
						wavArr.add(ttsTempDataMap.get(wavTempKey).getAudioFilePath()); //要合成的碎片wav文件路径
					}else {
						//为空，表示是模板自带的语音文件片段
						String filePath = this.getHsWavPath(hushuDirPath, templateId) + wavTempKey + ".wav";
						wavArr.add(filePath);
					}
				}
				//合成后的wav文件本地路径
				String ttsFilePath = tmpFilePath + com.guiji.utils.SystemUtil.getBusiSerialNo(templateId)+".wav";
				try {
					//合成语音
					WavMergeUtil.mergeWav(wavArr,ttsFilePath);
					ttsFilePathList.add(ttsFilePath);
				} catch (Exception e) {
					logger.error("WAV文件拼装异常!",e);
					throw new RobotException(AiErrorEnum.AI00060013.getErrorCode(),AiErrorEnum.AI00060013.getErrorMsg());
				}
				//上传文件服务器
				SysFileReqVO sysFileReqVO = new SysFileReqVO();
				sysFileReqVO.setBusiType("TEMP");	//临时文件 
				sysFileReqVO.setSysCode("ROBOT");	//机器人能力中心
				SysFileRspVO sysFileRspVO = new NasUtil().uploadNas(sysFileReqVO, new File(ttsFilePath));
				if(sysFileRspVO != null) {
					TtsVoice tts = new TtsVoice();
					tts.setTtsKey(ttsKey);
					tts.setTtsUrl(sysFileRspVO.getSkUrl());
					ttsList.add(tts);
				}else {
					logger.error("上传NAS服务器失败，返回文件url为空!");
					throw new RobotException(AiErrorEnum.AI00060014.getErrorCode(),AiErrorEnum.AI00060014.getErrorMsg());
				}
			}
		} catch (RobotException e) {
			throw e;
		}catch (Exception e1) {
			logger.error("合成WAV并上传NAS服务器发生未知异常",e1);
			throw new RobotException(AiErrorEnum.AI00060015.getErrorCode(),AiErrorEnum.AI00060015.getErrorMsg());
		}finally {
			//删除临时文件
			this.delTempFile(ttsTempDataMap);
			//删除合成后的wav文件
			if(ListUtil.isNotEmpty(ttsFilePathList)) {
				for(String tempTtsFilePath : ttsFilePathList) {
					File tempFile = new File(tempTtsFilePath);
					if(tempFile!=null && tempFile.exists()) tempFile.delete();
				}
			}
		}
		return ttsList;
	}
	
	
	/**
	 * 删除临时文件
	 * @param ttsTempDataMap
	 */
	private void delTempFile(Map<String,TtsTempData> ttsTempDataMap) {
		if(ttsTempDataMap != null) {
			//删除临时文件
			for (Map.Entry<String,TtsTempData> ttsTempDataEntry : ttsTempDataMap.entrySet()) {
				TtsTempData tempData = ttsTempDataEntry.getValue();
				String splitFilePath = tempData.getAudioFilePath(); //临时文件本地路径
				if(StrUtils.isNotEmpty(splitFilePath)) {
					File tempFile = new File(splitFilePath);
					if(tempFile!=null && tempFile.exists()) tempFile.delete();
				}
			}
		}
	}
	
	
	/**
	 * 获取话术模板的语音文件目录
	 * @param hushuDirPath
	 * @param ttsVoiceReq
	 * @return
	 */
	private String getHsWavPath(String hushuDirPath,String templateCode) {
		//模板名称去掉en，然后再加上rec
		String wavDirName = (templateCode.indexOf("_en")>0?templateCode.substring(0,templateCode.length()-2):templateCode) + "rec/";
		return hushuDirPath + templateCode + "/" + wavDirName;
	}
}
