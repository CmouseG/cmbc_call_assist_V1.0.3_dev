package com.guiji.process.server.service.impl;

import com.guiji.process.server.model.DeviceProcessConstant;
import com.guiji.process.server.service.IProceseScheduleService;
import com.guiji.process.server.util.DeviceProcessUtil;
import com.guiji.process.vo.CmdTypeEnum;
import com.guiji.process.vo.DeviceStatusEnum;
import com.guiji.process.vo.DeviceTypeEnum;
import com.guiji.process.vo.DeviceVO;
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
    public List<DeviceVO> getTTS(String model, int requestCount) {

        return getDevices(DeviceTypeEnum.TTS.name()+ "_" + model, requestCount);
    }

    @Override
    public List<DeviceVO> getSellbot(int requestCount) {
        return getDevices(DeviceTypeEnum.SELLBOT.name(), requestCount);
    }

    @Override
    public boolean release(List<DeviceVO> deviceVOS) {

        for (DeviceVO deviceVO:deviceVOS) {

            deviceVO.setWhoUsed("");
            deviceManageService.updateStatus(deviceVO.getType(), deviceVO.getIp(), deviceVO.getPort(), DeviceStatusEnum.UP, "");

            updateActiveCacheList(DeviceTypeEnum.SELLBOT.name(), deviceVO);
        }

        return true;
    }

    @Override
    public boolean releaseTts(String model, List<DeviceVO> deviceVOS) {
        for (DeviceVO deviceVO:deviceVOS) {

            deviceVO.setWhoUsed("");
            deviceManageService.updateStatus(deviceVO.getType(), deviceVO.getIp(), deviceVO.getPort(), DeviceStatusEnum.UP, "");

            updateActiveCacheList(DeviceTypeEnum.TTS.name()+ "_" + model, deviceVO);
        }

        return true;
    }

    @Override
    public void restoreTtsModel(String srcModel, String toModel, DeviceVO deviceVO) {

        if(StringUtils.equals(srcModel, toModel))
        {
            return;
        }

        String deviceKey = DeviceProcessUtil.getDeviceKey(deviceVO.getType(), deviceVO.getIp(), deviceVO.getPort());
        String activeSrcMapKey = DeviceProcessConstant.ACTICE_DEVIECE_KEY + "_" + DeviceTypeEnum.TTS.name()+ "_" + srcModel;
        String activeToMapKey = DeviceProcessConstant.ACTICE_DEVIECE_KEY + "_" + DeviceTypeEnum.TTS.name()+ "_" + toModel;

        Map<String, Object> deviceVOMap = (Map<String, Object>) redisUtil.get(activeSrcMapKey);
        if(deviceVOMap !=  null && deviceVOMap.containsKey(deviceKey))
        {
            deviceVOMap.remove(deviceKey);
            redisUtil.hmset(activeSrcMapKey, deviceVOMap);

            deviceVO.setWhoUsed("");
            deviceManageService.updateStatus(deviceVO.getType(), deviceVO.getIp(), deviceVO.getPort(), DeviceStatusEnum.DOWN, "");
        }

        // 通知更换模型 TODO 同步 并设定TTS的状态
        deviceManageService.cmd(deviceVO, CmdTypeEnum.RESTORE_MODEL);

        deviceVO.setWhoUsed(IdGenUtil.uuid());
        updateActiveCacheList(DeviceTypeEnum.TTS.name()+ "_" + toModel, deviceVO);
    }



    private List<DeviceVO> getDevices(String key, int requestCount)
    {
        List<DeviceVO> result = new ArrayList<DeviceVO>();

        Map<String, Object> deviceVOMap = (Map<String, Object>) redisUtil.get(DeviceProcessConstant.ALL_DEVIECE_KEY + "_" + DeviceTypeEnum.TTS);
        if(deviceVOMap == null)
        {
            return result;
        }

        DeviceVO deviceVO = null;
        String whoUsed = IdGenUtil.uuid();

        int count = 0;
        for (Map.Entry<String, Object> ent: deviceVOMap.entrySet()) {

            if(count == requestCount)
            {
                break;
            }

            try {
                deviceVO = (DeviceVO) ent.getValue();
                if(deviceVO.getStatus() == DeviceStatusEnum.UP && StringUtils.isEmpty(deviceVO.getWhoUsed()))
                {
                    deviceVO.setStatus(DeviceStatusEnum.BUSYING);
                    deviceVO.setWhoUsed(whoUsed);

                    deviceManageService.updateStatus(DeviceTypeEnum.TTS, deviceVO.getIp(), deviceVO.getPort(), deviceVO.getStatus(), whoUsed);

                    updateActiveCacheList(key, deviceVO);

                    result.add((DeviceVO) deviceVO.clone());
                    count++;
                }
            }
            catch (Exception e)
            {

            }

        }

        return  result;
    }

    private void updateActiveCacheList(String key, DeviceVO deviceVO)
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

        if(((DeviceVO)deviceVOMap.get(deviceKey)).getStatus()== deviceVO.getStatus())
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
