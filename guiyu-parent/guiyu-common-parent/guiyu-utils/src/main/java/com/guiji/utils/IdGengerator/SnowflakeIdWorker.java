package com.guiji.utils.IdGengerator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.text.ParseException;

/**
 * Twitter_Snowflake<br>
 * SnowFlake的结构如下(每部分用-分开):<br>
 * 0 - 0000000000 0000000000 0000000000 0000000000 0 - 00000 - 00000 - 000000000000 <br>
 * 1位标识，由于long基本类型在Java中是带符号的，最高位是符号位，正数是0，负数是1，所以id一般是正数，最高位是0<br>
 * 41位时间截(毫秒级)，注意，41位时间截不是存储当前时间的时间截，而是存储时间截的差值（当前时间截 - 开始时间截)
 * 得到的值），这里的的开始时间截，一般是我们的id生成器开始使用的时间，由我们程序来指定的（如下下面程序IdWorker类的startTime属性）。41位的时间截，可以使用69年，年T = (1L << 41) / (1000L * 60 * 60 * 24 * 365) = 69<br>
 * 10位的数据机器位，可以部署在1024个节点，包括5位datacenterId和5位workerId<br>
 * 12位序列，毫秒内的计数，12位的计数顺序号支持每个节点每毫秒(同一机器，同一时间截)产生4096个ID序号<br>
 * 加起来刚好64位，为一个Long型。<br>
 * SnowFlake的优点是，整体上按照时间自增排序，并且整个分布式系统内不会产生ID碰撞(由数据中心ID和机器ID作区分)，并且效率较高，经测试，SnowFlake每秒能够产生26万ID左右。
 */
@Configuration
@Component
public class SnowflakeIdWorker {

    // ==============================Fields===========================================
    /** 开始时间截 (2017-11-01) */
    public static final long twepoch = 1509465600000L;

    public static final long workerIdBits = 5L;

    public static final long orgCodeIdBits = 15L;

    public static final long maxWorkerId = -1L ^ (-1L << workerIdBits);

    public static final long maxDatacenterId = -1L ^ (-1L << orgCodeIdBits);

    public static final long sequenceBits = 8L;

//    public static final long placeHoldBits = 8L;
//
//    public static  final long sequenceIdShift = placeHoldBits;

    public static final long workerIdShift = sequenceBits;

    public static final long dataCenterIdShift = workerIdShift + workerIdBits;

    public static final long timestampShift = dataCenterIdShift + orgCodeIdBits;

    public static final long sequenceMask = -1L ^ (-1L << sequenceBits);

    public static long workerId=5;

    private static long orgId;

    private static long sequence = 0L;

    private static long lastTimestamp = -1L;

    //==============================Constructors=====================================
    public SnowflakeIdWorker(long workerId, long datacenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("orgCode Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        this.workerId = workerId;
        orgId = datacenterId;
    }

    public SnowflakeIdWorker() {

    }

    @Value("${server.id:1}")
    public void setWorkerId(long workerId)
    {
        SnowflakeIdWorker.workerId = workerId;
    }
    // ==============================Methods==========================================
    /**
     * 获得下一个ID (该方法是线程安全的)
     * @return SnowflakeId
     */
    public synchronized static long nextId(long orgId) {
        orgId = orgId;
        long timestamp = currentMillis();

        long minus = (timestamp - lastTimestamp)/1000;
        //如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (minus < 0) {
            throw new RuntimeException(
                    String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        //如果是同一时间生成的，则进行毫秒内序列
        if (minus == 0) {
            sequence = (sequence + 1) & sequenceMask;
            //毫秒内序列溢出
            if (sequence == 0) {
                //阻塞到下一个毫秒,获得新的时间戳
                timestamp = tilNextMillis(lastTimestamp);
            }
        }
        //时间戳改变，毫秒内序列重置
        else {
            sequence = 0L;
        }

        //上次生成ID的时间截
        lastTimestamp = timestamp;

        //移位并通过或运算拼到一起组成64位的ID
        return ((timestamp - twepoch)/1000 << timestampShift) //
                | (orgId << dataCenterIdShift) //
                | (workerId << workerIdShift) //
//                | (sequence << sequenceIdShift)
                | sequence;
//                | (0XFF & sequence);
    }

    protected static long tilNextMillis(long lastTimestamp) {
        long timestamp = currentMillis();
        while ((timestamp-lastTimestamp)/1000 <= 0) {
            timestamp = currentMillis();
        }
        return timestamp;
    }

    /**
     * 返回以毫秒为单位的当前时间
     * @return 当前时间(毫秒)
     */
    protected static long currentMillis() {
        return System.currentTimeMillis();
    }



    //==============================Test=============================================
    /** 测试 */
    public static void main(String[] args) throws ParseException
    {

        long value = 40317281061831143L;

        SnowflakeIdWorker idWorker = new SnowflakeIdWorker();
//        for (int i = 0; i < 1000; i++) {
//            value = SnowflakeIdWorker.nextId(6);
//
//      System.out.println(value + "::::" +IdUtils.doParse(value).getSequence());
////
////            System.out.println(IdUtils.doParse(11536524554323431);
//        }
        System.out.println(IdUtils.doParse(11536655819252483L));
    }

}
