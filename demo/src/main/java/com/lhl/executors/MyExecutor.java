package com.lhl.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池基础用法
 * Created by lihongli on 2019/1/15
 */
public class MyExecutor extends Thread {
    private int index;
    public MyExecutor(int i){
        this.index = i;
    }

    @Override
    public void run() {
        try {
            System.out.println("[" + this.index + "] start...");
            Thread.sleep((int)(Math.random()*1000));
            System.out.println("[" + this.index + "] end.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        ExecutorService service = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 4; i++) {
//            service.execute(new MyExecutor(i));
            service.submit(new MyExecutor(i));
        }
        System.out.println("submit finish");
        service.shutdown();
    }
}
