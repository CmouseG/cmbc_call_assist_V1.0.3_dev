package com.guiji.ccmanager.service;

import com.guiji.callcenter.dao.entity.LineInfo;

import java.util.List;

/**
 * @Auther: 黎阳
 * @Date: 2018/10/25 0025 17:47
 * @Description:
 */
public interface LineInfoService {

    public List<LineInfo> getLineInfoByCustom(String customerId);


}
