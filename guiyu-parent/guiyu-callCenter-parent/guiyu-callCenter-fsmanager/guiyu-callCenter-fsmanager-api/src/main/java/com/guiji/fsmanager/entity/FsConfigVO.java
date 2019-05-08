package com.guiji.fsmanager.entity;

import java.util.List;

public class FsConfigVO {
    private String module;
    private List<ModuleVO> config;

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public List<ModuleVO> getConfig() {
        return config;
    }

    public void setConfig(List<ModuleVO> config) {
        this.config = config;
    }

    @Override
    public String toString() {
        return "FsConfigVO{" +
                "module='" + module + '\'' +
                ", config=" + config +
                '}';
    }
}
