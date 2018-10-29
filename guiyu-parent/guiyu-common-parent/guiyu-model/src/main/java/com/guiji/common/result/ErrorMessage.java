package com.guiji.common.result;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
public class ErrorMessage {
	
	@Value("${error.file:properties/error.properties}")
	private String errorFile;
	
	@Bean(name="errorProperties")
	public Properties init() throws IOException{
		Properties props=new Properties();
		Resource resource=new ClassPathResource(errorFile);
		if(resource.exists()){
			props.load(new InputStreamReader(resource.getInputStream(), "utf-8"));
		}
		return props;
	}
}
