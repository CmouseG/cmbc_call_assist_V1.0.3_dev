package com.guiji.utils;

import com.guiji.common.exception.GuiyuException;
import com.guiji.common.exception.GuiyuExceptionEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/** 
* @ClassName: NetFileDownUtil 
* @Description: URL资源文件下载工具类
* @author: weiyunbo
* @date 2018年7月20日 下午8:29:23 
* @version V1.0  
*/
public class NetFileDownUtil {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 文件下載URL路徑
	 */
	private String fileUrl = null;
	/**
	 * 文件對象
	 */
	private File file = null;

	public NetFileDownUtil(String url, File file) {
		this.fileUrl = url;
		this.file = file;
	}

	/**
	 * 从网络Url中下载文件
	 * 
	 * @throws IOException
	 */
	public String downfile() throws IOException {
		FileOutputStream fos = null;
		InputStream inputStream = null;
		try {
			if(fileUrl.startsWith("https")||fileUrl.startsWith("HTTPS")){
				trustAllHttpsCertificates();
				HttpsURLConnection.setDefaultHostnameVerifier(hv);
			}
			// 初始化URL資源
			URL url = new URL(fileUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();		
			
			// 设置超时间为3秒
			conn.setConnectTimeout(6 * 1000);
			// 防止屏蔽程序抓取而返回403错误
			conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			// 得到输入流
			inputStream = conn.getInputStream();
			// 得到输出流
			fos = new FileOutputStream(file);
			// 获取自己数组
			convertStream(inputStream, fos);
			return file.getCanonicalPath();
		} catch (Exception e) {
			logger.info("下载URL流失败"+e.getMessage());
			throw new GuiyuException(GuiyuExceptionEnum.EXCP_COMMON_DOWNLOAD_URL_ERR);
		} finally {
			if (fos != null) {
				fos.close();
			}
			if (inputStream != null) {
				inputStream.close();
			}
		}
	}

	/**
	 * 文件落地
	 * 
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	private void convertStream(InputStream inputStream, FileOutputStream fos) throws IOException {
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inputStream.read(buffer)) != -1) {
			fos.write(buffer, 0, len);
		}
	}

	private void trustAllHttpsCertificates() throws Exception {
		javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];
		javax.net.ssl.TrustManager tm = new miTM();
		trustAllCerts[0] = tm;
		javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, null);
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	}
	
	private HostnameVerifier hv = new HostnameVerifier() {
        public boolean verify(String urlHostName, SSLSession session) {
            System.out.println("Warning: URL Host: " + urlHostName + " vs. "+ session.getPeerHost());
            return true;
        }
    };

	static class miTM implements javax.net.ssl.TrustManager, javax.net.ssl.X509TrustManager {
		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		public boolean isServerTrusted(java.security.cert.X509Certificate[] certs) {
			return true;
		}

		public boolean isClientTrusted(java.security.cert.X509Certificate[] certs) {
			return true;
		}

		public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType)
				throws java.security.cert.CertificateException {
			return;
		}

		public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType)
				throws java.security.cert.CertificateException {
			return;
		}
	}

}
