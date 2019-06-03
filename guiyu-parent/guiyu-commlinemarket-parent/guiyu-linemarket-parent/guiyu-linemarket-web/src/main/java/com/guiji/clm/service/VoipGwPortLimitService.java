package com.guiji.clm.service;

import com.guiji.clm.dao.VoipGwPortLimitMapper;
import com.guiji.clm.dao.entity.VoipGwPortLimit;
import com.guiji.clm.dao.entity.VoipGwPortLimitExample;
import com.guiji.clm.model.PortLimitVo;
import com.guiji.clm.ro.OperPortLimitRo;
import com.guiji.clm.ro.PortLimitInfoRo;
import com.guiji.clm.vo.PortLimitInfoVo;
import com.guiji.utils.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Classname VoipGwPortLimitService
 * @Description TODO
 * @Date 2019/5/28 17:25
 * @Created by qinghua
 */
@Service
public class VoipGwPortLimitService {

    @Autowired
    VoipGwPortLimitMapper voipGwPortLimitMapper;

    /**
     * 新增
     * @param portLimitPojo
     */
    @Transactional(rollbackFor = Exception.class)
    public void insertLimitInfo(OperPortLimitRo portLimitPojo){

        List<PortLimitInfoRo> ros = portLimitPojo.getRoList();

        Integer userId = portLimitPojo.getUserId();

        Integer portId = portLimitPojo.getPortId();

        for (PortLimitInfoRo ro : ros) {


            VoipGwPortLimit limit = new VoipGwPortLimit();

            BeanUtil.copyProperties(ro, limit);

            limit.setCrtUser(userId);
            limit.setCrtTime(new Date());
            limit.setPortId(portId);

            voipGwPortLimitMapper.insert(limit);

        }

    }

    /**
     * 编辑
     * @param portLimitPojo
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateLimitInfo(OperPortLimitRo portLimitPojo){
        List<PortLimitInfoRo> ros = portLimitPojo.getRoList();

        Integer userId = portLimitPojo.getUserId();

        for (PortLimitInfoRo ro : ros) {
            VoipGwPortLimit limit = new VoipGwPortLimit();

            limit.setId(ro.getId());
            limit.setTimeLength(ro.getTimeLength());
            limit.setMaxLimit(ro.getMaxLimit());
            limit.setUpdateTime(new Date());
            limit.setUpdateUser(userId);

            voipGwPortLimitMapper.updateByPrimaryKeySelective(limit);

        }
    }

    /**
     * 前端页面点编辑时展示的详情
     * @param portId
     * @return
     */
    public List<PortLimitInfoVo> queryPortLimitByPortId(Integer portId) {

        VoipGwPortLimitExample example = new VoipGwPortLimitExample();

        example.createCriteria().andPortIdEqualTo(portId);

        List<VoipGwPortLimit> voipGwPortLimits = voipGwPortLimitMapper.selectByExample(example);

        if(CollectionUtils.isEmpty(voipGwPortLimits)) {
            return new ArrayList<>();
        } else {
            List<PortLimitInfoVo> voList = new ArrayList<>();

            for (VoipGwPortLimit voipGwPortLimit : voipGwPortLimits) {

                PortLimitInfoVo vo = new PortLimitInfoVo();

                vo.setId(voipGwPortLimit.getId());
                vo.setLimitType(voipGwPortLimit.getLimitType());
                vo.setMaxLimit(voipGwPortLimit.getMaxLimit());
                vo.setTimeLength(voipGwPortLimit.getTimeLength());

                voList.add(vo);
            }

            return voList;
        }

    }


    /**
     * 呼叫中心根据lineId调用查询
     * @param portId
     * @return
     */
    public List<PortLimitVo> queryPortLimitByLineId(Integer lineId) {

        VoipGwPortLimitExample example = new VoipGwPortLimitExample();

        example.createCriteria().andLineIdEqualTo(lineId);

        List<VoipGwPortLimit> voipGwPortLimits = voipGwPortLimitMapper.selectByExample(example);

        if(CollectionUtils.isEmpty(voipGwPortLimits)) {
            return new ArrayList<>();
        } else {
            List<PortLimitVo> voList = new ArrayList<>();

            for (VoipGwPortLimit voipGwPortLimit : voipGwPortLimits) {

                PortLimitVo vo = new PortLimitVo();

                vo.setLimitType(voipGwPortLimit.getLimitType());
                vo.setMaxLimit(voipGwPortLimit.getMaxLimit());
                vo.setTimeLength(voipGwPortLimit.getTimeLength());

                voList.add(vo);
            }

            return voList;
        }

    }
}
