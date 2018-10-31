package com.guiji.fsmanager.api;

import com.guiji.common.result.Result;
import com.guiji.fsmanager.entity.FsBind;
import com.guiji.fsmanager.entity.ServiceTypeEnum;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Auther: 黎阳
 * @Date: 2018/10/26 0026 16:35
 * @Description:
 */
public interface FsResourceApi {

    @ApiOperation(value = "申请freeswitch资源接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "serviceId", value = "服务Id", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "serviceType", value = "服务类型", dataType = "String", paramType = "query"),
    })
    @GetMapping(value="applyfs")
    public Result.ReturnData<FsBind> applyfs(String serviceId, ServiceTypeEnum serviceType);

    @ApiOperation(value = "释放freeswitch资源接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "serviceId", value = "服务Id", dataType = "String", paramType = "query")
    })
    @GetMapping(value="releasefs")
    public Result.ReturnData  releasefs(String serviceId);

}