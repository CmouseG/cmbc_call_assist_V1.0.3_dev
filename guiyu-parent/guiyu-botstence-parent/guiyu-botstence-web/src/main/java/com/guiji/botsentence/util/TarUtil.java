package com.guiji.botsentence.util;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.io.IOUtils;
import java.io.*;
import java.util.logging.Logger;
import java.util.zip.GZIPOutputStream;

/**
 * Created by superman on 2014/9/16.
 */
public class TarUtil {
	private Logger logger = Logger.getLogger(getClass().getName());
	private TarArchiveOutputStream tarArchiveOutputStream;
	private File source;
	private File dist;
	private boolean deleteSource;

	/**
	 * 将指定目录下的文件打成tar包
	 * 
	 * @param srcDir
	 *            要压缩的目录
	 * @param distDir
	 *            输出目录
	 * @param tarName
	 *            tar包的名称
	 * @param deleteSource
	 *            压缩后是否删除源文件
	 */
	public TarUtil(String srcDir, String distDir, String tarName, boolean deleteSource) {
		source = new File(srcDir);
		dist = new File(distDir, tarName);
		this.deleteSource = deleteSource;
		if (source.exists()) {
			try {
				tarArchiveOutputStream = new TarArchiveOutputStream(new FileOutputStream(dist));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	public void build() {
		action(source);
		if (tarArchiveOutputStream != null) {
			try {
				tarArchiveOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void action(File file) {
		if (tarArchiveOutputStream == null) {
			logger.severe(source.getName() + "not found.");
			return;
		}
		if (file.isFile()) {
			append(tarArchiveOutputStream, file);
		} else if (file.isDirectory()) {
			File[] files = file.listFiles();
			if (files != null && files.length > 0) {
				for (File f : files) {
					action(f);
				}
			}
		}
	}

	private void append(TarArchiveOutputStream tarArchiveOutputStream, File file) {
		InputStream is = null;
		try {
			is = new BufferedInputStream(new FileInputStream(file));
			TarArchiveEntry entry = new TarArchiveEntry(file);
			entry.setSize(file.length());
			entry.setName(file.getAbsolutePath().substring(source.getAbsolutePath().length() + 1));
			tarArchiveOutputStream.putArchiveEntry(entry);
			IOUtils.copy(is, tarArchiveOutputStream);
			tarArchiveOutputStream.flush();
			tarArchiveOutputStream.closeArchiveEntry();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(is);
			if (deleteSource) {
				if (!file.delete()) {
					logger.warning("Delete source file" + file.getName() + "failed.");
				}
			}
		}
	}
	
	/**
	  * 
	  * @Title: compress
	  * @Description: 将文件用gzip压缩
	  * @param  source 需要压缩的文件
	  * @return File    返回压缩后的文件
	  * @throws
	  */
	 public static File compress(File source) {
	  File target = new File(source.getName() + ".gz");
	  FileInputStream in = null;
	  GZIPOutputStream out = null;
	  try {
	   in = new FileInputStream(source);
	   out = new GZIPOutputStream(new FileOutputStream(target));
	   byte[] array = new byte[1024];
	   int number = -1;
	   while((number = in.read(array, 0, array.length)) != -1) {
	    out.write(array, 0, number);
	   }
	  } catch (FileNotFoundException e) {
	   e.printStackTrace();
	   return null;
	  } catch (IOException e) {
	   e.printStackTrace();
	   return null;
	  } finally {
	   if(in != null) {
	    try {
	     in.close();
	    } catch (IOException e) {
	     e.printStackTrace();
	     return null;
	    }
	   }
	   
	   if(out != null) {
	    try {
	     out.close();
	    } catch (IOException e) {
	     e.printStackTrace();
	     return null;
	    }
	   }
	  }
	  return target;
	 }

	public static void main(String[] args) {
		TarUtil tarBuilder = new TarUtil("D:\\apps\\template\\20181101170840-test3_93332_en",
				"D:\\apps", "tt.tar", false);
		tarBuilder.build();
	}
}
