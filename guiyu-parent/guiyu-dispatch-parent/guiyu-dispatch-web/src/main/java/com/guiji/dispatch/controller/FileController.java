package com.guiji.dispatch.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.guiji.common.model.Page;
import com.guiji.component.result.Result;
import com.guiji.dispatch.bean.PlanUuidDto;
import com.guiji.dispatch.dao.DispatchPlanMapper;
import com.guiji.dispatch.dao.FileErrorRecordsMapper;
import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.dispatch.dao.entity.DispatchPlanExample;
import com.guiji.dispatch.dao.entity.FileErrorRecords;
import com.guiji.dispatch.dao.entity.FileErrorRecordsExample;
import com.guiji.dispatch.dao.entity.FileRecords;
import com.guiji.dispatch.service.FileInterface;
import com.guiji.dispatch.util.HttpDownload;

import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class FileController {

	@Autowired
	private FileInterface file;
	@Autowired
	private FileErrorRecordsMapper errorMapper;
	@Autowired
	private DispatchPlanMapper dispatchMapper;

	@GetMapping(value = "queryFileRecords")
	public Page<FileRecords> queryFileInterface(@RequestParam(required = true, name = "pagenum") int pagenum,
			@RequestParam(required = true, name = "pagesize") int pagesize,
			@RequestParam(required = false, name = "batchName") String batchName,
			@RequestParam(required = false, name = "startTime") String startTime,
			@RequestParam(required = false, name = "endTime") String endTime) {
		Page<FileRecords> queryFileInterface = file.queryFileInterface(pagenum, pagesize, batchName, startTime,
				endTime);
		return queryFileInterface;
	}

	@PostMapping(value = "deleteFileRecordsById")
	public boolean deleteFileRecordsById(@RequestParam(required = true, name = "id") Integer id) {
		return file.deleteFileRecordsById(id);
	}

	@GetMapping(value = "errorCountById")
	public JSONObject errorCountById(@RequestParam(required = true, name = "ids") String id) {
		JSONObject jsonObject = new JSONObject();
		Map<String, Integer> map = new HashMap<>();
		if (id.contains(",")) {
			String[] split = id.split(",");
			for (String ids : split) {
				FileErrorRecordsExample ex = new FileErrorRecordsExample();
				ex.createCriteria().andFileRecordsIdEqualTo(Long.valueOf(ids));
				int count = errorMapper.countByExample(ex);
				map.put(ids, count);
			}
		} else if (id != null && id != "") {
			FileErrorRecordsExample ex = new FileErrorRecordsExample();
			ex.createCriteria().andFileRecordsIdEqualTo(Long.valueOf(id));
			int count = errorMapper.countByExample(ex);
			map.put(id, count);
		}
		jsonObject.put("data", map);
		return jsonObject;
	}

	@PostMapping(value = "downloadChooseNum")
	public Result.ReturnData<Object> downloadChooseNum(@RequestBody PlanUuidDto[] dtos, HttpServletResponse resp)
			throws UnsupportedEncodingException, WriteException {
		List<String> ids = new ArrayList<>();
		for (int i = 0; i < dtos.length; i++) {
			ids.add(dtos[i].getPlanuuid());
		}
		DispatchPlanExample ex = new DispatchPlanExample();
		ex.createCriteria().andPlanUuidIn(ids);

		List<DispatchPlan> selectByExample = dispatchMapper.selectByExample(ex);

		String fileName = "任务导出结果详情.xls";
		HttpDownload.setHeader(resp, fileName);

		OutputStream out = null;
		try {
			out = resp.getOutputStream();
			generateExcel4Dispatch(selectByExample, out);
		} catch (IOException e) {
			log.error("downloadDialogue IOException :" + e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					log.error("out.close error:" + e);
				}
			}
		}
		return null;
	}

	private void generateExcel4Dispatch(List<DispatchPlan> selectByExample, OutputStream out)
			throws RowsExceededException, WriteException, IOException {
		
		Map<String,String> map = new HashMap<>();
		map.put("1", "计划中");
		map.put("2", "已完成");
		map.put("3", "已暂停");
		map.put("4", "已停止");
		WritableWorkbook wb = Workbook.createWorkbook(out);
		WritableSheet sheet = wb.createSheet("sheet1", 0);
		WritableCellFormat format = new WritableCellFormat();
		format.setBorder(Border.ALL, BorderLineStyle.THIN);
		format.setWrap(true);

		sheet.setColumnView(0, 12);
		sheet.setColumnView(1, 12);

		sheet.addCell(new Label(0, 0, "批次"));
		sheet.addCell(new Label(1, 0, "号码"));
		sheet.addCell(new Label(2, 0, "计划状态"));
		sheet.addCell(new Label(3, 0, "话术"));
		sheet.addCell(new Label(4, 0, "线路"));
		sheet.addCell(new Label(5, 0, "计划日期"));
		sheet.addCell(new Label(6, 0, "计划时间"));
		sheet.addCell(new Label(7, 0, "所属用户"));
		for (int i = 0; i < selectByExample.size(); i++) {
			DispatchPlan dispatchPlan = selectByExample.get(i);
			int k = 0;
			sheet.addCell(new Label(k, i + 1, dispatchPlan.getBatchName()));
			k++;
			sheet.addCell(new Label(k, i + 1, dispatchPlan.getPhone()));
			k++;
			sheet.addCell(new Label(k, i + 1, map.get(String.valueOf(dispatchPlan.getStatusPlan()))));
			k++;
			sheet.addCell(new Label(k, i + 1, dispatchPlan.getRobotName()));
			k++;
			sheet.addCell(new Label(k, i + 1, dispatchPlan.getLineName()));
			k++;
			sheet.addCell(new Label(k, i + 1, String.valueOf(dispatchPlan.getCallData())));
			k++;
			sheet.addCell(new Label(k, i + 1, String.valueOf(dispatchPlan.getCallHour())));
			k++;
			sheet.addCell(new Label(k, i + 1, dispatchPlan.getUsername()));
		}

		wb.write();
		wb.close();

	}

	@GetMapping(value = "downloadErrorRecords")
	public Result.ReturnData<Object> downloadErrorRecords(@RequestParam(required = true, name = "fileRecordId") String fileRecordId, HttpServletResponse resp)
			throws UnsupportedEncodingException, WriteException {
		List<FileErrorRecords> queryErrorRecords = file.queryErrorRecords(fileRecordId);
		String fileName = "错误详情结果.xls";
		HttpDownload.setHeader(resp, fileName);

		OutputStream out = null;
		try {
			out = resp.getOutputStream();
			generateExcel(queryErrorRecords, out);
		} catch (IOException e) {
			log.error("downloadDialogue IOException :" + e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					log.error("out.close error:" + e);
				}
			}
		}
		return null;
	}

	private void generateExcel(List<FileErrorRecords> queryErrorRecords, OutputStream out)
			throws WriteException, IOException {
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(1, "机器人校验失败");
		map.put(2, "params参数校验失败");
		map.put(3, "重复号码");
		map.put(-1, "未识别号码");
		WritableWorkbook wb = Workbook.createWorkbook(out);
		WritableSheet sheet = wb.createSheet("sheet1", 0);
		WritableCellFormat format = new WritableCellFormat();
		format.setBorder(Border.ALL, BorderLineStyle.THIN);
		format.setWrap(true);

		sheet.setColumnView(0, 12);
		sheet.setColumnView(1, 12);

		sheet.addCell(new Label(0, 0, "手机号码"));
		sheet.addCell(new Label(1, 0, "attach"));
		sheet.addCell(new Label(2, 0, "params"));
		sheet.addCell(new Label(3, 0, "错误类型"));
		for (int i = 0; i < queryErrorRecords.size(); i++) {
			FileErrorRecords fileErrorRecords = queryErrorRecords.get(i);
			int k = 0;
			sheet.addCell(new Label(k, i + 1, fileErrorRecords.getPhone()));
			k++;
			sheet.addCell(new Label(k, i + 1, fileErrorRecords.getAttach()));
			k++;
			sheet.addCell(new Label(k, i + 1, fileErrorRecords.getParams()));
			k++;
			sheet.addCell(new Label(k, i + 1, map.get(fileErrorRecords.getErrorType())));
		}

		wb.write();
		wb.close();

	}

}
