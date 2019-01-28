package com.guiji.botsentence.manager.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.guiji.botsentence.manager.IAIManager;
import com.guiji.botsentence.util.CommonUtil;
import com.guiji.botsentence.util.RestHttpUtil;
import com.guiji.botsentence.vo.AIHelloRequestVO;
import com.guiji.botsentence.vo.AIResponseVO;
import com.guiji.botsentence.vo.AIRestoreRequestVO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class IAIManagerImpl implements IAIManager {
    @Value("${selftest.sellbot.serverUrl}")
    private String sellbotUrl;

    @Value("${selftest.sellbot.serverStartPort}")
    private Integer sellbotStartPort;

    @Value("${selftest.sellbot.serverPortCount}")
    private Integer sellbotPortCount;

    Cache<String, Integer> sellbotCache;

    //初始化缓存
    @PostConstruct
    public void init(){
        sellbotCache = CacheBuilder.newBuilder()
                .maximumSize(1000)
                .expireAfterAccess(2, TimeUnit.MINUTES)
                .build();
    }

    /**
     * 根据uuid获取端口号，如果不存在则新建，否则返回
     * @return
     */
    private Integer getPort(String uuid) throws Exception {
        Integer port = sellbotCache.getIfPresent(uuid);
        if(port!=null && port>0){
            log.info("会话[{}]已被分配过sellbot端口[{}]", uuid, port);
            return port;
        }

        ConcurrentMap<String, Integer> concurrentMap = sellbotCache.asMap();
        for(Integer i=sellbotStartPort;i<sellbotStartPort+sellbotPortCount;i++){
            if(concurrentMap.containsValue(i)){
                continue;
            }else{
                log.info("会话[{}]获取到的可用sellbot端口为[{}]", uuid, i);
                sellbotCache.put(uuid, i);
                return i;
            }
        }

        throw new Exception("机器人正在努力赶来，请稍等!");
    }

    /**
     * 判断通话uuid是否已存在
     * @param uuid
     * @return
     */
    @Override
    public boolean isUuidExist(String uuid){
        Integer port = sellbotCache.getIfPresent(uuid);
        return port!=null && port>0;
    }

    /**
     * 发起hello请求
     * @param aiRequest
     * @return
     * @throws Exception
     */
    public AIResponseVO hello(AIHelloRequestVO aiRequest) throws Exception {
        log.info("开始发起sellbot请求，request[{}]", aiRequest);
        try {
            AIResponseVO request = AIResponseVO.builder()
                    .sentence(aiRequest.getSentence())
                    .state(aiRequest.getState())
                    .build();
            int port = getPort(aiRequest.getSeqid());
            String url = String.format("http://%s:%s", sellbotUrl, port);
            String resp = RestHttpUtil.post(url, aiRequest);
            log.info("获取到的sellbot结果为：url[{}], request[{}], response[{}]", url, request, resp);
            
            AIResponseVO response = CommonUtil.jsonToJavaBean(resp, AIResponseVO.class);
            response.setPort(port);
            return response;
        }catch (Exception ex){
            log.warn("sellbot的hello出现异常", ex);
            throw new Exception(ex.getMessage());
        }
    }

    @Override
    public void end(String uuid){
        log.info("开始结束通话,[{}]", uuid);
        sellbotCache.invalidate(uuid);
    }

    /**
     * 启动新的sellbot识别
     */
    public AIResponseVO restore(AIRestoreRequestVO aiRequest) throws Exception {
        log.info("开始发起sellbot请求，request[{}]", aiRequest);
        try {
        	int port = getPort(aiRequest.getSeqid());
            String url = String.format("http://%s:%s/restore", sellbotUrl, port);


            String resp = RestHttpUtil.post(url, aiRequest);
            log.info("获取到的sellbot restore结果为：url[{}], request[{}], response[{}]", url, aiRequest, resp);
            AIResponseVO response =CommonUtil.jsonToJavaBean(resp, AIResponseVO.class);
            response.setPort(port);
            return response;
        }catch (Exception ex){
            log.warn("sellbot的restore出现异常", ex);
            throw new Exception(ex.getMessage());
        }
    }

	public Cache<String, Integer> getSellbotCache() {
		return sellbotCache;
	}

	public void setSellbotCache(Cache<String, Integer> sellbotCache) {
		this.sellbotCache = sellbotCache;
	}

	@Override
	public void endAll() {
		log.info("开始清空所有会话...");
        sellbotCache.invalidateAll();
	}
}
