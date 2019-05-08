package com.guiji.fsagent.util;

import com.guiji.fsagent.config.Constant;
import com.guiji.fsagent.entity.FreeSWITCH;
import com.guiji.fsmanager.entity.LineXmlnfoVO;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
@Slf4j
public class LineOperUtil {

    public static void ergodicLine(List<LineXmlnfoVO> lineList, FreeSWITCH freeSwitch,String role){
        for(LineXmlnfoVO line:lineList){
            String fileName = line.getFileName();
            if (line.getConfigType().equals(Constant.CONFIG_TYPE_DIALPLAN)) {
                Base64Util.base64ToFile(line.getFileData(), freeSwitch.getDialplan() + fileName);
                log.info("load dialplan：[{}]",freeSwitch.getDialplan() + fileName);
            } else if (line.getConfigType().equals(Constant.CONFIG_TYPE_GATEWAY)) {
                Base64Util.base64ToFile(line.getFileData(), freeSwitch.getGateway() + fileName);
                log.info("load gateway：[{}]",freeSwitch.getGateway() + fileName);
                //执行esl命令卸载网关
                freeSwitch.execute("sofia profile external killgw " + fileName.substring(0, fileName.lastIndexOf(".")));
            }
        }
        //执行esl命令加载xml
        freeSwitch.execute("reloadxml");
        if (!role.equals(Constant.FSAGENT_ROLE_LINE_SIMCARD)) {
            //执行esl命令加载网关
            freeSwitch.execute("sofia profile external rescan reloadxml");
        }
    }
}
