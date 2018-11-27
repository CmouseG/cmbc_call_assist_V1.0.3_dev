package ai.guiji;


import java.util.List;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import ai.guiji.component.client.config.JsonParamArgResolverHandler;


/** 
* @ClassName: BotSentenceApplication 
* @Description: 话术市场
* @author: zhangpeng
* @date 2018年8月06日 上午9:45:26 
* @version V1.0   
*/
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages={"com.guiji.*.api"})
@MapperScan({"ai.guiji.*.dao","ai.guiji.botsentence.crm.dao"})
public class BotSentenceApplication extends WebMvcConfigurerAdapter{

	public static void main(String[] args) {
		SpringApplication.run(BotSentenceApplication.class, args);
	}
	
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(new JsonParamArgResolverHandler());
		super.addArgumentResolvers(argumentResolvers);
	}
}
