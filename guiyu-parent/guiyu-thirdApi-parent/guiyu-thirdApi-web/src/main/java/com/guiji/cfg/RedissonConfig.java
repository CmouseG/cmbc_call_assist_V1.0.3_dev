package com.guiji.cfg;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * redisson 配置类
 * Created on 2018/6/19
 */
@Configuration
public class RedissonConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private String port;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.database}")
    private Integer database;

    @Value("${spring.redis.timeout}")
    private Integer timeout;

    @Value("${spring.redis.pool.max-active}")
    private Integer maxActive;

    @Value("${spring.redis.pool.max-idle}")
    private Integer maxIdle;

    @Value("${spring.redis.pool.min-idle}")
    private Integer minIdle;

    @Value("${spring.redis.pool.max-wait}")
    private Integer maxWait;

    @Bean(destroyMethod = "shutdown")
    public RedissonClient getRedisson() {

        Config config = new Config();

        config.setCodec(new org.redisson.codec.JsonJacksonCodec());
        config.useSingleServer().setAddress("redis://" + host + ":" + port)
                .setDatabase(database)
                .setPassword(password)
                .setTimeout(timeout)
                .setConnectionMinimumIdleSize(minIdle)
                .setConnectionPoolSize(maxIdle);
        //添加主从配置
//        config.useMasterSlaveServers().setMasterAddress("").setPassword("").addSlaveAddress(new String[]{"",""});

        return Redisson.create(config);
    }

}