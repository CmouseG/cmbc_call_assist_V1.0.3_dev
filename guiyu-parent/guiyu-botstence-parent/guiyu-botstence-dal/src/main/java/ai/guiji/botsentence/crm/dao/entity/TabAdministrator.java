package ai.guiji.botsentence.crm.dao.entity;

import java.io.Serializable;

public class TabAdministrator implements Serializable {
    private Integer id;

    private String username;

    private String userpwd;

    private String tip;

    private String loginline;

    private Integer addTime;

    private Short groupid;

    private String validFlag;

    private String termdate;

    private Integer customerId;

    private Integer db;

    private String phone;

    private String email;

    private Double surplus;

    private Byte role;

    private Integer balance;

    private String industrytemp;

    private String manageCustomers;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getUserpwd() {
        return userpwd;
    }

    public void setUserpwd(String userpwd) {
        this.userpwd = userpwd == null ? null : userpwd.trim();
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip == null ? null : tip.trim();
    }

    public String getLoginline() {
        return loginline;
    }

    public void setLoginline(String loginline) {
        this.loginline = loginline == null ? null : loginline.trim();
    }

    public Integer getAddTime() {
        return addTime;
    }

    public void setAddTime(Integer addTime) {
        this.addTime = addTime;
    }

    public Short getGroupid() {
        return groupid;
    }

    public void setGroupid(Short groupid) {
        this.groupid = groupid;
    }

    public String getValidFlag() {
        return validFlag;
    }

    public void setValidFlag(String validFlag) {
        this.validFlag = validFlag == null ? null : validFlag.trim();
    }

    public String getTermdate() {
        return termdate;
    }

    public void setTermdate(String termdate) {
        this.termdate = termdate == null ? null : termdate.trim();
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getDb() {
        return db;
    }

    public void setDb(Integer db) {
        this.db = db;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Double getSurplus() {
        return surplus;
    }

    public void setSurplus(Double surplus) {
        this.surplus = surplus;
    }

    public Byte getRole() {
        return role;
    }

    public void setRole(Byte role) {
        this.role = role;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public String getIndustrytemp() {
        return industrytemp;
    }

    public void setIndustrytemp(String industrytemp) {
        this.industrytemp = industrytemp == null ? null : industrytemp.trim();
    }

    public String getManageCustomers() {
        return manageCustomers;
    }

    public void setManageCustomers(String manageCustomers) {
        this.manageCustomers = manageCustomers == null ? null : manageCustomers.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", username=").append(username);
        sb.append(", userpwd=").append(userpwd);
        sb.append(", tip=").append(tip);
        sb.append(", loginline=").append(loginline);
        sb.append(", addTime=").append(addTime);
        sb.append(", groupid=").append(groupid);
        sb.append(", validFlag=").append(validFlag);
        sb.append(", termdate=").append(termdate);
        sb.append(", customerId=").append(customerId);
        sb.append(", db=").append(db);
        sb.append(", phone=").append(phone);
        sb.append(", email=").append(email);
        sb.append(", surplus=").append(surplus);
        sb.append(", role=").append(role);
        sb.append(", balance=").append(balance);
        sb.append(", industrytemp=").append(industrytemp);
        sb.append(", manageCustomers=").append(manageCustomers);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}