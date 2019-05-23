package com.guiji.component.lock;

import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;


public class RedisLockLuaScripts {

    /**
     * redisUtil.setIfAbsent 新加的带有超时的setIfAbsent 脚本
     */
    private static final String newSetIfAbsentScriptStr = " if 1 == redis.call('setnx',KEYS[1],ARGV[1]) then" +
            " redis.call('expire',KEYS[1],ARGV[2])" +
            " return 1;" +
            " else" +
            " return 0;" +
            " end;";

    public static final RedisScript<Boolean> newSetIfAbsentScript = new DefaultRedisScript<Boolean>(newSetIfAbsentScriptStr, Boolean.class);
}
