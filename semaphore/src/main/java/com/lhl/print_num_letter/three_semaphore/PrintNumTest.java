package com.lhl.print_num_letter.three_semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Created by lihongli on 2019/1/21
 */
public class PrintNumTest {
    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        Semaphore semaphore1 = new Semaphore(1);
        Semaphore semaphore2 = new Semaphore(1);
        Semaphore semaphore3 = new Semaphore(1);
        try {
            semaphore2.acquire();
            semaphore3.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        service.execute(new PrintNumThread(semaphore1, semaphore2, 1, "线程1"));
        service.execute(new PrintNumThread(semaphore2, semaphore3, 6, "线程2"));
        service.execute(new PrintNumThread(semaphore3, semaphore1, 11, "线程3"));
        service.shutdown();
    }
}
