package com.guiji.process.server.service.impl;

import com.guiji.process.server.model.DeviceProcessConstant;
import com.guiji.process.server.service.IDeviceManageService;
import com.guiji.process.server.util.DeviceProcessUtil;
import com.guiji.process.vo.CmdTypeEnum;
import com.guiji.process.vo.DeviceStatusEnum;
import com.guiji.process.vo.DeviceTypeEnum;
import com.guiji.process.vo.DeviceVO;
import com.guiji.utils.RedisUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DeviceManageService implements IDeviceManageService {

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 注册
     * @param deviceVOS 设备
     */
    @Override
    public void register(List<DeviceVO> deviceVOS) {

        DeviceVO nowDeviceVO = null;
        for (DeviceVO deviceVO:deviceVOS) {

            nowDeviceVO = getDevice(deviceVO.getType(), deviceVO.getIp(), deviceVO.getPort());

            if(nowDeviceVO == null)
            {
                continue;
            }

            // 存入数据库 TODO


            updateStatus(deviceVO.getType(), deviceVO.getIp(), deviceVO.getPort(), deviceVO.getStatus(), "");
        }
    }

    @Override
    public boolean cmd(DeviceVO deviceVO, CmdTypeEnum cmdType) {
        if(deviceVO == null || cmdType == null)
        {
            return false;
        }

        if(deviceVO.getType() == DeviceTypeEnum.AGENT)
        {
            return false;
        }

        if (deviceVO.getType() == DeviceTypeEnum.TTS && cmdType == CmdTypeEnum.RESTORE_MODEL)
        {
            // 调用TTS 的重新restore

            return true;
        }

        // 调用底层通信，发送命令

        return true;

    }

    @Override
    public DeviceStatusEnum getDeviceStatus(DeviceTypeEnum type, String ip, int port) {

        DeviceVO deviceVO = getDevice(type, ip, port);

        if (deviceVO == null)
        {
            return DeviceStatusEnum.UNKNOWN;
        }

        return deviceVO.getStatus();
    }

    @Override
    public DeviceVO getDevice(DeviceTypeEnum type, String ip, int port) {

        Map<String, DeviceVO> deviceVOMap = (Map<String, DeviceVO>) redisUtil.get(DeviceProcessConstant.ALL_DEVIECE_KEY);

        if(deviceVOMap == null)
        {
            return null;
        }

        return deviceVOMap.get(DeviceProcessUtil.getDeviceKey(type, ip, port));
    }

    @Override
    public void updateStatus(DeviceTypeEnum type, String ip, int port, DeviceStatusEnum status, String whoUsed) {

        DeviceVO deviceVO = getDevice(type, ip, port);

        if (deviceVO == null)
        {
            deviceVO = new DeviceVO();
            deviceVO.setIp(ip);
            deviceVO.setPort(port);
            deviceVO.setType(type);
        }

        if(deviceVO.getStatus() == status && StringUtils.equals(deviceVO.getWhoUsed(), whoUsed))
        {
            return;
        }

        deviceVO.setStatus(status);
        deviceVO.setWhoUsed(whoUsed);

        updateAllDeviceCachList(deviceVO);
    }

    @Override
    public void updateStatus(DeviceTypeEnum type, String ip, int port, DeviceStatusEnum status) {
        DeviceVO deviceVO = getDevice(type, ip, port);

        if (deviceVO == null)
        {
           return;
        }

        if(deviceVO.getStatus() == status)
        {
            return;
        }

        deviceVO.setStatus(status);

        updateAllDeviceCachList(deviceVO);
    }


    private void updateAllDeviceCachList(DeviceVO deviceVO)
    {
        Map<String, Object> deviceVOMap = (Map<String, Object>) redisUtil.get(DeviceProcessConstant.ALL_DEVIECE_KEY);
        if(deviceVOMap == null)
        {
            deviceVOMap = new HashMap<String, Object>();
        }

        deviceVOMap.put(DeviceProcessUtil.getDeviceKey(deviceVO.getType(), deviceVO.getIp(), deviceVO.getPort()), deviceVO);

        redisUtil.hmset(DeviceProcessConstant.ALL_DEVIECE_KEY, deviceVOMap);
    }

}
