package com.guiji.ai.tts.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.ai.tts.constants.GuiyuAIExceptionEnum;
import com.guiji.ai.tts.service.TtsService;
import com.guiji.ai.vo.TtsReqVO;
import com.guiji.ai.vo.TtsRspVO;
import com.guiji.common.exception.GuiyuException;
import com.guiji.utils.RedisUtil;

/**
 * Created by ty on 2018/11/13.
 */
@Service
public class TtsServiceImpl implements TtsService {
	private static Logger logger = LoggerFactory.getLogger(TtsServiceImpl.class);
	
	@Autowired
    private RedisUtil redisUtil;
	@Autowired
	TtsServiceFactory ttsServiceFactory;
	
    @Override
    public TtsRspVO translate(TtsReqVO ttsReqVO) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        // 结果集
        Map<String,String> radioMap = new HashMap<String,String>();
        List<String> taskList = ttsReqVO.getContents();
        // 全流式处理转换成CompletableFuture[]+组装成一个无返回值CompletableFuture，join等待执行完毕。返回结果whenComplete获取
        CompletableFuture[] cfs = taskList.stream()
                .map(text -> CompletableFuture.supplyAsync(() -> calc(ttsReqVO.getBusId(),ttsReqVO.getModel(),text), executorService)
                        .whenComplete((s, e) -> {
                            radioMap.put(text,s);
                        })
                ).toArray(CompletableFuture[]::new);

        // 封装后无返回值，必须自己whenComplete()获取
        CompletableFuture.allOf(cfs).join();

        TtsRspVO ttsRspVO = new TtsRspVO();
        ttsRspVO.setBusId(ttsReqVO.getBusId());
        ttsRspVO.setModel(ttsReqVO.getModel());
        ttsRspVO.setAudios(radioMap);
        return ttsRspVO;
    }

    public String calc(String busId, String model, String text) {
        String audioUrl = null;
        try {
        	//判断时候已经合成
        	audioUrl = (String) redisUtil.get(model+"_"+text);
        	if(audioUrl == null){
        		//合成
        		ITtsServiceProvide provide = ttsServiceFactory.getTtsProvide(model);
        		if(provide == null){
        			throw new GuiyuException(GuiyuAIExceptionEnum.EXCP_AI_GET_GPU); //没有获取到可用GPU
        		}
        		audioUrl= provide.transfer(busId, model, text);
        		if(audioUrl != null){
        			redisUtil.set(model+"_"+text, audioUrl); //返回值url存入redis
            		logger.info(audioUrl + "存入Redis");
        		}else{
        			logger.error("文本转语音失败！");
        		}
        	}
        } catch (Exception e) {
            logger.error("语音文件合成失败!" + e);
        }
        return audioUrl;
    }
}
