package com.guiji.process.agent.service;

import com.guiji.process.agent.model.CfgNodeVO;
import com.guiji.utils.JsonUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProcessCfgService {
    private static ProcessCfgService instance = new ProcessCfgService();
    public static final Map<Integer, CfgNodeVO> cfgMap = new HashMap<Integer, CfgNodeVO>();

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

        cfgMap.clear();

        for (CfgNodeVO cfgNodeVO:cfgNodeVOS ) {
            cfgMap.put(cfgNodeVO.getPort(), cfgNodeVO);
        }

    }

}
