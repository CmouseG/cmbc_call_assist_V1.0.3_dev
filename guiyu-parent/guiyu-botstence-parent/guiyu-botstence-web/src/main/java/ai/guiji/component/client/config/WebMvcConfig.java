package ai.guiji.component.client.config;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.MultipartConfigElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import ai.guiji.component.client.interceptor.AccessLogInterceptor;
import ai.guiji.component.client.util.SkUtil;




/**
* @ClassName: WebMvcConfig
* @Description: 配置系统拦截器
* @author: weiyunbo
* @date 2018年5月30日 下午4:36:02
* @version V1.0
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    @Autowired
    private AccessLogInterceptor accessLogInterceptor;
    @Value("${file.tmpPath:apps/tmp/}")
    private String tempFilePath;

    @Override
	  public void addCorsMappings(CorsRegistry registry) {
	    registry.addMapping("/**")
	        .allowedOrigins("*")
	        .allowCredentials(true)
	        .allowedMethods("GET", "POST", "DELETE", "PUT","OPTIONS")
	        .allowedHeaders("*")
	        .maxAge(3600);
	  }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(accessLogInterceptor).addPathPatterns("/**");
//    }

    /**
     * 跨域过滤器
     * @return
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig()); // 4
        return new CorsFilter(source);
    }

    /**
     * 文件上传临时路径
     */
     @Bean
     MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        String tmpFilePath = SkUtil.getRootPath()+tempFilePath; //根目录+配置的临时目录
        factory.setLocation(tmpFilePath);
        return factory.createMultipartConfig();
    }


    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        List<String> list = new ArrayList<>();
	    list.add("*");
	    corsConfiguration.setAllowedOrigins(list);
        corsConfiguration.addAllowedOrigin("*");
//        corsConfiguration.addAllowedOrigin("http://localhost:1000");
//        corsConfiguration.addAllowedOrigin("http://localhost:1001");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        return corsConfiguration;
    }
}
