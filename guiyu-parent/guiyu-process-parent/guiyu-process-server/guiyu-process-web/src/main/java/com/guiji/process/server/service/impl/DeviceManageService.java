package com.guiji.process.server.service.impl;

import com.guiji.process.core.vo.CmdTypeEnum;
import com.guiji.process.core.vo.DeviceStatusEnum;
import com.guiji.process.core.vo.DeviceTypeEnum;
import com.guiji.process.core.vo.ProcessInstanceVO;
import com.guiji.process.server.model.DeviceProcessConstant;
import com.guiji.process.server.service.IDeviceManageService;
import com.guiji.process.server.util.DeviceProcessUtil;

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
     * @param processInstanceVOS 设备
     */
    @Override
    public void register(List<ProcessInstanceVO> processInstanceVOS) {

        ProcessInstanceVO nowProcessInstanceVO = null;
        for (ProcessInstanceVO processInstanceVO : processInstanceVOS) {

            nowProcessInstanceVO = getDevice(processInstanceVO.getType(), processInstanceVO.getIp(), processInstanceVO.getPort());

            if(nowProcessInstanceVO != null)
            {
                continue;
            }

            // 存入数据库 TODO


            updateStatus(processInstanceVO.getType(), processInstanceVO.getIp(), processInstanceVO.getPort(), processInstanceVO.getStatus(), "");
        }
    }

    @Override
    public boolean cmd(ProcessInstanceVO processInstanceVO, CmdTypeEnum cmdType) {
        if(processInstanceVO == null || cmdType == null)
        {
            return false;
        }

        if(processInstanceVO.getType() == DeviceTypeEnum.AGENT)
        {
            return false;
        }

        if (processInstanceVO.getType() == DeviceTypeEnum.TTS && cmdType == CmdTypeEnum.RESTORE_MODEL)
        {
            // 调用TTS 的重新restore

            return true;
        }

        // 调用底层通信，发送命令

        return true;

    }

    @Override
    public DeviceStatusEnum getDeviceStatus(DeviceTypeEnum type, String ip, int port) {

        ProcessInstanceVO processInstanceVO = getDevice(type, ip, port);

        if (processInstanceVO == null)
        {
            return DeviceStatusEnum.UNKNOWN;
        }

        return processInstanceVO.getStatus();
    }

    @Override
    public ProcessInstanceVO getDevice(DeviceTypeEnum type, String ip, int port) {

        Map<String, ProcessInstanceVO> deviceVOMap = (Map<String, ProcessInstanceVO>) redisUtil.get(DeviceProcessConstant.ALL_DEVIECE_KEY);

        if(deviceVOMap == null)
        {
            return null;
        }

        return deviceVOMap.get(DeviceProcessUtil.getDeviceKey(type, ip, port));
    }

    @Override
    public void updateStatus(DeviceTypeEnum type, String ip, int port, DeviceStatusEnum status, String whoUsed) {

        ProcessInstanceVO processInstanceVO = getDevice(type, ip, port);

        if (processInstanceVO == null)
        {
            processInstanceVO = new ProcessInstanceVO();
            processInstanceVO.setIp(ip);
            processInstanceVO.setPort(port);
            processInstanceVO.setType(type);
        }

        if(processInstanceVO.getStatus() == status && StringUtils.equals(processInstanceVO.getWhoUsed(), whoUsed))
        {
            return;
        }

        processInstanceVO.setStatus(status);
        processInstanceVO.setWhoUsed(whoUsed);

        updateAllDeviceCachList(processInstanceVO);
    }

    @Override
    public void updateStatus(DeviceTypeEnum type, String ip, int port, DeviceStatusEnum status) {
        ProcessInstanceVO processInstanceVO = getDevice(type, ip, port);

        if (processInstanceVO == null)
        {
            return;
        }

        if(processInstanceVO.getStatus() == status)
        {
            return;
        }

        processInstanceVO.setStatus(status);

        updateAllDeviceCachList(processInstanceVO);
    }


    private void updateAllDeviceCachList(ProcessInstanceVO processInstanceVO)
    {
        Map<String, Object> deviceVOMap = (Map<String, Object>) redisUtil.get(DeviceProcessConstant.ALL_DEVIECE_KEY);
        if(deviceVOMap == null)
        {
            deviceVOMap = new HashMap<String, Object>();
        }

        deviceVOMap.put(DeviceProcessUtil.getDeviceKey(processInstanceVO.getType(), processInstanceVO.getIp(), processInstanceVO.getPort()), processInstanceVO);

        redisUtil.hmset(DeviceProcessConstant.ALL_DEVIECE_KEY, deviceVOMap);
    }

}
