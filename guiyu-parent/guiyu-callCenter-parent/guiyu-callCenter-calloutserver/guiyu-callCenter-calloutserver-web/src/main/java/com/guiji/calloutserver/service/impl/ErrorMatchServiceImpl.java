package com.guiji.calloutserver.service.impl;

import com.google.common.base.Strings;
import com.guiji.callcenter.dao.ErrorMatchMapper;
import com.guiji.callcenter.dao.entity.ErrorMatch;
import com.guiji.callcenter.dao.entity.ErrorMatchExample;
import com.guiji.calloutserver.service.ErrorMatchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @Auther: 魏驰
 * @Date: 2018/11/23 16:21
 * @Project：guiyu-parent
 * @Description: 用于F类识别
 */
@Slf4j
@Service
public class ErrorMatchServiceImpl implements ErrorMatchService {
    @Autowired
    ErrorMatchMapper errorMatchMapper;

    private List<ErrorMatch> errorMatchList;

    @PostConstruct
    public void init(){
        ErrorMatchExample errorMatchExample =  new ErrorMatchExample();
        errorMatchList = errorMatchMapper.selectByExample(errorMatchExample);

        if(errorMatchList==null || errorMatchList.size()==0){
            //TODO: 报警
            log.warn("F类识别服务出错，未读取到记录");
        }
    }

    @Override
    public ErrorMatch findError(String responseMsg) {
        ErrorMatch errorMatch = null;

        if(Strings.isNullOrEmpty(responseMsg)){
            log.warn("F类识别失败，因待识别消息为空");
            errorMatch = new ErrorMatch();
            errorMatch.setErrorType(-1);
            errorMatch.setErrorName("空识别");

            return errorMatch;
        }

        log.debug("开始做F类识别，待识别消息为[{}]", responseMsg);
        if(errorMatchList==null || errorMatchList.size()==0 ){
            log.warn("F类匹配出错，未读取到记录");
            return null;
        }

        for(ErrorMatch match: errorMatchList){
            if(responseMsg.contains(match.getKeyWord())){
                errorMatch = match;
                break;
            }
        }

        if(errorMatch == null){
            //TODO: 报警，需要将未知的F类原因进行报警，以便后期加入数据库
            log.warn("未知的F类原因[{}]", responseMsg);
        }

        log.debug("F类识别结果为[{}]", errorMatch);
        return errorMatch;
    }
}
