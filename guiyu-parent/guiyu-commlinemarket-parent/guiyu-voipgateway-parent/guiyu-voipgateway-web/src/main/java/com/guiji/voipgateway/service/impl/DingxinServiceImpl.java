package com.guiji.voipgateway.service.impl;

import com.guiji.voipgateway.dingxin.dao.*;
import com.guiji.voipgateway.dingxin.dao.entity.*;
import com.guiji.voipgateway.model.*;
import com.guiji.voipgateway.service.ThirdGateWayService;
import groovy.util.logging.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 鼎信语音网关对接
 */

@Slf4j
@Service("dingxinService")
public class DingxinServiceImpl implements ThirdGateWayService {

    @Autowired
    TblNeMapper tblNeMapper;

    @Autowired
    TblGwMapper tblGwMapper;

    @Override
    public GwDevtbl queryCompanyByDevName(String devName) {

        TblNeExample tblNeExample = new TblNeExample();

        tblNeExample.createCriteria().andAliasEqualTo(devName);

        List<TblNe> tblNes = tblNeMapper.selectByExample(tblNeExample);

        if (CollectionUtils.isEmpty(tblNes)) return null;

        Integer uuid = tblNes.get(0).getUuid();

        TblGwExample tblGwExample = new TblGwExample();

        tblGwExample.createCriteria().andNeUuidEqualTo(uuid);

        List<TblGw> tblGws = tblGwMapper.selectByExample(tblGwExample);

        if (CollectionUtils.isEmpty(tblGws)) return null;

        TblGw tblGw = tblGws.get(0);

        GwDevtbl gwDevtbl = new GwDevtbl();

        gwDevtbl.setCompanyId(tblGw.getDefaultGrpUuid());
        gwDevtbl.setDevId(tblGw.getNeUuid());

        return gwDevtbl;
    }

    @Override
    public Company queryCompanyById(Integer companyId) {
        return null;
    }

    @Override
    public List<GwDevtbl> queryGwDevtblListByCompId(Integer companyId) {

        return null;
    }

    @Override
    public GwDevtbl queryGwDevByDevId(Integer companyId, Integer devId) {
        //设备是否启用adminStatus
        //工作情况runStatus
        //总端口数portTotalCount
        //工作端口数portWorkCount

        TblNe tblNe = tblNeMapper.selectByPrimaryKey(devId);

        GwDevtbl gwDevtbl = new GwDevtbl();

        //ENABLE/DISABLE/LOCKED/NO_BALANCE
        gwDevtbl.setBeEnable(tblNe.getAdminStatus() != 2 ? true : false);
        //INIT/AUTH/ACTIVE/FAULT/COMM_FAIL
        gwDevtbl.setWorkStatusId(tblNe.getRunStatus() == 3 ? 1 : 2);

        TblPortExample tblPortExample = new TblPortExample();

        tblPortExample.createCriteria().andNeUuidEqualTo(devId);

        List<TblPort> tblPorts = tblPortMapper.selectByExample(tblPortExample);

        int busy = 0;
        int idle = 0;
        //IDLE-10 BUSY-11
        for (TblPort tblPort : tblPorts) {
            if (tblPort.getRunStatus() == 10) {
                idle++;
            }
            if (tblPort.getRunStatus() == 11) {
                busy++;
            }
        }

        gwDevtbl.setChUseNum(busy);
        gwDevtbl.setIdleChNum(idle);
        gwDevtbl.setChPutNum(busy + idle);

        return gwDevtbl;
    }

    @Autowired
    TblPortMapper tblPortMapper;

    @Autowired
    TblGwpMapper tblGwpMapper;

    @Autowired
    TblSimMapper tblSimMapper;

