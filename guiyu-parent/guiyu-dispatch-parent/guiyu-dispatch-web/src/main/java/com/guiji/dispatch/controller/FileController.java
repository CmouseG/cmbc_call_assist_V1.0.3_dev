package com.guiji.dispatch.controller;

import com.alibaba.fastjson.JSONObject;
import com.guiji.common.exception.GuiyuException;
import com.guiji.common.model.Page;
import com.guiji.common.model.SysFileReqVO;
import com.guiji.common.model.SysFileRspVO;
import com.guiji.component.jurisdiction.Jurisdiction;
import com.guiji.component.result.Result;
import com.guiji.dispatch.bean.PlanUuidDto;
import com.guiji.dispatch.dao.DispatchPlanMapper;
import com.guiji.dispatch.dao.FileErrorRecordsMapper;
import com.guiji.dispatch.dao.entity.*;
import com.guiji.dispatch.dto.QueryDownloadPlanListDto;
import com.guiji.dispatch.entity.ExportFileRecord;
import com.guiji.dispatch.enums.BusiTypeEnum;
import com.guiji.dispatch.enums.ExportFileStatusEnum;
import com.guiji.dispatch.enums.FileTypeEnum;
import com.guiji.dispatch.line.IDispatchBatchLineService;
import com.guiji.dispatch.model.ExportFileDto;
import com.guiji.dispatch.service.FileInterface;
import com.guiji.dispatch.service.GetApiService;
import com.guiji.dispatch.service.IDispatchPlanService;
import com.guiji.dispatch.service.IExportFileService;
import com.guiji.dispatch.sys.ResultPage;
import com.guiji.dispatch.util.DateTimeUtils;
import com.guiji.dispatch.util.HttpDownload;
import com.guiji.dispatch.vo.DownLoadPlanVo;
import com.guiji.dispatch.vo.FileRecordsListVo;
import com.guiji.user.dao.entity.SysUser;
import com.guiji.utils.IdGengerator.IdUtils;
import com.guiji.utils.JsonUtils;
import com.guiji.utils.NasUtil;
import io.swagger.annotations.ApiOperation;
import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.write.*;
import jxl.write.biff.RowsExceededException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.Boolean;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
@RestController
public class FileController {

	private Logger logger = LoggerFactory.getLogger(FileController.class);

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

	@Autowired
	private IExportFileService exportFileService;

	@Autowired
	private GetApiService getApiService;

	@Value("${file.tmpPath}")
	private String tmpPath;

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
	public Page<FileRecordsListVo> queryFileInterface(@RequestParam(required = true, name = "pagenum") int pagenum,
			@RequestParam(required = true, name = "pagesize") int pagesize,
			@RequestParam(required = false, name = "batchName") String batchName,
			@RequestParam(required = false, name = "startTime") String startTime,
			@RequestParam(required = false, name = "endTime") String endTime,
			@RequestHeader String userId, @RequestHeader String orgCode, @RequestHeader Integer authLevel) {
		logger.info("userId:{},orgCode:{},authLevel:{}", userId, orgCode, authLevel);
		Page<FileRecordsListVo> queryFileInterface = file.queryFileInterface(pagenum, pagesize, batchName, startTime,
				endTime, userId, orgCode, authLevel);
		return queryFileInterface;
	}

	/**
	 * 删除文件记录
	 * @param id
	 * @return
	 */
	@Jurisdiction("taskCenter_importRecord_delete")
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

	@Jurisdiction("taskCenter_phonelist_batchExport")
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

