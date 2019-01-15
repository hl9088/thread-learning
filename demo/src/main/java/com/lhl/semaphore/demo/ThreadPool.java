package com.lhl.semaphore.demo;

import java.util.concurrent.Semaphore;

/**
 * 使用信号量控制模拟实现对线程池的访问 参照jdk-api文档
 * Created by lihongli on 2019/1/15
 */
public class ThreadPool {

    private Semaphore semaphore = new Semaphore(5, true);

    private Thread[] threads = {new Thread("线程1"),
            new Thread("线程2"), new Thread("线程3"),
            new Thread("线程4"), new Thread("线程5")};
    private boolean[] used = new boolean[5];

    /**
     * 获取一个线程
     *
     * @return
     */
    public Thread getThread() throws InterruptedException {
        semaphore.acquire(1);
        return getNextAvaliableThread();
    }

    /**
     * 在线程使用完成后 释放该线程
     *
     * @param thread
     */
    public void releaseThread(Thread thread) {
        if (markAsUnUsed(thread)) {
            semaphore.release(1);
        }
    }

    /**
     * 循环遍历 取出一个没有被使用的线程
     *
     * @return
     */
    private synchronized Thread getNextAvaliableThread() {
        for (int i = 0; i < used.length; i++) {
            if (!used[i]) {
                System.err.println("从线程池从取出" + threads[i].getName());
                used[i] = true;
                return threads[i];
            }
        }
        return null;
    }

    /**
     * 标记线程为可使用状态
     *
     * @param thread
     * @return
     */
    private synchronized boolean markAsUnUsed(Thread thread) {
        for (int i = 0; i < used.length; i++) {
            if (thread == threads[i]) {
                if (used[i]) {
                    System.err.println("将" + threads[i].getName() + "放回线程池");
                    used[i] = false;
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }
}
