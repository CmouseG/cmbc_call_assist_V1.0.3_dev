package com.guiji.eventbus.event;

import com.guiji.entity.CallDetail;
import com.guiji.entity.ECallDirection;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Auther: 魏驰
 * @Date: 2019/1/24 21:25
 * @Project：guiyu-parent
 * @Description:
 */
@Data
@AllArgsConstructor
public class UploadRecordEvent {
    private CallDetail callDetail;
    private ECallDirection callDirection;
}