		Map<Integer, String> batchLineMap = new HashMap<>();
		for (int i = 0; i < selectByExample.size(); i++) {
			DownLoadPlanVo dispatchPlan = selectByExample.get(i);
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
			//查询线路
			String lineNames = "";
			if (!batchLineMap.containsKey(dispatchPlan.getBatchId())) {
				List<DispatchBatchLine> queryLinesByPlanUUID = lineServiceImpl.queryListByBatchId(dispatchPlan.getBatchId());

				if (queryLinesByPlanUUID != null && !queryLinesByPlanUUID.isEmpty()) {
					String lineName = "";
					//查询线路
					for (DispatchBatchLine lines : queryLinesByPlanUUID) {
						lineName = lineName + "" + lines.getLineName() + ",";
					}

					lineNames = lineName.substring(0, lineName.length() - 1);
					batchLineMap.put(dispatchPlan.getBatchId(), lineNames);
				}
			} else {
				lineNames = batchLineMap.get(dispatchPlan.getBatchId());
			}
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
		sheet.addCell(new Label(3, 0, "客户名称"));
		sheet.addCell(new Label(4, 0, "客户所属单位"));
		sheet.addCell(new Label(5, 0, "错误类型"));
		for (int i = 0; i < queryErrorRecords.size(); i++) {
			FileErrorRecords fileErrorRecords = queryErrorRecords.get(i);
			int k = 0;
			sheet.addCell(new Label(k, i + 1, fileErrorRecords.getPhone()));
			k++;
			sheet.addCell(new Label(k, i + 1, fileErrorRecords.getAttach()));
			k++;
			sheet.addCell(new Label(k, i + 1, fileErrorRecords.getParams()));
			k++;
			sheet.addCell(new Label(k, i + 1, fileErrorRecords.getCustName()));
			k++;
			sheet.addCell(new Label(k, i + 1, fileErrorRecords.getCustCompany()));
			k++;
			sheet.addCell(new Label(k, i + 1, map.get(fileErrorRecords.getErrorType())));
		}

		wb.write();
		wb.close();

	}



