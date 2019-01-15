package com.lhl.semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Semaphore 英[ˈseməfɔ:(r)]
 * Semaphore类是一个计数信号量，必须由获取它的线程释放，
 * 通常用于限制可以访问某些资源（物理或逻辑的）线程数目。
 * <p>
 * Created by lihongli on 2019/1/15
 */
public class MySemaphore extends Thread {

    Semaphore position;
    private int id;

    public MySemaphore(int i, Semaphore semaphore) {
        this.id = i;
        this.position = semaphore;
    }

    @Override
    public void run() {
        try {
            int i = position.availablePermits();
            System.out.println("剩余空位" + i);
            if (i > 0) {
                System.out.println("顾客【" + this.id + "】进入，有空位");
            } else {
                System.out.println("顾客【" + this.id + "】进入，没有空位，排队");
            }
            position.acquire();
            System.out.println("顾客【" + this.id + "】获得空位");
            Thread.sleep(100);
            System.out.println("顾客【" + this.id + "】使用完成");
            position.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 代码注释部分用来理解acquireUninterruptibly的使用
     *
     * @param args
     */
    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        Semaphore position = new Semaphore(2, true);
        // 如果此处添加下面这样一句话 将无可用的许可 线程任务会阻塞
        // position.acquireUninterruptibly(2);
        for (int i = 0; i < 10; i++) {
            service.submit(new MySemaphore(i + 1, position));
        }
        // position.release(2);
        // 释放许可 线程可以重新运行
        service.shutdown();
        position.acquireUninterruptibly(2);
        position.release(2);
    }
}
