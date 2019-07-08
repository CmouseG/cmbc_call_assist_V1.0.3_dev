package com.guiji.fsmanager.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@ApiModel(description = "线路信息")
public class OutLineInfoAddReq implements Serializable {

    @ApiModelProperty(value = "线路名称")
    @NotBlank(message = "线路名称不能为空")
    @Length(max = 30, message = "线路名称长度不能超过30个字符")
    private String lineName;

    @ApiModelProperty(value = "sip线路IP地址")
    @Pattern(regexp = "((25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))", message = "线路IP地址格式错误")
    @NotNull(message = "IP地址不能为空")
    private String sipIp;

    @ApiModelProperty(value = "sip线路端口")
    @Min(value = 1, message = "端口只能为数字")
    @NotNull(message = "端口不能为空")
    private String sipPort;

    @ApiModelProperty(value = "编码")
    @NotNull(message = "编码不能为空")
    @Pattern(regexp = "PCMA|PCMU|G729", message = "编码格式错误")
    private String codec;

    @ApiModelProperty(value = "呼叫号码")
    private String callerNum;
    @ApiModelProperty(value = "区号")
    private String calleePrefix;

    @ApiModelProperty(value = "最大并发数")
    @Min(value = 0, message = "并发数只能为数字")
    @NotNull(message = "最大并发数不能为空")
    private Integer maxConcurrentCalls;
    @ApiModelProperty(value = "备注")
    private String remark;

    private String orgCode;
    @ApiModelProperty(value = "Register模式sip线路账号")
    private String sipUser="user";

    @ApiModelProperty(value = "Register模式sip线路密码")
    private String sipPwd="pwd";

    @ApiModelProperty(value = "线路类型")
    //trunk模式为0，register模式为1，sim卡模式为2
    private int lineType;

    //线路归属地区号
    private String lineLocation;
    //外地号码前缀
    private String nonlocalPrefix;

    private static final long serialVersionUID = 1L;

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName == null ? null : lineName.trim();
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
        return "OutLineInfoAddReq{" +
                "lineName='" + lineName + '\'' +
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