package com.guiji.process.server.service;

import com.guiji.common.model.process.ProcessStatusEnum;
import com.guiji.common.model.process.ProcessTypeEnum;
import com.guiji.process.core.vo.CmdTypeEnum;
import com.guiji.common.model.process.ProcessInstanceVO;

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
    boolean cmd(ProcessInstanceVO processInstanceVO, CmdTypeEnum cmdType, List<String> parameters);

    /**
     * 获取设备的状态
     * @param ip ip
     * @param port port
     * @return
     */
    ProcessStatusEnum getDeviceStatus(ProcessTypeEnum type, String ip, int port);


    /**
     * 获取设备
     * @param ip ip
     * @param port port
     * @return
     */
    ProcessInstanceVO getDevice(ProcessTypeEnum type, String ip, int port);


    /**
     * 更新设备状态
     * @param processInstanceVO processInstanceVO
     * @return
     */
    void updateStatus(ProcessInstanceVO processInstanceVO);

    /**
     * 更新设备状态
     * @param ip ip
     * @param port port
     * @param status port
     * @return
     */
    void updateUnRegister(ProcessTypeEnum type, String ip, int port, ProcessStatusEnum status, String whoUsed);



    /**
     * 更新设备状态
     * @param ip ip
     * @param port port
     * @param status port
     * @return
     */
    void updateStatus(ProcessTypeEnum type, String ip, int port, ProcessStatusEnum status);
}
