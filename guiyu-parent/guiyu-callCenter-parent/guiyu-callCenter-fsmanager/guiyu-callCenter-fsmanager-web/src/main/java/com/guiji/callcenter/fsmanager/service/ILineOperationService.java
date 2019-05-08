package com.guiji.callcenter.fsmanager.service;

import com.guiji.fsmanager.entity.*;

import java.util.List;

public interface ILineOperationService {
    List<LineInfoVO> batchAddLineInfo(List<OutLineInfoAddReq> outLineInfoAddReqList);

    Integer addLineInfo(OutLineInfoAddReq outLineInfoAddReq);

    void updateLineInfo(OutLineInfoUpdateReq outLineInfoUpdateReq);

    void deleteLineInfo(Integer lineId);

    FsLineInfoVO getFsInfoByLineId(Integer LineId);

    List<LineXmlnfoVO> linexmlinfosByFsagentId(String serviceId);

    List<LineXmlnfoVO> getLinexmlinfoByLineId(Integer LineId);

    void  batchDeleteLineInfo(List<Integer> lineIds);

    List<LineInfoVO> batchAddSimLineInfo( String lineIp, String linePort,List<OutLineInfoAddReq> outLineInfoAddReqList);

    void initToRedis();

}
