package com.guiji.ai.tts.handler;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.guiji.ai.dao.TtsStatusMapper;
import com.guiji.ai.dao.entity.TtsStatus;

@Component
public class SaveTtsStatusHandler
{
	private static Logger logger = LoggerFactory.getLogger(SaveTtsStatusHandler.class);

	private static final SaveTtsStatusHandler INSTANCE = new SaveTtsStatusHandler();
	private Productor productor = null;
	private Consummer consummer = null;
	TtsStatusMapper ttsStatusMapper;

	private SaveTtsStatusHandler() {
		ExecutorService executorService = Executors.newFixedThreadPool(20);
		productor = new Productor();
		consummer = new Consummer(productor);
		executorService.execute(consummer);
	}

	public static SaveTtsStatusHandler getInstance() {
		return INSTANCE;
	}

	public void add(TtsStatus ttsStatus, TtsStatusMapper ttsStatusMapper) {
		try {
			this.ttsStatusMapper = ttsStatusMapper;
			productor.produce(ttsStatus);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public class Productor {
		private BlockingQueue<TtsStatus> queue = null;

		protected Productor() {
			queue = new LinkedBlockingQueue<TtsStatus>();
		}

		public void produce(TtsStatus ttsStatus) throws InterruptedException {
			queue.put(ttsStatus);
		}

		public TtsStatus get() throws InterruptedException {
			return queue.take();
		}
	}

	public class Consummer implements Runnable {
		private Productor productor = null;

		public Consummer(Productor productor) {
			this.productor = productor;
		}

		@Transactional
		public void run() {
			TtsStatus record = new TtsStatus();
			while (true) {
				try {
					record = this.productor.get();
					if (record != null) {
						logger.info("获取到一条待插入数据", record);
						ttsStatusMapper.insertSelective(record);
						logger.info("保存成功！");
					}
				} catch (Exception e) {
					logger.error("数据保存失败！", e);
					continue;
				}
			}
		}
	}
}
