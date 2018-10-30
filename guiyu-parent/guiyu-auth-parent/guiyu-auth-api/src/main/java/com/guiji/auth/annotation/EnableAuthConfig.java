package com.guiji.auth.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import com.guiji.auth.advice.ErrorController;
import com.guiji.auth.cache.dao.RedisSessionDAO;
import com.guiji.auth.config.RedisCacheConfig;
import com.guiji.auth.config.ShiroConfig;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({RedisSessionDAO.class,ShiroConfig.class,RedisCacheConfig.class,ErrorController.class})
public @interface EnableAuthConfig {

}
