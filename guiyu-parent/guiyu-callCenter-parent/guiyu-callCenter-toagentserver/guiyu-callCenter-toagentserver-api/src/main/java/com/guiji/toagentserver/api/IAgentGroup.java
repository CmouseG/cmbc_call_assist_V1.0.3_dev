package com.guiji.toagentserver.api;

import com.guiji.component.result.Result;
import com.guiji.toagentserver.entity.AgentGroupInfo;
import com.guiji.toagentserver.entity.FsInfoVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Auther: 魏驰
 * @Date: 2019/1/21 10:30
 * @Project：guiyu-parent
 * @Description:
 */
@FeignClient("guiyu-callcenter-toagentserver")
public interface IAgentGroup {
    @ApiOperation(value = "根据企业代码获取所有队列")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orgCode", value = "用户的组织代码", dataType = "String", paramType = "query", required = true)
    })
    @GetMapping(value="getgroups")
    Result.ReturnData<List<AgentGroupInfo>> getGroups(@RequestParam(value = "orgCode", required = true) String orgCode);

    @ApiOperation(value = "获取转人工freeswitch的基本信息")
    @GetMapping(value="getfsinfo")
    Result.ReturnData<FsInfoVO> getFsInfo();

    @ApiOperation(value = "解除转人工线路绑定")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "lineId", value = "线路Id", dataType = "String", paramType = "query")
    })
    @RequestMapping(value = "/untying/{lineId}", method = RequestMethod.DELETE)
    Result.ReturnData  untyingLineinfos(@PathVariable(value = "lineId") String lineId);

}
