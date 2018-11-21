package com.guiji.robot.util;

import java.io.File;

/** 
* @ClassName: SystemUtil 
* @Description: 系统级工具类
* @date 2018年11月20日 下午5:06:03 
* @version V1.0  
*/
public class SystemUtil {

	/**
     * 获取根目录
     * @return the root path
     */
    public static String getRootPath() {
        String rootPath = "";
        /** For Windows */
        if ("\\".equals(File.separator)) {
            return System.getProperty("user.dir").substring(0, 2)+"/";
        }
        /** For Linux */
        if ("/".equals(File.separator)) {
          return "/";
        }
        return rootPath;
    }
}
