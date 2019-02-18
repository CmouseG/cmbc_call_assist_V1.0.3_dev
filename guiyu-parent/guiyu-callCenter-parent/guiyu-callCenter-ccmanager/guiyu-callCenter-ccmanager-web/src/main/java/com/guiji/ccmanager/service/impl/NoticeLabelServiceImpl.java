package com.guiji.ccmanager.service.impl;

import com.guiji.callcenter.dao.NoticeSendLabelMapper;
import com.guiji.callcenter.dao.entity.NoticeSendLabel;
import com.guiji.callcenter.dao.entity.NoticeSendLabelExample;
import com.guiji.ccmanager.service.NoticeLabelService;
import com.guiji.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class NoticeLabelServiceImpl implements NoticeLabelService {

    @Autowired
    NoticeSendLabelMapper noticeSendLabelMapper;
    @Autowired
    RedisUtil redisUtil;

    @Override
    public String queryNoticeIntent(String orgCode) {
        Object value =  redisUtil.get("callCenter_notice_label_orgCode_"+orgCode);
        if(value!=null){
            return (String) value;
        }else{
            NoticeSendLabelExample example= new NoticeSendLabelExample();
            example.createCriteria().andOrgCodeEqualTo(orgCode);
            List<NoticeSendLabel> list = noticeSendLabelMapper.selectByExample(example);
            if(list!=null && list.size()>0){
                String result = list.get(0).getLabel();
                redisUtil.set("callCenter_notice_label_orgCode_"+orgCode,result);
                return result;
            }
        }
        return null;
    }

    @Override
    public void updateNoticeIntent(String labels, String orgCode) {

        NoticeSendLabelExample example = new NoticeSendLabelExample();
        example.createCriteria().andOrgCodeEqualTo(orgCode);

        List<NoticeSendLabel> list = noticeSendLabelMapper.selectByExample(example);
        if(list!=null && list.size()>0){  //有记录，修改
            NoticeSendLabel record = new NoticeSendLabel();
            record.setLabel(labels);
            noticeSendLabelMapper.updateByExampleSelective(record,example);
        }else{ //没有记录，插入
            NoticeSendLabel noticeSendLabel = new NoticeSendLabel();
            noticeSendLabel.setLabel(labels);
            noticeSendLabel.setOrgCode(orgCode);
            noticeSendLabelMapper.insert(noticeSendLabel);
        }
        redisUtil.set("callCenter_notice_label_orgCode_"+orgCode,labels);
    }
}
