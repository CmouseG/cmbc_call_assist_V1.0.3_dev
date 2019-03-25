package com.guiji.dispatch.batchimport;

import org.springframework.web.multipart.MultipartFile;

public interface AsynFileService {
	public void batchPlanImport(String fileName, Long userId, MultipartFile file, String str,
								String orgCode, Integer orgId)throws Exception ;
}
