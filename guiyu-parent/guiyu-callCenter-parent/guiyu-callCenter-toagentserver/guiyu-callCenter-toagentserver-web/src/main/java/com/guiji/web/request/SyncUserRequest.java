package com.guiji.web.request;

import lombok.Data;

@Data
public class SyncUserRequest {
    private String userName;
    private String domain;
}
