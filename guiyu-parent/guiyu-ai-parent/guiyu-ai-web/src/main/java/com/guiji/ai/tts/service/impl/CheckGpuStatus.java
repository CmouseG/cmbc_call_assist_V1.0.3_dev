package com.guiji.ai.tts.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import com.guiji.ai.tts.constants.TtsConstants;
import com.guiji.ai.tts.vo.TtsGpuVO;
import com.guiji.utils.RedisUtil;

public class CheckGpuStatus implements ApplicationRunner {

	private static Logger logger = LoggerFactory.getLogger(CheckGpuStatus.class);
	RedisUtil redisUtil = new RedisUtil();

	/**
	 * 初始化
	 */
	@Override
	public void run(ApplicationArguments arg0) throws Exception {
		logger.info("调用进程管理接口查看GPU分配情况");
		// TODO 调用进程管理接口查看GPU分配情况

		List<TtsGpuVO> returnList = new ArrayList<>(); // TODO 泛型待修改  returnList进程管理接口返回值
		logger.info("返回的列表", returnList);
		Collections.sort(returnList, new Comparator<TtsGpuVO>() {
			@Override
			public int compare(TtsGpuVO o1, TtsGpuVO o2) {
				return o1.getModel().compareTo(o2.getModel());
			}
		});

		String modelName = returnList.get(0).getModel();
		List<GuiyuTtsGpu> gpuList = new ArrayList<>();

		for (int i = 0; i < returnList.size(); i++) {
			GuiyuTtsGpu gpu = new GuiyuTtsGpu();
			gpu.setIp(returnList.get(i).getIp());
			gpu.setPort(returnList.get(i).getPort());

			if (!returnList.get(i).getModel().equals(modelName)) {
				redisUtil.lSet(TtsConstants.GUIYUTTS + modelName + TtsConstants.AVALIABLE, gpuList);
				modelName = returnList.get(i).getModel();
				gpuList = new ArrayList<>();
			}
			gpuList.add(gpu);
		}
		redisUtil.lSet(TtsConstants.GUIYUTTS + modelName + TtsConstants.AVALIABLE, gpuList);
	}

}
