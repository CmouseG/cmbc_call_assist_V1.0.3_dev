package ai.guiji.component.client.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * 操作文件工具类
 * 
 * @Description:
 * @author liyang
 * @date 2018年8月23日
 *
 */
public class FileUtil {

	/**
	 * 复制文件夹
	 */
	public static void copyDir(String sourcePath, String newPath) throws IOException {
		File file = new File(sourcePath);
		String[] filePath = file.list();

		if (!(new File(newPath)).exists()) {
			(new File(newPath)).mkdirs();
		}

		for (int i = 0; i < filePath.length; i++) {
			if ((new File(sourcePath + file.separator + filePath[i])).isDirectory()) {
				copyDir(sourcePath + file.separator + filePath[i], newPath + file.separator + filePath[i]);
			}

			if (new File(sourcePath + file.separator + filePath[i]).isFile()) {
				copyFile(sourcePath + file.separator + filePath[i], newPath + file.separator + filePath[i]);
			}

		}
	}

	/**
	 * 复制文件
	 */
	public static void copyFile(String oldPath, String newPath) throws IOException {
		File oldFile = new File(oldPath);
		File file = new File(newPath);
		FileInputStream in = new FileInputStream(oldFile);
		FileOutputStream out = new FileOutputStream(file);

		byte[] buffer = new byte[1024];
		int readByte = 0;
		while ((readByte = in.read(buffer)) != -1) {
			out.write(buffer, 0, readByte);
		}

		in.close();
		out.close();
	}

	public static void main(String[] args) throws IOException {
		System.out.println(getFilePath2Deep("D:\\template", "fpxdkyksq"));
	}

	/**
	 * 查找指定名称的文件夹,查找一层子目录
	 */
	public static String getFilePath(String path, String dirName) {
		File file = new File(path);
		File[] files = file.listFiles();
		for (int i = 0; i < files.length; i++) {

			if (files[i].isDirectory()) {

				File filei = files[i];
				File[] fileis = filei.listFiles();
				for (int j = 0; j < fileis.length; j++) {

					if (fileis[j].getName().equals(dirName)) {
						return fileis[j].getPath();
					}
				}
			}
		}
		return null;
	}

	/**
	 * 查找指定名称的文件夹,查找2层子目录
	 */
	public static String getFilePath2Deep(String path, String dirName) {
		File file = new File(path);
		File[] files = file.listFiles();
		for (int i = 0; i < files.length; i++) {

			if (files[i].isDirectory()) {

				File filei = files[i];
				File[] fileis = filei.listFiles();
				for (int j = 0; j < fileis.length; j++) {

					if (fileis[j].isDirectory()) {

						File file2Deep = fileis[j];
						File[] file2Deeps = file2Deep.listFiles();
						for (int m = 0; m < file2Deeps.length; m++) {

							if (file2Deeps[m].getName().equals(dirName)) {
								return file2Deeps[m].getPath();
							}
						}
					}
				}
			}
		}
		return null;
	}

	/**
	 * 获取目录下，所有文件
	 * @param filePath
	 * @param filePaths
	 * @return
	 */
	  public static List<File> getAllFilePaths(File filePath,List<File> filePaths){
         File[] files = filePath.listFiles();
         if(files == null){
             return filePaths;    
         }    
         for(File f:files){
             if(f.isDirectory()){
                 filePaths.add(f);
                 getAllFilePaths(f,filePaths);
             }else{
                 filePaths.add(f);
             }    
         }
         return filePaths;
     }
	  
	  
	  /**
		 * 获取目录下，所有文件
		 * @param filePath
		 * @param filePaths
		 * @return
		 */
	  public static List<File> getAllFilePathsWithDic(File filePath,List<File> filePaths){
         File[] files = filePath.listFiles();
         if(files == null){
             return filePaths;    
         }    
         for(File f:files){
             if(f.isDirectory()){
                 continue;
             }else{
                 filePaths.add(f);
             }    
         }
         return filePaths;
     }
	  

	/**
	 * 往文件中写入内容
	 */
	public static void writeFile(String filePath, String content) throws IOException {

		File file = new File(filePath);
		if (!file.exists()) {
			file.createNewFile();
		}

		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(content);
		bw.close();

	}
	
	
	/**
	 * 读取文件内容
	 */
	public static String readToString(File file) throws IOException {
		Long filelength = file.length();
		byte[] filecontent = new byte[filelength.intValue()];
		FileInputStream in = new FileInputStream(file);
		in.read(filecontent);
		in.close();
		return new String(filecontent, "UTF-8");
	}

}
