package com.guiji.process.agent.util;

import com.guiji.process.core.vo.DeviceStatusEnum;

import java.util.Random;

/**
 * Created by ty on 2018/11/21.
 */
public class tEST {

    public static void main(String [] aa)
    {
        Random r = new Random();

        for (int i = 0; i < 10; i++) {
            int rr = r.nextInt();

            int tt = rr % 2;

            System.out.println(tt);
        }

    }
}
