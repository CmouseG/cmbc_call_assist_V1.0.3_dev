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

    @ExcelProperty(index = 3)
    private String custCompany;

    @ExcelProperty(index = 4)
    private String custName;

    public String getPhone()
    {
        if(null == phone || "".equals(phone)){
            return "";
        }
        return phone;
    }

    public void setPhone(String phone)
    {
        if(null == phone || "".equals(phone)){
            phone = "";
        }
        this.phone = phone;
    }

    public String getParamaters()
    {
        if(null == paramaters || "".equals(paramaters)){
            return "";
        }
        return paramaters;
    }

    public void setParamaters(String paramaters)
    {
        if(null == paramaters || "".equals(paramaters)){
            paramaters = "";
        }
        this.paramaters = paramaters;
    }

    public String getAttach()
    {
        if(null == attach || "".equals(attach)){
            return "";
        }
        return attach;
    }

    public void setAttach(String attach)
    {
        if(null == attach || "".equals(attach)){
            attach = "";
        }
        this.attach = attach;
    }

    public String getCustCompany() {
        return custCompany;
    }

    public void setCustCompany(String custCompany) {
        this.custCompany = custCompany;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    @Override
    public String toString() {
        return "BatchImportExcelModel{" +
                "phone='" + phone + '\'' +
                ", paramaters='" + paramaters + '\'' +
                ", attach='" + attach + '\'' +
                ", custCompany='" + custCompany + '\'' +
                ", custName='" + custName + '\'' +
                '}';
    }
}
