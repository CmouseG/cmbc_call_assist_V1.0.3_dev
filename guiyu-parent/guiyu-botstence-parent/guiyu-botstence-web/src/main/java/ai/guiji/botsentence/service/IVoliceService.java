package ai.guiji.botsentence.service;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import ai.guiji.botsentence.dao.entity.VoliceInfo;
import ai.guiji.botsentence.dao.entity.VoliceInfoExt;
import ai.guiji.botsentence.vo.RefuseVoliceVO;
import ai.guiji.component.client.config.JsonParam;

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
	public long saveVoliceInfo(VoliceInfo voliceInfo,Long userId);
	
	
	/**
	 * 更新一条录音记录
	 * @param voliceId
	 * @param businessType
	 * @param url
	 */
	public void updateVoliceInfo(long voliceId, String businessType, String voliceUrl,Long userId);
	
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
	public List<String> uploadVoliceZip(String processId,InputStream inStream,Long userId);
	
	/**
	 * 上传保存url
	 * @param processId
	 * @param inStream
	 */
	public String uploadOneVolice(String processId, String voliceId, InputStream inStream, String type,Long userId);
	
	
	/**
	 * json文件上传打包
	 * @param processId
	 * @param inStream
	 */
	public boolean uploadVoliceJsonZip(File dir,String fileName,String processId, String templateId,Long userId);
	
	/**
	 * 保存挽回话术池
	 * @param processId
	 * @param contents
	 */
	public void saveRefuseVolice(String processId, List<RefuseVoliceVO> contents,Long userId);
	
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
}
