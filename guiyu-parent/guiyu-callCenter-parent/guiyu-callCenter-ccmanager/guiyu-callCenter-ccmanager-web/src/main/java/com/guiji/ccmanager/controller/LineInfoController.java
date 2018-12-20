package com.guiji.ccmanager.controller;

import com.guiji.callcenter.dao.entity.LineInfo;
import com.guiji.ccmanager.constant.Constant;
import com.guiji.ccmanager.service.LineInfoService;
import com.guiji.ccmanager.vo.LineInfo4AllotRes;
import com.guiji.ccmanager.vo.LineInfoAddVO;
import com.guiji.ccmanager.vo.LineInfoUpdateVO;
import com.guiji.common.model.Page;
import com.guiji.component.result.Result;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Auther: 黎阳
 * @Date: 2018/10/25 0025 17:31
 * @Description: 线路的增删改查
 */
@Validated
@RestController
public class LineInfoController {

    private final Logger log = LoggerFactory.getLogger(LineInfoController.class);

    @Autowired
    private LineInfoService lineInfoService;


    @ApiOperation(value = "查看客户所有线路接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "customerId", value = "客户Id", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "lineName", value = "线路名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "pageNo", value = "第几页，从1开始", dataType = "String", paramType = "query", required = true)
    })
    @GetMapping(value = "getLineInfos")
    public Result.ReturnData<Page<LineInfo>> getLineInfoByCustom(String lineName, String pageSize, String pageNo, @RequestHeader Long userId, @RequestHeader Boolean isSuperAdmin) {

        log.info("get request getLineInfoByCustom，lineName[{}]，pageSize[{}]，pageNo[{}]", lineName, pageSize, pageNo);

        if (StringUtils.isBlank(pageSize) || StringUtils.isBlank(pageNo)) {
            return Result.error(Constant.ERROR_PARAM);
        }
        int pageSizeInt = Integer.parseInt(pageSize);
        int pageNoInt = Integer.parseInt(pageNo);
        List<LineInfo> list = lineInfoService.getLineInfoByCustom(isSuperAdmin ? null : String.valueOf(userId), lineName, pageSizeInt, pageNoInt);
        int count = lineInfoService.getLineInfoByCustomCount(isSuperAdmin ? null : String.valueOf(userId), lineName);

        Page<LineInfo> page = new Page<LineInfo>();
        page.setPageNo(pageNoInt);
        page.setPageSize(pageSizeInt);
        page.setTotal(count);
        page.setRecords(list);

        log.info("response success getLineInfoByCustom，lineName[{}]，pageSize[{}]，pageNo[{}]", lineName, pageSize, pageNo);
        return Result.ok(page);
    }

    @ApiOperation(value = "增加线路接口")
    @PostMapping(value = "addLineInfo")
    public Result.ReturnData<Boolean> addLineInfo(@RequestBody @Validated LineInfoAddVO lineInfoVO, @RequestHeader Long userId) {

        log.info("get request addLineInfo，LineInfoAddVO[{}]", lineInfoVO);

        if (StringUtils.isBlank(lineInfoVO.getOrgCode())) {
            return Result.error(Constant.ERROR_PARAM);
        }
        lineInfoVO.setCustomerId(String.valueOf(userId));
        lineInfoService.addLineInfo(lineInfoVO);
        return Result.ok(true);
    }

    @ApiOperation(value = "修改线路接口")
    @PostMapping(value = "updateLineInfo")
    public Result.ReturnData<Boolean> updateLineInfo(@RequestBody @Validated LineInfoUpdateVO lineInfoVO, @RequestHeader Long userId) {
        if (lineInfoVO.getLineId() == 0) {
            return Result.error(Constant.ERROR_PARAM);
        }
        log.info("get request updateLineInfo，lineInfoVO[{}]", lineInfoVO);

        lineInfoService.updateLineInfo(lineInfoVO, userId);
        return Result.ok(true);
    }

    @ApiOperation(value = "删除线路接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "线路id", dataType = "String", paramType = "query", required = true)
    })
    @PostMapping(value = "deleteLineInfo/{id}")
    public Result.ReturnData<Boolean> deleteLineInfo(@PathVariable("id") String id) {
        log.info("get request deleteLineInfo，id[{}]", id);
        lineInfoService.delLineInfo(id);
        return Result.ok(true);
    }

    @ApiOperation(value = "查询线路，用于分配查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "customerId", value = "客户Id", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "orgCode", value = "组织机构", dataType = "String", paramType = "query")
    })
    @GetMapping(value = "getLineInfos4Allot")
    public List<LineInfo4AllotRes> getLineInfos4Allot(@NotEmpty(message = "用户id不能为空") String customerId,
                                                      @NotEmpty(message = "组织机构不能为空") String orgCode,
                                                      @RequestHeader Long userId, @RequestHeader Boolean isSuperAdmin) {

        log.info("get request getLineInfos4Allot，customerId[{}]，userId[{}]，orgCode[{}]", customerId, userId, orgCode);

        List<LineInfo4AllotRes> list = lineInfoService.getLineInfos4Allot(customerId, userId, isSuperAdmin, orgCode);

        return list;
    }

    @ApiOperation(value = "分配线路给某个用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "customerId", value = "分配客户的Id", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "lineIds", value = "勾的线路id，以逗号分隔", dataType = "String", paramType = "query")
    })
    @PostMapping(value = "allotLineInfo")
    public Result.ReturnData allotLineInfo(@RequestBody Map<String, String> reqMap,
                                           @RequestHeader Long userId, @RequestHeader Boolean isSuperAdmin, @RequestHeader String orgCode) {

        log.info("post request allotLineInfo，userId[{}]，orgCode[{}], reqMap[{}]", userId, orgCode, reqMap);

        String customerId = reqMap.get("customerId");
        String lineIds = reqMap.get("lineIds");
        if (customerId == null || lineIds == null) {
            return Result.error(Constant.ERROR_PARAM);
        }

        lineInfoService.allotLineInfo(customerId, lineIds);

        return Result.ok();
    }

}
