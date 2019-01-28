package com.guiji.web.request;

import lombok.Data;

@Data
public class UpdateLabelRequest {
    private String callRecordId;
    private String label;
}
