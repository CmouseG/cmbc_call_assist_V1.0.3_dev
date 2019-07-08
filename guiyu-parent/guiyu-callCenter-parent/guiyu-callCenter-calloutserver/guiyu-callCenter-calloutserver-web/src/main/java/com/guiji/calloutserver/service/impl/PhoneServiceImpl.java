package com.guiji.calloutserver.service.impl;

import com.guiji.callcenter.dao.PhoneMapper;
import com.guiji.callcenter.dao.entity.Phone;
import com.guiji.calloutserver.service.PhoneService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author:liyang
 * Date:2019/6/20 16:52
 * Description:
 */
@Service
@Slf4j
public class PhoneServiceImpl implements PhoneService {

    @Autowired
    PhoneMapper phoneMapper;

    @Override
    public String findLocationByPhone(String mobile) {

        Phone phone = phoneMapper.selectByPrimaryKey(mobile.substring(0,7));
        if(phone == null){
            log.warn("未跟进号码[{}]找到归属地信息", mobile);
            return null;
        }

        return phone.getAreaCode();
    }
}
