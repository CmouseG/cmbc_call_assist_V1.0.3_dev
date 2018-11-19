package ai.guiji.botsentence.crm.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class TabCustomer implements Serializable {
    private Integer id;

    private String company;

    private String name;

    private String phone;

    private Date adddate;

    private Date termdate;

    private String code;

    private Integer portNum;

    private String serialNumber;

    private String machineCode;

    private String phonePort;

    private Boolean islimit;

    private Byte limitNum;

    private String operate;

    private String temp;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company == null ? null : company.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Date getAdddate() {
        return adddate;
    }

    public void setAdddate(Date adddate) {
        this.adddate = adddate;
    }

    public Date getTermdate() {
        return termdate;
    }

    public void setTermdate(Date termdate) {
        this.termdate = termdate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public Integer getPortNum() {
        return portNum;
    }

    public void setPortNum(Integer portNum) {
        this.portNum = portNum;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber == null ? null : serialNumber.trim();
    }

    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode == null ? null : machineCode.trim();
    }

    public String getPhonePort() {
        return phonePort;
    }

    public void setPhonePort(String phonePort) {
        this.phonePort = phonePort == null ? null : phonePort.trim();
    }

    public Boolean getIslimit() {
        return islimit;
    }

    public void setIslimit(Boolean islimit) {
        this.islimit = islimit;
    }

    public Byte getLimitNum() {
        return limitNum;
    }

    public void setLimitNum(Byte limitNum) {
        this.limitNum = limitNum;
    }

    public String getOperate() {
        return operate;
    }

    public void setOperate(String operate) {
        this.operate = operate == null ? null : operate.trim();
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp == null ? null : temp.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", company=").append(company);
        sb.append(", name=").append(name);
        sb.append(", phone=").append(phone);
        sb.append(", adddate=").append(adddate);
        sb.append(", termdate=").append(termdate);
        sb.append(", code=").append(code);
        sb.append(", portNum=").append(portNum);
        sb.append(", serialNumber=").append(serialNumber);
        sb.append(", machineCode=").append(machineCode);
        sb.append(", phonePort=").append(phonePort);
        sb.append(", islimit=").append(islimit);
        sb.append(", limitNum=").append(limitNum);
        sb.append(", operate=").append(operate);
        sb.append(", temp=").append(temp);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}