package com.guiji.calloutserver.eventbus.event;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

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
    private Date startStamp;

    //variable_answer_stamp
    private Date answerStamp;

    //variable_progress_media_stamp
    private Date progressStamp;

    //variable_end_stamp
    private Date hangupStamp;

    //谁发起的挂断, send_bye/recv_bye
    //variable_sip_hangup_disposition
    private String hangupDisposition;

    //sip挂机错误码
    private String sipHangupCause;

    //rtp_audio_in_media_packet_count
    private String rtpAudioInMediaPacketCount;
}
