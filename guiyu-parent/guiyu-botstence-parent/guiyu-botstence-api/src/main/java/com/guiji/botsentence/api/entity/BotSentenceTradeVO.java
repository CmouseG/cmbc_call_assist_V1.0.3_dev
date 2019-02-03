package com.guiji.botsentence.api.entity;

import java.io.Serializable;

public class BotSentenceTradeVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

    private String industryId;

    private String industryName;

    private Integer level;

    private String parentId;

    private String parentName;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIndustryId() {
        return industryId;
    }

    public void setIndustryId(String industryId) {
        this.industryId = industryId == null ? null : industryId.trim();
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName == null ? null : industryName.trim();
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName == null ? null : parentName.trim();
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", industryId=").append(industryId);
        sb.append(", industryName=").append(industryName);
        sb.append(", level=").append(level);
        sb.append(", parentId=").append(parentId);
        sb.append(", parentName=").append(parentName);
        sb.append("]");
        return sb.toString();
    }
}