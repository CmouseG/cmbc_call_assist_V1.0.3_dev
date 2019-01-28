package com.guiji.botsentence.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

/**
 * @Auther: 魏驰
 * @Date: 2019/1/10 11:44
 * @Project：guiji-parent
 * @Description:
 */
public class CommonUtil {
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

    private static int getNum(int start,int end) {
        return (int)(Math.random()*(end-start+1)+start);
    }

    private static String[] telFirst="134,135,136,137,138,139,150,151,152,157,158,159,130,131,132,155,156,133,153".split(",");
    /**
     * 获取随机手机号码
     * @return
     */
    public static String getRandPhoneNum() {
        int index=getNum(0,telFirst.length-1);
        String first=telFirst[index];
        String second=String.valueOf(getNum(1,888)+10000).substring(1);
        String third=String.valueOf(getNum(1,9100)+10000).substring(1);
        return first+second+third;
    }
}
