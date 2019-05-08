//package com.guiji.ccmanager.service;
//
//import com.guiji.callcenter.dao.entity.LineInfo;
//import com.guiji.ccmanager.vo.LineInfo4AllotRes;
//import com.guiji.ccmanager.vo.LineInfo4Page;
//import com.guiji.ccmanager.vo.LineInfoAddVO;
//import com.guiji.ccmanager.vo.LineInfoUpdateVO;
//
//import java.util.List;
//
///**
// * @Auther: 黎阳
// * @Date: 2018/10/25 0025 17:47
// * @Description:
// */
//public interface LineInfoService {
//
//    List<LineInfo4Page> getLineInfoByCustom(Boolean isSuperAdmin,String customerId,String orgCode, String lineName, int pageSizeInt, int pageNoInt);
//
//    int getLineInfoByCustomCount(Boolean isSuperAdmin,String customerId,String orgCode, String lineName);
//
//    List<LineInfo> outLineinfos(String customerId);
//
//    List<LineInfo4AllotRes> getLineInfos4Allot(String customerId);
//
//    void allotLineInfo(String customerId, String lineIds);
//
//    List<LineInfo> getLineInfoByOrgCode(String orgCode);
//
//    LineInfo getLineInfoById(Integer lineId);
//}
////