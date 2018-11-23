package com.guiji.ai.tts.service.impl;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guiji.ai.dao.TtsResultMapper;
import com.guiji.ai.dao.entity.TtsResult;

/**
 * Created by ty on 2018/11/13.
 */
@Component
public class TtsTransferAfterImpl {
	private static Logger logger = LoggerFactory.getLogger(TtsTransferAfterImpl.class);

	@Autowired
	TtsResultMapper ttsResultMapper;

	private static final TtsTransferAfterImpl INSTANCE = new TtsTransferAfterImpl();

	private Productor productor = null;

	private Consummer consummer = null;

	private TtsTransferAfterImpl() {
		ExecutorService executorService = Executors.newFixedThreadPool(20);
		productor = new Productor();
		consummer = new Consummer(productor);
		executorService.execute(consummer);
	}

	public static TtsTransferAfterImpl getInstance() {
		return INSTANCE;
	}

	public void add(TtsResult ttsResult) {
		try {
			productor.produce(ttsResult);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public class Productor {
		private BlockingQueue<TtsResult> queue = null;

		protected Productor() {
			queue = new LinkedBlockingQueue<TtsResult>();
		}

		public void produce(TtsResult ttsResult) throws InterruptedException {
			queue.put(ttsResult);
		}

		public TtsResult get() throws InterruptedException {
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
			TtsResult record = new TtsResult();
			while (true) {
				try {
					record = this.productor.get();
					if (record != null) {
						logger.info("获取到一条待插入数据", record);
						ttsResultMapper.insert(record);
						logger.info("保存成功！");
					}
				} catch (Exception e) {
					logger.error("数据保存失败！", e);
				}
			}
		}
	}
}
