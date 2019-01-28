package com.guiji.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.guiji.sms.dao.SmsPlatformMapper;
import com.guiji.sms.dao.SmsTunnelMapper;
import com.guiji.sms.dao.entity.SmsPlatform;
import com.guiji.sms.dao.entity.SmsPlatformExample;
import com.guiji.sms.dao.entity.SmsTunnel;
import com.guiji.sms.dao.entity.SmsTunnelExample;
import com.guiji.utils.RedisUtil;

/**
 * 将平台和通道存入Redis
 *
 */
@Component
public class SavePlatformAndTunnelHandler implements ApplicationRunner
{
	@Autowired
	SmsPlatformMapper platformMapper;
	@Autowired
	SmsTunnelMapper tunnelMapper;
	@Autowired
	RedisUtil redisUtil;

	@Override
	public void run(ApplicationArguments args) throws Exception
	{
		// platform
		SmsPlatformExample example1 = new SmsPlatformExample();
		List<SmsPlatform> platformList = platformMapper.selectByExample(example1);
		for(SmsPlatform platform : platformList)
		{
			redisUtil.set(platform.getPlatformName(), platform);
		}
		
		// tunnel
		SmsTunnelExample example2 = new SmsTunnelExample();
		List<SmsTunnel> tunnelList = tunnelMapper.selectByExampleWithBLOBs(example2);
		for(SmsTunnel tunnel : tunnelList)
		{
			redisUtil.set(tunnel.getTunnelName(), tunnel);
		}
	}

}
