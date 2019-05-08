package com.guiji.fsagent.fs.mod;

import com.guiji.fsagent.util.FsConfigUtil;
import lombok.Data;

/**
 * @Auther: 魏驰
 * @Date: 2019/4/1 17:39
 * @Project：guiyu-parent
 * @Description:
 */
@Data
public class FsMod {
    private String filePath;
    private String reloadCmd;

    /**
     * 设置模块属性
     * @param key
     * @param val
     */
    public void setConfig(String key, String val){
        FsConfigUtil.set(filePath, key, val);
    }

    /**
     * 获取模块属性
     * @param key
     * @param val
     * @return
     */
    public String getConfig(String key, String val){
        return FsConfigUtil.get(filePath, key);
    }

    public void reload(){}
}
