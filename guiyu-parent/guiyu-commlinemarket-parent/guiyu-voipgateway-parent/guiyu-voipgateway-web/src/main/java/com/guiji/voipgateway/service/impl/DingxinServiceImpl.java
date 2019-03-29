package com.guiji.voipgateway.service.impl;

import com.guiji.voipgateway.dingxin.dao.*;
import com.guiji.voipgateway.dingxin.dao.entity.*;
import com.guiji.voipgateway.model.Company;
import com.guiji.voipgateway.model.GwDevtbl;
import com.guiji.voipgateway.model.SimPort;
import com.guiji.voipgateway.service.ThirdGateWayService;
import groovy.util.logging.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        String productSn = devName.replace("-", "");

        TblNe tblNe = tblNeMapper.selectByProductSn(productSn);

        if (tblNe == null) return null;

        Integer uuid = tblNe.getUuid();

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
            if (tblPort.getRunStatus() == 10) idle++;
            if (tblPort.getRunStatus() == 11) busy++;
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

        List<Integer> ids = Lists.newArrayList();
        Map<Integer, TblPort> map = new HashMap<>();

        tblPorts.forEach(obj -> {
            ids.add(obj.getUuid());

            map.put(obj.getUuid(), obj);
        });

        TblGwpExample tblGwpExample = new TblGwpExample();

        tblGwpExample.createCriteria().andPortUuidIn(ids);

        List<TblGwp> tblGwps = tblGwpMapper.selectByExample(tblGwpExample);

        List<SimPort> simPorts = Lists.newArrayList();

        for (TblGwp tblGwp : tblGwps) {

            SimPort simPort = new SimPort();
            if (tblGwp.getLocalSimUuid() == 0) continue;
            TblSim tblSim = tblSimMapper.selectByPrimaryKey(tblGwp.getLocalSimUuid());

            simPort.setPortNumber(map.get(tblGwp.getPortUuid()).getPortNo());
            simPort.setLoadType(tblGwp.getModSignalLevel());
            simPort.setPhoneNumber(tblSim.getMobile());
            simPort.setConnectionStatus(1);

            simPorts.add(simPort);
        }

        return simPorts;
    }
}
