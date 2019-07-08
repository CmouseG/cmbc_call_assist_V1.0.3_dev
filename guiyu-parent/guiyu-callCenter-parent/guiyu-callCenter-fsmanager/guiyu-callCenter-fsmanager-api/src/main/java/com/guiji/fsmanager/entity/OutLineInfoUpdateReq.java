package com.guiji.fsmanager.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@ApiModel(description = "线路信息")
public class OutLineInfoUpdateReq implements Serializable {

    @ApiModelProperty(value = "线路id")
    @NotEmpty(message = "id不能为空")
    private Integer lineId;

    @ApiModelProperty(value = "sip线路IP地址")
    @Pattern(regexp = "((25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))", message = "线路IP地址格式错误")
    private String sipIp;

    @ApiModelProperty(value = "sip线路端口")
    @Min(value = 1, message = "端口只能为数字")
    private String sipPort;

    @ApiModelProperty(value = "编码")
    @Pattern(regexp = "PCMA|PCMU|G729", message = "编码格式错误")
    private String codec;

    @ApiModelProperty(value = "呼叫号码")
    private String callerNum;
    @ApiModelProperty(value = "区号")
    private String calleePrefix;

    @ApiModelProperty(value = "最大并发数")
    @Min(value = 0, message = "并发数只能为数字")
    private Integer maxConcurrentCalls;
    @ApiModelProperty(value = "备注")
    private String remark;

    private String orgCode;

    private String sipUser="user";

    private String sipPwd="pwd";

    private int lineType;

    //线路归属地区号
    private String lineLocation;
    //外地号码前缀
    private String nonlocalPrefix;

    private static final long serialVersionUID = 1L;

    public Integer getLineId() {
        return lineId;
    }

    public void setLineId(Integer lineId) {
        this.lineId = lineId;
    }

    public String getSipIp() {
        return sipIp;
    }

    public void setSipIp(String sipIp) {
        this.sipIp = sipIp == null ? null : sipIp.trim();
    }

    public String getSipPort() {
        return sipPort;
    }

    public void setSipPort(String sipPort) {
        this.sipPort = sipPort == null ? null : sipPort.trim();
    }

    public String getCodec() {
        return codec;
    }

    public void setCodec(String codec) {
        this.codec = codec == null ? null : codec.trim();
    }

    public String getCallerNum() {
        return callerNum;
    }

    public void setCallerNum(String callerNum) {
        this.callerNum = callerNum == null ? null : callerNum.trim();
    }

    public String getCalleePrefix() {
        return calleePrefix;
    }

    public void setCalleePrefix(String calleePrefix) {
        this.calleePrefix = calleePrefix == null ? null : calleePrefix.trim();
    }

    public Integer getMaxConcurrentCalls() {
        return maxConcurrentCalls;
    }

    public void setMaxConcurrentCalls(Integer maxConcurrentCalls) {
        this.maxConcurrentCalls = maxConcurrentCalls;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }


    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getSipUser() {
        return sipUser;
    }

    public void setSipUser(String sipUser) {
        this.sipUser = sipUser;
    }

    public String getSipPwd() {
        return sipPwd;
    }

    public void setSipPwd(String sipPwd) {
        this.sipPwd = sipPwd;
    }

    public int getLineType() {
        return lineType;
    }

    public void setLineType(int lineType) {
        this.lineType = lineType;
    }

    public String getLineLocation() {
        return lineLocation;
    }

    public void setLineLocation(String lineLocation) {
        this.lineLocation = lineLocation;
    }

    public String getNonlocalPrefix() {
        return nonlocalPrefix;
    }

    public void setNonlocalPrefix(String nonlocalPrefix) {
        this.nonlocalPrefix = nonlocalPrefix;
    }

    @Override
    public String toString() {
        return "OutLineInfoUpdateReq{" +
                "lineId=" + lineId +
                ", sipIp='" + sipIp + '\'' +
                ", sipPort='" + sipPort + '\'' +
                ", codec='" + codec + '\'' +
                ", callerNum='" + callerNum + '\'' +
                ", calleePrefix='" + calleePrefix + '\'' +
                ", maxConcurrentCalls=" + maxConcurrentCalls +
                ", remark='" + remark + '\'' +
                ", orgCode='" + orgCode + '\'' +
                ", sipUser='" + sipUser + '\'' +
                ", sipPwd='" + sipPwd + '\'' +
                ", lineType=" + lineType +
                ", lineLocation=" + lineLocation +
                ", nonlocalPrefix=" + nonlocalPrefix +
                '}';
    }
}