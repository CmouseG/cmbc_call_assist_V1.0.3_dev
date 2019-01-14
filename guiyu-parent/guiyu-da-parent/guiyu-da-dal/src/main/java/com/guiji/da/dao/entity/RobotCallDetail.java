package com.guiji.da.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class RobotCallDetail implements Serializable {
    private Integer id;

    private String seqId;

    private String scene;

    private String humanWav;

    private String humanSay;

    private String sayTime;

    private String robotSay;

    private String type;

    private String robotWav;

    private String aiScene;

    private String aiIntent;

    private Integer domainType;

    private Integer isRefused;

    private Integer hangupType;

    private Integer matchType;

    private String wavId;

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

    public String getSeqId() {
        return seqId;
    }

    public void setSeqId(String seqId) {
        this.seqId = seqId == null ? null : seqId.trim();
    }

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene == null ? null : scene.trim();
    }

    public String getHumanWav() {
        return humanWav;
    }

    public void setHumanWav(String humanWav) {
        this.humanWav = humanWav == null ? null : humanWav.trim();
    }

    public String getHumanSay() {
        return humanSay;
    }

    public void setHumanSay(String humanSay) {
        this.humanSay = humanSay == null ? null : humanSay.trim();
    }

    public String getSayTime() {
        return sayTime;
    }

    public void setSayTime(String sayTime) {
        this.sayTime = sayTime == null ? null : sayTime.trim();
    }

    public String getRobotSay() {
        return robotSay;
    }

    public void setRobotSay(String robotSay) {
        this.robotSay = robotSay == null ? null : robotSay.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getRobotWav() {
        return robotWav;
    }

    public void setRobotWav(String robotWav) {
        this.robotWav = robotWav == null ? null : robotWav.trim();
    }

    public String getAiScene() {
        return aiScene;
    }

    public void setAiScene(String aiScene) {
        this.aiScene = aiScene == null ? null : aiScene.trim();
    }

    public String getAiIntent() {
        return aiIntent;
    }

    public void setAiIntent(String aiIntent) {
        this.aiIntent = aiIntent == null ? null : aiIntent.trim();
    }

    public Integer getDomainType() {
        return domainType;
    }

    public void setDomainType(Integer domainType) {
        this.domainType = domainType;
    }

    public Integer getIsRefused() {
        return isRefused;
    }

    public void setIsRefused(Integer isRefused) {
        this.isRefused = isRefused;
    }

    public Integer getHangupType() {
        return hangupType;
    }

    public void setHangupType(Integer hangupType) {
        this.hangupType = hangupType;
    }

    public Integer getMatchType() {
        return matchType;
    }

    public void setMatchType(Integer matchType) {
        this.matchType = matchType;
    }

    public String getWavId() {
        return wavId;
    }

    public void setWavId(String wavId) {
        this.wavId = wavId == null ? null : wavId.trim();
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
        sb.append(", seqId=").append(seqId);
        sb.append(", scene=").append(scene);
        sb.append(", humanWav=").append(humanWav);
        sb.append(", humanSay=").append(humanSay);
        sb.append(", sayTime=").append(sayTime);
        sb.append(", robotSay=").append(robotSay);
        sb.append(", type=").append(type);
        sb.append(", robotWav=").append(robotWav);
        sb.append(", aiScene=").append(aiScene);
        sb.append(", aiIntent=").append(aiIntent);
        sb.append(", domainType=").append(domainType);
        sb.append(", isRefused=").append(isRefused);
        sb.append(", hangupType=").append(hangupType);
        sb.append(", matchType=").append(matchType);
        sb.append(", wavId=").append(wavId);
        sb.append(", crtDate=").append(crtDate);
        sb.append(", crtTime=").append(crtTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}