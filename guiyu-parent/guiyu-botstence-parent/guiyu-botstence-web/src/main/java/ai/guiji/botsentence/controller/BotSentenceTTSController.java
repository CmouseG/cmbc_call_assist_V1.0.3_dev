package ai.guiji.botsentence.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.guiji.botsentence.service.IBotSentenceTtsService;
import ai.guiji.botsentence.service.impl.BotSentenceTtsServiceImpl;
import ai.guiji.botsentence.vo.FlowInfoVO;
import ai.guiji.botsentence.vo.TtsBackup;
import ai.guiji.botsentence.vo.TtsBackupVO;
import ai.guiji.botsentence.vo.TtsParam;
import ai.guiji.botsentence.vo.TtsParamVO;
import ai.guiji.component.client.config.JsonParam;
import ai.guiji.component.model.ServerResult;

/**
 * 
 * @Description:tts制作相关类
 * @author zhangpeng
 * @date 2018年10月30日 
 *
 */
@RestController
@RequestMapping(value="tts")
public class BotSentenceTTSController {

	@Autowired
	private IBotSentenceTtsService botSentenceTtsService;
	
	@RequestMapping(value="saveTtsParam")
	public ServerResult saveTtsParam(@JsonParam TtsParamVO param,@RequestHeader Long userId) {
		
		if(null == param) {
			return ServerResult.createByErrorMessage("参数为空");
		}
		
		if(StringUtils.isBlank(param.getProcessId())) {
			return ServerResult.createByErrorMessage("话术流程编号为空");
		}
		
		if(null == param.getParams() || param.getParams().size() < 1) {
			return ServerResult.createByErrorMessage("参数列表为空");
		}
		
		botSentenceTtsService.saveTtsParam(param,userId);
		
		return ServerResult.createBySuccess();
	}
	
	
	@RequestMapping(value="saveTtsBackup")
	public ServerResult saveTtsBackup(@JsonParam TtsBackupVO param,@RequestHeader Long userId) {
		
		if(null == param) {
			return ServerResult.createByErrorMessage("参数为空");
		}
		
		if(StringUtils.isBlank(param.getProcessId())) {
			return ServerResult.createByErrorMessage("话术流程编号为空");
		}
		
		if(null == param.getBackups() || param.getBackups().size() < 1) {
			return ServerResult.createByErrorMessage("参数列表为空");
		}
		
		botSentenceTtsService.saveTtsBackup(param,userId);
		
		return ServerResult.createBySuccess();
	}
	
	@RequestMapping(value="queryTtsParamList")
	public ServerResult<List<TtsParam>> queryTtsParamList(@JsonParam String processId) {
		if(StringUtils.isBlank(processId)) {
			return ServerResult.createByErrorMessage("话术流程编号为空");
		}
		
		List<TtsParam> list = botSentenceTtsService.queryTtsParamList(processId);
		
		return ServerResult.createBySuccess(list);
	}
	
	@RequestMapping(value="queryTtsBackupList")
	public ServerResult<List<TtsBackup>> queryTtsBackupList(@JsonParam String processId) {
		if(StringUtils.isBlank(processId)) {
			return ServerResult.createByErrorMessage("话术流程编号为空");
		}
		
		List<TtsBackup> list = botSentenceTtsService.queryTtsBackupList(processId);
		
		return ServerResult.createBySuccess(list);
	}
	
	/**
	 * 生成TTS合成录音
	 * @param
	 * @return
	 */
	@RequestMapping(value="generateTTS")
	public ServerResult<FlowInfoVO> generateTTS(@JsonParam String processId,@RequestHeader Long userId){
		botSentenceTtsService.generateTTS(processId,userId);
		return ServerResult.createBySuccess();
	}
	
}
