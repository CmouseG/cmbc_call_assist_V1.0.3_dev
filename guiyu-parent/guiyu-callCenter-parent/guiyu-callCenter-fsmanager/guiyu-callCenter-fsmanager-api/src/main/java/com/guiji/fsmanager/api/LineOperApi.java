package com.guiji.fsmanager.api;

import com.guiji.common.model.ServerResult;
import com.guiji.fsmanager.entity.FsBind;
import com.guiji.fsmanager.entity.LineInfo;
import com.guiji.fsmanager.entity.LineXmlnfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: 黎阳
 * @Date: 2018/10/26 0026 16:57
 * @Description:
 */
public interface LineOperApi {

    @ApiOperation(value = "增加线路接口")
    @PostMapping(value="lineinfos")
    public ServerResult addLineinfos(LineInfo lineInfo);

    @ApiOperation(value = "修改线路接口")
    @PutMapping(value="/lineinfos/{lineId}")
    public ServerResult editLineinfos(@PathVariable String lineId,LineInfo lineInfo);

    @ApiOperation(value = "删除线路接口")
    @DeleteMapping(value="/lineinfos/{lineId}")
    public ServerResult deleteLineinfos(@PathVariable String lineId);

    @ApiOperation(value = "获取线路配置文件接口")
    @GetMapping(value="/linexmlinfos/{lineId}")
    public ServerResult<LineXmlnfo> linexmlinfos(@PathVariable String lineId);

    @ApiOperation(value = "获取所有配置文件接口")
    @GetMapping(value="/linexmlinfos")
    public ServerResult<LineXmlnfo> linexmlinfosAll();

}
