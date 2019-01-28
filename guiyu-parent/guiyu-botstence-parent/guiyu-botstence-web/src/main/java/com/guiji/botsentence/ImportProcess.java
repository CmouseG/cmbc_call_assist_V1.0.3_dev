package com.guiji.botsentence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.guiji.botsentence.controller.server.vo.ImportDomainVO;
import com.guiji.component.client.util.FileUtil;
import com.guiji.component.client.util.FtpUploadUtil;


@Service
public class ImportProcess {
	
	@Autowired
	private FtpUploadUtil ftpUploadUtil; 
	@Value("${sftp.path}")
	private String ftpPath;

	public static void main(String[] args) {
		
		
//		ImportProcess importProcess = new ImportProcess();
//		importProcess.handleData();
	}

	public void handleData() {
		
		File sourceFile = new File("D:\\imports");
		List<File> listFile = new ArrayList<File>();
		FileUtil.getAllFilePaths(sourceFile,listFile);
		
		//获取processid
		
		
		//存储mav的url
		Map<String,String> mavMap = new HashMap<String,String>();
		//语音文字map
		Map<String,String> wordsMap = new HashMap<String,String>();
		
		for(File file:listFile) {
			String fileName = file.getName();
			if(fileName.equals("new_domain_cfg")){
				File[] domainFiles = file.listFiles();
				for(File domainFile:domainFiles) {
					String domainFileName = domainFile.getName();
					if(domainFileName.endsWith(".json")) {
						try {
							processDomains(FileUtil.readToString(domainFile),domainFileName.replace(".json", ""));
						} catch (IOException e) {
							e.printStackTrace();
						}
					}else if(domainFileName.equals("need_record.txt")) {
						try {
							String records = FileUtil.readToString(domainFile);
							String[] recordArr = records.split("\\*");
							String id = recordArr[recordArr.length-1];
							String content = records.replace("*"+id, "");
							wordsMap.put(id, content);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}else if(fileName.endsWith("_rec")){
				File[] mavFiles = file.listFiles();
				for(File mavFile:mavFiles) {
					String mavName =mavFile.getName();
					if(mavName.endsWith(".mav")) {
						String uploadurl=ftpUploadUtil.upload(ftpPath, mavName, mavFile);
						mavMap.put(mavName.replace(".mav", ""), uploadurl);
					}
					
				}
			}
		}
	}
	
	public static void processDomains(String domainJson,String domainName) {
		System.out.println(domainJson);
		JSONObject jSONObjectDomainDetail = (JSONObject) JSON.parseObject(domainJson).get(domainName);

		ImportDomainVO importDomainVO = JSONObject.parseObject(jSONObjectDomainDetail.toJSONString(), ImportDomainVO.class);
		
		importDomainVO.getCom_domain();
		importDomainVO.getBranch();
		importDomainVO.getEnter();
		importDomainVO.getFailed_enter();
		importDomainVO.getIgnore_but_domains();
		importDomainVO.getLimit();
	}
	
	
	

}
