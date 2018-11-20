package com.guiji.ccmanager.vo;

import com.guiji.callcenter.dao.entity.LineInfo;

/**
 * @Auther: 黎阳
 * @Date: 2018/11/20 0020 14:16
 * @Description: 查询列表是使用
 */
public class LineInfo4Select extends LineInfo {

    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
