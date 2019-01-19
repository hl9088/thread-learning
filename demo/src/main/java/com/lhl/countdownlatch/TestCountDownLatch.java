package com.lhl.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lihongli on 2019/1/19
 */
public class TestCountDownLatch {

    public static void main(String[] args) throws Exception{
        // 开始的倒数锁
        final CountDownLatch begin = new CountDownLatch(1);
        // 结束的倒数锁
        final CountDownLatch end = new CountDownLatch(10);
        // 十名选手
        final ExecutorService exec = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 10; i++) {
            final int NO = i + 1;
            Runnable run = new Runnable() {
                @Override
                public void run() {
                    try {
                        begin.await();
                        Thread.sleep((int)(Math.random()*1000));
                        System.out.println("No." + NO + " arrived");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        end.countDown();
                    }
                }
            };
            exec.submit(run);
        }
        System.out.println("Game start");
        begin.countDown();
        end.await();
        System.out.println("Game over");
        exec.shutdown();
    }

}
