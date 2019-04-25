package com.guiji.nas.util;

import java.io.InputStream;
import java.net.URL;

import com.aliyun.oss.OSSClient;
import com.guiji.nas.property.AliyunUtil;

public class OssUtil {
	
	private static OSSClient ossClient = null;

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
	
		InputStream inputStream = new URL(sourceUrl).openStream();
		ossClient.putObject(AliyunUtil.getBucketName(), fileName, inputStream);
		return fileName;
	}
	
}
