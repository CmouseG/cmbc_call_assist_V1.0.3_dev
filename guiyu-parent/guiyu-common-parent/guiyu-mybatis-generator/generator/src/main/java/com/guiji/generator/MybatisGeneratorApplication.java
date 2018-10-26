package com.guiji.generator;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

/**
 * 执行该程序，maven命令：mybatis-generator:generate
 * @author zhang.peng
 *
 */
@SpringBootApplication
public class MybatisGeneratorApplication {

	public static void main(String[] args) {
		//SpringApplication.run(MybatisGeneratorApplication.class, args);
		//MybatisGeneratorApplication.execCMD();
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
