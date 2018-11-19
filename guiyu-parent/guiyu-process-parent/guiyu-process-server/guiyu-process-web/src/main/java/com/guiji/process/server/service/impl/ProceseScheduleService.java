package com.guiji.process.server.service.impl;

import com.guiji.process.core.vo.CmdTypeEnum;
import com.guiji.process.core.vo.DeviceStatusEnum;
import com.guiji.process.core.vo.DeviceTypeEnum;
import com.guiji.process.core.vo.ProcessInstanceVO;
import com.guiji.process.server.model.DeviceProcessConstant;
import com.guiji.process.server.service.IProceseScheduleService;
import com.guiji.process.server.util.DeviceProcessUtil;
import com.guiji.utils.IdGenUtil;
import com.guiji.utils.RedisUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProceseScheduleService implements IProceseScheduleService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private DeviceManageService deviceManageService;

    @Override
    public List<ProcessInstanceVO> getTTS(String model, int requestCount) {

        return getDevices(DeviceTypeEnum.TTS.name()+ "_" + model, requestCount);
    }

    @Override
    public List<ProcessInstanceVO> getSellbot(int requestCount) {
        return getDevices(DeviceTypeEnum.SELLBOT.name(), requestCount);
    }

    @Override
    public boolean release(List<ProcessInstanceVO> processInstances) {

        for (ProcessInstanceVO processInstance:processInstances) {

            processInstance.setWhoUsed("");
            deviceManageService.updateStatus(processInstance.getType(), processInstance.getIp(), processInstance.getPort(), DeviceStatusEnum.UP, "");

            updateActiveCacheList(DeviceTypeEnum.SELLBOT.name(), processInstance);
        }

        return true;
    }

    @Override
    public boolean releaseTts(String model, List<ProcessInstanceVO> processInstances) {
        for (ProcessInstanceVO processInstance:processInstances) {

            processInstance.setWhoUsed("");
            deviceManageService.updateStatus(processInstance.getType(), processInstance.getIp(), processInstance.getPort(), DeviceStatusEnum.UP, "");

            updateActiveCacheList(DeviceTypeEnum.TTS.name()+ "_" + model, processInstance);
        }

        return true;
    }

    @Override
    public void restoreTtsModel(String srcModel, String toModel, ProcessInstanceVO processInstance) {

        if(StringUtils.equals(srcModel, toModel))
        {
            return;
        }

        String deviceKey = DeviceProcessUtil.getDeviceKey(processInstance.getType(), processInstance.getIp(), processInstance.getPort());
        String activeSrcMapKey = DeviceProcessConstant.ACTICE_DEVIECE_KEY + "_" + DeviceTypeEnum.TTS.name()+ "_" + srcModel;
        String activeToMapKey = DeviceProcessConstant.ACTICE_DEVIECE_KEY + "_" + DeviceTypeEnum.TTS.name()+ "_" + toModel;

        Map<String, Object> deviceVOMap = (Map<String, Object>) redisUtil.get(activeSrcMapKey);
        if(deviceVOMap !=  null && deviceVOMap.containsKey(deviceKey))
        {
            deviceVOMap.remove(deviceKey);
            redisUtil.hmset(activeSrcMapKey, deviceVOMap);

            processInstance.setWhoUsed("");
            deviceManageService.updateStatus(processInstance.getType(), processInstance.getIp(), processInstance.getPort(), DeviceStatusEnum.DOWN, "");
        }

        // 通知更换模型 TODO 同步 并设定TTS的状态
        deviceManageService.cmd(processInstance, CmdTypeEnum.RESTORE_MODEL);

        processInstance.setWhoUsed(IdGenUtil.uuid());
        updateActiveCacheList(DeviceTypeEnum.TTS.name()+ "_" + toModel, processInstance);
    }



    private List<ProcessInstanceVO> getDevices(String key, int requestCount)
    {
        List<ProcessInstanceVO> result = new ArrayList<ProcessInstanceVO>();

        Map<String, Object> deviceVOMap = (Map<String, Object>) redisUtil.get(DeviceProcessConstant.ALL_DEVIECE_KEY + "_" + DeviceTypeEnum.TTS);
        if(deviceVOMap == null)
        {
            return result;
        }

        ProcessInstanceVO deviceVO = null;
        String whoUsed = IdGenUtil.uuid();

        int count = 0;
        for (Map.Entry<String, Object> ent: deviceVOMap.entrySet()) {

            if(count == requestCount)
            {
                break;
            }

            try {
                deviceVO = (ProcessInstanceVO) ent.getValue();
                if(deviceVO.getStatus() == DeviceStatusEnum.UP && StringUtils.isEmpty(deviceVO.getWhoUsed()))
                {
                    deviceVO.setStatus(DeviceStatusEnum.BUSYING);
                    deviceVO.setWhoUsed(whoUsed);

                    deviceManageService.updateStatus(DeviceTypeEnum.TTS, deviceVO.getIp(), deviceVO.getPort(), deviceVO.getStatus(), whoUsed);

                    updateActiveCacheList(key, deviceVO);

                    result.add((ProcessInstanceVO) deviceVO.clone());
                    count++;
                }
            }
            catch (Exception e)
            {

            }

        }

        return  result;
    }

    private void updateActiveCacheList(String key, ProcessInstanceVO deviceVO)
    {
        Map<String, Object> deviceVOMap = (Map<String, Object>) redisUtil.get(DeviceProcessConstant.ACTICE_DEVIECE_KEY + "_" + key);
        if(deviceVOMap == null)
        {
            deviceVOMap = new HashMap<String, Object>();
        }

        String deviceKey = DeviceProcessUtil.getDeviceKey(deviceVO.getType(), deviceVO.getIp(), deviceVO.getPort());
        if(!deviceVOMap.containsKey(deviceKey))
        {
            if(deviceVO.getStatus() == DeviceStatusEnum.BUSYING || deviceVO.getStatus() == DeviceStatusEnum.UP)
            {
                deviceVOMap.put(deviceKey, deviceVO);
                redisUtil.hmset(DeviceProcessConstant.ACTICE_DEVIECE_KEY + "_" + key, deviceVOMap);
            }

            return;
        }

        if(((ProcessInstanceVO)deviceVOMap.get(deviceKey)).getStatus()== deviceVO.getStatus())
        {
            return;
        }

        if(deviceVO.getStatus() == DeviceStatusEnum.BUSYING || deviceVO.getStatus() == DeviceStatusEnum.UP)
        {
            deviceVOMap.put(deviceKey, deviceVO);
        }
        else
        {
            deviceVOMap.remove(deviceKey);
        }

        redisUtil.hmset(DeviceProcessConstant.ACTICE_DEVIECE_KEY + "_" + key, deviceVOMap);
    }
}
