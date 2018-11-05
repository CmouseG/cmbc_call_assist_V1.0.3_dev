package com.guiji.ccmanager.service;

import com.guiji.callcenter.dao.entity.LineInfo;
import com.guiji.ccmanager.vo.LineInfoVO;
import com.guiji.component.result.Result;

import java.util.List;

/**
 * @Auther: 黎阳
 * @Date: 2018/10/25 0025 17:47
 * @Description:
 */
public interface LineInfoService {

    public List<LineInfo> getLineInfoByCustom(String customerId);

    public boolean addLineInfo(LineInfoVO lineInfoVO);

    public Result.ReturnData<Boolean> updateLineInfo(LineInfoVO lineInfoVO);

    public boolean delLineInfo(String id);

    public List<LineInfo> outLineinfos(String customerId);
}
