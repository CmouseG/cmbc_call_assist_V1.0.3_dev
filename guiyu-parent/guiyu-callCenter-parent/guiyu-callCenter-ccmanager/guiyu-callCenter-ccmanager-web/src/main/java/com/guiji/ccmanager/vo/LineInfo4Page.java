package com.guiji.ccmanager.vo;

import com.guiji.callcenter.dao.entity.LineInfo;

public class LineInfo4Page extends LineInfo {

    private String CreatetUser;
    private String UpdateUser;
    private String CustomerUser;

    public String getCreatetUser() {
        return CreatetUser;
    }

    public void setCreatetUser(String createtUser) {
        CreatetUser = createtUser;
    }

    public String getUpdateUser() {
        return UpdateUser;
    }

    public void setUpdateUser(String updateUser) {
        UpdateUser = updateUser;
    }

    public String getCustomerUser() {
        return CustomerUser;
    }

    public void setCustomerUser(String customerUser) {
        CustomerUser = customerUser;
    }
}
