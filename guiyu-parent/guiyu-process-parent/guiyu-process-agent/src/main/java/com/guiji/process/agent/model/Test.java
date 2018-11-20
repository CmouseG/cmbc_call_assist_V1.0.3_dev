package com.guiji.process.agent.model;

import com.guiji.process.agent.service.ProcessCfgService;
import com.guiji.process.core.vo.CmdTypeEnum;
import com.guiji.utils.JsonUtils;

import java.io.File;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String[] args)  {

        ProcessCfgService process = new ProcessCfgService();
        process.onChanged(new File("d:\\test\\cfg.json"));
//        List<CfgNodeVO> result = new ArrayList<CfgNodeVO>();
//
//        for (int i = 0; i < 10; i++) {
//            CfgNodeVO nodeVO = new CfgNodeVO();
//
//            nodeVO.setName("name" + i);
//            nodeVO.setDeviceTypeEnum(0);
//            nodeVO.setPort(i);
//            nodeVO.setProcessKey("key"+i);
//
//
//            List<CfgNodeOperVO> CfgNodeOpers = new ArrayList<CfgNodeOperVO>();
//            for (int j = 0; j < 5; j++) {
//                CfgNodeOperVO cfgNodeOperVO = new CfgNodeOperVO();
//                cfgNodeOperVO.setCmd("d:/ss.bat"+ i + "  _ "+j);
//                cfgNodeOperVO.setCmdTypeEnum(CmdTypeEnum.START.getValue());
//                CfgNodeOpers.add(cfgNodeOperVO);
//            }
//
//            nodeVO.setCfgNodeOpers(CfgNodeOpers);
//
//            result.add(nodeVO);
//        }
//
//        System.out.println(JsonUtils.bean2Json(result));
    }
}
