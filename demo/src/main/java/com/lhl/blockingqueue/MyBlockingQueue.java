package com.lhl.blockingqueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by lihongli on 2019/1/19
 */
public class MyBlockingQueue extends Thread {
    private static BlockingQueue<String> queue = new LinkedBlockingDeque<>(3);

    private int index;

    public MyBlockingQueue(int index) {
        this.index = index;
    }

    @Override
    public void run() {
        try {
            queue.put(String.valueOf(index));
            System.out.println("{" + index + "} in queue");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        ExecutorService service = Executors.newCachedThreadPool();

        for (int i = 0; i < 10; i++) {
            service.submit(new MyBlockingQueue(i));
        }

        Thread thread = new Thread(){
            public void run(){
                while (true){
                    try {
                        Thread.sleep((int)(Math.random()*1000));
                        if(MyBlockingQueue.queue.isEmpty()){
                            break;
                        }
                        String take = MyBlockingQueue.queue.take();
                        System.out.println(take + " has take!");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        service.submit(thread);
        service.shutdown();
    }
}
