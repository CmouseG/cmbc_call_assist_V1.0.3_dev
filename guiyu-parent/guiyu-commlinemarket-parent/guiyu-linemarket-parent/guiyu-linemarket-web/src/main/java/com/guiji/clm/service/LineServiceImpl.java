package com.guiji.clm.service;

import com.guiji.clm.constant.ClmConstants;
import com.guiji.clm.dao.SipLineBaseInfoMapper;
import com.guiji.clm.dao.SipLineExclusiveMapper;
import com.guiji.clm.dao.VoipGwPortMapper;
import com.guiji.clm.dao.entity.*;
import com.guiji.clm.exception.ClmErrorEnum;
import com.guiji.clm.exception.ClmException;
import com.guiji.clm.model.SipLineVO;
import com.guiji.utils.JsonUtils;
import com.guiji.utils.RedisUtil;
import com.guiji.utils.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Classname LineServiceImpl
 * @Description 查询线路信息
 * @Date 2019/6/10 10:12
 * @Created by qinghua
 */
@Service
public class LineServiceImpl implements LineService {

    @Autowired
    VoipGwPortMapper voipGwPortMapper;

    @Autowired
    SipLineExclusiveMapper sipLineExclusiveMapper;

    @Autowired
    SipLineBaseInfoMapper sipLineBaseInfoMapper;

    @Autowired
    RedisUtil redisUtil;

    @Override
    public SipLineVO queryLineInfo(Integer userId, Integer lineId) {

        Object obj = redisUtil.hget(ClmConstants.LINE_INFO_MAP, lineId.toString());

        if(obj != null) {

            SipLineVO vo = (SipLineVO) obj;

            if(vo.getUserId().equals(userId.toString())) {
                return vo;
            }
        }

        //查询已分配
        SipLineExclusiveExample example = new SipLineExclusiveExample();
        example.createCriteria().andBelongUserEqualTo(userId.toString()).andLineIdEqualTo(lineId);
        List<SipLineExclusive> list = sipLineExclusiveMapper.selectByExample(example);

        if (!CollectionUtils.isEmpty(list)) {
            SipLineVO sipLineVO = new SipLineVO();

            sipLineVO.setLineId(lineId);
            sipLineVO.setLineName(list.get(0).getLineName());
            sipLineVO.setUserId(userId.toString());

            redisUtil.hset(ClmConstants.LINE_INFO_MAP, lineId.toString(), sipLineVO);

            return sipLineVO;
        }

        //查询网关
        VoipGwPortExample voipGwPortExample = new VoipGwPortExample();

        voipGwPortExample.createCriteria().andUserIdEqualTo(userId.toString()).andLineIdEqualTo(lineId);

        List<VoipGwPort> voipGwPorts = voipGwPortMapper.selectByExample(voipGwPortExample);

        if(!CollectionUtils.isEmpty(voipGwPorts)) {

            SipLineVO vo = new SipLineVO();

            vo.setUserId(userId.toString());
            vo.setLineId(lineId);

            if(StrUtils.isNotEmpty(voipGwPorts.get(0).getPhoneNo())) {
                //卡线如果有手机号，那么返回手机号
                vo.setLineName(voipGwPorts.get(0).getPhoneNo());
            }else {
                vo.setLineName("网关端口"+voipGwPorts.get(0).getPort());
            }

            redisUtil.hset(ClmConstants.LINE_INFO_MAP, lineId.toString(), vo);

            return vo;
        }

        //查询基础线路
        SipLineBaseInfoExample sipLineBaseInfoExample = new SipLineBaseInfoExample();

        sipLineBaseInfoExample.createCriteria().andLineIdEqualTo(lineId);

        List<SipLineBaseInfo> sipLineBaseInfos = sipLineBaseInfoMapper.selectByExample(sipLineBaseInfoExample);

        if(!CollectionUtils.isEmpty(voipGwPorts)) {

            SipLineVO vo = new SipLineVO();

            vo.setUserId(userId.toString());
            vo.setLineId(lineId);
            vo.setLineName(sipLineBaseInfos.get(0).getLineName());

            redisUtil.hset(ClmConstants.LINE_INFO_MAP, lineId.toString(), vo);

            return vo;
        }

        throw new ClmException(ClmErrorEnum.CLM1809319.getErrorCode(), ClmErrorEnum.CLM1809319.getErrorMsg());
    }

    @Override
    public void updateLineInfo(SipLineExclusive exclusive, int op) {

        if (ClmConstants.DEL == op) {
            redisUtil.hdel(ClmConstants.LINE_INFO_MAP, exclusive.getLineId().toString());
        }

        SipLineVO sipLineVO = new SipLineVO();

        sipLineVO.setLineId(exclusive.getLineId());
        sipLineVO.setLineName(exclusive.getLineName());
        sipLineVO.setUserId(exclusive.getBelongUser());

        redisUtil.hset(ClmConstants.LINE_INFO_MAP, exclusive.getLineId().toString(), sipLineVO);

    }

    @Override
    public void updateLineInfo(VoipGwPort voipGwPort, int op) {

        String userId = voipGwPort.getUserId();
        Integer lineId = voipGwPort.getLineId();

        if (ClmConstants.DEL == op) {
            redisUtil.hdel(ClmConstants.LINE_INFO_MAP, lineId.toString());
        }

        SipLineVO vo = new SipLineVO();

        vo.setUserId(userId);
        vo.setLineId(lineId);

        if(StrUtils.isNotEmpty(voipGwPort.getPhoneNo())) {
            //卡线如果有手机号，那么返回手机号
            vo.setLineName(voipGwPort.getPhoneNo());
        }else {
            vo.setLineName("网关端口"+voipGwPort.getPort());
        }

        redisUtil.hset(ClmConstants.LINE_INFO_MAP, lineId.toString(), vo);
    }

}
