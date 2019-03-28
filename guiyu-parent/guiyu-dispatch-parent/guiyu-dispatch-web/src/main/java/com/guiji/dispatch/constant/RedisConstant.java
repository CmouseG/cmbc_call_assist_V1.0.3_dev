package com.guiji.dispatch.constant;

public class RedisConstant {

    public static interface RedisConstantKey{

        //推入redis总列表
        public static final String REDIS_USER_ROBOT_LINE_MAX_PLAN = "REDIS_USER_ROBOT_LINE_MAX_PLAN";

        //用户线路机器人分配数据
        public static final String REDIS_PLAN_QUEUE_USER_LINE_ROBOT = "REDIS_PLAN_QUEUE_USER_LINE_ROBOT_";

        //网关线路KEY
        public final static String gatewayLineKey = "redis_gateway_line_key_";

        //分配用户、话术模板的机器人数据
        public final static String ROBOT_USER_RESOURCE = "ROBOT_USER_RESOURCE";

        //是否已经外推消息
        public final static String MSG_NOTIFY_FLAG_ = "MSG_NOTIFY_FLAG_";

        //临时保存查询计划列表用户名称
        public final static String QUERY_PLANLIST_USERNAME_TMP = "QUERY_PLANLIST_USERNAME_TMP_";
        //临时保存查询计划列表用户名称有效时间长度
        public final static long QUERY_PLANLIST_USERNAME_TMP_TIMELONG = 10L;


        //呼叫中心模板没有准备好，不打印“五分钟没有回调，主动调用呼叫中心isCallEnd接口失败”
        public final static String TEMPLATE_NO_READY = "TEMPLATE_NO_READY_";
        //有效时间 5分钟
        public final static long TEMPLATE_NO_READY_TIMELONG = 300L;
    }
}
