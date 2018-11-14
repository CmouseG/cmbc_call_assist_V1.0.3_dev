package com.guiji.ai.tts.service.impl;

import com.guiji.ai.tts.service.TtsService;
import com.guiji.ai.vo.TtsReqVO;
import com.guiji.ai.vo.TtsRspVO;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ty on 2018/11/13.
 */
@Service
public class TtsServiceImpl implements TtsService {
    @Override
    public TtsRspVO translate(TtsReqVO ttsReqVO) {
        Long start = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        // 结果集
        Map<String,String> radioMap = new HashMap<String,String>();
        List<String> taskList = ttsReqVO.getContents();
        // 全流式处理转换成CompletableFuture[]+组装成一个无返回值CompletableFuture，join等待执行完毕。返回结果whenComplete获取
        CompletableFuture[] cfs = taskList.stream()
                .map(text -> CompletableFuture.supplyAsync(() -> calc(ttsReqVO.getBusId(),ttsReqVO.getModel(),text), executorService)
//                        .thenApply(h->Integer.toString(h))
                        .whenComplete((s, e) -> {
                            //System.out.println("任务"+s+"完成!result="+s+"，异常 e="+e+","+new Date());
                            radioMap.put(text,s);
                        })
                ).toArray(CompletableFuture[]::new);

        // 封装后无返回值，必须自己whenComplete()获取
        CompletableFuture.allOf(cfs).join();
       // System.out.println("radioMap="+radioMap+",耗时="+(System.currentTimeMillis()-start));

        TtsRspVO ttsRspVO = new TtsRspVO();
        ttsRspVO.setBusId(ttsReqVO.getBusId());
        ttsRspVO.setModel(ttsReqVO.getModel());
        ttsRspVO.setAudios(radioMap);
        return ttsRspVO;
    }

    public static String calc(String busId, String module, String text) {
        String audioUrl = null;
        try {
            audioUrl = "合成语音URL" + text;
//            System.out.println("task线程：" + Thread.currentThread().getName()
//                    + "yw=" + busId + "module=" + text + "wenben=" + text + ",完成！+" + new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return audioUrl;
    }

    public static void main(String [] args)
    {
        TtsReqVO ttsReqVO = new TtsReqVO();

        ttsReqVO.setBusId("busId");
        ttsReqVO.setModel("module");

        List<String> contents = new ArrayList<String>();
        for (int i = 0; i < 100; i++) {
            contents.add("这是一段文本"+i);
        }

        ttsReqVO.setContents(contents);

        long start = System.currentTimeMillis();
        TtsServiceImpl impl = new TtsServiceImpl();
        TtsRspVO ttsRspVO = impl.translate(ttsReqVO);
        for (String key : ttsRspVO.getAudios().keySet()) {
            System.out.print("Key = " + key);
            System.out.println("Value = " + ttsRspVO.getAudios().get(key));
        }
        long end = System.currentTimeMillis();
        System.out.print("耗时："+(end-start));
    }
}
