package com.guiji.botsentence.controller;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.guiji.auth.api.IAuth;
import com.guiji.auth.api.IProduct;
import com.guiji.botsentence.service.IImportProcessService;
import com.guiji.botsentence.vo.BotSentenceProcessVO;
import com.guiji.component.client.util.IOUtil;
import com.guiji.component.result.ServerResult;

/**
 * @Description:
 * @author liyang  
 * @date 2018年8月28日  
 *
 */
@RestController
public class ImportProcessController {

	private static Logger logger = LoggerFactory.getLogger(ImportProcessController.class);
	
	@Autowired
	private IImportProcessService importProcessService;
	
	
	@Value("${template.dir}")
	private String tempDir;
	
	private static String FILE_SEPARATOR = System.getProperty("file.separator");
	
	@RequestMapping(value="importAdminProcess")
	public ServerResult importAdminProcess(MultipartFile multipartFile,BotSentenceProcessVO paramVO,@RequestParam String userId) throws Exception{
		return importProcess(multipartFile,paramVO, userId);
	}
	
	@RequestMapping(value="importProcess")
	public ServerResult importProcess(MultipartFile multipartFile, BotSentenceProcessVO paramVO, @RequestHeader String userId) throws Exception{
		String fileName = multipartFile.getOriginalFilename();
		String suffix =  fileName.substring(fileName.lastIndexOf(".") + 1);
		if(!"zip".equals(suffix)) {
			return ServerResult.createByErrorMessage("请上传zip格式压缩文件!");
		}
		
		
		File file=File.createTempFile(String.valueOf(System.currentTimeMillis()), "zip");
		 writeFile(multipartFile.getInputStream(),file);
		 ZipFile zipFile=new ZipFile(file, Charset.forName("gbk"));
   	
		 if(StringUtils.isBlank(userId)) {
			 return ServerResult.createByErrorMessage("用户信息为空!"); 
		 }
		 
		 
		 String s = UUID.randomUUID().toString();

		 File descFile = new File(tempDir + FILE_SEPARATOR + userId + FILE_SEPARATOR + fileName.split("\\.")[0] + "-" + s);
		 descFile.mkdirs();
		 
		unZipFiles(zipFile, descFile.getPath());
		logger.info("本地存储上传话术模板临时路径 : " + descFile.getPath());
		importProcessService.importProcess(descFile, paramVO, userId);
		
		FileUtils.deleteDirectory(descFile);
		logger.info("删除临时路径..." + descFile.getPath());
		
		return ServerResult.createBySuccessMessage("success");
	 
	}
	
	
	

	@RequestMapping(value="importProcessInit")
	public ServerResult importProcessInit(MultipartFile multipartFile,@RequestParam("templateType") String templateType,@RequestParam("templatId") String templatId, @RequestHeader String userId) throws Exception{
		String fileName = multipartFile.getOriginalFilename();
		String suffix =  fileName.substring(fileName.lastIndexOf(".") + 1);
		if(!"zip".equals(suffix)) {
			return ServerResult.createByErrorMessage("请上传zip格式压缩文件!");
		}
		
		
		File file=File.createTempFile(String.valueOf(System.currentTimeMillis()), "zip");
		 writeFile(multipartFile.getInputStream(),file);
		 ZipFile zipFile=new ZipFile(file, Charset.forName("gbk"));
   	
		 
		 String s = UUID.randomUUID().toString();

		 File descFile = new File(tempDir + FILE_SEPARATOR + System.currentTimeMillis() + FILE_SEPARATOR + fileName.split("\\.")[0] + "-" + s);
		 descFile.mkdirs();
		 
		unZipFiles(zipFile, descFile.getPath());
		logger.info("本地存储上传话术模板临时路径 : " + descFile.getPath());
		//importProcessService.importProcess(descFile, templateType, templatId, null, userId);
		
		FileUtils.deleteDirectory(descFile);
		logger.info("删除临时路径..." + descFile.getPath());
		
		return ServerResult.createBySuccessMessage("success");
	 
	}
	
	
	private void writeFile(InputStream in,File file){
		OutputStream out=null;
		try {
			out=new FileOutputStream(file);
			byte[] b=new byte[1024];
			int index=-1;
			while((index=in.read(b))!=-1){
				out.write(b, 0, index);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			IOUtil.close(out);
			IOUtil.close(in);
		}
	}
	
	public static void unzip(String zipFile) throws Exception {
        FileSystem fs = FileSystems.newFileSystem(Paths.get(zipFile), null);
        final String currentPath = System.getProperty("user.dir");
        System.out.println("current directory:" + currentPath);
 
        Files.walkFileTree(fs.getPath("/"), new SimpleFileVisitor<Path>() {
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Path destPath = Paths.get(currentPath, file.toString());
                Files.deleteIfExists(destPath);
                Files.createDirectories(destPath.getParent());
                Files.move(file, destPath);
                return FileVisitResult.CONTINUE;
            }
        });
    }
	
