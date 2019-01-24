package com.lhl.print_num_letter.three_semaphore;

import java.util.concurrent.Semaphore;

/**
 * 间隔打印数字
 * Created by lihongli on 2019/1/21
 */
public class PrintNumThread implements Runnable {
    private Semaphore current;
    private Semaphore after;
    private int startNum;
    private String name;

    public PrintNumThread(Semaphore current, Semaphore after, int startNum, String name) {
        this.current = current;
        this.after = after;
        this.startNum = startNum;
        this.name = name;
    }

    public void run() {
        int count = 0;
        for (int i = startNum; i <= 100; i++) {
            try {
                current.acquire();
                System.out.println(name + " : " + i);
                count++;
                if (count == 5) {
                    // 打印够5次了 则换下一个线程打印
                    after.release();
                    i = i + 10;
                    // 重置为0 下次打印时继续用
                    count = 0;
                } else {
                    // 没有打印够5次 继续打印
                    current.release();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
