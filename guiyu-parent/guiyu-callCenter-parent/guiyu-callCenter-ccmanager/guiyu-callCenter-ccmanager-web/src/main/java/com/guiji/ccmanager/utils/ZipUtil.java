package com.guiji.ccmanager.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
public class ZipUtil {

	private ZipUtil() {
	}

	public static File zip(File source, OutputStream outresp){
		ZipOutputStream out=null;
		BufferedOutputStream bos=null;
		File zip=null;
		try {
			log.info("compress zip start...");
			//创建zip输出流
			out = new ZipOutputStream( outresp);
			//创建缓冲输出流
			bos = new BufferedOutputStream(out);
			String base=source.getName();
			//调用函数
			compress(out,bos,source,base);
			log.info("compress zip over...");
		}catch (Exception e) {
			log.error("ZipUtil zip error:"+e);
		}finally {
			if (bos != null) {
				try{bos.close();}
				catch (IOException e){
					log.error("ZipUtil bos close error:"+e);
				}
			}
		}
		return zip;
	}

	public static void compress(ZipOutputStream out,BufferedOutputStream bos,File sourceFile,String base){
		//如果路径为目录（文件夹）
		if(sourceFile.isDirectory())
		{
			//取出文件夹中的文件（或子文件夹）
			File[] flist = sourceFile.listFiles();

			if(flist.length==0)//如果文件夹为空，则只需在目的地zip文件中写入一个目录进入点
			{
				log.info("compress file:"+base+"/");
				try {
					out.putNextEntry(new ZipEntry(base+"/") );
				} catch (IOException e) {
					log.error("ZipUtil compress  has error when flist.length=0:"+e);
				}
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
			BufferedInputStream bis = null;
			FileInputStream fos = null;
			try{
				out.putNextEntry( new ZipEntry(base) );
				fos = new FileInputStream(sourceFile);
				bis = new BufferedInputStream(fos);

				int tag;
				log.info("compress file:"+base);
				//将源文件写入到zip文件中
				while((tag=bis.read())!=-1){
					bos.write(tag);
				}
				bos.flush();

			}catch (IOException e){
				log.error("ZipUtil compress has error:"+e);
			}finally {
				if(bis!=null){
					try{bis.close();}
					catch (IOException e){
						log.error("ZipUtil compress close bis error:"+e);
					}
				}
				if(fos!=null){
					try{fos.close();}
					catch (IOException e){
						log.error("ZipUtil compress close fos error:"+e);
					}
				}
			}

		}
	}
	
}
