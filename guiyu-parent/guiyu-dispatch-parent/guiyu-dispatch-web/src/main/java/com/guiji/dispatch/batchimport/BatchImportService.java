package com.guiji.dispatch.batchimport;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.fastjson.util.IOUtils;
import com.guiji.common.exception.GuiyuException;
import com.guiji.dispatch.batchimport.listener.BatchImportExcelListener;
import com.guiji.dispatch.batchimport.listener.BatchImportExcelModel;
import com.guiji.dispatch.dao.FileRecordsMapper;
import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.dispatch.exception.DispatchCodeExceptionEnum;
import com.guiji.dispatch.impl.DispatchPlanServiceImpl;
import com.guiji.dispatch.service.IBlackListService;
import com.guiji.dispatch.service.IDispatchPlanService;
import com.guiji.dispatch.service.IPhoneRegionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class BatchImportService implements IBatchImportService {

	private static Logger logger = LoggerFactory.getLogger(DispatchPlanServiceImpl.class);

	@Autowired
	private IBatchImportFieRecordErrorService fileRecordErrorService;
	@Autowired
	private FileRecordsMapper fileRecordsMapper;
	@Autowired
	private IBlackListService blackService;
	@Autowired
	private IPhoneRegionService phoneRegionService;

	@Autowired
	private IDispatchPlanService dispatchPlanService;

	@Async("asyncBatchImportExecutor")
	@Override
	public void batchImport(InputStream inputStream, int batchId, DispatchPlan dispatchPlanParam, Long userId, String orgCode, Integer orgId)
	{
		try
		{
			logger.info("批量导入开始，批次ID:{},dispatchPlanParam:{},userId:{},orgCode:{},orgId:{}",batchId,dispatchPlanParam,userId,orgCode,orgId);
			BatchImportExcelListener excelListener = new BatchImportExcelListener(dispatchPlanParam, batchId, userId, orgCode, orgId);
			excelListener.setBlackService(blackService);
			excelListener.setFileRecordErrorService(fileRecordErrorService);
			excelListener.setFileRecordsMapper(fileRecordsMapper);
			excelListener.setPhoneRegionService(phoneRegionService);
			excelListener.setDispatchPlanService(dispatchPlanService);
			EasyExcelFactory.readBySax(inputStream, new Sheet(1, 1, BatchImportExcelModel.class), excelListener);
		} catch (Exception e) {
			logger.error("批量导入失败!!!", e);
			throw new GuiyuException(e);
		} finally {
			IOUtils.close(inputStream);
		}
	}
}
