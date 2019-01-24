package com.lhl.print_num_letter;

import java.util.concurrent.Semaphore;

/**
 * 打印数字
 * Created by lihongli on 2019/1/21
 */
public class PrintNumThread implements Runnable {
    private Semaphore semaphore1;
    private Semaphore semaphore2;

    public PrintNumThread(Semaphore semaphore1, Semaphore semaphore2) {
        this.semaphore1 = semaphore1;
        this.semaphore2 = semaphore2;
    }

    public void run() {
        for (int i = 1; i <= 52; i++) {
            try {
                // 实现思路：当前线程打印时，把另一个线程的许可给占用了，自己打印完再释放别人的许可让人家打印
                semaphore1.acquire();
                System.out.println(i);
                if(i % 2 == 0){
                    semaphore2.release();
                }else{
                    semaphore1.release();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
