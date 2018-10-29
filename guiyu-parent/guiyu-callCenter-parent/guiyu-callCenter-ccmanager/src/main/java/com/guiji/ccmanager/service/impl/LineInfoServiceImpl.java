package com.guiji.ccmanager.service.impl;

import com.guiji.callcenter.dao.LineInfoMapper;
import com.guiji.callcenter.dao.entity.LineInfo;
import com.guiji.callcenter.dao.entity.LineInfoExample;
import com.guiji.ccmanager.service.LineInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: 黎阳
 * @Date: 2018/10/25 0025 17:49
 * @Description:
 */
@Service
public class LineInfoServiceImpl implements LineInfoService {

    @Autowired
    private LineInfoMapper lineInfoMapper;

    public List<LineInfo> getLineInfoByCustom(String customerId) {

        LineInfoExample example = new LineInfoExample();
        LineInfoExample.Criteria criteria = example.createCriteria();
        criteria.andCustomerIdEqualTo(customerId);
        return lineInfoMapper.selectByExample(example);
    }

}
