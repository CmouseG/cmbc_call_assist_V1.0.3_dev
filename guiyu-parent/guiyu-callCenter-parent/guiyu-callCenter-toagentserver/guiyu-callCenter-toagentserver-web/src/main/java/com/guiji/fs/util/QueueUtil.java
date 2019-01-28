package com.guiji.fs.util;

public class QueueUtil {
    /**
     * 构建FreeSWITCH可以识别的queueId
     * @param queueId
     * @return
     */
    public static String buildQueueId(String queueId){
        return queueId;
    }

    /**
     * 将从FreeSWITCH获取的id进行清理,去掉@后面的信息
     * @param queueId
     * @return
     */
    public static String unbuildQueueId(String queueId){
        int key = queueId.indexOf("@");
        if(key>0){
            queueId = queueId.substring(0, key);
        }

        return queueId;
    }
}
