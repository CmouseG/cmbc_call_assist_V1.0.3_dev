package com.guiji.process.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ty on 2019/3/19.
 */
public class PublishBotstenceTaskVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String jobId;
    private List<String> subJobIds;

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public List<String> getSubJobIds() {
        return subJobIds;
    }

    public void setSubJobIds(List<String> subJobIds) {
        this.subJobIds = subJobIds;
    }
}
