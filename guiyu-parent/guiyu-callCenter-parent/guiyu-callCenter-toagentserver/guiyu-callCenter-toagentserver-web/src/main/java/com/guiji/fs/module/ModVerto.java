package com.guiji.fs.module;

import com.guiji.fs.FreeSWITCH;
import com.guiji.fs.pojo.VertoStatus;

public class ModVerto {
    private FreeSWITCH freeSWITCH;

    public ModVerto(FreeSWITCH freeSWITCH){
        this.freeSWITCH = freeSWITCH;
    }

    /**
     * 获取verto用户的状态
     * @param userId
     * @return
     */
    public VertoStatus getStatus(String userId){
        String result = freeSWITCH.execute("verto_contact " + userId);
        if(result!=null && !result.contains("user_not_registered")){
            return VertoStatus.online;
        }else{
            return VertoStatus.offline;
        }
    }
}
