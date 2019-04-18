package com.guiji.dispatch.util;

import com.guiji.dispatch.bean.UserLineBotenceVO;
import com.guiji.robot.model.TemplateInfo;
import com.guiji.robot.model.UserResourceCache;
import com.guiji.robot.model.UserResourceCacheWithVersion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllotUserLineBotenceUtil {

    public static List<UserLineBotenceVO> allot(List<UserLineBotenceVO> userLineBotenceVOList,
                                                List<UserResourceCacheWithVersion> configRes, Integer maxRobot) {

        List<UserLineBotenceVO> result = new ArrayList<>();
        if (userLineBotenceVOList == null || userLineBotenceVOList.isEmpty()) {
            return result;
        }

        // 如果每个都不够分配一个，则所有的都分配，最终的分配不了
        Integer canDividCount = maxRobot - userLineBotenceVOList.size();
        if (canDividCount <= 0) {
            int hasDistributeCount = 0;
            for (UserLineBotenceVO item : userLineBotenceVOList) {
                if (hasDistributeCount == userLineBotenceVOList.size()) {
                    return result;
                }

                // 至少给一个机器人
                item.setMaxRobotCount(1);
                result.add(item);

                hasDistributeCount = hasDistributeCount + 1;
            }
            return result;
        }

        //用户机器人，拼接key
        Map<String, List<UserLineBotenceVO>> allUserBotence = getAllUserBotence(userLineBotenceVOList);

        //用户各个模板配置的机器人数量，拼接key:userId+templateId
        Map<String, Integer> allUserBotenceCfg = convertUserRes2Map(configRes);

        int allCount = 0;
        for (Map.Entry<String, List<UserLineBotenceVO>> ent : allUserBotence.entrySet()) {

            if(allUserBotenceCfg.containsKey(ent.getKey()))//用户模板机器人数包涵用户机器人
            {
                allCount = allCount + allUserBotenceCfg.get(ent.getKey());
            }
        }


        int userBotenceCount = 0;//分配用户机器人数
        int hasDividCount = 0;  //已使用数量
        Map<String, Integer> userDistributeMap = new HashMap<>();
        for (Map.Entry<String, List<UserLineBotenceVO>> ent : allUserBotence.entrySet()) {

            if(!allUserBotenceCfg.containsKey(ent.getKey()))
            {
                continue;
            }

            //剩余可使用数量*用户模板配置机器人数/机器人总数 + 用户机器人数据
            userBotenceCount = canDividCount * allUserBotenceCfg.get(ent.getKey()) / allCount + ent.getValue().size();
            if ((userBotenceCount) > allUserBotenceCfg.get(ent.getKey())) {
                userBotenceCount = allUserBotenceCfg.get(ent.getKey());
            }

            //设置分配（用户ID+模板）机器人数
            userDistributeMap.put(ent.getKey(), userBotenceCount);
            hasDividCount = hasDividCount + userBotenceCount;
        }

        // 补偿
        if (hasDividCount < maxRobot) {
            for (Map.Entry<String, Integer> ent : userDistributeMap.entrySet()) {
                // 如果没有配置机器人，则不参与分配
                if(!allUserBotenceCfg.containsKey(ent.getKey()))
                {
                    continue;
                }
                int cut = maxRobot - hasDividCount;     //最大机器人数 - 已使用机器人数量
                int tmp = ent.getValue() + cut - allUserBotenceCfg.get(ent.getKey());//分配用户机器人数+剩余数量-用户模板机器人数
                if (tmp > 0) {
                    ent.setValue(allUserBotenceCfg.get(ent.getKey()));
                    hasDividCount = hasDividCount + allUserBotenceCfg.get(ent.getKey()) - ent.getValue();
                } else {
                    ent.setValue(ent.getValue() + cut);
                    break;
                }
            }
        }

        int everyOneCount = 0;
        for (Map.Entry<String, List<UserLineBotenceVO>> ent : allUserBotence.entrySet()) {

            if(!userDistributeMap.containsKey(ent.getKey()))
            {
                continue;
            }
            everyOneCount = userDistributeMap.get(ent.getKey()) / ent.getValue().size();

            for (int i = 0; i < ent.getValue().size(); i++) {
                UserLineBotenceVO currenVO = ent.getValue().get(i);
                currenVO.setMaxRobotCount(everyOneCount);

                int cut = userDistributeMap.get(ent.getKey()) - everyOneCount * ent.getValue().size();
                if (cut > 0 && (i == ent.getValue().size() - 1))
                {
                    ent.getValue().get(i).setMaxRobotCount(everyOneCount + cut);
                }
                result.add(ent.getValue().get(i));
            }


        }

        return result;
    }

    /**
     * 拼接用户-机器人
     * @param userLineBotenceVOList
     * @return
     */
    public static Map<String, List<UserLineBotenceVO>> getAllUserBotence(List<UserLineBotenceVO> userLineBotenceVOList) {
        Map<String, List<UserLineBotenceVO>> result = new HashMap<>();
        if (userLineBotenceVOList == null) {
            return result;
        }

        String key = "";
        for (UserLineBotenceVO item : userLineBotenceVOList) {
            key = item.getUserId() + "-" + item.getBotenceName();

            List<UserLineBotenceVO> tmpList;
            if (result.containsKey(key)) {
                tmpList = result.get(key);
            } else {
                tmpList = new ArrayList<>();
            }
            tmpList.add(item);

            result.put(key, tmpList);
        }

        return result;
    }

    /**
     * 拼接用户模板配置的机器人数量:  key:userId+templateId   value:机器人数量
     * @param configRes
     * @return
     */
    public static Map<String, Integer> convertUserRes2Map(List<UserResourceCacheWithVersion> configRes) {
        Map<String, Integer> result = new HashMap<>();
        if (configRes == null) {
            return result;
        }

        for (UserResourceCacheWithVersion item : configRes) {
            for (Map.Entry<String, TemplateInfo> ent : item.getTempInfoMap().entrySet()) {
                if(null != ent.getValue()){
                    TemplateInfo templ = (TemplateInfo)ent.getValue();
                    result.put(item.getUserId() + "-" + ent.getKey(), templ.getNum());
                }
            }
        }

        return result;
    }
}
