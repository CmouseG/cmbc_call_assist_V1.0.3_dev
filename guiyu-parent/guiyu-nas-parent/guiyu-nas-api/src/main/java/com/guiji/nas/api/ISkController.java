package com.guiji.nas.api;

import com.guiji.common.result.Result;
import com.guiji.nas.model.SkFileInfoReq;
import com.guiji.nas.model.SkFileInfoRsp;
import com.guiji.nas.model.SkFileQueryReq;
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
public interface ISkController {
	/**
	 * 文件上传文件服务器
	 * @param skFileInfoReq
	 * @param file
	 * @return
	 */
	@ApiOperation(value="简单附件上传", notes="上传附件")
	@ApiImplicitParams({
	    @ApiImplicitParam(name="file",value="文件流",required=true)
	})
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public Result.ReturnData<SkFileInfoRsp> uploadFile(SkFileInfoReq skFileInfoReq, MultipartFile file);
	
	
	/**
	 * 根据文件系统ID查询文件信息
	 * @param skFileQueryReq
	 * @return
	 */
	@ApiOperation(value="查询文件信息", notes="根据文件系统ID查询文件信息")
	@RequestMapping(value = "/query", method = RequestMethod.POST)
	public Result.ReturnData<List<SkFileInfoRsp>> querySkFileInfo(SkFileQueryReq skFileQueryReq);
	
	/**
	 * 删除影像
	 * @param skId
	 * @return
	 */
	@ApiOperation(value="删除文件", notes="根据文件系统ID删除文件信息")
	@ApiImplicitParams({
	    @ApiImplicitParam(name="id",value="文件系统ID",required=true)
	})
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public Result.ReturnData deleteFile(String id);

	/**
	 * 下载影像
	 * @param url
	 * @return
	 */
	@ApiOperation(value="下载文件", notes="根据文件url下载文件")
	@ApiImplicitParams({
			@ApiImplicitParam(name="url",value="文件url",required=true)
	})
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public Result.ReturnData downloadFile(String url,HttpServletResponse response);
	
}
