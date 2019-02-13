package com.guiji.wechat.service.api;

import com.guiji.wechat.dtos.WeChatUserDto;

public interface WeChatCommonApi {

    WeChatUserDto getWeChatUserInfo(String openId);
}
