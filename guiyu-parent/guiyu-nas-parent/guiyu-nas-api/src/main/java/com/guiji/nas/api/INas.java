package com.guiji.nas.api;

import com.guiji.nas.vo.SysFileQueryReqVO;
import com.guiji.nas.vo.SysFileReqVO;
import com.guiji.nas.vo.SysFileRspVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.guiji.component.result.Result.ReturnData;

import java.util.List;

/**
 * 影像系统对外服务
 */
@FeignClient("guiyu-nas-web")
public interface INas {
	/**
	 * 文件上传文件服务器
	 * @param sysFileReqVO
	 * @param file
	 * @return
	 */
	@ApiOperation(value="文件上传")
	@ApiImplicitParams({
			@ApiImplicitParam(name="file",value="待上传文件",required=true)
	})
	@PostMapping(value = "upload")
	public ReturnData<SysFileRspVO> uploadFile(@RequestBody SysFileReqVO sysFileReqVO, @RequestParam(value = "file", required = true) MultipartFile file);
	
	
	/**
	 * 根据文件系统ID查询文件信息
	 * @param sysFileQueryReqVO
	 * @return
	 */
	@ApiOperation(value="查询文件信息")
	@PostMapping(value = "query")
	public ReturnData<List<SysFileRspVO>> querySkFileInfo(@RequestBody SysFileQueryReqVO sysFileQueryReqVO);
	
	/**
	 * 删除影像
	 * @param id
	 * @return
	 */
	@ApiOperation(value="删除文件")
	@ApiImplicitParams({
			@ApiImplicitParam(name="id",value="文件系统ID",required=true)
	})
	@PostMapping(value = "delete")
	public ReturnData deleteFile(Long id);
}
