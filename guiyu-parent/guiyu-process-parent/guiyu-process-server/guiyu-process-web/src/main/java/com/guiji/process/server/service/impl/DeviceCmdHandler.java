package com.guiji.process.server.service.impl;

import com.guiji.process.vo.DeviceMsgVO;
import com.guiji.process.vo.DeviceVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeviceCmdHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DeviceManageService deviceManageService;

    public void excute(DeviceMsgVO deviceMsgVO)
    {
        if(deviceMsgVO == null)
        {
            return;
        }

        switch (deviceMsgVO.getCmdType()) {

            case REGISTER:

                doRegister(deviceMsgVO);
                break;

            case RESTART:

                break;

            case UNKNOWN:
                break;

            case START:
                break;

            case STOP:
                break;

            case HEALTH:

                doHealthStatus(deviceMsgVO);
                break;

                default:
                    break;
        }

    }


    private void doHealthStatus(DeviceMsgVO deviceMsgVO)
    {
        DeviceVO oldDeviceVO = deviceManageService.getDevice(deviceMsgVO.getType(), deviceMsgVO.getIp(), deviceMsgVO.getPort());
        if(oldDeviceVO == null)
        {
            // 未注册过，不做
            return;
        }

        DeviceVO deviceVO = new DeviceVO();
        deviceVO.setIp(deviceMsgVO.getIp());
        deviceVO.setPort(deviceMsgVO.getPort());
        deviceVO.setType(deviceMsgVO.getType());
        deviceVO.setStatus(deviceMsgVO.getStatus());

        deviceManageService.updateStatus(deviceMsgVO.getType(), deviceMsgVO.getIp(), deviceMsgVO.getPort(), deviceVO.getStatus());
    }


    private void doRegister(DeviceMsgVO deviceMsgVO)
    {
        DeviceVO oldDeviceVO = deviceManageService.getDevice(deviceMsgVO.getType(), deviceMsgVO.getIp(), deviceMsgVO.getPort());
        if(oldDeviceVO != null)
        {
            return;
        }

        DeviceVO deviceVO = new DeviceVO();
        deviceVO.setIp(deviceMsgVO.getIp());
        deviceVO.setPort(deviceMsgVO.getPort());
        deviceVO.setType(deviceMsgVO.getType());

        // 首次上线，直接更新缓存中的状态
        List<DeviceVO> lst = new ArrayList<DeviceVO>();
        lst.add(deviceVO);

        deviceManageService.register(lst);

    }

}
