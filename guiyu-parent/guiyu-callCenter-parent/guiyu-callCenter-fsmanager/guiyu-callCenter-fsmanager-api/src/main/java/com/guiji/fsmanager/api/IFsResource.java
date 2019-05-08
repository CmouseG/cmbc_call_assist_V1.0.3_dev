package com.guiji.fsmanager.api;

import com.guiji.component.result.Result;
import com.guiji.fsmanager.entity.FsBindVO;
import com.guiji.fsmanager.entity.FsConfigVO;
import com.guiji.fsmanager.entity.ServiceTypeEnum;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Auther: 黎阳
 * @Date: 2018/10/26 0026 16:35
 * @Description:
 */
@FeignClient("guiyu-callcenter-fsmanager")
public interface IFsResource {

    @ApiOperation(value = "申请freeswitch资源接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "serviceId", value = "服务Id", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "serviceType", value = "服务类型", dataType = "String", paramType = "query"),
    })
    @RequestMapping(value = "/applyfs", method = RequestMethod.GET)
     Result.ReturnData<FsBindVO> applyfs(@RequestParam("serviceId") String serviceId, @RequestParam("serviceType")ServiceTypeEnum serviceType);

    @ApiOperation(value = "释放freeswitch资源接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "serviceId", value = "服务Id", dataType = "String", paramType = "query")
    })
    @RequestMapping(value = "/releasefs", method = RequestMethod.GET)
     Result.ReturnData<Boolean>  releasefs(@RequestParam("serviceId") String serviceId);


    @ApiOperation(value = "释放freeswitch资源接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "role", value = "服务Id", dataType = "String", paramType = "query")
    })
    @RequestMapping(value = "/getFsConfig", method = RequestMethod.GET)
    Result.ReturnData<List<FsConfigVO>>  getFsConfig(@RequestParam("role") String role);
}
