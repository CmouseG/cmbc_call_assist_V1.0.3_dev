package com.guiji.billing.dto;

import java.io.Serializable;

public class EditRechargeSnapshotDto implements Serializable {

    /**
     * 充值计费ID
     */
    private String chargingId;

    /**
     * 附件快照地址
     */
    private String attachmentSnapshotUrl;

    public String getChargingId() {
        return chargingId;
    }

    public void setChargingId(String chargingId) {
        this.chargingId = chargingId;
    }

    public String getAttachmentSnapshotUrl() {
        return attachmentSnapshotUrl;
    }

    public void setAttachmentSnapshotUrl(String attachmentSnapshotUrl) {
        this.attachmentSnapshotUrl = attachmentSnapshotUrl;
    }
}
