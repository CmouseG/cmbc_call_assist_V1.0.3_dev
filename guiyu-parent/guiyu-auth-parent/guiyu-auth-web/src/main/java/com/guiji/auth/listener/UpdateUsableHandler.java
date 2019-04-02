package com.guiji.auth.listener;

import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.guiji.user.dao.SysOrganizationMapper;

@Component
@RabbitListener(bindings=@QueueBinding(value=@Queue(value="fanout.dispatch.creatOrgDone.add",durable = "true"),exchange=@Exchange(value="fanout.dispatch.creatOrgDone",type="fanout",durable = "true")))
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
