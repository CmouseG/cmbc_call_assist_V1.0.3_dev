package com.guiji.entity;

import com.guiji.callcenter.dao.entity.CallInDetail;
import com.guiji.callcenter.dao.entity.CallInDetailRecord;
import com.guiji.callcenter.dao.entity.CallOutDetail;
import com.guiji.callcenter.dao.entity.CallOutDetailRecord;
import com.guiji.util.CommonUtil;
import lombok.Data;
import org.springframework.beans.BeanUtils;


/**
 * 用于在CallInDetail和CallOutDetail上提供一个抽象对象
 */
@Data
public class CallDetail extends CallOutDetail{
    private String agentRecordFile;

    private String agentRecordUrl;

    private String botRecordFile;

    private String botRecordUrl;

    private String customerRecordFile;

    private String customerRecordUrl;

    private boolean isUpdateRecord = false;

    public void setAgentRecordFile(String agentRecordFile) {
        this.agentRecordFile = agentRecordFile;
        this.isUpdateRecord = true;
    }

    public void setAgentRecordUrl(String agentRecordUrl) {
        this.agentRecordUrl = agentRecordUrl;
        this.isUpdateRecord = true;
    }

    public void setBotRecordFile(String botRecordFile) {
        this.botRecordFile = botRecordFile;
        this.isUpdateRecord = true;
    }

    public void setBotRecordUrl(String botRecordUrl) {
        this.botRecordUrl = botRecordUrl;
        this.isUpdateRecord = true;
    }

    public void setCustomerRecordFile(String customerRecordFile) {
        this.customerRecordFile = customerRecordFile;
        this.isUpdateRecord = true;
    }

    public void setCustomerRecordUrl(String customerRecordUrl) {
        this.customerRecordUrl = customerRecordUrl;
        this.isUpdateRecord = true;
    }

    public CallOutDetail toCallOutDetail(){
        CallOutDetail callOutDetail = new CallOutDetail();
        BeanUtils.copyProperties(this, callOutDetail, CommonUtil.getNullPropertyNames(this));
        return callOutDetail;
    }

    public CallInDetail toCallInDetail(){
        CallInDetail callInDetail = new CallInDetail();
        BeanUtils.copyProperties(this, callInDetail, CommonUtil.getNullPropertyNames(this));
        return callInDetail;
    }

    public CallOutDetailRecord toCallOutDetailRecord(){
        if (isUpdateRecord) {
            CallOutDetailRecord record = new CallOutDetailRecord();
            BeanUtils.copyProperties(this, record, CommonUtil.getNullPropertyNames(this));
            return record;
        }

        return null;
    }

    public CallInDetailRecord toCallInDetailRecord(){
        if (isUpdateRecord) {
            CallInDetailRecord record = new CallInDetailRecord();
            BeanUtils.copyProperties(this, record, CommonUtil.getNullPropertyNames(this));
            return record;
        }

        return null;
    }
}