package ai.guiji.botsentence.util;
 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.bytedeco.javacpp.avcodec;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.FrameRecorder;
import org.bytedeco.javacv.FrameRecorder.Exception;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
public class AudioConvertUtil {
	
	public static void convert(String inputFile, String outputFile, int audioCodec, int sampleRate, int audioBitrate,
			int audioChannels) {
		Frame audioSamples = null;
		FFmpegFrameRecorder recorder = null;
		FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(inputFile);
 
		if (start(grabber)) {
			recorder = new FFmpegFrameRecorder(outputFile, audioChannels);
			recorder.setAudioOption("crf", "0");
			recorder.setAudioCodec(audioCodec);
			recorder.setAudioBitrate(audioBitrate);
			recorder.setAudioChannels(audioChannels);
			recorder.setSampleRate(sampleRate);
			recorder.setAudioQuality(0);
			recorder.setAudioOption("aq", "10");
			if (start(recorder)) {
				try {
					while ((audioSamples = grabber.grabFrame()) != null) {
						recorder.setTimestamp(grabber.getTimestamp());
						recorder.record(audioSamples);
					}
 
				} catch (org.bytedeco.javacv.FrameGrabber.Exception e1) {
					System.err.println("抓取失败");
				} catch (Exception e) {
					System.err.println("录制失败");
				}
				stop(grabber);
				stop(recorder);
			}
		}
 
	}
 
	public static boolean start(FrameGrabber grabber) {
		try {
			grabber.start();
			return true;
		} catch (org.bytedeco.javacv.FrameGrabber.Exception e2) {
			try {
				System.err.println("首次打开抓取器失败，准备重启抓取器...");
				grabber.restart();
				return true;
			} catch (org.bytedeco.javacv.FrameGrabber.Exception e) {
				try {
					System.err.println("重启抓取器失败，正在关闭抓取器...");
					grabber.stop();
				} catch (org.bytedeco.javacv.FrameGrabber.Exception e1) {
					System.err.println("停止抓取器失败！");
				}
			}
 
		}
		return false;
	}
 
	public static boolean start(FrameRecorder recorder) {
		try {
			recorder.start();
			return true;
		} catch (Exception e2) {
			try {
				System.err.println("首次打开录制器失败！准备重启录制器...");
				recorder.stop();
				recorder.start();
				return true;
			} catch (Exception e) {
				try {
					System.err.println("重启录制器失败！正在停止录制器...");
					recorder.stop();
				} catch (Exception e1) {
					System.err.println("关闭录制器失败！");
				}
			}
		}
		return false;
	}
 
	public static boolean stop(FrameGrabber grabber) {
		try {
			grabber.flush();
			grabber.stop();
			return true;
		} catch (org.bytedeco.javacv.FrameGrabber.Exception e) {
			return false;
		} finally {
			try {
				grabber.stop();
			} catch (org.bytedeco.javacv.FrameGrabber.Exception e) {
				System.err.println("关闭抓取器失败");
			}
		}
	}
 
	public static boolean stop(FrameRecorder recorder) {
		try {
			recorder.stop();
			recorder.release();
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			try {
				recorder.stop();
			} catch (Exception e) {
 
			}
		}
	}
	
	

	// 测试
		public static void main(String[] args) {
			//pcm参数转换
//			convert("东部信息.wav", "eguid.wav", avcodec.AV_CODEC_ID_PCM_S16LE, 8000, 16000,1);
			//pcm转mp3编码示例
			convert("C:\\Users\\Administrator\\Desktop\\20181017001617_qmz_93162_en.wav", "eguid.wav", avcodec.AV_CODEC_ID_PCM_S16LE, 8000, 16000, 1);
		}

		
		private static Logger logger = LoggerFactory.getLogger(AudioConvertUtil.class);

	    //ffmpeg执行目录
	    private final static String FFMPEG_PATH ="ffmpeg";  

	    /** 
	     * 将一个wav文件转换成8000k 单声道文件 
	     *  
	     * @param wavPath
	     * @param  newWavPath  
	     * @throws IOException 
	     * @retuen boolean
	     */  
	    public static boolean converWav(String wavPath,String newWavPath) {
	    	
	       String shellContent = FFMPEG_PATH + " -i " +  wavPath + " -f wav -ar 8000 -ac 1 "+newWavPath;
	       
	       logger.info("开始转换["+ wavPath +"]");
	       logger.info("执行命令行为:"+shellContent);
			try {
				Process process = Runtime.getRuntime().exec(shellContent);
				//取得命令结果的输出流    
	             InputStream fis=process.getInputStream();    
	            //用一个读输出流类去读    
	             InputStreamReader isr=new InputStreamReader(fis);    
	            //用缓冲器读行    
	             BufferedReader br=new BufferedReader(isr);    
	             String line=null;    
	            //直到读完为止    
	            while((line=br.readLine())!=null)    
	             {    
	                 logger.info(line);  
	             }   
			} catch (IOException e) {
				logger.error("转换wav格式异常...", e);
				return false;
			}
			logger.info("转换["+ wavPath +"]成功");
			return true;
	    }
	    
	    
	    
	    /** 
	     * 将一个wav文件去掉头信息
	     *  
	     * @param wavPath
	     * @param  newWavPath  
	     * @throws IOException 
	     * @retuen boolean
	     */  
	    public static boolean removeHeadInfo(String wavPath,String newWavPath) {
	    	
	       String shellContent = "sox " +  wavPath + " /apps/cfgs/standard_head.wav " + newWavPath;
	       
	       logger.info("开始清空头信息["+ wavPath +"]");
	       logger.info("执行命令行为:"+shellContent);
			try {
				Process process = Runtime.getRuntime().exec(shellContent);
				//取得命令结果的输出流    
	             InputStream fis=process.getInputStream();    
	            //用一个读输出流类去读    
	             InputStreamReader isr=new InputStreamReader(fis);    
	            //用缓冲器读行    
	             BufferedReader br=new BufferedReader(isr);    
	             String line=null;    
	            //直到读完为止    
	            while((line=br.readLine())!=null)    
	             {
	                 logger.info(line);  
	             }   
			} catch (IOException e) {
				logger.error("清空wav头信息异常...", e);
				return false;
			}
			logger.info("清空头信息["+ wavPath +"]成功");
			return true;
	    }
		
}
