package com.guiji.component.result;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

@Configuration
public class ErrorMessage {
	@Value("${properties.error}")
	private String errorPropertiesPath;

//	@Bean(name="errorProperties")
//	public Properties init() throws IOException{
//		Properties props=new Properties();
//		Resource defalutResource=new ClassPathResource("properties");
//		File file=defalutResource.getFile();
//		File[] files=file.listFiles((dir,name)->{ return name.endsWith("properties"); });
//		for(File fileItem:files){
//			props.load(new InputStreamReader(new FileInputStream(fileItem), "utf-8"));
//		}
//
//		return props;
//	}

	@Bean(name="errorProperties")
	public Properties init() throws IOException{
		Properties props=new Properties();
		Resource defaultResource=new ClassPathResource(errorPropertiesPath);
		props.load(new InputStreamReader(defaultResource.getInputStream(),"utf-8"));
		return props;
	}
}
