package com.guiji.robot.cfg;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
* @ClassName: MQConfig 
* @Description: Robot机器人能力中心发起的消息队列
* @date 2019年1月16日 下午4:12:25 
* @version V1.0
 */
@Configuration
public class RobotMqConfig {
	//TTS合成回调
	public static final String QUENE_TTS_OK = "robot.ttsok";

	//tts合成回调，自己生产消费
    public final static String TTS_CALLBACK_QUEUE = "robot.ttscallback";

    @Bean
    public Queue ttsOk() {
        return new Queue(QUENE_TTS_OK);
    }

    @Bean
    public Queue ttsCallback() {
        return new Queue(TTS_CALLBACK_QUEUE);
    }
    
}
