package com.guiji.nas.controller;

import com.guiji.common.result.Result;
import com.guiji.nas.api.ISkController;
import com.guiji.nas.constants.SKErrorEnum;
import com.guiji.nas.dao.entity.SysFile;
import com.guiji.nas.model.SkFileInfoReq;
import com.guiji.nas.model.SkFileInfoRsp;
import com.guiji.nas.model.SkFileQueryReq;
import com.guiji.nas.service.SKService;
import com.guiji.nas.service.impl.FastDFSClientImpl;
import com.guiji.utils.BeanUtil;
import com.guiji.utils.StrUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/** 
* 文件处理服务类，包括文件上传、下载服务
*/

@RestController
public class SkController implements ISkController {
	static Logger logger = LoggerFactory.getLogger(SkController.class);
	@Autowired
	private SKService skService;
	@Autowired
	private FastDFSClientImpl fastDFSClientImpl;
	@Value("${fdfs.webServerUrl}")
    private String hostUrl;		//文件服务器主机url
	
	/**
     * 文件上传
     * @date:2018年6月26日 上午8:23:55 
     * @param skFileInfoReq 上传附件时带的业务流程
     * @param file 要上传的文件
     */
	@Override
	public Result.ReturnData<SkFileInfoRsp> uploadFile(SkFileInfoReq skFileInfoReq, @RequestParam(value="file", required=true) MultipartFile file) {
		logger.info("文件开始上传!",file.getName());
		try {
			SkFileInfoRsp skFileInfoRsp = skService.uploadFile(skFileInfoReq, file);
			if(StrUtils.isNotEmpty(skFileInfoRsp.getSkUrl())) {
				//返回的URL加上访问主机地址
				skFileInfoRsp.setSkUrl(hostUrl + skFileInfoRsp.getSkUrl());
			}
			if(StrUtils.isNotEmpty(skFileInfoRsp.getSkThumbImageUrl())) {
				//返回的URL加上访问主机地址
				skFileInfoRsp.setSkUrl(hostUrl + skFileInfoRsp.getSkThumbImageUrl());
			}
			logger.info("文件上传成功!",file.getName());
			//返回成功状态和数据
			return Result.ok(skFileInfoRsp);
		}catch (Exception e) {
			logger.error("文件上传失败!",e);
			return Result.error(SKErrorEnum.UPLOAD_ERROR.getErrorCode());
		}
	}

	
	/**
     * 文件查询
     * @date:2018年6月26日 上午8:24:50 
     * @param skFileQueryReq 查询文件的查询条件
     */
	@Override
	public Result.ReturnData<List<SkFileInfoRsp>> querySkFileInfo(SkFileQueryReq skFileQueryReq) {
		logger.info("开始查询!");
		List<SysFile> fileList = skService.querySkFileByCondition(skFileQueryReq);
		if(fileList != null) {
			List<SkFileInfoRsp> fileRspList = new ArrayList<SkFileInfoRsp>();
			for(SysFile sysFile : fileList) {
				SkFileInfoRsp fileRsp = new SkFileInfoRsp();
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
	public Result.ReturnData deleteFile(String id) {
		skService.deleteById(id);
		return Result.ok();
	}

	@Override
	public Result.ReturnData downloadFile(String url,HttpServletResponse response) {
		logger.info("文件开始下载!",url);
		ServletOutputStream outputStream = null;
		try {
			byte[] bytes = fastDFSClientImpl.downloadFile(url);
			String fileName = url.split("/")[url.split("/").length-1];
			// 这里只是为了整合fastdfs，所以写死了文件格式。需要在上传的时候保存文件名。下载的时候使用对应的格式
			response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
			response.setCharacterEncoding("UTF-8");
			outputStream = response.getOutputStream();
			outputStream.write(bytes);
		} catch (Exception e) {
			logger.error("文件下载失败!",e);
			return Result.error(SKErrorEnum.DOWNLOAD_ERROR.getErrorCode());
		} finally {
			try {
				outputStream.flush();
				outputStream.close();
			} catch (IOException e) {
				logger.error("文件下载失败!",e);
				return Result.error(SKErrorEnum.DOWNLOAD_ERROR.getErrorCode());
			}
		}
		logger.info("文件下载成功!",url);
		return Result.ok();
	}

}