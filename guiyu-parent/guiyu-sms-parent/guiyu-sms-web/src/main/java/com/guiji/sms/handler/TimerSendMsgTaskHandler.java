package com.guiji.sms.handler;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import com.guiji.sms.dao.SmsTaskMapper;
import com.guiji.sms.dao.entity.SmsTask;
import com.guiji.sms.dao.entity.SmsTaskExample;
import com.guiji.sms.rabbitmq.DirectSender;
import com.guiji.sms.utils.DateUtil;
import com.guiji.sms.utils.JsonUtil;

/**
 * 定时发送任务处理
 * @author Sun
 */
@Component 
@EnableScheduling
public class TimerSendMsgTaskHandler implements SchedulingConfigurer
{
	@Autowired
	SmsTaskMapper taskMapper;
	@Autowired
	DirectSender sender;
	
	private static String cron = "0 0/1 * * * *"; // 频度为1min执行一次
	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar)
	{
		Runnable task = new Runnable()
		{
			@Override
			public void run()
			{
				String sendTime = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm");
				SmsTaskExample example = new SmsTaskExample();
				example.createCriteria().andSendTimeEqualTo(sendTime);
				List<SmsTask> taskList = taskMapper.selectByExample(example);
				if(taskList != null && CollectionUtils.isNotEmpty(taskList))
				{
					cron = "0 0/15 * * * *"; // 第一次查询到任务时，频度改为15min
					taskList.stream().forEach(task -> {
						sender.send("SendMessageTaskMQ.direct.Sms", JsonUtil.bean2JsonStr(task));
					});
				}
			}
		};
		Trigger trigger = new Trigger()
		{
			@Override
			public Date nextExecutionTime(TriggerContext triggerContext)
			{
				CronTrigger trigger = new CronTrigger(cron);
				Date nextExec = trigger.nextExecutionTime(triggerContext);
				return nextExec;
			}
		};
		taskRegistrar.addTriggerTask(task, trigger);
	}

}
