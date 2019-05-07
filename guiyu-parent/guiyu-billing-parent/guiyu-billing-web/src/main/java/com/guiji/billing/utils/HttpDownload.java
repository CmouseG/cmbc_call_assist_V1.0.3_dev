package com.guiji.billing.utils;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
public class HttpDownload {

    public static void  downLoadFromUrl(String urlStr,String fileName,String savePath){
        InputStream inputStream = null;
        FileOutputStream fos = null;
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //得到输入流
            inputStream = conn.getInputStream();
            //获取自己数组
            byte[] getData = readInputStream(inputStream);
            //文件保存位置
            File saveDir = new File(savePath);
            if (!saveDir.exists()) {
                saveDir.mkdirs();
            }
            File file = new File(saveDir + File.separator + fileName);
            fos = new FileOutputStream(file);
            fos.write(getData);
            fos.flush();
        }catch (IOException e){
            log.error("downLoadFromUrl IOException"+e);
        }finally {
            if(fos!=null){
                try{fos.close();}
                catch (IOException e){
                    log.error("fos.close error"+e);
                }
            }
            if(inputStream!=null){
                try{inputStream.close();}
                catch (IOException e){
                    log.error("inputStream.close error"+e);
                 }
            }
        }

    }


    /**
     * 从输入流中获取字节数组
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static  byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        bos.flush();
        return bos.toByteArray();
    }
    public static void setHeader(HttpServletResponse resp, String fileName) throws UnsupportedEncodingException {
        resp.setContentType("application/octet-stream;charset=GBK");
        resp.setHeader("Content-Disposition", "attachment;filename="+
                new String(fileName.getBytes("utf-8"),"iso-8859-1"));
    }

}
