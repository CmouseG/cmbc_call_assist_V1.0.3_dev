package com.guiji.fsmanager.api;

import com.guiji.common.result.Result;
import com.guiji.fsmanager.entity.FsBindVO;
import com.guiji.fsmanager.entity.ServiceTypeEnum;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Auther: 黎阳
 * @Date: 2018/10/26 0026 16:35
 * @Description:
 */
@FeignClient("guiyu-callcenter-fsmanager")
public interface IFsResourceApi {

    @ApiOperation(value = "申请freeswitch资源接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "serviceId", value = "服务Id", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "serviceType", value = "服务类型", dataType = "String", paramType = "query"),
    })
    @GetMapping(value="applyfs")
    public Result.ReturnData<FsBindVO> applyfs(String serviceId, ServiceTypeEnum serviceType);

    @ApiOperation(value = "释放freeswitch资源接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "serviceId", value = "服务Id", dataType = "String", paramType = "query")
    })
    @GetMapping(value="releasefs")
    public Result.ReturnData<Boolean>  releasefs(String serviceId);

}
