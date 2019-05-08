//package com.guiji.ccmanager.controller;
//
//import com.guiji.ccmanager.api.ILineOperation;
//import com.guiji.ccmanager.entity.OutLineInfoAddReq;
//import com.guiji.ccmanager.entity.OutLineInfoUpdateReq;
//import com.guiji.ccmanager.service.LineOperationService;
//import com.guiji.component.result.Result;
//import com.guiji.fsmanager.entity.LineInfoVO;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
///**
// * 线路市场调用，操作线路信息
// */
//@Slf4j
//@RestController
//public class LineOperationController implements ILineOperation {
//
//    @Autowired
//    LineOperationService lineOperationService;
//
//    @Override
//    public Result.ReturnData<List<LineInfoVO>> batchAddLineInfo(@RequestBody List<OutLineInfoAddReq> outLineInfoAddReqList) {
//        log.info("-------get request addLineInfo size[{}]", outLineInfoAddReqList.size());
//        List<LineInfoVO> list = lineOperationService.batchAddLineInfo(outLineInfoAddReqList);
//        return Result.ok(list);
//    }
//
//    @Override
//    public Result.ReturnData addLineInfo(@RequestBody OutLineInfoAddReq outLineInfoAddReq) {
//        log.info("-------get request addLineInfo outLineInfoAddReq[{}]", outLineInfoAddReq);
//        Integer lineId =  lineOperationService.addLineInfo(outLineInfoAddReq);
//        return Result.ok(lineId);
//    }
//
//    @Override
//    public Result.ReturnData updateLineInfo(@RequestBody OutLineInfoUpdateReq outLineInfoUpdateReq) {
//        log.info("-------get request updateLineInfo outLineInfoUpdateReq[{}]", outLineInfoUpdateReq);
//        lineOperationService.updateLineInfo(outLineInfoUpdateReq);
//        return Result.ok();
//    }
//
//    @Override
//    public Result.ReturnData deleteLineInfo(@RequestParam(value = "LineId") Integer lineId) {
//        log.info("-------get request deleteLineInfo lineId[{}]", lineId);
//        lineOperationService.deleteLineInfo(Integer.valueOf(lineId));
//        return Result.ok();
//    }
//}
