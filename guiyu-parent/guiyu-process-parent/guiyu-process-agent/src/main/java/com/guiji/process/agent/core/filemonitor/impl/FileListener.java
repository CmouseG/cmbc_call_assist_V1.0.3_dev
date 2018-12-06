package com.guiji.process.agent.core.filemonitor.impl;

import java.io.*;
import java.util.logging.Logger;

import com.guiji.process.agent.service.ProcessCfgService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.slf4j.LoggerFactory;

/**
 * 文件监听器
 */
public final class FileListener extends FileAlterationListenerAdaptor {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(FileListener.class);
    /**
     * 文件创建执行
     */
    public void onFileCreate(File file) {
        logger.debug("[新建]:" + file.getAbsolutePath());
    }

    /**
     * 文件创建修改
     */
    public void onFileChange(File file) {
        logger.debug("[修改]:" + file.getAbsolutePath());
        BufferedReader buf = null;
        BufferedReader br = null;
        try {
            buf = new BufferedReader(new InputStreamReader(new FileInputStream(file),"GBK"));
            br = new BufferedReader(buf);
            String line = null;
            ProcessCfgService.getIntance().onChanged(file);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 这里释放系统 IO 资源
            IOUtils.closeQuietly(buf);
            IOUtils.closeQuietly(br);
        }
    }

    /**
     * 文件删除
     */
    public void onFileDelete(File file) {
        logger.debug("[删除]:" + file.getAbsolutePath());
    }

    /**
     * 目录创建
     */
    public void onDirectoryCreate(File directory) {
        logger.debug("[新建]:" + directory.getAbsolutePath());
    }

    /**
     * 目录修改
     */
    public void onDirectoryChange(File directory) {
        logger.debug("[修改]:" + directory.getAbsolutePath());
    }

    /**
     * 目录删除
     */
    public void onDirectoryDelete(File directory) {
        logger.debug("[删除]:" + directory.getAbsolutePath());
    }

    public void onStart(FileAlterationObserver observer) {
        // TODO Auto-generated method stub
        super.onStart(observer);
    }

    public void onStop(FileAlterationObserver observer) {
        // TODO Auto-generated method stub
        super.onStop(observer);
    }

}
