package com.guiji.nas.api;

import com.guiji.common.result.Result;
import com.guiji.common.result.Result.ReturnData;
import com.guiji.nas.vo.SysFileQueryReqVO;
import com.guiji.nas.vo.SysFileReqVO;
import com.guiji.nas.vo.SysFileRspVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 影像系统对外服务
 */
@Api(tags="影像系统接口")
@FeignClient("nas")
public interface INas {
	/**
	 * 文件上传文件服务器
	 * @param sysFileReqVO
	 * @param file
	 * @return
	 */
	@ApiOperation(value="简单附件上传", notes="上传附件")
	@ApiImplicitParams({
	    @ApiImplicitParam(name="file",value="文件流",required=true)
	})
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ReturnData<SysFileRspVO> uploadFile(SysFileReqVO sysFileReqVO, MultipartFile file);
	
	
	/**
	 * 根据文件系统ID查询文件信息
	 * @param sysFileQueryReqVO
	 * @return
	 */
	@ApiOperation(value="查询文件信息", notes="根据文件系统ID查询文件信息")
	@RequestMapping(value = "/query", method = RequestMethod.POST)
	public ReturnData<List<SysFileRspVO>> querySkFileInfo(SysFileQueryReqVO sysFileQueryReqVO);
	
	/**
	 * 删除影像
	 * @param id
	 * @return
	 */
	@ApiOperation(value="删除文件", notes="根据文件系统ID删除文件信息")
	@ApiImplicitParams({
	    @ApiImplicitParam(name="id",value="文件系统ID",required=true)
	})
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ReturnData deleteFile(String id);
}
