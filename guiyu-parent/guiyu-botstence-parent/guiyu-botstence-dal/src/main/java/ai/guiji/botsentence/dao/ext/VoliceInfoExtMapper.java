package ai.guiji.botsentence.dao.ext;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import ai.guiji.botsentence.dao.entity.VoliceInfo;
import ai.guiji.botsentence.dao.entity.VoliceInfoExt;
import ai.guiji.botsentence.dao.entity.ext.VoliceInfoVO;

public interface VoliceInfoExtMapper {
	
    public List<VoliceInfoExt> queryVoliceInfoList(String processId);
    
    public List<VoliceInfoExt> queryVoliceInfoListByIds(Map<String,Object> map);
    
    public void updateVoliceUrlById(@Param("voliceId")String voliceId,@Param("url")String url);
    
    public void updateVoliceUrlAndNameById(@Param("voliceId")String voliceId,@Param("url")String url,@Param("name")String name);
    
    public List<String> queryAllVoliceId(String processId);
    
    //获取版本号
    public String getVersionByProcessId(String processId);
    
    //获取机器码
    public String getCodeByProcessId(String processId);
    
    public int batchInsert(List<VoliceInfoVO> list);
    
    public void updateVoliceFlag(String processId);
    
    //查询文案内容
    public String getContentByVoliceId(String voliceId);
}