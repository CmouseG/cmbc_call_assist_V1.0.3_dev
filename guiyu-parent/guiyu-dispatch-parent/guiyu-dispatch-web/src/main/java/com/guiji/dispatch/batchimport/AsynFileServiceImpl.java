package com.guiji.dispatch.batchimport;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.guiji.auth.api.IAuth;
import com.guiji.common.model.SysFileReqVO;
import com.guiji.common.model.SysFileRspVO;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.dispatch.dao.DispatchPlanBatchMapper;
import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.dispatch.dao.entity.DispatchPlanBatch;
import com.guiji.dispatch.dao.entity.FileRecords;
import com.guiji.dispatch.util.Constant;
import com.guiji.user.dao.entity.SysUser;
import com.guiji.utils.DateUtil;
import com.guiji.utils.NasUtil;

@Service
public class AsynFileServiceImpl implements AsynFileService {

	@Autowired
	private DispatchPlanBatchMapper dispatchPlanBatchMapper;

	@Autowired
	private IAuth authService;

	@Autowired
	private IBatchImportFileRecordService batchImportFileRecordService;

	@Autowired
	private IBatchImportService batchImportService;

	@Override
	public void batchPlanImport(String fileName, Long userId, MultipartFile file, String str, String orgCode)
			throws Exception {
		List<DispatchPlan> succ = new ArrayList<>();

		if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
			throw new Exception("上传文件格式不正确");
		}
		boolean isExcel2003 = true;
		if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
			isExcel2003 = false;
		}
		InputStream is = file.getInputStream();
		Workbook wb = null;
		if (isExcel2003) {
			wb = new HSSFWorkbook(is);
		} else {
			wb = new XSSFWorkbook(is);
		}
		Sheet sheet = wb.getSheetAt(0);
		if (sheet == null) {
			throw new Exception("模板文件不正确。");
		}
		//
		DispatchPlanBatch dispatchPlanBatch = JSONObject.parseObject(str, DispatchPlanBatch.class);
		dispatchPlanBatch.setGmtModified(DateUtil.getCurrent4Time());
		dispatchPlanBatch.setGmtCreate(DateUtil.getCurrent4Time());
		dispatchPlanBatch.setStatusNotify(Constant.STATUS_NOTIFY_0);
		dispatchPlanBatch.setUserId(userId.intValue());

		// 查询用户名称
		ReturnData<SysUser> sysUser = authService.getUserById(userId);
		if (sysUser == null || sysUser.getBody() == null) {
			throw new Exception("用户不存在");
		}

		DispatchPlan dispatchPlan = JSONObject.parseObject(str, DispatchPlan.class);
		dispatchPlan.setUsername(sysUser.getBody().getUsername());
		dispatchPlan.setUserId(userId.intValue());

		dispatchPlanBatch.setName(dispatchPlan.getBatchName());
		dispatchPlanBatch.setUserId(userId.intValue());
		dispatchPlanBatch.setOrgCode(orgCode);
		dispatchPlanBatch.setStatusShow(Constant.BATCH_STATUS_SHOW);
		dispatchPlanBatchMapper.insert(dispatchPlanBatch);

		Long fileRecordId = saveFileRecord(fileName, dispatchPlanBatch, dispatchPlan, userId, orgCode, file);
		dispatchPlan.setFileRecordId(fileRecordId.intValue());

		// 导入
		batchImportService.batchImport(sheet, dispatchPlanBatch.getId(), dispatchPlan, userId, orgCode);
	}

	private Long saveFileRecord(String fileName, DispatchPlanBatch dispatchPlanBatch, DispatchPlan dispatchPlan,
			Long userId, String orgCode, MultipartFile file) throws Exception {
		FileRecords fileRecords = new FileRecords();
		fileRecords.setBatchid(dispatchPlanBatch.getId());
		fileRecords.setBatchName(dispatchPlan.getBatchName());
		fileRecords.setFileName(fileName);
		fileRecords.setCreateTime(DateUtil.getCurrent4Time());
		fileRecords.setCallData("" + dispatchPlan.getCallData());
		fileRecords.setCallHour(dispatchPlan.getCallHour());
		// fileRecords.setFilePath(sysFileRsp.getSkUrl());
		fileRecords.setIsClean(dispatchPlan.getClean());
		fileRecords.setLineId("" + dispatchPlan.getLine());
		fileRecords.setOrgCode(orgCode);
		fileRecords.setRobot(dispatchPlan.getRobot());
		fileRecords.setUserId(userId.intValue());
		batchImportFileRecordService.save(fileRecords);

		Long fileRecordId = fileRecords.getId();
		SysFileReqVO sysFileReqVO = new SysFileReqVO();
		sysFileReqVO.setBusiId(fileRecords.getId().toString());
		sysFileReqVO.setBusiType("dispatch"); // 上传的影像文件业务类型
		sysFileReqVO.setSysCode("02"); // 文件上传系统码
		sysFileReqVO.setThumbImageFlag("0"); // 是否需要生成缩略图,0-无需生成，1-生成，默认不生成缩略图
		SysFileRspVO sysFileRsp = new NasUtil().uploadNas(sysFileReqVO, getFile(file));
		fileRecords = new FileRecords();
		fileRecords.setId(fileRecordId);
		fileRecords.setUrl(sysFileRsp.getSkUrl());
		batchImportFileRecordService.update(fileRecords);
		return fileRecordId;

	}

	private File getFile(MultipartFile file) {
		File f = null;
		try {

			InputStream ins = file.getInputStream();
			f = new File(file.getOriginalFilename());
			inputStreamToFile(ins, f);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return f;
	}

	private void inputStreamToFile(InputStream ins, File file) {
		try {
			OutputStream os = new FileOutputStream(file);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			os.close();
			ins.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
