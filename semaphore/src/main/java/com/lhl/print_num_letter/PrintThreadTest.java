package com.lhl.print_num_letter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 实现思路：当前线程打印时，把另一个线程的许可给占用了，自己打印完再释放别人的许可让人家打印
 * Created by lihongli on 2019/1/21
 */
public class PrintThreadTest {
    public static void main(String[] args) {
        Semaphore semaphore1 = new Semaphore(1);
        Semaphore semaphore2 = new Semaphore(1);
        ExecutorService service = Executors.newCachedThreadPool();
        try {
            // 先把信号量2给占用了 保证优先执行信号量1绑定的任务
            semaphore2.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        service.execute(new PrintNumThread(semaphore1, semaphore2));
        service.execute(new PrintLetterThread(semaphore1, semaphore2));
        service.shutdown();
    }
}
