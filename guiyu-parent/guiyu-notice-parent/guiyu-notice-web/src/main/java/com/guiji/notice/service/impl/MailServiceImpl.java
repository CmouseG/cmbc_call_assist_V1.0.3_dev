package com.guiji.notice.service.impl;

import com.guiji.notice.dao.NoticeMailInfoMapper;
import com.guiji.notice.dao.NoticeMailInfoExtMapper;
import com.guiji.notice.dao.entity.NoticeMailInfo;
import com.guiji.notice.dao.entity.NoticeMailInfoExample;
import com.guiji.notice.service.MailService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    NoticeMailInfoMapper noticeMailInfoMapper;
    @Autowired
    NoticeMailInfoExtMapper noticeMailInfoExtMapper;

    @Override
    public List<Map> queryMailList(Long userId, String isRead, String noticeType,int pageSize, int pageNo) {

        Integer read = null;
        if(StringUtils.isNotBlank(isRead)){
            read = Integer.valueOf(isRead);
        }
        Integer noticeTypeInt = null;
        if(StringUtils.isNotBlank(noticeType)){
            noticeTypeInt = Integer.valueOf(noticeType);
        }
        int limitStart = (pageNo - 1) * pageSize;
       return noticeMailInfoExtMapper.queryMailList(userId,read,noticeTypeInt,limitStart,pageSize);
    }

    @Override
    public int countMailList(Long userId, String isRead, String noticeType) {
        Integer read = null;
        if(StringUtils.isNotBlank(isRead)){
            read = Integer.valueOf(isRead);
        }
        Integer noticeTypeInt = null;
        if(StringUtils.isNotBlank(noticeType)){
            noticeTypeInt = Integer.valueOf(noticeType);
        }
        return noticeMailInfoExtMapper.countMailList(userId,read,noticeTypeInt);
    }

    @Override
    public void deleteMailById(List<Integer> ids) {
        
        NoticeMailInfo noticeMailInfo = new NoticeMailInfo();
        noticeMailInfo.setIsdel(true);
        NoticeMailInfoExample example = new NoticeMailInfoExample();
        example.createCriteria().andIdIn(ids);
        noticeMailInfoMapper.updateByExampleSelective(noticeMailInfo,example);
    }

    @Override
    public Map readMailById(Integer id) {

        NoticeMailInfo example = new NoticeMailInfo();
        example.setId(Integer.valueOf(id));
        example.setIsRead(true);
        noticeMailInfoMapper.updateByPrimaryKeySelective(example);

        List<Map> list = noticeMailInfoExtMapper.selectMailInfo(Integer.valueOf(id));
        if(list!=null && list.size()>0){
            return list.get(0);
        }
        return  null;
    }
}
