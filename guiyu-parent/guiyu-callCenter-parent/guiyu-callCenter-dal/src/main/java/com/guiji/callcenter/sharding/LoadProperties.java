package com.guiji.callcenter.sharding;

import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.Date;
import java.util.Properties;

public class LoadProperties {

    private static Properties prop = new Properties();
    static {
        try {
        	InputStream resourceAsStream = LoadProperties.class.getClassLoader().getResourceAsStream("jdbc.properties");
            prop.load(resourceAsStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return prop.getProperty(key);
    }

    public static void updateProperties(String keyname, String keyvalue) {
        try {
            File file = ResourceUtils.getFile("classpath:authorization.properties");
            OutputStream fos = new FileOutputStream(file);
            prop.setProperty(keyname, keyvalue);
            prop.store(fos, "andieguo modify" + new Date().toString());
        } catch (IOException e) {
            System.err.println("LoadProperties error");
        }
    }

}


