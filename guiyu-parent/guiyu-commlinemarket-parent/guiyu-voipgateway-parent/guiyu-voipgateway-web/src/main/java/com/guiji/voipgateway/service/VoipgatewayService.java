package com.guiji.voipgateway.service;

import com.guiji.common.exception.GuiyuException;
import com.guiji.voipgateway.exception.VoipGateWayErrorEnum;
import com.guiji.voipgateway.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version V1.0
 * @Description: 语音网关服务
 * @Author: weiyunbo
 * @date 2019年1月25日 下午6:25:25
 */

@Service
public class VoipgatewayService {

    //三汇
    private final static String SANHUI = "三汇";
    //鼎信
    private final static String DINGXIN = "鼎信";

    @Autowired
    @Qualifier("dingxinService")
    ThirdGateWayService dingxinService;

    @Autowired
    @Qualifier("synwayService")
    ThirdGateWayService synwayService;

    private ThirdGateWayService getService(String gwBrand) {
        switch (gwBrand) {
            case SANHUI:
                return synwayService;
            case DINGXIN:
                return dingxinService;
            default:
                throw new GuiyuException(VoipGateWayErrorEnum.C00060001);
        }
    }

    /**
     * 根据设备名称查找设备基本信息
     *
     * @param devName
     * @return
     */
    public GwDevtbl queryCompanyByDevName(String gwBrand, String devName) {

        return getService(gwBrand).queryCompanyByDevName(devName);

    }

    /**
     * 根据公司编号查找公司信息
     *
     * @param companyId
     * @return
     */
    public Company queryCompanyById(String gwBrand, Integer companyId) {

        return getService(gwBrand).queryCompanyById(companyId);

    }

    /**
     * 根据客户编号查询客户下所有设备
     *
     * @param companyId
     * @return
     */
    public List<GwDevtbl> queryGwDevtblListByCompId(String gwBrand, Integer companyId) {

        return getService(gwBrand).queryGwDevtblListByCompId(companyId);

    }

    /**
     * 根据设备编号查询设备基本信息
     *
     * @param companyId
     * @param devId
     * @return
     */
    public GwDevtbl queryGwDevByDevId(String gwBrand, Integer companyId, Integer devId) {

        return getService(gwBrand).queryGwDevByDevId(companyId, devId);

    }

    /**
     * 根据设备编号查询该设备下所有端口情况
     *
     * @param companyId
     * @param devId
     * @return
     */
    public List<SimPort> querySimPortListByDevId(String gwBrand, Integer companyId, Integer devId) {

        return getService(gwBrand).querySimPortListByDevId(companyId, devId);

    }

    public PortStatusEnum querySipPortStatus(PortRo portRo) {

        return getService(portRo.getGwBrand()).querySimPortStatus(portRo);

    }
}
