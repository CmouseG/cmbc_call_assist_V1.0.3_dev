//package com.guiji.ccmanager.service.impl;
//
//import com.guiji.callcenter.dao.LineInfoMapper;
//import com.guiji.callcenter.dao.entity.LineInfo;
//import com.guiji.ccmanager.constant.CcmanagerExceptionEnum;
//import com.guiji.ccmanager.constant.Constant;
//import com.guiji.ccmanager.entity.OutLineInfoAddReq;
//import com.guiji.ccmanager.entity.OutLineInfoUpdateReq;
//import com.guiji.ccmanager.service.LineOperationService;
//import com.guiji.common.exception.GuiyuException;
//import com.guiji.component.result.Result;
//import com.guiji.fsmanager.entity.LineInfoVO;
//import com.guiji.toagentserver.api.IAgentGroup;
//import com.guiji.utils.BeanUtil;
//import com.guiji.utils.DateUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.transaction.interceptor.TransactionAspectSupport;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Slf4j
//@Service
//public class LineOperationServiceImpl implements LineOperationService {
//
//    @Autowired
//    LineInfoMapper lineInfoMapper;
//    @Autowired
//    IAgentGroup iAgentGroup;
//
//
//    @Override
//    @Transactional
//    public void updateLineInfo(OutLineInfoUpdateReq outLineInfoUpdateReq) {
//
//        LineInfo lineInfoDB = lineInfoMapper.selectByPrimaryKey(outLineInfoUpdateReq.getLineId());
//
//        if(lineInfoDB==null){
//            throw new GuiyuException(CcmanagerExceptionEnum.EXCP_CCMANAGER_LINE_NOTEXIST);
//        }
//
//        //本地更新数据库lineinfo
//        LineInfo lineInfo = new LineInfo();
//        BeanUtil.copyProperties(outLineInfoUpdateReq,lineInfo);
//        lineInfo.setUpdateDate(DateUtil.getCurrentTime());
////        lineInfo.setUpdateBy(userId.intValue());
//        lineInfoMapper.updateByPrimaryKeySelective(lineInfo);
//
//        //调用fsmanager的更新线路接口
//        com.guiji.fsmanager.entity.LineInfoVO lineInfoApi = new com.guiji.fsmanager.entity.LineInfoVO();
//        BeanUtil.copyProperties(outLineInfoUpdateReq,lineInfoApi);
//        lineInfoApi.setLineId(String.valueOf(outLineInfoUpdateReq.getLineId()));
//        Result.ReturnData result = lineOperApiFeign.editLineinfos(lineInfoApi.getLineId(),lineInfoApi);
//        if(!result.getCode().equals(Constant.SUCCESS_COMMON)){// body应该也要判断一下
//            log.warn("lineOperApiFeign.editLineinfos failed,code:"+result.getCode());
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//            throw new GuiyuException(CcmanagerExceptionEnum.EXCP_CCMANAGER_FSMANAGER_EDITLINE);
//        }
//    }
//
//
//    @Override
//    @Transactional
//    public void deleteLineInfo(Integer id) {
//        //本地删除数据库lineinfo记录，同时调用fsmanager的删除线路接口
//        lineInfoMapper.deleteByPrimaryKey(id);
//        Result.ReturnData result = lineOperApiFeign.deleteLineinfos(String.valueOf(id));
//        if(!result.getCode().equals(Constant.SUCCESS_COMMON)){// body应该也要判断一下
//            log.warn("lineOperApiFeign.deleteLineinfos,code:"+result.getCode());
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//            throw new GuiyuException(CcmanagerExceptionEnum.EXCP_CCMANAGER_FSMANAGER_DELETELINE);
//        }
//        Result.ReturnData resultUntying = iAgentGroup.untyingLineinfos(String.valueOf(id));
//        if(!resultUntying.getCode().equals(Constant.SUCCESS_COMMON)){// body应该也要判断一下
//            log.warn("lineOperApiFeign.untyingLineinfos,code:"+resultUntying.getCode());
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//            throw new GuiyuException(CcmanagerExceptionEnum.EXCP_CCMANAGER_FSMANAGER_DELETELINE);
//        }
//    }
//}
