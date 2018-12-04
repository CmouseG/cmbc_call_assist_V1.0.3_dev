package com.guiji.process.agent.util;

import com.guiji.common.model.process.ProcessStatusEnum;
import com.guiji.common.model.process.ProcessTypeEnum;
import com.guiji.process.agent.handler.ImClientProtocolBO;
import com.guiji.process.agent.model.CfgProcessOperVO;
import com.guiji.process.agent.model.CfgProcessVO;
import com.guiji.process.agent.model.CommandResult;
import com.guiji.process.agent.model.OperateVO;
import com.guiji.process.agent.service.ProcessStatusLocal;
import com.guiji.process.core.ProcessMsgHandler;
import com.guiji.process.core.message.CmdMessageVO;
import com.guiji.process.core.vo.CmdTypeEnum;
import com.guiji.common.model.process.ProcessInstanceVO;
import com.guiji.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;

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

    public static void sendHealth(int port, ProcessTypeEnum processTypeEnum, CfgProcessOperVO cfgProcessOperVO, String name) throws UnknownHostException {
        CmdMessageVO cmdMessageVO = new CmdMessageVO();
        cmdMessageVO.setCmdType(CmdTypeEnum.HEALTH);
        ProcessInstanceVO processInstanceVO = new ProcessInstanceVO();
        processInstanceVO.setIp(Inet4Address.getLocalHost().getHostAddress());
        processInstanceVO.setType(processTypeEnum);
        processInstanceVO.setPort(port);
        processInstanceVO.setName(name);
        boolean isUp = ProcessUtil.checkRun(port);
        if (isUp) {
            processInstanceVO.setStatus(ProcessStatusEnum.UP);
        } else {
            processInstanceVO.setStatus(ProcessStatusEnum.DOWN);
        }
        cmdMessageVO.setProcessInstanceVO(processInstanceVO);
        ImClientProtocolBO.getIntance().send(cmdMessageVO,3);

        //停止状态的进程自动重启
        if (ProcessStatusEnum.DOWN == processInstanceVO.getStatus()) {
            cmdMessageVO.setCmdType(CmdTypeEnum.START);
            ProcessMsgHandler.getInstance().add(cmdMessageVO);
        }
    }


    public static void sendRegister(int port, CfgProcessVO cfgProcessVO) throws UnknownHostException {
        CmdMessageVO cmdMessageVO = new CmdMessageVO();
        cmdMessageVO.setCmdType(CmdTypeEnum.REGISTER);
        ProcessInstanceVO processInstanceVO = new ProcessInstanceVO();
        processInstanceVO.setIp(Inet4Address.getLocalHost().getHostAddress());
        processInstanceVO.setType(cfgProcessVO.getProcessTypeEnum());
        processInstanceVO.setPort(port);
        processInstanceVO.setName(cfgProcessVO.getName());
        processInstanceVO.setProcessKey(cfgProcessVO.getProcessKey());
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        processInstanceVO.setStatus(ProcessStatusEnum.REGISTER);
        cmdMessageVO.setProcessInstanceVO(processInstanceVO);
        ImClientProtocolBO.getIntance().send(cmdMessageVO,3);
        ProcessStatusLocal.getInstance().put(port, processInstanceVO.getStatus());
    }

    public static void sendUnRegister(int port, CfgProcessVO cfgProcessVO) throws UnknownHostException {
        CmdMessageVO cmdMessageVO = new CmdMessageVO();
        cmdMessageVO.setCmdType(CmdTypeEnum.UNREGISTER);
        ProcessInstanceVO processInstanceVO = new ProcessInstanceVO();
        processInstanceVO.setIp(Inet4Address.getLocalHost().getHostAddress());
        processInstanceVO.setType(cfgProcessVO.getProcessTypeEnum());
        processInstanceVO.setPort(port);
        processInstanceVO.setProcessKey(cfgProcessVO.getProcessKey());
        processInstanceVO.setStatus(ProcessStatusEnum.UNREGISTER);
        cmdMessageVO.setProcessInstanceVO(processInstanceVO);
        ImClientProtocolBO.getIntance().send(cmdMessageVO,3);
    }

    public static boolean neetExecute(int port,CmdTypeEnum cmdTypeEnum) {
        boolean needExecute = true;
        List<OperateVO> operateVOList = ImClientProtocolBO.operateVOList;
        for(OperateVO operateVO : operateVOList) {
            if (operateVO.getPort() == port && cmdTypeEnum == operateVO.getCmdTypeEnum()) {
                //同一进程相同操作，间隔30s才能再次操作
                long currentTimeMillis = System.currentTimeMillis();
                long lastTimeMills = operateVO.getTime().getTime();
                if ((currentTimeMillis - lastTimeMills) < ImClientProtocolBO.operateIntervalTime) {
                    needExecute = false;
                    break;
                }
            }
        }
        return needExecute;
    }

    public static void afterCMD(int port,CmdTypeEnum cmdTypeEnum) {

        if(cmdTypeEnum == CmdTypeEnum.HEALTH)
        {
            return;
        }

        // 操作记录记载内存
        boolean needAddFlg = true;
        List<OperateVO> operateVOList = ImClientProtocolBO.operateVOList;
        for (OperateVO operateVO:operateVOList) {
            if (operateVO.getPort() == port && operateVO.getCmdTypeEnum() == cmdTypeEnum) {
                operateVO.setTime(new Date());
                needAddFlg = false;
                break;
            }
        }
        if (needAddFlg) {
            //如果第一次操作需要添加到内存记录
            OperateVO vo = new OperateVO();
            vo.setPort(port);
            vo.setCmdTypeEnum(cmdTypeEnum);
            vo.setTime(new Date());
            ImClientProtocolBO.operateVOList.add(vo);
        }
    }

    public static String getLocalIp() throws UnknownHostException {
        return Inet4Address.getLocalHost().getHostAddress();
    }
}
