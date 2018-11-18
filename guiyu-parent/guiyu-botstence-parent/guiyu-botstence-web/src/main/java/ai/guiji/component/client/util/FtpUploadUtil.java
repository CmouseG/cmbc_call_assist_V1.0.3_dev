package ai.guiji.component.client.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

/**
 * sftp文件上传
 * @author Administrator
 *
 */

@Configuration
@ConditionalOnProperty(value = "sftp.enable", havingValue = "true")
public class FtpUploadUtil {

	private  Logger log = LoggerFactory.getLogger(this.getClass()); 

	private static ChannelSftp sftp;  

	private Session session;  
	/** FTP 登录用户名*/    
	@Value("${sftp.username}")
	private String username;  
	/** FTP 登录密码*/    
	@Value("${sftp.password}" )
	private String password;  
	/** 私钥 */
	private String privateKey;  
	/** FTP 服务器地址IP地址*/    
	@Value("${sftp.host}")
	private String host;  
	/** FTP 端口*/  
	@Value("${sftp.port}")
	private int port;
	
	//访问前缀路径
	@Value("${sftp.requestPath}")
	private String requestPath;
	
	/** 
	 * 连接sftp服务器 
	 * 
	 * @throws Exception  
	 */ 
	//@PostConstruct
	public void login(){  
		try {  
			JSch jsch = new JSch();  
			if (privateKey != null) {  
				jsch.addIdentity(privateKey);// 设置私钥  
				log.info("sftp connect,path of private key file：{}" , privateKey);  
			}  
			log.info("sftp connect by host:{} username:{}",host,username);  

			session = jsch.getSession(username, host, port);  
			log.info("Session is build");  
			if (password != null) {  
				session.setPassword(password);    
			}  
			Properties config = new Properties();  
			config.put("StrictHostKeyChecking", "no");  

			session.setConfig(config);  
			session.connect();  
			log.info("Session is connected");  

			Channel channel = session.openChannel("sftp");  
			channel.connect();  
			log.info("channel is connected");  

			sftp = (ChannelSftp) channel;  
			log.info(String.format("sftp server host:[%s] port:[%s] is connect successfull", host, port));  
		} catch (JSchException e) {  
			log.error("Cannot connect to specified sftp server : {}:{} \n Exception message is: {}", new Object[]{host, port, e.getMessage()});    
		}  
	}    

	/** 
	 * 关闭连接 server  
	 */ 
	//@PreDestroy
	public void logout(){  
		if (sftp != null) {  
			if (sftp.isConnected()) {  
				sftp.disconnect();  
				log.info("sftp is closed already");  
			}  
		}  
		if (session != null) {  
			if (session.isConnected()) {  
				session.disconnect();  
				log.info("sshSession is closed already");  
			}  
		}  
	}  

	/**  
	 * 将输入流的数据上传到sftp作为文件  
	 *   
	 * @param directory  
	 *            上传到该目录  
	 * @param sftpFileName  
	 *            sftp端文件名  
	 * @param in  
	 *            输入流  
	 * @throws SftpException   
	 * @throws Exception  
	 */    
	public String upload(String directory, String sftpFileName, InputStream input) throws SftpException{  
		try {
			login();
			sftp.cd(directory);  
		}catch (SftpException e) {  
			log.warn("directory is not exist");  
			sftp.mkdir(directory);  
			sftp.cd(directory);  
		}
		try {
			sftp.put(input, sftpFileName);  
			log.info("file:{} is upload successful" , sftpFileName);
		}finally {
			logout();
		}
		return requestPath+sftpFileName;
	}  

	/** 
	 * 将输入流的数据上传到sftp作为文件  
	 *   
	 * @param directory  
	 *            上传到该目录  
	 * @param sftpFileName  
	 *            sftp端文件名  
	 * @param in  
	 *            输入流  
	 * @throws SftpException   
	 * @throws Exception  
	 */    
	public String upload(String directory, String sftpFileName, File file){  
		InputStream input=null;
		try {
			input=new FileInputStream(file);
			return upload(directory,sftpFileName,input);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (SftpException e) {
			e.printStackTrace();
		}finally {
			IOUtil.close(input);
		}
		return null;
	}  
	/**  
	 * 上传单个文件 
	 * 
	 * @param directory  
	 *            上传到sftp目录  
	 * @param uploadFile 
	 *            要上传的文件,包括路径  
	 * @throws FileNotFoundException 
	 * @throws SftpException 
	 * @throws Exception 
	 */  
	public String upload(String directory, String uploadFile) throws FileNotFoundException, SftpException{  
		File file = new File(uploadFile);  
		return upload(directory, file.getName(), new FileInputStream(file));  
	}  

	/** 
	 * 将byte[]上传到sftp，作为文件。注意:从String生成byte[]是，要指定字符集。 
	 *  
	 * @param directory 
	 *            上传到sftp目录 
	 * @param sftpFileName 
	 *            文件在sftp端的命名 
	 * @param byteArr 
	 *            要上传的字节数组 
	 * @throws SftpException 
	 * @throws Exception 
	 */  
	public String upload(String directory, String sftpFileName, byte[] byteArr) throws SftpException{  
		return upload(directory, sftpFileName, new ByteArrayInputStream(byteArr));  
	}  

	/**  
	 * 将字符串按照指定的字符编码上传到sftp 
	 *   
	 * @param directory 
	 *            上传到sftp目录 
	 * @param sftpFileName 
	 *            文件在sftp端的命名 
	 * @param dataStr 
	 *            待上传的数据 
	 * @param charsetName 
	 *            sftp上的文件，按该字符编码保存 
	 * @throws UnsupportedEncodingException 
	 * @throws SftpException 
	 * @throws Exception 
	 */  
	public String upload(String directory, String sftpFileName, String dataStr, String charsetName) throws UnsupportedEncodingException, SftpException{    
		return upload(directory, sftpFileName, new ByteArrayInputStream(dataStr.getBytes(charsetName)));    
	} 
}
