package com.guiji.ccmanager.service;

import com.guiji.callcenter.dao.entity.LineInfo;
import com.guiji.ccmanager.vo.LineInfoVO;

import java.util.List;

/**
 * @Auther: 黎阳
 * @Date: 2018/10/25 0025 17:47
 * @Description:
 */
public interface LineInfoService {

    public List<LineInfo> getLineInfoByCustom(String customerId);

    public void addLineInfo(LineInfoVO lineInfoVO);

    public void updateLineInfo(LineInfoVO lineInfoVO);

    public void delLineInfo(String id);
}
