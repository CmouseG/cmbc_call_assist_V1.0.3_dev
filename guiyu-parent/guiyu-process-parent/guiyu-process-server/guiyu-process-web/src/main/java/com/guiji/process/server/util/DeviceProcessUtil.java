package com.guiji.process.server.util;

import com.guiji.common.model.process.ProcessTypeEnum;
import com.guiji.process.server.model.DeviceProcessConstant;
import io.netty.channel.ChannelHandlerContext;

import java.net.InetSocketAddress;

public class DeviceProcessUtil {

    public static String getDeviceKey(ProcessTypeEnum type, String ip, int port)
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


    public static String getRemoreIp(ChannelHandlerContext ctx)
    {
        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIP = insocket.getAddress().getHostAddress();
        return clientIP;
    }
}
