package com.guiji.process.server.service;

import com.guiji.process.vo.CmdTypeEnum;
import com.guiji.process.vo.DeviceStatusEnum;
import com.guiji.process.vo.DeviceTypeEnum;
import com.guiji.process.vo.DeviceVO;

import java.util.List;

public interface IDeviceManageService {

    /**
     * 注册
     * @param deviceVOS 设备
     * @return
     */
    void register(List<DeviceVO> deviceVOS);


    /**
     * 对设备处理
     * @param deviceVO 设备
     * @param cmdType 对设备处理
     * @return
     */
    boolean cmd(DeviceVO deviceVO, CmdTypeEnum cmdType);

    /**
     * 获取设备的状态
     * @param ip ip
     * @param port port
     * @return
     */
    DeviceStatusEnum getDeviceStatus(DeviceTypeEnum type, String ip, int port);


    /**
     * 获取设备
     * @param ip ip
     * @param port port
     * @return
     */
    DeviceVO getDevice(DeviceTypeEnum type, String ip, int port);


    /**
     * 更新设备状态
     * @param ip ip
     * @param port port
     * @param status port
     * @return
     */
    void updateStatus(DeviceTypeEnum type, String ip, int port, DeviceStatusEnum status, String whoUsed);


    /**
     * 更新设备状态
     * @param ip ip
     * @param port port
     * @param status port
     * @return
     */
    void updateStatus(DeviceTypeEnum type, String ip, int port, DeviceStatusEnum status);
}
