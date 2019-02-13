package com.guiji.billing.utils;

import com.guiji.billing.constants.BusiTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

import static java.lang.System.nanoTime;

/**
 * @Description: 描述：ID生成器
 * @date 2019/1/25 13:44
 */
@Component
public class IdWorker {
    protected static final Logger LOG = LoggerFactory.getLogger(IdWorker.class);
    private long workerId;
    private long datacenterId;
    private long sequence = 0L;
    private long twepoch = 1288834974657L;
    private long workerIdBits = 10L;
    private long datacenterIdBits = 5L;
    private long maxWorkerId = -1L ^ (-1L << workerIdBits);
    private long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
    private long sequenceBits = 12L;
    private long workerIdShift = sequenceBits;
    private long datacenterIdShift = sequenceBits + workerIdBits;
    private long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
    private long sequenceMask = -1L ^ (-1L << sequenceBits);

    private long lastTimestamp = -1L;

    public IdWorker() {
    }

    public IdWorker(long workerId, long datacenterId) {
        // sanity check for workerId
        long localWorkerId = getWorkerId();
        this.workerId = getWorkerId();
        this.datacenterId = datacenterId;
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        LOG.info(String.format("worker starting. timestamp left shift %d, datacenter id bits %d, worker id bits %d, " +
                        "sequence bits %d, workerid %d", timestampLeftShift, datacenterIdBits, workerIdBits,
                sequenceBits, localWorkerId));
    }

    public static long getWorkerId() {
        InetAddress addr = null;
        try {
            addr = InetAddress.getLocalHost();
            byte[] ipAddr = addr.getAddress();
            String ipAddrStr = "";
            for (int i = 0; i < ipAddr.length; i++) {
                if (i > 0) {
                    ipAddrStr += ".";
                }
                ipAddrStr += ipAddr[i] & 0xFF;
            }
            //System.out.println(ipAddrStr);
            String ip = ipAddrStr;
            String[] ipArray = ip.split("\\.");
            String lastSegIp = ipArray[3];
            long id = Long.valueOf(lastSegIp);
            return id;
        } catch (Exception e) {
            //有异常返回当前系统纳秒后三位
            e.printStackTrace();
            Long nanoTime = System.nanoTime();
            String nanoTimeS = String.valueOf(nanoTime);
            nanoTime = Long.valueOf(nanoTimeS.substring(nanoTimeS.length() - 3, nanoTimeS.length()));
            return nanoTime;
        }
    }

    public String getItemId(BusiTypeEnum busiTypeEnum){
        if(null == busiTypeEnum){
            return nextId();
        }
        String nextId = String.valueOf(nextId());
        return nextId.startsWith("-") ? (busiTypeEnum.getCode() + nextId.substring(3)): (busiTypeEnum.getCode() + nextId.substring(2));
    }

    // 通过业务类型取得订单的ID
    // Add by start
    public String getBusiId(BusiTypeEnum busiTypeEnum){
        if(null == busiTypeEnum){
            return nextId();
        }
        String nextId = String.valueOf(nextId());
        return nextId.startsWith("-") ? (busiTypeEnum.getCode() + nextId.substring(3)):(busiTypeEnum.getCode() + nextId.substring(2));
    }
    // Add by end

    public String getBusiId(String busiType){
        BusiTypeEnum typeEnum= BusiTypeEnum.getEnum(busiType);
        String nextId = String.valueOf(nextId());
        return nextId.startsWith("-") ? (typeEnum.getCode() + nextId.substring(3)): (typeEnum.getCode() + nextId.substring(2));
    }

    public synchronized String nextId() {
        long timestamp = timeGen();

        if (timestamp < lastTimestamp) {
            LOG.error(String.format("clock is moving backwards.  Rejecting requests until %d.", lastTimestamp));
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        long nextId = Math.abs(((timestamp - twepoch) << timestampLeftShift) | (datacenterId << datacenterIdShift) | (workerId << workerIdShift) | sequence);
        return nextId + "";
    }

    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    protected long timeGen() {
        return System.currentTimeMillis();
    }

    public static void main(String[] args) {
        IdWorker idWorker111=new IdWorker(1,1);

        System.out.println(idWorker111.nextId());


//        System.out.println(nanoTime());
//        new IdWorker(999, 0);
//        Thread a1 = new Thread() {
//            IdWorker idWorker = new IdWorker(0, 0);
//
//            @Override
//            public void run() {
//                for (int a = 0; a < 5; a++) {
//                    System.out.println(idWorker.nextId());
//                }
//            }
//        };
//
//        Thread a2 = new Thread() {
//            IdWorker idWorker = new IdWorker(0, 0);
//
//            @Override
//            public void run() {
//                for (int a = 0; a < 5; a++) {
//                    System.out.println(idWorker.nextId());
//                }
//            }
//        };
//
//        Thread a3 = new Thread() {
//            IdWorker idWorker = new IdWorker(0, 0);
//
//            @Override
//            public void run() {
//                for (int a = 0; a < 5; a++) {
//                    System.out.println(idWorker.nextId());
//                }
//            }
//        };
//        a1.run();
//        a2.run();
//        a3.run();
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        a1.run();
    }
}
