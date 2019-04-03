package com.guiji.wechat.service;

import com.alibaba.fastjson.JSON;
import com.guiji.wechat.dtos.WeChatUserDto;
import com.guiji.wechat.service.api.WeChatCommonApi;
import com.guiji.wechat.util.constants.WeChatConstant;
import com.guiji.wechat.util.properties.WeChatUrlProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

import static com.guiji.wechat.util.constants.WeChatConstant.PARAM_OPEN_ID;

@Service
public class WeChatCommonImpl implements WeChatCommonApi {

    private Logger logger = LoggerFactory.getLogger(WeChatCommonImpl.class);

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private WeChatUrlProperty weChatUrlProperty;

    @Override
    public WeChatUserDto getWeChatUserInfo(String openId) {

        StringHttpMessageConverter messageConverter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        RestTemplate localRest = new RestTemplateBuilder().additionalMessageConverters(messageConverter).build();

        String accessToken = stringRedisTemplate.opsForValue().get(WeChatConstant.ACCESS_TOKEN_CACHE_KEY);
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(weChatUrlProperty.getSingleWeChatUserInfoUrl())
                .queryParam(WeChatConstant.PARAM_ACCESS_TOKEN, accessToken)
                .queryParam(PARAM_OPEN_ID, openId);

        ResponseEntity<String> responseEntity = localRest.getForEntity(builder.build().toUri(), String.class);

        logger.info("weChat user info:{}", responseEntity.getBody());

        return JSON.parseObject(responseEntity.getBody(), WeChatUserDto.class);
    }
}
