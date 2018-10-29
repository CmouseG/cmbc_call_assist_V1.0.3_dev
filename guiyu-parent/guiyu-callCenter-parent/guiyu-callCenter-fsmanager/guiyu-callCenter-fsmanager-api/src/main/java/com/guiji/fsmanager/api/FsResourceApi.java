package com.guiji.fsmanager.api;

import com.guiji.common.result.Result;
import com.guiji.fsmanager.entity.FsBind;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Auther: 黎阳
 * @Date: 2018/10/26 0026 16:35
 * @Description:
 */
public interface FsResourceApi {

    @ApiOperation(value = "申请freeswitch资源接口")
    @GetMapping(value="applyfs")
    public Result.ReturnData<FsBind> applyfs(String serviceId, String serviceType);

    @ApiOperation(value = "释放freeswitch资源接口")
    @GetMapping(value="releasefs")
    public Result.ReturnData  releasefs(String serviceId);

}