    @Override
    public List<SimPort> querySimPortListByDevId(Integer companyId, Integer devId) {

        //端口注册状态
        TblPortExample tblPortExample = new TblPortExample();

        tblPortExample.createCriteria().andNeUuidEqualTo(devId);

        List<TblPort> tblPorts = tblPortMapper.selectByExample(tblPortExample);

        if(CollectionUtils.isEmpty(tblPorts)) {
            return new ArrayList<>();
        }

        List<Integer> ids = Lists.newArrayList();
        Map<Integer, TblPort> map = new HashMap<>();

        tblPorts.forEach(obj -> {
            ids.add(obj.getUuid());

            map.put(obj.getUuid(), obj);
        });

        TblGwpExample tblGwpExample = new TblGwpExample();

        tblGwpExample.createCriteria().andPortUuidIn(ids);

        List<TblGwp> tblGwps = tblGwpMapper.selectByExample(tblGwpExample);

        if(CollectionUtils.isEmpty(tblGwps)) {
            return new ArrayList<>();
        }

        List<SimPort> simPorts = Lists.newArrayList();

        for (TblGwp tblGwp : tblGwps) {

            SimPort simPort = new SimPort();
            if (tblGwp.getLocalSimUuid() == 0) {
                continue;
            }
            TblSim tblSim = tblSimMapper.selectByPrimaryKey(tblGwp.getLocalSimUuid());

            simPort.setRegStatusId(map.get(tblGwp.getPortUuid()).getOprStatus() == 1 ? 1:0 );
            simPort.setWorkStatusId(map.get(tblGwp.getPortUuid()).getRunStatus() == 10 ? 1 : 0);
            simPort.setPortNumber(map.get(tblGwp.getPortUuid()).getPortNo());
            simPort.setLoadType(tblGwp.getModSignalLevel());
            simPort.setPhoneNumber(tblSim.getMobile());
            simPort.setConnectionStatus(1);

            simPorts.add(simPort);
        }

        return simPorts;
    }

    @Override
    public PortStatusEnum querySimPortStatus(PortRo ro) {

        return PortStatusEnum.IDLE;

//        TblPortExample tblPortExample = new TblPortExample();
//
//        tblPortExample.createCriteria().andNeUuidEqualTo(ro.getDevId());
//
//        List<TblPort> tblPorts = tblPortMapper.selectByExample(tblPortExample);
//
//        TblPort port = null;
//
//        for (TblPort obj : tblPorts) {
//            if(obj.getPortNo().equals(ro.getPortNo())){
//                port = obj;
//            }
//        }
//
//        TblGwpExample tblGwpExample = new TblGwpExample();
//
//        tblGwpExample.createCriteria().andPortUuidEqualTo(port.getUuid());
//
//        List<TblGwp> tblGwps = tblGwpMapper.selectByExample(tblGwpExample);
//
//        TblGwp tblGwp = tblGwps.get(0);
//
//        //	1 初始化
//        //	5 空的
//        //	6 通信失败（端口错误）
//        //	10 空闲
//        //	11 忙碌
//        //	23 关机
//        Integer runStatus = port.getRunStatus();
//
//        //	0 无卡
//        //	3 未注册
//        //	5 注册成功
//        //	9 关机+
//        Integer modStatus = tblGwp.getModStatus();
//
//        if(modStatus == 5) {
//            switch (runStatus){
//                case 10:
//                    return PortStatusEnum.IDLE;
//                case 11:
//                    return PortStatusEnum.BUSY;
//                case 5:
//                    return PortStatusEnum.EMPTY;
//                case 6:
//                    return PortStatusEnum.ERROR_CONN;
//                case 23:
//                    return PortStatusEnum.CLOSED;
//            }
//        } else if (modStatus == 0) {
//            return PortStatusEnum.NO_SIM;
//        } else if (modStatus == 3) {
//            return PortStatusEnum.NOT_REGIST;
//        } else if (modStatus == 9) {
//            return PortStatusEnum.CLOSED;
//        } else {
//            return PortStatusEnum.OTHER;
//        }
//
//
//        return null;
    }
}
