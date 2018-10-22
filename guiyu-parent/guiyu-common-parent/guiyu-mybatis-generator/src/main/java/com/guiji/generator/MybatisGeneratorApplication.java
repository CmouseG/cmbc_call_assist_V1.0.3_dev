package com.guiji.generator;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class MybatisGeneratorApplication {

	public static void main(String[] args) {
//		SpringApplication.run(MybatisGeneratorApplication.class, args);
		MybatisGeneratorApplication.execCMD();
	}
	
    public static void execCMD(){
        Runtime runtime=Runtime.getRuntime();
        try {
            runtime.exec("cmd /k cd D:\\develop\\workspace\\Mybatis-Generator && mvn mybatis-generator:generate");
//            runtime.exec("cmd.exe   /d   cd D:\\develop\\workspace\\Mybatis-Generator   mvn mybatis-generator:generate");
        } catch (IOException e) {        
            e.printStackTrace();
        }        
    }
}
