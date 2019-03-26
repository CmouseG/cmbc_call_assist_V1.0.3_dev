package com.guiji;

import com.guiji.wechat.util.AccessToken;
import com.guiji.wechat.util.properties.WeChatEnvProperties;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableScheduling
@EnableRabbit
@EnableSwagger2
@EnableConfigurationProperties(WeChatEnvProperties.class)
@EnableFeignClients(basePackages = {"com.guiji.*.api"})//启用feign
public class WeChatApplication {

    @Bean(value = "restTemplate")
    public RestTemplate createRestTemplate() {
        return new RestTemplate();
    }

    @Bean(value = "accessToken")
    public AccessToken initiateAccessToken(){
        return new AccessToken();
    }

    public static void main(String[] args) {
        SpringApplication.run(WeChatApplication.class, args);
    }
}
