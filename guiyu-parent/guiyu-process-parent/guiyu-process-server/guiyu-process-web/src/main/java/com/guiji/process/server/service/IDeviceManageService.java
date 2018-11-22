package com.guiji.process.server.service;

import com.guiji.process.core.vo.CmdTypeEnum;
import com.guiji.process.core.vo.DeviceStatusEnum;
import com.guiji.process.core.vo.DeviceTypeEnum;
import com.guiji.process.core.vo.ProcessInstanceVO;

import java.util.List;

public interface IDeviceManageService {

    /**
     * 注册
     * @param processInstanceVOS 设备
     * @return
     */
    void register(List<ProcessInstanceVO> processInstanceVOS);

    /**
     * 注销
     * @param processInstanceVOS 设备
     * @return
     */
    void unRegister(List<ProcessInstanceVO> processInstanceVOS);


    /**
     * 对设备处理
     * @param processInstanceVO 设备
     * @param cmdType 对设备处理
     * @return
     */
    boolean cmd(ProcessInstanceVO processInstanceVO, CmdTypeEnum cmdType);

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
    ProcessInstanceVO getDevice(DeviceTypeEnum type, String ip, int port);


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
    void updateUnRegister(DeviceTypeEnum type, String ip, int port, DeviceStatusEnum status, String whoUsed);



    /**
     * 更新设备状态
     * @param ip ip
     * @param port port
     * @param status port
     * @return
     */
    void updateStatus(DeviceTypeEnum type, String ip, int port, DeviceStatusEnum status);
}
