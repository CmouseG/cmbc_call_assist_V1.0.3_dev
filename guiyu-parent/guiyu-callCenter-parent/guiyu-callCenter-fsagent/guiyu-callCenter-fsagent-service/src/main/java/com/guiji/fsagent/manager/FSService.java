package com.guiji.fsagent.manager;

import com.guiji.fsagent.config.FsConfig;
import com.guiji.fsagent.entity.FreeSWITCH;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class FSService {
    private List<FreeSWITCH> fsList;

    @Autowired
    FsConfig fsConfig;

    @PostConstruct
    public void init() {
        fsList = initFSList();
    }

    /**
     * 返回当前目录下所有的FreeSWITCH
     *
     * @return
     */
    public List<FreeSWITCH> getAllFS() {
        return fsList;
    }

    /**
     * 根据fs名字获取fs对象
     *
     * @param fsName
     * @return
     */
    public FreeSWITCH getFSByName(String fsName) {
        for (FreeSWITCH fs : fsList) {
            if (fs.getFsName().equals(fsName)) {
                return fs;
            }
        }
        return null;
    }

    /**
     * 初始化FreeSWITCH文件
     *
     * @return
     */
    private List<FreeSWITCH> initFSList() {
        List<FreeSWITCH> fsList = new ArrayList<>();

        File[] files = new File(fsConfig.getHomeDir()).listFiles();
        String fsFileName;
        for (File file : files) {
            fsFileName = file.getName();
            if (file.isDirectory() && fsFileName.matches("fs\\d{0,3}")) {
                FreeSWITCH fs = new FreeSWITCH(fsFileName, file.getAbsolutePath());
                fsList.add(fs);
            } else if (file.isDirectory() && fsFileName.equals("conf")) {  //homeDir直接指向单个freeswitch的目录
                //判断是否为真正的freeswitch配置文件目录
                if (isExist(file.getAbsolutePath() + "/vars.xml")) {
                    // FreeSWITCH fs = new FreeSWITCH("fs", file.getAbsolutePath());
                    FreeSWITCH fs = new FreeSWITCH("fs", fsConfig.getHomeDir());
                    fsList.add(fs);
                }
            }
        }
        return fsList;
    }

    /**
     * 判断文件
     * @param fileName
     * @return
     */
    public static boolean isExist(String fileName) {
        File file = new File(fileName);
        return file.exists();
    }

}
