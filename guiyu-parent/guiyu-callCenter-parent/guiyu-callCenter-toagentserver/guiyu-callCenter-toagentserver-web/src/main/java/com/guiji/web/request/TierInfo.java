package com.guiji.web.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class TierInfo {
    @NonNull
    private String queueId;
    @NonNull
    private String agentId;
    private String level;
    private String position;
}
