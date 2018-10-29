/** 
 *@Copyright:Copyright (c) 2008 - 2100 
 *@Company:guojaing
 */  
package com.guiji.nas.service;

import com.guiji.nas.dao.entity.SysFile;
import com.guiji.nas.model.SkFileInfoReq;
import com.guiji.nas.model.SkFileInfoRsp;
import com.guiji.nas.model.SkFileQueryReq;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/** 
 * 影像系统服务接口
 */
public interface SKService {

	/**
	 * 文件上传
	 * 1、先上传
	 * 2、再更新本地表
	 * @date:2018年6月25日 下午8:17:02 
	 * @param skFileInfoReq
	 * @param file
	 * @return SkFileInfoRsp
	 * @throws IOException 
	 */
	public SkFileInfoRsp uploadFile(SkFileInfoReq skFileInfoReq, MultipartFile file) throws IOException;
	
	/**
	 * 删除文件
	 * @date:2018年6月25日 下午10:09:35 
	 * @param id void
	 */
	public void deleteById(String id);
	
	/**
	 * 根据条件查询文件信息列表
	 * @date:2018年6月25日 下午10:14:17 
	 * @param skFileQueryReq
	 * @return List<SkFileInfo>
	 */
	public List<SysFile> querySkFileByCondition(SkFileQueryReq skFileQueryReq);
}
  
