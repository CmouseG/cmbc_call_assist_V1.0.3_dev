package com.guiji.service;

import com.guiji.callcenter.dao.entity.Phone;

/**
 * @Auther: 魏驰
 * @Date: 2018/12/31 14:09
 * @Project：ccserver
 * @Description:
 */
public interface PhoneService {
    /**
     * 获取手机号码相关信息
     * @param mobile
     * @return
     */
    Phone getPhoneInfo(String mobile);

    /**
     * 获取号码归属地
     * @param mobile
     * @return
     */
    String findLocationByPhone(String mobile);
}
