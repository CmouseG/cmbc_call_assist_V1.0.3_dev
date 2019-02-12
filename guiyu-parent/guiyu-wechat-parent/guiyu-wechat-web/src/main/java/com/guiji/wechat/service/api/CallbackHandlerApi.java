package com.guiji.wechat.service.api;

import java.util.Map;

public interface CallbackHandlerApi {

    String handle(Map<String, String> callbackMessage);
}
