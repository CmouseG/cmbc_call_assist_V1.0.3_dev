//package com.guiji.ccmanager.controller;
//
//import com.guiji.callcenter.dao.entity.LineInfo;
//import com.guiji.ccmanager.constant.Constant;
//import com.guiji.ccmanager.vo.LineInfo4AllotRes;
//import com.guiji.component.result.Result;
//import io.swagger.annotations.ApiImplicitParam;
//import io.swagger.annotations.ApiImplicitParams;
//import io.swagger.annotations.ApiOperation;
//import org.hibernate.validator.constraints.NotEmpty;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * @Auther: 黎阳
// * @Date: 2018/10/25 0025 17:31
// * @Description: 线路的增删改查
// */
//@Validated
//@RestController
//public class LineInfoController {
//
//    private final Logger log = LoggerFactory.getLogger(LineInfoController.class);
//
//    @Autowired
//    private LineInfoService lineInfoService;
//
////
////    @ApiOperation(value = "查看客户所有线路接口")
////    @ApiImplicitParams({
////            @ApiImplicitParam(name = "customerId", value = "客户Id", dataType = "String", paramType = "query"),
////            @ApiImplicitParam(name = "lineName", value = "线路名称", dataType = "String", paramType = "query"),
////            @ApiImplicitParam(name = "pageSize", value = "每页数量", dataType = "String", paramType = "query", required = true),
////            @ApiImplicitParam(name = "pageNo", value = "第几页，从1开始", dataType = "String", paramType = "query", required = true)
////    })
////    @Jurisdiction("callCenter_lineInfoList_defquery")
////    @GetMapping(value = "getLineInfos")
////    public Result.ReturnData<Page<LineInfo4Page>> getLineInfoByCustom(String lineName, String pageSize, String pageNo, @RequestHeader String orgCode,
////                                                                      @RequestHeader Long userId, @RequestHeader Integer authLevel) {
////
////        log.info("get request getLineInfoByCustom，lineName[{}]，pageSize[{}]，pageNo[{}]", lineName, pageSize, pageNo);
////
////        if (StringUtils.isBlank(pageSize) || StringUtils.isBlank(pageNo)) {
////            return Result.error(Constant.ERROR_PARAM);
////        }
////        int pageSizeInt = Integer.parseInt(pageSize);
////        int pageNoInt = Integer.parseInt(pageNo);
////        List<LineInfo4Page> list = lineInfoService.getLineInfoByCustom(authLevel, String.valueOf(userId),orgCode, lineName, pageSizeInt, pageNoInt);
////        int count = lineInfoService.getLineInfoByCustomCount(authLevel,String.valueOf(userId),orgCode, lineName);
////
////        Page<LineInfo4Page> page = new Page<LineInfo4Page>();
////        page.setPageNo(pageNoInt);
////        page.setPageSize(pageSizeInt);
////        page.setTotal(count);
////        page.setRecords(list);
////
////        log.info("response success getLineInfoByCustom，lineName[{}]，pageSize[{}]，pageNo[{}]", lineName, pageSize, pageNo);
////        return Result.ok(page);
////    }
//
//    @ApiOperation(value = "查询线路，用于分配查询")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "customerId", value = "客户Id", dataType = "String", paramType = "query"),
//            @ApiImplicitParam(name = "orgCode", value = "组织机构", dataType = "String", paramType = "query")
//    })
//    @GetMapping(value = "getLineInfos4Allot")
//    public List<LineInfo4AllotRes> getLineInfos4Allot(@NotEmpty(message = "用户id不能为空") String customerId) {
//
//        log.info("get request getLineInfos4Allot，customerId[{}]", customerId);
//
//        List<LineInfo4AllotRes> list = lineInfoService.getLineInfos4Allot(customerId);
//
//        return list;
//    }
//
//    @ApiOperation(value = "分配线路给某个用户")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "customerId", value = "分配客户的Id", dataType = "String", paramType = "query"),
//            @ApiImplicitParam(name = "lineIds", value = "勾的线路id，以逗号分隔", dataType = "String", paramType = "query")
//    })
//    @PostMapping(value = "allotLineInfo")
//    public Result.ReturnData allotLineInfo(@RequestBody Map<String, String> reqMap,
//                                           @RequestHeader Long userId, @RequestHeader Boolean isSuperAdmin, @RequestHeader String orgCode) {
//
//        log.info("post request allotLineInfo，userId[{}]，orgCode[{}], reqMap[{}]", userId, orgCode, reqMap);
//
//        String customerId = reqMap.get("customerId");
//        String lineIds = reqMap.get("lineIds");
//        if (customerId == null || lineIds == null) {
//            return Result.error(Constant.ERROR_PARAM);
//        }
//
//        lineInfoService.allotLineInfo(customerId, lineIds);
//
//        return Result.ok();
//    }
//
//
//    @ApiOperation(value = "开户管理，查询某个企业的线路")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "orgCode", value = "企业的组织编码", dataType = "String", paramType = "query")
//    })
//    @GetMapping(value = "getLineInfoByOrgCode")
//    public Result.ReturnData<Map> getLineInfoByOrgCode(@NotEmpty(message = "组织编码不能为空") String orgCode) {
//
//        log.info("get request getLineInfoByOrgCode，orgCode[{}]", orgCode);
//
//        List<LineInfo> list = lineInfoService.getLineInfoByOrgCode(orgCode);
//        Map map = new HashMap();
//        map.put("list",list);
//        if(list!=null){
//            map.put("count",list.size());
//        }else{
//            map.put("count",0);
//        }
//        log.info("response get request getLineInfoByOrgCode，orgCode[{}]", orgCode);
//        return Result.ok(map);
//    }
//
//
//
//}
