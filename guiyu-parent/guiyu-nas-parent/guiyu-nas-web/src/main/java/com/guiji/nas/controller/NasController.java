package com.guiji.nas.controller;

import com.guiji.common.exception.GuiyuException;
import com.guiji.component.result.Result;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.nas.api.INas;
import com.guiji.nas.constants.GuiyuNasExceptionEnum;
import com.guiji.nas.constants.NasErrorConstant;
import com.guiji.nas.dao.entity.SysFile;
import com.guiji.nas.service.NasService;
import com.guiji.nas.service.impl.FastDFSClientImpl;
import com.guiji.nas.vo.SysFileQueryReqVO;
import com.guiji.nas.vo.SysFileReqVO;
import com.guiji.nas.vo.SysFileRspVO;
import com.guiji.utils.BeanUtil;
import com.guiji.utils.StrUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/** 
* 文件处理服务类，包括文件上传、下载服务
*/

@RestController
public class NasController implements INas {
	static Logger logger = LoggerFactory.getLogger(NasController.class);
	@Autowired
	private NasService nasService;
	@Autowired
	private FastDFSClientImpl fastDFSClientImpl;
	@Value("${fdfs.webServerUrl}")
	private String hostUrl;        //文件服务器主机url

	/**
	 * 文件上传
	 *
	 * @param sysFileReqVO 上传附件时带的业务流程
	 * @param file         要上传的文件
	 * @date:2018年6月26日 上午8:23:55
	 */
	@Override
	@PostMapping(value = "upload")
	public ReturnData<SysFileRspVO> uploadFile(SysFileReqVO sysFileReqVO, @RequestParam(value = "file", required = true) MultipartFile file) {
		logger.info("文件开始上传!", file.getName());
		try {
			SysFileRspVO sysFileRspVO = nasService.uploadFile(sysFileReqVO, file);
			if (StrUtils.isNotEmpty(sysFileRspVO.getSkUrl())) {
				//返回的URL加上访问主机地址
				sysFileRspVO.setSkUrl(hostUrl + sysFileRspVO.getSkUrl());
			}
			if (StrUtils.isNotEmpty(sysFileRspVO.getSkThumbImageUrl())) {
				//返回的URL加上访问主机地址
				sysFileRspVO.setSkUrl(hostUrl + sysFileRspVO.getSkThumbImageUrl());
			}
			logger.info("文件上传成功!", file.getName());
			//返回成功状态和数据
			return Result.ok(sysFileRspVO);
		} catch (GuiyuException e) {
			logger.error("文件上传失败!", e);
			if (GuiyuNasExceptionEnum.EXCP_NAS_UPLOAD_ERROR.name().equals(e.getName())) {
				return Result.error(NasErrorConstant.NAS_UPLOAD_FILE_NULL);
			} else if (GuiyuNasExceptionEnum.EXCP_NAS_UPLOAD_FILE_SIZE_NOTNULL.name().equals(e.getName())) {
				return Result.error(NasErrorConstant.NAS_UPLOAD_FILESIZE_NOTNULL);
			} else {
				return Result.error(NasErrorConstant.NAS_UPLOAD_ERROR);
			}
		} catch (Exception e) {
			logger.error("文件上传失败!", e);
			return Result.error(NasErrorConstant.NAS_UPLOAD_ERROR);
		}
	}

	/**
     * 文件查询
     * @date:2018年6月26日 上午8:24:50 
     * @param sysFileQueryReqVO 查询文件的查询条件
     */
	@Override
	@PostMapping(value = "query")
	public ReturnData<List<SysFileRspVO>> querySkFileInfo(@RequestBody SysFileQueryReqVO sysFileQueryReqVO) {
		logger.info("开始查询!");
		List<SysFile> fileList = null;
		try{
			fileList = nasService.querySkFileByCondition(sysFileQueryReqVO);
		} catch (GuiyuException e) {
			logger.error("文件上传失败!", e);
			if (GuiyuNasExceptionEnum.EXCP_NAS_QUERY_NULL.name().equals(e.getName())) {
				return Result.error(NasErrorConstant.NAS_QUERY_NULL);
			} else {
				return Result.error(NasErrorConstant.NAS_UPLOAD_ERROR);
			}
		}
		if(fileList != null) {
			List<SysFileRspVO> fileRspList = new ArrayList<SysFileRspVO>();
			for(SysFile sysFile : fileList) {
				SysFileRspVO fileRsp = new SysFileRspVO();
				BeanUtil.copyProperties(sysFile, fileRsp);
				if(StrUtils.isNotEmpty(fileRsp.getSkUrl())) {
					//返回的URL加上访问主机地址
					fileRsp.setSkUrl(hostUrl + fileRsp.getSkUrl());
				}
				if(StrUtils.isNotEmpty(fileRsp.getSkThumbImageUrl())) {
					//返回的URL加上访问主机地址
					fileRsp.setSkUrl(hostUrl + fileRsp.getSkThumbImageUrl());
				}
				fileRspList.add(fileRsp);
			}
			return Result.ok(fileRspList);
		}
		logger.info("完成查询!");
		return Result.ok();
	}

	@Override
	@PostMapping(value = "delete")
	public ReturnData deleteFile(Long id) {
		nasService.deleteById(id);
		return Result.ok();
	}
}
