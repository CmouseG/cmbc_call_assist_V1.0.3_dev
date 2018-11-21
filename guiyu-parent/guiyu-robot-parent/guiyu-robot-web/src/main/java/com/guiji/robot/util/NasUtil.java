/** 
 *@Copyright:Copyright (c) 2008 - 2100 
 *@Company:guojaing
 */  
package com.guiji.robot.util;

import java.io.File;
import java.io.FileInputStream;

import org.apache.http.entity.ContentType;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.guiji.component.result.Result.ReturnData;
//import com.guiji.nas.api.INas;
//import com.guiji.nas.vo.SysFileReqVO;
//import com.guiji.nas.vo.SysFileRspVO;
import com.guiji.robot.exception.RobotException;


/** 
 *@Description: NAS文件工具类
 *@history:
 *@Version:v1.0 
 */
@Component
public class NasUtil {
	private static final Logger logger = LoggerFactory.getLogger(NasUtil.class);
//	@Autowired
//	INas Inas;
	
//	/**
//	 * 上传文件服务器
//	 */
//	public String uploadToNas(SysFileReqVO sysFileReqVO, File file) {
//		String nasFileUrl = null;
//		ReturnData<SysFileRspVO> returnData = null;
//		SysFileRspVO sysFileRspVO = null;
//		FileInputStream fileInputStream = null;
//		try {
//			fileInputStream = new FileInputStream(file);
//			MultipartFile mockMultipartFile = new MockMultipartFile(file.getName(), file.getName(), 
//					ContentType.APPLICATION_SVG_XML.toString(), fileInputStream);
//			Long userId = 1L;
//			logger.info("上传NAS文件服务器");
////			returnData = Inas.uploadFile(sysFileReqVO, mockMultipartFile, userId);
//			if(returnData != null){
//				sysFileRspVO = returnData.getBody();
//				if(sysFileRspVO != null)
//					nasFileUrl = sysFileRspVO.getSkUrl();
//			}
//		} catch (Exception e) {
//			logger.error("上传失败！" + e);
//			throw new RobotException("上传NAS服务器失败！",e);
//		} finally {
//			IOUtils.closeQuietly(fileInputStream);
//		}
//		return nasFileUrl;
//	}
	
     
}
  
