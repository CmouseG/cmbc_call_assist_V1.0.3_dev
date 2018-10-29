package com.guiji.fsmanager.api;

import com.guiji.common.result.Result;
import com.guiji.fsmanager.entity.FsBind;
import com.guiji.fsmanager.entity.LineInfo;
import com.guiji.fsmanager.entity.LineXmlnfo;
import feign.RequestLine;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

/**
 * @Auther: 黎阳
 * @Date: 2018/10/26 0026 16:57
 * @Description:
 */
public interface LineOperApi {

    @ApiOperation(value = "增加线路接口")
    @PostMapping(value="lineinfos")
    public Result.ReturnData  addLineinfos(LineInfo lineInfo);

    @ApiOperation(value = "修改线路接口")
    @PutMapping(value="/lineinfos/{lineId}")
    public Result.ReturnData  editLineinfos(@PathVariable String lineId,LineInfo lineInfo);

    @ApiOperation(value = "删除线路接口")
    @DeleteMapping(value="/lineinfos/{lineId}")
    public Result.ReturnData  deleteLineinfos(@PathVariable String lineId);

    @ApiOperation(value = "获取线路配置文件接口")
    @GetMapping(value="/linexmlinfos/{lineId}")
    public Result.ReturnData linexmlinfos(@PathVariable String lineId);

    @ApiOperation(value = "获取所有配置文件接口")
    @GetMapping(value="/linexmlinfos")
    public Result.ReturnData  linexmlinfosAll();

}
