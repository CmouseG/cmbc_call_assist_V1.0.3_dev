package com.guiji.fsagent.manager;

import com.guiji.component.result.Result;
import com.guiji.fsagent.config.Constant;
import com.guiji.fsagent.config.FsConfig;
import com.guiji.fsagent.controller.LineOperateController;
import com.guiji.fsagent.entity.FreeSWITCH;
import com.guiji.fsagent.util.Base64Util;
import com.guiji.fsmanager.api.ILineOper;
import com.guiji.fsmanager.entity.LineXmlnfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

/**
 * 启动时执行，下载所有的line文件
 */
@Component
public class ReloadLineManager {
    private final Logger logger = LoggerFactory.getLogger(ReloadLineManager.class);
    @Autowired
    ILineOper lineOperApiFeign;
    @Autowired
    FsConfig fsConfig;
    @Autowired
    FSService fsService;

    @PostConstruct
    public void init() {
        Result.ReturnData<List<LineXmlnfoVO>> result = lineOperApiFeign.linexmlinfosAll();
        List<LineXmlnfoVO> lineList = result.getBody();
        //获取fs对象
        FreeSWITCH fs = fsService.getFreeSwitch();
        Base64Util util = new Base64Util();
        try {
            for (LineXmlnfoVO line : lineList) {
                if (line.getConfigType().equals(Constant.CONFIG_TYPE_DIALPLAN)) {
                    util.base64ToFile(line.getFileData(), fs.getDialplan() + line.getFileName());
                } else if (line.getConfigType().equals(Constant.CONFIG_TYPE_GATEWAY)) {
                    util.base64ToFile(line.getFileData(), fs.getGateway() + line.getFileName());
                    //执行esl命令卸载网关
                    fs.execute("sofia profile external killgw " + line.getFileName());
                }
            }
            //执行esl命令加载网关
            fs.execute("sofia profile external rescan reloadxml");
        } catch (IOException e) {
            logger.info("下载新得xml后转base64过程中失败", e);
        }
    }
}
