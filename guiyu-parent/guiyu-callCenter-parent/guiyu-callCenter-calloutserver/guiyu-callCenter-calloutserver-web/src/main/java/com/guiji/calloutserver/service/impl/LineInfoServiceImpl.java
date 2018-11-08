package com.guiji.calloutserver.service.impl;


import com.guiji.callcenter.dao.LineInfoMapper;
import com.guiji.callcenter.dao.entity.CallOutPlan;
import com.guiji.callcenter.dao.entity.LineInfo;
import com.guiji.calloutserver.service.LineInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: 魏驰
 * @Date: 2018/11/1 15:08
 * @Project：guiyu-parent
 * @Description:
 */
@Service
public class LineInfoServiceImpl implements LineInfoService {
    @Autowired
    LineInfoMapper lineInfoMapper;

    public LineInfo getByLineId(Integer lineId) {
        LineInfo lineInfo = lineInfoMapper.selectByPrimaryKey(lineId);
        return lineInfo;
    }


}
