/** 
 *@Copyright:Copyright (c) 2008 - 2100 
 *@Company:guojaing
 */  
package com.guiji.nas.service;

import com.guiji.nas.dao.entity.SysFile;
import com.guiji.nas.vo.SysFileQueryReqVO;
import com.guiji.nas.vo.SysFileReqVO;
import com.guiji.nas.vo.SysFileRspVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/** 
 * 影像系统服务接口
 */
public interface NasService {

	/**
	 * 文件上传
	 * 1、先上传
	 * 2、再更新本地表
	 * @date:2018年6月25日 下午8:17:02 
	 * @param sysFileReqVO
	 * @param file
	 * @return SysFileRspVO
	 * @throws IOException 
	 */
	public SysFileRspVO uploadFile(SysFileReqVO sysFileReqVO, MultipartFile file,Long userId) throws IOException;
	
	/**
	 * 删除文件
	 * @date:2018年6月25日 下午10:09:35 
	 * @param id void
	 */
	public void deleteById(String id);
	
	/**
	 * 根据条件查询文件信息列表
	 * @date:2018年6月25日 下午10:14:17 
	 * @param sysFileQueryReqVO
	 * @return List<SysFile>
	 */
	public List<SysFile> querySkFileByCondition(SysFileQueryReqVO sysFileQueryReqVO);
}
  
