package com.guiji.dispatch.constant;

public class RedisConstant {

    public static interface RedisConstantKey{

        //用户线路机器人分配数据
        public static final String REDIS_PLAN_QUEUE_USER_LINE_ROBOT = "REDIS_PLAN_QUEUE_USER_LINE_ROBOT_";

        //网关线路KEY
        public final static String gatewayLineKey = "redis_gateway_line_key_";

        //分配用户、话术模板的机器人数据
        public final static String ROBOT_USER_RESOURCE = "ROBOT_USER_RESOURCE";
    }
}
