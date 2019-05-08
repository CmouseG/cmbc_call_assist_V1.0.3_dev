//package com.guiji.ccmanager.service.impl;
//
//import com.guiji.auth.api.IAuth;
//import com.guiji.callcenter.dao.LineInfoMapper;
//import com.guiji.callcenter.dao.entity.LineInfo;
//import com.guiji.callcenter.dao.entity.LineInfoExample;
//import com.guiji.ccmanager.constant.CcmanagerExceptionEnum;
//import com.guiji.ccmanager.constant.Constant;
//import com.guiji.ccmanager.manager.CacheManager;
//import com.guiji.ccmanager.service.AuthService;
//import com.guiji.ccmanager.service.LineInfoService;
//import com.guiji.ccmanager.vo.LineInfo4AllotRes;
//import com.guiji.ccmanager.vo.LineInfo4Page;
//import com.guiji.ccmanager.vo.LineInfoAddVO;
//import com.guiji.ccmanager.vo.LineInfoUpdateVO;
//import com.guiji.common.exception.GuiyuException;
//import com.guiji.component.result.Result;
//import com.guiji.fsmanager.api.ILineOper;
//import com.guiji.user.dao.entity.SysOrganization;
//import com.guiji.utils.BeanUtil;
//import com.guiji.utils.DateUtil;
//import com.guiji.utils.RedisUtil;
//import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.client.discovery.DiscoveryClient;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.transaction.interceptor.TransactionAspectSupport;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @Auther: 黎阳
// * @Date: 2018/10/25 0025 17:49
// * @Description:
// */
//@Service
//public class LineInfoServiceImpl implements LineInfoService {
//
//    private final Logger log = LoggerFactory.getLogger(LineInfoServiceImpl.class);
//
//    @Autowired
//    private LineInfoMapper lineInfoMapper;
//    @Autowired
//    private ILineOper lineOperApiFeign;
//    @Autowired
//    private DiscoveryClient discoveryClient;
//    @Autowired
//    CacheManager cacheManager;
//    @Autowired
//    private IAuth iAuth;
//    @Autowired
//    private RedisUtil redisUtil;
//    @Autowired
//    AuthService authService;
//
//    public LineInfoExample getExample(Integer authLevel, String customerId, String orgCode, String lineName) {
//        LineInfoExample example = new LineInfoExample();
//        LineInfoExample.Criteria criteria = example.createCriteria();
//        if(authLevel==1){
//            criteria.andCustomerIdEqualTo(Integer.valueOf(customerId));
//        }else if(authLevel==2){
//            criteria.andOrgCodeEqualTo(orgCode);
//        }else if(authLevel==3){
//            criteria.andOrgCodeLike(orgCode+"%");
//        }
//        if (StringUtils.isNotBlank(lineName)) {
//            criteria.andLineNameEqualTo(lineName);
//        }
//        return example;
//    }
//
//
//
//
//    public List<LineInfo4Page> getLineInfoByCustom(Integer authLevel,String customerId,String orgCode, String lineName, int pageSize, int pageNo) {
//
//        LineInfoExample example = getExample(authLevel,customerId, orgCode, lineName);
//
//        int limitStart = (pageNo-1)*pageSize;
//        example.setLimitStart(limitStart);
//        example.setLimitEnd(pageSize);
//
//        List<LineInfo> list = lineInfoMapper.selectByExample(example);
//        List<LineInfo4Page> reslist = new ArrayList<LineInfo4Page>();
//        if(list!=null && list.size()>0){
//            for(LineInfo lineInfo:list){
//                LineInfo4Page lineInfo4Page=new LineInfo4Page();
//                BeanUtil.copyProperties(lineInfo,lineInfo4Page);
//                Integer userId = lineInfo.getCreatetBy();
//                if(null !=userId){
//                    lineInfo4Page.setCreatetUser(cacheManager.getUserName(userId));
//                }
//                Integer userUpdate = lineInfo.getUpdateBy();
//                if(null!=userUpdate){
//                    lineInfo4Page.setUpdateUser(cacheManager.getUserName(userId));
//                }
//                Integer cId = lineInfo.getCustomerId();
//                if(null !=cId){
//                    lineInfo4Page.setCustomerUser( cacheManager.getUserName(cId));
//                }
//                reslist.add(lineInfo4Page);
//            }
//        }
//        return reslist;
//    }
//
//
//    @Override
//    public int getLineInfoByCustomCount(Integer authLevel,String customerId,String orgCode, String lineName) {
//        LineInfoExample example = getExample(authLevel,customerId, orgCode, lineName);
//        return lineInfoMapper.countByExample(example);
//    }
//
//    @Override
//    @Transactional
//    public void addLineInfo(LineInfoAddVO lineInfoVO) {
//
//        //本地存储数据库lineinfo
//        LineInfo lineInfo = new LineInfo();
//        BeanUtil.copyProperties(lineInfoVO,lineInfo);
//        lineInfo.setCreateDate(DateUtil.getCurrentTime());
//        lineInfo.setCreatetBy(Integer.valueOf(lineInfoVO.getCustomerId()));
//        lineInfo.setUpdateDate(DateUtil.getCurrentTime());
//        lineInfo.setUpdateBy(Integer.valueOf(lineInfoVO.getCustomerId()));
//        lineInfo.setOrgCode(lineInfoVO.getOrgCode());
//        lineInfo.setCustomerId(0);
//        lineInfoMapper.insertSelective(lineInfo);
//
//        //调用fsmanager的增加线路接口
//        com.guiji.fsmanager.entity.LineInfoVO lineInfoApi = new com.guiji.fsmanager.entity.LineInfoVO();
//        BeanUtil.copyProperties(lineInfo,lineInfoApi);
//        lineInfoApi.setLineId(String.valueOf(lineInfo.getLineId()));
//        Result.ReturnData result = lineOperApiFeign.addLineinfos(lineInfoApi);
//        if(!result.getCode().equals(Constant.SUCCESS_COMMON)){// body应该也要判断一下
//            log.warn("lineOperApiFeign.addLineinfos failed,code:"+result.getCode());
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚事务
//            throw new GuiyuException(CcmanagerExceptionEnum.EXCP_CCMANAGER_FSMANAGER_ADDLINE);
//        }
//        //读取所有的calloutserver，将线路并发数平分到各个calloutserver，存储到linecount表中
///*        List<String> listServer = ServerUtil.getInstances(discoveryClient,Constant.SERVER_NAME_CALLOUTSERVER);
//        int maxAll = lineInfoVO.getMaxConcurrentCalls();
//        int count = listServer.size();
//        int maxcon = maxAll/count;
//        int remain = maxAll%count;
//        for(int i=0; i<count ; i++){
//            LineCount lineCount = new LineCount();
//            lineCount.setCalloutserverId(listServer.get(i));
//            lineCount.setLineId(lineInfo.getLineId());
//            if(i<remain){
//                lineCount.setMaxConcurrentCalls(maxcon+1);
//            }else{
//                lineCount.setMaxConcurrentCalls(maxcon);
//            }
//            lineCount.setUsedConcurrentCalls(0);
//            lineCountMapper.insert(lineCount);
//        }*/
//        //刷新redis缓存
//        if(lineInfoVO!=null && lineInfoVO.getCustomerId()!=null){
//            List<LineInfo> lineInfos = getLineInfoByCustomerId(lineInfoVO.getCustomerId());
//            if(lineInfos!=null && lineInfos.size()>0){
//                redisUtil.set("callCenter-lineInfo-"+lineInfoVO.getCustomerId(),lineInfos);
//            }
//        }
//
//    }
//
//    @Override
//    @Transactional
//    public void updateLineInfo(LineInfoUpdateVO lineInfoVO, Long userId){
//
//        LineInfo lineInfoDB = lineInfoMapper.selectByPrimaryKey(lineInfoVO.getLineId());
//
//        if(lineInfoDB==null){
//            throw new GuiyuException(CcmanagerExceptionEnum.EXCP_CCMANAGER_LINE_NOTEXIST);
//        }
//
//        //本地更新数据库lineinfo
//        LineInfo lineInfo = new LineInfo();
//        BeanUtil.copyProperties(lineInfoVO,lineInfo);
//        lineInfo.setUpdateDate(DateUtil.getCurrentTime());
//        lineInfo.setUpdateBy(userId.intValue());
//        lineInfoMapper.updateByPrimaryKeySelective(lineInfo);
//
//        //调用fsmanager的更新线路接口
//        com.guiji.fsmanager.entity.LineInfoVO lineInfoApi = new com.guiji.fsmanager.entity.LineInfoVO();
//        BeanUtil.copyProperties(lineInfoVO,lineInfoApi);
//        lineInfoApi.setLineId(String.valueOf(lineInfoVO.getLineId()));
//        Result.ReturnData result = lineOperApiFeign.editLineinfos(lineInfoApi.getLineId(),lineInfoApi);
//        if(!result.getCode().equals(Constant.SUCCESS_COMMON)){// body应该也要判断一下
//            log.warn("lineOperApiFeign.editLineinfos failed,code:"+result.getCode());
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//            throw new GuiyuException(CcmanagerExceptionEnum.EXCP_CCMANAGER_FSMANAGER_EDITLINE);
//        }
//
//        //若并发数有更新，则从linecount表读取线路并发数分配信息，并根据重新计算的并发数进行修改保存。
//        /*if(lineInfoDB.getMaxConcurrentCalls()!=lineInfoVO.getMaxConcurrentCalls()){
//            List<String> listServer = ServerUtil.getInstances(discoveryClient,Constant.SERVER_NAME_CALLOUTSERVER);
//            int maxAll = lineInfoVO.getMaxConcurrentCalls();
//            int count = listServer.size();
//            int maxcon = maxAll/count;
//            int remain = maxAll%count;
//            for(int i=0; i<count ; i++){
//                LineCount lineCount = new LineCount();
//                lineCount.setCalloutserverId(listServer.get(i));
//                lineCount.setLineId(lineInfo.getLineId());
//                if(i<remain){
//                    lineCount.setMaxConcurrentCalls(maxcon+1);
//                }else{
//                    lineCount.setMaxConcurrentCalls(maxcon);
//                }
//                LineCountExample example = new LineCountExample();
//                LineCountExample.Criteria criteria = example.createCriteria();
//                criteria.andLineIdEqualTo(lineCount.getLineId());
//                criteria.andCalloutserverIdEqualTo(lineCount.getCalloutserverId());
//                lineCountMapper.updateByExampleSelective(lineCount,example);
//            }
//        }*/
//        //刷新redis缓存
//        if(lineInfoVO!=null && lineInfoVO.getCustomerId()!=null){
//            List<LineInfo> lineInfos = getLineInfoByCustomerId(lineInfoVO.getCustomerId());
//            if(lineInfos!=null && lineInfos.size()>0){
//                redisUtil.set("callCenter-lineInfo-"+lineInfoVO.getCustomerId(),lineInfos);
//            }
//        }
//    }
//
//    @Override
//    @Transactional
//    public void delLineInfo(String id) {
//        //查询customerid，供缓存使用
//        LineInfo lineInfo = lineInfoMapper.selectByPrimaryKey(Integer.valueOf(id));
//        //本地删除数据库lineinfo记录，同时调用fsmanager的删除线路接口
//        lineInfoMapper.deleteByPrimaryKey(Integer.valueOf(id));
//        Result.ReturnData result = lineOperApiFeign.deleteLineinfos(id);
//        if(!result.getCode().equals(Constant.SUCCESS_COMMON)){// body应该也要判断一下
//            log.warn("lineOperApiFeign.deleteLineinfos,code:"+result.getCode());
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//            throw new GuiyuException(CcmanagerExceptionEnum.EXCP_CCMANAGER_FSMANAGER_DELETELINE);
//        }
//        //删除linecount中对应的线路并发数信息
///*        LineCountExample example = new LineCountExample();
//        LineCountExample.Criteria criteria = example.createCriteria();
//        criteria.andLineIdEqualTo(Integer.valueOf(id));
//        lineCountMapper.deleteByExample(example);*/
//        //刷新redis缓存
//        if(lineInfo!=null && lineInfo.getCustomerId()!=null){
//            List<LineInfo> lineInfos = getLineInfoByCustomerId(String.valueOf(lineInfo.getCustomerId()));
//            if(lineInfos!=null && lineInfos.size()>0){
//                redisUtil.set("callCenter-lineInfo-"+lineInfo.getCustomerId(),lineInfos);
//            }
//        }
//    }
//
//
//    @Override
//    public List<LineInfo> outLineinfos(String customerId) {
//
//        if(redisUtil.get("callCenter-lineInfo-"+customerId)!=null){
//            List<LineInfo> lineInfoCache = (List<LineInfo>) redisUtil.get("callCenter-lineInfo-"+customerId);
//            return lineInfoCache;
//        }else{
//            List<LineInfo> lineInfos = getLineInfoByCustomerId(customerId);
//            if(lineInfos!=null && lineInfos.size()>0){
//                redisUtil.set("callCenter-lineInfo-"+customerId,lineInfos);
//            }
//
//            return lineInfos;
//        }
//    }
//
//    private List<LineInfo> getLineInfoByCustomerId(String customerId){
//        LineInfoExample example = new LineInfoExample();
//        LineInfoExample.Criteria criteria = example.createCriteria();
//        criteria.andCustomerIdEqualTo(Integer.valueOf(customerId));
//        return lineInfoMapper.selectByExample(example);
//    }
//
//    @Override
//    public List<LineInfo4AllotRes> getLineInfos4Allot(String customerId) {
//
//        Result.ReturnData<SysOrganization> data=iAuth.getOrgByUserId(Long.valueOf(customerId));
//        String orgCode=data.getBody().getCode();
//
//        LineInfoExample example = new LineInfoExample();
//        LineInfoExample.Criteria criteria = example.createCriteria();
//
//        criteria.andOrgCodeLike(orgCode+"%");
//        List customerList = new ArrayList();
//        customerList.add(customerId);
//        customerList.add("0");
//        criteria.andCustomerIdIn(customerList);
//
//        List<LineInfo> lineInfos = lineInfoMapper.selectByExample(example);
//        List<LineInfo4AllotRes> resList = new ArrayList<>();
//        if(lineInfos!=null && lineInfos.size()>0){
//            for(LineInfo lineInfo:lineInfos){
//                LineInfo4AllotRes lineInfo4AllotRes = new LineInfo4AllotRes();
//                lineInfo4AllotRes.setLineId(lineInfo.getLineId());
//                lineInfo4AllotRes.setLineName(lineInfo.getLineName());
//                if(lineInfo.getCustomerId()!=null && lineInfo.getCustomerId().toString().equals(customerId)){
//                    lineInfo4AllotRes.setAlloted(true);
//                }else{
//                    lineInfo4AllotRes.setAlloted(false);
//                }
//                resList.add(lineInfo4AllotRes);
//            }
//
//        }
//        return resList;
//    }
//
//    @Override
//    @Transactional
//    public void allotLineInfo(String customerId, String lineIds) {
//
//        //先把该客户的都置为0
//        LineInfoExample example = new LineInfoExample();
//        example.createCriteria().andCustomerIdEqualTo(Integer.valueOf(customerId));
//        LineInfo record = new LineInfo();
//        record.setCustomerId(0);
//        lineInfoMapper.updateByExampleSelective(record,example);
//
//        //重新分配
//        if(StringUtils.isNotBlank(lineIds)){
//            String[] arr = lineIds.split(",");
//            for(String lineID:arr){
//                LineInfo updateLine = new LineInfo();
//                updateLine.setCustomerId(Integer.valueOf(customerId));
//                updateLine.setLineId(Integer.valueOf(lineID));
//                lineInfoMapper.updateByPrimaryKeySelective(updateLine);
//            }
//        }
//
//        //刷新redis缓存
//        if(customerId!=null){
//            List<LineInfo> lineInfos = getLineInfoByCustomerId(customerId);
//            if(lineInfos!=null && lineInfos.size()>0){
//                redisUtil.set("callCenter-lineInfo-"+customerId,lineInfos);
//            }
//        }
//
//    }
//
//    @Override
//    public List<LineInfo> getLineInfoByOrgCode(String orgCode) {
//        LineInfoExample example = new LineInfoExample();
//        example.createCriteria().andOrgCodeLike(orgCode+"%");
//        return lineInfoMapper.selectByExample(example);
//    }
//
//    @Override
//    public LineInfo getLineInfoById(Integer lineId) {
//        return  lineInfoMapper.selectByPrimaryKey(lineId);
//    }
//}
