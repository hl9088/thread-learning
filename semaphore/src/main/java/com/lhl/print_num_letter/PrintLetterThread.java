package com.lhl.print_num_letter;

import java.util.concurrent.Semaphore;

/**
 * 打印字母
 * Created by lihongli on 2019/1/21
 */
public class PrintLetterThread implements Runnable {
    private Semaphore semaphore1;
    private Semaphore semaphore2;

    public PrintLetterThread(Semaphore semaphore1, Semaphore semaphore2) {
        this.semaphore1 = semaphore1;
        this.semaphore2 = semaphore2;
    }

    public void run() {
        for (char i = 'A'; i <= 'Z'; i++) {
            try {
                semaphore2.acquire();
                System.out.println(i);
                semaphore1.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
