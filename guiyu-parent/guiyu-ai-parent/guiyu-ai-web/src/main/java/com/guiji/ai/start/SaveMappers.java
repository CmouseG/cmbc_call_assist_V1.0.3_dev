package com.guiji.ai.start;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.guiji.ai.dao.AiModelFactoryMapper;
import com.guiji.ai.dao.entity.AiModelFactory;
import com.guiji.ai.dao.entity.AiModelFactoryExample;
import com.guiji.utils.RedisUtil;

/**
 * 保存模型厂商配置
 */
@Component
public class SaveMappers implements ApplicationRunner
{
	@Autowired
	AiModelFactoryMapper mapper;
	@Autowired
	RedisUtil redisUtil;

	@Override
	public void run(ApplicationArguments args) throws Exception
	{
		AiModelFactoryExample example = new AiModelFactoryExample();
		example.setOrderByClause("model");
		List<AiModelFactory> factorys = mapper.selectByExample(example);
		if(factorys == null || factorys.isEmpty()) {
			return;
		}
		
		String model = factorys.get(0).getModel();
		List<String> factoryList = new ArrayList<>();
		for(AiModelFactory factory : factorys)
		{
			if(!factory.getModel().equals(model))
			{
				redisUtil.set(model, factoryList);
				model = factory.getModel();
				factoryList = new ArrayList<>();
			}
			factoryList.add(String.valueOf(factory.getFactory()));
		}
		redisUtil.set(model, factoryList);
	}
}
