package ai.guiji.component.client.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

/**
 * 七牛云存储文件上传
 * @author Administrator
 *
 */

@Component
@ConditionalOnProperty(value = "qn.enable", havingValue = "true")
public class QiuniuUploadUtil {

	private  Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Value("${qn.accessKey}")
	private String accessKey;
	@Value("${qn.secretKey}")
	private String secretKey;
	@Value("${qn.bucket}")
	private String bucket;
	@Value("${qn.path}")
	private String path;
	
	private UploadManager uploadManager;
	
	private Auth auth;
	
	
	@PostConstruct
	public void init(){
		Configuration cfg = new Configuration(Zone.zone0());
		//...其他参数参考类注释
		uploadManager = new UploadManager(cfg);
		//...生成上传凭证，然后准备上传
		auth = Auth.create(accessKey, secretKey);
	}
	
	public String upload(InputStream input,String key){
	    String upToken = auth.uploadToken(bucket);
	    try {
	        Response response = uploadManager.put(input,key,upToken,null, null);
	        //解析上传成功的结果
	        DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
	        return path+putRet.key;
	    } catch (QiniuException ex) {
	        Response r = ex.response;
	        log.error(r.toString());
	        try {
	        	log.error(r.bodyString());
	        } catch (QiniuException ex2) {
	            //ignore
	        }
	    }
	    return null;
	}
	
	
	public void upload(String filePath){
		File file=new File(filePath);
		String fileName=file.getName();
		try {
			InputStream in=new FileInputStream(file);
			upload(in,fileName);
		} catch (FileNotFoundException e) {
			log.error("",e);
		}
	}
}
