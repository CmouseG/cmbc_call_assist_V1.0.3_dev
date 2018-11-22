package com.guiji.process.agent.util;

import com.guiji.process.agent.handler.ImClientProtocolBO;
import com.guiji.process.agent.model.CommandResult;
import com.guiji.process.core.message.CmdMessageVO;
import com.guiji.process.core.vo.CmdTypeEnum;
import com.guiji.process.core.vo.DeviceStatusEnum;
import com.guiji.process.core.vo.DeviceTypeEnum;
import com.guiji.process.core.vo.ProcessInstanceVO;
import com.guiji.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Random;

/**
 * Created by ty on 2018/11/19.
 */
public class ProcessUtil {
    /**
     * 根据端口号获取进程号
     * @param port
     * @return
     */
    public static String getPid(int port) {
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
    public static boolean checkRun(int port) {
        boolean up = false;
        String pid = getPid(port);
        if (StringUtils.isNotEmpty(pid)) {
            up = true;
        }
        return up;
    }

    public static void killProcess(int port) {
        String pid = getPid(port);
        String command = "";
        String osName = System.getProperty("os.name");
        if (osName.startsWith("Windows")) {
            command = "taskkill /pid "+pid+" -t -f";
            CommandResult result = CommandUtils.exec(command);
            if (result != null && StringUtils.isNotEmpty(result.getOutput())) {
                String[] resultArray = result.getOutput().split(" ");
                if (resultArray != null && resultArray.length > 0) {
                    pid = resultArray[resultArray.length-1];
                }
            }
        } else {
            command = "kill -9 "+ pid;
            CommandResult result = CommandUtils.exec(command);
            if (result != null && StringUtils.isNotEmpty(result.getOutput())) {
                String[] resultArray = result.getOutput().split(" ");
                if (resultArray != null && resultArray.length > 0) {
                    pid = resultArray[resultArray.length-1].split("/")[0];
                }
            }
        }
    }

    public static void sendHealth(int port) throws UnknownHostException {
        CmdMessageVO cmdMessageVO = new CmdMessageVO();
        cmdMessageVO.setCmdType(CmdTypeEnum.HEALTH);
        ProcessInstanceVO processInstanceVO = new ProcessInstanceVO();
        processInstanceVO.setIp(Inet4Address.getLocalHost().getHostAddress());
        processInstanceVO.setType(DeviceTypeEnum.SELLBOT);
        processInstanceVO.setPort(port);
        boolean isUp = ProcessUtil.checkRun(port);
        if (isUp) {
            processInstanceVO.setStatus(DeviceStatusEnum.UP);
        } else {
            processInstanceVO.setStatus(DeviceStatusEnum.DOWN);
        }
        cmdMessageVO.setProcessInstanceVO(processInstanceVO);
        String msg = JsonUtils.bean2Json(cmdMessageVO);

        // TODO
        Random r = new Random();
        int rr = r.nextInt(1000);
        if(rr % 2 == 0)
        {
            processInstanceVO.setStatus(DeviceStatusEnum.UP);
        }
        else
        {
            processInstanceVO.setStatus(DeviceStatusEnum.DOWN);
        }
        ImClientProtocolBO.getIntance().send(msg,3);
    }


    public static void sendRegister(int port) throws UnknownHostException {
        CmdMessageVO cmdMessageVO = new CmdMessageVO();
        cmdMessageVO.setCmdType(CmdTypeEnum.REGISTER);
        ProcessInstanceVO processInstanceVO = new ProcessInstanceVO();
        processInstanceVO.setIp(Inet4Address.getLocalHost().getHostAddress());
        processInstanceVO.setType(DeviceTypeEnum.SELLBOT);
        processInstanceVO.setPort(port);
        boolean isUp = ProcessUtil.checkRun(port);
        if (isUp) {
            processInstanceVO.setStatus(DeviceStatusEnum.UP);
        } else {
            processInstanceVO.setStatus(DeviceStatusEnum.DOWN);
        }
        cmdMessageVO.setProcessInstanceVO(processInstanceVO);
        String msg = JsonUtils.bean2Json(cmdMessageVO);
        ImClientProtocolBO.getIntance().send(msg,3);
    }

    public static void sendUnRegister(int port) throws UnknownHostException {
        CmdMessageVO cmdMessageVO = new CmdMessageVO();
        cmdMessageVO.setCmdType(CmdTypeEnum.UNREGISTER);
        ProcessInstanceVO processInstanceVO = new ProcessInstanceVO();
        processInstanceVO.setIp(Inet4Address.getLocalHost().getHostAddress());
        processInstanceVO.setType(DeviceTypeEnum.SELLBOT);
        processInstanceVO.setPort(port);
        processInstanceVO.setStatus(DeviceStatusEnum.UNREGISTER);
        cmdMessageVO.setProcessInstanceVO(processInstanceVO);
        String msg = JsonUtils.bean2Json(cmdMessageVO);
        ImClientProtocolBO.getIntance().send(msg,3);
    }
}
