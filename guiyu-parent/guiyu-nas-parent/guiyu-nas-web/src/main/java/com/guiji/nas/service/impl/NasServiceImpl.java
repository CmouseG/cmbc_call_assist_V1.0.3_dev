/** 
 *@Copyright:Copyright (c) 2008 - 2100 
 *@Company:guojaing
 */  
package com.guiji.nas.service.impl;

import com.guiji.common.exception.GuiyuException;
import com.guiji.nas.constants.SKConstants;
import com.guiji.nas.constants.GuiyuNasExceptionEnum;
import com.guiji.nas.dao.SysFileMapper;
import com.guiji.nas.dao.entity.SysFile;
import com.guiji.nas.dao.entity.SysFileExample;
import com.guiji.nas.service.NasService;
import com.guiji.nas.vo.SysFileQueryReqVO;
import com.guiji.nas.vo.SysFileReqVO;
import com.guiji.nas.vo.SysFileRspVO;
import com.guiji.utils.BeanUtil;
import com.guiji.utils.IdGenUtil;
import com.guiji.utils.StrUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/** 
 * SK文件系统服务
 */
@Service
public class NasServiceImpl implements NasService {
	static Logger logger = LoggerFactory.getLogger(NasServiceImpl.class);
	@Resource  
    private FastDFSClientImpl client;
	@Autowired
	private SysFileMapper sysFileMapper;
	@Value("${fdfs.webServerUrl}")
    private String hostUrl;
	
	
	/**
	 * 文件上传
	 * 1、先上传
	 * 2、再更新本地表
	 * @param sysFileReqVO
	 * @param file
	 * @return SysFileRspVO
	 * @throws IOException 
	 */
	public SysFileRspVO uploadFile(SysFileReqVO sysFileReqVO, MultipartFile file) throws IOException {
		if(file != null) {
			//1、先上传FS，然后再本地进行保存
			//2、保存文件信息到本地表
			String fileName = file.getOriginalFilename();
	        String ext = fileName.substring(fileName.lastIndexOf(".") + 1); //文件后缀
	        logger.info("开始上传文件...");
			String url = client.uploadFile(file);//正常上传
			logger.info("文件上传成功..."+url);
			String thumbUrl = null;	//缩略图URL
			SysFile skFile = new SysFile();
			if(sysFileReqVO != null) {
				if(SKConstants.THUMB_IMAGE_FILAG_Y.equals(sysFileReqVO.getThumbImageFlag()) && isPic(ext)) {
					//如果上传时要求生成缩略图，且上传文件为图片，那么生成缩略图
					thumbUrl = client.uploadImageAndCrtThumbImage(file);//小图
				}
				skFile.setBusiId(sysFileReqVO.getBusiId()); //业务ID
				skFile.setBusiType(sysFileReqVO.getBusiType()); //文件类型（业务类型）
				skFile.setSysCode(sysFileReqVO.getSysCode()); //系统代码
				//String loginId = "1";	//当前登录ID WebContextUtil.getLoginId() TODO
				skFile.setCrtUser(sysFileReqVO.getUserId()); //创建人
				skFile.setLstUpdateUser(sysFileReqVO.getUserId());	//最后更新人
			}
			skFile.setFileName(fileName); //文件名称
			if (file.getSize() <= 0) {//文件大小不能为0
				throw new GuiyuException(GuiyuNasExceptionEnum.EXCP_NAS_UPLOAD_FILE_SIZE_NOTNULL);
			}
			skFile.setFileSize(Double.longBitsToDouble(file.getSize())); //文件大小
			skFile.setFileType(StrUtils.isNotEmpty(ext) ? ext.toUpperCase() : null); //文件后缀，存储大写
			skFile.setSkUrl(url); //文件URL
			skFile.setSkThumbImageUrl(thumbUrl); //缩略图url
			skFile.setCrtTime(new Date());	//创建时间
			skFile.setLstUpdateTime(new Date()); //最后创建时间
			//sysFileMapper.insert(skFile);
			SysFileRspVO rsp = new SysFileRspVO();
			//拷贝返回信息
			BeanUtil.copyProperties(skFile, rsp);
			return rsp;
		} else {
			throw new GuiyuException(GuiyuNasExceptionEnum.EXCP_NAS_UPLOAD_ERROR);
		}
	}
	
	
	/**
	 * 删除文件
	 * @param id void
	 */
	public void deleteById(Long id) {
		//先根据主键查出来
		SysFile skFileInfo = sysFileMapper.selectByPrimaryKey(id);
		if(skFileInfo != null) {
			String filePath = skFileInfo.getSkUrl();
			client.deleteFile(filePath);
			logger.info("删除文件url:"+filePath);
			String thumbfilePath = skFileInfo.getSkThumbImageUrl();
			if(StrUtils.isNotEmpty(thumbfilePath)) {
				client.deleteFile(thumbfilePath);
				logger.info("删除缩略图url:"+thumbfilePath);
			}
			sysFileMapper.deleteByPrimaryKey(id);
		}else {
			logger.warn("删除附件-附件文件不存在，ID="+id);
		}
	}
	
	/**
	 * 删除文件
	 * @param sourceUrl void
	 */
	public void deleteByUrl(String sourceUrl) {
		//获取域名后路径
		sourceUrl.replace(hostUrl, "");
		client.deleteFile(sourceUrl);
	}
	
	/**
	 * 根据条件查询文件信息列表
	 * @date:2018年6月25日 下午10:14:17 
	 * @param sysFileQueryReqVO
	 * @return List<SysFile>
	 */
	public List<SysFile> querySkFileByCondition(SysFileQueryReqVO sysFileQueryReqVO){
		Long id = sysFileQueryReqVO.getId();
		String sysCode = sysFileQueryReqVO.getSysCode();
		String busiId = sysFileQueryReqVO.getBusiId();
		String busiType = sysFileQueryReqVO.getBusiType();
		if(StrUtils.isEmpty(id) && StrUtils.isEmpty(busiId)) {
			logger.error("请求信息文件ID、业务ID不能都为空!");
    		//注册信息为空异常
    		throw new GuiyuException(GuiyuNasExceptionEnum.EXCP_NAS_QUERY_NULL);
		}
		SysFileExample example = new SysFileExample();
		SysFileExample.Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(id); //主键ID
		criteria.andSysCodeEqualTo(sysCode);	//系统码
		criteria.andBusiIdEqualTo(busiId);	//业务ID
		criteria.andBusiTypeEqualTo(busiType);	//业务附件类型
		return sysFileMapper.selectByExample(example);
	}
	
	
	/**
	 * 根据后缀校验是否是图片
	 * @date:2018年6月25日 下午9:36:45 
	 * @param extUpp
	 * @return boolean
	 */
	private boolean isPic(String extUpp) {
		String pics = "JPG,JPEG,PNG,GIF,BMP,WBMP";
		if(StrUtils.isNotEmpty(extUpp) && pics.contains(extUpp.toUpperCase())) {
			return true;
		}
		return false;
	}
}
  
