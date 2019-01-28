package com.guiji.service.impl;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.guiji.callcenter.dao.PhoneMapper;
import com.guiji.callcenter.dao.entity.Phone;
import com.guiji.service.PhoneService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: 魏驰
 * @Date: 2018/12/31 14:10
 * @Project：ccserver
 * @Description:
 */
@Slf4j
@Service
public class PhoneServiceImpl implements PhoneService {
    @Autowired
    PhoneMapper phoneMapper;

    @Override
    public Phone getPhoneInfo(String mobile) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(mobile), "null mobile");
        return phoneMapper.selectByPrimaryKey(mobile.substring(0,7));
    }

    @Override
    public String findLocationByPhone(String mobile) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(mobile), "null mobile");
        Phone phone = phoneMapper.selectByPrimaryKey(mobile.substring(0,7));
        if(phone == null){
            log.warn("未跟进号码[{}]找到归属地信息", mobile);
            return null;
        }

        return phone.getProvince()+phone.getCity();
    }
}
