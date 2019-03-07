package com.guiji.dispatch.batchimport;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.fastjson.util.IOUtils;
import com.guiji.dispatch.batchimport.listener.BatchImportExcelListener;
import com.guiji.dispatch.dao.FileRecordsMapper;
import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.dispatch.impl.DispatchPlanServiceImpl;
import com.guiji.dispatch.service.IBlackListService;
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
	private IBatchImportQueueHandlerService batchImportQueueHandler;
	@Autowired
	private FileRecordsMapper fileRecordsMapper;
	@Autowired
	private IBlackListService blackService;
	@Autowired
	private IPhoneRegionService phoneRegionService;

	@Async("asyncBatchImportExecutor")
	@Override
	public void batchImport(InputStream inputStream, int batchId, DispatchPlan dispatchPlanParam, Long userId, String orgCode)
	{
		try
		{
			BatchImportExcelListener excelListener = new BatchImportExcelListener(dispatchPlanParam, batchId, userId, orgCode);
			excelListener.setBatchImportQueueHandler(batchImportQueueHandler);
			excelListener.setBlackService(blackService);
			excelListener.setFileRecordErrorService(fileRecordErrorService);
			excelListener.setFileRecordsMapper(fileRecordsMapper);
			excelListener.setPhoneRegionService(phoneRegionService);
			EasyExcelFactory.readBySax(inputStream, new Sheet(1, 1), excelListener);
		} catch (Exception e) {
			logger.error("批量导入失败!!!", e);
		} finally {
			IOUtils.close(inputStream);
		}
	}
}
