package com.guiji.dispatch.controller;

import com.alibaba.fastjson.JSONObject;
import com.guiji.common.exception.GuiyuException;
import com.guiji.common.model.Page;
import com.guiji.component.result.Result;
import com.guiji.dispatch.bean.PlanUuidDto;
import com.guiji.dispatch.dao.DispatchPlanMapper;
import com.guiji.dispatch.dao.FileErrorRecordsMapper;
import com.guiji.dispatch.dao.entity.*;
import com.guiji.dispatch.dto.QueryDownloadPlanListDto;
import com.guiji.dispatch.line.IDispatchBatchLineService;
import com.guiji.dispatch.service.FileInterface;
import com.guiji.dispatch.service.IDispatchPlanService;
import com.guiji.dispatch.util.HttpDownload;
import com.guiji.dispatch.vo.DownLoadPlanVo;
import com.guiji.utils.IdGengerator.IdUtils;
import io.swagger.annotations.ApiOperation;
import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.write.*;
import jxl.write.biff.RowsExceededException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.Boolean;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class FileController {

	@Autowired
	private FileInterface file;
	@Autowired
	private FileErrorRecordsMapper errorMapper;
	@Autowired
	private DispatchPlanMapper dispatchMapper;
	@Autowired
	private IDispatchBatchLineService lineServiceImpl;

	@Autowired
	private IDispatchPlanService dispatchPlanService;

	/**
	 * 查询文件记录
	 * @param pagenum
	 * @param pagesize
	 * @param batchName
	 * @param startTime
	 * @param endTime
	 * @param orgCode
	 * @return
	 */
	@GetMapping(value = "queryFileRecords")
	public Page<FileRecords> queryFileInterface(@RequestParam(required = true, name = "pagenum") int pagenum,
			@RequestParam(required = true, name = "pagesize") int pagesize,
			@RequestParam(required = false, name = "batchName") String batchName,
			@RequestParam(required = false, name = "startTime") String startTime,
			@RequestParam(required = false, name = "endTime") String endTime,
			@RequestHeader String orgCode) {
		Page<FileRecords> queryFileInterface = file.queryFileInterface(pagenum, pagesize, batchName, startTime,
				endTime,orgCode);
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
	public Result.ReturnData<Object> downloadChooseNum(@RequestHeader Integer isDesensitization,
			@RequestBody PlanUuidDto[] dtos, HttpServletResponse resp)
			throws UnsupportedEncodingException, WriteException {
		List<Long> ids = new ArrayList<>();
		List<Integer> orgIds = new ArrayList<>();
		for (int i = 0; i < dtos.length; i++) {
			ids.add(Long.valueOf(dtos[i].getPlanuuid()));

			Integer orgId = IdUtils.doParse(Long.valueOf(dtos[i].getPlanuuid())).getOrgId();
			if(!orgIds.contains(orgId))
			{
				orgIds.add(orgId);
			}
		}
		DispatchPlanExample ex = new DispatchPlanExample();
		ex.createCriteria().andPlanUuidIn(ids).andOrgIdIn(orgIds);

		List<DownLoadPlanVo> selectByExample = dispatchMapper.queryDownloadPlanList(ex);
		String fileName = "任务导出结果详情.xls";
		HttpDownload.setHeader(resp, fileName);

		OutputStream out = null;
		try {
			out = resp.getOutputStream();
			generateExcel4Dispatch(selectByExample, out,isDesensitization);
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

	private void generateExcel4Dispatch(List<DownLoadPlanVo> selectByExample, OutputStream out, Integer isDesensitization)
			throws RowsExceededException, WriteException, IOException {

		Map<String, String> map = new HashMap<>();
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

		/*sheet.addCell(new Label(0, 0, "批次"));
		sheet.addCell(new Label(1, 0, "号码"));
		sheet.addCell(new Label(2, 0, "计划状态"));
		sheet.addCell(new Label(3, 0, "话术"));
		sheet.addCell(new Label(4, 0, "线路"));
		sheet.addCell(new Label(5, 0, "计划日期"));
		sheet.addCell(new Label(6, 0, "计划时间"));
		sheet.addCell(new Label(7, 0, "所属用户"));*/

		sheet.addCell(new Label(0, 0, "批次"));
		sheet.addCell(new Label(1, 0, "号码"));
		sheet.addCell(new Label(2, 0, "变量参数"));
		sheet.addCell(new Label(3, 0, "附件参数"));
		sheet.addCell(new Label(4, 0, "计划状态"));
		sheet.addCell(new Label(5, 0, "意向标签"));
		sheet.addCell(new Label(6, 0, "话术"));
		sheet.addCell(new Label(7, 0, "线路"));
		sheet.addCell(new Label(8, 0, "计划日期"));
		sheet.addCell(new Label(9, 0, "计划时间"));
		sheet.addCell(new Label(10, 0, "所属用户"));
		sheet.addCell(new Label(11, 0, "添加日期"));
		for (int i = 0; i < selectByExample.size(); i++) {
			DownLoadPlanVo dispatchPlan = selectByExample.get(i);
			//查询线路
			List<DispatchBatchLine> queryLinesByPlanUUID = lineServiceImpl.queryListByBatchId(dispatchPlan.getBatchId());
			int k = 0;
			sheet.addCell(new Label(k, i + 1, dispatchPlan.getBatchName()));
			k++;
			if(isDesensitization.equals(0)){
				String phoneNumber = dispatchPlan.getPhone().substring(0, 3) + "****"
						+ dispatchPlan.getPhone().substring(7, dispatchPlan.getPhone().length());
				sheet.addCell(new Label(k, i + 1,phoneNumber));
				k++;
			}else{
				sheet.addCell(new Label(k, i + 1, dispatchPlan.getPhone()));
				k++;
			}
			sheet.addCell(new Label(k, i + 1, dispatchPlan.getParams()));
			k++;
			sheet.addCell(new Label(k, i + 1, dispatchPlan.getAttach()));
			k++;
			sheet.addCell(new Label(k, i + 1, map.get(String.valueOf(dispatchPlan.getStatusPlan()))));
			k++;
			sheet.addCell(new Label(k, i + 1, dispatchPlan.getResult()));
			k++;
			sheet.addCell(new Label(k, i + 1, dispatchPlan.getRobotName()));
			k++;
			String lineName="";
			for(DispatchBatchLine lines : queryLinesByPlanUUID){
				lineName = lineName +""+ lines.getLineName()+",";
			}
			String lineNames = lineName.substring(0, lineName.length()-1);
			sheet.addCell(new Label(k, i + 1, lineNames));
			k++;
			sheet.addCell(new Label(k, i + 1, String.valueOf(dispatchPlan.getCallData())));
			k++;
			sheet.addCell(new Label(k, i + 1, String.valueOf(dispatchPlan.getCallHour())));
			k++;
			sheet.addCell(new Label(k, i + 1, dispatchPlan.getUsername()));
			k++;
			sheet.addCell(new Label(k, i + 1, dispatchPlan.getAddTime()));
		}

		wb.write();
		wb.close();

	}

	@GetMapping(value = "downloadErrorRecords")
	public Result.ReturnData<Object> downloadErrorRecords(@RequestHeader Integer isDesensitization,
			@RequestParam(required = true, name = "fileRecordId") String fileRecordId, HttpServletResponse resp)
			throws UnsupportedEncodingException, WriteException {
		List<FileErrorRecords> queryErrorRecords = file.queryErrorRecords(fileRecordId);
		String fileName = "错误详情结果.xls";
		HttpDownload.setHeader(resp, fileName);

		OutputStream out = null;
		try {
			out = resp.getOutputStream();
			generateExcel(queryErrorRecords, out, isDesensitization);
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

	private void generateExcel(List<FileErrorRecords> queryErrorRecords, OutputStream out, Integer isDesensitization)
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



	//查询计划列表
	@ApiOperation(value="导出计划列表", notes="导出计划列表")
	@RequestMapping(value = "dispatch/file/downloadPlanList", method = {RequestMethod.POST, RequestMethod.GET})
	public Result.ReturnData<Object> downloadPlanList(@RequestHeader Long userId, @RequestHeader String orgCode,
													  @RequestHeader Boolean isSuperAdmin, @RequestHeader Integer isDesensitization,
													  HttpServletResponse resp,
													  @RequestBody QueryDownloadPlanListDto queryPlanDto)
			throws UnsupportedEncodingException, WriteException {
		/********导出数量处理	begin*********************/
		int maxCount = 30000;
		int pageSize = maxCount;
		int startIdx = 0;
		int endIdx = 0;
		if(null == queryPlanDto){
			queryPlanDto = new QueryDownloadPlanListDto();
		}else{
			startIdx = (queryPlanDto.getStartIdx()>0)?(queryPlanDto.getStartIdx()-1):0;
			endIdx = (queryPlanDto.getEndIdx()>0)?queryPlanDto.getEndIdx():0;
			if(startIdx>endIdx){
				throw new GuiyuException("开始数不能大于结束数");
			}
			if(startIdx>=0 && endIdx>=0){
				pageSize = (endIdx-startIdx>maxCount)?maxCount:(endIdx-startIdx);
			}
		}
		queryPlanDto.setPageSize(pageSize);
		queryPlanDto.setStartIdx(startIdx);
		/********导出数量处理	end*********************/

		/********操作员条件处理	*********************/
		queryPlanDto.setOperUserId(userId+"");
		queryPlanDto.setOperOrgCode(orgCode);
		queryPlanDto.setSuperAdmin(isSuperAdmin);
		queryPlanDto.setIsDesensitization(isDesensitization);

		//查询导出数据
		List<DownLoadPlanVo> selectByExample = dispatchPlanService.queryDownloadPlanList(queryPlanDto);
		String fileName = "任务导出结果详情.xls";
		HttpDownload.setHeader(resp, fileName);

		OutputStream out = null;
		try {
			out = resp.getOutputStream();
			this.generateDownloadExcel(selectByExample, out,isDesensitization);
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


	private void generateDownloadExcel(List<DownLoadPlanVo> selectByExample, OutputStream out, Integer isDesensitization)
			throws RowsExceededException, WriteException, IOException {

		Map<String, String> map = new HashMap<>();
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
		sheet.addCell(new Label(2, 0, "变量参数"));
		sheet.addCell(new Label(3, 0, "附件参数"));
		sheet.addCell(new Label(4, 0, "计划状态"));
		sheet.addCell(new Label(5, 0, "意向标签"));
		sheet.addCell(new Label(6, 0, "话术"));
		sheet.addCell(new Label(7, 0, "线路"));
		sheet.addCell(new Label(8, 0, "计划日期"));
		sheet.addCell(new Label(9, 0, "计划时间"));
		sheet.addCell(new Label(10, 0, "所属用户"));
		sheet.addCell(new Label(11, 0, "添加日期"));

		for (int i = 0; i < selectByExample.size(); i++) {
			DownLoadPlanVo dispatchPlan = selectByExample.get(i);
			//查询线路
			//	List<DispatchLines> queryLinesByPlanUUID = lineServiceImpl.queryLinesByPlanUUID(dispatchPlan.getPlanUuidLong());
			int k = 0;
			sheet.addCell(new Label(k, i + 1, dispatchPlan.getBatchName()));
			k++;
			if(isDesensitization.equals(0)){
				String phoneNumber = dispatchPlan.getPhone().substring(0, 3) + "****"
						+ dispatchPlan.getPhone().substring(7, dispatchPlan.getPhone().length());
				sheet.addCell(new Label(k, i + 1,phoneNumber));
				k++;
			}else{
				sheet.addCell(new Label(k, i + 1, dispatchPlan.getPhone()));
				k++;
			}
			sheet.addCell(new Label(k, i + 1, dispatchPlan.getParams()));//变量参数
			k++;
			sheet.addCell(new Label(k, i + 1, dispatchPlan.getAttach()));//附件参数
			k++;
			sheet.addCell(new Label(k, i + 1, map.get(String.valueOf(dispatchPlan.getStatusPlan()))));
			k++;
			sheet.addCell(new Label(k, i + 1, dispatchPlan.getResult()));//意向标签
			k++;
			sheet.addCell(new Label(k, i + 1, dispatchPlan.getRobotName()));
			k++;
			String lineName="";
			/*for(DispatchLines lines : queryLinesByPlanUUID){
				lineName = lineName +""+ lines.getLineName()+",";
			}*/
			//String lineNames = lineName.substring(0, lineName.length()-1);
			sheet.addCell(new Label(k, i + 1, ""));
			k++;
			sheet.addCell(new Label(k, i + 1, String.valueOf(dispatchPlan.getCallData())));
			k++;
			sheet.addCell(new Label(k, i + 1, String.valueOf(dispatchPlan.getCallHour())));
			k++;
			sheet.addCell(new Label(k, i + 1, dispatchPlan.getUsername()));
			k++;
			sheet.addCell(new Label(k, i + 1, dispatchPlan.getAddTime()));
		}

		wb.write();
		wb.close();

	}
}
