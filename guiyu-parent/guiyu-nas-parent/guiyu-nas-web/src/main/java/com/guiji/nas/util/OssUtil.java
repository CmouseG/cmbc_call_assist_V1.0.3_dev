package com.guiji.nas.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.aliyun.oss.OSSClient;
import com.github.tobato.fastdfs.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.guiji.nas.property.AliyunUtil;

public class OssUtil {

	private static OSSClient ossClient = null;
	
	@Autowired  
    private FastFileStorageClient storageClient;
	
	@Value("${fdfs.webServerUrl}")
	private String hostUrl;        //文件服务器主机url

	private static final OssUtil INSTANCE = new OssUtil();
	
	private OssUtil()
	{
		if(ossClient == null)
		{
			ossClient = new OSSClient(AliyunUtil.getEndpoint(), AliyunUtil.getAccessKeyId(), AliyunUtil.getAccessKeySecret());
		}
	}
	
	public static OssUtil getInstance() {
		return INSTANCE;
	}
	
	public String upload(String sourceUrl) throws Exception {
		
		if(ossClient == null) {
			ossClient = new OSSClient(AliyunUtil.getEndpoint(), AliyunUtil.getAccessKeyId(), AliyunUtil.getAccessKeySecret());
		}
		String fileName= sourceUrl.substring(sourceUrl.lastIndexOf("/") + 1, sourceUrl.length());
		
		String fileUrl = sourceUrl.replace(hostUrl, "");
		
		String group = fileUrl.substring(0, fileUrl.indexOf("/"));
        String path = fileUrl.substring(fileUrl.indexOf("/") + 1);
        DownloadByteArray downloadByteArray = new DownloadByteArray();
        byte[] bytes = storageClient.downloadFile(group, path, downloadByteArray);
	
//		InputStream inputStream = new URL(sourceUrl).openStream();
		ossClient.putObject(AliyunUtil.getBucketName(), fileName, new ByteArrayInputStream(bytes));
		return fileName;
	}
	
}
