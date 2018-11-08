package com.guiji.calloutserver.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.Set;

public class CommonUtil {
    /**
     * 执行系统命令，并返回结果
     */
    public static String doCommand(String cmd) throws IOException {
        String[] cmdList = null;
        if(cmd.contains(" ")){
            cmdList = cmd.split(" ");
        }else{
            cmdList = new String[]{cmd};
        }

        Process process = Runtime.getRuntime().exec(cmdList);
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));

        String str;
        StringBuilder builder = new StringBuilder();
        while ((str = stdInput.readLine()) != null) {
            builder.append(str);
        }

        return builder.toString();
    }

    /**
     * 执行shell命令，并返回结果
     */
    public static String doShCommand(String cmd) throws IOException {
        String[] cmdList = null;
        cmdList = new String[]{"/bin/sh", "-c", cmd};

        Process process = Runtime.getRuntime().exec(cmdList);
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));

        String str;
        StringBuilder builder = new StringBuilder();
        while ((str = stdInput.readLine()) != null) {
            builder.append(str);
        }

        return builder.toString();
    }


    /**
     * 获取对象中值为空的属性名称列表
     * @param source
     * @return
     */
    public static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    /**
     * json to javabean
     *
     * @param json
     */
    public static <T> T jsonToJavaBean(String json,Type type){
        Gson gson = new Gson();
        T obj = gson.fromJson(json,type);
        return  obj;
    }

    /**
     * bean转json
     * @param obj
     * @return
     */
    public  static String  beanToJson(Object obj){
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        return gson.toJson(obj);
    }

    /**
     * 将double保留两位小数
     * @param d
     * @return
     */
    public static Double formatDouble(Double d){
        BigDecimal bg = new BigDecimal(d).setScale(2, RoundingMode.UP);
        return bg.doubleValue();
    }
}
