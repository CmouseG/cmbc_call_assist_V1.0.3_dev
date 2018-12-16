package com.guiji.ccmanager.service.impl;

import com.guiji.auth.api.IAuth;
import com.guiji.callcenter.dao.LineCountMapper;
import com.guiji.callcenter.dao.LineInfoMapper;
import com.guiji.callcenter.dao.entity.LineCount;
import com.guiji.callcenter.dao.entity.LineCountExample;
import com.guiji.callcenter.dao.entity.LineInfo;
import com.guiji.callcenter.dao.entity.LineInfoExample;
import com.guiji.ccmanager.constant.CcmanagerExceptionEnum;
import com.guiji.ccmanager.constant.Constant;
import com.guiji.ccmanager.manager.CacheManager;
import com.guiji.ccmanager.service.LineInfoService;
import com.guiji.ccmanager.vo.LineInfoAddVO;
import com.guiji.ccmanager.vo.LineInfoUpdateVO;
import com.guiji.common.exception.GuiyuException;
import com.guiji.component.result.Result;
import com.guiji.fsmanager.api.ILineOper;
import com.guiji.user.dao.entity.SysUser;
import com.guiji.utils.BeanUtil;
import com.guiji.utils.DateUtil;
import com.guiji.utils.ServerUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: 黎阳
 * @Date: 2018/10/25 0025 17:49
 * @Description:
 */
@Service
public class LineInfoServiceImpl implements LineInfoService {

    private final Logger log = LoggerFactory.getLogger(LineInfoServiceImpl.class);

    @Autowired
    private LineInfoMapper lineInfoMapper;
    @Autowired
    private ILineOper lineOperApiFeign;
    @Autowired
    private LineCountMapper lineCountMapper;
    @Autowired
    private DiscoveryClient discoveryClient;
    @Autowired
    CacheManager cacheManager;

