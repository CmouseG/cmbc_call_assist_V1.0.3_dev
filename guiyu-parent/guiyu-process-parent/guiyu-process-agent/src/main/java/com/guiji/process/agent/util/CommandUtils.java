package com.guiji.process.agent.util;

import com.guiji.process.agent.model.CommandResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public class CommandUtils {
    static Logger logger = LoggerFactory.getLogger(CommandUtils.class);
    public static int DEFAULT_TIMEOUT=100000;
    public static final int DEFAULT_INTERVAL=100;
    public static long startTime ;
    public static CommandResult exec(String command){
        startTime = System.nanoTime();
        Process process=null;
        CommandResult commandResult =null;
        try {
            if (null == command || "".equals(command = command.trim())) {
                commandResult = new CommandResult() ;
                commandResult.setExitValue(1);
                commandResult.setError("command is not empty");
            }
            String[] cmd;
            String osName = System.getProperty("os.name");
            if (osName.startsWith("Windows")) {
                cmd = new String[3];
                if (osName.equals("Windows 95")) { // windows 95 only
                    cmd[0] = "command.com";
                } else {
                    cmd[0] = "cmd.exe";
                }
                cmd[1] = "/C";
                cmd[2] = command;
            } else if (osName.equals("Linux")) {  //linux
                cmd = new String[3];
                cmd[0] = "/bin/bash";
                cmd[1] = "-c";
                cmd[2] = command;
            } else {
                cmd = new String[3];
                cmd[0] = "/bin/bash";
                cmd[1] = "-c";
                cmd[2] = command;
            }
             process= Runtime.getRuntime().exec(cmd);
             commandResult = wait(process);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }finally {
            if (process!=null){
                process.destroy();
            }
        }
        return commandResult;
    }
//判断是否超时
    public static  boolean isOverTime(){
        return  (TimeUnit.MILLISECONDS.toNanos(DEFAULT_TIMEOUT) - (System.nanoTime() - startTime)) < 0;
    }
 
    public static CommandResult wait(Process process) throws InterruptedException, IOException {
        BufferedReader errorStreamReader = null;
        BufferedReader inputStreamReader = null;
        try {
            errorStreamReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            inputStreamReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            boolean isFinished = false;
            while (true) {  //这是一个死循环，直到超时，或者command 执行完成
                if (isOverTime()) {  //如果超时 ，直接返回
                    CommandResult result = new CommandResult();
                    result.setExitValue(CommandResult.EXIT_VALUE_TIMEOUT);
                    result.setOutput("Command process timeout");
                    return result;
                }
                if (isFinished) {
                    CommandResult result = new CommandResult();
                    result.setExitValue(process.waitFor());
                    //  error info 
                    if (errorStreamReader.ready()) {  
                        StringBuilder buffer = new StringBuilder();
                        String line;
                        while ((line = errorStreamReader.readLine()) != null) {
                            buffer.append(line);
                        }
                        result.setError(buffer.toString());
                    }
                    //  info
                    if (inputStreamReader.ready()) {
                        StringBuilder buffer = new StringBuilder();
                        String line;
                        while ((line = inputStreamReader.readLine()) != null) {
                            buffer.append(line);
                        }
                        result.setOutput(buffer.toString());
                    }
                    return result;
                }
                try {
                    isFinished = true;     //很乐观的认为command 已经执行完成了 ，将isFinished 赋值为true
                    process.exitValue();   //这个方法，command 没有执行完成的时候会抛出异常IllegalThreadStateException被catch捕获，如果执行完成 则isFinished =true
                } catch (IllegalThreadStateException e) {
                    //  hasn't finished yet
                    isFinished = false;  //执行到这里，说明command 没有执行完成，那么将isFinished 重新设置为false；
                    Thread.sleep(DEFAULT_INTERVAL);  //如果command 没有执行完成，可以睡眠 DEFAULT_INTERVAL 
                }
            }
        } finally {
            if (errorStreamReader != null) {
                try {
                    errorStreamReader.close();
                } catch (IOException e) {
                }
            }
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
