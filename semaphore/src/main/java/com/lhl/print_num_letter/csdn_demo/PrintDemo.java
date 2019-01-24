package com.lhl.print_num_letter.csdn_demo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * demo 源自 https://blog.csdn.net/chjttony/article/details/12434087
 * Created by lihongli on 2019/1/21
 */
public class PrintDemo {
    private final Semaphore semaphore = new Semaphore(1);
    private static char currentThread = 'A';

    public static void main(String[] args){
        ExecutorService service = Executors.newCachedThreadPool();
        PrintDemo printDemo = new PrintDemo();
        service.execute(printDemo.new RunnableA());
        service.execute(printDemo.new RunnableB());
        service.shutdown();
    }

    private class RunnableA implements Runnable{
        public void run() {
            for (int i = 1; i <= 52 ; i++) {
                try {
                    semaphore.acquire();
                    while(currentThread != 'A'){
                        semaphore.release();
                    }
                    System.out.println(i);
                    if(i%2 == 0){
                        currentThread = 'B';
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }
            }

        }
    }

    private class RunnableB implements Runnable{
        public void run() {
            for (char i = 'A'; i <= 'Z'; i++) {
                try {
                    semaphore.acquire();
                    while(currentThread != 'B'){
                        semaphore.release();
                    }
                    System.out.println(i);
                    currentThread = 'A';
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    semaphore.release();
                }
            }
        }
    }

}
