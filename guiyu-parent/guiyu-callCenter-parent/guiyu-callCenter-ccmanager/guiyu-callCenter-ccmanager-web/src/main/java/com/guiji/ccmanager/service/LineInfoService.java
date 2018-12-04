package com.guiji.ccmanager.service;

import com.guiji.callcenter.dao.entity.LineInfo;
import com.guiji.ccmanager.vo.LineInfo4Select;
import com.guiji.ccmanager.vo.LineInfoVO;

import java.util.List;

/**
 * @Auther: 黎阳
 * @Date: 2018/10/25 0025 17:47
 * @Description:
 */
public interface LineInfoService {

    List<LineInfo4Select> getLineInfoByCustom(String customerId, String lineName, int pageSizeInt, int pageNoInt);

    void addLineInfo(LineInfoVO lineInfoVO);

    void updateLineInfo(LineInfoVO lineInfoVO, Long userId);

    void delLineInfo(String id);

    int getLineInfoByCustomCount(String customerId, String lineName);

    List<LineInfo> outLineinfos(String customerId);
}
