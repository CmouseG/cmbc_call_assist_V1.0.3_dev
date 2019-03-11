package com.guiji.botsentence.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.guiji.botsentence.dao.entity.VoliceInfo;
import com.guiji.botsentence.dao.entity.VoliceInfoExt;
import com.guiji.botsentence.vo.RefuseVoliceVO;
import com.guiji.component.client.config.JsonParam;

/**
 * 
* @ClassName: IVoliceService
* @Description: 录音相关服务类
* @author: 张朋
* @date 2018年8月15日 下午12:36:02 
* @version V1.0
 */
public interface IVoliceService {

	/**
	 * 保存一条录音记录
	 * @param businessType
	 * @param url
	 */
	public long saveVoliceInfo(VoliceInfo voliceInfo, String userId);
	
	
	/**
	 * 更新一条录音记录
	 * @param voliceId
	 * @param businessType
	 * @param url
	 */
	public void updateVoliceInfo(long voliceId, String businessType, String voliceUrl, int times, String userId);
	
	/**
	 * 根据主键查询一条录音记录
	 * @param voliceId
	 * @return
	 */
	public VoliceInfo getVoliceInfo(long voliceId);
	
	/**
	 * 根据流程id初始化上传录音列表
	 * @param voliceId
	 * @return
	 */
	public List<VoliceInfoExt> queryVoliceInfoList(String processId);
	
	/**
	 * 获取选中的音频文案
	 * @param processId
	 * @param voliceIds
	 * @return
	 */
	public List<VoliceInfoExt> queryVoliceInfoListByIds(String processId,String[] voliceIds);
	
	/**
	 * 上传保存url
	 * @param processId
	 * @param inStream
	 */
	public List<String> uploadVoliceZip(String processId,InputStream inStream, String userId);
	
	public List<String> uploadVoliceZipOffline(String processId,InputStream inStream, String userId);
	
	/**
	 * 上传保存url
	 * @param processId
	 * @param inStream
	 */
	public String uploadOneVolice(String processId, String voliceId, File inStream, String type, int times, String userId);
	
	public String uploadOneVolice(String processId, String voliceId, MultipartFile multipartFile, String type, String userId) throws IOException;
	
	/**
	 * json文件上传打包
	 * @param processId
	 * @param inStream
	 */
	public boolean uploadVoliceJsonZip(File dir,String fileName,String processId, String templateId, String userId);
	
	/**
	 * 保存挽回话术池
	 * @param processId
	 * @param contents
	 */
	public void saveRefuseVolice(String processId, List<RefuseVoliceVO> contents, String userId);
	
	/**
	 * 根据话术流程编号查询挽回话术池
	 * @param processId
	 */
	public List<VoliceInfo> queryRefuseVoliceList(String processId);
	
	/**
	 * 删除一条挽回话术池
	 * @param processId
	 * @param voliceId
	 */
	public void deleteRefuseVolice(String processId, String voliceId, String domainName);
	
	public int countFinishNum(String processId);
	
	public int countUnFinishNum(String processId);
	
	/**
	 * 查询话术所有录音
	 * @param processId
	 * @return
	 */
	public List<VoliceInfoExt> queryVoliceListSimple(String processId);
	
	public void deleteAllVolice(String processId);
		
	public String uploadOneVoliceOffline(String processId, String voliceId, MultipartFile multipartFile, String type, String userId) throws IOException;

	public VoliceInfo getVoliceByWavName(String processId, String wavName);
}
