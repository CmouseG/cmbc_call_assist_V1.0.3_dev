package com.guiji.component.client.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtil {

	public static File zip(File source,String fileName){
		ZipOutputStream out=null;
		BufferedOutputStream bos=null;
		File zip=null;
		try {
			System.out.println("压缩中...");
			//zip=File.createTempFile(fileName, ".zip");
			zip=new File("D:\\apps\\template_encode\\aaa\\a.zip");
			//创建zip输出流
			out = new ZipOutputStream( new FileOutputStream(zip));
			//创建缓冲输出流
			bos = new BufferedOutputStream(out);
			String base=source.getName();
			//调用函数
			compress(out,bos,source,base);
			System.out.println("压缩完成");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			IOUtil.close(bos);	
		}
		return zip;
	}

	public static void compress(ZipOutputStream out,BufferedOutputStream bos,File sourceFile,String base) throws Exception{
		//如果路径为目录（文件夹）
		if(sourceFile.isDirectory())
		{

			//取出文件夹中的文件（或子文件夹）
			File[] flist = sourceFile.listFiles();

			if(flist.length==0)//如果文件夹为空，则只需在目的地zip文件中写入一个目录进入点
			{
				System.out.println(base+"/");
				out.putNextEntry(new ZipEntry(base+"/") );
			}
			else//如果文件夹不为空，则递归调用compress，文件夹中的每一个文件（或文件夹）进行压缩
			{
				for(int i=0;i<flist.length;i++)
				{
					compress(out,bos,flist[i],base+"/"+flist[i].getName());
				}
			}
		}
		else{//如果不是目录（文件夹），即为文件，则先写入目录进入点，之后将文件写入zip文件中
			out.putNextEntry( new ZipEntry(base) );
			FileInputStream fos = new FileInputStream(sourceFile);
			BufferedInputStream bis = new BufferedInputStream(fos);

			int tag;
			System.out.println(base);
			//将源文件写入到zip文件中
			while((tag=bis.read())!=-1){
				bos.write(tag);
			}
			IOUtil.close(bis);
		}
	}
	
}
