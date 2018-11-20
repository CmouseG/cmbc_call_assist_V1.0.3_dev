package com.guiji.process.server.util;

import com.guiji.process.core.vo.DeviceTypeEnum;
import com.guiji.process.server.model.DeviceProcessConstant;

public class DeviceProcessUtil {

    public static String getDeviceKey(DeviceTypeEnum type, String ip, int port)
    {
        StringBuffer sb = new StringBuffer();
        sb.append(DeviceProcessConstant.DEVIECE_KEY_PRE);
        sb.append("_");
        sb.append(type);
        sb.append("_");
        sb.append(ip);
        sb.append("_");
        sb.append(port);

        return sb.toString();
    }
}