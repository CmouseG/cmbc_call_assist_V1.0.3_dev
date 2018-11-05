package com.guiji.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by ty on 2018/11/5.
 */
public class EncoderUtil {
    public static String encode(String password) {
        // 加密
        String encodedPassword = new BCryptPasswordEncoder().encode(password);
        return encodedPassword;
    }
}
