package com.guiji.common.result;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
public class ErrorMessage {

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
		Resource defalutResource=new ClassPathResource("properties/error.properties");
		props.load(new InputStreamReader(defalutResource.getInputStream(),"utf-8"));
		return props;
	}
}
