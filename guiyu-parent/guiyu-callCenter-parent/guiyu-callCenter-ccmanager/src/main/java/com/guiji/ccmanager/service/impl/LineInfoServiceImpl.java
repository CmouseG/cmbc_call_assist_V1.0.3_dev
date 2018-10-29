package com.guiji.ccmanager.service.impl;

import com.guiji.callcenter.dao.LineCountMapper;
import com.guiji.callcenter.dao.LineInfoMapper;
import com.guiji.callcenter.dao.entity.LineCountExample;
import com.guiji.callcenter.dao.entity.LineInfo;
import com.guiji.callcenter.dao.entity.LineInfoExample;
import com.guiji.ccmanager.feign.LineOperApiFeign;
import com.guiji.ccmanager.service.LineInfoService;
import com.guiji.ccmanager.vo.LineInfoVO;
import com.guiji.common.result.Result;
import com.guiji.utils.BeanUtil;
import com.guiji.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Autowired
    private LineOperApiFeign lineOperApiFeign;
    @Autowired
    private LineCountMapper lineCountMapper;

    public List<LineInfo> getLineInfoByCustom(String customerId) {

        LineInfoExample example = new LineInfoExample();
        LineInfoExample.Criteria criteria = example.createCriteria();
        criteria.andCustomerIdEqualTo(customerId);
        return lineInfoMapper.selectByExample(example);
    }

    @Override
    @Transactional
    public void addLineInfo(LineInfoVO lineInfoVO) {

        //本地存储数据库lineinfo
        LineInfo lineInfo = new LineInfo();
        BeanUtil.copyProperties(lineInfoVO,lineInfo);
        lineInfo.setCreateDate(DateUtil.getCurrentTime());
        lineInfoMapper.insert(lineInfo);

        //调用fsmanager的增加线路接口
        com.guiji.fsmanager.entity.LineInfo lineInfoApi = new com.guiji.fsmanager.entity.LineInfo();
        BeanUtil.copyProperties(lineInfoVO,lineInfoApi);
        Result.ReturnData result = lineOperApiFeign.addLineinfos(lineInfoApi);


        //读取所有的calloutserver，将线路并发数平分到各个calloutserver，存储到linecount表中

    }

    @Override
    @Transactional
    public void updateLineInfo(LineInfoVO lineInfoVO) {

        //本地更新数据库lineinfo
        LineInfo lineInfo = new LineInfo();
        BeanUtil.copyProperties(lineInfoVO,lineInfo);
        lineInfo.setUpdateDate(DateUtil.getCurrentTime());
        lineInfoMapper.updateByPrimaryKey(lineInfo);
        //调用fsmanager的更新线路接口
        com.guiji.fsmanager.entity.LineInfo lineInfoApi = new com.guiji.fsmanager.entity.LineInfo();
        BeanUtil.copyProperties(lineInfoVO,lineInfoApi);
        Result.ReturnData result = lineOperApiFeign.editLineinfos(String.valueOf(lineInfoApi.getLineId()),lineInfoApi);
        //若并发数有更新，则从linecount表读取线路并发数分配信息，并根据重新计算的并发数进行修改保存。

    }

    @Override
    @Transactional
    public void delLineInfo(String id) {
        //本地删除数据库lineinfo记录，同时调用fsmanager的删除线路接口
        lineInfoMapper.deleteByPrimaryKey(Integer.valueOf(id));
        lineOperApiFeign.deleteLineinfos(id);
        //删除linecount中对应的线路并发数信息
        LineCountExample example = new LineCountExample();
        LineCountExample.Criteria criteria = example.createCriteria();
        criteria.andLineIdEqualTo(Integer.valueOf(id));
        lineCountMapper.deleteByExample(example);
    }
}
