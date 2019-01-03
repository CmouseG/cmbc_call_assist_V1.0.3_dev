package com.guiji.ai.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface TtsModelMapperExt {
	
	List<Map<String, Object>> selectModelGpuCount();

	int updateModelByIpPort(@Param("ip") String ip, @Param("port") String port, @Param("model") String model);
    
}