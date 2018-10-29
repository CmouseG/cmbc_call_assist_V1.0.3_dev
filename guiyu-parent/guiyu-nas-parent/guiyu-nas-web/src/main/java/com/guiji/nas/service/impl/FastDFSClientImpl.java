package com.guiji.nas.service.impl;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.exception.FdfsUnsupportStorePathException;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

/**
* FastDFSClientWrapper
*/
@Component
public class FastDFSClientImpl {
	private final Logger logger = LoggerFactory.getLogger(getClass());  
	  
    @Autowired  
    private FastFileStorageClient storageClient;  
  
    /** 
     * 上传文件 
     *  
     * @param file 
     *            文件对象 
     * @return 文件访问地址 
     * @throws IOException 
     */  
    public String uploadFile(MultipartFile file) throws IOException {
        StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(),  
                FilenameUtils.getExtension(file.getOriginalFilename()), null);
        return getResAccessUrl(storePath);  
    }  
  
    /** 
     * 将一段字符串生成一个文件上传 
     *  
     * @param content 
     *            文件内容 
     * @param fileExtension 
     * @return 
     */  
    public String uploadFile(String content, String fileExtension) {  
        byte[] buff = content.getBytes(Charset.forName("UTF-8"));  
        ByteArrayInputStream stream = new ByteArrayInputStream(buff);  
        StorePath storePath = storageClient.uploadFile(stream, buff.length, fileExtension, null);  
        return getResAccessUrl(storePath);  
    }  
  
    // 封装图片完整URL地址  
    private String getResAccessUrl(StorePath storePath) {  
        String fileUrl = storePath.getFullPath();  
        return fileUrl;  
    }  
  
    /** 
     * 传图片并同时生成一个缩略图 "JPG", "JPEG", "PNG", "GIF", "BMP", "WBMP" 
     *  
     * @param file 
     *            文件对象 
     * @return 文件访问地址 
     * @throws IOException 
     */  
    public String uploadImageAndCrtThumbImage(MultipartFile file) throws IOException {  
        StorePath storePath = storageClient.uploadImageAndCrtThumbImage(file.getInputStream(), file.getSize(),  
                FilenameUtils.getExtension(file.getOriginalFilename()), null);  
        return getResAccessUrl(storePath);  
    }  
  
    /** 
     * 删除文件 
     *  
     * @param fileUrl 
     *            文件访问地址 
     * @return 
     */  
    public void deleteFile(String fileUrl) {  
        if (fileUrl != null && !fileUrl.trim().equals("")) {  
            return;  
        }  
        try {  
            StorePath storePath = StorePath.praseFromUrl(fileUrl);  
            storageClient.deleteFile(storePath.getGroup(), storePath.getPath());  
        } catch (FdfsUnsupportStorePathException e) {  
            logger.warn(e.getMessage());  
        }  
    }
}
