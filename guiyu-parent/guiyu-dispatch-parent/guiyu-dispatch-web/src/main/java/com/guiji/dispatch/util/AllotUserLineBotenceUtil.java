package com.guiji.dispatch.util;

import com.guiji.dispatch.bean.UserLineBotenceVO;
import com.guiji.robot.model.UserResourceCache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllotUserLineBotenceUtil {

    public static List<UserLineBotenceVO> allot(List<UserLineBotenceVO> userLineBotenceVOList,
                                                List<UserResourceCache> configRes, Integer maxRobot) {

        List<UserLineBotenceVO> result = new ArrayList<>();
        if (userLineBotenceVOList == null || userLineBotenceVOList.isEmpty()) {
            return result;
        }

        Integer canDividCount = maxRobot - userLineBotenceVOList.size();
        if (canDividCount <= 0) {
            int hasDistributeCount = 0;
            for (UserLineBotenceVO item : userLineBotenceVOList) {
                if (hasDistributeCount == userLineBotenceVOList.size()) {
                    return result;
                }
                item.setMaxRobotCount(1);
                result.add(item);

                hasDistributeCount = hasDistributeCount + 1;
            }
            return result;
        }

        Map<String, List<UserLineBotenceVO>> allUserBotence = getAllUserBotence(userLineBotenceVOList);
        System.out.println(allUserBotence);
        Map<String, Integer> allUserBotenceCfg = convertUserRes2Map(configRes);


        int allCount = 0;
        for (Map.Entry<String, List<UserLineBotenceVO>> ent : allUserBotence.entrySet()) {

            allCount = allCount + allUserBotenceCfg.get(ent.getKey());
        }


        int userBotenceCount = 0;
        int hasDividCount = 0;
        Map<String, Integer> userDistributeMap = new HashMap<>();
        for (Map.Entry<String, List<UserLineBotenceVO>> ent : allUserBotence.entrySet()) {
            userBotenceCount = canDividCount * allUserBotenceCfg.get(ent.getKey()) / allCount + ent.getValue().size();
            if ((userBotenceCount) > allUserBotenceCfg.get(ent.getKey())) {
                userBotenceCount = allUserBotenceCfg.get(ent.getKey());
            }

            userDistributeMap.put(ent.getKey(), userBotenceCount);
            hasDividCount = hasDividCount + userBotenceCount;
        }

        // 补偿
        if (hasDividCount < maxRobot) {
            for (Map.Entry<String, Integer> ent : userDistributeMap.entrySet()) {
                int cut = maxRobot - hasDividCount;
                int tmp = ent.getValue() + cut - allUserBotenceCfg.get(ent.getKey());
                if (tmp > 0) {
                    ent.setValue(allUserBotenceCfg.get(ent.getKey()));
                    hasDividCount = hasDividCount + allUserBotenceCfg.get(ent.getKey()) - ent.getValue();
                } else {
                    ent.setValue(ent.getValue() + cut);
                    break;
                }
            }
        }

        System.out.println("----------");

        int everyOneCount = 0;
        for (Map.Entry<String, List<UserLineBotenceVO>> ent : allUserBotence.entrySet()) {
            everyOneCount = userDistributeMap.get(ent.getKey()) / ent.getValue().size();

            for (int i = 0; i < ent.getValue().size(); i++) {
                ent.getValue().get(i).setMaxRobotCount(everyOneCount);

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


    private static Map<String, List<UserLineBotenceVO>> getAllUserBotence(List<UserLineBotenceVO> userLineBotenceVOList) {
        Map<String, List<UserLineBotenceVO>> result = new HashMap<>();
        if (userLineBotenceVOList == null) {
            return result;
        }

        System.out.println(userLineBotenceVOList);

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

    private static Map<String, Integer> convertUserRes2Map(List<UserResourceCache> configRes) {
        Map<String, Integer> result = new HashMap<>();
        if (configRes == null) {
            return result;
        }

        for (UserResourceCache item : configRes) {
            for (Map.Entry<String, Integer> ent : item.getTempAiNumMap().entrySet()) {
                result.put(item.getUserId() + "-" + ent.getKey(), ent.getValue());
            }
        }

        return result;
    }
}
