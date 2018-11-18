package ai.guiji.botsentence.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import ai.guiji.botsentence.dao.BotSentenceLabelMapper;
import ai.guiji.botsentence.dao.entity.BotSentenceLabel;
import ai.guiji.botsentence.dao.entity.MyBotLabel;
import ai.guiji.botsentence.dao.entity.MyBotLabelScore;
import ai.guiji.botsentence.service.IBotSentenceLabelService;
import ai.guiji.botsentence.service.impl.BotSentenceProcessServiceImpl;
import ai.guiji.botsentence.util.BotSentenceUtil;
import ai.guiji.component.client.config.JsonParam;
import ai.guiji.component.client.util.BeanUtil;
import ai.guiji.component.exception.CommonException;
import ai.guiji.component.model.ServerResult;

/**
 * 意向标签
 * 
 * @Description:
 * @author liyang
 * @date 2018年8月27日
 *
 */
@RestController
@RequestMapping(value = "botSentenceLabel")
public class BotSentenceLabelController {

	@Autowired
	private IBotSentenceLabelService botSentenceLabelService;
	
	@Autowired
	private BotSentenceProcessServiceImpl botSentenceProcessServiceImpl;
	
	@Autowired
	private BotSentenceLabelMapper botSentenceLabelMapper;

	/**
	 * 获取信息
	 * 
	 * @param processId
	 * @return
	 */
	@RequestMapping(value = "getKeysList")
	public ServerResult<List<MyBotLabel>> getKeysList(@JsonParam String processId) {

		List<MyBotLabel> list = botSentenceLabelService.getKeysList(processId);
		for (MyBotLabel myBotLabel : list) {
			/*if(StringUtils.isBlank(myBotLabel.getDisplayKeywords()) || "[]".equals(myBotLabel.getDisplayKeywords())) {//用户没有保存过，则显示提示文字，前三个关键字
				String tips = "如:";
				String keywords = myBotLabel.getKeywords();
				keywords = keywords.replace("\"", "");
				if(StringUtils.isNotBlank(keywords) && !"[]".equals(keywords)) {
					String array[] =  keywords.substring(1,keywords.length()-1).split(",");
					if(array.length > 2) {
						tips = tips + array[0] + "," + array[1] + "," + array[2];
					}else if(array.length == 0) {
						tips = "";
					}else if(array.length == 1) {
						tips = tips + array[0] ;
					}else if(array.length == 2) {
						tips = tips + array[0] + "," + array[1];
					}
					
				}else {
					myBotLabel.setDisplayKeywords(null);
					tips = "请录入相应关键字...";
				}
				myBotLabel.setTips(tips);
			}else {
				String before = myBotLabel.getDisplayKeywords();
				before = before.replace("\",\"", ";").replace("[\"", "").replace("\"]", "").replace("\", \"", ";");
				myBotLabel.setDisplayKeywords(before);
			}*/
			
			String before = myBotLabel.getKeywords();
			if(StringUtils.isBlank(before) || "[]".equals(before)) {
				before = "";
			}else {
				before = before.replace("\",\"", ",").replace("[\"", "").replace("\"]", "").replace("\", \"", ",");
			}
			myBotLabel.setDisplayKeywords(before);
			
		}

		return ServerResult.createBySuccess(list);
	}

