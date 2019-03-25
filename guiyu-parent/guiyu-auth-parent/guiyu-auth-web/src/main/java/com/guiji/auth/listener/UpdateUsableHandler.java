package com.guiji.auth.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.guiji.user.dao.SysOrganizationMapper;

@Component
@RabbitListener(queues = "fanout.dispatch.creatOrgDone.add")
public class UpdateUsableHandler
{
	@Autowired
	private SysOrganizationMapper sysOrganizationMapper;
	
	@RabbitHandler
	public void process(String message) throws Exception
	{
		Integer orgId = Integer.valueOf(message);
		if(orgId == null) {return;}
		sysOrganizationMapper.updateUsableByOrgId(orgId);
	}
}
