package ai.guiji.botsentence.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guiji.common.model.process.ProcessTypeEnum;
import com.guiji.guiyu.message.model.PublishBotstenceResultMsgVO;

import ai.guiji.botsentence.dao.BotPublishSentenceLogMapper;
import ai.guiji.botsentence.dao.entity.BotPublishSentenceLog;
import ai.guiji.botsentence.dao.entity.BotPublishSentenceLogExample;
import ai.guiji.botsentence.receiver.UpdateReceiverResolver;
import ai.guiji.component.client.config.JsonParam;
import ai.guiji.component.model.Page;
import ai.guiji.component.model.ServerResult;

@RestController
@RequestMapping("publishSentence")
public class BotPublishSentenceLogController {
	
	@Autowired
	private BotPublishSentenceLogMapper botPublishSentenceLogMapper;
	
	@RequestMapping("log")
	public ServerResult getBotPublishSentenceLogByPage(@JsonParam int pageSize,@JsonParam int pageNo,@RequestHeader Long userId){
		BotPublishSentenceLogExample example=new BotPublishSentenceLogExample();
		example.setLimitStart((pageNo-1)*pageSize);
		example.setLimitEnd(pageSize*pageNo);
		example.createCriteria().andCreateIdEqualTo(userId);
		example.setOrderByClause(" create_time desc");
		List<BotPublishSentenceLog>  results =botPublishSentenceLogMapper.selectByExample(example);
		Page<BotPublishSentenceLog> page = new Page<BotPublishSentenceLog>();
		int totalNum=botPublishSentenceLogMapper.countByExample(example);
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setRecords(results);
		page.setTotal(totalNum);
		
		return ServerResult.createBySuccess(page);
	}
	
}