	/**
	 * 修改信息
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(value = "updateBotLabelVO")
	public ServerResult<String> updateBotLabelVO(@JsonParam String labelId, @JsonParam String processId, @JsonParam String displayKeywords,@RequestHeader Long userId
//			@JsonParam String scoreUp, @JsonParam String scoreLow
			) {

		if (StringUtils.isBlank(labelId)) {
			throw new CommonException("label id can not be null");
		}

		BotSentenceLabel botSentenceLabel = new BotSentenceLabel();
		botSentenceLabel.setLabelId(labelId);
		botSentenceLabel.setProcessId(processId);
		if (StringUtils.isNotBlank(displayKeywords)) {
			displayKeywords = displayKeywords.replace(";", "\",\"");
			botSentenceLabel.setDisplayKeywords("[\"" + displayKeywords + "\"]");
		}
//		if (StringUtils.isNotBlank(scoreUp)) {
//			botSentenceLabel.setScoreUp(Double.parseDouble(scoreUp));
//		}
//		if (StringUtils.isNotBlank(scoreLow)) {
//			botSentenceLabel.setScoreLow(Double.parseDouble(scoreLow));
//		}

		int result = botSentenceLabelService.updateBotLabelVO(botSentenceLabel);

		if (result == 1) {
			return ServerResult.createBySuccessMessage("success");
		} else {
			return ServerResult.createBySuccessMessage("fail");
		}
	}

	/**
	 * 修改信息
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(value = "updateBotLabelVOList")
	@Transactional
	public ServerResult<String> updateBotLabelVOList(@JsonParam List<JSONObject> labelList,@RequestHeader Long userId) {

		String processId = null;
		
		for (JSONObject myBotLabel : labelList) {
			String labelId = myBotLabel.getString("labelId");
			String displayKeywords = myBotLabel.getString("displayKeywords");

			if (StringUtils.isBlank(labelId)) {
				throw new CommonException("意向标签ID不能为空!");
			}

			BotSentenceLabel botSentenceLabel = new BotSentenceLabel();
			botSentenceLabel.setLabelId(labelId);
			
			if(StringUtils.isBlank(processId)) {
				processId = botSentenceLabelMapper.selectByPrimaryKey(labelId).getProcessId();
			}
			
			botSentenceLabel.setProcessId(processId);
			
			if (StringUtils.isNotBlank(displayKeywords)) {
				displayKeywords = displayKeywords.replace("，", ",");//替换中文逗号
				displayKeywords = displayKeywords.replace("\n", "");//替换换行符
				displayKeywords = displayKeywords.replace("\"", "");//替换英文双引号
				displayKeywords = displayKeywords.replace("“", "");//替换中文双引号
				displayKeywords = displayKeywords.replace(",", "\",\"");
				botSentenceLabel.setKeywords("[\"" + displayKeywords + "\"]");
				
			}else {
				botSentenceLabel.setKeywords("[]");
			}
			botSentenceLabelService.updateBotLabelVO(botSentenceLabel);
		}
		
		//更新流程状态
		botSentenceProcessServiceImpl.updateProcessState(processId,userId);
		
		return ServerResult.createBySuccessMessage("success");

	}
	
	
	/**
	 * 获取分数
	 * 
	 * @param processId
	 * @return
	 */
	@RequestMapping(value = "getSocreList")
	public ServerResult<Map<String, MyBotLabelScore>> getSocreList(@JsonParam String processId) {
		List<MyBotLabel> list = botSentenceLabelService.getKeysList(processId);
		Map<String,MyBotLabelScore> map = new HashMap<String,MyBotLabelScore>();
		for (MyBotLabel myBotLabel : list) {
			String labelName = myBotLabel.getLabelName();
			if (labelName.equals("A") || labelName.equals("B") || labelName.equals("C") || labelName.equals("D")) {

				MyBotLabelScore myBotLabelScore = new MyBotLabelScore();
				BeanUtil.copyProperties(myBotLabel, myBotLabelScore);
				map.put(labelName, myBotLabelScore);

			}
		}

		return ServerResult.createBySuccess(map);
	}
		

	/**
	 * 修改分数
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(value = "updateScoreList")
	@Transactional
	public ServerResult<String> updateScoreList(@JsonParam String data,@RequestHeader Long userId) {
		 
		List<JSONObject> list = new ArrayList<JSONObject>();
		JSONObject resdata = JSON.parseObject(data);
		if(resdata.containsKey("A") && resdata.get("A")!=null) {
			list.add(resdata.getJSONObject("A"));
		}
		if(resdata.containsKey("B") && resdata.get("B")!=null) {
			list.add(resdata.getJSONObject("B"));
		}
		if(resdata.containsKey("C") && resdata.get("C")!=null) {
			list.add(resdata.getJSONObject("C"));
		}
		if(resdata.containsKey("D") && resdata.get("D")!=null) {
			list.add(resdata.getJSONObject("D"));
		}
		
		String processId = null;
		
		for (JSONObject jSONObject : list) {
			String labelId = jSONObject.getString("labelId");

			if (StringUtils.isNotBlank(labelId)) {
				String scoreUp = jSONObject.getString("scoreUp");
				String scoreLow = jSONObject.getString("scoreLow");
				
				BotSentenceLabel botSentenceLabel = new BotSentenceLabel();
				botSentenceLabel.setLabelId(labelId);
				if (StringUtils.isNotBlank(scoreUp)) {
					botSentenceLabel.setScoreUp(Double.parseDouble(scoreUp));
				}
				if (StringUtils.isNotBlank(scoreLow)) {
					botSentenceLabel.setScoreLow(Double.parseDouble(scoreLow));
				}

				if(StringUtils.isBlank(processId)) {
					processId = botSentenceLabelMapper.selectByPrimaryKey(labelId).getProcessId();
				}
				
				botSentenceLabel.setProcessId(processId);;
				
				botSentenceLabelService.updateBotLabelVO(botSentenceLabel);
			 
			}
		}
		
		//更新流程状态
		botSentenceProcessServiceImpl.updateProcessState(processId,userId);
		
		return ServerResult.createBySuccessMessage("success");

	}
}
