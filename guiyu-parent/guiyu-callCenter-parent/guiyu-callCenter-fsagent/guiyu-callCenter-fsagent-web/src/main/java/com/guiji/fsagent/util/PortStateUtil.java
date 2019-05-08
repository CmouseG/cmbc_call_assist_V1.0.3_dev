package com.guiji.fsagent.util;


import com.guiji.utils.HttpClientUtil;
import com.guiji.utils.JsonUtils;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Socket;

/**
 * 判断某个端口是否正常
 */
public class PortStateUtil {
    private static final Logger logger = LoggerFactory.getLogger(PortStateUtil.class);

    public static boolean PortIsIn(String port) {
        Socket socket = null;
        try {
            socket = new Socket("localhost", Integer.parseInt(port));
            if (!socket.isConnected()) {
                return false;
            }
        } catch (Exception e) {
            logger.info("检查本机某个端口出错", e);
            return false;
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    logger.info("关闭socket出错", e);
                }
            }
        }
        return true;
    }
}
