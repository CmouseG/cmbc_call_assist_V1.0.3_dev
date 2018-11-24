package com.guiji.process.agent.service;

import com.guiji.process.agent.model.CfgProcessVO;
import com.guiji.process.agent.util.ProcessUtil;
import com.guiji.utils.JsonUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ProcessCfgService {
    private static ProcessCfgService instance = new ProcessCfgService();
    public static final Map<Integer, CfgProcessVO> cfgMap = new ConcurrentHashMap<Integer, CfgProcessVO>();

    public static ProcessCfgService getIntance()
    {
        return instance;
    }

    public void onChanged(File file)
    {
        List<CfgProcessVO> cfgProcessVOS = null;
        try {
            cfgProcessVOS =   readFileToVO(file);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        initMap(cfgProcessVOS);
    }


    private List<CfgProcessVO> readFileToVO(File file) throws IOException {
        String json = FileUtils.readFileToString(file);

        return  JsonUtils.json2List(json, CfgProcessVO.class);
    }


    public void init(String file)
    {
        List<CfgProcessVO> cfgProcessVOS = null;
        try {
            cfgProcessVOS =   readFileToVO(new File(file));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        initMap(cfgProcessVOS);




    }

    private void initMap(List<CfgProcessVO> cfgProcessVOS)
    {
        if(cfgProcessVOS == null)
        {
            return;
        }

        Map<Integer, CfgProcessVO> cfgMapUnRegister = new ConcurrentHashMap<Integer, CfgProcessVO>();
        if (cfgMap != null) {
            for (Integer key : cfgMap.keySet()) {
                boolean flg = false;
                for (CfgProcessVO cfgProcessVO : cfgProcessVOS) {
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


        cfgMap.clear();

        for (CfgProcessVO cfgProcessVO : cfgProcessVOS) {
            cfgMap.put(cfgProcessVO.getPort(), cfgProcessVO);
            System.out.println(cfgProcessVO);
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
                    ProcessUtil.sendUnRegister(key,cfgMap.get(key));
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        }
    }




}
