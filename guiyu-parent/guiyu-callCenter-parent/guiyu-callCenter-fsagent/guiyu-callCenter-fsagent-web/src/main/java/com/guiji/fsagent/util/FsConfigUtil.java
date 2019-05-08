package com.guiji.fsagent.util;

import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Auther: 魏驰
 * @Date: 2019/4/1 11:12
 * @Project：guiyu-parent
 * @Description:
 */
@Slf4j
public class FsConfigUtil {
    public static String get(String filePath, String key){
        log.info("开始读配置文件，file[{}], key[{}]", filePath, key);
        String value = null;
        try {
            List<String> allLines = Files.readAllLines(Paths.get(filePath));
            for(String line: allLines){
                if(!Strings.isNullOrEmpty(line)){
                    line = line.trim();
                    if(line.contains(key)) {
                        if(line.startsWith("<X-PRE-PROCESS ")){
                            int index = line.lastIndexOf("=");
                            value = line.substring(index+1, line.length()-3);
                        }else if(line.startsWith("<param")){
                            int index = line.lastIndexOf("=");
                            value = line.substring(index+2, line.length()-3);
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.warn("读配置时出现异常"+filePath + ", key:" + key, e);
        }

        return value;
    }

    /**
     * 将指定的属性，写到配置文件中
     * @param filePath
     * @param key
     * @param value
     */
    public static void set(String filePath, String key, String value){
        log.info("开始写配置文件，file[{}], key[{}], value[{}]", filePath, key, value);
        try {
            String line;
            List<String> allLines = Files.readAllLines(Paths.get(filePath));
            for(int i=0;i<allLines.size();i++){
                line = allLines.get(i);
                if(!Strings.isNullOrEmpty(line)){
                    if(line.contains(key)){
                        if(line.contains("<X-PRE-PROCESS ")){
                            line = String.format("<X-PRE-PROCESS cmd=\"set\" data=\"%s=%s\"/>", key, value);
                        }else if(line.contains("<param")){
                            line = String.format("<param name=\"%s\" value=\"%s\"/>", key, value);
                        }else{
                            log.warn("未知的配置文件类型[{}]", line);
                        }

                        log.info("将配置文件[{}]第[{}]行的配置修改为[{}]", filePath, i+1, line);
                        allLines.set(i, line);
                    }
                }
            }

            Files.write(Paths.get(filePath), allLines);
        } catch (Exception e) {
            log.warn("写配置文件出现异常"+filePath + ", key:" + key, e);
            e.printStackTrace();
        }
    }
}
