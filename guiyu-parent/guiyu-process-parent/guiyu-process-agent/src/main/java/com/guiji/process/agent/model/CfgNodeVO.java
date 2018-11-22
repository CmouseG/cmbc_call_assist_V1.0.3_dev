package com.guiji.process.agent.model;

import com.guiji.process.core.vo.CmdTypeEnum;
import com.guiji.process.core.vo.DeviceTypeEnum;

import java.util.List;

public class CfgNodeVO {

    private String name;

    private int port;

    private DeviceTypeEnum deviceTypeEnum;

    private List<CfgNodeOperVO> CfgNodeOpers;

    private String processKey;


    public DeviceTypeEnum getDeviceTypeEnum() {
        return deviceTypeEnum;
    }

    public void setDeviceTypeEnum(Integer deviceTypeEnum) {
        this.deviceTypeEnum = DeviceTypeEnum.valueOf(deviceTypeEnum);
    }

    public void setDeviceTypeEnum(String deviceTypeEnum) {
        this.deviceTypeEnum = DeviceTypeEnum.valueOf(deviceTypeEnum);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public List<CfgNodeOperVO> getCfgNodeOpers() {
        return CfgNodeOpers;
    }

    public void setCfgNodeOpers(List<CfgNodeOperVO> cfgNodeOpers) {
        CfgNodeOpers = cfgNodeOpers;
    }

    public String getProcessKey() {
        return processKey;
    }

    public void setProcessKey(String processKey) {
        this.processKey = processKey;
    }


    public CfgNodeOperVO getCfgNodeOper(CmdTypeEnum cmdTypeEnum)
    {
        if(this.CfgNodeOpers== null)
        {
            return null;
        }

        for (CfgNodeOperVO cfgNodeOperVO: CfgNodeOpers) {
            if(cfgNodeOperVO.getCmdTypeEnum()== cmdTypeEnum)
            {
                return cfgNodeOperVO;
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return "CfgNodeVO{" +
                "name='" + name + '\'' +
                ", port=" + port +
                ", deviceTypeEnum=" + deviceTypeEnum +
                ", CfgNodeOpers=" + CfgNodeOpers +
                ", processKey='" + processKey + '\'' +
                '}';
    }
}
