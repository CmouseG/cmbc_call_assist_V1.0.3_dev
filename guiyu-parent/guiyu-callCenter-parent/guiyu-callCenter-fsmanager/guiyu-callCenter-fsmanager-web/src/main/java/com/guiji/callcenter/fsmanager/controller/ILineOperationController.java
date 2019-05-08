package com.guiji.callcenter.fsmanager.controller;

import com.guiji.callcenter.fsmanager.service.ILineOperationService;
import com.guiji.component.result.Result;
import com.guiji.fsmanager.api.ILineOperation;
import com.guiji.fsmanager.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@Slf4j
public class ILineOperationController implements ILineOperation {
    @Autowired
    ILineOperationService lineOperationService;

    @Override
    public Result.ReturnData<List<LineInfoVO>> batchAddLineInfo(@RequestBody List<OutLineInfoAddReq> outLineInfoAddReqList) {
        log.info("收到批量添加线路的请求，size[{}]", outLineInfoAddReqList.size());
        List<LineInfoVO> list = lineOperationService.batchAddLineInfo(outLineInfoAddReqList);
        return Result.ok(list);
    }

    @Override
    public Result.ReturnData<List<LineInfoVO>> batchAddSimLineInfo(@RequestParam(value = "lineIp") String lineIp,@RequestParam(value = "linePort") String linePort,@RequestBody List<OutLineInfoAddReq> outLineInfoAddReqList) {
        log.info("收到批量添加单个sim卡线路的请求，lineIp[{}],linePort[{]],size[{}]", lineIp,linePort,outLineInfoAddReqList.size());
        List<LineInfoVO> list = lineOperationService.batchAddSimLineInfo(lineIp,linePort, outLineInfoAddReqList);
        return Result.ok(list);
    }

    @Override
    public Result.ReturnData batchDeleteLineInfo(@RequestBody List<Integer> lineIds) {
        lineOperationService.batchDeleteLineInfo(lineIds);
        return Result.ok();
    }

    @Override
    public Result.ReturnData addLineInfo(@RequestBody OutLineInfoAddReq outLineInfoAddReq) {
        log.info("收到创建线路的请求，outLineInfoAddReq[{}]", outLineInfoAddReq);
        Integer lineId =  lineOperationService.addLineInfo(outLineInfoAddReq);
        return Result.ok(lineId);
    }

    @Override
    public Result.ReturnData updateLineInfo(@RequestBody OutLineInfoUpdateReq outLineInfoUpdateReq) {
        log.info("收到修改线路的请求，outLineInfoUpdateReq[{}]", outLineInfoUpdateReq);
        lineOperationService.updateLineInfo(outLineInfoUpdateReq);
        return Result.ok();
    }

    @Override
    public Result.ReturnData deleteLineInfo(@RequestParam(value = "LineId") Integer lineId) {
        log.info("收到删除线路的请求，lineId[{}]", lineId);
        lineOperationService.deleteLineInfo(Integer.valueOf(lineId));
        return Result.ok();
    }

    @Override
    public Result.ReturnData<FsLineInfoVO> getFsInfoByLineId(@RequestParam(value = "lineId") Integer lineId) {
        log.info("收到根据线路id获取freeswitch的信息的请求[{}]",lineId);
        FsLineInfoVO fsLineInfoVO = lineOperationService.getFsInfoByLineId(lineId);
        return Result.ok(fsLineInfoVO);
    }

    @Override
    public Result.ReturnData<List<LineXmlnfoVO>> linexmlinfosByFsagentId(@RequestParam("serviceId") String serviceId) {
        log.info("收到根据fsagent服务的serviceId获取线路配置文件的请求[{}]",serviceId);
        List<LineXmlnfoVO> lineXmlnfoVOList= lineOperationService.linexmlinfosByFsagentId(serviceId);
        return Result.ok(lineXmlnfoVOList);
    }

    @Override
    public Result.ReturnData<List<LineXmlnfoVO>> getLinexmlinfoByLineId(@RequestParam(value = "lineId") Integer lineId) {
        log.info("收到根据lineId获取线路的配置文件请求[{}]",lineId);
        List<LineXmlnfoVO> lineXmlnfoVOList= lineOperationService.getLinexmlinfoByLineId(lineId);
        log.info("根据lineId获取线路的配置文件结果[{}]",lineXmlnfoVOList.toString());
        return Result.ok(lineXmlnfoVOList);
    }
}
