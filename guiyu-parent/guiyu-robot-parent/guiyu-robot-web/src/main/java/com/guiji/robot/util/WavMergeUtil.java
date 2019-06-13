package com.guiji.robot.util;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.*;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
/** 
* @ClassName: WavMergeUtil
* @Description: wav语音拼接工具类 
* @date 2018年11月20日 上午10:50:47 
* @version V1.0  
*/
public class WavMergeUtil {
	
	
	/**
	 * 顺序合并多个wav语音并输出到新的wav文件
	 * 原wav语音比特率尽量保持一致，如果不一致可能合并后的语音无法播放或效果很差
	 * @param orgWavList
	 * @param outputWav
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 */
	public static void mergeWav(List<String> orgWavList,String outputWav) throws UnsupportedAudioFileException, IOException {
		//生成的新.wav路径
		File fileOut = new File(outputWav);
		//如果这个语音大于 2 个
		if (orgWavList.size() >= 2){
			BufferedInputStream bi1 = null;
			BufferedInputStream bi3 = null;
			BufferedInputStream bi2 = null;
			try {
				bi1 = new BufferedInputStream(new FileInputStream(new File(orgWavList.get(0))));
				bi2 = new BufferedInputStream(new FileInputStream(new File(orgWavList.get(1))));
				AudioInputStream audio1 = AudioSystem.getAudioInputStream(bi1);
				AudioInputStream audio2 = AudioSystem.getAudioInputStream(bi2);

				AudioInputStream audioBuild = new AudioInputStream(
						new SequenceInputStream(audio1, audio2),
						audio1.getFormat(),
						audio1.getFrameLength() +
								audio2.getFrameLength()
				);
				AudioInputStream audio3 = null;
				//大于两个时继续合并
				for(int i = 2; i<orgWavList.size();i++){
					bi3 = new BufferedInputStream(new FileInputStream(new File(orgWavList.get(i))));
					audio3 = AudioSystem.getAudioInputStream(bi3);
					audioBuild = new AudioInputStream(
							new SequenceInputStream(audioBuild, audio3),
							audioBuild.getFormat(), audioBuild.getFrameLength() +
							audio3.getFrameLength()
					);
				}
				//生成语音
				AudioSystem.write(audioBuild, AudioFileFormat.Type.WAVE, fileOut);

				if(audio1 != null) {
					audio1.close();
				}
				if(audio2 != null) {
					audio2.close();
				}
				if(audio3 != null) {
					audio3.close();
				}
				if(audioBuild != null) {
					audioBuild.close();
				}
			} catch (Exception e) {

			} finally {

				IOUtils.closeQuietly(bi1);
				IOUtils.closeQuietly(bi2);
				IOUtils.closeQuietly(bi3);
			}
		} else {

			BufferedInputStream b4 = null;

			AudioInputStream audioInputStream = null;

			try {
				//否则只有一个,直接返回语音路径
				b4 = new BufferedInputStream(new FileInputStream(new File(orgWavList.get(0))));

				audioInputStream = AudioSystem.getAudioInputStream(b4);

				//生成语音
				AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, fileOut);
			} catch (Exception ex) {

			} finally {
				IOUtils.closeQuietly(b4);

				IOUtils.closeQuietly(audioInputStream);
			}


		}
	}
}
