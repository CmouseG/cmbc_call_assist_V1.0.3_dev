package com.guiji.fsmanager.api;

import com.guiji.component.result.Result;
import com.guiji.fsmanager.entity.LineInfoVO;
import com.guiji.fsmanager.entity.LineXmlnfoVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @Auther: 黎阳
 * @Date: 2018/10/26 0026 16:57
 * @Description:
 */
@FeignClient("guiyu-callcenter-fsmanager")
public interface ILineOperApi {

    @ApiOperation(value = "增加线路接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "lineId", value = "线路Id", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "sipIp", value = "sip地址", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "sipPort", value = "sip端口", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "codec", value = "编码方式", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "callerNum", value = "主叫", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "calleePrefix", value = "前缀", dataType = "String", paramType = "query")
    })
    @PostMapping(value="lineinfos")
    public Result.ReturnData  addLineinfos(@RequestBody LineInfoVO lineInfo);

    @ApiOperation(value = "修改线路接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "lineId", value = "线路Id", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "sipIp", value = "sip地址", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "sipPort", value = "sip端口", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "codec", value = "编码方式", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "callerNum", value = "主叫", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "calleePrefix", value = "前缀", dataType = "String", paramType = "query")
    })
    @PutMapping(value="/lineinfos/{lineId}")
    public Result.ReturnData  editLineinfos(@PathVariable(value = "lineId") String lineId,LineInfoVO lineInfo);

    @ApiOperation(value = "删除线路接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "lineId", value = "线路Id", dataType = "String", paramType = "query")
    })
    @DeleteMapping(value="/lineinfos/{lineId}")
    public Result.ReturnData  deleteLineinfos(@PathVariable(value = "lineId") String lineId);

    @ApiOperation(value = "获取线路配置文件接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "lineId", value = "线路Id", dataType = "String", paramType = "query")
    })
    @GetMapping(value="/linexmlinfos/{lineId}")
    public Result.ReturnData<List<LineXmlnfoVO>> linexmlinfos(@PathVariable(value = "lineId") String lineId);

    @ApiOperation(value = "获取所有配置文件接口")
    @GetMapping(value="/linexmlinfos")
    public Result.ReturnData<List<LineXmlnfoVO>>  linexmlinfosAll();

}