	 public static void readZipFile(String file) throws Exception { 
	     // ZipFile zf = new ZipFile(file); 
	      
	      ZipFile zf=new ZipFile(file, Charset.forName("gbk"));
	      
	      InputStream in = new BufferedInputStream(new FileInputStream(file)); 
	      ZipInputStream zin = new ZipInputStream(in); 
	      ZipEntry ze; 
	      while ((ze = zin.getNextEntry()) != null) { 
	        if (ze.isDirectory()) {
	        } else { 
	          System.err.println("file - " + ze.getName() + " : " 
	              + ze.getSize() + " bytes"); 
	          long size = ze.getSize(); 
	          if (size > 0) {
	            BufferedReader br = new BufferedReader( 
	                new InputStreamReader(zf.getInputStream(ze))); 
	            String line; 
	            while ((line = br.readLine()) != null) { 
	              //System.out.println(line); 
	            } 
	            br.close(); 
	          } 
	          System.out.println(); 
	        } 
	      } 
	      zin.closeEntry(); 

	 }
	 
	 /** 
	     * 解压文件到指定目录 
	     * @param zipFile 
	     * @param descDir 
	     * @author isea533 
	     */  
	    @SuppressWarnings("rawtypes")  
	    public static void unZipFiles(ZipFile zip,String descDir)throws IOException{  
	        File pathFile = new File(descDir);  
	        if(!pathFile.exists()){  
	            pathFile.mkdirs();  
	        }  
	       // ZipFile zip = new ZipFile(zipFile);  
	        for(Enumeration entries = zip.entries();entries.hasMoreElements();){  
	            ZipEntry entry = (ZipEntry)entries.nextElement();  
	            
//	            if(entry.isDirectory()) {
//	            	continue;
//	            }
	            
	            String zipEntryName = entry.getName();  
	            InputStream in = zip.getInputStream(entry);  
	            //String outPath = (descDir+ FILE_SEPARATOR +zipEntryName).replaceAll("\\*", "/");;
	            String outPath = (descDir+ FILE_SEPARATOR +zipEntryName); 
	           
	            logger.info("解压文件存储路径11: " + outPath);
	            //判断路径是否存在,不存在则创建文件路径  
//	            File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
	            File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));  
	            if(!file.exists()){
	                file.mkdirs();  
	            }  
	            logger.info("解压文件存储路径22: " + file.getPath());
	            //判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压  
	            if(new File(outPath).isDirectory()){
	                continue;  
	            }  
	            //输出文件路径信息  
	            System.out.println(outPath);  
	              
	            OutputStream out = new FileOutputStream(outPath);
	            byte[] buf1 = new byte[1024];  
	            int len;  
	            while((len=in.read(buf1))>0){  
	                out.write(buf1,0,len);  
	            }  
	            in.close();  
	            out.close();  
	            }  
	        System.out.println("******************解压完毕********************");  
	    }  
	 
	    public static void main(String[] args) throws IOException {
	    	
	    	
		}
}
