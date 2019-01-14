package com.guiji.da.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class CustPreInfo implements Serializable {
    private Integer id;

    private String phone;

    private String name;

    private String seqId;

    private String need;

    private String isSelf;

    private String agreeConnect;

    private String age;

    private String gender;

    private String addr;

    private String home;

    private String car;

    private String marry;

    private String job;

    private String salary;

    private String warranty;

    private String appointedTime;

    private String crtDate;

    private Date crtTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSeqId() {
        return seqId;
    }

    public void setSeqId(String seqId) {
        this.seqId = seqId == null ? null : seqId.trim();
    }

    public String getNeed() {
        return need;
    }

    public void setNeed(String need) {
        this.need = need == null ? null : need.trim();
    }

    public String getIsSelf() {
        return isSelf;
    }

    public void setIsSelf(String isSelf) {
        this.isSelf = isSelf == null ? null : isSelf.trim();
    }

    public String getAgreeConnect() {
        return agreeConnect;
    }

    public void setAgreeConnect(String agreeConnect) {
        this.agreeConnect = agreeConnect == null ? null : agreeConnect.trim();
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age == null ? null : age.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr == null ? null : addr.trim();
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home == null ? null : home.trim();
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car == null ? null : car.trim();
    }

    public String getMarry() {
        return marry;
    }

    public void setMarry(String marry) {
        this.marry = marry == null ? null : marry.trim();
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job == null ? null : job.trim();
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary == null ? null : salary.trim();
    }

    public String getWarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty == null ? null : warranty.trim();
    }

    public String getAppointedTime() {
        return appointedTime;
    }

    public void setAppointedTime(String appointedTime) {
        this.appointedTime = appointedTime == null ? null : appointedTime.trim();
    }

    public String getCrtDate() {
        return crtDate;
    }

    public void setCrtDate(String crtDate) {
        this.crtDate = crtDate == null ? null : crtDate.trim();
    }

    public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", phone=").append(phone);
        sb.append(", name=").append(name);
        sb.append(", seqId=").append(seqId);
        sb.append(", need=").append(need);
        sb.append(", isSelf=").append(isSelf);
        sb.append(", agreeConnect=").append(agreeConnect);
        sb.append(", age=").append(age);
        sb.append(", gender=").append(gender);
        sb.append(", addr=").append(addr);
        sb.append(", home=").append(home);
        sb.append(", car=").append(car);
        sb.append(", marry=").append(marry);
        sb.append(", job=").append(job);
        sb.append(", salary=").append(salary);
        sb.append(", warranty=").append(warranty);
        sb.append(", appointedTime=").append(appointedTime);
        sb.append(", crtDate=").append(crtDate);
        sb.append(", crtTime=").append(crtTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}