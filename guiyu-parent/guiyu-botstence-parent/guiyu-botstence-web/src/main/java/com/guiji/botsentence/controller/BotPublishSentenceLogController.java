package com.guiji.botsentence.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guiji.botsentence.dao.BotPublishSentenceLogMapper;
import com.guiji.botsentence.dao.entity.BotPublishSentenceLog;
import com.guiji.botsentence.dao.entity.BotPublishSentenceLogExample;
import com.guiji.botsentence.dao.entity.BotPublishSentenceLogExample.Criteria;
import com.guiji.botsentence.receiver.UpdateReceiverResolver;
import com.guiji.common.model.process.ProcessTypeEnum;
import com.guiji.component.client.config.JsonParam;
import com.guiji.component.model.Page;
import com.guiji.component.result.ServerResult;
import com.guiji.guiyu.message.model.PublishBotstenceResultMsgVO;

@RestController
@RequestMapping("publishSentence")
public class BotPublishSentenceLogController {
	
	@Autowired
	private BotPublishSentenceLogMapper botPublishSentenceLogMapper;
	
	@RequestMapping("log")
	public ServerResult getBotPublishSentenceLogByPage(@JsonParam int pageSize,@JsonParam int pageNo,@RequestHeader Long userId
			, @RequestHeader String orgCode, @RequestHeader Integer authLevel){
		BotPublishSentenceLogExample example=new BotPublishSentenceLogExample();
		example.setLimitStart((pageNo-1)*pageSize);
		example.setLimitEnd(pageSize*pageNo);
		Criteria criteria = example.createCriteria();
		
		if(1 == authLevel) {//查询本人
			criteria.andCreateIdEqualTo(userId);
		}else if(2 == authLevel) {//查询本组织
			criteria.andOrgCodeEqualTo(orgCode);
		}else if(3 == authLevel) {//查询本组织及以下
			criteria.andOrgCodeLike(orgCode+"%");
		}
		
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
