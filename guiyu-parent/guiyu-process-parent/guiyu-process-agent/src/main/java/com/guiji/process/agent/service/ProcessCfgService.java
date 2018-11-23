package com.guiji.process.agent.service;

import com.guiji.process.agent.model.CfgNodeVO;
import com.guiji.process.agent.util.ProcessUtil;
import com.guiji.utils.JsonUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ProcessCfgService {
    private static ProcessCfgService instance = new ProcessCfgService();
    public static final Map<Integer, CfgNodeVO> cfgMap = new ConcurrentHashMap<Integer, CfgNodeVO>();

    public static ProcessCfgService getIntance()
    {
        return instance;
    }

    public void onChanged(File file)
    {
        List<CfgNodeVO> cfgNodeVOS = null;
        try {
            cfgNodeVOS  =   readFileToVO(file);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        initMap(cfgNodeVOS);
    }


    private List<CfgNodeVO> readFileToVO(File file) throws IOException {
        String json = FileUtils.readFileToString(file);

        return  JsonUtils.json2List(json, CfgNodeVO.class);
    }


    public void init(String file)
    {
        List<CfgNodeVO> cfgNodeVOS = null;
        try {
            cfgNodeVOS =   readFileToVO(new File(file));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        initMap(cfgNodeVOS);




    }

    private void initMap(List<CfgNodeVO> cfgNodeVOS)
    {
        if(cfgNodeVOS == null)
        {
            return;
        }

        Map<Integer, CfgNodeVO> cfgMapUnRegister = new ConcurrentHashMap<Integer, CfgNodeVO>();
        if (cfgMap != null) {
            for (Integer key : cfgMap.keySet()) {
                boolean flg = false;
                for (CfgNodeVO cfgNodeVO : cfgNodeVOS) {
                    if (cfgNodeVO.getPort() == key) {
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

        for (CfgNodeVO cfgNodeVO:cfgNodeVOS ) {
            cfgMap.put(cfgNodeVO.getPort(), cfgNodeVO);
            System.out.println(cfgNodeVO);
        }
        // 发送注册信息
        Map<Integer, CfgNodeVO> cfgMap = ProcessCfgService.getIntance().cfgMap;
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