	//查询计划列表
	@ApiOperation(value="导出计划列表", notes="导出计划列表")
	@RequestMapping(value = "dispatch/file/downloadPlanList", method = {RequestMethod.POST, RequestMethod.GET})
	public Result.ReturnData<Object> downloadPlanList(@RequestHeader Long userId, @RequestHeader String orgCode, @RequestHeader Integer authLevel,
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
		queryPlanDto.setAuthLevel(authLevel);

		logger.info("/dispatch/file/downloadPlanList:{}", JsonUtils.bean2Json(queryPlanDto));
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

		Map<Integer, String> batchLineMap = new HashMap<>();
		for (int i = 0; i < selectByExample.size(); i++) {
			DownLoadPlanVo dispatchPlan = selectByExample.get(i);

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

			//查询线路
			String lineNames = "";
			if (!batchLineMap.containsKey(dispatchPlan.getBatchId())) {
				List<DispatchBatchLine> queryLinesByPlanUUID = lineServiceImpl.queryListByBatchId(dispatchPlan.getBatchId());

				if (queryLinesByPlanUUID != null && !queryLinesByPlanUUID.isEmpty()) {
					String lineName = "";
					//查询线路
					for (DispatchBatchLine lines : queryLinesByPlanUUID) {
						lineName = lineName + "" + lines.getLineName() + ",";
					}

					lineNames = lineName.substring(0, lineName.length() - 1);
					batchLineMap.put(dispatchPlan.getBatchId(), lineNames);
				}
			} else {
				lineNames = batchLineMap.get(dispatchPlan.getBatchId());
			}

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


	//下载导入记录文件
	@ApiOperation(value="下载导入记录文件", notes="下载导入记录文件")
	@RequestMapping(value = "dispatch/file/downloadImportRecord", method = {RequestMethod.POST, RequestMethod.GET})
	public void downloadImportRecord_bak(HttpServletRequest request, HttpServletResponse response,
									 @RequestParam(required = false, name = "id") Long id)
			throws UnsupportedEncodingException, WriteException {
		FileRecords fileRecords = file.queryFileRecordById(id);
		if(null != fileRecords && !StringUtils.isEmpty(fileRecords.getUrl())){
			String fileUrl = fileRecords.getUrl();
			String fileType = fileUrl.substring(fileUrl.lastIndexOf("."), fileUrl.length());
			response.reset();
			HttpDownload.setHeader(response, "导入记录" + fileType);
			//创建url连接;
			File file = new File(fileUrl);
			InputStream is = null;
			OutputStream os = null;
			//创建url连接;
			HttpURLConnection urlconn = null;
			try {
				URL url = new URL(fileUrl);
				urlconn = (HttpURLConnection)url.openConnection();
				//链接远程服务器;
				urlconn.connect();

			//	is = new BufferedInputStream(new FileInputStream(file));
				is = new BufferedInputStream(urlconn.getInputStream());
				os = new BufferedOutputStream(response.getOutputStream());

				byte[] buffer = new byte[is.available()];
				int len;
				while((len =is.read(buffer))>0){
					os.write(buffer, 0, len);
				}
				os.flush();
			}catch(Exception e){
				logger.error("", e);
			}finally{
				if(null != is){
					try {
						is.close();
					} catch (IOException e) {
						log.error("is.close error:" + e);
						e.printStackTrace();
					}
				}

				if(null != os){
					try {
						os.close();
					} catch (IOException e) {
						log.error("os.close error:" + e);
						e.printStackTrace();
					}
				}

				if(null != urlconn){
					urlconn.disconnect();
				}
			}
		}
	}

	//下载导入记录文件
	@ApiOperation(value="下载导入记录文件", notes="下载导入记录文件")
	@RequestMapping(value = "dispatch/file/importRecord", method = {RequestMethod.POST, RequestMethod.GET})
	public void downloadImportRecord(HttpServletRequest request, HttpServletResponse response,
							 @RequestParam(required = false, name = "id") Long id,
							 @RequestHeader String userId, @RequestHeader String orgCode)
			throws UnsupportedEncodingException, WriteException {
		FileRecords fileRecords = file.queryFileRecordById(id);
		if(null != fileRecords && !StringUtils.isEmpty(fileRecords.getUrl())) {
			//增加导出文件记录
			ExportFileRecord recordRes = exportFileService.addExportFile(this.getExportFileData(fileRecords, userId, orgCode));
			boolean bool = false;
			String fileUrl = fileRecords.getUrl();//"http://192.168.1.57:8080/group1/M00/01/23/wKgBOVybN92AO6QJAAApcQroVSU36.xlsx";
			File generateFile = null;	//生成文件
			File zipFile = null;		//压缩文件
			SysFileRspVO resFile = null;
			try{
				//生成导出文件
				generateFile = this.generateFile(fileUrl);
				//压缩文件
				zipFile = this.generateZipFile(generateFile);
				//上传压缩文件
				resFile = this.uploadFile(zipFile);
				bool = true;
			}catch(Exception e){
				logger.error("导出文件异常", e);
			}finally {
				//导出结果变更
				exportFileService.endExportFile(recordRes.getRecordId(),
						bool ? ExportFileStatusEnum.FINISH.getStatus() : ExportFileStatusEnum.FAIL.getStatus(),
						null != resFile ? resFile.getSkUrl() : null);

				if(null != generateFile){

				}
			}
		}else{
			throw new GuiyuException("下载文件不存在");
		}
	}

	/**
	 * 封装
	 * @param fileRecords
	 * @return
	 */
	private ExportFileDto getExportFileData(FileRecords fileRecords, String userId, String orgCode){
		ExportFileDto data = new ExportFileDto();
		data.setBusiId(fileRecords.getId()+"");
		data.setBusiType(BusiTypeEnum.DISPATCH.getType());
		data.setFileOriginalUrl(fileRecords.getUrl());
		data.setFileType(FileTypeEnum.EXECL.getType());
		Integer batchId = fileRecords.getBatchid();
		if(null != batchId){
			int batchCount = dispatchPlanService.queryPlanCountByBatch(batchId);
			data.setTotalNum(batchCount);
		}
		SysUser user = getApiService.getUserById(userId);
		data.setUserId(userId);
		data.setOrgCode(orgCode);
		data.setCreateName(null != user?user.getUsername():null);
		data.setCreateTime(new SimpleDateFormat(DateTimeUtils.DEFAULT_DATE_FORMAT_PATTERN_FULL).format(new Date()));
		/*data.setCreateName(fileRecords.getUserName());
		data.setCreateTime(new SimpleDateFormat(DateTimeUtils.DEFAULT_DATE_FORMAT_PATTERN_FULL).format(fileRecords.getCreateTime()));*/
		return data;
	}


	/**
	 * 上传nas文件
	 * @param zipFile
	 * @return
	 */
	private SysFileRspVO uploadFile(File zipFile){
		try {
			Long fileRecordId = System.currentTimeMillis();
			SysFileReqVO sysFileReqVO = new SysFileReqVO();
			sysFileReqVO.setBusiId(System.currentTimeMillis() + "");
			sysFileReqVO.setBusiType("dispatch"); // 上传的影像文件业务类型
			sysFileReqVO.setSysCode("02"); // 文件上传系统码
			sysFileReqVO.setThumbImageFlag("0"); // 是否需要生成缩略图,0-无需生成，1-生成，默认不生成缩略图
			SysFileRspVO sysFileRsp = new NasUtil().uploadNas(sysFileReqVO, zipFile);
			System.out.println(JsonUtils.bean2Json(sysFileRsp));
			return sysFileRsp;
		}catch(Exception e){
			logger.error("上传nas异常", e);
			throw new GuiyuException("上传nas异常", e);
		}
	}

	/**
	 * 生成.zip文件;
	 * @param excelFile
	 * @return
	 * @throws IOException
	 */
	public File generateZipFile(File excelFile){
		File file = new File(this.tmpPath + File.separator + excelFile.getName() +".zip");
		ZipOutputStream zipOutputStream = null;
		try {
			zipOutputStream = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
			//	File[] files = new File(path).listFiles();
			FileInputStream fileInputStream = null;
			byte[] buf = new byte[1024];
			int len = 0;
		/*if(files!=null && files.length > 0){
			for(File excelFile:files){*/
			String fileName = excelFile.getName();
			fileInputStream = new FileInputStream(excelFile);
			//放入压缩zip包中;
			zipOutputStream.putNextEntry(new ZipEntry( fileName));//this.tmpPath + File.separator  +
			//读取文件;
			while ((len = fileInputStream.read(buf)) > 0) {
				zipOutputStream.write(buf, 0, len);
			}
			//关闭;
			zipOutputStream.closeEntry();
			if (fileInputStream != null) {
				fileInputStream.close();
			}
			/*}
		}*/
		}catch(IOException ex){
			logger.error("生成.zip文件异常", ex);
			throw new GuiyuException("生成zip文件异常", ex);
		}catch(Exception e){
			logger.error("生成.zip文件异常", e);
			throw new GuiyuException("生成zip文件异常", e);
		}finally {
			if (zipOutputStream != null) {
				try {
					zipOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return file;

		/*File f = new File(lj);
		InputStream istream = new FileInputStream(f);
		return istream;*/
	}

	/**
	 * 生成导出文件
	 * @param fileUrl
	 * @return
	 */
	private File generateFile(String fileUrl){
		File file = new File(fileUrl);
		File generateFile = new File(this.tmpPath + File.separator + file.getName());
		InputStream is = null;
		OutputStream os = null;
		//创建url连接;
		HttpURLConnection urlconn = null;
		try {
			URL url = new URL(fileUrl);
			urlconn = (HttpURLConnection)url.openConnection();
			//链接远程服务器;
			urlconn.connect();
			//	is = new BufferedInputStream(new FileInputStream(file));
			is = new BufferedInputStream(urlconn.getInputStream());
			os = new BufferedOutputStream(new FileOutputStream(generateFile));

			byte[] buffer = new byte[is.available()];
			int len;
			while((len =is.read(buffer))>0){
				os.write(buffer, 0, len);

			}
			os.flush();
		}catch(Exception e){
			logger.error("生成导出文件异常", e);
			throw new GuiyuException("生成导出文件异常", e);
		}finally{
			if(null != is){
				try {
					is.close();
				} catch (IOException e) {
					log.error("is.close error:" + e);
					e.printStackTrace();
				}
			}

			if(null != os){
				try {
					os.close();
				} catch (IOException e) {
					log.error("os.close error:" + e);
					e.printStackTrace();
				}
			}

			if(null != urlconn){
				urlconn.disconnect();
			}

			return generateFile;
		}
	}

}
