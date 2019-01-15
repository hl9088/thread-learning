package com.lhl.semaphore.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 任务
 * Created by lihongli on 2019/1/15
 */
public class Task extends Thread {

    private int num;
    private ThreadPool threadPool;

    public Task(int num, ThreadPool threadPool) {
        this.num = num;
        this.threadPool = threadPool;
    }

    @Override
    public void run() {
//        System.err.println("任务" + num + "启动");
        try {
            // 获取处理任务的线程
            Thread thread = threadPool.getThread();
            if (thread != null) {
                System.out.println(thread.getName() + "正在处理任务" + num);
                TimeUnit.SECONDS.sleep(1);
                System.out.println(thread.getName() + "处理完成任务" + num);
                // 释放线程
                threadPool.releaseThread(thread);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 测试
    public static void main(String[] args) {
        ThreadPool threadPool = new ThreadPool();
        List<Task> tasks = new ArrayList<Task>();
        for (int i = 0; i < 5; i++) {
            tasks.add(new Task(i + 1, threadPool));
        }
        for (int i = 0; i < 5; i++) {
            tasks.get(i).start();
        }

       /*Executor executor = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            executor.execute(new Task(i + 1, threadPool));
        }*/
    }
}