    public LineInfoExample getExample(String customerId, String lineName){
        LineInfoExample example = new LineInfoExample();
        LineInfoExample.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(customerId)){
            criteria.andCustomerIdEqualTo(customerId);
        }
        if(StringUtils.isNotBlank(lineName)){
            criteria.andLineNameEqualTo(lineName);
        }
        return example;
    }

    public List<LineInfo> getLineInfoByCustom(String customerId, String lineName, int pageSize, int pageNo) {

        LineInfoExample example = getExample(customerId,  lineName);

        int limitStart = (pageNo-1)*pageSize;
        example.setLimitStart(limitStart);
        example.setLimitEnd(pageSize);

        List<LineInfo> list = lineInfoMapper.selectByExample(example);

        if(list!=null && list.size()>0){
            for(LineInfo lineInfo:list){
                String userId = lineInfo.getCreatetBy();
                if(StringUtils.isNotBlank(userId)){
                    lineInfo.setCreatetBy(cacheManager.getUserName(userId));
                }
                String userUpdate = lineInfo.getUpdateBy();
                if(StringUtils.isNotBlank(userUpdate)){
                    lineInfo.setUpdateBy(cacheManager.getUserName(userId));
                }
                String cId = lineInfo.getCustomerId();
                if(StringUtils.isNotBlank(cId)){
                    lineInfo.setCustomerId( cacheManager.getUserName(cId));
                }
            }
        }
        return list;
    }


    @Override
    public int getLineInfoByCustomCount(String customerId, String lineName) {
        LineInfoExample example = getExample( customerId,  lineName);
        return lineInfoMapper.countByExample(example);
    }

    @Override
    @Transactional
    public void addLineInfo(LineInfoAddVO lineInfoVO) {

        //本地存储数据库lineinfo
        LineInfo lineInfo = new LineInfo();
        BeanUtil.copyProperties(lineInfoVO,lineInfo);
        lineInfo.setCreateDate(DateUtil.getCurrentTime());
        lineInfo.setCreatetBy(lineInfoVO.getCustomerId());
        lineInfo.setUpdateDate(DateUtil.getCurrentTime());
        lineInfo.setUpdateBy(lineInfoVO.getCustomerId());
        lineInfoMapper.insertSelective(lineInfo);

        //调用fsmanager的增加线路接口
        com.guiji.fsmanager.entity.LineInfoVO lineInfoApi = new com.guiji.fsmanager.entity.LineInfoVO();
        BeanUtil.copyProperties(lineInfo,lineInfoApi);
        lineInfoApi.setLineId(String.valueOf(lineInfo.getLineId()));
        Result.ReturnData result = lineOperApiFeign.addLineinfos(lineInfoApi);
        if(!result.getCode().equals(Constant.SUCCESS_COMMON)){// body应该也要判断一下
            log.warn("lineOperApiFeign.addLineinfos failed,code:"+result.getCode());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚事务
            throw new GuiyuException(CcmanagerExceptionEnum.EXCP_CCMANAGER_FSMANAGER_ADDLINE);
        }
        //读取所有的calloutserver，将线路并发数平分到各个calloutserver，存储到linecount表中
        List<String> listServer = ServerUtil.getInstances(discoveryClient,Constant.SERVER_NAME_CALLOUTSERVER);
        int maxAll = lineInfoVO.getMaxConcurrentCalls();
        int count = listServer.size();
        int maxcon = maxAll/count;
        int remain = maxAll%count;
        for(int i=0; i<count ; i++){
            LineCount lineCount = new LineCount();
            lineCount.setCalloutserverId(listServer.get(i));
            lineCount.setLineId(lineInfo.getLineId());
            if(i<remain){
                lineCount.setMaxConcurrentCalls(maxcon+1);
            }else{
                lineCount.setMaxConcurrentCalls(maxcon);
            }
            lineCount.setUsedConcurrentCalls(0);
            lineCountMapper.insert(lineCount);
        }

    }

    @Override
    @Transactional
    public void updateLineInfo(LineInfoUpdateVO lineInfoVO, Long userId){

        LineInfo lineInfoDB = lineInfoMapper.selectByPrimaryKey(lineInfoVO.getLineId());

        if(lineInfoDB==null){
            throw new GuiyuException(CcmanagerExceptionEnum.EXCP_CCMANAGER_LINE_NOTEXIST);
        }

        //本地更新数据库lineinfo
        LineInfo lineInfo = new LineInfo();
        BeanUtil.copyProperties(lineInfoVO,lineInfo);
        lineInfo.setUpdateDate(DateUtil.getCurrentTime());
        lineInfo.setUpdateBy(String.valueOf(userId));
        lineInfoMapper.updateByPrimaryKeySelective(lineInfo);

        //调用fsmanager的更新线路接口
        com.guiji.fsmanager.entity.LineInfoVO lineInfoApi = new com.guiji.fsmanager.entity.LineInfoVO();
        BeanUtil.copyProperties(lineInfoVO,lineInfoApi);
        lineInfoApi.setLineId(String.valueOf(lineInfoVO.getLineId()));
        Result.ReturnData result = lineOperApiFeign.editLineinfos(lineInfoApi.getLineId(),lineInfoApi);
        if(!result.getCode().equals(Constant.SUCCESS_COMMON)){// body应该也要判断一下
            log.warn("lineOperApiFeign.editLineinfos failed,code:"+result.getCode());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new GuiyuException(CcmanagerExceptionEnum.EXCP_CCMANAGER_FSMANAGER_EDITLINE);
        }

        //若并发数有更新，则从linecount表读取线路并发数分配信息，并根据重新计算的并发数进行修改保存。
        if(lineInfoDB.getMaxConcurrentCalls()!=lineInfoVO.getMaxConcurrentCalls()){
            List<String> listServer = ServerUtil.getInstances(discoveryClient,Constant.SERVER_NAME_CALLOUTSERVER);
            int maxAll = lineInfoVO.getMaxConcurrentCalls();
            int count = listServer.size();
            int maxcon = maxAll/count;
            int remain = maxAll%count;
            for(int i=0; i<count ; i++){
                LineCount lineCount = new LineCount();
                lineCount.setCalloutserverId(listServer.get(i));
                lineCount.setLineId(lineInfo.getLineId());
                if(i<remain){
                    lineCount.setMaxConcurrentCalls(maxcon+1);
                }else{
                    lineCount.setMaxConcurrentCalls(maxcon);
                }
                LineCountExample example = new LineCountExample();
                LineCountExample.Criteria criteria = example.createCriteria();
                criteria.andLineIdEqualTo(lineCount.getLineId());
                criteria.andCalloutserverIdEqualTo(lineCount.getCalloutserverId());
                lineCountMapper.updateByExampleSelective(lineCount,example);
            }
        }
    }

    @Override
    @Transactional
    public void delLineInfo(String id) {
        //本地删除数据库lineinfo记录，同时调用fsmanager的删除线路接口
        lineInfoMapper.deleteByPrimaryKey(Integer.valueOf(id));
        Result.ReturnData result = lineOperApiFeign.deleteLineinfos(id);
        if(!result.getCode().equals(Constant.SUCCESS_COMMON)){// body应该也要判断一下
            log.warn("lineOperApiFeign.deleteLineinfos,code:"+result.getCode());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new GuiyuException(CcmanagerExceptionEnum.EXCP_CCMANAGER_FSMANAGER_DELETELINE);
        }
        //删除linecount中对应的线路并发数信息
        LineCountExample example = new LineCountExample();
        LineCountExample.Criteria criteria = example.createCriteria();
        criteria.andLineIdEqualTo(Integer.valueOf(id));
        lineCountMapper.deleteByExample(example);
    }


    @Override
    public List<LineInfo> outLineinfos(String customerId) {

       // 从lineinfos表中读取相应的信息返回即可
        LineInfoExample example = new LineInfoExample();
        LineInfoExample.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(customerId)){
            criteria.andCustomerIdEqualTo(customerId);
        }
        List<LineInfo> lineInfos = lineInfoMapper.selectByExample(example);

        return lineInfos;
    }
}
