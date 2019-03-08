package com.guiji.dispatch.batchimport.listener;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

public class BatchImportExcelModel extends BaseRowModel
{

    @ExcelProperty(index = 0)
    private String phone;

    @ExcelProperty(index = 1)
    private String paramaters;

    @ExcelProperty(index = 2)
    private String attach;

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getParamaters()
    {
        return paramaters;
    }

    public void setParamaters(String paramaters)
    {
        this.paramaters = paramaters;
    }

    public String getAttach()
    {
        return attach;
    }

    public void setAttach(String attach)
    {
        this.attach = attach;
    }

    @Override
    public String toString()
    {
        return "BatchImportExcelModel{" + "phone='" + phone + '\'' + ", paramaters='" + paramaters + '\'' + ", attach='" + attach + '\'' + '}';
    }
}
