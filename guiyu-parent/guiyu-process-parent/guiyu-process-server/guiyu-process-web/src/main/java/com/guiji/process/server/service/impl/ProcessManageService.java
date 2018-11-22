package com.guiji.process.server.service.impl;

import com.guiji.process.core.message.CmdMessageVO;
import com.guiji.process.core.message.MessageProto;
import com.guiji.process.core.vo.CmdTypeEnum;
import com.guiji.process.core.vo.DeviceStatusEnum;
import com.guiji.process.core.vo.DeviceTypeEnum;
import com.guiji.process.core.vo.ProcessInstanceVO;
import com.guiji.process.server.core.ConnectionPool;
import com.guiji.process.server.model.DeviceProcessConstant;
import com.guiji.process.server.service.IDeviceManageService;
import com.guiji.process.server.util.DeviceProcessUtil;

import com.guiji.utils.JsonUtils;
import com.guiji.utils.RedisUtil;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ProcessManageService implements IDeviceManageService {

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


            updateStatus(processInstanceVO);
        }
    }

    /**
     * 注销
     * @param processInstanceVOS 设备
     */
    @Override
    public void unRegister(List<ProcessInstanceVO> processInstanceVOS) {

        ProcessInstanceVO nowProcessInstanceVO = null;
        for (ProcessInstanceVO processInstanceVO : processInstanceVOS) {

            nowProcessInstanceVO = getDevice(processInstanceVO.getType(), processInstanceVO.getIp(), processInstanceVO.getPort());

            if(nowProcessInstanceVO == null)
            {
                continue;
            }

            // 存入数据库 TODO


            updateUnRegister(processInstanceVO.getType(), processInstanceVO.getIp(), processInstanceVO.getPort(), processInstanceVO.getStatus(), "");
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
        ChannelHandlerContext ctx = ConnectionPool.getChannel(processInstanceVO.getIp());
        CmdMessageVO cmdMessageVO = new CmdMessageVO();
        cmdMessageVO.setCmdType(cmdType);
        cmdMessageVO.setProcessInstanceVO(processInstanceVO);
        String msg = JsonUtils.bean2Json(cmdMessageVO);
        MessageProto.Message.Builder builder = MessageProto.Message.newBuilder().setType(2);
        builder.setContent(msg);
        ctx.writeAndFlush(builder);
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

        Map<Object, Object> deviceVOMap =  redisUtil.hmget(DeviceProcessConstant.ALL_DEVIECE_KEY);

        if(deviceVOMap == null)
        {
            return null;
        }

        return (ProcessInstanceVO) deviceVOMap.get(DeviceProcessUtil.getDeviceKey(type, ip, port));
    }

    @Override
    public void updateStatus(ProcessInstanceVO processInstanceVO) {

        ProcessInstanceVO oldProcessInstanceVO = getDevice(processInstanceVO.getType(), processInstanceVO.getIp(), processInstanceVO.getPort());

        if(oldProcessInstanceVO != null && oldProcessInstanceVO.getStatus() == processInstanceVO.getStatus() && StringUtils.equals(oldProcessInstanceVO.getWhoUsed(), processInstanceVO.getWhoUsed()))
        {
            return;
        }

        updateAllDeviceCachList(processInstanceVO);
    }

    @Override
    public void updateUnRegister(DeviceTypeEnum type, String ip, int port, DeviceStatusEnum status, String whoUsed) {

        ProcessInstanceVO processInstanceVO = new ProcessInstanceVO();
        processInstanceVO.setIp(ip);
        processInstanceVO.setPort(port);
        processInstanceVO.setType(type);
        processInstanceVO.setStatus(status);
        processInstanceVO.setWhoUsed(whoUsed);
        updateUnRegisterDeviceCachList(processInstanceVO);
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
        Map<Object, Object> deviceVOMap = (Map<Object, Object>) redisUtil.hmget(DeviceProcessConstant.ALL_DEVIECE_KEY);
        if(deviceVOMap == null)
        {
            deviceVOMap = new ConcurrentHashMap<Object, Object>();
        }

        deviceVOMap.put(DeviceProcessUtil.getDeviceKey(processInstanceVO.getType(), processInstanceVO.getIp(), processInstanceVO.getPort()), processInstanceVO);

        Map<String, Object> deviceVOMapTmp = new ConcurrentHashMap<String, Object>();
        for (Map.Entry<Object, Object> ent:deviceVOMap.entrySet()) {
            deviceVOMapTmp.put((String) ent.getKey(), ent.getValue());
        }

        redisUtil.hmset(DeviceProcessConstant.ALL_DEVIECE_KEY, deviceVOMapTmp);
    }

    private void updateUnRegisterDeviceCachList(ProcessInstanceVO processInstanceVO)
    {
        Map<Object, Object> deviceVOMap = (Map<Object, Object>) redisUtil.hmget(DeviceProcessConstant.ALL_DEVIECE_KEY);
        if(deviceVOMap == null)
        {
            deviceVOMap = new ConcurrentHashMap<Object, Object>();
        }

        deviceVOMap.put(DeviceProcessUtil.getDeviceKey(processInstanceVO.getType(), processInstanceVO.getIp(), processInstanceVO.getPort()), processInstanceVO);

        Map<String, Object> deviceVOMapTmp = new ConcurrentHashMap<String, Object>();
        for (Map.Entry<Object, Object> ent:deviceVOMap.entrySet()) {
            deviceVOMapTmp.put((String) ent.getKey(), ent.getValue());
        }
        redisUtil.hmset(DeviceProcessConstant.ALL_DEVIECE_KEY, deviceVOMapTmp);
    }

}
