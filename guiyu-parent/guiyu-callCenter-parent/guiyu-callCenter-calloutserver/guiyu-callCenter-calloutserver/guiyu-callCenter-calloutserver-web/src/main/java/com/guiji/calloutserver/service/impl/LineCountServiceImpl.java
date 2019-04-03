package com.guiji.calloutserver.service.impl;

import com.guiji.callcenter.dao.LineCountMapper;
import com.guiji.callcenter.dao.entity.LineCount;
import com.guiji.callcenter.dao.entity.LineCountExample;
import com.guiji.calloutserver.service.LineCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: 魏驰
 * @Date: 2018/11/1 20:29
 * @Project：guiyu-parent
 * @Description:
 */
@Service
public class LineCountServiceImpl implements LineCountService {
    @Autowired
    LineCountMapper lineCountMapper;

    @Override
    public List<LineCount> findByInstanceIdAndLineId(String instanceId, Integer lineId) {
        LineCountExample example = new LineCountExample();
        LineCountExample.Criteria criteria = example.createCriteria();
        criteria.andCalloutserverIdEqualTo(instanceId);
        criteria.andLineIdEqualTo(lineId);

        return lineCountMapper.selectByExample(example);
    }
}
