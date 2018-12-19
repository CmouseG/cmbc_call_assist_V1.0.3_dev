package com.guiji.process.agent.service;

import com.guiji.common.model.process.ProcessTypeEnum;
import com.guiji.process.agent.model.CfgAgentNodeVO;
import com.guiji.process.agent.model.CfgProcessOperVO;
import com.guiji.process.agent.model.CfgProcessVO;
import com.guiji.process.agent.util.ProcessUtil;
import com.guiji.utils.JsonUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ProcessCfgService {
    private static ProcessCfgService instance = new ProcessCfgService();
    public static final Map<Integer, CfgProcessVO> cfgMap = new ConcurrentHashMap<Integer, CfgProcessVO>();

    public static CfgAgentNodeVO gloablCfgAgentNodeVO = null;

    public static Integer agentPort = 0;

    private String file;

    public static ProcessCfgService getIntance()
    {
        return instance;
    }

    public void onChanged(File file)
    {
        CfgAgentNodeVO cfgAgentNodeVO = null;
        try {
            cfgAgentNodeVO =   readFileToVO(file);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        initMap(cfgAgentNodeVO);
    }


    private CfgAgentNodeVO readFileToVO(File file) throws IOException {

        this.file = file.getAbsolutePath();
        String json = FileUtils.readFileToString(file);

        return  JsonUtils.json2Bean(json, CfgAgentNodeVO.class);
    }


    public void init(String file, int agentPort)
    {
        ProcessCfgService.agentPort= agentPort;
        CfgAgentNodeVO cfgAgentNodeVO = null;
        try {
            cfgAgentNodeVO =   readFileToVO(new File(file));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        initMap(cfgAgentNodeVO);
        gloablCfgAgentNodeVO = cfgAgentNodeVO;
    }

    public void reConnect()
    {
        initMap(gloablCfgAgentNodeVO);
    }

    private void initMap(CfgAgentNodeVO cfgAgentNodeVO)
    {
        if(cfgAgentNodeVO == null)
        {
            return;
        }

        Map<Integer, CfgProcessVO> cfgMapUnRegister = new ConcurrentHashMap<Integer, CfgProcessVO>();
        if (cfgMap != null) {
            for (Integer key : cfgMap.keySet()) {
                boolean flg = false;
                for (CfgProcessVO cfgProcessVO : cfgAgentNodeVO.getProcesses()) {
                    if (cfgProcessVO.getPort() == key) {
                        flg = true;
                        break;
                    }
                }

                if(!flg)
                {
                    cfgMapUnRegister.put(key,cfgMap.get(key));
                }
            }
        }

        if (cfgMap != null) {
            cfgMap.clear();
            for (CfgProcessVO cfgProcessVO : cfgAgentNodeVO.getProcesses()) {
                cfgMap.put(cfgProcessVO.getPort(), cfgProcessVO);
            }
        }

        // 发送注册信息
        Map<Integer, CfgProcessVO> cfgMap = ProcessCfgService.getIntance().cfgMap;
        if (cfgMap != null) {
            for (Integer key : cfgMap.keySet()) {
                try {
                    ProcessUtil.sendRegister(key,cfgMap.get(key));
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        }

        // 发送注销信息
        if (cfgMapUnRegister != null) {
            for (Integer key : cfgMapUnRegister.keySet()) {
                try {
                    ProcessUtil.sendUnRegister(key,cfgMapUnRegister.get(key));
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        }


    }


    public void refreshProcessKey(int port, String processKey)
    {
        CfgAgentNodeVO cfgAgentNodeVO = null;
        File file = new File(this.file);
        try {
            cfgAgentNodeVO =   readFileToVO(file);
            for (CfgProcessVO cfgProcessVO : cfgAgentNodeVO.getProcesses()) {
                if (cfgProcessVO.getPort() == port) {
                    cfgProcessVO.setProcessKey(processKey);
                    break;
                }
            }

            FileUtils.write(file, JsonUtils.bean2Json(cfgAgentNodeVO));

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }




}
