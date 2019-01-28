package com.guiji.eventbus.event;

import com.guiji.entity.ECallDirection;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChannelHangupEvent {
    //variable_call_uuid
    private String uuid;

    //variable_billsec
    private Integer billSec;

    //variable_duration
    private Integer duration;

    //variable_start_stamp
    private String startStamp;

    //variable_answer_stamp
    private String answerStamp;

    //variable_progress_media_stamp
    private String progressStamp;

    //variable_end_stamp
    private String hangupStamp;

    //谁发起的挂断, send_bye/recv_bye
    //variable_sip_hangup_disposition
    private String hangupDisposition;

    //sip挂机错误码
    private String sipHangupCause;

    private ECallDirection callDirection;
}
