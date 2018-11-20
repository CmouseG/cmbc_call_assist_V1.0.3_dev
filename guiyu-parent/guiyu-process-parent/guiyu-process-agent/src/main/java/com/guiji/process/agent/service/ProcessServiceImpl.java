package com.guiji.process.agent.service;

import com.ctc.wstx.util.StringUtil;
import com.guiji.process.agent.model.CommandResult;
import com.guiji.process.agent.util.CommandUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * Created by ty on 2018/11/19.
 */
@Service
public class ProcessServiceImpl {
    /**
     * 根据端口号获取进程号
     * @param port
     * @return
     */
    public String getPid(String port) {
        String pid = "";
        String command = "";
        String osName = System.getProperty("os.name");
        if (osName.startsWith("Windows")) {
            command = "netstat -aon |findstr "+ port;
            CommandResult result = CommandUtils.exec(command);
            if (result != null && StringUtils.isNotEmpty(result.getOutput())) {
                String[] resultArray = result.getOutput().split(" ");
                if (resultArray != null && resultArray.length > 0) {
                    pid = resultArray[resultArray.length-1];
                }
            }
        } else {
            command = "netstat -anp | grep "+ port;
            CommandResult result = CommandUtils.exec(command);
            if (result != null && StringUtils.isNotEmpty(result.getOutput())) {
                String[] resultArray = result.getOutput().split(" ");
                if (resultArray != null && resultArray.length > 0) {
                    pid = resultArray[resultArray.length-1].split("/")[0];
                }
            }
        }
        return pid;
    }

    /**
     * 检查进程状态true:up,false:down
     * @param port
     * @return
     */
    public boolean checkRun(String port) {
        boolean up = false;
        String pid = getPid(port);
        if (StringUtils.isNotEmpty(pid)) {
            up = true;
        }
        return up;
    }
}
