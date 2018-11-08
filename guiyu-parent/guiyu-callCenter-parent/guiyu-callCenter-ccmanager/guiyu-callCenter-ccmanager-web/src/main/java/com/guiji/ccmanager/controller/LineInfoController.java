package com.guiji.ccmanager.controller;

import com.guiji.callcenter.dao.entity.LineInfo;
import com.guiji.ccmanager.constant.Constant;
import com.guiji.ccmanager.service.LineInfoService;
import com.guiji.ccmanager.vo.LineInfoVO;
import com.guiji.common.model.Page;
import com.guiji.component.result.Result;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Auther: 黎阳
 * @Date: 2018/10/25 0025 17:31
 * @Description: 线路的增删改查
 */
@RestController
public class LineInfoController {

    @Autowired
    private LineInfoService lineInfoService;


    @ApiOperation(value = "查看客户所有线路接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "customerId", value = "客户Id", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "pageNo", value = "第几页，从1开始", dataType = "String", paramType = "query", required = true)
    })
    @GetMapping(value="getLineInfos")
    public Result.ReturnData<Page<LineInfo>> getLineInfoByCustom(String customerId, String pageSize, String pageNo){

        if(StringUtils.isBlank(pageSize) || StringUtils.isBlank(pageNo)){
            return Result.error(Constant.ERROR_PARAM);
        }
        int pageSizeInt = Integer.parseInt(pageSize);
        int pageNoInt = Integer.parseInt(pageNo);
        List<LineInfo> list =  lineInfoService.getLineInfoByCustom(customerId,pageSizeInt,pageNoInt);
        int count = lineInfoService.getLineInfoByCustomCount(customerId,pageSizeInt,pageNoInt);

        Page<LineInfo> page = new Page<LineInfo>();
        page.setPageNo(pageNoInt);
        page.setPageSize(pageSizeInt);
        page.setTotal(count);
        page.setRecords(list);

        return Result.ok(page);
    }

    @ApiOperation(value = "增加线路接口")
    @PostMapping(value="addLineInfo")
    public Result.ReturnData<Boolean> addLineInfo(@RequestBody LineInfoVO lineInfoVO){

        if(lineInfoVO.getCustomerId()==null || lineInfoVO.getSipIp()==null || lineInfoVO.getSipPort() == null || lineInfoVO.getCodec() == null){
            return Result.error(Constant.ERROR_PARAM);
        }
        String codec = lineInfoVO.getCodec();
        if(!codec.equals(Constant.CODEC_G729) && !codec.equals(Constant.CODEC_PCMA) && !codec.equals(Constant.CODEC_PCMU) ){
            return Result.error(Constant.ERROR_CODEC);
        }

        return lineInfoService.addLineInfo(lineInfoVO);
    }

    @ApiOperation(value = "修改线路接口")
    @PostMapping(value="updateLineInfo")
    public Result.ReturnData<Boolean> updateLineInfo(@RequestBody LineInfoVO lineInfoVO){

        if(lineInfoVO.getLineId()==0){
            return Result.error(Constant.ERROR_PARAM);
        }
        if( lineInfoVO.getCodec()!=null) {
            String codec = lineInfoVO.getCodec();
            if (!codec.equals(Constant.CODEC_G729) && !codec.equals(Constant.CODEC_PCMA) && !codec.equals(Constant.CODEC_PCMU)) {
                return Result.error(Constant.ERROR_CODEC);
            }
        }

        return lineInfoService.updateLineInfo(lineInfoVO);
    }

    @ApiOperation(value = "删除线路接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "线路id", dataType = "String", paramType = "query", required = true)
    })
    @PostMapping(value="deleteLineInfo/{id}")
    public Result.ReturnData<Boolean> deleteLineInfo(@PathVariable("id") String id){
        return lineInfoService.delLineInfo(id);
    }

}
